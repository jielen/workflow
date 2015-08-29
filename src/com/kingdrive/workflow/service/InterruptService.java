 package com.kingdrive.workflow.service;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
 import com.kingdrive.workflow.model.runtime.InstanceModel;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import com.kingdrive.workflow.utils.WFUtil;
 import org.apache.log4j.Logger;
 import org.springframework.util.Assert;
 
 public class InterruptService extends BasicService
 {
   private static Logger logger = Logger.getLogger(InterruptService.class);
 
   public void doExecute(WorkflowContext context) throws WorkflowException {
     String user = context.getExecutor();
     Long instanceId = context.getInstanceId();
 
     Assert.notNull(instanceId, "流程实例id为空!");
     Assert.notNull(user, "提交人不能为空!");
 
     _interrupt(context);
   }
 
   public void interruptNotJudge(WorkflowContext context) {
     try {
       InstanceModel instance = getRuntimeService().getInstanceById(context.getInstanceId());
       if (instance.getStatus().longValue() == -9L) {
         return;
       }
       instance.setStatus(Long.valueOf("-9"));
       context.setExeTime(WFUtil.getSysTime());
       instance.setEndTime(context.getExeTime());
       getRuntimeService().updateInstance(instance);
       createActionHistory(instance.getInstanceId(), context);
     } catch (Exception ex) {
       logger.error(ex);
       throw new WorkflowException(ex);
     }
   }
 
   private void _interrupt(WorkflowContext context) throws WorkflowException {
     try {
       InstanceModel instance = getRuntimeService().getInstanceById(context.getInstanceId());
       if (instance.getStatus().longValue() == -9L) {
         return;
       }
       if (instance.getStatus().longValue() == 9L) {
         throw new Exception("工作流实例已经结束，不能中止。");
       }
       instance.setStatus(Long.valueOf("-9"));
       instance.setEndTime(context.getExeTime());
       getRuntimeService().updateInstance(instance);
       createActionHistory(instance.getInstanceId(), context);
     } catch (Exception ex) {
       logger.error(ex);
       throw new WorkflowException(ex);
     }
   }
 
   private void createActionHistory(Long instanceId, WorkflowContext context) {
     ActionHistoryModel actionHistory = new ActionHistoryModel();
     actionHistory.setInstanceId(instanceId);
     actionHistory.setNodeId(Long.valueOf("-9"));
     actionHistory.setActionName("interrupt_instance");
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
 * Qualified Name:     com.kingdrive.workflow.service.InterruptService
 * JD-Core Version:    0.6.0
 */