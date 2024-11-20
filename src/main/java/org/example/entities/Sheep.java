package org.example.entities;

public class Sheep extends Entity{
  private static int sheepCount = 0;

  public Sheep(int row, int column) {
    super(row, column);
    setName(Character.toString('A' + 1));
  }

  @Override
  public void run() {
    System.out.println("Sheep " + getName() + " is running");
  }

  @Override
  public String toString() {
    return getName();
  }
}
