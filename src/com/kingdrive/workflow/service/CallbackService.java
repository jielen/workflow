 package com.kingdrive.workflow.service;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.model.TaskUser;
 import com.kingdrive.workflow.model.meta.LinkModel;
 import com.kingdrive.workflow.model.meta.NodeModel;
 import com.kingdrive.workflow.model.meta.TemplateModel;
 import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
 import com.kingdrive.workflow.model.runtime.ActionModel;
 import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
 import com.kingdrive.workflow.model.runtime.InstanceModel;
 import com.kingdrive.workflow.model.runtime.TaskExecutorModel;
 import com.kingdrive.workflow.service.db.WFMetaService;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import com.kingdrive.workflow.utils.WFUtil;
 import java.lang.reflect.InvocationTargetException;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.springframework.util.Assert;
 
 public class CallbackService extends BasicService
 {
   private static Logger logger = Logger.getLogger(CallbackService.class);
 
   public void doExecute(WorkflowContext context) throws WorkflowException
   {
     String user = context.getExecutor();
     Long instanceId = context.getInstanceId();
 
     Assert.notNull(instanceId, "流程实例id为空!");
     Assert.notNull(user, "提交人不能为空!");
     try
     {
       InstanceModel instance = getRuntimeService().getInstanceById(instanceId);
       TemplateModel template = getMetaService().getTemplate(instance.getTemplateId());
 
       ActionHistoryModel actionHistory = checkCallbackAvailable(instanceId, user);
       NodeModel currentNode = template.getNode(actionHistory.getNodeId());
 
       context.setCurrentNode(currentNode);
       executeListener(currentNode, "beforeCallback", context);
       _callback(template, actionHistory, context);
       executeListener(currentNode, "afterCallback", context);
     } catch (InvocationTargetException ex) {
       logger.error(ex);
       throw new WorkflowException("收回失败:" + ex.getTargetException() == null ? ex.getMessage() : ex.getTargetException().getMessage());
     } catch (Exception ex) {
       logger.error(ex);
       throw new WorkflowException(ex);
     }
   }
 
   private boolean checkNextNodeIsBingQian(NodeModel currentNode) {
     List list = currentNode.getFromLinkList();
     for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
       LinkModel po = (LinkModel)iterator.next();
       if ("1".equals(po.getNextNode().getExecutorsMethod())) {
         return true;
       }
     }
     return false;
   }
 
   private void _callback(TemplateModel template, ActionHistoryModel actionHistory, WorkflowContext context) {
     Long instanceId = context.getInstanceId();
     NodeModel currentNode = context.getCurrentNode();
     List nextTaskNode = template.getNextActualTaskNodes(currentNode, context);
 
     NodeModel nextNode = null;
     List linkList = new ArrayList();
     for (int i = 0; i < nextTaskNode.size(); i++) {
       nextNode = (NodeModel)nextTaskNode.get(i);
       linkList.add(nextNode.getRuntimeFromLink());
 
       removeTaskExecutorByNode(nextNode, instanceId);
 
       removeCurrentTaskByCreator(nextNode, instanceId, currentNode.getNodeId(), context);
       getCommonService().removeNodeState(instanceId, nextNode);
     }
 
     getCommonService().removeLinkState(instanceId, linkList);
 
     ActionModel action = new ActionModel();
     action.setInstanceId(instanceId);
     action.setNodeId(currentNode.getNodeId());
     action.setExecutor(context.getExecutor());
     List actionList = getRuntimeService().getAction(action);
     Assert.notEmpty(actionList, "没有找到当前节点，当前人的活动记录!");
     ActionModel ac = (ActionModel)actionList.get(actionList.size() - 1);
     getRuntimeService().removeAction(ac);
 
     List executors = new ArrayList();
 
     if ("1".equals(currentNode.getExecutorsMethod()))
       executors.add(new TaskUser(context.getExecutor()));
     else {
       executors = getCurrentNodeLatestExecutor(currentNode, actionHistory.getInstanceId());
     }
     getCommonService().createCurrentTask(instanceId, actionHistory.getNodeId(), context.getExecutor(), executors, context.getExeTime(), 
       "callback_flow");
     getCommonService().setInstanceStateByNode(instanceId, currentNode);
 
     actionHistory.setActionHistoryId(null);
     actionHistory.setActionName("callback_flow");
     actionHistory.setExecuteTime(WFUtil.getSysTime());
     actionHistory.setExecutor(context.getExecutor());
     actionHistory.setOwner(context.getExecutor());
     actionHistory.setDescription(context.getComment());
     String limitTime = actionHistory.getLimitExecuteTime();
     limitTime = limitTime == null ? "" : limitTime;
     actionHistory.setLimitExecuteTime(limitTime);
     getRuntimeService().createActionHistory(actionHistory);
   }
 
   private void removeCurrentTaskByCreator(NodeModel currentNode, Long instanceId, Long parentNodeId, WorkflowContext context)
   {
     getRuntimeService()
       .executeUpdate(
       "delete from wf_current_task ta where ta.instance_id = ? and  ta.node_id = ? and ta.creator in (select distinct e.executor from wf_task_executor e where e.instance_id = ? and e.node_id =?)", 
       new Object[] { instanceId, currentNode.getNodeId(), instanceId, parentNodeId });
 
     TemplateModel template = getMetaService().getTemplate(currentNode.getTemplateId());
     NodeModel parentNode = template.getNode(parentNodeId);
     List currTask = getRuntimeService().getCurentTaskByNode(instanceId, currentNode.getNodeId());
     if (currTask.size() > 0)
       for (Iterator iterator2 = currTask.iterator(); iterator2.hasNext(); ) {
         CurrentTaskModel task = (CurrentTaskModel)iterator2.next();
         NodeModel tempNode = template.getNode(task.getNodeId());
         List parentNodes = template.getParentTaskNodes(tempNode);
 
         if ((parentNodes.size() == 1) && (((NodeModel)parentNodes.get(0)).getNodeId().longValue() == parentNode.getNodeId().longValue()))
           getRuntimeService().removeCurrentTaskById(task.getCurrentTaskId());
         else
           throw new RuntimeException("不能确定当前单据的可收回代办任务!");
       }
   }
 
   private void removeTaskExecutorByNode(NodeModel currentNode, Long instanceId)
   {
     List list = getCurrentNodeLatestExecutorModel(currentNode, instanceId);
     for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
       TaskExecutorModel tem = (TaskExecutorModel)iterator.next();
 
       getRuntimeService()
         .executeUpdate(
         "delete from wf_task_executor t where t.task_executor_order_id = ? and t.executor in (select distinct e.executor from wf_task_executor e where e.instance_id = ? and e.node_id =?)", 
         new Object[] { tem.getTaskExecutorOrderId(), instanceId, currentNode.getNodeId() });
     }
   }
 
   public ActionHistoryModel checkCallbackAvailable(Long instanceId, String user) throws Exception {
     List actionHistorys = getRuntimeService().getActionHistoryByUser(instanceId, user);
     Assert.notEmpty(actionHistorys, "没有活动历史记录，不能收回!");
     ActionHistoryModel actionHistory = (ActionHistoryModel)actionHistorys.get(actionHistorys.size() - 1);
     Long historyNodeId = actionHistory.getNodeId();
 
     List actions = getRuntimeService().getActionByinsId(instanceId);
     Assert.notEmpty(actions, "没有活动记录，不能收回！");
     ActionModel action = (ActionModel)actions.get(actions.size() - 1);
     Long actionNodeId = action.getNodeId();
 
     if (historyNodeId.longValue() != actionNodeId.longValue()) {
       throw new Exception("该单据下一执行人已办，在当前状态不能收回!");
     }
     return actionHistory;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.service.CallbackService
 * JD-Core Version:    0.6.0
 */