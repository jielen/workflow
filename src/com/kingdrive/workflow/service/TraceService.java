package com.kingdrive.workflow.service;

import com.kingdrive.workflow.exception.WorkflowException;
import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
import com.kingdrive.workflow.model.runtime.ActionModel;
import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
import com.kingdrive.workflow.model.runtime.TraceInfoModel;
import com.kingdrive.workflow.service.db.WFRuntimeService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.util.Assert;

public class TraceService {
	private static String QUERY_INSTANCE = "select e.emp_name, t.start_time, t.end_time, t.status from wf_instance t inner join as_emp e on t.owner=e.user_id where t.instance_id=?";

	private static String QUERY_ACTION = "select n.name node_name, t.action_name, e.emp_name, t.description, t.execute_time from wf_action t inner join wf_node n on t.node_id=n.node_id inner join as_emp e on t.executor=e.user_id where t.instance_id=? order by t.execute_time";

	private static String QUERY_CURRENTTASK = "select n.name node_name, e.emp_name FROM wf_current_task t inner join wf_node n on t.node_id=n.node_id inner join as_emp e on t.executor=e.user_id WHERE t.INSTANCE_ID=? order by t.current_task_id";

	private static String QUERY_ACTIONHISTORY = "select n.name, t.action_name, t.execute_time, t.description, e.emp_name  from wf_action_history t inner join wf_node n on t.node_id=n.node_id inner join as_emp e on t.executor=e.user_id where t.instance_id=? order by t.execute_time";
	private WFRuntimeService runtimeService;

	public WFRuntimeService getRuntimeService() {
		return this.runtimeService;
	}

	public void setRuntimeService(WFRuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public TraceInfoModel getTraceInfo(Long instanceId)
			throws WorkflowException {
		try {
			TraceInfoModel model = new TraceInfoModel();
			queryInstanceInfo(model, instanceId);
			queryActionInfo(model, instanceId);
			queryCurrentTaskInfo(model, instanceId);
			queryActionHistoryInfo(model, instanceId);
			return model;
		} catch (Exception ex) {
			throw new WorkflowException(ex);
		}
	}

	private TraceInfoModel queryInstanceInfo(TraceInfoModel model,
			Long instanceId) throws Exception {
		SimpleDateFormat viewFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

		List result = getRuntimeService().queryForList(QUERY_INSTANCE,
				new Object[] { instanceId });
		Assert.notEmpty(result, "流程 " + instanceId + " 未找到");
		Map record = (Map) result.get(0);
		model.setInstanceId(instanceId);
		model.setCreator((String) record.get("EMP_NAME"));
		String startTime = (String) record.get("START_TIME");
		model.setStartTime(viewFormat.format(format.parse(startTime)));
		String endTime = (String) record.get("END_TIME");
		model.setEndTime(viewFormat.format(format.parse(endTime)));
		model.setStatus(record.get("STATUS").toString());
		return model;
	}

	private TraceInfoModel queryActionInfo(TraceInfoModel model, Long instanceId)
			throws Exception {
		SimpleDateFormat viewFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.getDefault());
		List result = getRuntimeService().queryForList(QUERY_ACTION,
				new Object[] { instanceId });
		Map record = null;
		for (int i = 0; i < result.size(); i++) {
			record = (Map) result.get(i);
			ActionModel action = new ActionModel();
			action.setNodeName((String) record.get("NODE_NAME"));
			action.setActionName((String) record.get("ACTION_NAME"));
			action.setExecutorName((String) record.get("EMP_NAME"));
			action.setDescription((String) record.get("DESCRIPTION"));
			String exeTime = (String) record.get("EXECUTE_TIME");
			action.setExecuteTime(viewFormat.format(format.parse(exeTime)));
			model.getDoneActionList().add(action);
		}
		return model;
	}

	private TraceInfoModel queryCurrentTaskInfo(TraceInfoModel model,
			Long instanceId) throws Exception {
		if (!"9".equals(model.getStatus())) {
			List result = getRuntimeService().queryForList(QUERY_CURRENTTASK,
					new Object[] { instanceId });
			Map record = null;
			for (int i = 0; i < result.size(); i++) {
				record = (Map) result.get(i);
				CurrentTaskModel task = new CurrentTaskModel();
				task.setNodeName((String) record.get("NODE_NAME"));
				task.setExecutorName((String) record.get("EMP_NAME"));
				model.getTodoAction().add(task);
			}
		}
		return model;
	}

	private TraceInfoModel queryActionHistoryInfo(TraceInfoModel model,
			Long instanceId) throws Exception {
		List result = new ArrayList();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat viewFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		List mapList = getRuntimeService().queryForList(QUERY_ACTIONHISTORY,
				new Object[] { instanceId });
		Map record = null;
		ActionHistoryModel actionHistory = null;
		for (int i = 0; i < mapList.size(); i++) {
			record = (Map) mapList.get(i);
			actionHistory = new ActionHistoryModel();
			actionHistory.setActionName(transLateActionName((String) record
					.get("ACTION_NAME")));
			actionHistory.setDescription((String) record.get("DESCRIPTION"));
			String exeTime = (String) record.get("EXECUTE_TIME");
			actionHistory.setExecuteTime(viewFormat.format(format
					.parse(exeTime)));
			actionHistory.setNodeName((String) record.get("NAME"));
			actionHistory.setExecutorName((String) record.get("EMP_NAME"));
			result.add(actionHistory);
		}

		model.setActionHistoryList(result);
		actionHistory = (ActionHistoryModel) result.get(result.size() - 1);
		if ("退回".equals(actionHistory.getActionName())) {
			model.setUntreadAction(actionHistory);
		}

		return model;
	}

	private String transLateActionName(String value) {
		String result = value;
		if ("callback_flow".equals(value)) {
			result = "收回";
		}
		if ("untread_flow".equals(value)) {
			result = "退回";
		}
		return result;
	}
}

/*
 * Location: D:\temp\wf\wf.jar Qualified Name:
 * com.kingdrive.workflow.service.TraceService JD-Core Version: 0.6.0
 */