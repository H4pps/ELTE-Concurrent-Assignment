package org.example.entities;

public abstract class Entity extends Thread {
  private int row, column;
  public int getRow() {
    return row;
  }
  public int getColumn() {
    return column;
  }

  public Entity(int row, int column) {
    this.row = row;
    this.column = column;
  }
}
