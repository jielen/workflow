 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import com.kingdrive.workflow.utils.SpringUtil;
 import java.io.PrintStream;
 import java.util.List;
 import junit.framework.TestCase;
 
 public class TestExecutor extends TestCase
 {
   private static WFRuntimeService service = (WFRuntimeService)SpringUtil.getBean("WFRuntimeService");
 
   public void testManagerRelation() {
     List result = service.getSuperStaffSet("6071031000", "10061", new Long(2009L));
     System.out.println(result.get(0));
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestExecutor
 * JD-Core Version:    0.6.0
 */