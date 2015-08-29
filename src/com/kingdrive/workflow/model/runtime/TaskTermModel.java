 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 
 public class TaskTermModel
   implements Serializable
 {
   private int instanceId;
   private int nodeId;
   private int limitExecuteTerm;
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public int getInstanceId() {
     return this.instanceId;
   }
 
   public void setInstanceId(int instanceId) {
     this.instanceId = instanceId;
   }
 
   public int getLimitExecuteTerm() {
     return this.limitExecuteTerm;
   }
 
   public void setLimitExecuteTerm(int limitExecuteTerm) {
     this.limitExecuteTerm = limitExecuteTerm;
   }
 
   public int getNodeId() {
     return this.nodeId;
   }
 
   public void setNodeId(int nodeId) {
     this.nodeId = nodeId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.TaskTermModel
 * JD-Core Version:    0.6.0
 */