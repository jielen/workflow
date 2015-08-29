 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.model.meta.TemplateModel;
 import com.kingdrive.workflow.service.WFCommonService;
 import com.kingdrive.workflow.service.db.WFMetaService;
 import com.kingdrive.workflow.utils.SpringUtil;
 import java.io.PrintStream;
 import java.util.List;
 import junit.framework.TestCase;
 
 public class TestGetExecutedNodeListBetween extends TestCase
 {
   private static WFCommonService service = (WFCommonService)
     SpringUtil.getBean("WFCommonService");
 
   private static WFMetaService metaService = (WFMetaService)
     SpringUtil.getBean("WFMetaService");
 
   public void testGetBetweenNode()
   {
     try {
       TemplateModel template = metaService.getTemplate(new Long(2023L));
       List list = service.getExecutedNodeListBetween(template, 
         new Long(2033L), new Long(-1L), new Long(35L));
       if (list.size() > 0)
         System.out.println(list.get(0));
     }
     catch (Exception ex) {
       ex.printStackTrace();
       System.out.println(ex.getMessage());
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestGetExecutedNodeListBetween
 * JD-Core Version:    0.6.0
 */