 package com.kingdrive.workflow.dao.impl;
 
 import com.kingdrive.workflow.dao.WFRuntimeDao;
 import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
 import com.kingdrive.workflow.model.runtime.ActionModel;
 import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
 import com.kingdrive.workflow.model.runtime.DraftModel;
 import com.kingdrive.workflow.model.runtime.InstanceModel;
 import com.kingdrive.workflow.model.runtime.StateValueModel;
 import com.kingdrive.workflow.model.runtime.TaskExecutorModel;
 import java.sql.Connection;
 import java.sql.Statement;
 import java.util.List;
 import javax.sql.DataSource;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.jdbc.datasource.DataSourceUtils;
 import org.springframework.jdbc.support.JdbcUtils;
 import org.springframework.orm.ibatis.SqlMapClientTemplate;
 import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
 
 public class WFRuntimeDaoImpl extends SqlMapClientDaoSupport
   implements WFRuntimeDao
 {
   public DraftModel createDraft(DraftModel draft)
   {
     Long id = (Long)getSqlMapClientTemplate().insert("WFEngine.create_Draft", draft);
     draft.setDraftId(id);
     return draft;
   }
 
   public void removeDraftById(Long draftId) {
     getSqlMapClientTemplate().delete("WFEngine.remove_Draft_byId", draftId);
   }
 
   public DraftModel getDraftById(Long draftId) {
     return (DraftModel)getSqlMapClientTemplate().queryForObject("WFEngine.get_Draft_byId", draftId);
   }
 
   public InstanceModel createInscance(InstanceModel instance) {
     Long id = (Long)getSqlMapClientTemplate().insert("WFEngine.create_Instance", instance);
     instance.setInstanceId(id);
     return instance;
   }
 
   public void removeInstanceById(Long instanceId) {
     getSqlMapClientTemplate().delete("WFEngine.remove_Instance_byId", instanceId);
   }
 
   public InstanceModel getInstanceById(Long instanceId) {
     return (InstanceModel)getSqlMapClientTemplate().queryForObject("WFEngine.get_Instance_byId", instanceId);
   }
 
   public void updateInstance(InstanceModel instance) {
     getSqlMapClientTemplate().update("WFEngine.update_instance", instance);
   }
 
   public CurrentTaskModel createCurrentTask(CurrentTaskModel task) {
     Long id = (Long)getSqlMapClientTemplate().insert("WFEngine.create_CurrentTask", task);
     task.setCurrentTaskId(id);
     return task;
   }
 
   public List getCurrentTask(CurrentTaskModel task) {
     return getSqlMapClientTemplate().queryForList("WFEngine.get_currentTask", task);
   }
 
   public TaskExecutorModel createTaskExecutor(TaskExecutorModel executor) {
     Long id = (Long)getSqlMapClientTemplate().insert("WFEngine.create_TaskExecutor", executor);
     executor.setTaskExecutorOrderId(id);
     return executor;
   }
 
   public List getTaskExecutor(Long instanceId, Long nodeId) {
     TaskExecutorModel executor = new TaskExecutorModel();
     executor.setInstanceId(instanceId);
     executor.setNodeId(nodeId);
     return getSqlMapClientTemplate().queryForList("WFEngine.get_TaskExecutor", executor);
   }
 
   public void removeCurrentTask(CurrentTaskModel task) {
     getSqlMapClientTemplate().delete("WFEngine.remove_currentTask", task);
   }
 
   public void removeTaskExecutor(TaskExecutorModel executor) {
     getSqlMapClientTemplate().delete("WFEngine.remove_TaskExecutor", executor);
   }
 
   public ActionModel createAction(ActionModel action) {
     Long id = (Long)getSqlMapClientTemplate().insert("WFEngine.create_Action", action);
     action.setActionId(id);
     return action;
   }
 
   public void removeAction(ActionModel action) {
     getSqlMapClientTemplate().delete("WFEngine.remove_Action", action);
   }
 
   public List getAction(ActionModel action) {
     return getSqlMapClientTemplate().queryForList("WFEngine.get_Action", action);
   }
 
   public Long getActionCountByNodeLink(Long instanceId, Long nodeId, String linkName) {
     ActionModel action = new ActionModel();
     action.setInstanceId(instanceId);
     action.setNodeId(nodeId);
     action.setActionName(linkName);
     List result = getAction(action);
     return new Long(result.size());
   }
 
   public ActionHistoryModel createActionHistory(ActionHistoryModel actionHistory) {
     Long id = (Long)getSqlMapClientTemplate().insert("WFEngine.create_ActionHistory", actionHistory);
     actionHistory.setActionHistoryId(id);
     return actionHistory;
   }
 
   public List getActionHistory(ActionHistoryModel actionHistory) {
     return getSqlMapClientTemplate().queryForList("WFEngine.get_ActionHistory", actionHistory);
   }
 
   public StateValueModel createStateValue(StateValueModel stateValue) {
     Long id = (Long)getSqlMapClientTemplate().insert("WFEngine.create_StateValue", stateValue);
     stateValue.setStateValueId(id);
     return stateValue;
   }
 
   public List getStateValueByIns(Long instanceId) {
     StateValueModel stateValue = new StateValueModel();
     stateValue.setInstanceId(instanceId);
     return getSqlMapClientTemplate().queryForList("WFEngine.get_StateValue", stateValue);
   }
 
   public StateValueModel updateStateValue(StateValueModel stateValue) {
     getSqlMapClientTemplate().update("WFEngine.update_StateValue", stateValue);
     return stateValue;
   }
 
   public void removeSateValue(StateValueModel state) {
     getSqlMapClientTemplate().delete("WFEngine.remove_StateValue", state);
   }
 
   public List getSuperiorByPri(Integer nd) {
     return getSqlMapClientTemplate().queryForList("WFEngine.get_BusinessJunIor", nd);
   }
 
   public List queryForList(String sql, Object[] params) {
     JdbcTemplate template = new JdbcTemplate(getSqlMapClientTemplate().getDataSource());
     return template.queryForList(sql, params);
   }
 
   public List queryForList(String sql, Object[] params, Class model) {
     JdbcTemplate template = new JdbcTemplate(getSqlMapClientTemplate().getDataSource());
     return template.queryForList(sql, params, model);
   }
 
   public void executeBatchUpdate(List sqlList) {
     DataSource ds = null;
     Connection con = null;
     Statement sta = null;
     try {
       ds = getSqlMapClientTemplate().getDataSource();
       con = DataSourceUtils.getConnection(ds);
       sta = con.createStatement();
       sta.clearBatch();
       for (int i = 0; i < sqlList.size(); i++) {
         sta.addBatch((String)sqlList.get(i));
       }
       sta.executeBatch();
     } catch (Exception ex) {
       throw new RuntimeException(ex);
     } finally {
       JdbcUtils.closeStatement(sta);
       DataSourceUtils.releaseConnection(con, ds);
     }
   }
 
   public void executeUpdate(String sql, Object[] params) {
     JdbcTemplate template = new JdbcTemplate(getSqlMapClientTemplate().getDataSource());
     template.update(sql, params);
   }
 
   public List getTaskExecutorModel(TaskExecutorModel m)
   {
     return getSqlMapClientTemplate().queryForList("WFEngine.get_TaskExecutorModel", m);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dao.impl.WFRuntimeDaoImpl
 * JD-Core Version:    0.6.0
 */