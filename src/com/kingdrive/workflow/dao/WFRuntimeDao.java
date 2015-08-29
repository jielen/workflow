package com.kingdrive.workflow.dao;

import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
import com.kingdrive.workflow.model.runtime.ActionModel;
import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
import com.kingdrive.workflow.model.runtime.DraftModel;
import com.kingdrive.workflow.model.runtime.InstanceModel;
import com.kingdrive.workflow.model.runtime.StateValueModel;
import com.kingdrive.workflow.model.runtime.TaskExecutorModel;
import java.util.List;

public abstract interface WFRuntimeDao
{
  public abstract DraftModel createDraft(DraftModel paramDraftModel);

  public abstract DraftModel getDraftById(Long paramLong);

  public abstract void removeDraftById(Long paramLong);

  public abstract InstanceModel createInscance(InstanceModel paramInstanceModel);

  public abstract InstanceModel getInstanceById(Long paramLong);

  public abstract void removeInstanceById(Long paramLong);

  public abstract void updateInstance(InstanceModel paramInstanceModel);

  public abstract CurrentTaskModel createCurrentTask(CurrentTaskModel paramCurrentTaskModel);

  public abstract List getCurrentTask(CurrentTaskModel paramCurrentTaskModel);

  public abstract void removeCurrentTask(CurrentTaskModel paramCurrentTaskModel);

  public abstract TaskExecutorModel createTaskExecutor(TaskExecutorModel paramTaskExecutorModel);

  public abstract List getTaskExecutor(Long paramLong1, Long paramLong2);

  public abstract void removeTaskExecutor(TaskExecutorModel paramTaskExecutorModel);

  public abstract ActionModel createAction(ActionModel paramActionModel);

  public abstract void removeAction(ActionModel paramActionModel);

  public abstract List getAction(ActionModel paramActionModel);

  public abstract Long getActionCountByNodeLink(Long paramLong1, Long paramLong2, String paramString);

  public abstract ActionHistoryModel createActionHistory(ActionHistoryModel paramActionHistoryModel);

  public abstract List getActionHistory(ActionHistoryModel paramActionHistoryModel);

  public abstract StateValueModel createStateValue(StateValueModel paramStateValueModel);

  public abstract List getStateValueByIns(Long paramLong);

  public abstract void removeSateValue(StateValueModel paramStateValueModel);

  public abstract StateValueModel updateStateValue(StateValueModel paramStateValueModel);

  public abstract List queryForList(String paramString, Object[] paramArrayOfObject);

  public abstract List queryForList(String paramString, Object[] paramArrayOfObject, Class paramClass);

  public abstract void executeBatchUpdate(List paramList);

  public abstract void executeUpdate(String paramString, Object[] paramArrayOfObject);

  public abstract List getSuperiorByPri(Integer paramInteger);

  public abstract List getTaskExecutorModel(TaskExecutorModel paramTaskExecutorModel);
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dao.WFRuntimeDao
 * JD-Core Version:    0.6.0
 */