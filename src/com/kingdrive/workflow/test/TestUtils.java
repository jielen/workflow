 package com.kingdrive.workflow.test;
 
 import java.io.File;
 import java.io.PrintStream;
 import java.lang.reflect.Method;
 import java.net.URI;
 import java.net.URL;
 import java.net.URLClassLoader;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 
 public class TestUtils
 {
   private static Method addURL = initAddMethod();
 
   private static URLClassLoader classloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
 
   private static Method initAddMethod() {  
	   return null;
   } 
   public static void loadClasspath() { List files = getJarFiles();
     for (Iterator iterator = files.iterator(); iterator.hasNext(); ) {
       String f = (String)iterator.next();
       loadClasspath(f);
     }
 
     List resFiles = getResFiles();
     for (Iterator iterator = resFiles.iterator(); iterator.hasNext(); ) {
       String r = (String)iterator.next();
       loadResourceDir(r);
     } }
 
   private static void loadClasspath(String filepath)
   {
     File file = new File(filepath);
     loopFiles(file);
   }
 
   private static void loadResourceDir(String filepath) {
     File file = new File(filepath);
     loopDirs(file);
   }
 
   private static void loopDirs(File file)
   {
     if (file.isDirectory()) {
       addURL(file);
       File[] tmps = file.listFiles();
       for (int i = 0; i < tmps.length; i++)
         loopDirs(tmps[i]);
     }
   }
 
   private static void loopFiles(File file)
   {
     if (file.isDirectory()) {
       File[] tmps = file.listFiles();
       for (int i = 0; i < tmps.length; i++) {
         loopFiles(tmps[i]);
       }
     }
     else if ((file.getAbsolutePath().endsWith(".jar")) || (file.getAbsolutePath().endsWith(".zip"))) {
       addURL(file);
     }
   }
 
   private static void addURL(File file)
   {
     try
     {
       addURL.invoke(classloader, new Object[] { file.toURI().toURL() });
     }
     catch (Exception localException)
     {
     }
   }
 
   private static List getJarFiles()
   {
     ArrayList list = new ArrayList();
     list.add("E:\\projects\\SHX_ST\\lib\\compile\\GK.jar");
     return list;
   }
 
   private static List getResFiles()
   {
     return new ArrayList();
   }
 
   public static void main(String[] args) {
     loadClasspath();
     try {
       Class cl = Class.forName("com.anyi.gk.core.SimpleSaveAction");
       Object ins = cl.newInstance();
       System.out.println(ins);
     }
     catch (ClassNotFoundException e) {
       e.printStackTrace();
     }
     catch (InstantiationException e) {
       e.printStackTrace();
     }
     catch (IllegalAccessException e) {
       e.printStackTrace();
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestUtils
 * JD-Core Version:    0.6.0
 */