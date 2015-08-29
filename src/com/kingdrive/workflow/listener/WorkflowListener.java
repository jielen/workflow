package com.kingdrive.workflow.listener;

import com.kingdrive.workflow.event.Event;
import com.kingdrive.workflow.exception.WorkflowException;

public abstract interface WorkflowListener
{
  public abstract void beforeCommit(Event paramEvent)
    throws WorkflowException;

  public abstract void afterCommit(Event paramEvent)
    throws WorkflowException;
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.listener.WorkflowListener
 * JD-Core Version:    0.6.0
 */