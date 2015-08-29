 package com.kingdrive.workflow.utils;
 
 import java.awt.Container;
 import java.awt.Dimension;
 import java.awt.Toolkit;
 import java.awt.Window;
 import java.io.IOException;
 import java.io.PrintStream;
 import javax.imageio.ImageIO;
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 import javax.swing.JWindow;
 
 public class DialogUtil
 {
   public Window createSplashWindow(String path)
   {
     JWindow window = new JWindow();
     try {
       ImageIcon icon = crateIcon(path);
       JLabel label = new JLabel(icon);
       window.getContentPane().add(label);
       window.setSize(400, 300);
     } catch (Exception ex) {
       System.out.println(ex.getMessage());
     }
     return window;
   }
 
   public void centerWindow(Window w) {
     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     Dimension frameSize = w.getSize();
     w.setLocation(screenSize.width / 2 - frameSize.width / 2, 
    	      screenSize.height / 2 - frameSize.height / 2);
   }
 
   public static ImageIcon crateIcon(String path) throws IOException {
     ImageIcon icon = new ImageIcon(ImageIO.read(DialogUtil.class
       .getResource(path)));
     return icon;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.utils.DialogUtil
 * JD-Core Version:    0.6.0
 */