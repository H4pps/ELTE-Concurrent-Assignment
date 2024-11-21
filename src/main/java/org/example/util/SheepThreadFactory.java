package org.example.util;

import java.util.concurrent.ThreadFactory;

public class SheepThreadFactory implements ThreadFactory {
  private int sheepCount = 0;

  @Override
  public Thread newThread(Runnable r) {
    Thread newThread =  new Thread(r) {
      @Override
      public String toString() {
        return getName();
      }
    };
    newThread.setName(Character.toString('A' + sheepCount++));
    return newThread;
  }

}
