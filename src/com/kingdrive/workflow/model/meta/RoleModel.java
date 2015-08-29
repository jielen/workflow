 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class RoleModel
   implements Serializable
 {
   private String roleId = null;
 
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
 
   public String getRoleId() {
     return this.roleId;
   }
 
   public void setRoleId(String roleId) {
     this.roleId = roleId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.RoleModel
 * JD-Core Version:    0.6.0
 */