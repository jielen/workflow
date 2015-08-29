 package com.kingdrive.workflow.utils;
 
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 
 public class WFUtil
 {
   public static String getSysTime()
   {
     return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
   }
 
   public static String[] split2(String s, String separator)
   {
     if ((s == null) || (s.trim().length() == 0)) {
       return null;
     }
     String[] result = (String[])null;
     String[] tmp = new String[1000];
     int k = 0;
     int j = 0;
     for (int i = 0; i < s.length(); i++) {
       if (separator.equals(s.substring(i, i + 1))) {
         tmp[(k++)] = s.substring(j, i);
         j = i + 1;
       }
     }
     tmp[(k++)] = s.substring(j, s.length());
     if (k == 0) {
       return result;
     }
     result = new String[k];
     for (int i = 0; i < k; i++) {
       result[i] = tmp[i];
     }
     return result;
   }
 
   public static String getCurrentNd() {
     Calendar rightNow = Calendar.getInstance();
     return Integer.toString(rightNow.get(1));
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.utils.WFUtil
 * JD-Core Version:    0.6.0
 */