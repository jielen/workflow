 package com.kingdrive.workflow.service;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.model.meta.NodeModel;
 import com.kingdrive.workflow.model.meta.TemplateModel;
 import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
 import com.kingdrive.workflow.model.runtime.InstanceModel;
 import com.kingdrive.workflow.service.db.WFMetaService;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.springframework.util.Assert;
 
 public class RestartService extends BasicService
 {
   private static Logger logger = Logger.getLogger(RestartService.class);
 
   public void doExecute(WorkflowContext context) throws WorkflowException {
     String user = context.getExecutor();
     Long instanceId = context.getInstanceId();
     Long currentNodeId = context.getCurrentNodeId();
 
     Assert.notNull(instanceId, "流程实例id为空!");
     Assert.notNull(user, "提交人不能为空!");
 
     _restart(context);
   }
 
   private void _restart(WorkflowContext context) throws WorkflowException
   {
     try {
       Long instanceId = context.getInstanceId();
       InstanceModel instance = getRuntimeService().getInstanceById(instanceId);
       instance.setStatus(Long.valueOf("1"));
       instance.setEndTime(context.getExeTime());
       getRuntimeService().updateInstance(instance);
 
       TemplateModel template = getMetaService().getTemplate(context.getTemplateId());
       NodeModel startNode = template.getStartNode();
       List executors = getRuntimeService().getTaskExecutor(instanceId, startNode.getNodeId());
 
       getRuntimeService().removeActionByInst(instanceId);
       getRuntimeService().removePassByInst(instanceId);
       getRuntimeService().removeTaskExecutorByInst(instanceId);
       getRuntimeService().removeCurrentTaskByIns(instanceId);
       getRuntimeService().removeStateValueByInst(instanceId);
 
       getCommonService().createTaskExecutor(instanceId, startNode.getNodeId(), executors);
       getCommonService().createCurrentTask(instanceId, startNode.getNodeId(), context.getExecutor(), executors, context.getExeTime(), 
         "restart_instance");
       getCommonService().setInstanceStateByLinks(instanceId, template.getStartNode().getFromLinkList());
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
     actionHistory.setActionName("restart_instance");
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
 * Qualified Name:     com.kingdrive.workflow.service.RestartService
 * JD-Core Version:    0.6.0
 */