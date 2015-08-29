 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import com.kingdrive.workflow.utils.SpringUtil;
 import java.io.PrintStream;
 import java.util.List;
 import junit.framework.TestCase;
 
 public class TestCurrentTask extends TestCase
 {
   private static WFRuntimeService service = (WFRuntimeService)
     SpringUtil.getBean("WFRuntimeService");
 
   private static CurrentTaskModel task = new CurrentTaskModel();
 
   protected void setUp() throws Exception {
     super.setUp();
   }
 
   public void testCreateCurrentTask() {
     task.setInstanceId(new Long(1002L));
     task.setNodeId(new Long(222L));
     task.setExecutor("bobo");
     task.setDelegationId(new Long(1L));
     task.setOwner("bobo");
     task.setCreator("bobo");
     service.createCurrentTask(task);
   }
 
   public void testGetCurrentTask()
   {
     List task = service.getCurrentTaskByUser(new Long(1002L), "bobo");
     System.err.println(task.size());
   }
 
   public void testGetCurrentTask1() {
     CurrentTaskModel model = service.getCurrentTaskByNodeUser(new Long(1002L), 
       new Long(222L), "bobo");
     System.err.println(model.getExecutor());
   }
 
   public void testRemoveCurrentTask() {
     service.removeCurrentTaskById(task.getCurrentTaskId());
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestCurrentTask
 * JD-Core Version:    0.6.0
 */