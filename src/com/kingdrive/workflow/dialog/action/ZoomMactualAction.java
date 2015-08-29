 package com.kingdrive.workflow.dialog.action;
 
 import com.kingdrive.workflow.utils.DialogUtil;
 import java.awt.event.ActionEvent;
 import java.io.PrintStream;
 import javax.swing.AbstractAction;
 import javax.swing.ImageIcon;
 import org.jgraph.JGraph;
 
 public class ZoomMactualAction extends AbstractAction
 {
   private static String IMAGE_PATH = "kingdriveresource/zoomactual.gif";
   private JGraph jg;
 
   public ZoomMactualAction(String name, JGraph jg)
   {
     super(name);
     ImageIcon icon = null;
     try {
       icon = DialogUtil.crateIcon(IMAGE_PATH);
     } catch (Exception ex) {
       System.out.println(ex);
     }
     putValue("SmallIcon", icon);
     this.jg = jg;
   }
 
   public void actionPerformed(ActionEvent e)
   {
     this.jg.setScale(1.0D);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.action.ZoomMactualAction
 * JD-Core Version:    0.6.0
 */