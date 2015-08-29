 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.WFEngine;
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.model.TableData;
 import com.kingdrive.workflow.utils.SpringUtil;
 import junit.framework.TestCase;
 import org.apache.log4j.Logger;
 
 public class TestCallback extends TestCase
 {
   private static WFEngine service = (WFEngine)SpringUtil.getBean("WFEngine");
   private static Logger logger = Logger.getLogger(TestCallback.class);
 
   public void testCallback() {
     try {
       WorkflowContext context = new WorkflowContext();
       TableData entity = new TableData();
       entity.setName("AM_BPAE");
       context.setEntityData(entity);
       context.setExecutor("003");
       context.setInstanceId(new Long(42L));
       context.setComment("this is bo's callback");
       service.callback(context);
     } catch (Exception ex) {
       logger.error(ex);
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestCallback
 * JD-Core Version:    0.6.0
 */