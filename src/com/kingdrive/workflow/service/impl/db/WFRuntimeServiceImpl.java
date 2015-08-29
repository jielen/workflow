 package com.kingdrive.workflow.service.impl.db;
 
 import com.kingdrive.workflow.dao.WFRuntimeDao;
 import com.kingdrive.workflow.model.TaskUser;
 import com.kingdrive.workflow.model.meta.CompoModel;
 import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
 import com.kingdrive.workflow.model.runtime.ActionModel;
 import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
 import com.kingdrive.workflow.model.runtime.DraftModel;
 import com.kingdrive.workflow.model.runtime.InstanceModel;
 import com.kingdrive.workflow.model.runtime.StateValueModel;
 import com.kingdrive.workflow.model.runtime.TaskExecutorModel;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 
 public class WFRuntimeServiceImpl
   implements WFRuntimeService
 {
   private WFRuntimeDao runtimeDao;
 
   public WFRuntimeDao getRuntimeDao()
   {
     return this.runtimeDao;
   }
 
   public void setRuntimeDao(WFRuntimeDao runtimeDao) {
     this.runtimeDao = runtimeDao;
   }
 
   public DraftModel createDraft(DraftModel draft)
   {
     return this.runtimeDao.createDraft(draft);
   }
 
   public DraftModel getDraftById(Long draftId)
   {
     return this.runtimeDao.getDraftById(draftId);
   }
 
   public void removeDraftById(Long draftId)
   {
     this.runtimeDao.removeDraftById(draftId);
   }
 
   public InstanceModel createInscance(InstanceModel instance) {
     return this.runtimeDao.createInscance(instance);
   }
 
   public InstanceModel getInstanceById(Long instanceId) {
     return this.runtimeDao.getInstanceById(instanceId);
   }
 
   public void removeInstanceById(Long id) {
     this.runtimeDao.removeInstanceById(id);
   }
 
   public void updateInstance(InstanceModel instance) {
     this.runtimeDao.updateInstance(instance);
   }
 
   public CurrentTaskModel createCurrentTask(CurrentTaskModel task) {
     return this.runtimeDao.createCurrentTask(task);
   }
 
   public List getCurrentTaskByUser(Long instanceId, String user) {
     CurrentTaskModel task = new CurrentTaskModel();
     task.setInstanceId(instanceId);
     task.setNodeId(null);
     task.setExecutor(user);
     return this.runtimeDao.getCurrentTask(task);
   }
 
   public CurrentTaskModel getCurrentTaskByNodeUser(Long instanceId, Long nodeId, String user) {
     CurrentTaskModel task = new CurrentTaskModel();
     task.setInstanceId(instanceId);
     task.setNodeId(nodeId);
     task.setExecutor(user);
     List list = getRuntimeDao().getCurrentTask(task);
     return (CurrentTaskModel)list.get(0);
   }
 
   public List getCurentTaskByNode(Long instanceId, Long nodeId) {
     CurrentTaskModel task = new CurrentTaskModel();
     task.setInstanceId(instanceId);
     task.setNodeId(nodeId);
     task.setExecutor(null);
     return this.runtimeDao.getCurrentTask(task);
   }
 
   public boolean hasCurrentTask(Long instanceId, Long nodeId) {
     List list = getCurentTaskByNode(instanceId, nodeId);
 
     return list.size() > 0;
   }
 
   public void removeCurrentTaskById(Long taskId)
   {
     CurrentTaskModel task = new CurrentTaskModel();
     task.setCurrentTaskId(taskId);
     this.runtimeDao.removeCurrentTask(task);
   }
 
   public void removeCurrentTaskByNode(Long instanceId, Long nodeId) {
     CurrentTaskModel task = new CurrentTaskModel();
     task.setInstanceId(instanceId);
     task.setNodeId(nodeId);
     this.runtimeDao.removeCurrentTask(task);
   }
 
   public void removeCurrentTaskByIns(Long instanceId) {
     CurrentTaskModel task = new CurrentTaskModel();
     task.setInstanceId(instanceId);
     this.runtimeDao.removeCurrentTask(task);
   }
 
   public TaskExecutorModel createTaskExecutor(TaskExecutorModel executor) {
     return this.runtimeDao.createTaskExecutor(executor);
   }
 
   public List getTaskExecutor(Long instanceId, Long nodeId) {
     List list = this.runtimeDao.getTaskExecutor(instanceId, nodeId);
     List result = new ArrayList();
     for (int i = 0; i < list.size(); i++) {
       result.add(new TaskUser((String)list.get(i)));
     }
     return result;
   }
 
   public void removeTaskExecutorByNode(Long instanceId, Long nodeId) {
     TaskExecutorModel taskExecutor = new TaskExecutorModel();
     taskExecutor.setInstanceId(instanceId);
     taskExecutor.setNodeId(nodeId);
     this.runtimeDao.removeTaskExecutor(taskExecutor);
   }
 
   public void removeTaskExecutorByInst(Long instanceId) {
     TaskExecutorModel taskExecutor = new TaskExecutorModel();
     taskExecutor.setInstanceId(instanceId);
     taskExecutor.setNodeId(null);
     this.runtimeDao.removeTaskExecutor(taskExecutor);
   }
 
   public void removeTaskExecutorByNodeExecutor(Long instanceId, Long nodeId, String executor) {
     TaskExecutorModel taskExecutor = new TaskExecutorModel();
     taskExecutor.setInstanceId(instanceId);
     taskExecutor.setNodeId(nodeId);
     taskExecutor.setExecutor(executor);
     this.runtimeDao.removeTaskExecutor(taskExecutor);
   }
 
   public ActionModel createAction(ActionModel action) {
     this.runtimeDao.createAction(action);
     return action;
   }
 
   public void removeAction(ActionModel action) {
     this.runtimeDao.removeAction(action);
   }
 
   public void removeActionByNode(Long instanceId, Long nodeId) {
     ActionModel action = new ActionModel();
     action.setInstanceId(instanceId);
     action.setNodeId(nodeId);
     removeAction(action);
   }
 
   public void removeActionByInst(Long instanceId) {
     ActionModel action = new ActionModel();
     action.setInstanceId(instanceId);
     action.setNodeId(null);
     removeAction(action);
   }
 
   public List getAction(ActionModel action) {
     return getRuntimeDao().getAction(action);
   }
 
   public List getActionByinsId(Long instanceId) {
     ActionModel action = new ActionModel();
     action.setInstanceId(instanceId);
     action.setExecutor(null);
     action.setActionName(null);
     return getAction(action);
   }
 
   public Long getActionCountByNodeLink(Long instanceId, Long nodeId, String linkName) {
     Long count = getRuntimeDao().getActionCountByNodeLink(instanceId, nodeId, linkName);
     return count;
   }
 
   public int getActionCountByNode(Long instanceId, Long nodeId) {
     ActionModel action = new ActionModel();
     action.setInstanceId(instanceId);
     action.setNodeId(nodeId);
     action.setActionName(null);
     return getAction(action).size();
   }
 
   public ActionHistoryModel createActionHistory(ActionHistoryModel actionHistory) {
     return getRuntimeDao().createActionHistory(actionHistory);
   }
 
   public List getActionHistoryByUser(Long instanceId, String user) {
     ActionHistoryModel actionHistory = new ActionHistoryModel();
     actionHistory.setInstanceId(instanceId);
     actionHistory.setExecutor(user);
     return this.runtimeDao.getActionHistory(actionHistory);
   }
 
   public List getActionHistoryByIns(Long instanceId) {
     ActionHistoryModel actionHistory = new ActionHistoryModel();
     actionHistory.setInstanceId(instanceId);
     actionHistory.setExecutor(null);
     return this.runtimeDao.getActionHistory(actionHistory);
   }
 
   public StateValueModel createStateValue(StateValueModel stateValue) {
     return getRuntimeDao().createStateValue(stateValue);
   }
 
   public List getStateValueByIns(Long instanceId) {
     return getRuntimeDao().getStateValueByIns(instanceId);
   }
 
   public StateValueModel updateStateValue(StateValueModel stateValue) {
     return getRuntimeDao().updateStateValue(stateValue);
   }
 
   public void removeSateValue(StateValueModel state) {
     getRuntimeDao().removeSateValue(state);
   }
 
   public void removeStateValueByInst(Long instanceId) {
     StateValueModel stateValue = new StateValueModel();
     stateValue.setInstanceId(instanceId);
     getRuntimeDao().removeSateValue(stateValue);
   }
 
   public List getSuperStaffSet(String executor, String orgPosiCode, Long nd) {
     String sql = "select distinct(a.STAFF_ID) from wf_staff a, wf_staff_position b, wf_org_position_level c, wf_staff_position d where a.staff_id = b.staff_id and b.org_position_id = c.parent_id and c.org_position_id = d.org_position_id and d.staff_id = ? and d.org_position_id =? and b.nd=?";
 
     List list = queryForList(sql, new Object[] { executor, orgPosiCode, nd });
     List result = new ArrayList();
     Map data = null;
     for (Iterator iter = list.iterator(); iter.hasNext(); ) {
       data = (Map)iter.next();
       result.add(data.get("STAFF_ID"));
     }
     return result;
   }
 
   public List getPreSetExecutor(Long nextNodeId, Long nd) {
     String sql = "SELECT DISTINCT(t.executor) FROM v_wf_executor_source t WHERE t.node_id=?";
     List list = queryForList(sql, new Object[] { nextNodeId });
     List result = new ArrayList();
     Map data = null;
     for (Iterator iter = list.iterator(); iter.hasNext(); ) {
       data = (Map)iter.next();
       result.add(data.get("EXECUTOR"));
     }
     return result;
   }
 
   public List queryForList(String sql, Object[] params) {
     return getRuntimeDao().queryForList(sql, params);
   }
 
   public List queryForList(String sql, Object[] params, Class model) {
     return getRuntimeDao().queryForList(sql, params, model);
   }
 
   public void executeBatchUpdate(List sqlList) {
     getRuntimeDao().executeBatchUpdate(sqlList);
   }
 
   public void executeUpdate(String sql, Object[] params) {
     getRuntimeDao().executeUpdate(sql, params);
   }
 
   public List getSuperior(Integer nd) {
     return getRuntimeDao().getSuperiorByPri(nd);
   }
 
   public CompoModel getCompoInfoById(String compoId) {
     String sql = "SELECT t.default_wf_template TEMPLATE, t.master_tab_id MASTETAB_ID, t.compo_name COMPO_NAME FROM as_compo t WHERE t.compo_id=?";
     List list = queryForList(sql, new String[] { compoId });
     if (list.size() > 0) {
       CompoModel model = new CompoModel();
       model.setCompoId(compoId);
       Map map = (Map)list.get(0);
       model.setTempolateId(new Long((String)map.get("TEMPLATE")));
       model.setCompoName((String)map.get("COMPO_NAME"));
       model.setMasterTabId((String)map.get("MASTETAB_ID"));
       return model;
     }
     return null;
   }
 
   public void removePassByInst(Long instanceId)
   {
     String sql = "delete from wf_pass t where t.instance_id=?";
     executeUpdate(sql, new Object[] { instanceId });
   }
 
   public void removeCurrentTaskByNode(Long instanceId, Long nodeId, String executor)
   {
     CurrentTaskModel task = new CurrentTaskModel();
     task.setInstanceId(instanceId);
     task.setNodeId(nodeId);
     task.setExecutor(executor);
     this.runtimeDao.removeCurrentTask(task);
   }
 
   public void removeCurrentTaskByCreator(Long instanceId, Long nodeId, String creator)
   {
     CurrentTaskModel task = new CurrentTaskModel();
     task.setInstanceId(instanceId);
     task.setNodeId(nodeId);
     task.setCreator(creator);
     this.runtimeDao.removeCurrentTask(task);
   }
 
   public List getCurentTaskByInstanceId(Long instanceId) {
     CurrentTaskModel task = new CurrentTaskModel();
     task.setInstanceId(instanceId);
     return this.runtimeDao.getCurrentTask(task);
   }
 
   public List getTaskExecutorModel(TaskExecutorModel m)
   {
     return this.runtimeDao.getTaskExecutorModel(m);
   }
 
   public void removeTaskExecutorById(Long taskExecutorOrderId)
   {
     TaskExecutorModel taskExecutor = new TaskExecutorModel();
     taskExecutor.setTaskExecutorOrderId(taskExecutorOrderId);
     this.runtimeDao.removeTaskExecutor(taskExecutor);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.service.impl.db.WFRuntimeServiceImpl
 * JD-Core Version:    0.6.0
 */