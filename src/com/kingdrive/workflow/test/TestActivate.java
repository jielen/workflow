 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.WFEngine;
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.model.TableData;
 import com.kingdrive.workflow.utils.SpringUtil;
 import junit.framework.TestCase;
 import org.apache.log4j.Logger;
 
 public class TestActivate extends TestCase
 {
   private static WFEngine service = (WFEngine)SpringUtil.getBean("WFEngine");
 
   private static Logger logger = Logger.getLogger(TestCommit.class);
 
   public void testDeactivate() {
     try {
       WorkflowContext context = new WorkflowContext();
       TableData data = new TableData();
       data.setName("AM_BPAE");
       context.setEntityData(data);
       context.setExecutor("003");
       context.setExeTime("20090414215858");
       context.setInstanceId(new Long(42L));
       service.activate(context);
     } catch (Exception ex) {
       logger.error(ex);
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestActivate
 * JD-Core Version:    0.6.0
 */