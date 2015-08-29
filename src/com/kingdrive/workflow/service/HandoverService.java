 package com.kingdrive.workflow.service;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.model.TaskUser;
 import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.springframework.util.Assert;
 
 public class HandoverService extends BasicService
 {
   private static Logger logger = Logger.getLogger(HandoverService.class);
 
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
     _handover(taskItem, context);
   }
 
   private void _handover(CurrentTaskModel taskItem, WorkflowContext context) throws WorkflowException {
     try {
       Assert.notNull(taskItem, "没有待办任务，不能移交!");
       List executors = context.getNextExecutor();
       String user = context.getExecutor();
       Assert.notEmpty(executors, "移交任务时，必须指定任务执行者。");
       TaskUser executor = null;
       for (int i = 0; i < executors.size(); i++) {
         executor = (TaskUser)executors.get(i);
         if (user.equals(executor.getUserId())) {
           throw new Exception("移交任务时，不能指定自己为任务执行者。");
         }
       }
 
       getRuntimeService().removeCurrentTaskById(taskItem.getCurrentTaskId());
 
       getRuntimeService().removeTaskExecutorByNodeExecutor(taskItem.getInstanceId(), taskItem.getNodeId(), user);
       getCommonService().createTaskExecutor(taskItem.getInstanceId(), taskItem.getNodeId(), executors);
 
       getCommonService().createActionHistory(taskItem, "forward_task", context.getExeTime(), context.getComment());
 
       getCommonService().createCurrentTask(taskItem.getInstanceId(), taskItem.getNodeId(), taskItem.getExecutor(), executors, 
         context.getExeTime(), "forward_task");
       getCommonService().createActionHistory(taskItem, "forward_task", context.getExeTime(), context.getComment());
     } catch (Exception ex) {
       logger.error(ex);
       throw new WorkflowException(ex);
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.service.HandoverService
 * JD-Core Version:    0.6.0
 */