 package com.kingdrive.workflow.context;
 
 import com.kingdrive.workflow.listener.TaskListener;
 import com.kingdrive.workflow.model.TableData;
 import com.kingdrive.workflow.model.TaskUser;
 import com.kingdrive.workflow.model.meta.NodeModel;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 
 public class WorkflowContext
 {
   private Long instanceId;
   private Long templateId;
   private String executor;
   private String tableName;
   private TableData entityData;
   private NodeModel currentNode = new NodeModel();
   private NodeModel nextNode = new NodeModel();
   private String comment = "";
   private String exeTime;
   private List defaultListener = new ArrayList();
 
   private Map variableMap = new HashMap();
   private List nextLink = new ArrayList();
   private List nextExecutor = new ArrayList();
   private List responsibility = new ArrayList();
 
   private WorkflowContext granterContext = null;
 
   public Long getInstanceId()
   {
     return this.instanceId;
   }
 
   public void setInstanceId(Long instanceId)
   {
     this.instanceId = instanceId;
   }
 
   public Long getTemplateId()
   {
     return this.templateId;
   }
 
   public void setTemplateId(Long templateId)
   {
     this.templateId = templateId;
   }
 
   public String getExecutor()
   {
     return this.executor;
   }
 
   public void setExecutor(String executor)
   {
     this.executor = executor;
   }
 
   public NodeModel getCurrentNode()
   {
     return this.currentNode;
   }
 
   public void setCurrentNode(NodeModel currentNode)
   {
     this.currentNode = currentNode;
   }
 
   public Long getCurrentNodeId()
   {
     return this.currentNode.getNodeId();
   }
 
   public void setCurrentNodeId(Long nodeId)
   {
     this.currentNode.setNodeId(nodeId);
   }
 
   public List getNextExecutor()
   {
     List result = new ArrayList();
     for (int i = 0; i < this.nextExecutor.size(); i++) {
       result.add(new TaskUser((String)this.nextExecutor.get(i)));
     }
     return result;
   }
 
   public void setNextExecutor(List nextExecutor)
   {
     this.nextExecutor = nextExecutor;
   }
 
   public List getResponsibility() {
     return this.responsibility;
   }
 
   public void setResponsibility(List responsibility) {
     this.responsibility = responsibility;
   }
 
   public TableData getEntityData()
   {
     return this.entityData;
   }
 
   public void setEntityData(TableData entityData)
   {
     this.entityData = entityData;
   }
 
   public List getNextLink()
   {
     return this.nextLink;
   }
 
   public void setNextLink(List nextLink)
   {
     this.nextLink = nextLink;
   }
 
   public void setVariable(String name, Object value)
   {
     this.variableMap.put(name, value);
   }
 
   public void setVariables(Map values) {
     this.variableMap.putAll(values);
   }
 
   public Object getVariable(String name) {
     return this.variableMap.get(name);
   }
 
   public Map getAllVariables() {
     return this.variableMap;
   }
 
   public String getComment() {
     return this.comment;
   }
 
   public void setComment(String comment) {
     this.comment = comment;
   }
 
   public String getExeTime() {
     return this.exeTime;
   }
 
   public void setExeTime(String exeTime) {
     this.exeTime = exeTime;
   }
 
   public NodeModel getNextNode() {
     return this.nextNode;
   }
 
   public void setNextNode(NodeModel nextNode) {
     this.nextNode = nextNode;
   }
 
   public Long getNextNodeId() {
     return this.nextNode.getNodeId();
   }
 
   public void setNextNodeId(Long id) {
     this.nextNode.setNodeId(id);
   }
 
   public String getTableName() {
     return this.tableName;
   }
 
   public void setTableName(String tableName) {
     this.tableName = tableName;
   }
 
   public List getDefaultListener() {
     return this.defaultListener;
   }
 
   public void addDefaultListener(TaskListener listener) {
     this.defaultListener.add(listener);
   }
 
   public WorkflowContext getGranterContext()
   {
     return this.granterContext;
   }
 
   public void setGranterContext(WorkflowContext granterContext)
   {
     this.granterContext = granterContext;
   }
 
   public boolean hasGranterContext() {
     return this.granterContext != null;
   }
 
   public Map getVariableMap()
   {
     return this.variableMap;
   }
 
   public void setVariableMap(Map variableMap)
   {
     this.variableMap = variableMap;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.context.WorkflowContext
 * JD-Core Version:    0.6.0
 */