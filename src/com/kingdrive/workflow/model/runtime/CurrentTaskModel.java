 package com.kingdrive.workflow.model.runtime;
 
 import com.kingdrive.workflow.model.meta.NodeModel;
 import java.io.Serializable;
 
 public class CurrentTaskModel
   implements Serializable
 {
   private static final long serialVersionUID = -2027339582973511263L;
   private Long currentTaskId;
   private InstanceModel instance = new InstanceModel();
   private Long nodeId;
   private String nodeName;
   private NodeModel node;
   private NodeModel prevNode = new NodeModel();
 
   private Long responsibility = new Long(1L);
 
   private Long parentTaskId = new Long(-1L);
 
   private String executor = "";
 
   private String executorName = "";
 
   private Long delegationId = new Long(1L);
 
   private String owner = "";
 
   private String creator = "";
 
   private String createTime = "";
 
   private String limitExecuteTime = "";
 
   private String taskStatus = "1";
 
   private String action = "";
 
   private String granterInfo = "";
 
   private String granterId = "";
   private String gkSendStatus;
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getCreateTime() {
     return this.createTime;
   }
 
   public void setCreateTime(String createTime) {
     this.createTime = createTime;
   }
 
   public String getCreator() {
     return this.creator;
   }
 
   public void setCreator(String creator) {
     this.creator = creator;
   }
 
   public Long getCurrentTaskId() {
     return this.currentTaskId;
   }
 
   public void setCurrentTaskId(Long currentTaskId) {
     this.currentTaskId = currentTaskId;
   }
 
   public Long getDelegationId() {
     return this.delegationId;
   }
 
   public void setDelegationId(Long delegationId) {
     this.delegationId = delegationId;
   }
 
   public String getExecutor() {
     return this.executor;
   }
 
   public void setExecutor(String executor) {
     this.executor = executor;
   }
 
   public InstanceModel getInstance() {
     return this.instance;
   }
 
   public void setInstance(InstanceModel instance) {
     this.instance = instance;
     setInstanceId(this.instance.getInstanceId());
   }
 
   public Long getInstanceId() {
     return this.instance.getInstanceId();
   }
 
   public void setInstanceId(Long instanceId) {
     this.instance.setInstanceId(instanceId);
   }
 
   public String getLimitExecuteTime() {
     return this.limitExecuteTime;
   }
 
   public void setLimitExecuteTime(String limitExecuteTime) {
     this.limitExecuteTime = (limitExecuteTime == null ? "" : limitExecuteTime);
   }
 
   public Long getNodeId() {
     return this.nodeId;
   }
 
   public void setNodeId(Long nodeId) {
     this.nodeId = nodeId;
   }
 
   public NodeModel getNode() {
     return this.node;
   }
 
   public void setNode(NodeModel node) {
     this.node = node;
   }
 
   public NodeModel getPrevNode() {
     return this.prevNode;
   }
 
   public void setPrevNode(NodeModel prevNode) {
     this.prevNode = prevNode;
   }
 
   public Long getPrevNodeId() {
     return this.prevNode.getNodeId();
   }
 
   public void setPrevNodeId(Long nodeId) {
     getPrevNode().setNodeId(nodeId);
   }
 
   public String getOwner() {
     return this.owner;
   }
 
   public void setOwner(String owner) {
     this.owner = owner;
   }
 
   public Long getParentTaskId() {
     return this.parentTaskId;
   }
 
   public void setParentTaskId(Long parentTaskId) {
     this.parentTaskId = parentTaskId;
   }
 
   public Long getResponsibility() {
     return this.responsibility;
   }
 
   public void setResponsibility(Long responsibility) {
     this.responsibility = responsibility;
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
 
   public String getTaskStatus()
   {
     return this.taskStatus;
   }
 
   public void setTaskStatus(String taskStatus)
   {
     this.taskStatus = taskStatus;
   }
 
   public String getGranterInfo()
   {
     return this.granterInfo;
   }
 
   public void setGranterInfo(String granterInfo)
   {
     granterInfo = granterInfo == null ? "" : granterInfo;
     this.granterInfo = granterInfo;
   }
 
   public String getGranterId()
   {
     this.granterId = (this.granterId == null ? "" : this.granterId);
     return this.granterId;
   }
 
   public void setGranterId(String granterId)
   {
     this.granterId = granterId;
   }
 
   public boolean isGrantedTask() {
     return !"".equals(getGranterId());
   }
 
   public String getGkSendStatus() {
     return this.gkSendStatus;
   }
 
   public void setGkSendStatus(String gkSendStatus) {
     this.gkSendStatus = gkSendStatus;
   }
 
   public String toString() {
     String result = "<html>";
     result = result + "<center>" + getNodeName() + "</center> <br>";
     result = result + "待办人:<font color=red size=3>" + getExecutorName() + "</font><br>";
     result = result + "</html>";
     return result;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.CurrentTaskModel
 * JD-Core Version:    0.6.0
 */