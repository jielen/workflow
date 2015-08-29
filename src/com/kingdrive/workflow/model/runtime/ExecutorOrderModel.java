 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 
 public class ExecutorOrderModel
   implements Serializable
 {
   private int nodeId;
   private String executor = null;
   private int executorOrder;
   private int responsibility;
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getExecutor() {
     return this.executor;
   }
 
   public void setExecutor(String executor) {
     this.executor = executor;
   }
 
   public int getExecutorOrder() {
     return this.executorOrder;
   }
 
   public void setExecutorOrder(int executorOrder) {
     this.executorOrder = executorOrder;
   }
 
   public int getNodeId() {
     return this.nodeId;
   }
 
   public void setNodeId(int nodeId) {
     this.nodeId = nodeId;
   }
 
   public int getResponsibility() {
     return this.responsibility;
   }
 
   public void setResponsibility(int responsibility) {
     this.responsibility = responsibility;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.ExecutorOrderModel
 * JD-Core Version:    0.6.0
 */