 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 
 public class StateValueModel
   implements Serializable
 {
   private static final long serialVersionUID = -9025817911860265961L;
   private Long stateValueId;
   private Long instanceId;
   private Long stateId;
   private String value = "";
 
   private String action = "";
 
   public String getAction() {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public Long getInstanceId() {
     return this.instanceId;
   }
 
   public void setInstanceId(Long instanceId) {
     this.instanceId = instanceId;
   }
 
   public Long getStateId() {
     return this.stateId;
   }
 
   public void setStateId(Long stateId) {
     this.stateId = stateId;
   }
 
   public Long getStateValueId() {
     return this.stateValueId;
   }
 
   public void setStateValueId(Long stateValueId) {
     this.stateValueId = stateValueId;
   }
 
   public String getValue() {
     return this.value;
   }
 
   public void setValue(String value) {
     this.value = value;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.StateValueModel
 * JD-Core Version:    0.6.0
 */