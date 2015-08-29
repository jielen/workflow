package com.kingdrive.workflow.dialog.message;

import java.io.PrintStream;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.NamingException;

public abstract class AsynMessThread extends Thread
{
  private ConnectionFactory cf = null;

  private Queue queue = null;
  private AsynMessageListener listener;
  private String selector;

  public ConnectionFactory getCf()
  {
    return this.cf;
  }

  public void setCf(ConnectionFactory cf)
  {
    this.cf = cf;
  }

  public Queue getQueue()
  {
    return this.queue;
  }

  public void setQueue(Queue queue)
  {
    this.queue = queue;
  }

  public void setReceiveMessage(boolean receiveMessage)
  {
    this.listener.setReceiveMessage(receiveMessage);
  }

  public AsynMessThread(AsynMessageListener listener, String selector)
    throws NamingException, JMSException
  {
    this.listener = listener;
    this.selector = selector;
    initMessageEnv();
  }

  public AsynMessThread(AsynMessageListener listener, String selector, boolean start)
    throws NamingException, JMSException
  {
    this.listener = listener;
    this.selector = selector;
    initMessageEnv();
    if (start)
      start();
  }

  public void run()
  {
    Connection connection = null;
    Session session = null;
    MessageConsumer consumer = null;
    try {
      connection = this.cf.createConnection();
      session = connection.createSession(false, 1);
      if ((this.selector != null) && (this.selector.length() > 0))
        consumer = session.createConsumer(this.queue, this.selector);
      else {
        consumer = session.createConsumer(this.queue);
      }
      consumer.setMessageListener(this.listener);
      connection.start();
      this.listener.getLatch().waitTillDone();
    } catch (Exception ex) {
      System.err.println(ex);
      try
      {
        connection.close(); } catch (Exception localException1) {
      } } finally { try { connection.close();
      }
      catch (Exception localException2)
      {
      }
    }
  }

  abstract void initMessageEnv()
    throws NamingException, JMSException;
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.message.AsynMessThread
 * JD-Core Version:    0.6.0
 */