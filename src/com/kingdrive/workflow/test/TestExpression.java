 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.expression.ExpressionEvaluator;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.Map;
 import junit.framework.TestCase;
 
 public class TestExpression extends TestCase
 {
   public void testExp()
   {
     try
     {
       String expression = "\"YSDW\"==svPoCode&&((\"0201\"==PAYTYPE_CODE&&(\"03\"==PAYOUT_CODE||\"04\"==PAYOUT_CODE||\"06\"==PAYOUT_CODE))||\"0202\"==PAYTYPE_CODE)";
       Map context = new HashMap();
       context.put("PAYOUT_CODE", "03");
       context.put("PAYTYPE_CODE", "0201");
       context.put("svPoCode", "YSDW");
       Object result = ExpressionEvaluator.calculate(expression, context);
       System.out.println(result);
     } catch (Exception ex) {
       System.out.println(ex);
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestExpression
 * JD-Core Version:    0.6.0
 */