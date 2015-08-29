 package com.kingdrive.workflow.service;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.listener.TaskListener;
 import com.kingdrive.workflow.model.TableData;
 import com.kingdrive.workflow.model.TaskUser;
 import com.kingdrive.workflow.model.meta.BindVariableModel;
 import com.kingdrive.workflow.model.meta.CompoModel;
 import com.kingdrive.workflow.model.meta.NodeModel;
 import com.kingdrive.workflow.model.meta.TemplateModel;
 import com.kingdrive.workflow.model.runtime.ActionModel;
 import com.kingdrive.workflow.model.runtime.TaskExecutorModel;
 import com.kingdrive.workflow.service.db.WFMetaService;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import com.kingdrive.workflow.utils.WFUtil;
 import java.lang.reflect.InvocationTargetException;
 import java.math.BigDecimal;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.beanutils.MethodUtils;
 import org.springframework.util.Assert;
 
 public abstract class BasicService
 {
   private WFRuntimeService runtimeService;
   private WFMetaService metaService;
   private WFCommonService commonService;
   private ResourceService resourceService;
 
   public WFMetaService getMetaService()
   {
     return this.metaService;
   }
 
   public void setMetaService(WFMetaService metaService) {
     this.metaService = metaService;
   }
 
   public WFRuntimeService getRuntimeService() {
     return this.runtimeService;
   }
 
   public void setRuntimeService(WFRuntimeService runtimeService) {
     this.runtimeService = runtimeService;
   }
 
   public WFCommonService getCommonService() {
     return this.commonService;
   }
 
   public void setCommonService(WFCommonService commonService) {
     this.commonService = commonService;
   }
 
   public ResourceService getResourceService()
   {
     return this.resourceService;
   }
 
   public void setResourceService(ResourceService resourceService)
   {
     this.resourceService = resourceService;
   }
 
   public void executeListener(NodeModel node, String methodName, WorkflowContext context)
     throws WorkflowException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException
   {
     Assert.notNull(node);
     String listenerStr = node.getTaskListener();
     if (listenerStr == null)
       return;
     String[] listeners = listenerStr.split(";");
     for (int i = 0; i < listeners.length; i++) {
       TaskListener listener = (TaskListener)Class.forName(listeners[i]).newInstance();
       MethodUtils.invokeMethod(listener, methodName, new Object[] { context });
     }
   }
 
   public void execute(WorkflowContext context)
     throws WorkflowException
   {
     try
     {
       TableData entity = context.getEntityData();
       if (context.getTemplateId() == null) {
         CompoModel compo = getRuntimeService().getCompoInfoById(entity.getName());
         Assert.notNull(compo, "部件:" + compo.getCompoId() + "不存在!");
         context.setTemplateId(compo.getTempolateId());
         context.setTableName(compo.getMasterTabId());
 
         if (context.getInstanceId() == null) {
           context.setInstanceId(entity.getInstanceId());
         }
       }
       context.setExeTime(WFUtil.getSysTime());
       setDefaultVariableValue(context);
       doExecute(context);
 
       getCommonService().syncDataByBindedWFSate(context.getInstanceId(), context.getTemplateId());
     }
     catch (Exception ex)
     {
       throw new WorkflowException(ex);
     }
   }
 
   public abstract void doExecute(WorkflowContext paramWorkflowContext)
     throws WorkflowException;
 
   public void setDefaultVariableValue(WorkflowContext context)
   {
     setBindVariableValue(context);
 
     String nd = (String)context.getVariable("ND");
     nd = nd == null ? WFUtil.getCurrentNd() : nd;
     context.setVariable("ND", nd);
   }
 
   public void setBindVariableValue(WorkflowContext context)
   {
     Long templateId = context.getTemplateId();
     TemplateModel template = this.metaService.getTemplate(templateId);
     Iterator iterator = template.getBindVariableList().iterator();
     BindVariableModel bindVariable = null;
     while (iterator.hasNext()) {
       bindVariable = (BindVariableModel)iterator.next();
       initBindVariable(bindVariable, context);
     }
   }
 
   private void initBindVariable(BindVariableModel bindVariable, WorkflowContext context)
   {
     String sql = "";
     String variableName = bindVariable.getName();
     if (context.getVariable(variableName) == null) {
       String tableId = bindVariable.getTableId();
       String expression = bindVariable.getBindExpression();
       String condition = bindVariable.getCondition();
       String filterByKey = bindVariable.getFilterByKey();
       filterByKey = filterByKey == null ? "" : filterByKey.toUpperCase();
       String type = bindVariable.getType();
 
       sql = " SELECT " + expression + " FROM " + tableId;
       String where = "";
       if ("Y".equals(filterByKey)) {
         where = " " + tableId + "." + "PROCESS_INST_ID=" + context.getInstanceId().longValue() + " ";
       }
       if ((condition != null) && (condition.length() > 0)) {
         if (where.length() > 0)
           where = where + " AND " + condition;
         else {
           where = condition;
         }
       }
       if (where.length() > 0) {
         sql = sql + " WHERE " + where;
       }
       List result = getRuntimeService().queryForList(sql, null);
       if (result.size() > 1) {
         throw new WorkflowException(sql + ": 查询结果多于一条!");
       }
       if (result.size() == 0) {
         throw new WorkflowException(sql + ": 没有符合条件记录!");
       }
       Map record = (Map)result.get(0);
       Object value = record.values().iterator().next();
       if (BindVariableModel.STRING.equals(type)) {
         value = value == null ? "" : value;
         context.setVariable(variableName, value.toString());
       } else {
         value = value == null ? "0" : value;
         context.setVariable(variableName, Float.valueOf(value.toString()));
       }
     }
   }
 
   public List getCurrentNodesLatestActionModel(NodeModel currentNode, Long instanceId) {
     List resultList = new ArrayList();
     List parentLatestAction = getParentNodesLatestActionModel(currentNode, instanceId);
     ActionModel para = new ActionModel();
     para.setInstanceId(instanceId);
     para.setNodeId(currentNode.getNodeId());
     List nodeAction = getRuntimeService().getAction(para);
     for (Iterator iterator = nodeAction.iterator(); iterator.hasNext(); ) {
       ActionModel tem = (ActionModel)iterator.next();
       for (Iterator iterator2 = parentLatestAction.iterator(); iterator2.hasNext(); ) {
         ActionModel parentTem = (ActionModel)iterator2.next();
         if (Long.valueOf(tem.getExecuteTime()).longValue() > Long.valueOf(parentTem.getExecuteTime()).longValue()) {
           resultList.add(tem);
           break;
         }
       }
     }
     return resultList;
   }
 
   private List getParentNodesLatestActionModel(NodeModel currentNode, Long instanceId) {
     TemplateModel template = getMetaService().getTemplate(currentNode.getTemplateId());
     List parentNodes = template.getParentTaskNodes(currentNode);
     List resultList = new ArrayList();
     for (Iterator iterator = parentNodes.iterator(); iterator.hasNext(); ) {
       NodeModel parentNode = (NodeModel)iterator.next();
       List latestAction = getRuntimeService().queryForList(
         "select * from wf_action ac where ac.instance_id = ? and ac.node_id = ? order by ac.execute_time desc", 
         new Object[] { instanceId, parentNode.getNodeId() });
       if ((latestAction != null) && (latestAction.size() > 0)) {
         Map mMap = (Map)latestAction.get(0);
         ActionModel m = new ActionModel();
         m.setActionId(new Long(((BigDecimal)mMap.get("ACTION_ID")).longValue()));
         m.setActionName(toStringHelp(mMap.get("ACTION_NAME")));
         m.setDescription(toStringHelp(mMap.get("DESCRIPTION")));
         m.setExecuteTime(toStringHelp(mMap.get("EXECUTE_TIME")));
         m.setExecutor(toStringHelp(mMap.get("EXECUTOR")));
         m.setInstanceId(new Long(((BigDecimal)mMap.get("INSTANCE_ID")).longValue()));
         m.setLimitExecuteTime(toStringHelp(mMap.get("LIMIT_EXECUTE_TIME")));
         m.setNodeId(new Long(((BigDecimal)mMap.get("NODE_ID")).longValue()));
         m.setOwner(toStringHelp(mMap.get("OWNER")));
         resultList.add(m);
       }
     }
     return resultList;
   }
 
   public List getParentNodesLatestExecutor(NodeModel currentNode, Long instancId)
   {
     TemplateModel template = getMetaService().getTemplate(currentNode.getTemplateId());
     List parentNodes = template.getParentTaskNodes(currentNode);
     List resultList = new ArrayList();
     for (Iterator iterator = parentNodes.iterator(); iterator.hasNext(); ) {
       NodeModel parentNode = (NodeModel)iterator.next();
       if (parentNode == null) {
         continue;
       }
       List latestExecutor = getRuntimeService().queryForList(
         "SELECT * FROM wf_task_executor t where t.instance_id = ? and t.node_id = ? order by t.task_executor_order_id desc ", 
         new Object[] { instancId, parentNode.getNodeId() });
       if ((latestExecutor != null) && (latestExecutor.size() > 0)) {
         Map poMap = (Map)latestExecutor.get(0);
         TaskExecutorModel tem = new TaskExecutorModel();
         tem.setExecutor((String)poMap.get("EXECUTOR"));
         tem.setExecutorOrder(new Long(((BigDecimal)poMap.get("EXECUTOR_ORDER")).longValue()));
         tem.setResponsibility(new Long(((BigDecimal)poMap.get("RESPONSIBILITY")).longValue()));
         tem.setTaskExecutorOrderId(new Long(((BigDecimal)poMap.get("TASK_EXECUTOR_ORDER_ID")).longValue()));
         tem.setNodeId(new Long(((BigDecimal)poMap.get("NODE_ID")).longValue()));
         tem.setInstanceId(new Long(((BigDecimal)poMap.get("INSTANCE_ID")).longValue()));
         resultList.add(tem);
       }
     }
     return resultList;
   }
 
   public List getTaskExecutorModelByNodeId(Long nodeId, Long instanceId)
   {
     List resultList = new ArrayList();
     List executorModel = getRuntimeService().queryForList("SELECT * FROM wf_task_executor t where t.instance_id = ? and t.node_id = ? ", 
       new Object[] { instanceId, nodeId });
     for (Iterator iterator = executorModel.iterator(); iterator.hasNext(); ) {
       Map poMap = (Map)iterator.next();
       TaskExecutorModel tem = new TaskExecutorModel();
       tem.setExecutor((String)poMap.get("EXECUTOR"));
       tem.setExecutorOrder(new Long(((BigDecimal)poMap.get("EXECUTOR_ORDER")).longValue()));
       tem.setResponsibility(new Long(((BigDecimal)poMap.get("RESPONSIBILITY")).longValue()));
       tem.setTaskExecutorOrderId(new Long(((BigDecimal)poMap.get("TASK_EXECUTOR_ORDER_ID")).longValue()));
       tem.setNodeId(new Long(((BigDecimal)poMap.get("NODE_ID")).longValue()));
       tem.setInstanceId(new Long(((BigDecimal)poMap.get("INSTANCE_ID")).longValue()));
       resultList.add(tem);
     }
     return resultList;
   }
 
   public List getCurrentNodeLatestExecutorModel(NodeModel currentNode, Long instancId) {
     List resultList = new ArrayList();
     List parentLatestExecutor = getParentNodesLatestExecutor(currentNode, instancId);
     List nodeExecutor = getTaskExecutorModelByNodeId(currentNode.getNodeId(), instancId);
     if (parentLatestExecutor.size() == 0) {
       return nodeExecutor;
     }
     for (Iterator iterator = nodeExecutor.iterator(); iterator.hasNext(); ) {
       TaskExecutorModel tem = (TaskExecutorModel)iterator.next();
       for (Iterator iterator2 = parentLatestExecutor.iterator(); iterator2.hasNext(); ) {
         TaskExecutorModel parentTem = (TaskExecutorModel)iterator2.next();
         if (tem.getTaskExecutorOrderId().longValue() > parentTem.getTaskExecutorOrderId().longValue()) {
           resultList.add(tem);
           break;
         }
       }
     }
     return resultList;
   }
 
   public List getCurrentNodeLatestExecutor(NodeModel currentNode, Long instancId) {
     List resultList = new ArrayList();
     List executorModels = getCurrentNodeLatestExecutorModel(currentNode, instancId);
     for (Iterator iterator = executorModels.iterator(); iterator.hasNext(); ) {
       TaskExecutorModel po = (TaskExecutorModel)iterator.next();
       resultList.add(new TaskUser(po.getExecutor()));
     }
     return resultList;
   }
 
   public String toStringHelp(Object obj) {
     if (obj == null)
       return "";
     return obj.toString();
   }
 
   public void removeActionByNode(NodeModel currentNode, Long instanceId) {
     List parentLatestAction = getCurrentNodesLatestActionModel(currentNode, instanceId);
     for (Iterator iterator = parentLatestAction.iterator(); iterator.hasNext(); ) {
       ActionModel ac = (ActionModel)iterator.next();
       getRuntimeService().removeAction(ac);
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.service.BasicService
 * JD-Core Version:    0.6.0
 */