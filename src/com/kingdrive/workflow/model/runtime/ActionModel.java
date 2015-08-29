 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 
 public class ActionModel
   implements Serializable
 {
   private static final long serialVersionUID = 6295961453367162007L;
   private Long actionId;
   private Long instanceId;
   private Long nodeId;
   private Long nextNodeId = Long.valueOf("-1");
   private String nodeName;
   private String actionName = "";
 
   private String executor = "";
 
   private String executorName = "";
 
   private String executeTime = "";
 
   private String description = "";
 
   private String owner = "";
 
   private String limitExecuteTime = "";
 
   private String workitemStatus = "-1";
 
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public Long getActionId() {
     return this.actionId;
   }
 
   public void setActionId(Long actionId) {
     this.actionId = actionId;
   }
 
   public String getActionName() {
     return this.actionName;
   }
 
   public void setActionName(String actionName) {
     this.actionName = actionName;
   }
 
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
 
   public String getExecuteTime() {
     return this.executeTime;
   }
 
   public void setExecuteTime(String executeTime) {
     this.executeTime = executeTime;
   }
 
   public String getExecutor() {
     return this.executor;
   }
 
   public void setExecutor(String executor) {
     this.executor = executor;
   }
 
   public Long getInstanceId() {
     return this.instanceId;
   }
 
   public void setInstanceId(Long instanceId) {
     this.instanceId = instanceId;
   }
 
   public String getLimitExecuteTime() {
     return this.limitExecuteTime;
   }
 
   public void setLimitExecuteTime(String limitExecuteTime) {
     this.limitExecuteTime = limitExecuteTime;
   }
 
   public Long getNodeId() {
     return this.nodeId;
   }
 
   public void setNodeId(Long nodeId) {
     this.nodeId = nodeId;
   }
 
   public Long getNextNodeId()
   {
     return this.nextNodeId;
   }
 
   public void setNextNodeId(Long nextNodeId)
   {
     this.nextNodeId = nextNodeId;
   }
 
   public String getOwner() {
     return this.owner;
   }
 
   public void setOwner(String owner) {
     this.owner = owner;
   }
 
   public String getExecutorName()
   {
     return this.executorName;
   }
 
   public void setExecutorName(String executorName)
   {
     this.executorName = executorName;
   }
 
   public String getNodeName()
   {
     return this.nodeName;
   }
 
   public void setNodeName(String nodeName)
   {
     this.nodeName = nodeName;
   }
 
   public String getWorkitemStatus()
   {
     return this.workitemStatus;
   }
 
   public void setWorkitemStatus(String workitemStatus)
   {
     this.workitemStatus = workitemStatus;
   }
 
   public String toString() {
     String result = "<html>";
     result = result + "<center>" + getNodeName() + "</center> <br>";
     result = result + "审核人:<font color=red size=3>" + getExecutorName() + "</font><br>";
     result = result + "审核时间:<font color=red size=3>" + getExecuteTime() + "</font>";
     result = result + "</html>";
     return result;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.ActionModel
 * JD-Core Version:    0.6.0
 */