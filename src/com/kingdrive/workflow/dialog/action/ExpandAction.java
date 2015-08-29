 package com.kingdrive.workflow.dialog.action;
 
 import com.kingdrive.workflow.utils.DialogUtil;
 import java.awt.Dimension;
 import java.awt.Toolkit;
 import java.awt.Window;
 import java.awt.event.ActionEvent;
 import java.io.PrintStream;
 import javax.swing.AbstractAction;
 import javax.swing.ImageIcon;
 
 public class ExpandAction extends AbstractAction
 {
   private static String IMAGE_PATH = "kingdriveresource/expand.gif";
   private Window w;
 
   public ExpandAction(String name, Window window)
   {
     super(name);
     ImageIcon icon = null;
     try {
       icon = DialogUtil.crateIcon(IMAGE_PATH);
     } catch (Exception ex) {
       System.out.println(ex);
     }
     putValue("SmallIcon", icon);
     this.w = window;
   }
 
   public void actionPerformed(ActionEvent e)
   {
     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     this.w.setBounds(0, 0, screenSize.width - 40, screenSize.height - 40);
     this.w.invalidate();
     this.w.validate();
     this.w.repaint();
     DialogUtil util = new DialogUtil();
     util.centerWindow(this.w);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.action.ExpandAction
 * JD-Core Version:    0.6.0
 */