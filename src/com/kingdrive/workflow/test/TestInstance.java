 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.model.runtime.InstanceModel;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import com.kingdrive.workflow.utils.SpringUtil;
 import junit.framework.TestCase;
 
 public class TestInstance extends TestCase
 {
   private static WFRuntimeService service = (WFRuntimeService)
     SpringUtil.getBean("WFRuntimeService");
 
   private static InstanceModel instance = new InstanceModel();
 
   protected void setUp() throws Exception {
     super.setUp();
   }
 
   public void testFinishInstance()
   {
     InstanceModel instance = new InstanceModel();
     instance.setInstanceId(new Long(2000627L));
     instance.setStatus(new Long(9L));
     instance.setEndTime("adfad");
     service.updateInstance(instance);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestInstance
 * JD-Core Version:    0.6.0
 */