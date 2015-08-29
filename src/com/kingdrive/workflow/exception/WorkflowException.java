 package com.kingdrive.workflow.exception;
 
 public class WorkflowException extends RuntimeException
 {
   private static final long serialVersionUID = 8877429042890984336L;
 
   public WorkflowException(String mess)
   {
     super(mess);
   }
 
   public WorkflowException(Exception ex) {
     super(ex.getMessage(), ex);
   }
 
   public WorkflowException(String message, Exception ex) {
     super(message, ex);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.exception.WorkflowException
 * JD-Core Version:    0.6.0
 */