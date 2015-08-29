package com.kingdrive.workflow.dialog.message;

import java.io.PrintStream;

public class DoneLatch
{
  volatile boolean done = false;

  public void waitTillDone() {
    synchronized (this) {
      while (!this.done)
        try {
          wait();
        } catch (InterruptedException localInterruptedException) {
        }
      System.out.println("listener has stop!");
    }
  }

  public void allDone() {
    synchronized (this) {
      this.done = true;
      notify();
    }
  }
}

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.dialog.message.DoneLatch
 * JD-Core Version:    0.6.0
 */