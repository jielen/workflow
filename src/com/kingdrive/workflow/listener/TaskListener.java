package com.kingdrive.workflow.listener;

import com.kingdrive.workflow.context.WorkflowContext;
import com.kingdrive.workflow.exception.WorkflowException;

public abstract interface TaskListener
{
  public abstract void beforeExecution(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void afterExecution(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void beforeUntread(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void afterUntread(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void beforeCallback(WorkflowContext paramWorkflowContext)
    throws WorkflowException;

  public abstract void afterCallback(WorkflowContext paramWorkflowContext)
    throws WorkflowException;
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.listener.TaskListener
 * JD-Core Version:    0.6.0
 */