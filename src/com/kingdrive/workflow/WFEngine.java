package com.kingdrive.workflow;

import com.kingdrive.workflow.context.WorkflowContext;
import com.kingdrive.workflow.exception.WorkflowException;
import com.kingdrive.workflow.model.runtime.TraceInfoModel;

public abstract interface WFEngine
{
  public abstract void commit(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void untread(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void callback(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void transfer(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void handover(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void deactivate(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void activate(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void interrupt(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void interruptNotJudge(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void rework(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void restart(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void newCommit(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract TraceInfoModel getTraceInfo(Long paramLong)
    throws WorkflowException;

  public abstract void cancelGrantedTask(String paramString)
    throws WorkflowException;
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.WFEngine
 * JD-Core Version:    0.6.0
 */