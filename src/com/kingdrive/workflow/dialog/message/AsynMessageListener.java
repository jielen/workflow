package com.kingdrive.workflow.dialog.message;

import java.io.PrintStream;
import javax.jms.Message;
import javax.jms.MessageListener;

public class AsynMessageListener
  implements MessageListener
{
  private DoneLatch latch = new DoneLatch();
  private MessageCallback callback;
  private volatile boolean receiveMessage = true;

  public boolean isReceiveMessage()
  {
    return this.receiveMessage;
  }

  public void setReceiveMessage(boolean receiveMessage)
  {
    this.receiveMessage = receiveMessage;
  }

  public AsynMessageListener()
  {
  }

  public AsynMessageListener(MessageCallback cb) {
    this.callback = cb;
  }

  public DoneLatch getLatch()
  {
    return this.latch;
  }

  public void onMessage(Message mess)
  {
    try {
      if (isReceiveMessage())
        this.callback.onMessage(mess);
    }
    catch (Exception ex) {
      System.err.println(ex);
      this.latch.allDone();
    }
  }
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.message.AsynMessageListener
 * JD-Core Version:    0.6.0
 */