 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class OrgPositionLevelModel
   implements Serializable
 {
   private String orgPositionId = null;
 
   private String parentId = null;
 
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getOrgPositionId() {
     return this.orgPositionId;
   }
 
   public void setOrgPositionId(String orgPositionId) {
     this.orgPositionId = orgPositionId;
   }
 
   public String getParentId() {
     return this.parentId;
   }
 
   public void setParentId(String parentId) {
     this.parentId = parentId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.OrgPositionLevelModel
 * JD-Core Version:    0.6.0
 */