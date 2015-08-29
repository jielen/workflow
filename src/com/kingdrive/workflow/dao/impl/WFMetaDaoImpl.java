package com.kingdrive.workflow.dao.impl;

import com.kingdrive.workflow.dao.WFMetaDao;
import com.kingdrive.workflow.model.meta.TemplateModel;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class WFMetaDaoImpl extends SqlMapClientDaoSupport
  implements WFMetaDao
{
  public TemplateModel getTemplateById(String stateId, Long id)
  {
    return (TemplateModel)getSqlMapClientTemplate().queryForObject("WFEngine.template_query_byId", id);
  }

  public List getNodeListByTemplate(String stateId, Long templateId) {
    return null;
  }

  public List getNodestateByNodeId(String stateId, Long nodeId) {
    return null;
  }

  public List getLinkListByTemplate(String stateId, Long templateId) {
    return null;
  }

  public List getLinkstateByLinkId(String stateId, Long linkId) {
    return null;
  }

  public List getBindVariable(Long templateId) {
    return null;
  }
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dao.impl.WFMetaDaoImpl
 * JD-Core Version:    0.6.0
 */