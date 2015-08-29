 package com.kingdrive.workflow.utils;
 
 import com.kingdrive.workflow.graph.WorkflowGraph;
 import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
 import com.kingdrive.workflow.model.runtime.ActionModel;
 import com.kingdrive.workflow.model.runtime.CurrentTaskModel;
 import com.kingdrive.workflow.model.runtime.TraceInfoModel;
 import java.awt.Color;
import java.awt.geom.Rectangle2D;
 import java.awt.geom.Rectangle2D.Double;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Hashtable;
 import java.util.List;
 import java.util.Map;
 import javax.swing.ImageIcon;
 import org.jgraph.JGraph;
 import org.jgraph.graph.AttributeMap;
 import org.jgraph.graph.ConnectionSet;
 import org.jgraph.graph.DefaultEdge;
 import org.jgraph.graph.DefaultGraphCell;
 import org.jgraph.graph.DefaultGraphModel;
 import org.jgraph.graph.GraphConstants;
 import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.util.ParallelEdgeRouter;
 
 public class JGraphUtil
 {
	  private static String TASK_ICON_PATH = "/com/kingdrive/workflow/resource/user_task.jpg";

	  private static String COMMIT_ICON_PATH = "/com/kingdrive/workflow/resource/user_commit.jpg";

	  private static String UNTREAD_ICON_PATH = "/com/kingdrive/workflow/resource/user_untread.jpg";

	  private static String EXTEND_ICON_PATH = "/com/kingdrive/workflow/resource/extend.jpg";

	  private static int DISTANCE_X = 260;
 
   public JGraph assembleJGraph(TraceInfoModel traceInfo, int offsetx, int offsety) throws IOException {
     Map attributes = new Hashtable();
     List cellList = new ArrayList();
     List vertexList = new ArrayList();
     List edgeList = new ArrayList();
     ConnectionSet cs = new ConnectionSet();
 
     ActionModel action = null;
     DefaultGraphCell vertex = null;
     DefaultEdge edge = null;
     List actionList = traceInfo.getDoneActionList();
     int positionx = 0;
     int positiony = 0;
     for (int i = 0; i < actionList.size(); i++) {
       action = (ActionModel)actionList.get(i);
       Map attribute = getDefaultCommitAttribute();
       positionx = offsetx + DISTANCE_X * i;
       positiony = offsety;
       GraphConstants.setBounds(attribute, new Rectangle2D.Double(positionx, positiony, 220.0D, 160.0D));
       vertex = createVertex(action);
       attributes.put(vertex, attribute);
       vertexList.add(vertex);
     }
 
     for (int i = 0; i < vertexList.size() - 1; i++) {
       action = (ActionModel)actionList.get(i);
       edge = createEdge(action.getActionName());
       attributes.put(edge, getDefaultEdgeAttribute());
       edgeList.add(edge);
       DefaultGraphCell source = (DefaultGraphCell)vertexList.get(i);
       DefaultGraphCell target = (DefaultGraphCell)vertexList.get(i + 1);
       cs.connect(edge, source.getFirstChild(), target.getFirstChild());
     }
 
     List todoActionList = traceInfo.getTodoAction();
     if (todoActionList.size() > 0) {
       DefaultGraphCell todoVertex = createVertex(todoActionList);
       Map attribute = getDefaultTaskAttribute();
       positionx += DISTANCE_X;
       GraphConstants.setBounds(attribute, new Rectangle2D.Double(positionx, positiony, 220.0D, 140.0D));
       attributes.put(todoVertex, attribute);
       if (vertexList.size() > 0) {
         DefaultGraphCell lastActionCell = (DefaultGraphCell)vertexList.get(vertexList.size() - 1);
         vertexList.add(todoVertex);
         edge = createEdge("");
         attributes.put(edge, getDefaultEdgeAttribute());
         edgeList.add(edge);
         cs.connect(edge, lastActionCell.getFirstChild(), todoVertex.getFirstChild());
       } else {
         vertexList.add(todoVertex);
       }
     }
     ActionHistoryModel untreadAction = traceInfo.getUntreadAction();
     if (untreadAction != null) {
       DefaultGraphCell lastActionCell = (DefaultGraphCell)vertexList.get(vertexList.size() - 1);
       DefaultGraphCell untreadVertex = createVertex(untreadAction);
       Map attribute = getDefaultUntreadAttribute();
       positionx += DISTANCE_X;
       GraphConstants.setBounds(attribute, new Rectangle2D.Double(positionx, positiony, 220.0D, 140.0D));
       attributes.put(untreadVertex, attribute);
       vertexList.add(untreadVertex);
 
       edge = createEdge("");
       attributes.put(edge, getDefaultEdgeAttribute());
       edgeList.add(edge);
       cs.connect(edge, lastActionCell.getFirstChild(), untreadVertex.getFirstChild());
 
       edge = createEdge("");
       attribute = getDefaultEdgeAttribute();
       GraphConstants.setLineColor(attribute, Color.red);
       attributes.put(edge, attribute);
       edgeList.add(edge);
       cs.connect(edge, untreadVertex.getFirstChild(), lastActionCell.getFirstChild());
     }
 
     cellList.addAll(vertexList);
     cellList.addAll(edgeList);
 
     if (("9".equals(traceInfo.getStatus())) && (traceInfo.getExtendActionList().size() > 0)) {
       DefaultGraphCell lastCell = (DefaultGraphCell)vertexList.get(vertexList.size() - 1);
       List extendList = traceInfo.getExtendActionList();
       DefaultGraphCell extendCell = null;
       for (int i = 0; i < extendList.size(); i++) {
         extendCell = new DefaultGraphCell(extendList.get(i));
         extendCell.addPort();
         Map attribute = getDefaultExtendNodeAttribute();
         positionx += DISTANCE_X;
         GraphConstants.setBounds(attribute, new Rectangle2D.Double(positionx, positiony, 220.0D, 140.0D));
         attributes.put(extendCell, attribute);
         cellList.add(extendCell);
         edge = createEdge("");
         attribute = getDefaultEdgeAttribute();
         attributes.put(edge, attribute);
         cellList.add(edge);
         cs.connect(edge, lastCell.getFirstChild(), extendCell.getFirstChild());
         lastCell = extendCell;
       }
     }
 
     JGraph graph = new WorkflowGraph(new DefaultGraphModel());
     graph.getGraphLayoutCache().insert(cellList.toArray(), attributes, cs, null, null);
     return graph;
   }
 
   private DefaultGraphCell createVertex(ActionModel action) {
     DefaultGraphCell vertex = new DefaultGraphCell(action);
     vertex.addPort();
     return vertex;
   }
 
   private DefaultGraphCell createVertex(ActionHistoryModel action) {
     DefaultGraphCell vertex = new DefaultGraphCell(action);
     vertex.addPort();
     return vertex;
   }
 
   private DefaultGraphCell createVertex(List todoActionList) {
     CurrentTaskModel currentTask = new CurrentTaskModel();
     CurrentTaskModel temp = null;
     StringBuffer executors = new StringBuffer();
     StringBuffer nodeName = new StringBuffer();
     for (int i = 0; i < todoActionList.size(); i++) {
       temp = (CurrentTaskModel)todoActionList.get(i);
       nodeName.append(temp.getNodeName());
       executors.append(temp.getExecutorName());
       if (i < todoActionList.size() - 1) {
         nodeName.append(",");
         executors.append(",");
       }
     }
     currentTask.setNodeName(nodeName.toString());
     currentTask.setExecutorName(executors.toString());
     DefaultGraphCell vertex = new DefaultGraphCell(currentTask);
     vertex.addPort();
     return vertex;
   }
 
   private DefaultEdge createEdge(Object userObject) {
     DefaultEdge edge = new DefaultEdge();
     edge.addPort();
     return edge;
   }
 
   private Map getDefaultTaskAttribute() throws IOException {
     ImageIcon icon = DialogUtil.crateIcon(TASK_ICON_PATH);
     AttributeMap m = new AttributeMap();
     GraphConstants.setOpaque(m, true);
     GraphConstants.setIcon(m, icon);
 
     return m;
   }
 
   private Map getDefaultCommitAttribute() throws IOException {
     ImageIcon icon = DialogUtil.crateIcon(COMMIT_ICON_PATH);
     AttributeMap m = new AttributeMap();
     GraphConstants.setOpaque(m, true);
     GraphConstants.setIcon(m, icon);
 
     return m;
   }
 
   private Map getDefaultUntreadAttribute() throws IOException {
     ImageIcon icon = DialogUtil.crateIcon(UNTREAD_ICON_PATH);
     AttributeMap m = new AttributeMap();
 
     GraphConstants.setOpaque(m, true);
     GraphConstants.setIcon(m, icon);
 
     return m;
   }
 
   private Map getDefaultExtendNodeAttribute() throws IOException {
     ImageIcon icon = DialogUtil.crateIcon(EXTEND_ICON_PATH);
     AttributeMap m = new AttributeMap();
     GraphConstants.setOpaque(m, true);
     GraphConstants.setIcon(m, icon);
     return m;
   }
 
   private Map getDefaultEdgeAttribute() {
     AttributeMap m = new AttributeMap();
     GraphConstants.setLineStyle(m, 12);
     GraphConstants.setLineColor(m, Color.GREEN);
     GraphConstants.setLineBegin(m, 0);
     GraphConstants.setLineEnd(m, 1);
     GraphConstants.setRouting(m, ParallelEdgeRouter.getSharedInstance());
     GraphConstants.setEndFill(m, true);
     return m;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.utils.JGraphUtil
 * JD-Core Version:    0.6.0
 */