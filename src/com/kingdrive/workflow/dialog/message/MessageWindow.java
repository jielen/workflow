 package com.kingdrive.workflow.dialog.message;
 
 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.Frame;
 import java.awt.Toolkit;
 import java.awt.event.MouseAdapter;
 import java.awt.event.MouseEvent;
 import java.io.IOException;
 import javax.imageio.ImageIO;
 import javax.jms.JMSException;
 import javax.jms.Message;
 import javax.jms.TextMessage;
 import javax.naming.NamingException;
 import javax.swing.BorderFactory;
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JTextPane;
import javax.swing.JWindow;
 
 public class MessageWindow extends JWindow
 {
   private static final long serialVersionUID = 1444993737967181941L;
   private static int WINDOW_WIDTH = 300;
 
   private static int WINDOW_HEIGHT = 200;
 
   private static int WINDOW_LEFT = 0;
 
   private static int WINDOW_TOP = 0;
 
   private static int MOVE_TIMER_INTERVAL = 30;
 
   private static int MOVE_INTERVAL = 10;
 
   private static String MESSAGE_ICON_PATH = "message.jpg";
 
   private static String BELL_ICON_PATH = "bell.png";
 
   private static String CLOSE_ICON_PATH = "close.png";
   private JTextPane messageContent;
   private JPanel top;
   private AsynMessThread messageThread;
   private AsynMessageListener listener;
   private Object showLock = new Object();
   private String selector;
   private JWindow self;
   private JPanel container;
 
   public MessageWindow(String selector)
   {
     try
     {
       this.self = this;
       this.container = ((JPanel)getContentPane());
       this.selector = selector;
       initView();
       initMessageThread();
     } catch (Exception ex) {
       ex.printStackTrace();
       throw new RuntimeException(ex);
     }
   }
 
   public MessageWindow(String selector, Frame owner) {
     super(owner);
     try {
       this.self = this;
       this.container = ((JPanel)getContentPane());
       this.selector = selector;
       initView();
       initMessageThread();
     } catch (Exception ex) {
       ex.printStackTrace();
       throw new RuntimeException(ex);
     }
   }
 
   private void initView() throws IOException {
     this.setAlwaysOnTop(true);
     this.container.setLayout(new BorderLayout());
 
     this.top = new JPanel();
     this.top.setBorder(BorderFactory.createEtchedBorder());
     this.top.setLayout(new BorderLayout());
     ImageIcon bellIcon = new ImageIcon(ImageIO.read(getClass()
       .getResource(BELL_ICON_PATH)));
     JLabel bellLabel = new JLabel(bellIcon);
     this.top.add(bellLabel, "West");
     JLabel contentLabel = new JLabel("Live Message");
     this.top.add(contentLabel, "Center");
     ImageIcon closeIcon = new ImageIcon(ImageIO.read(getClass()
       .getResource(CLOSE_ICON_PATH)));
     JLabel closeLabel = new JLabel(closeIcon);
     closeLabel.addMouseListener(new MouseAdapter() {
       public void mousePressed(MouseEvent event) {
         MessageWindow.this.self.setLocation(MessageWindow.WINDOW_LEFT, MessageWindow.WINDOW_TOP);
         synchronized (MessageWindow.this.showLock) {
           MessageWindow.this.showLock.notifyAll();
         }
       }
     });
     this.top.add(closeLabel, "East");
     this.container.add(this.top, "North");
 
     this.messageContent = new JTextPane();
     this.messageContent.setEditable(false);
     this.container.add(this.messageContent, "Center");
 
     ImageIcon messageIcon = new ImageIcon(ImageIO.read(getClass()
       .getResource(MESSAGE_ICON_PATH)));
     JLabel iconLabel = new JLabel(messageIcon);
     iconLabel.setOpaque(true);
     iconLabel.setBackground(Color.WHITE);
     iconLabel.setVerticalAlignment(1);
     this.container.add(iconLabel, "West");
     this.container.setBorder(BorderFactory.createEtchedBorder());
 
     WINDOW_LEFT = Toolkit.getDefaultToolkit().getScreenSize().width - 
       WINDOW_WIDTH;
     WINDOW_TOP = Toolkit.getDefaultToolkit().getScreenSize().height;
     setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
     setLocation(WINDOW_LEFT, WINDOW_TOP);
     setVisible(true);
   }
 
   private void initMessageThread() throws NamingException, JMSException {
		listener = new AsynMessageListener(new MyCallback());
		messageThread = new OpenMQMessThread(listener, this.selector);
		messageThread.start();
   }

	private class MyCallback implements MessageCallback {
		public void onMessage(Message mess) {
			try {
				TextMessage m = (TextMessage) mess;
				messageContent.setText(m.getText());
				Thread ShowThread = new ShowThread();
				ShowThread.start();
				synchronized (showLock) {
					showLock.wait();
				}
			} catch (JMSException ex) {
				ex.printStackTrace();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	private class ShowThread extends Thread {
		public void run() {
			int offset = 0;
			try {
				while (offset < WINDOW_HEIGHT) {
					self.setLocation(WINDOW_LEFT, WINDOW_TOP - offset - 20);
					offset += MOVE_INTERVAL;
					Thread.sleep(MOVE_TIMER_INTERVAL);
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.message.MessageWindow
 * JD-Core Version:    0.6.0
 */