package org.example.entities;

import org.example.Direction;
import org.example.Farm;
import org.example.util.SubmitData;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Entity implements Runnable {
  private final Entities entityType;
  protected final Farm farm;

  protected int row, column;

  public int getRow() {
    return row;
  }
  public int getColumn() {
    return column;
  }

  public Entity(int row, int column, Farm farm, Entities entityType) {
    this.row = row;
    this.column = column;
    this.farm = farm;
    this.entityType = entityType;
  }

  @Override
  public void run() {
    int[] from = new int[]{row, column};
    move();
    submitChanges(from);
  }

  protected void move() {
    Direction movementDirection;
    movementDirection = getRandomPossibleDirection(getPossibleDirections());

    if (movementDirection != null) {
      changePosition(movementDirection);
    }
  }

  private void submitChanges(int[] from) {
    SubmitData changeData = new SubmitData(from, new int[]{row, column}, entityType);
    synchronized (farm) {
      farm.submitChanges(changeData);
    }
  }

  protected void changePosition(Direction direction) {
    switch (direction) {
      case UP:
        row--;
        break;
      case DOWN:
        row++;
        break;
      case LEFT:
        column--;
        break;
      case RIGHT:
        column++;
        break;
    }
  }

  protected Direction getRandomPossibleDirection(ArrayList<Direction> directions) {
    if (directions.isEmpty()) {
      // can't move in any direction
      return null;
    }

    return directions.get(ThreadLocalRandom.current().nextInt(0, directions.size()));
  }

  abstract protected ArrayList<Direction> getPossibleDirections();
}
