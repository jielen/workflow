 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class NodeStateModel
   implements Serializable
 {
   private Long nodeStateId;
   private Long nodeId;
   private Long stateId;
   private String stateValue = null;
 
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public Long getNodeId() {
     return this.nodeId;
   }
 
   public void setNodeId(Long nodeId) {
     this.nodeId = nodeId;
   }
 
   public Long getNodeStateId() {
     return this.nodeStateId;
   }
 
   public void setNodeStateId(Long nodeStateId) {
     this.nodeStateId = nodeStateId;
   }
 
   public Long getStateId() {
     return this.stateId;
   }
 
   public void setStateId(Long stateId) {
     this.stateId = stateId;
   }
 
   public String getStateValue() {
     return this.stateValue;
   }
 
   public void setStateValue(String stateValue) {
     this.stateValue = stateValue;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.NodeStateModel
 * JD-Core Version:    0.6.0
 */