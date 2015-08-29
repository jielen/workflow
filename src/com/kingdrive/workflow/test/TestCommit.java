 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.WFEngine;
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.model.TableData;
 import com.kingdrive.workflow.utils.SpringUtil;
 import junit.framework.TestCase;
 import org.apache.log4j.Logger;
 
 public class TestCommit extends TestCase
 {
   private static WFEngine service = (WFEngine)
     SpringUtil.getBean("WFEngine");
 
   private static Logger logger = Logger.getLogger(TestCommit.class);
 
   protected void setUp() throws Exception {
     super.setUp();
   }
 
   public void testCommit() {
     try {
       WorkflowContext context = new WorkflowContext();
       TableData entity = new TableData();
       entity.setName("DP_EDIT");
       entity.setFieldValue("D_ATTR3", "0201");
       context.setEntityData(entity);
       context.setExecutor("030010101");
       context.setInstanceId(new Long(2004435L));
       context.setComment("this is hello's commit");
       context.setVariable("ND", "2009");
 
       service.commit(context);
     } catch (Exception ex) {
       logger.error(ex);
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestCommit
 * JD-Core Version:    0.6.0
 */