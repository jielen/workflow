 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class StateModel
   implements Serializable
 {
   private int stateId;
   private String name = null;
 
   private String description = null;
   private int templateId;
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public int getStateId() {
     return this.stateId;
   }
 
   public void setStateId(int stateId) {
     this.stateId = stateId;
   }
 
   public int getTemplateId() {
     return this.templateId;
   }
 
   public void setTemplateId(int templateId) {
     this.templateId = templateId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.StateModel
 * JD-Core Version:    0.6.0
 */