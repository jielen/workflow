 package com.kingdrive.workflow.utils;
 
 import org.springframework.context.ApplicationContext;
 import org.springframework.context.support.ClassPathXmlApplicationContext;
 
 public class SpringUtil
 {
   private static ApplicationContext context = null;
 
   static {
     context = new ClassPathXmlApplicationContext("applicationContext*.xml");
   }
 
   public static Object getBean(String beanName) {
     return context.getBean(beanName);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.utils.SpringUtil
 * JD-Core Version:    0.6.0
 */