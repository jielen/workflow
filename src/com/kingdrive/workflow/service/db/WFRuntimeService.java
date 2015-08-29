package com.kingdrive.workflow.service.db;

import com.kingdrive.workflow.model.meta.CompoModel;
import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
import com.kingdrive.workflow.model.runtime.ActionModel;
import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
import com.kingdrive.workflow.model.runtime.DraftModel;
import com.kingdrive.workflow.model.runtime.InstanceModel;
import com.kingdrive.workflow.model.runtime.StateValueModel;
import com.kingdrive.workflow.model.runtime.TaskExecutorModel;
import java.util.List;

public abstract interface WFRuntimeService
{
  public abstract DraftModel createDraft(DraftModel paramDraftModel);

  public abstract DraftModel getDraftById(Long paramLong);

  public abstract void removeDraftById(Long paramLong);

  public abstract InstanceModel createInscance(InstanceModel paramInstanceModel);

  public abstract InstanceModel getInstanceById(Long paramLong);

  public abstract void removeInstanceById(Long paramLong);

  public abstract void updateInstance(InstanceModel paramInstanceModel);

  public abstract CurrentTaskModel createCurrentTask(CurrentTaskModel paramCurrentTaskModel);

  public abstract List getCurrentTaskByUser(Long paramLong, String paramString);

  public abstract CurrentTaskModel getCurrentTaskByNodeUser(Long paramLong1, Long paramLong2, String paramString);

  public abstract List getCurentTaskByNode(Long paramLong1, Long paramLong2);

  public abstract boolean hasCurrentTask(Long paramLong1, Long paramLong2);

  public abstract void removeCurrentTaskById(Long paramLong);

  public abstract void removeCurrentTaskByNode(Long paramLong1, Long paramLong2);

  public abstract void removeCurrentTaskByIns(Long paramLong);

  public abstract TaskExecutorModel createTaskExecutor(TaskExecutorModel paramTaskExecutorModel);

  public abstract List getTaskExecutor(Long paramLong1, Long paramLong2);

  public abstract void removeTaskExecutorByNode(Long paramLong1, Long paramLong2);

  public abstract void removeTaskExecutorByInst(Long paramLong);

  public abstract void removeTaskExecutorByNodeExecutor(Long paramLong1, Long paramLong2, String paramString);

  public abstract ActionModel createAction(ActionModel paramActionModel);

  public abstract List getAction(ActionModel paramActionModel);

  public abstract List getActionByinsId(Long paramLong);

  public abstract Long getActionCountByNodeLink(Long paramLong1, Long paramLong2, String paramString);

  public abstract int getActionCountByNode(Long paramLong1, Long paramLong2);

  public abstract void removeAction(ActionModel paramActionModel);

  public abstract void removeActionByNode(Long paramLong1, Long paramLong2);

  public abstract void removeActionByInst(Long paramLong);

  public abstract ActionHistoryModel createActionHistory(ActionHistoryModel paramActionHistoryModel);

  public abstract List getActionHistoryByUser(Long paramLong, String paramString);

  public abstract List getActionHistoryByIns(Long paramLong);

  public abstract StateValueModel createStateValue(StateValueModel paramStateValueModel);

  public abstract List getStateValueByIns(Long paramLong);

  public abstract StateValueModel updateStateValue(StateValueModel paramStateValueModel);

  public abstract void removeSateValue(StateValueModel paramStateValueModel);

  public abstract void removeStateValueByInst(Long paramLong);

  public abstract List getSuperStaffSet(String paramString1, String paramString2, Long paramLong);

  public abstract List getPreSetExecutor(Long paramLong1, Long paramLong2);

  public abstract List queryForList(String paramString, Object[] paramArrayOfObject);

  public abstract void executeBatchUpdate(List paramList);

  public abstract void executeUpdate(String paramString, Object[] paramArrayOfObject);

  public abstract List getSuperior(Integer paramInteger);

  public abstract CompoModel getCompoInfoById(String paramString);

  public abstract void removePassByInst(Long paramLong);

  public abstract void removeCurrentTaskByNode(Long paramLong1, Long paramLong2, String paramString);

  public abstract void removeCurrentTaskByCreator(Long paramLong1, Long paramLong2, String paramString);

  public abstract List getCurentTaskByInstanceId(Long paramLong);

  public abstract List getTaskExecutorModel(TaskExecutorModel paramTaskExecutorModel);

  public abstract void removeTaskExecutorById(Long paramLong);
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.service.db.WFRuntimeService
 * JD-Core Version:    0.6.0
 */