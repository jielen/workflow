 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class PositionModel
   implements Serializable
 {
   private String positionId = null;
 
   private String name = null;
 
   private String description = null;
 
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
 
   public String getPositionId() {
     return this.positionId;
   }
 
   public void setPositionId(String positionId) {
     this.positionId = positionId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.PositionModel
 * JD-Core Version:    0.6.0
 */