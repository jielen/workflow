 package com.kingdrive.workflow.utils;
 
 import com.kingdrive.workflow.model.TableData;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Comparator;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 public class BusinessJuniorExp
   implements Comparator
 {
   public static final String BIGGER = "b";
   public static final String SMALLER = "s";
   public static final String EQUAL = "eq";
   public static final String BIGGER_EQUAL = "beq";
   public static final String SMALLER_EQUAL = "seq";
   public static final String NOT_EQUAL = "!eq";
   public static final String LIKE = "like";
   public static final String NOT_LIKE = "!like";
   public static final String TBIGGER = "tb";
   public static final String TSMALLER = "ts";
   public static final String TEQUAL = "teq";
   public static final String TBIGGER_EQUAL = "tbeq";
   public static final String TSMALLER_EQUAL = "tseq";
   public static final String TNOT_EQUAL = "!teq";
   public static final String TLIKE = "tlike";
   public static final String TNOT_LIKE = "!tlike";
   public static final String XBIGGER = "xb";
   public static final String XSMALLER = "xs";
   public static final String XEQUAL = "xeq";
   public static final String XBIGGER_EQUAL = "xbeq";
   public static final String XSMALLER_EQUAL = "xseq";
   public static final String XNOT_EQUAL = "!xeq";
   public static final String XLIKE = "xlike";
   public static final String XNOT_LIKE = "!xlike";
   public static final String NBIGGER = "nb";
   public static final String NSMALLER = "ns";
   public static final String NEQUAL = "neq";
   public static final String NBIGGER_EQUAL = "nbeq";
   public static final String NSMALLER_EQUAL = "nseq";
   public static final String NNOT_EQUAL = "!neq";
   public static final String NLIKE = "nlike";
   public static final String NNOT_LIKE = "!nlike";
   private String para1 = "";
 
   private String para2 = "";
 
   private String symbol = null;
 
   public BusinessJuniorExp(String exp)
     throws IllegalArgumentException
   {
     if (exp == null)
       throw new IllegalArgumentException("无法识别的表达式[" + exp + "]");
     Pattern p = Pattern.compile("^(.*)\\(\"(.*)\"\\,\"(.*)\"\\)$");
     Matcher m = p.matcher(exp.trim());
     if (m.find()) {
       this.symbol = m.group(1);
       this.para1 = m.group(2);
       this.para2 = m.group(3);
     }
   }
 
   public BusinessJuniorExp(String para1, String para2, String symbol)
   {
     this.para1 = para1;
     this.para2 = para2;
     this.symbol = symbol;
   }
 
   public boolean parse(Object val) {
     if ((val instanceof String)) {
       String value = (String)val;
       if ("b".equalsIgnoreCase(this.symbol))
         return bigger(value);
       if ("beq".equalsIgnoreCase(this.symbol))
         return (bigger(value)) || (equal(value));
       if ("s".equalsIgnoreCase(this.symbol))
         return smaller(value);
       if ("seq".equalsIgnoreCase(this.symbol))
         return (smaller(value)) || (equal(value));
       if ("eq".equalsIgnoreCase(this.symbol))
         return equal(value);
       if ("!eq".equalsIgnoreCase(this.symbol))
         return !equal(value);
       if ("like".equalsIgnoreCase(this.symbol))
         return like(value);
       if ("!like".equalsIgnoreCase(this.symbol)) {
         return !like(value);
       }
       return false;
     }
 
     if ((val instanceof ArrayList)) {
       ArrayList value = (ArrayList)val;
 
       if ("tb".equalsIgnoreCase(this.symbol))
         return bigger(total(value));
       if ("tbeq".equalsIgnoreCase(this.symbol))
         return (bigger(total(value))) || (equal(total(value)));
       if ("ts".equalsIgnoreCase(this.symbol))
         return smaller(total(value));
       if ("tseq".equalsIgnoreCase(this.symbol))
         return (smaller(total(value))) || (equal(total(value)));
       if ("teq".equalsIgnoreCase(this.symbol))
         return equal(total(value));
       if ("!teq".equalsIgnoreCase(this.symbol))
         return !equal(total(value));
       if ("tlike".equalsIgnoreCase(this.symbol))
         return like(total(value));
       if ("!tlike".equalsIgnoreCase(this.symbol)) {
         return !like(total(value));
       }
       if ("xb".equalsIgnoreCase(this.symbol))
         return bigger(max(value));
       if ("xbeq".equalsIgnoreCase(this.symbol))
         return (bigger(max(value))) || (equal(max(value)));
       if ("xs".equalsIgnoreCase(this.symbol))
         return smaller(max(value));
       if ("xseq".equalsIgnoreCase(this.symbol))
         return (smaller(max(value))) || (equal(max(value)));
       if ("xeq".equalsIgnoreCase(this.symbol))
         return equal(max(value));
       if ("!xeq".equalsIgnoreCase(this.symbol))
         return !equal(max(value));
       if ("xlike".equalsIgnoreCase(this.symbol))
         return like(max(value));
       if ("!xlike".equalsIgnoreCase(this.symbol)) {
         return !like(max(value));
       }
       if ("nb".equalsIgnoreCase(this.symbol))
         return bigger(min(value));
       if ("nbeq".equalsIgnoreCase(this.symbol))
         return (bigger(min(value))) || (equal(min(value)));
       if ("ns".equalsIgnoreCase(this.symbol))
         return smaller(min(value));
       if ("nseq".equalsIgnoreCase(this.symbol))
         return (smaller(min(value))) || (equal(min(value)));
       if ("neq".equalsIgnoreCase(this.symbol))
         return equal(min(value));
       if ("!neq".equalsIgnoreCase(this.symbol))
         return !equal(min(value));
       if ("nlike".equalsIgnoreCase(this.symbol))
         return like(min(value));
       if ("!nlike".equalsIgnoreCase(this.symbol)) {
         return !like(min(value));
       }
       return false;
     }
     return false;
   }
 
   public boolean parse(TableData data) {
     if (isCompoExpression()) {
       String[] atts = this.para1.split("[.]");
       Object value = null;
 
       if (atts.length == 1)
       {
         value = data.getName();
         return parse(value);
       }
 
       if (atts.length == 2) {
         String compoName = atts[0];
         if (!compoName.equals(data.getName())) {
           return false;
         }
         String fieldName = atts[1];
         value = data.getFieldValue(fieldName);
         return parse(value);
       }
       throw new RuntimeException("不支持子表");
     }
 
     return false;
   }
 
   public int compare(Object o1, Object o2)
   {
     float f1 = Float.parseFloat((String)o1);
     float f2 = Float.parseFloat((String)o2);
     if (f1 == f2)
       return 0;
     if (f2 < f1)
       return -1;
     return 1;
   }
 
   public Float max(ArrayList value)
   {
     if ((value != null) && (!value.isEmpty())) {
       Collections.sort(value, this);
       return new Float(String.valueOf(value.get(0)));
     }
     return null;
   }
 
   public Float min(ArrayList value)
   {
     if ((value != null) && (!value.isEmpty())) {
       Collections.sort(value, this);
       return new Float(String.valueOf(value.get(value.size() - 1)));
     }
     return null;
   }
 
   public boolean like(Object value)
   {
     if ((value instanceof String))
     {
       if (value != null) {
         return like((String)value, this.para2);
       }
     }
     return false;
   }
 
   public boolean like(String strSource, String strSub)
   {
     boolean result = false;
     String ER_MORE_CHAR = "\\w*";
     String ER_MORE_CHAR2 = "\\\\w*";
     String ER_ONE_CHAR = ".";
     String MORE_CHAR = "%";
     String ONE_CHAR = "\\?";
     String strPattern = "";
     if ((strSource == null) || (strSub == null)) {
       return false;
     }
     if (strSub.indexOf(MORE_CHAR) == -1) {
       strPattern = ER_MORE_CHAR + strSub + ER_MORE_CHAR;
       result = Pattern.matches(strPattern, strSource);
     } else {
       strPattern = strSub.replaceAll(MORE_CHAR, ER_MORE_CHAR2);
       if (strSub.indexOf(ONE_CHAR) == -1) {
         strPattern = strPattern.replaceAll(ONE_CHAR, ER_ONE_CHAR);
       }
       result = Pattern.matches(strPattern, strSource);
     }
     return result;
   }
 
   public boolean bigger(Object value)
   {
     float v = 0.0F;
     if ((value instanceof Float))
       v = ((Float)value).floatValue();
     else if ((value instanceof String))
       v = Float.parseFloat((String)value);
     else
       return false;
     float to = Float.parseFloat(this.para2);
 
     return v > to;
   }
 
   public boolean equal(Object v)
   {
     if ((v instanceof String)) {
       String value = (String)v;
       if (value != null) {
         if (value.trim().equals(this.para2)) {
           return true;
         }
 
         String[] para2Array = this.para2.split(",");
         if (para2Array.length > 1) {
           for (int i = 0; i < para2Array.length; i++) {
             if (value.trim().equals(para2Array[i]))
               return true;
           }
         }
       }
     }
     else if ((v instanceof Float)) {
       Float value = (Float)v;
       if ((value != null) && (value.floatValue() == Float.parseFloat(this.para2)))
         return true;
     }
     return false;
   }
 
   public boolean smaller(Object value)
   {
     float v = 0.0F;
     if ((value instanceof Float))
       v = ((Float)value).floatValue();
     else if ((value instanceof String))
       v = Float.parseFloat((String)value);
     else
       return false;
     float to = Float.parseFloat(this.para2);
 
     return v < to;
   }
 
   public Float total(ArrayList value)
   {
     float t = 0.0F;
     if ((value != null) && (value.size() > 0)) {
       for (int i = 0; i < value.size(); i++) {
         float v = Float.parseFloat((String)value.get(i));
         t += v;
       }
       return new Float(t);
     }
     return null;
   }
 
   public boolean isCompoExpression()
   {
     return true;
   }
 
   public boolean isSysExpression()
   {
     return false;
   }
 
   public String getPara1()
   {
     return this.para1;
   }
 
   public void setPara1(String para1)
   {
     this.para1 = para1;
   }
 
   public String getPara2()
   {
     return this.para2;
   }
 
   public void setPara2(String para2)
   {
     this.para2 = para2;
   }
 
   public String getSymbol()
   {
     return this.symbol;
   }
 
   public void setSymbol(String symbol)
   {
     this.symbol = symbol;
   }
 
   public static void main(String[] args) {
     ArrayList a = new ArrayList();
     a.add("1");
     a.add("8");
     a.add("-6.3");
     a.add("12.7");
     BusinessJuniorExp exp = new BusinessJuniorExp("teq(\"AS_COMPANY.COCODE\",\"12.7\")");
     System.out.println(exp.parse(a));
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.utils.BusinessJuniorExp
 * JD-Core Version:    0.6.0
 */