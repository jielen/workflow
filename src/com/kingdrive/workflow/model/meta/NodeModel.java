 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 
 public class NodeModel
   implements Serializable
 {
   private Long nodeId;
   private String name = null;
 
   private String description = null;
 
   private String type = null;
 
   private String businessType = null;
   private Long templateId;
   private String executorsMethod = null;
   private String executorRelation;
   private String taskListener = null;
   private Long limitExecuteTerm;
   private Long remindExecuteTerm;
   private List nodeStateList;
   private List fromLinkList = new ArrayList();
 
   private List toLinkList = new ArrayList();
 
   private String action = "";
 
   private LinkModel runtimeFromLink = null;
 
   private TemplateModel template = null;
 
   private boolean finished = false;
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getBusinessType() {
     return this.businessType;
   }
 
   public void setBusinessType(String businessType) {
     this.businessType = businessType;
   }
 
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
 
   public String getExecutorsMethod() {
     return this.executorsMethod;
   }
 
   public void setExecutorsMethod(String executorsMethod) {
     this.executorsMethod = executorsMethod;
   }
 
   public String getExecutorRelation() {
     return this.executorRelation;
   }
 
   public void setExecutorRelation(String executorRelation) {
     this.executorRelation = executorRelation;
   }
 
   public Long getLimitExecuteTerm() {
     return this.limitExecuteTerm;
   }
 
   public void setLimitExecuteTerm(Long limitExecuteTerm) {
     this.limitExecuteTerm = limitExecuteTerm;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public Long getNodeId() {
     return this.nodeId;
   }
 
   public void setNodeId(Long nodeId) {
     this.nodeId = nodeId;
   }
 
   public Long getRemindExecuteTerm() {
     return this.remindExecuteTerm;
   }
 
   public void setRemindExecuteTerm(Long remindExecuteTerm) {
     this.remindExecuteTerm = remindExecuteTerm;
   }
 
   public String getTaskListener() {
     return this.taskListener;
   }
 
   public void setTaskListener(String taskListener) {
     this.taskListener = taskListener;
   }
 
   public Long getTemplateId() {
     return this.templateId;
   }
 
   public void setTemplateId(Long templateId) {
     this.templateId = templateId;
   }
 
   public String getType() {
     return this.type;
   }
 
   public void setType(String type) {
     this.type = type;
   }
 
   public LinkModel getRuntimeFromLink() {
     return this.runtimeFromLink;
   }
 
   public void setRuntimeFromLink(LinkModel runtimeFromLink) {
     this.runtimeFromLink = runtimeFromLink;
   }
 
   public TemplateModel getTemplate()
   {
     return this.template;
   }
 
   public void setTemplate(TemplateModel template) {
     this.template = template;
   }
 
   public boolean isFinished()
   {
     return this.finished;
   }
 
   public void setFinished(boolean finished) {
     this.finished = finished;
   }
 
   public List getFromLinkList() {
     return this.fromLinkList;
   }
 
   public void setFromLinkList(List fromLinkList) {
     this.fromLinkList = fromLinkList;
   }
 
   public List getNodeStateList() {
     return this.nodeStateList;
   }
 
   public void setNodeStateList(List nodeState) {
     this.nodeStateList = nodeState;
   }
 
   public List getToLinkList() {
     return this.toLinkList;
   }
 
   public void setToLinkList(List toLinkList) {
     this.toLinkList = toLinkList;
   }
 
   public void addFormLink(LinkModel link) {
     if (link != null) {
       this.fromLinkList.add(link);
       link.setCurrentNode(this);
     }
   }
 
   public void addToLink(LinkModel link) {
     if (link != null) {
       this.toLinkList.add(link);
       link.setNextNode(this);
     }
   }
 
   public boolean isTaskNode()
   {
     return "2".equals(this.type);
   }
 
   public boolean isSplitNode()
   {
     return "3".equals(this.type);
   }
 
   public boolean isMergeNode()
   {
     return false;
   }
 
   public boolean isLastNode() {
     NodeModel node = getTemplate().getLastNode();
 
     return node.equals(this);
   }
 
   public boolean equals(Object node)
   {
     if (node == null) {
       return false;
     }
     if (!(node instanceof NodeModel)) {
       return false;
     }
     NodeModel model = (NodeModel)node;
     return getNodeId().longValue() == model.getNodeId()
       .longValue();
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.NodeModel
 * JD-Core Version:    0.6.0
 */