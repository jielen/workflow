 package com.kingdrive.workflow.service;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.model.meta.LinkModel;
 import com.kingdrive.workflow.model.meta.NodeModel;
 import com.kingdrive.workflow.model.meta.TemplateModel;
 import com.kingdrive.workflow.model.runtime.ActionModel;
 import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
 import com.kingdrive.workflow.model.runtime.InstanceModel;
 import com.kingdrive.workflow.service.db.WFMetaService;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import java.lang.reflect.InvocationTargetException;
 import java.util.List;
 import org.apache.commons.beanutils.BeanUtils;
 import org.apache.log4j.Logger;
 import org.springframework.util.Assert;
 
 public class CommitService extends BasicService
 {
   private static Logger logger = Logger.getLogger(CommitService.class);
 
   public void doExecute(WorkflowContext context) throws WorkflowException {
     String user = context.getExecutor();
     Long instanceId = context.getInstanceId();
     Long currentNodeId = context.getCurrentNodeId();
 
     Assert.notNull(instanceId, "流程实例id为空!");
     Assert.notNull(user, "提交人不能为空!");
 
     CurrentTaskModel taskItem = null;
     if (currentNodeId != null) {
       taskItem = getRuntimeService().getCurrentTaskByNodeUser(instanceId, currentNodeId, user);
     } else {
       List currentTask = getRuntimeService().getCurrentTaskByUser(instanceId, user);
 
       if (currentTask.size() > 0) {
         taskItem = (CurrentTaskModel)currentTask.get(0);
       }
     }
 
     if (taskItem.isGrantedTask()) {
       String[] granterInfo = taskItem.getGranterInfo().split(",");
       WorkflowContext granterContext = null;
       try {
         granterContext = (WorkflowContext)BeanUtils.cloneBean(context);
       } catch (Exception ex) {
         throw new WorkflowException(ex);
       }
 
       granterContext.setVariable("WF_EXECUTOR", granterInfo[1]);
       granterContext.setVariable("WF_COMPANY_CODE", granterInfo[2]);
       granterContext.setVariable("WF_ORG_CODE", granterInfo[3]);
       granterContext.setVariable("WF_POSITION_ID", granterInfo[4]);
       granterContext.setVariable("WF_POSITION_CODE", granterInfo[5]);
       granterContext.setVariable("svCoCode", granterInfo[2]);
       granterContext.setVariable("svOrgCode", granterInfo[3]);
       granterContext.setVariable("svCoLevel", new Integer(granterInfo[6]));
       granterContext.setVariable("svPoCode", granterInfo[5]);
       context.setGranterContext(granterContext);
     }
     _commitSingleTaskItem(taskItem, context);
   }
 
   private void _commitSingleTaskItem(CurrentTaskModel taskItem, WorkflowContext context)
     throws WorkflowException
   {
     try
     {
       Assert.notNull(taskItem, "没有待办任务，不能提交!");
       Long templateId = taskItem.getInstance().getTemplateId();
       TemplateModel template = getMetaService().getTemplate(templateId);
       Assert.notNull(template, "模板 " + templateId + " 不存在!");
       NodeModel currentNode = template.getNode(taskItem.getNodeId());
       taskItem.setNode(currentNode);
       context.setCurrentNode(currentNode);
       executeListener(currentNode, "beforeExecution", context);
 
       List nextTaskNode = template.getNextTaskNodes(currentNode, context);
       Assert.notEmpty(nextTaskNode, "无法确定下一流程节点!");
       for (int i = 0; i < nextTaskNode.size(); i++) {
         NodeModel nextNode = (NodeModel)nextTaskNode.get(i);
 
         List nextExecutor = context.getNextExecutor();
         if ((nextExecutor == null) || (nextExecutor.isEmpty())) {
           nextExecutor = getResourceService().getExecutorsByRelation(nextNode, context);
         }
         _commitSingleNodeTask(taskItem, nextNode, template, nextExecutor, context);
       }
 
       executeListener(currentNode, "afterExecution", context);
     } catch (InvocationTargetException ex) {
       logger.error(ex);
       throw new WorkflowException("提交失败:" + ex.getTargetException() == null ? ex.getMessage() : ex.getTargetException().getMessage());
     } catch (Exception e) {
       logger.error(e);
       throw new WorkflowException("提交失败:" + e.getMessage());
     }
   }
 
   private void _commitSingleNodeTask(CurrentTaskModel taskItem, NodeModel nextNode, TemplateModel template, List nextExecutor, WorkflowContext context) throws WorkflowException
   {
     boolean taskFinishFlag = false;
 
     InstanceModel instance = taskItem.getInstance();
     NodeModel currentNode = taskItem.getNode();
 
     String exeTime = context.getExeTime();
     createCurrentAction(taskItem, nextNode, context, exeTime);
     if (getCommonService().canFinishNodeTask(taskItem, nextNode)) {
       if (nextNode.isLastNode()) {
         instance.setEndTime(exeTime);
         instance.setStatus(new Long(9L));
         getCommonService().setInstanceStateByLink(instance.getInstanceId(), nextNode.getRuntimeFromLink());
         getCommonService().finishWorkflow(instance);
       }
       else {
         Assert.notEmpty(nextExecutor, "未找到下一节点:" + nextNode.getNodeId() + " 执行人！");
         getCommonService().createTaskExecutor(instance.getInstanceId(), nextNode.getNodeId(), nextExecutor);
 
         getCommonService().createCurrentTask(taskItem.getInstanceId(), nextNode.getNodeId(), taskItem.getExecutor(), nextExecutor, exeTime, 
           nextNode.getRuntimeFromLink().getActionName());
         getCommonService().setInstanceStateByNode(instance.getInstanceId(), nextNode);
       }
       getRuntimeService().removeCurrentTaskByNode(instance.getInstanceId(), taskItem.getNodeId());
       taskFinishFlag = true;
     } else {
       getRuntimeService().removeCurrentTaskByNode(instance.getInstanceId(), taskItem.getNodeId(), context.getExecutor());
     }
     String comment = context.getComment();
     comment = comment == null ? "" : comment;
     comment = comment.length() == 0 ? "同意" : comment;
     getCommonService().createActionHistory(taskItem, nextNode.getRuntimeFromLink().getActionName(), exeTime, context.getComment());
     nextNode.setFinished(taskFinishFlag);
   }
 
   private void createCurrentAction(CurrentTaskModel taskItem, NodeModel nextNode, WorkflowContext context, String exeTime)
   {
     ActionModel action = new ActionModel();
     action.setInstanceId(taskItem.getInstanceId());
     action.setNodeId(taskItem.getNodeId());
     action.setActionName(nextNode.getRuntimeFromLink().getActionName());
     action.setExecutor(context.getExecutor());
     action.setExecuteTime(exeTime);
     action.setDescription(context.getComment());
     action.setOwner(taskItem.getOwner());
     action.setLimitExecuteTime(taskItem.getLimitExecuteTime());
     getRuntimeService().createAction(action);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.service.CommitService
 * JD-Core Version:    0.6.0
 */