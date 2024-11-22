package org.example;

public class Main {
  private static final int zoneSize = 10;
  public static void main(String[] args) {
//    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
//    service.scheduleAtFixedRate(() -> System.out.println("Hello"), 0, 1, java.util.concurrent.TimeUnit.SECONDS);

    Farm farm = new Farm(14);
    farm.startSimulation();
  }
}