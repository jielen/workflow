 package com.kingdrive.workflow;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.model.runtime.TraceInfoModel;
 import com.kingdrive.workflow.service.ActivateService;
 import com.kingdrive.workflow.service.CallbackService;
 import com.kingdrive.workflow.service.CommitService;
 import com.kingdrive.workflow.service.DeactivateService;
 import com.kingdrive.workflow.service.HandoverService;
 import com.kingdrive.workflow.service.InterruptService;
 import com.kingdrive.workflow.service.RestartService;
 import com.kingdrive.workflow.service.ReworkService;
 import com.kingdrive.workflow.service.TraceService;
 import com.kingdrive.workflow.service.TransferService;
 import com.kingdrive.workflow.service.UntreadService;
 import com.kingdrive.workflow.service.WFCommonService;
 
 public class BasicWFEngine
   implements WFEngine
 {
   private WFCommonService commonService;
   private ActivateService activateService;
   private CallbackService callbackService;
   private CommitService commitService;
   private DeactivateService deactivateService;
   private InterruptService interruptService;
   private RestartService restartService;
   private ReworkService reworkService;
   private TransferService transferService;
   private UntreadService untreadService;
   private HandoverService handoverService;
   private TraceService traceService;
 
   public ActivateService getActivateService()
   {
     return this.activateService;
   }
 
   public void setActivateService(ActivateService activateService)
   {
     this.activateService = activateService;
   }
 
   public CallbackService getCallbackService()
   {
     return this.callbackService;
   }
 
   public void setCallbackService(CallbackService callbackService)
   {
     this.callbackService = callbackService;
   }
 
   public CommitService getCommitService()
   {
     return this.commitService;
   }
 
   public void setCommitService(CommitService commitService)
   {
     this.commitService = commitService;
   }
 
   public DeactivateService getDeactivateService()
   {
     return this.deactivateService;
   }
 
   public void setDeactivateService(DeactivateService deactivateService)
   {
     this.deactivateService = deactivateService;
   }
 
   public InterruptService getInterruptService()
   {
     return this.interruptService;
   }
 
   public void setInterruptService(InterruptService interruptService)
   {
     this.interruptService = interruptService;
   }
 
   public RestartService getRestartService()
   {
     return this.restartService;
   }
 
   public void setRestartService(RestartService restartService)
   {
     this.restartService = restartService;
   }
 
   public ReworkService getReworkService()
   {
     return this.reworkService;
   }
 
   public void setReworkService(ReworkService reworkService)
   {
     this.reworkService = reworkService;
   }
 
   public TransferService getTransferService()
   {
     return this.transferService;
   }
 
   public void setTransferService(TransferService transferService)
   {
     this.transferService = transferService;
   }
 
   public UntreadService getUntreadService()
   {
     return this.untreadService;
   }
 
   public void setUntreadService(UntreadService untreadService)
   {
     this.untreadService = untreadService;
   }
 
   public HandoverService getHandoverService()
   {
     return this.handoverService;
   }
 
   public void setHandoverService(HandoverService handoverService)
   {
     this.handoverService = handoverService;
   }
 
   public WFCommonService getCommonService() {
     return this.commonService;
   }
 
   public void setCommonService(WFCommonService commonService) {
     this.commonService = commonService;
   }
 
   public TraceService getTraceService()
   {
     return this.traceService;
   }
 
   public void setTraceService(TraceService traceService)
   {
     this.traceService = traceService;
   }
 
   public void commit(WorkflowContext context)
     throws WorkflowException
   {
     getCommitService().execute(context);
   }
 
   public void untread(WorkflowContext context)
     throws WorkflowException
   {
     getUntreadService().execute(context);
   }
 
   public void callback(WorkflowContext context)
     throws WorkflowException
   {
     getCallbackService().execute(context);
   }
 
   public void transfer(WorkflowContext context)
     throws WorkflowException
   {
     getTransferService().execute(context);
   }
 
   public void deactivate(WorkflowContext context)
     throws WorkflowException
   {
     getDeactivateService().execute(context);
   }
 
   public void activate(WorkflowContext context)
     throws WorkflowException
   {
     getActivateService().execute(context);
   }
 
   public void interrupt(WorkflowContext context)
     throws WorkflowException
   {
     getInterruptService().execute(context);
   }
 
   public void rework(WorkflowContext context)
     throws WorkflowException
   {
     getReworkService().execute(context);
   }
 
   public void restart(WorkflowContext context)
     throws WorkflowException
   {
     getRestartService().execute(context);
   }
 
   public void handover(WorkflowContext context)
     throws WorkflowException
   {
     getHandoverService().execute(context);
   }
 
   public void newCommit(WorkflowContext context)
     throws WorkflowException
   {
     getCommonService().newCommit(context);
     commit(context);
   }
 
   public TraceInfoModel getTraceInfo(Long instanceId)
     throws WorkflowException
   {
     return getTraceService().getTraceInfo(instanceId);
   }
 
   public void cancelGrantedTask(String userId) throws WorkflowException
   {
     getCommonService().cancelGrantedTask(userId);
   }
 
   public void interruptNotJudge(WorkflowContext context) throws WorkflowException
   {
     getInterruptService().interruptNotJudge(context);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.BasicWFEngine
 * JD-Core Version:    0.6.0
 */