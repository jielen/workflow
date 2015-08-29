 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.model.runtime.TraceInfoModel;
 import com.kingdrive.workflow.service.TraceService;
 import com.kingdrive.workflow.utils.SpringUtil;
 import java.io.PrintStream;
 import junit.framework.TestCase;
 
 public class TestTrace extends TestCase
 {
   private static TraceService service = (TraceService)
     SpringUtil.getBean("traceService");
 
   public void testTraceInfo()
   {
     TraceInfoModel traceInfo = service.getTraceInfo(new Long(31L));
     System.out.println(traceInfo.getCreator());
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestTrace
 * JD-Core Version:    0.6.0
 */