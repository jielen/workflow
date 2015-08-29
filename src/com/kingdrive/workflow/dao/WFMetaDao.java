package com.kingdrive.workflow.dao;

import com.kingdrive.workflow.model.meta.TemplateModel;
import java.util.List;

public abstract interface WFMetaDao
{
  public abstract TemplateModel getTemplateById(String paramString, Long paramLong);

  public abstract List getNodeListByTemplate(String paramString, Long paramLong);

  public abstract List getNodestateByNodeId(String paramString, Long paramLong);

  public abstract List getLinkListByTemplate(String paramString, Long paramLong);

  public abstract List getLinkstateByLinkId(String paramString, Long paramLong);

  public abstract List getBindVariable(Long paramLong);
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dao.WFMetaDao
 * JD-Core Version:    0.6.0
 */