 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 
 public class InstanceModel
   implements Serializable
 {
   private static final long serialVersionUID = -9182382066488148948L;
   private Long instanceId;
   private String name = "";
 
   private String description = "";
   private Long templateId;
   private String owner = "";
 
   private String startTime = "";
 
   private String endTime = "";
 
   private Long status = null;
 
   private String action = "";
 
   private Long parentInstanceId = new Long(-1L);
 
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
 
   public String getEndTime() {
     return this.endTime;
   }
 
   public void setEndTime(String endTime) {
     this.endTime = endTime;
   }
 
   public Long getInstanceId() {
     return this.instanceId;
   }
 
   public void setInstanceId(Long instanceId) {
     this.instanceId = instanceId;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getOwner() {
     return this.owner;
   }
 
   public void setOwner(String owner) {
     this.owner = owner;
   }
 
   public Long getParentInstanceId() {
     return this.parentInstanceId;
   }
 
   public void setParentInstanceId(Long parentInstanceId) {
     this.parentInstanceId = parentInstanceId;
   }
 
   public String getStartTime() {
     return this.startTime;
   }
 
   public void setStartTime(String startTime) {
     this.startTime = startTime;
   }
 
   public Long getStatus() {
     return this.status;
   }
 
   public void setStatus(Long status) {
     this.status = status;
   }
 
   public Long getTemplateId() {
     return this.templateId;
   }
 
   public void setTemplateId(Long templateId) {
     this.templateId = templateId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.InstanceModel
 * JD-Core Version:    0.6.0
 */