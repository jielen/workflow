 package com.kingdrive.workflow.graph;
 
 import com.kingdrive.workflow.model.runtime.ActionHistoryModel;
 import com.kingdrive.workflow.model.runtime.ActionModel;
 import java.awt.event.MouseEvent;
 import org.jgraph.JGraph;
 import org.jgraph.graph.BasicMarqueeHandler;
 import org.jgraph.graph.DefaultEdge;
 import org.jgraph.graph.DefaultGraphCell;
 import org.jgraph.graph.GraphLayoutCache;
 import org.jgraph.graph.GraphModel;
 
 public class WorkflowGraph extends JGraph
 {
   public WorkflowGraph()
   {
   }
 
   public WorkflowGraph(GraphModel arg0)
   {
     super(arg0);
   }
 
   public WorkflowGraph(GraphLayoutCache arg0)
   {
     super(arg0);
   }
 
   public WorkflowGraph(GraphModel arg0, GraphLayoutCache arg1)
   {
     super(arg0, arg1);
   }
 
   public WorkflowGraph(GraphModel arg0, BasicMarqueeHandler arg1)
   {
     super(arg0, arg1);
   }
 
   public WorkflowGraph(GraphModel arg0, GraphLayoutCache arg1, BasicMarqueeHandler arg2)
   {
     super(arg0, arg1, arg2);
   }
 
   public String getToolTipText(MouseEvent e)
   {
     if (e != null) {
       DefaultGraphCell c = (DefaultGraphCell)getFirstCellForLocation(e
         .getX(), e.getY());
       if ((c != null) && (!(c instanceof DefaultEdge))) {
         String result = "<html>";
         Object o = c.getUserObject();
         if ((o instanceof ActionModel)) {
           ActionModel action = (ActionModel)o;
           if (action.getDescription() != null)
             result = result + "<b>意见:</b> " + action.getDescription();
         }
         else if ((o instanceof ActionHistoryModel)) {
           ActionHistoryModel history = (ActionHistoryModel)o;
           if (history.getDescription() != null) {
             result = result + "<b>意见:</b> " + history.getDescription();
           }
         }
         result = result + "</html>";
         return result;
       }
     }
     return null;
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.graph.WorkflowGraph
 * JD-Core Version:    0.6.0
 */