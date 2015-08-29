 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 
 public class PairInfo
   implements Serializable
 {
   private String id = null;
 
   private String content = null;
 
   private String reference = null;
 
   private String action = "";
 
   private String defaultPath = "";
   public static final String TYPE_DEFAULT = "1";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getContent() {
     return this.content;
   }
 
   public void setContent(String content) {
     this.content = content;
   }
 
   public String getDefaultPath() {
     return this.defaultPath;
   }
 
   public void setDefaultPath(String defaultPath) {
     this.defaultPath = defaultPath;
   }
 
   public String getId() {
     return this.id;
   }
 
   public void setId(String id) {
     this.id = id;
   }
 
   public String getReference() {
     return this.reference;
   }
 
   public void setReference(String reference) {
     this.reference = reference;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.PairInfo
 * JD-Core Version:    0.6.0
 */