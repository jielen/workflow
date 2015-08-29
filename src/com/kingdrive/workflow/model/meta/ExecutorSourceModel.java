 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class ExecutorSourceModel
   implements Serializable
 {
   private int nodeId;
   private String executor = null;
   private int source;
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
 
   public int getSource() {
     return this.source;
   }
 
   public void setSource(int source) {
     this.source = source;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.ExecutorSourceModel
 * JD-Core Version:    0.6.0
 */