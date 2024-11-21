package org.example.entities;

public class Dog extends Entity {
  private static int dogCount = 0;
  private int id;
  public Dog(int row, int column) {
    super(row, column);
    id = dogCount++;
  }

  @Override
  public void run() {
    System.out.println("Dog " + id + " is running");
  }
}
