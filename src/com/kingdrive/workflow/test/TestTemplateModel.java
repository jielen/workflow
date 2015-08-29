 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.context.WorkflowContext;
 import com.kingdrive.workflow.model.meta.NodeModel;
 import com.kingdrive.workflow.model.meta.TemplateModel;
 import com.kingdrive.workflow.service.db.WFMetaService;
 import com.kingdrive.workflow.utils.SpringUtil;
 import java.io.PrintStream;
 import java.util.List;
 import junit.framework.TestCase;
 
 public class TestTemplateModel extends TestCase
 {
   private TemplateModel template = null;
 
   private static WFMetaService service = (WFMetaService)
     SpringUtil.getBean("WFMetaService");
 
   private WorkflowContext context = new WorkflowContext();
 
   protected void setUp() throws Exception {
     super.setUp();
     this.template = service.getTemplate(new Long(2023L));
   }
 
   public void testGetStartNode()
   {
   }
 
   public void testGetLastNode()
   {
   }
 
   public void testGetNextTaskNodes()
     throws Exception
   {
     NodeModel node = this.template.getNode(new Long(2532L));
 
     this.context.setVariable("svPoCode", "YSDW");
     this.context.setVariable("PAYTYPE_CODE", "0201");
     this.context.setVariable("PAYOUT_CODE", "03");
     this.context.setVariable("ND", new Long(2009L));
     List nodes = this.template.getNextTaskNodes(node, this.context);
     for (int i = 0; i < nodes.size(); i++) {
       NodeModel temp = (NodeModel)nodes.get(i);
       System.out.println(temp.getNodeId());
     }
   }
 
   public void testGetNode() throws Exception {
     NodeModel node = this.template.getStartNode();
     System.out.println(node.getNodeStateList());
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestTemplateModel
 * JD-Core Version:    0.6.0
 */