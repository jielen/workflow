package com.kingdrive.workflow.service;

import com.kingdrive.workflow.context.WorkflowContext;
import com.kingdrive.workflow.exception.WorkflowException;
import com.kingdrive.workflow.model.TableData;
import com.kingdrive.workflow.model.TaskUser;
import com.kingdrive.workflow.model.meta.LinkModel;
import com.kingdrive.workflow.model.meta.NodeModel;
import com.kingdrive.workflow.model.runtime.BusinessJuniorModel;
import com.kingdrive.workflow.service.db.WFMetaService;
import com.kingdrive.workflow.service.db.WFRuntimeService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.Assert;

public class ResourceService {
	private WFRuntimeService runtimeService;
	private WFMetaService metaService;

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

	public List getExecutorsByRelation(NodeModel nextNode,
			WorkflowContext context) {
		List mainExecutor = getMainExecutor(nextNode, context);
		return mainExecutor;
	}

	private List getMainExecutor(NodeModel nextNode, WorkflowContext _context) {
		List result = null;
		WorkflowContext context = _context;
		if (_context.hasGranterContext()) {
			context = _context.getGranterContext();
		}
		TableData businessData = context.getEntityData();
		String compoName = businessData.getName();

		String junior = (String) context.getVariable("WF_EXECUTOR");
		if (junior == null) {
			junior = context.getExecutor();
		}
		String coCode = (String) context.getVariable("WF_COMPANY_CODE");
		String orgCode = (String) context.getVariable("WF_ORG_CODE");
		String positionId = (String) context.getVariable("WF_POSITION_ID");
		String positionCode = (String) context.getVariable("WF_POSITION_CODE");

		String nd = (String) context.getVariable("ND");

		String relation = nextNode.getExecutorRelation();
		if (relation.equals("2")) {
			result = new ArrayList();
			result.add(new TaskUser(context.getExecutor()));
		} else if (relation.equals("1")) {
			result = getSuperStaffSet(junior, positionId, Long.valueOf(nd));
		} else if (relation.equals("3")) {
			result = getSuperiorByPri(compoName, businessData, junior, coCode,
					orgCode, positionCode, Integer.valueOf(nd));
		} else if (relation.equals("0")) {
			String sql = nextNode.getRuntimeFromLink().getDescription();
			if ((sql != null) && (!"".equals(sql.trim()))){
				try {
					List list = getRuntimeService().queryForList(sql,new Object[] { _context.getInstanceId() });
					if ((list != null) && (list.size() > 0)) {
						result = new ArrayList();
						for (Iterator iterator = list.iterator(); iterator.hasNext();) {
							Map data = (Map) iterator.next();
							Assert.notEmpty(data, "模板中定义脚本，返回执行人为空!");
							if ((data != null) && (!data.values().isEmpty())){
								result.add(new TaskUser((String) data.values().iterator().next()));
								break;
							}								
						}
					}
					if(result==null || result.size()==0){
						result = getPreSetExecutor(nextNode.getNodeId(),Long.valueOf(nd));
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}else {
				result = getPreSetExecutor(nextNode.getNodeId(),Long.valueOf(nd));
			}
		}
		return result;
	}

	public List getGrantedExecutor(String granterId) {
		List result = new ArrayList();
		String sql = "SELECT * FROM wf_task_granter t WHERE t.grant_user_id=? AND t.Is_Grant = 'Y' AND to_date(to_char(sysdate, 'yyyydd'),'yyyydd') <= t.grant_to_date";
		List list = getRuntimeService().queryForList(sql,
				new Object[] { granterId });
		Map data = null;
		for (int i = 0; i < list.size(); i++) {
			data = (Map) list.get(i);
			TaskUser u = new TaskUser((String) data.get("GRANTED_USER"));
			u.setGranted(true);
			u.setGrantUserCoCode((String) data.get("GRANT_USER_CO_CODE"));
			u.setGrantUserId((String) data.get("GRANT_USER_ID"));
			u.setGrantUserOrgCode((String) data.get("GRANT_USER_ORG_CODE"));
			u.setGrantUserPosiCode((String) data.get("GRANT_USER_POSI_CODE"));
			u.setGrantUserPosiId((String) data.get("GRANT_USER_POSI_ID"));
			BigDecimal decimal = (BigDecimal) data.get("GRANT_LEVEL");
			decimal = decimal == null ? new BigDecimal(0) : decimal;
			u.setGrantLevel(decimal.intValue());
			result.add(u);
		}
		return result;
	}

	public List getGrantedExecutor(List granterIds) {
		List result = new ArrayList();
		String ids = "";
		for (int i = 0; i < granterIds.size(); i++) {
			ids = ids + ",'" + granterIds.get(i) + "'";
		}
		ids = ids.length() > 0 ? ids.substring(1) : ids;
		String sql = "SELECT * FROM wf_task_granter t WHERE t.grant_user_id in ("
				+ ids
				+ ") AND t.Is_Grant = 'Y' AND to_date(to_char(sysdate, 'yyyydd'),'yyyydd') <= t.grant_to_date";
		List list = getRuntimeService().queryForList(sql, new Object[0]);
		Map data = null;
		for (int i = 0; i < list.size(); i++) {
			data = (Map) list.get(i);
			TaskUser u = new TaskUser((String) data.get("GRANTED_USER"));
			u.setGranted(true);
			u.setGrantUserCoCode((String) data.get("GRANT_USER_CO_CODE"));
			u.setGrantUserId((String) data.get("GRANT_USER_ID"));
			u.setGrantUserOrgCode((String) data.get("GRANT_USER_ORG_CODE"));
			u.setGrantUserPosiCode((String) data.get("GRANT_USER_POSI_CODE"));
			u.setGrantUserPosiId((String) data.get("GRANT_USER_POSI_ID"));
			BigDecimal decimal = (BigDecimal) data.get("GRANT_LEVEL");
			u.setGrantLevel(decimal.intValue());
			result.add(u);
		}
		return result;
	}

	private List getSuperStaffSet(String executor, String orgPosiCode, Long nd) {
		String sql = "select distinct(a.STAFF_ID) from wf_staff a, wf_staff_position b, wf_org_position_level c, wf_staff_position d where a.staff_id = b.staff_id and b.org_position_id = c.parent_id and c.org_position_id = d.org_position_id and d.staff_id = ? and d.org_position_id =? and b.nd=?";

		List list = getRuntimeService().queryForList(sql,
				new Object[] { executor, orgPosiCode, nd });
		List result = new ArrayList();
		Map data = null;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			data = (Map) iter.next();
			result.add(new TaskUser((String) data.get("STAFF_ID")));
		}
		return result;
	}

	private List getPreSetExecutor(Long nextNodeId, Long nd) {
		String sql = "select a.EXECUTOR from v_wf_executor_source a, wf_staff b where a.executor = b.staff_id and a.node_id = ? and a.extnd=?";
		List list = getRuntimeService().queryForList(sql,
				new Object[] { nextNodeId, nd });
		List result = new ArrayList();
		Map data = null;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			data = (Map) iter.next();
			result.add(new TaskUser((String) data.get("EXECUTOR")));
		}
		return result;
	}

	private List getSuperiorByPri(String compoName, TableData data,
			String junior, String junCoCode, String junOrgCode,
			String junPosiCode, Integer nd) {
		List superior = new ArrayList();
		List all = getRuntimeService().getSuperior(nd);
		List records = BusinessJuniorModel.doFilter(all, junCoCode, junOrgCode,
				junPosiCode, junior);
		List ret = new ArrayList();
		for (int i = 0; i < records.size(); i++) {
			BusinessJuniorModel bj = (BusinessJuniorModel) records.get(i);
			if (BusinessJuniorModel.isBelow(compoName, data, bj))
				ret.add(bj);
		}
		if (ret.size() > 0) {
			Collections.sort(ret, new BusinessJuniorModel());

			ArrayList arr = BusinessJuniorModel.getSmalls(ret);

			for (int i = 0; i < arr.size(); i++) {
				BusinessJuniorModel bj = (BusinessJuniorModel) ret.get(i);
				Set tempSuperior = bj
						.fallbackSuperior(junCoCode, junOrgCode, junPosiCode,
								junior, nd.toString(), getRuntimeService());
				if ((tempSuperior != null) && (tempSuperior.size() > 0)) {
					superior.addAll(tempSuperior);
				}
			}
		}
		List result = new ArrayList();
		for (int i = 0; i < superior.size(); i++) {
			result.add(new TaskUser((String) superior.get(i)));
		}
		return result;
	}

	public String getEmpCode(String userId) throws WorkflowException {
		String sql = "SELECT T.EMP_CODE FROM AS_EMP T WHERE T.USER_ID = ?";
		List result = this.runtimeService.queryForList(sql,
				new String[] { userId });
		if (result.size() == 0) {
			throw new WorkflowException(userId + "没有empcode");
		}
		Map record = (Map) result.get(0);
		return (String) record.get("EMP_CODE");
	}
}

/*
 * Location: D:\temp\wf\wf.jar Qualified Name:
 * com.kingdrive.workflow.service.ResourceService JD-Core Version: 0.6.0
 */