 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class BindStateModel
   implements Serializable
 {
   private static final long serialVersionUID = -2422343444490193695L;
   private Long templateId;
   private Long stateId;
   private String tableId;
   private String fieldId;
 
   public String getFieldId()
   {
     return this.fieldId;
   }
 
   public void setFieldId(String fieldId) {
     this.fieldId = fieldId;
   }
 
   public Long getStateId() {
     return this.stateId;
   }
 
   public void setStateId(Long stateId) {
     this.stateId = stateId;
   }
 
   public String getTableId() {
     return this.tableId;
   }
 
   public void setTableId(String tableId) {
     this.tableId = tableId;
   }
 
   public Long getTemplateId() {
     return this.templateId;
   }
 
   public void setTemplateId(Long templateId) {
     this.templateId = templateId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.BindStateModel
 * JD-Core Version:    0.6.0
 */