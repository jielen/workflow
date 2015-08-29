 package com.kingdrive.workflow.service;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
 import com.kingdrive.workflow.model.runtime.InstanceModel;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import org.apache.log4j.Logger;
 import org.springframework.util.Assert;
 
 public class ActivateService extends BasicService
 {
   private static Logger logger = Logger.getLogger(ActivateService.class);
 
   public void doExecute(WorkflowContext context) throws WorkflowException {
     String user = context.getExecutor();
     Long instanceId = context.getInstanceId();
 
     Assert.notNull(instanceId, "流程实例id为空!");
     Assert.notNull(user, "提交人不能为空!");
 
     _activate(context);
   }
 
   public void _activate(WorkflowContext context) throws WorkflowException {
     try {
       Long instanceId = context.getInstanceId();
       String user = context.getExecutor();
       InstanceModel instance = getRuntimeService().getInstanceById(instanceId);
       if (instance.getStatus().longValue() == 1L) {
         return;
       }
       if (instance.getStatus().longValue() != -1L) {
         throw new Exception("工作流实例已经结束或中止，不能激活/挂起。");
       }
       instance.setStatus(Long.valueOf("1"));
       getRuntimeService().updateInstance(instance);
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
     actionHistory.setActionName("activate_instance");
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
 * Qualified Name:     com.kingdrive.workflow.service.ActivateService
 * JD-Core Version:    0.6.0
 */