 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class CompoModel
   implements Serializable
 {
   private static final long serialVersionUID = -2814230297816822763L;
   private String compoId;
   private String compoName;
   private Long tempolateId;
   private String masterTabId;
 
   public String getCompoId()
   {
     return this.compoId;
   }
 
   public void setCompoId(String compoId) {
     this.compoId = compoId;
   }
 
   public String getCompoName() {
     return this.compoName;
   }
 
   public void setCompoName(String compoName) {
     this.compoName = compoName;
   }
 
   public String getMasterTabId() {
     return this.masterTabId;
   }
 
   public void setMasterTabId(String masterTabId) {
     this.masterTabId = masterTabId;
   }
 
   public Long getTempolateId() {
     return this.tempolateId;
   }
 
   public void setTempolateId(Long tempolateId) {
     this.tempolateId = tempolateId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.CompoModel
 * JD-Core Version:    0.6.0
 */