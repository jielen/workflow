 package com.kingdrive.workflow.expression;
 
 import bsh.EvalError;
 import bsh.Interpreter;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 public class ExpressionEvaluator
 {
   public static Object calculate(String expression, Map context)
     throws EvalError
   {
     String buffer = expression;
     Boolean result = Boolean.FALSE;
     Interpreter interpreter = new Interpreter();
     Pattern pattern = null;
     Matcher matcher = null;
     if (context != null) {
       Iterator iterator = context.entrySet().iterator();
       while (iterator.hasNext()) {
         Map.Entry entry = (Map.Entry)iterator.next();
         String key = entry.getKey().toString();
         pattern = Pattern.compile(key);
         matcher = pattern.matcher(buffer);
         Object value = entry.getValue();
         if ((value instanceof Number))
           buffer = matcher.replaceAll(value.toString());
         else if ((value instanceof String)) {
           buffer = matcher.replaceAll("\"" + value.toString() + "\"");
         }
       }
       result = (Boolean)interpreter.eval(buffer);
     }
     return result;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.expression.ExpressionEvaluator
 * JD-Core Version:    0.6.0
 */