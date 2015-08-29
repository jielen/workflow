 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.WFEngine;
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.model.TableData;
 import com.kingdrive.workflow.utils.SpringUtil;
 import java.io.PrintStream;
 import junit.framework.TestCase;
 import org.apache.log4j.Logger;
 
 public class TestNewCommit extends TestCase
 {
   private static WFEngine service = (WFEngine)
     SpringUtil.getBean("WFEngine");
 
   private static Logger logger = Logger.getLogger(TestNewCommit.class);
 
   protected void setUp() throws Exception {
     super.setUp();
   }
 
   public void testNewCommit() {
     try {
       WorkflowContext context = new WorkflowContext();
       TableData entity = new TableData();
       entity.setName("DP_EDIT_AUDIT");
       entity.setTitleFieldValue("liuboTest_9");
       context.setEntityData(entity);
       context.setExecutor("cp_sj");
       context.setInstanceId(new Long(-2000291));
       context.setComment("this is bo's test");
       context.setVariable("svCoLevel", new Long(2L));
 
       service.newCommit(context);
       System.out.println("adfads");
     } catch (Exception ex) {
       ex.printStackTrace();
       logger.error(ex);
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestNewCommit
 * JD-Core Version:    0.6.0
 */