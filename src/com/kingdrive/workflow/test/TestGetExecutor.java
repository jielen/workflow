 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.model.TableData;
 import com.kingdrive.workflow.model.meta.NodeModel;
 import com.kingdrive.workflow.model.meta.TemplateModel;
 import com.kingdrive.workflow.service.ResourceService;
 import com.kingdrive.workflow.service.db.WFMetaService;
 import com.kingdrive.workflow.utils.SpringUtil;
 import java.io.PrintStream;
 import java.util.List;
 import junit.framework.TestCase;
 
 public class TestGetExecutor extends TestCase
 {
   private static ResourceService service = (ResourceService)
     SpringUtil.getBean("WFResourceService");
 
   private static WFMetaService metaService = (WFMetaService)
     SpringUtil.getBean("WFMetaService");
 
   protected void setUp() throws Exception
   {
     super.setUp();
   }
 
   protected void tearDown() throws Exception {
     super.tearDown();
   }
 
   public void testGetExecutor()
   {
     try {
       TemplateModel template = metaService.getTemplate(new Long(10273L));
       NodeModel node = template.getNode(new Long(10278L));
       node.setExecutorRelation("3");
       WorkflowContext context = new WorkflowContext();
       TableData data = new TableData();
       data.setFieldValue("D_ATTR3", "0201");
       data.setName("DP_ADJUST_AUDIT");
       context.setEntityData(data);
       context.setExecutor("607103");
       context.setVariable("ND", "2009");
       context.setVariable("WF_POSITION_ID", "12700112259540539370");
       context.setVariable("WF_COMPANY_CODE", "6071031000");
       context.setVariable("WF_ORG_CODE", "0001");
       List list = service.getExecutorsByRelation(node, context);
       if (list.size() > 0)
         System.out.println(list.get(0));
       else
         System.out.println("it is empty");
     }
     catch (Exception ex) {
       System.err.println(ex.getLocalizedMessage());
       ex.printStackTrace();
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestGetExecutor
 * JD-Core Version:    0.6.0
 */