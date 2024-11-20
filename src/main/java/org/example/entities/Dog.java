package org.example.entities;

public class Dog extends Entity {
  private static int dogCount = 0;
  public Dog(int row, int column) {
    super(row, column);
    setName(Integer.valueOf(dogCount++).toString());
  }

  @Override
  public void run() {
    System.out.println("Dog " + getName() + " is running");
  }

  @Override
  public String toString() {
    return getName();
  }
}
