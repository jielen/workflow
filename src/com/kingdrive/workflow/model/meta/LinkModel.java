 package com.kingdrive.workflow.model.meta;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.expression.ExpressionEvaluator;
 import java.io.Serializable;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 
 public class LinkModel
   implements Serializable
 {
   private Long nodeLinkId;
   private String name = "";
 
   private String description = "";
 
   private String linkType = "";
   private Long templateId;
   private Long currentNodeId;
   private NodeModel currentNode = null;
   private Long nextNodeId;
   private NodeModel nextNode = null;
 
   private String executorRelation = "";
 
   private String executorsMethod = "";
 
   private String numberOrPercent = "";
 
   private Double passValue = null;
 
   private String expression = "";
 
   private String defaultPath = "";
 
   private String actionName = "";
 
   private List linkStateList = null;
 
   private String action = "";
   private TemplateModel template;
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getActionName() {
     return this.actionName;
   }
 
   public void setActionName(String actionName) {
     this.actionName = actionName;
   }
 
   public Long getCurrentNodeId() {
     return this.currentNodeId;
   }
 
   public void setCurrentNodeId(Long currentNodeId) {
     this.currentNodeId = currentNodeId;
   }
 
   public String getDefaultPath() {
     return this.defaultPath;
   }
 
   public void setDefaultPath(String defaultPath) {
     this.defaultPath = defaultPath;
   }
 
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
 
   public String getExecutorRelation() {
     return this.executorRelation;
   }
 
   public void setExecutorRelation(String executorRelation) {
     this.executorRelation = executorRelation;
   }
 
   public String getExecutorsMethod() {
     return this.executorsMethod;
   }
 
   public void setExecutorsMethod(String executorsMethod) {
     this.executorsMethod = executorsMethod;
   }
 
   public String getExpression() {
     return this.expression;
   }
 
   public void setExpression(String expression) {
     this.expression = expression;
   }
 
   public String getLinkType() {
     return this.linkType;
   }
 
   public void setLinkType(String linkType) {
     this.linkType = linkType;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public Long getNextNodeId() {
     return this.nextNodeId;
   }
 
   public void setNextNodeId(Long nextNodeId) {
     this.nextNodeId = nextNodeId;
   }
 
   public Long getNodeLinkId() {
     return this.nodeLinkId;
   }
 
   public void setNodeLinkId(Long nodeLinkId) {
     this.nodeLinkId = nodeLinkId;
   }
 
   public String getNumberOrPercent() {
     return this.numberOrPercent;
   }
 
   public void setNumberOrPercent(String numberOrPercent) {
     this.numberOrPercent = numberOrPercent;
   }
 
   public Double getPassValue() {
     return this.passValue;
   }
 
   public void setPassValue(Double passValue) {
     this.passValue = passValue;
   }
 
   public Long getTemplateId() {
     return this.templateId;
   }
 
   public void setTemplateId(Long templateId) {
     this.templateId = templateId;
   }
 
   public void setCurrentNode(NodeModel node) {
     this.currentNode = node;
   }
 
   public NodeModel getCurrentNode() {
     return this.currentNode;
   }
 
   public void setNextNode(NodeModel node) {
     this.nextNode = node;
   }
 
   public NodeModel getNextNode() {
     return this.nextNode;
   }
 
   public List getLinkStateList() {
     return this.linkStateList;
   }
 
   public void setLinkStateList(List linkState) {
     this.linkStateList = linkState;
   }
 
   public TemplateModel getTemplate()
   {
     return this.template;
   }
 
   public void setTemplate(TemplateModel template) {
     this.template = template;
   }
 
   public String getPassPolicy()
   {
     String policy = "";
     Double passvalue = getPassValue();
     passvalue = passvalue == null ? new Double(0.0D) : passvalue;
     if (passvalue.doubleValue() > 0.0D) {
       return "1";
     }
     return "0";
   }
 
   public boolean isMatchExpr(WorkflowContext context) throws Exception
   {
     Map varialbes = context.getAllVariables();
     if (StringUtils.isEmpty(this.expression)) return true;
     Object result = ExpressionEvaluator.calculate(this.expression, varialbes);
     if ((result == null) || (!(result instanceof Boolean))) {
       throw new Exception("计算link(" + getNodeLinkId() + ")表达式 " + this.expression + " : " + "不能返回一个boolean型结果");
     }
     Boolean b = (Boolean)result;
     return b.booleanValue();
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.LinkModel
 * JD-Core Version:    0.6.0
 */