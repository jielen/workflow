 package com.kingdrive.workflow.service.impl.db;
 
 import com.kingdrive.workflow.dao.WFMetaDao;
 import com.kingdrive.workflow.model.meta.TemplateModel;
 import com.kingdrive.workflow.service.db.WFMetaService;
 import java.io.InputStream;
 import java.util.Map;
 import java.util.Properties;
 import org.springframework.core.CollectionFactory;
 
 public class WFMetaServiceImpl
   implements WFMetaService
 {
   public static Map templateCache = CollectionFactory.createConcurrentMapIfPossible(15);
   private WFMetaDao metaDao;
   private Properties config = new Properties();
 
   public WFMetaDao getMetaDao()
   {
     return this.metaDao;
   }
 
   public void setMetaDao(WFMetaDao metaDao) {
     this.metaDao = metaDao;
   }
 
   public synchronized TemplateModel getTemplate(Long templateId)
   {
     initConfig();
     boolean isCache = Boolean.valueOf((String)this.config.get("isCache")).booleanValue();
     TemplateModel template = null;
     if (isCache) {
       template = (TemplateModel)templateCache.get(templateId);
     }
     if (template == null) {
       template = this.metaDao.getTemplateById("WFEngine.template_query_byId", templateId);
       template.assembleGraph();
       templateCache.put(templateId, template);
     }
     return template;
   }
 
   private void initConfig() {
     try {
       InputStream stream = getClass().getClassLoader().getResourceAsStream(
         "workflowConfig.properties");
       this.config.load(stream);
     } catch (Exception ex) {
       throw new RuntimeException(ex);
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.service.impl.db.WFMetaServiceImpl
 * JD-Core Version:    0.6.0
 */