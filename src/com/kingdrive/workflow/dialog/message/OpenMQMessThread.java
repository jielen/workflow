 package com.kingdrive.workflow.dialog.message;
 
 import javax.jms.JMSException;
 import javax.naming.NamingException;
 
 public class OpenMQMessThread extends AsynMessThread
 {
   public OpenMQMessThread(AsynMessageListener listener, String selector)
     throws NamingException, JMSException
   {
     super(listener, selector);
   }
 
   public OpenMQMessThread(AsynMessageListener listener, String selector, boolean start) throws NamingException, JMSException {
     super(listener, selector, start);
   }
 
   protected void initMessageEnv()
     throws NamingException, JMSException
   {
     throw new Error(
       "Unresolved compilation problems: \n\tcom.sun.messaging cannot be resolved to a type\n\tcom.sun.messaging cannot be resolved to a type\n\tConnectionConfiguration cannot be resolved\n\tcom.sun.messaging cannot be resolved to a type\n\tcom.sun.messaging cannot be resolved to a type\n");
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.message.OpenMQMessThread
 * JD-Core Version:    0.6.0
 */