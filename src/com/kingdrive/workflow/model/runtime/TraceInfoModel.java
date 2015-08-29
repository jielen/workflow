 package com.kingdrive.workflow.model.runtime;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 
 public class TraceInfoModel
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Long instanceId;
   private String creator;
   private String startTime;
   private String endTime;
   private String status;
   private List doneActionList = new ArrayList();
   private List todoAction = new ArrayList();
   private List actionHistoryList = new ArrayList();
   private ActionHistoryModel untreadAction;
   private List extendActionList = new ArrayList();
 
   public List getDoneActionList()
   {
     return this.doneActionList;
   }
 
   public void setDoneActionList(List doneActionList)
   {
     this.doneActionList = doneActionList;
   }
 
   public String getEndTime()
   {
     return this.endTime;
   }
 
   public void setEndTime(String endTime)
   {
     this.endTime = endTime;
   }
 
   public Long getInstanceId()
   {
     return this.instanceId;
   }
 
   public void setInstanceId(Long instanceId)
   {
     this.instanceId = instanceId;
   }
 
   public String getStartTime()
   {
     return this.startTime;
   }
 
   public void setStartTime(String startTime)
   {
     this.startTime = startTime;
   }
 
   public String getStatus()
   {
     return this.status;
   }
 
   public void setStatus(String status)
   {
     this.status = status;
   }
 
   public String getCreator()
   {
     return this.creator;
   }
 
   public void setCreator(String creator)
   {
     this.creator = creator;
   }
 
   public List getTodoAction()
   {
     return this.todoAction;
   }
 
   public void setTodoAction(List todoAction)
   {
     this.todoAction = todoAction;
   }
 
   public ActionHistoryModel getUntreadAction()
   {
     return this.untreadAction;
   }
 
   public void setUntreadAction(ActionHistoryModel untreadAction)
   {
     this.untreadAction = untreadAction;
   }
 
   public List getActionHistoryList()
   {
     return this.actionHistoryList;
   }
 
   public void setActionHistoryList(List actionHistoryList)
   {
     this.actionHistoryList = actionHistoryList;
   }
 
   public List getExtendActionList()
   {
     return this.extendActionList;
   }
 
   public void setExtendActionList(List extendActionList)
   {
     this.extendActionList = extendActionList;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.TraceInfoModel
 * JD-Core Version:    0.6.0
 */