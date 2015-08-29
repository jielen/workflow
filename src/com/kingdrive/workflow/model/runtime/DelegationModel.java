 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 
 public class DelegationModel
   implements Serializable
 {
   private int delegationId;
   private String description = null;
   private int templateId;
   private int nodeId;
   private String sender = null;
 
   private String owner = null;
 
   private String receiver = null;
   private int parentId;
   private String delegationTime = null;
 
   private String startTime = null;
 
   private String endTime = null;
 
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public int getDelegationId() {
     return this.delegationId;
   }
 
   public void setDelegationId(int delegationId) {
     this.delegationId = delegationId;
   }
 
   public String getDelegationTime() {
     return this.delegationTime;
   }
 
   public void setDelegationTime(String delegationTime) {
     this.delegationTime = delegationTime;
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
 
   public int getNodeId() {
     return this.nodeId;
   }
 
   public void setNodeId(int nodeId) {
     this.nodeId = nodeId;
   }
 
   public String getOwner() {
     return this.owner;
   }
 
   public void setOwner(String owner) {
     this.owner = owner;
   }
 
   public int getParentId() {
     return this.parentId;
   }
 
   public void setParentId(int parentId) {
     this.parentId = parentId;
   }
 
   public String getReceiver() {
     return this.receiver;
   }
 
   public void setReceiver(String receiver) {
     this.receiver = receiver;
   }
 
   public String getSender() {
     return this.sender;
   }
 
   public void setSender(String sender) {
     this.sender = sender;
   }
 
   public String getStartTime() {
     return this.startTime;
   }
 
   public void setStartTime(String startTime) {
     this.startTime = startTime;
   }
 
   public int getTemplateId() {
     return this.templateId;
   }
 
   public void setTemplateId(int templateId) {
     this.templateId = templateId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.DelegationModel
 * JD-Core Version:    0.6.0
 */