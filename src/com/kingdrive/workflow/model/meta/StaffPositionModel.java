 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class StaffPositionModel
   implements Serializable
 {
   private String orgPositionId = null;
 
   private String staffId = null;
 
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
 
   public String getStaffId() {
     return this.staffId;
   }
 
   public void setStaffId(String staffId) {
     this.staffId = staffId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.StaffPositionModel
 * JD-Core Version:    0.6.0
 */