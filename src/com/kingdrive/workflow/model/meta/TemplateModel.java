package com.kingdrive.workflow.model.meta;

import com.kingdrive.workflow.context.WorkflowContext;
import com.kingdrive.workflow.exception.WorkflowException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

public class TemplateModel implements Serializable {
	private static final long serialVersionUID = 7978089612196700405L;
	private Long templateId = null;

	private String name = null;

	private String description = null;

	private String templateType = null;

	private String version = null;

	private String startTime = null;

	private String expireTime = null;

	private String createTime = null;

	private String createStaffId = null;
	private String active;
	private List nodeList = new ArrayList();

	private List linkList = new ArrayList();

	private List bindStateList = new ArrayList();

	private List bindVariableList = new ArrayList();

	private Map nodeMap = new HashMap();

	private Map linkMap = new HashMap();
	private NodeModel startNode;
	private NodeModel lastNode;
	private String action = "";

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreateStaffId() {
		return this.createStaffId;
	}

	public void setCreateStaffId(String createStaffId) {
		this.createStaffId = createStaffId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List getLinkList() {
		return this.linkList;
	}

	public void setLinkList(List linkList) {
		this.linkList = linkList;
	}

	public List getNodeList() {
		return this.nodeList;
	}

	public void setNodeList(List nodeList) {
		this.nodeList = nodeList;
	}

	public NodeModel getStartNode() {
		return this.startNode;
	}

	public void setStartNode(NodeModel startNode) {
		this.startNode = startNode;
	}

	public NodeModel getLastNode() {
		return this.lastNode;
	}

	public void setLastNode(NodeModel lastNode) {
		this.lastNode = lastNode;
	}

	public List getBindStateList() {
		return this.bindStateList;
	}

	public void setBindStateList(List bindStateList) {
		this.bindStateList = bindStateList;
	}

	public List getBindVariableList() {
		return this.bindVariableList;
	}

	public void setBindVariableList(List bindVariable) {
		this.bindVariableList = bindVariable;
	}

	public void assembleGraph() {
		populateNodeMap();
		LinkModel link = null;
		Long currengNodeId = null;
		NodeModel currentNode = null;
		NodeModel nextNode = null;
		Long nextNodeId = null;
		NodeModel tempNode = null;
		for (int i = 0; i < this.nodeList.size(); i++) {
			tempNode = (NodeModel) this.nodeList.get(i);
			tempNode.setTemplate(this);
		}
		for (int i = 0; i < this.linkList.size(); i++) {
			link = (LinkModel) this.linkList.get(i);
			link.setTemplate(this);
			currengNodeId = link.getCurrentNodeId();
			currentNode = getNode(currengNodeId);
			nextNodeId = link.getNextNodeId();
			nextNode = getNode(nextNodeId);
			if (currentNode != null) {
				currentNode.addFormLink(link);
			}
			if (nextNode != null) {
				nextNode.addToLink(link);
			}
			if (currengNodeId.intValue() == -1) {
				this.startNode = nextNode;
			}
			if (nextNodeId.intValue() == -2)
				this.lastNode = currentNode;
		}
	}

	public NodeModel getNode(Long nodeId) {
		return (NodeModel) this.nodeMap.get(nodeId);
	}

	public List getNextTaskNodes(NodeModel currentNode, WorkflowContext _context)
			throws WorkflowException {
		List nodes = new ArrayList();

		WorkflowContext context = null;
		context = _context;
		try {
			if (_context.hasGranterContext())
				context = _context.getGranterContext();
			List fromLinks;
			if (context.getNextLink().size() > 0) {
				LinkModel link = (LinkModel) context.getNextLink().get(0);
				if (link.getCurrentNode().equals(currentNode))
					fromLinks = context.getNextLink();
				else
					fromLinks = currentNode.getFromLinkList();
			} else {
				fromLinks = currentNode.getFromLinkList();
			}
			for (int i = 0; i < fromLinks.size(); i++) {
				LinkModel link = (LinkModel) fromLinks.get(i);
				if (link.isMatchExpr(context)) {
					NodeModel nextNode = link.getNextNode();
					NodeModel tempNode = (NodeModel) BeanUtils
							.cloneBean(nextNode);
					tempNode.setExecutorRelation(link.getExecutorRelation());
					tempNode.setRuntimeFromLink(link);
					if ((nextNode != null) && (nextNode.isTaskNode())) {
						nodes.add(tempNode);
					} else {
						if ((nextNode == null) || (!nextNode.isSplitNode()))
							continue;
						if (nextNode.equals(getLastNode())) {
							nodes.add(tempNode);
							break;
						}
						nodes.addAll(getNextTaskNodes(nextNode, context));
					}
				}

			}

			return nodes;
		} catch (Exception ex) {
			throw new WorkflowException(ex);
		}
	}

	public List getNextActualTaskNodes(NodeModel currentNode,
			WorkflowContext _context) throws WorkflowException {
		List nodes = new ArrayList();

		WorkflowContext context = null;
		context = _context;
		try {
			if (_context.hasGranterContext())
				context = _context.getGranterContext();
			List fromLinks;
			if (context.getNextLink().size() > 0) {
				LinkModel link = (LinkModel) context.getNextLink().get(0);
				if (link.getCurrentNode().equals(currentNode))
					fromLinks = context.getNextLink();
				else
					fromLinks = currentNode.getFromLinkList();
			} else {
				fromLinks = currentNode.getFromLinkList();
			}
			for (int i = 0; i < fromLinks.size(); i++) {
				LinkModel link = (LinkModel) fromLinks.get(i);
				NodeModel nextNode = link.getNextNode();
				NodeModel tempNode = (NodeModel) BeanUtils.cloneBean(nextNode);
				tempNode.setExecutorRelation(link.getExecutorRelation());
				tempNode.setRuntimeFromLink(link);
				if ((nextNode != null) && (nextNode.isTaskNode())) {
					nodes.add(tempNode);
				} else {
					if ((nextNode == null) || (!nextNode.isSplitNode()))
						continue;
					if (nextNode.equals(getLastNode())) {
						nodes.add(tempNode);
					} else {
						nodes.addAll(getNextActualTaskNodes(nextNode, context));
					}
				}
			}

			return nodes;
		} catch (Exception ex) {
			throw new WorkflowException(ex);
		}
	}

	public List getParentTaskNodes(NodeModel currentNode)
			throws WorkflowException {
		List nodes = new ArrayList();
		try {
			List toLinks = currentNode.getToLinkList();
			if ((toLinks != null) && (toLinks.size() > 0)) {
				for (int i = 0; i < toLinks.size(); i++) {
					LinkModel link = (LinkModel) toLinks.get(i);
					NodeModel parentNode = link.getCurrentNode();
					NodeModel tempNode = (NodeModel) BeanUtils
							.cloneBean(parentNode);
					if ((parentNode != null) && (parentNode.isTaskNode())) {
						nodes.add(tempNode);
					} else if ((parentNode != null)
							&& (parentNode.isSplitNode())) {
						if (parentNode.equals(getStartNode())) {
							nodes.add(tempNode);
							break;
						}
						nodes.addAll(getParentTaskNodes(parentNode));
					}
				}
			}
		} catch (Exception localException) {
		}

		return nodes;
	}

	private void populateNodeMap() {
		NodeModel node = null;
		for (int i = 0; i < this.nodeList.size(); i++) {
			node = (NodeModel) this.nodeList.get(i);
			this.nodeMap.put(node.getNodeId(), node);
		}
	}
}

/*
 * Location: D:\temp\wf\wf.jar Qualified Name:
 * com.kingdrive.workflow.model.meta.TemplateModel JD-Core Version: 0.6.0
 */