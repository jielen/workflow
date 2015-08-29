 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.model.runtime.ActionModel;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import com.kingdrive.workflow.utils.SpringUtil;
 import java.io.PrintStream;
 import junit.framework.TestCase;
 
 public class TestAction extends TestCase
 {
   private static WFRuntimeService service = (WFRuntimeService)SpringUtil.getBean("WFRuntimeService");
   private static ActionModel action = new ActionModel();
 
   protected void setUp() throws Exception {
     super.setUp();
   }
 
   public void testCreateAction() {
     action.setInstanceId(new Long(12345L));
     action.setNodeId(new Long(12345L));
     action.setActionName("dfakd");
     action.setExecutor("df");
     action.setExecuteTime("sd");
     action.setDescription("sd");
     action.setOwner("Sd");
     action.setLimitExecuteTime("dfd");
     service.createAction(action);
   }
 
   public void testGetActionCountByNodeLink() {
     Long count = service.getActionCountByNodeLink(new Long(12345L), new Long(12345L), "dfakd");
     System.err.print("count is : " + count);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestAction
 * JD-Core Version:    0.6.0
 */