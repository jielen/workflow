 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class BindVariableModel
   implements Serializable
 {
   private static final long serialVersionUID = -8389220349443404888L;
   public static String STRING = "1";
 
   public static String NUMBER = "0";
 
   private Integer variableId = null;
 
   private String name = null;
 
   private String description = null;
 
   private String type = STRING;
 
   private Integer templateId = null;
 
   private String bindExpression = "";
 
   private String tableId = "";
 
   private String condition = "";
 
   private String filterByKey = "N";
 
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getBindExpression() {
     return this.bindExpression;
   }
 
   public void setBindExpression(String bindExpression) {
     this.bindExpression = bindExpression;
   }
 
   public String getCondition() {
     return this.condition;
   }
 
   public void setCondition(String condition) {
     this.condition = condition;
   }
 
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
 
   public String getFilterByKey() {
     return this.filterByKey;
   }
 
   public void setFilterByKey(String filterByKey) {
     this.filterByKey = filterByKey;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getTableId() {
     return this.tableId;
   }
 
   public void setTableId(String tableId) {
     this.tableId = tableId;
   }
 
   public Integer getTemplateId() {
     return this.templateId;
   }
 
   public void setTemplateId(Integer templateId) {
     this.templateId = templateId;
   }
 
   public String getType() {
     return this.type;
   }
 
   public void setType(String type) {
     this.type = type;
   }
 
   public Integer getVariableId() {
     return this.variableId;
   }
 
   public void setVariableId(Integer variableId) {
     this.variableId = variableId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.BindVariableModel
 * JD-Core Version:    0.6.0
 */