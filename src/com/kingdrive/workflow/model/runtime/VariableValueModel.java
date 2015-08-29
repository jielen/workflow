 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 
 public class VariableValueModel
   implements Serializable
 {
   private int valueId;
   private int instanceId;
   private int variableId;
   private String value = null;
 
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
 
   public String getValue() {
     return this.value;
   }
 
   public void setValue(String value) {
     this.value = value;
   }
 
   public int getValueId() {
     return this.valueId;
   }
 
   public void setValueId(int valueId) {
     this.valueId = valueId;
   }
 
   public int getVariableId() {
     return this.variableId;
   }
 
   public void setVariableId(int variableId) {
     this.variableId = variableId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.VariableValueModel
 * JD-Core Version:    0.6.0
 */