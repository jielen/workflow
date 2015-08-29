 package com.kingdrive.workflow.test;
 
 import com.kingdrive.workflow.model.runtime.DraftModel;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import com.kingdrive.workflow.utils.SpringUtil;
 import java.io.PrintStream;
 import junit.framework.TestCase;
 
 public class TestDraft extends TestCase
 {
   private static WFRuntimeService service = (WFRuntimeService)SpringUtil.getBean("WFRuntimeService");
   private static DraftModel draft = new DraftModel();
 
   protected void setUp() throws Exception {
     super.setUp();
   }
 
   public void testCreateDraft() throws Exception {
     draft.setCompoId("111");
     draft.setDraftName("22222");
     draft.setMasteTableId("3333");
     draft.setUserId("bobo");
     DraftModel d = service.createDraft(draft);
   }
 
   public void testGetDraftById() throws Exception {
     DraftModel m = service.getDraftById(draft.getDraftId());
     System.out.println(draft.getMasteTableId());
   }
 
   public void testRemoveDraft() throws Exception {
     Long id = draft.getDraftId();
     service.removeDraftById(id);
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.test.TestDraft
 * JD-Core Version:    0.6.0
 */