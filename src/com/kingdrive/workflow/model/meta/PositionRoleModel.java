 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class PositionRoleModel
   implements Serializable
 {
   private String positionId = null;
 
   private String roleId = null;
 
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getPositionId() {
     return this.positionId;
   }
 
   public void setPositionId(String positionId) {
     this.positionId = positionId;
   }
 
   public String getRoleId() {
     return this.roleId;
   }
 
   public void setRoleId(String roleId) {
     this.roleId = roleId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.PositionRoleModel
 * JD-Core Version:    0.6.0
 */