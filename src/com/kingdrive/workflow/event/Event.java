 package com.kingdrive.workflow.event;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 
 public class Event
 {
   public static String COMMIT_BRFORE = "commit_before";
 
   public static String COMMIT_AFTER = "commit_after";
 
   public static String UNTREAD_BRFORE = "untread_before";
 
   public static String UNTREAD_AFTER = "untread_after";
 
   public static String CALLBACK_BRFORE = "callback_before";
 
   public static String CALLBACK_AFTER = "callback_after";
 
   public static String TRANSFER_BEFORE = "transfer_before";
 
   public static String TRANSFER_ARTER = "transter_after";
 
   public static String ACTIVATE_BEFORE = "activate_before";
 
   public static String ACTIVATE_ARTER = "activate_after";
 
   public static String DEACTIVATE_BEFORE = "deactivate_before";
 
   public static String DEACTIVATE_ARTER = "deactivate_after";
 
   public static String INTERRUPT_BEFORE = "interrupt_before";
 
   public static String INTERRUPT_AFTER = "interrupt_after";
 
   public static String REWORK_BEFORE = "rework_before";
 
   public static String REWORK_AFTER = "rework_after";
 
   public static String RESTART_BEFORE = "restart_before";
 
   public static String RESTART_AFTER = "restart_after";
   private String eventType;
   private WorkflowContext workflowContext;
 
   public Event()
   {
   }
 
   public Event(String type, WorkflowContext context)
   {
     this.eventType = type;
     this.workflowContext = context;
   }
 
   public String getEventType()
   {
     return this.eventType;
   }
 
   public void setEventType(String eventType)
   {
     this.eventType = eventType;
   }
 
   public WorkflowContext getWorkflowContext()
   {
     return this.workflowContext;
   }
 
   public void setWorkflowContext(WorkflowContext workflowContext)
   {
     this.workflowContext = workflowContext;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.event.Event
 * JD-Core Version:    0.6.0
 */