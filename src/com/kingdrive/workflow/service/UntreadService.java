 package com.kingdrive.workflow.service;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.model.meta.NodeModel;
 import com.kingdrive.workflow.model.meta.TemplateModel;
 import com.kingdrive.workflow.model.runtime.ActionModel;
 import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
 import com.kingdrive.workflow.model.runtime.InstanceModel;
 import com.kingdrive.workflow.model.runtime.TaskExecutorModel;
 import com.kingdrive.workflow.service.db.WFMetaService;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import java.lang.reflect.InvocationTargetException;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.springframework.util.Assert;
 
 public class UntreadService extends BasicService
 {
   private static Logger logger = Logger.getLogger(UntreadService.class);
 
   public void doExecute(WorkflowContext context) throws WorkflowException
   {
     try {
       String user = context.getExecutor();
       Long instanceId = context.getInstanceId();
       Long currentNodeId = context.getCurrentNodeId();
       Long nextNodeId = context.getNextNodeId();
 
       Assert.notNull(instanceId, "流程实例id为空!");
       Assert.notNull(user, "提交人不能为空!");
       Assert.notNull(nextNodeId, "回退节点不能为空!");
 
       CurrentTaskModel taskItem = null;
       if (currentNodeId != null) {
         taskItem = getRuntimeService().getCurrentTaskByNodeUser(instanceId, currentNodeId, user);
       } else {
         List currentTask = getRuntimeService().getCurrentTaskByUser(instanceId, user);
 
         if (currentTask.size() > 0) {
           taskItem = (CurrentTaskModel)currentTask.get(0);
         }
 
       }
 
       Assert.notNull(taskItem, "不是代办任务，不能回退!");
 
       TemplateModel template = getMetaService().getTemplate(taskItem.getInstance().getTemplateId());
       NodeModel currentNode = template.getNode(taskItem.getNodeId());
       context.setCurrentNode(currentNode);
       executeListener(currentNode, "beforeUntread", context);
       _untread(taskItem, context);
       executeListener(currentNode, "afterUntread", context);
     } catch (InvocationTargetException ex) {
       logger.error(ex);
       throw new WorkflowException("退回失败:" + ex.getTargetException() == null ? ex.getMessage() : ex.getTargetException().getMessage());
     } catch (Exception ex) {
       logger.error(ex);
       throw new WorkflowException(ex);
     }
   }
 
   private void _untread(CurrentTaskModel taskItem, WorkflowContext context) throws Exception {
     TemplateModel template = getMetaService().getTemplate(taskItem.getInstance().getTemplateId());
     NodeModel currentNode = context.getCurrentNode();
     if (currentNode.equals(template.getStartNode())) {
       throw new Exception("流程首节点不能回退!");
     }
 
     Long untreadNodeId = context.getNextNodeId();
     NodeModel untreadNode = null;
 
     if (untreadNodeId.longValue() == -1L) {
       List betweenNodeList = getCommonService().getExecutedNodeListBetween(template, taskItem.getNodeId(), untreadNodeId, 
         taskItem.getInstanceId());
       untreadNode = template.getNode((Long)betweenNodeList.get(0));
       betweenNodeList = betweenNodeList.subList(0, 1);
     } else if (untreadNodeId.longValue() == -2L) {
       untreadNode = template.getStartNode();
     }
     else {
       untreadNode = template.getNode(untreadNodeId);
     }
     Assert.notNull(untreadNode, "未找到回退节点不存在!");
 
     Long instanceId = taskItem.getInstanceId();
     Long currentNodeId = taskItem.getNodeId();
 
     getRuntimeService().removeCurrentTaskByNode(instanceId, currentNodeId);
     getRuntimeService().removeTaskExecutorByNode(instanceId, currentNodeId);
     getCommonService().removeNodeState(instanceId, currentNode);
     if (untreadNodeId.longValue() == -1L) {
       clearUntreadNodeInfo(instanceId, untreadNode);
     } else if (untreadNodeId.longValue() == -2L) {
       ActionModel action = new ActionModel();
       action.setInstanceId(instanceId);
       getRuntimeService().removeAction(action);
 
       removeTaskExecutorsUntread2StartNode(instanceId, template.getStartNode().getNodeId());
     }
     else
     {
       removeAction2UntreadNode(untreadNode, instanceId);
       removeTaskExecutors2UntreadNode(untreadNode, instanceId);
     }
 
     List executors = getCurrentNodeLatestExecutor(untreadNode, instanceId);
     getCommonService().createCurrentTask(instanceId, untreadNode.getNodeId(), taskItem.getExecutor(), executors, context.getExeTime(), 
       "untread_flow");
 
     getCommonService().setInstanceStateByLinks(instanceId, untreadNode.getFromLinkList());
     getCommonService().createActionHistory(taskItem, "untread_flow", context.getExeTime(), context.getComment());
   }
 
   private void removeTaskExecutors2UntreadNode(NodeModel untreadNode, Long instanceId) {
     TaskExecutorModel m = new TaskExecutorModel();
     m.setInstanceId(instanceId);
     List executors = getRuntimeService().getTaskExecutorModel(m);
     int isFind = 0;
     for (Iterator iterator = executors.iterator(); iterator.hasNext(); ) {
       TaskExecutorModel exe = (TaskExecutorModel)iterator.next();
       if (((isFind == 0) || (isFind == 1)) && (exe.getNodeId().equals(untreadNode.getNodeId()))) {
         isFind = 1;
       }
       else if ((isFind == 1) || (isFind == 2)) {
         isFind = 2;
         getRuntimeService().removeTaskExecutorById(exe.getTaskExecutorOrderId());
       }
     }
   }
 
   private void removeAction2UntreadNode(NodeModel untreadNode, Long instanceId)
   {
     List actions = getRuntimeService().getActionByinsId(instanceId);
     boolean isFind = false;
     for (Iterator iterator = actions.iterator(); iterator.hasNext(); ) {
       ActionModel am = (ActionModel)iterator.next();
       if ((!isFind) && (am.getNodeId().equals(untreadNode.getNodeId()))) {
         isFind = true;
       }
       if (isFind)
         getRuntimeService().removeAction(am);
     }
   }
 
   private void removeTaskExecutorsUntread2StartNode(Long instanceId, Long startNodeId)
   {
     getRuntimeService().executeUpdate("delete from wf_task_executor t where t.instance_id = ? and t.node_id <> ?", 
       new Object[] { instanceId, startNodeId });
   }
 
   private void clearUntreadNodeInfo(Long instanceId, NodeModel untreadNode)
   {
     removeActionByNode(untreadNode, instanceId);
   }
 
   private void clearBetweenNodeInfo(Long instanceId, List nodeList)
   {
     for (int i = 0; i < nodeList.size(); i++) {
       Long nodeId = (Long)nodeList.get(i);
 
       getRuntimeService().removeActionByNode(instanceId, nodeId);
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.service.UntreadService
 * JD-Core Version:    0.6.0
 */