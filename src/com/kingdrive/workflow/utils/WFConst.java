package com.kingdrive.workflow.utils;

public class WFConst
{
  public static final String ACTION_TYPE_AUTHORIZE_TASK = "authorize_task";
  public static final String ACTION_TYPE_FORWARD_TASK = "forward_task";
  public static final String ACTION_TYPE_CALLBACK_FLOW = "callback_flow";
  public static final String ACTION_TYPE_GIVEBACK_FLOW = "giveback_flow";
  public static final String ACTION_TYPE_TRANSFER_FLOW = "transfer_flow";
  public static final String ACTION_TYPE_UNTREAD_FLOW = "untread_flow";
  public static final String ACTION_TYPE_ACTIVATE_INSTANCE = "activate_instance";
  public static final String ACTION_TYPE_DEACTIVATE_INSTANCE = "deactivate_instance";
  public static final String ACTION_TYPE_INTERRUPT_INSTANCE = "interrupt_instance";
  public static final String ACTION_TYPE_RESTART_INSTANCE = "restart_instance";
  public static final String ACTION_TYPE_REDO_INSTANCE = "redo_instance";
  public static final int ACTION_NODE_UNKNOWN = -9;
  public static final int TASK_IDENTITY_NORMAL = 1;
  public static final int TASK_IDENTITY_DELEGATED = -1;
  public static final int TASK_IDENTITY_DELEGATING = -9;
  public static final int TASK_TYPE_NORMAL = 0;
  public static final int TASK_TYPE_TOCOLLECT = -2;
  public static final int TASK_TYPE_COLLECTED = -1;
  public static final int TASK_TYPE_TOCOLLECT_DETAIL = -999;
  public static final String TASK_NODE = "2";
  public static final String SPLIT_NODE = "3";
  public static final String MERGE_NODE = "4";
  public static final int INSTANCE_STATUS_ACTIVE = 1;
  public static final int INSTANCE_STATUS_DEACTIVE = -1;
  public static final int INSTANCE_STATUS_FINISHED = 9;
  public static final int INSTANCE_STATUS_INTERRUPTED = -9;
  public static final String EXECUTORS_METHOD_SOLO = "0";
  public static final String EXECUTORS_METHOD_PARALLEL = "1";
  public static final String EXECUTORS_METHOD_SERIAL = "2";
  public static final String EXECUTORS_POLICY_SOLO = "0";
  public static final String EXECUTORS_POLICY_PARALLEL = "1";
  public static final String NUMBERORPERCENT_NUMBER = "0";
  public static final String NUMBERORPERCENT_PERCENT = "1";
  public static final String PROCESS_INST_ID_FIELD = "PROCESS_INST_ID";
  public static final String INITIAL_END_TIME = "00000000000000";
  public static final String WF_COMPANY_CODE = "WF_COMPANY_CODE";
  public static final String WF_ORG_CODE = "WF_ORG_CODE";
  public static final String WF_POSITION_ID = "WF_POSITION_ID";
  public static final String WF_POSITION_CODE = "WF_POSITION_CODE";
  public static final String WF_EMP_CODE = "WF_EMP_CODE";
  public static final String WF_EXECUTOR = "WF_EXECUTOR";
  public static final String ND = "ND";
  public static final String EXECUTOR_RELATION_NONE = "0";
  public static final String EXECUTOR_RELATION_MANAGER = "1";
  public static final String EXECUTOR_RELATION_SELF = "2";
  public static final String EXECUTOR_RELATION_BUSINESS_SUPPERIOR = "3";
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.utils.WFConst
 * JD-Core Version:    0.6.0
 */