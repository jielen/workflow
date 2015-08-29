 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class OrgPositionModel
   implements Serializable
 {
   private String orgPositionId = null;
 
   private String organizationId = null;
 
   private String positionId = null;
 
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getOrganizationId() {
     return this.organizationId;
   }
 
   public void setOrganizationId(String organizationId) {
     this.organizationId = organizationId;
   }
 
   public String getOrgPositionId() {
     return this.orgPositionId;
   }
 
   public void setOrgPositionId(String orgPositionId) {
     this.orgPositionId = orgPositionId;
   }
 
   public String getPositionId() {
     return this.positionId;
   }
 
   public void setPositionId(String positionId) {
     this.positionId = positionId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.OrgPositionModel
 * JD-Core Version:    0.6.0
 */