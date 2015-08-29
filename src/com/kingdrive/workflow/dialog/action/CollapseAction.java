 package com.kingdrive.workflow.dialog.action;
 
 import com.kingdrive.workflow.utils.DialogUtil;
 import java.awt.Dimension;
 import java.awt.Window;
 import java.awt.event.ActionEvent;
 import java.io.PrintStream;
 import javax.swing.AbstractAction;
 import javax.swing.ImageIcon;
 
 public class CollapseAction extends AbstractAction
 {
   private static String IMAGE_PATH = "kingdriveresource/collapse.gif";
   private Window w;
 
   public CollapseAction(String name, Window window)
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
     DialogUtil util = new DialogUtil();
     Dimension dimension = new Dimension(800, 550);
     this.w.setSize(dimension);
     this.w.invalidate();
     this.w.validate();
     this.w.repaint();
     util.centerWindow(this.w);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.action.CollapseAction
 * JD-Core Version:    0.6.0
 */