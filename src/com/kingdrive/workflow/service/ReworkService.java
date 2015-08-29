 package com.kingdrive.workflow.service;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.model.meta.TemplateModel;
 import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
 import com.kingdrive.workflow.model.runtime.ActionModel;
 import com.kingdrive.workflow.model.runtime.InstanceModel;
 import com.kingdrive.workflow.service.db.WFMetaService;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.springframework.util.Assert;
 
 public class ReworkService extends BasicService
 {
   private static Logger logger = Logger.getLogger(ReworkService.class);
 
   public void doExecute(WorkflowContext context) throws WorkflowException {
     String user = context.getExecutor();
     Long instanceId = context.getInstanceId();
 
     Assert.notNull(instanceId, "流程实例id为空!");
     Assert.notNull(user, "提交人不能为空!");
 
     _rework(context);
   }
 
   private void _rework(WorkflowContext context) throws WorkflowException {
     try {
       Long instanceId = context.getInstanceId();
       String user = context.getExecutor();
       TemplateModel template = getMetaService().getTemplate(context.getTemplateId());
 
       InstanceModel instance = getRuntimeService().getInstanceById(instanceId);
       instance.setStatus(Long.valueOf("1"));
       instance.setEndTime(context.getExeTime());
       getRuntimeService().updateInstance(instance);
 
       List actionList = getRuntimeService().getActionByinsId(instanceId);
       Assert.notEmpty(actionList, "活动节点为空，无法确定最后的活动节点。");
       ActionModel lastAction = (ActionModel)actionList.get(actionList.size() - 1);
       getRuntimeService().removeActionByNode(instanceId, lastAction.getNodeId());
       getCommonService().setInstanceStateByNode(instanceId, template.getNode(lastAction.getNodeId()));
       List executors = getRuntimeService().getTaskExecutor(instanceId, lastAction.getNodeId());
       getCommonService().createCurrentTask(instanceId, lastAction.getNodeId(), user, executors, context.getExeTime(), 
         "redo_instance");
       createActionHistory(instanceId, context);
     } catch (Exception ex) {
       logger.error(ex);
       throw new WorkflowException(ex);
     }
   }
 
   private void createActionHistory(Long instanceId, WorkflowContext context) {
     ActionHistoryModel actionHistory = new ActionHistoryModel();
     actionHistory.setInstanceId(instanceId);
     actionHistory.setNodeId(Long.valueOf("-9"));
     actionHistory.setActionName("redo_instance");
     actionHistory.setExecutor(context.getExecutor());
     actionHistory.setExecuteTime(context.getExeTime());
     String comment = context.getComment();
     comment = comment == null ? "" : comment;
     actionHistory.setDescription(comment);
     actionHistory.setOwner(context.getExecutor());
     actionHistory.setLimitExecuteTime("");
     getRuntimeService().createActionHistory(actionHistory);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.service.ReworkService
 * JD-Core Version:    0.6.0
 */