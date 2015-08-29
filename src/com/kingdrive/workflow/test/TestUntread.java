 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.WFEngine;
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.model.TableData;
 import com.kingdrive.workflow.utils.SpringUtil;
 import junit.framework.TestCase;
 import org.apache.log4j.Logger;
 
 public class TestUntread extends TestCase
 {
   private static WFEngine service = (WFEngine)SpringUtil.getBean("WFEngine");
   private static Logger logger = Logger.getLogger(TestUntread.class);
 
   public void testUntread() {
     try {
       WorkflowContext context = new WorkflowContext();
       TableData entity = new TableData();
       entity.setName("DP_EDIT_XX");
       context.setEntityData(entity);
       context.setExecutor("607103");
       context.setInstanceId(new Long(15126L));
       context.setNextNodeId(new Long(-1L));
       context.setComment("this is bo's test_untreadtest");
       service.untread(context);
     } catch (Exception ex) {
       logger.error(ex);
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestUntread
 * JD-Core Version:    0.6.0
 */