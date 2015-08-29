 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 
 public class PassModel
   implements Serializable
 {
   private int passCountId;
   private int instanceId;
   private int nodeLinkId;
   private int currentNodeId;
   private int nextNodeId;
   private String action = "";
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public int getCurrentNodeId() {
     return this.currentNodeId;
   }
 
   public void setCurrentNodeId(int currentNodeId) {
     this.currentNodeId = currentNodeId;
   }
 
   public int getInstanceId() {
     return this.instanceId;
   }
 
   public void setInstanceId(int instanceId) {
     this.instanceId = instanceId;
   }
 
   public int getNextNodeId() {
     return this.nextNodeId;
   }
 
   public void setNextNodeId(int nextNodeId) {
     this.nextNodeId = nextNodeId;
   }
 
   public int getNodeLinkId() {
     return this.nodeLinkId;
   }
 
   public void setNodeLinkId(int nodeLinkId) {
     this.nodeLinkId = nodeLinkId;
   }
 
   public int getPassCountId() {
     return this.passCountId;
   }
 
   public void setPassCountId(int passCountId) {
     this.passCountId = passCountId;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.PassModel
 * JD-Core Version:    0.6.0
 */