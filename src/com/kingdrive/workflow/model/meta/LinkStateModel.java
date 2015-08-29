 package com.kingdrive.workflow.model.meta;
 
 import java.io.Serializable;
 
 public class LinkStateModel
   implements Serializable
 {
   private Long nodeLinkStateId;
   private Long nodeLinkId;
   private Long stateId;
   private String stateValue = null;
 
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public Long getNodeLinkId() {
     return this.nodeLinkId;
   }
 
   public void setNodeLinkId(Long nodeLinkId) {
     this.nodeLinkId = nodeLinkId;
   }
 
   public Long getNodeLinkStateId() {
     return this.nodeLinkStateId;
   }
 
   public void setNodeLinkStateId(Long nodeLinkStateId) {
     this.nodeLinkStateId = nodeLinkStateId;
   }
 
   public Long getStateId() {
     return this.stateId;
   }
 
   public void setStateId(Long stateId) {
     this.stateId = stateId;
   }
 
   public String getStateValue() {
     return this.stateValue;
   }
 
   public void setStateValue(String stateValue) {
     this.stateValue = stateValue;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.meta.LinkStateModel
 * JD-Core Version:    0.6.0
 */