 package com.kingdrive.workflow.dialog;
 
 import com.kingdrive.workflow.dialog.action.ZoomInAction;
 import com.kingdrive.workflow.dialog.action.ZoomMactualAction;
 import com.kingdrive.workflow.dialog.action.ZoomOutAction;
 import com.kingdrive.workflow.model.runtime.TraceInfoModel;
 import com.kingdrive.workflow.utils.JGraphUtil;
 import java.awt.BorderLayout;
 import java.io.IOException;
 import javax.swing.JPanel;
 import javax.swing.JScrollPane;
 import javax.swing.JSplitPane;
 import javax.swing.JTable;
 import javax.swing.JToolBar;
 import javax.swing.ToolTipManager;
 import org.jgraph.JGraph;
 
 public class WFTracePanel extends JPanel
 {
   private String[] columnNames = { "节点", "活动", "执行人", "执行时间", "意见" };
 
   private String[] propNames = { "nodeName", "actionName", "executorName", 
     "executeTime", "description" };
   private JGraph jgraph;
   private TraceInfoModel traceInfoModel;
   private JToolBar toolBar;
   private JSplitPane splitPane;
   public String traceInfo;
 
   public WFTracePanel(TraceInfoModel traceInfo, JTable table)
   {
     setLayout(new BorderLayout());
     this.traceInfoModel = traceInfo;
     this.splitPane = new JSplitPane(0);
     this.splitPane.setOneTouchExpandable(true);
     this.splitPane.setDividerLocation(230);
     add(this.splitPane, "Center");
     initData(this.traceInfoModel);
     initGraph();
     initComponent();
     initTable(table);
   }
 
   private void initComponent() {
     try {
       this.toolBar = new JToolBar();
       this.toolBar.add(new ZoomMactualAction("zoommactual", this.jgraph));
       this.toolBar.add(new ZoomInAction("zoomin", this.jgraph));
       this.toolBar.add(new ZoomOutAction("zoomout", this.jgraph));
       this.toolBar.addSeparator();
 
       this.toolBar.setFloatable(false);
       add(this.toolBar, "North");
     } catch (Exception ex) {
       ex.printStackTrace();
     }
   }
 
   private void initData(TraceInfoModel traceInfoModel) {
     String str = "开始时间: " + traceInfoModel.getStartTime() + "  ";
     if ("1".equals(traceInfoModel.getStatus())) {
       str = str + "进行中......";
     }
     else if ("9".equals(traceInfoModel.getStatus())) {
       str = str + "结束时间: " + traceInfoModel.getEndTime();
     }
 
     this.traceInfo = str;
   }
 
   private void initGraph()
   {
     JGraphUtil util = new JGraphUtil();
     try {
       this.jgraph = util.assembleJGraph(this.traceInfoModel, 50, 20);
       this.jgraph.setEditable(false);
       ToolTipManager.sharedInstance().registerComponent(this.jgraph);
       this.splitPane.setTopComponent(new JScrollPane(this.jgraph));
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   private void initTable(JTable table) {
     JTable traceTable = table;
     if (traceTable == null) {
       traceTable = new JTable(
         new ObjectTableModel(this.columnNames, this.propNames, 
         this.traceInfoModel.getActionHistoryList()));
     }
     traceTable.setModel(
       new ObjectTableModel(this.columnNames, this.propNames, 
       this.traceInfoModel.getActionHistoryList()));
 
     this.splitPane.setBottomComponent(new JScrollPane(traceTable));
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.WFTracePanel
 * JD-Core Version:    0.6.0
 */