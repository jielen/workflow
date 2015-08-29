package com.kingdrive.workflow.dialog;

import com.kingdrive.workflow.WFEngine;
import com.kingdrive.workflow.model.runtime.TraceInfoModel;
import com.kingdrive.workflow.utils.SpringUtil;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Frametest extends JFrame
{
	private JButton button;
	
	public Frametest() {
		button = new JButton("show trace");
		final JFrame self = this;
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				WFEngine service = (WFEngine) SpringUtil.getBean("WFEngine");
				TraceInfoModel traceInfoModel = service.getTraceInfo(Long.valueOf(15773+""));
				new TraceDialog(self, traceInfoModel, true);
			}
		});
		this.getContentPane().add(button);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		Frametest t = new Frametest();
	}
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.Frametest
 * JD-Core Version:    0.6.0
 */