 package com.kingdrive.workflow.dialog;
 
 import java.awt.BorderLayout;
 import java.awt.Container;
 import java.awt.FlowLayout;
 import java.awt.Frame;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JEditorPane;
 import javax.swing.JPanel;
 
 public class CommentDialog extends JDialog
   implements ActionListener
 {
   private static final long serialVersionUID = 7714089677620182874L;
   private JEditorPane editor;
   private JButton confirmButton;
   private JButton cancelButton;
   private String comment = "";
 
   public CommentDialog()
   {
     init();
   }
 
   public CommentDialog(Frame owner) {
     super(owner);
     init();
   }
 
   public CommentDialog(Frame owner, boolean modal) {
     super(owner, modal);
     init();
   }
 
   public String getComment()
   {
     return this.comment;
   }
 
   public void setComment(String comment) {
     this.comment = comment;
   }
 
   private void init() {
     getContentPane().setLayout(new BorderLayout());
     this.editor = new JEditorPane();
     getContentPane().add(this.editor, "Center");
     JPanel panel = new JPanel();
     panel.setLayout(new FlowLayout());
     this.confirmButton = new JButton("确定");
     this.confirmButton.setActionCommand("confirm");
     this.confirmButton.addActionListener(this);
     this.cancelButton = new JButton("取消");
     this.cancelButton.setActionCommand("cancel");
     this.cancelButton.addActionListener(this);
     panel.add(this.confirmButton);
     panel.add(this.cancelButton);
     getContentPane().add(panel, "South");
     setSize(400, 400);
     setDefaultCloseOperation(2);
     setVisible(true);
   }
 
   public void actionPerformed(ActionEvent event) {
     String command = event.getActionCommand();
     if (command.equals("confirm")) {
       this.comment = this.editor.getText();
       dispose();
     }
     if (command.equals("cancel"))
       dispose();
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.CommentDialog
 * JD-Core Version:    0.6.0
 */