 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class CompanyModel
   implements Serializable
 {
   private String companyId = null;
 
   private String name = null;
 
   private String description = null;
 
   private String parentId = null;
 
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getCompanyId() {
     return this.companyId;
   }
 
   public void setCompanyId(String companyId) {
     this.companyId = companyId;
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
 
   public String getParentId() {
     return this.parentId;
   }
 
   public void setParentId(String parentId) {
     this.parentId = parentId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.CompanyModel
 * JD-Core Version:    0.6.0
 */