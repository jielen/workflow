 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class StaffModel
   implements Serializable
 {
   private String staffId = null;
 
   private String name = null;
 
   private String pwd = null;
 
   private String description = null;
 
   private String email = null;
 
   private String status = null;
 
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
 
   public String getEmail() {
     return this.email;
   }
 
   public void setEmail(String email) {
     this.email = email;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getPwd() {
     return this.pwd;
   }
 
   public void setPwd(String pwd) {
     this.pwd = pwd;
   }
 
   public String getStaffId() {
     return this.staffId;
   }
 
   public void setStaffId(String staffId) {
     this.staffId = staffId;
   }
 
   public String getStatus() {
     return this.status;
   }
 
   public void setStatus(String status) {
     this.status = status;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.StaffModel
 * JD-Core Version:    0.6.0
 */