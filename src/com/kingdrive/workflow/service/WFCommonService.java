package com.kingdrive.workflow.service;

import com.kingdrive.workflow.context.WorkflowContext;
import com.kingdrive.workflow.exception.WorkflowException;
import com.kingdrive.workflow.model.TableData;
import com.kingdrive.workflow.model.TaskUser;
import com.kingdrive.workflow.model.meta.BindStateModel;
import com.kingdrive.workflow.model.meta.CompoModel;
import com.kingdrive.workflow.model.meta.LinkModel;
import com.kingdrive.workflow.model.meta.LinkStateModel;
import com.kingdrive.workflow.model.meta.NodeModel;
import com.kingdrive.workflow.model.meta.NodeStateModel;
import com.kingdrive.workflow.model.meta.TemplateModel;
import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
import com.kingdrive.workflow.model.runtime.DraftModel;
import com.kingdrive.workflow.model.runtime.InstanceModel;
import com.kingdrive.workflow.model.runtime.StateValueModel;
import com.kingdrive.workflow.model.runtime.TaskExecutorModel;
import com.kingdrive.workflow.service.db.WFMetaService;
import com.kingdrive.workflow.service.db.WFRuntimeService;
import com.kingdrive.workflow.utils.WFUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.Assert;

public class WFCommonService {
	private WFRuntimeService runtimeService;
	private WFMetaService metaService;
	private ResourceService resourceService;

	public WFMetaService getMetaService() {
		return this.metaService;
	}

	public void setMetaService(WFMetaService metaService) {
		this.metaService = metaService;
	}

	public WFRuntimeService getRuntimeService() {
		return this.runtimeService;
	}

	public void setRuntimeService(WFRuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public ResourceService getResourceService() {
		return this.resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void finishWorkflow(InstanceModel instance) throws WorkflowException {
		try {
			getRuntimeService()
					.removeCurrentTaskByIns(instance.getInstanceId());
			getRuntimeService().updateInstance(instance);
		} catch (Exception ex) {
			throw new WorkflowException(ex);
		}
	}

	public void createTaskExecutor(Long instanceId, Long nodeId, List executors)
			throws WorkflowException {
		try {
			List oldExecutor = getRuntimeService().getTaskExecutor(instanceId,
					nodeId);
			if (oldExecutor == null) {
				oldExecutor = new ArrayList();
			}
			TaskUser executor = null;
			TaskUser granted = null;

			TaskExecutorModel taskExecutor = new TaskExecutorModel();
			taskExecutor.setInstanceId(instanceId);
			taskExecutor.setNodeId(nodeId);

			List granterIds = new ArrayList();
			for (int i = 0; i < executors.size(); i++) {
				taskExecutor.setTaskExecutorOrderId(new Long(1L));
				executor = (TaskUser) executors.get(i);
				if (!oldExecutor.contains(executor.getUserId())) {
					taskExecutor.setExecutor(executor.getUserId());
					getRuntimeService().createTaskExecutor(taskExecutor);
				}

			}

		} catch (Exception ex) {
			throw new WorkflowException(ex);
		}
	}

	public boolean canFinishNodeTask(CurrentTaskModel taskItem,
			NodeModel nextNode) throws WorkflowException {
		try {
			boolean result = false;
			Long instanceId = taskItem.getInstanceId();
			Long currentNodeId = taskItem.getNodeId();
			LinkModel link = nextNode.getRuntimeFromLink();

			int count = getRuntimeService().getActionCountByNode(instanceId,
					currentNodeId);

			String executePolicy = link.getPassPolicy();

			if (("0".equals(executePolicy)) && (count > 0)) {
				result = true;
			}

			if ("1".equals(executePolicy)) {
				int passValue = link.getPassValue().intValue();
				if ("0".equals(link.getNumberOrPercent())) {
					if ((count >= passValue) && (count % passValue == 0)) {
						result = true;
					}
				} else if ("1".equals(link.getNumberOrPercent())) {
					List executorList = getRuntimeService().getTaskExecutor(
							instanceId, currentNodeId);
					int totalCount = executorList.size();
					double passPercent = count * 1.0D / totalCount;
					double needPercent = passValue / 100.0D;
					if (passPercent >= needPercent) {
						result = true;
					}
				}
			}

			return result;
		} catch (Exception ex) {
			throw new WorkflowException(ex);
		}
	}

	public void createActionHistory(CurrentTaskModel taskItem,
			String actionName, String exeTime, String comment) {
		ActionHistoryModel actionHistory = new ActionHistoryModel();
		actionHistory.setInstanceId(taskItem.getInstanceId());
		actionHistory.setNodeId(taskItem.getNodeId());
		actionHistory.setActionName(actionName);
		actionHistory.setExecutor(taskItem.getExecutor());
		actionHistory.setExecuteTime(exeTime);
		comment = comment == null ? "" : comment;
		actionHistory.setDescription(comment);
		actionHistory.setOwner(taskItem.getExecutor());
		String limitTime = taskItem.getLimitExecuteTime();
		limitTime = limitTime == null ? "" : limitTime;
		actionHistory.setLimitExecuteTime(limitTime);
		getRuntimeService().createActionHistory(actionHistory);
	}

	public void setInstanceStateByNode(Long instanceId, NodeModel nextNode) {
		List stateValueList = getRuntimeService()
				.getStateValueByIns(instanceId);
		List nodeStateValueList = nextNode.getNodeStateList();
		StateValueModel sv = null;
		NodeStateModel ns = null;
		boolean update = false;
		for (int i = 0; i < nodeStateValueList.size(); i++) {
			update = false;
			ns = (NodeStateModel) nodeStateValueList.get(i);
			for (int j = 0; j < stateValueList.size(); j++) {
				sv = (StateValueModel) stateValueList.get(j);
				if (ns.getStateId().intValue() == sv.getStateId().intValue()) {
					sv.setValue(ns.getStateValue());
					getRuntimeService().updateStateValue(sv);
					update = true;
					break;
				}
			}
			if (!update) {
				StateValueModel model = new StateValueModel();
				model.setInstanceId(instanceId);
				model.setStateId(ns.getStateId());
				model.setValue(ns.getStateValue());
				getRuntimeService().createStateValue(model);
			}
		}
	}

	public void setInstanceStateByLink(Long instanceId, LinkModel linkModel) {
		List stateValueList = getRuntimeService()
				.getStateValueByIns(instanceId);
		List linkStateValueList = linkModel.getLinkStateList();
		StateValueModel sv = null;
		LinkStateModel ls = null;
		boolean update = false;
		for (int i = 0; i < linkStateValueList.size(); i++) {
			update = false;
			ls = (LinkStateModel) linkStateValueList.get(i);
			for (int j = 0; j < stateValueList.size(); j++) {
				sv = (StateValueModel) stateValueList.get(j);
				if (ls.getStateId().intValue() == sv.getStateId().intValue()) {
					sv.setValue(ls.getStateValue());
					getRuntimeService().updateStateValue(sv);
					update = true;
					break;
				}
			}
			if (!update) {
				StateValueModel model = new StateValueModel();
				model.setInstanceId(instanceId);
				model.setStateId(ls.getStateId());
				model.setValue(ls.getStateValue());
				getRuntimeService().createStateValue(model);
			}
		}
	}

	public void setInstanceStateByLinks(Long instanceId, List linkList) {
		for (int i = 0; i < linkList.size(); i++)
			setInstanceStateByLink(instanceId, (LinkModel) linkList.get(i));
	}

	public void syncDataByBindedWFSate(Long instanceId, Long templateId) {
		TemplateModel template = getMetaService().getTemplate(templateId);
		List sqlList = new ArrayList();
		List stateValueList = getRuntimeService()
				.getStateValueByIns(instanceId);
		List bindStateList = template.getBindStateList();
		BindStateModel bindState = null;
		StateValueModel stateValue = null;
		for (int i = 0; i < bindStateList.size(); i++) {
			bindState = (BindStateModel) bindStateList.get(i);
			for (int j = 0; j < stateValueList.size(); j++) {
				stateValue = (StateValueModel) stateValueList.get(j);
				if (bindState.getStateId().longValue() == stateValue
						.getStateId().longValue()) {
					Object[] params = { stateValue.getValue(), instanceId };
					StringBuffer buffer = new StringBuffer();
					buffer.append(" update ").append(bindState.getTableId())
							.append(" set " + bindState.getFieldId() + " =? ")
							.append(" where PROCESS_INST_ID=?");
					getRuntimeService()
							.executeUpdate(buffer.toString(), params);
				}
			}
		}
	}

	public void removeNodeState(Long instanceId, NodeModel node) {
		List nodeStateList = node.getNodeStateList();
		NodeStateModel nodeState = null;
		StateValueModel stateValue = new StateValueModel();
		stateValue.setInstanceId(instanceId);
		for (int i = 0; i < nodeStateList.size(); i++) {
			nodeState = (NodeStateModel) nodeStateList.get(i);
			stateValue.setStateId(nodeState.getStateId());
			getRuntimeService().removeSateValue(stateValue);
		}
	}

	public void removeLinkState(Long instanceId, List links) {
		List linkStates = new ArrayList();
		LinkModel link = null;
		for (int i = 0; i < links.size(); i++) {
			link = (LinkModel) links.get(i);
			linkStates.addAll(link.getLinkStateList());
		}
		LinkStateModel linkState = null;
		StateValueModel stateValue = new StateValueModel();
		stateValue.setInstanceId(instanceId);
		for (int i = 0; i < linkStates.size(); i++) {
			linkState = (LinkStateModel) linkStates.get(i);
			stateValue.setStateId(linkState.getStateId());
			getRuntimeService().removeSateValue(stateValue);
		}
	}

	public void newCommit(WorkflowContext context) {
		String user = context.getExecutor();
		Long draftId = context.getInstanceId();

		TableData entity = context.getEntityData();
		CompoModel compo = getRuntimeService().getCompoInfoById(
				entity.getName());
		context.setTemplateId(compo.getTempolateId());
		context.setTableName(compo.getMasterTabId());

		TemplateModel template = getMetaService().getTemplate(
				compo.getTempolateId());

		Assert.notNull(draftId, "草稿id为空!");
		Assert.notNull(user, "提交人不能为空!");

		DraftModel draft = getRuntimeService().getDraftById(draftId);
		Assert.notNull(draft, "单据:" + draftId + " 没有流程信息，无法送审！");

		InstanceModel instance = new InstanceModel();
		createInstance(template, instance, context);
		updateBusinessData(draftId, instance.getInstanceId(),
				context.getTableName());
		getRuntimeService().removeDraftById(draftId);
	}

	public void createCurrentTask(Long instanceId, Long nodeId, String user,
			List executors, String exeTime, String actionName) {
		CurrentTaskModel task = new CurrentTaskModel();
		task.setInstanceId(instanceId);
		task.setNodeId(nodeId);
		task.setCreator(user);
		task.setCreateTime(exeTime);
		task.setGkSendStatus(getGkSendStatus(actionName));
		List mainExecutorIds = new ArrayList();
		TaskUser taskUser = null;
		for (int i = 0; i < executors.size(); i++) {
			taskUser = (TaskUser) executors.get(i);
			mainExecutorIds.add(taskUser.getUserId());
		}
		executors.addAll(this.resourceService
				.getGrantedExecutor(mainExecutorIds));
		for (int i = 0; i < executors.size(); i++) {
			TaskUser executor = (TaskUser) executors.get(i);
			task.setExecutor(executor.getUserId());
			task.setOwner(executor.getUserId());
			task.setGranterId(executor.getGrantUserId());
			task.setGranterInfo(executor.toString());
			task.setCurrentTaskId(null);
			getRuntimeService().createCurrentTask(task);
		}
	}

	private String getGkSendStatus(String actionName) {
		if (actionName == null)
			return null;
		if ("authorize_task".equals(actionName))
			return "4";
		if ("forward_task".equals(actionName))
			return "1";
		if ("callback_flow".equals(actionName))
			return "5";
		if ("giveback_flow".equals(actionName))
			return "3";
		if ("transfer_flow".equals(actionName))
			return "6";
		if ("untread_flow".equals(actionName))
			return "2";
		if ("activate_instance".equals(actionName))
			return "7";
		if ("deactivate_instance".equals(actionName))
			return "8";
		if ("interrupt_instance".equals(actionName))
			return "9";
		if ("restart_instance".equals(actionName))
			return "10";
		if ("redo_instance".equals(actionName)) {
			return "11";
		}
		return null;
	}

	private InstanceModel createInstance(TemplateModel template,
			InstanceModel instance, WorkflowContext context) {
		TableData tableData = context.getEntityData();
		String instanceName = tableData.getTitleFieldValue();
		String instanceDesc = (String) context.getVariable("instanceDesc");
		instanceDesc = instanceDesc == null ? "" : instanceDesc;

		instance.setTemplateId(template.getTemplateId());
		instance.setOwner(context.getExecutor());
		instance.setStartTime(WFUtil.getSysTime());
		instance.setEndTime("00000000000000");
		instance.setName(instanceName);
		instance.setStatus(new Long(1L));
		instance.setDescription(instanceDesc);
		getRuntimeService().createInscance(instance);

		NodeModel startNode = template.getStartNode();
		setInstanceStateByNode(instance.getInstanceId(), startNode);

		List taskExecutors = new ArrayList();
		taskExecutors.add(new TaskUser(context.getExecutor()));
		createTaskExecutor(instance.getInstanceId(), startNode.getNodeId(),
				taskExecutors);

		CurrentTaskModel currentTask = new CurrentTaskModel();
		currentTask.setInstance(instance);
		currentTask.setNodeId(startNode.getNodeId());
		currentTask.setExecutor(context.getExecutor());
		currentTask.setCreator(context.getExecutor());
		currentTask.setOwner(context.getExecutor());
		currentTask.setCreateTime(instance.getStartTime());
		getRuntimeService().createCurrentTask(currentTask);

		context.setInstanceId(instance.getInstanceId());
		context.setCurrentNode(startNode);
		return instance;
	}

	private void updateBusinessData(Long draftId, Long instanceId,
			String tableName) {
		String sql = " update " + tableName + " set " + "PROCESS_INST_ID"
				+ "=? where " + "PROCESS_INST_ID" + "=?";
		getRuntimeService().executeUpdate(sql,
				new Object[] { instanceId, draftId });
	}

	public List getExecutedNodeListBetween(TemplateModel template,
			Long currentNodeId, Long prevNodeId, Long instanceId) {
		List result = new ArrayList();
		NodeModel currentNode = template.getNode(currentNodeId);
		List linkList = currentNode.getToLinkList();
		NodeModel tempNode = null;
		LinkModel link = null;
		for (int i = 0; i < linkList.size(); i++) {
			link = (LinkModel) linkList.get(i);
			tempNode = link.getCurrentNode();
			if ((tempNode != null) && (tempNode.isTaskNode())) {
				int actionCount = getRuntimeService().getActionCountByNode(
						instanceId, tempNode.getNodeId());
				if (actionCount <= 0)
					continue;
				if (-1L == prevNodeId.longValue()) {
					result.add(tempNode.getNodeId());
				} else if (tempNode.getNodeId().longValue() == prevNodeId
						.longValue()) {
					result.add(tempNode.getNodeId());
				} else {
					List list = getExecutedNodeListBetween(template,
							tempNode.getNodeId(), prevNodeId, instanceId);
					if (list.size() > 0) {
						result.addAll(list);
						result.add(tempNode.getNodeId());
					}
				}

			} else if ((tempNode != null) && (tempNode.isSplitNode())) {
				result.addAll(getExecutedNodeListBetween(template,
						tempNode.getNodeId(), prevNodeId, instanceId));
			}
		}
		return getExecutedNodesOrderByTime(result, instanceId);
	}

	private List getExecutedNodesOrderByTime(List source, Long instanceId) {
		List result = new ArrayList();
		if ((source != null) && (source.size() > 0)) {
			List historyList = getRuntimeService().getActionHistoryByIns(
					instanceId);
			ActionHistoryModel history = null;
			for (int i = historyList.size() - 1; i > -1; i--) {
				history = (ActionHistoryModel) historyList.get(i);
				if (isNormalActionHistory(source, history)) {
					result.add(history.getNodeId());
				}
			}
		}
		return result;
	}

	private boolean isNormalActionHistory(List nodeSource,
			ActionHistoryModel actionHistory) {
		Long tempNodeId = null;
		for (int i = 0; i < nodeSource.size(); i++) {
			tempNodeId = (Long) nodeSource.get(i);
			if (tempNodeId.longValue() == actionHistory.getNodeId().longValue()) {
				return isNormalCommit(actionHistory.getActionName());
			}

		}

		return false;
	}

	private boolean isNormalCommit(String strActionName) {
		boolean isNormalCommit = (!"authorize_task".equals(strActionName))
				&& (!"callback_flow".equals(strActionName))
				&& (!"giveback_flow".equals(strActionName))
				&& (!"transfer_flow".equals(strActionName))
				&& (!"untread_flow".equals(strActionName))
				&& (!"activate_instance".equals(strActionName))
				&& (!"deactivate_instance".equals(strActionName))
				&& (!"interrupt_instance".equals(strActionName))
				&& (!"restart_instance".equals(strActionName));
		return isNormalCommit;
	}

	public void cancelGrantedTask(String userId) throws WorkflowException {
		String sql = "DELETE FROM wf_current_task t WHERE t.granter_id = ? AND t.granter_id IS NOT NULL";
		try {
			getRuntimeService().executeUpdate(sql, new Object[] { userId });
		} catch (Exception ex) {
			throw new WorkflowException(ex);
		}
	}
}

/*
 * Location: D:\temp\wf\wf.jar Qualified Name:
 * com.kingdrive.workflow.service.WFCommonService JD-Core Version: 0.6.0
 */