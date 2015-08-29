 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.service.ResourceService;
 import com.kingdrive.workflow.utils.SpringUtil;
 import java.io.PrintStream;
 import java.util.Arrays;
 import java.util.List;
 import junit.framework.TestCase;
 import org.apache.log4j.Logger;
 
 public class GetGranterTest extends TestCase
 {
   private static ResourceService service = (ResourceService)SpringUtil.getBean("WFResourceService");
 
   private static Logger logger = Logger.getLogger(TestNewCommit.class);
 
   protected void setUp() throws Exception {
     super.setUp();
   }
 
   protected void tearDown() throws Exception {
     super.tearDown();
   }
 
   public void testGetGranter() throws Exception {
     Object[] ids = { "sa" };
     List result = service.getGrantedExecutor(Arrays.asList(ids));
     System.out.println(result.size());
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.GetGranterTest
 * JD-Core Version:    0.6.0
 */