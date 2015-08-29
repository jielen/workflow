package com.kingdrive.workflow.dialog.message;

import javax.jms.Message;

public abstract interface MessageCallback
{
  public abstract void onMessage(Message paramMessage);
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.message.MessageCallback
 * JD-Core Version:    0.6.0
 */