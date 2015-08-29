 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class OrgModel
   implements Serializable
 {
   private String organizationId = null;
 
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
 
   public String getOrganizationId() {
     return this.organizationId;
   }
 
   public void setOrganizationId(String organizationId) {
     this.organizationId = organizationId;
   }
 
   public String getParentId() {
     return this.parentId;
   }
 
   public void setParentId(String parentId) {
     this.parentId = parentId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.OrgModel
 * JD-Core Version:    0.6.0
 */