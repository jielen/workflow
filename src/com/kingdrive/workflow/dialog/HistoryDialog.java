 package com.kingdrive.workflow.dialog;
 
 import java.awt.BorderLayout;
 import java.awt.Container;
 import java.util.List;
 import javax.swing.JDialog;
 import javax.swing.JScrollPane;
 import javax.swing.JTable;
 
 public class HistoryDialog extends JDialog
 {
   private static final long serialVersionUID = -432914577795804103L;
   private String[] columnNames = { "节点", "活动", "执行人", "执行时间", "意见" };
 
   private String[] propNames = { "nodeName", "actionName", "executorName", "executeTime", "description" };
   private List rowList;
   private JTable table;
 
   public HistoryDialog(List historyList)
   {
     setModal(false);
     this.rowList = historyList;
     getContentPane().setLayout(new BorderLayout());
     this.table = new JTable(new ObjectTableModel(this.columnNames, this.propNames, this.rowList));
     JScrollPane pane = new JScrollPane(this.table);
     getContentPane().add(pane, "Center");
     setDefaultCloseOperation(2);
     setSize(600, 400);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.HistoryDialog
 * JD-Core Version:    0.6.0
 */