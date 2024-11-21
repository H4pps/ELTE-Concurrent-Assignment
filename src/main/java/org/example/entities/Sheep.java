package org.example.entities;

public class Sheep extends Entity{
  private static int sheepCount = 0;
  private int id;

  public Sheep(int row, int column) {
    super(row, column);
    id = sheepCount++;
  }

  @Override
  public void run() {
    System.out.println("Sheep " + id + " is running");
  }
}
