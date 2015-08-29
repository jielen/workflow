 package com.kingdrive.workflow.model;
 
 import java.io.Serializable;
 
 public class TaskUser
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String userId;
   private boolean isGranted = false;
   private String grantUserId = "";
   private String grantUserCoCode = "";
   private String grantUserOrgCode = "";
   private String grantUserPosiCode = "";
   private String grantUserPosiId = "";
   private int grantLevel = 0;
 
   public String getGrantUserCoCode()
   {
     return this.grantUserCoCode;
   }
 
   public void setGrantUserCoCode(String grantUserCoCode)
   {
     this.grantUserCoCode = grantUserCoCode;
   }
 
   public String getGrantUserId()
   {
     return this.grantUserId;
   }
 
   public void setGrantUserId(String grantUserId)
   {
     this.grantUserId = grantUserId;
   }
 
   public String getGrantUserOrgCode()
   {
     return this.grantUserOrgCode;
   }
 
   public void setGrantUserOrgCode(String grantUserorgCode)
   {
     this.grantUserOrgCode = grantUserorgCode;
   }
 
   public String getGrantUserPosiCode()
   {
     return this.grantUserPosiCode;
   }
 
   public void setGrantUserPosiCode(String grantUserPosiCode)
   {
     this.grantUserPosiCode = grantUserPosiCode;
   }
 
   public String getGrantUserPosiId()
   {
     return this.grantUserPosiId;
   }
 
   public void setGrantUserPosiId(String grantUserPosiId)
   {
     this.grantUserPosiId = grantUserPosiId;
   }
 
   public boolean isGranted()
   {
     return this.isGranted;
   }
 
   public void setGranted(boolean isGranted)
   {
     this.isGranted = isGranted;
   }
 
   public String getUserId()
   {
     return this.userId;
   }
 
   public void setUserId(String userId)
   {
     this.userId = userId;
   }
 
   public TaskUser() {
   }
 
   public TaskUser(String userId) {
     this.userId = userId;
   }
 
   public int getGrantLevel()
   {
     return this.grantLevel;
   }
 
   public void setGrantLevel(int grantLevel)
   {
     this.grantLevel = grantLevel;
   }
 
   public String toString()
   {
     String result = "";
     result = result + getUserId() + ",";
     result = result + getGrantUserId() + ",";
     result = result + getGrantUserCoCode() + ",";
     result = result + getGrantUserOrgCode() + ",";
     result = result + getGrantUserPosiId() + ",";
     result = result + getGrantUserPosiCode() + ",";
     result = result + getGrantLevel();
     return result;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.TaskUser
 * JD-Core Version:    0.6.0
 */