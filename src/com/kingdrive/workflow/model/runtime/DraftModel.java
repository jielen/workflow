 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 
 public class DraftModel
   implements Serializable
 {
   private static final long serialVersionUID = 221386161948875756L;
   private Long draftId;
   private String draftName = "";
 
   private String compoId = "";
 
   private String masteTableId = "";
 
   private String userId = "";
 
   private String userName = "";
 
   private String saveTime = "";
 
   private String status = "";
 
   public String getCompoId() {
     return this.compoId;
   }
 
   public void setCompoId(String compoId) {
     this.compoId = compoId;
   }
 
   public Long getDraftId() {
     return this.draftId;
   }
 
   public void setDraftId(Long draftId) {
     this.draftId = draftId;
   }
 
   public String getDraftName() {
     return this.draftName;
   }
 
   public void setDraftName(String draftName) {
     this.draftName = draftName;
   }
 
   public String getMasteTableId() {
     return this.masteTableId;
   }
 
   public void setMasteTableId(String masteTableId) {
     this.masteTableId = masteTableId;
   }
 
   public String getSaveTime() {
     return this.saveTime;
   }
 
   public void setSaveTime(String saveTime) {
     this.saveTime = saveTime;
   }
 
   public String getStatus() {
     return this.status;
   }
 
   public void setStatus(String status) {
     this.status = status;
   }
 
   public String getUserId() {
     return this.userId;
   }
 
   public void setUserId(String userId) {
     this.userId = userId;
   }
 
   public String getUserName() {
     return this.userName;
   }
 
   public void setUserName(String userName) {
     this.userName = userName;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.DraftModel
 * JD-Core Version:    0.6.0
 */