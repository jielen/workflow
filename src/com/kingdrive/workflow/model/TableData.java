 package com.kingdrive.workflow.model;
 
 import java.util.HashMap;
 import java.util.Map;
 
 public class TableData
 {
   private String name;
   private Long instanceId;
   private String titleFieldValue = "";
   private Map fields = new HashMap();
 
   public TableData() {
   }
 
   public TableData(String n) {
     this.name = n;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getTitleFieldValue() {
     return this.titleFieldValue;
   }
 
   public void setTitleFieldValue(String titleFieldValue) {
     this.titleFieldValue = titleFieldValue;
   }
 
   public Long getInstanceId() {
     return this.instanceId;
   }
 
   public void setInstanceId(Long instanceId) {
     this.instanceId = instanceId;
   }
 
   public void setFieldValue(String fName, Object value) {
     this.fields.put(fName, value);
   }
 
   public Object getFieldValue(String fName) {
     return this.fields.get(fName);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.TableData
 * JD-Core Version:    0.6.0
 */