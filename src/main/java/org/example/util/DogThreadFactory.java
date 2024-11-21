package org.example.util;

import java.util.concurrent.ThreadFactory;

public class DogThreadFactory implements ThreadFactory {
  private int dogCount = 0;

  @Override
  public Thread newThread(Runnable r) {
    Thread newThread =  new Thread(r) {
      @Override
      public String toString() {
        return getName();
      }
    };
    newThread.setName(Character.toString(dogCount++));
    return newThread;
  }
}
