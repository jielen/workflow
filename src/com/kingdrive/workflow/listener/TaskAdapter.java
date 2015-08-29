package com.kingdrive.workflow.listener;

import com.kingdrive.workflow.context.WorkflowContext;
import com.kingdrive.workflow.exception.WorkflowException;

public abstract class TaskAdapter
  implements TaskListener
{
  public void beforeExecution(WorkflowContext context)
    throws WorkflowException
  {
  }

  public void afterExecution(WorkflowContext context)
    throws WorkflowException
  {
  }

  public void beforeUntread(WorkflowContext context)
    throws WorkflowException
  {
  }

  public void afterUntread(WorkflowContext context)
    throws WorkflowException
  {
  }

  public void beforeCallback(WorkflowContext context)
    throws WorkflowException
  {
  }

  public void afterCallback(WorkflowContext context)
    throws WorkflowException
  {
  }
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.listener.TaskAdapter
 * JD-Core Version:    0.6.0
 */