package org.example.entities;

import org.example.Direction;
import org.example.Farm;

import java.util.ArrayList;

public class Dog extends Entity {
//  private static int dogCount = 0;
//  private int id;
  public Dog(int row, int column, Farm farm) {
    super(row, column, farm, Entities.DOG);
//    id = dogCount++;
  }

  private boolean notInTheCenter(int row, int column) {
    int[] middle = farm.getZoneOrigin(farm.centralZoneNumber);
    int zoneSize = farm.zoneSize;

    return row < middle[0] || row >= middle[0] + zoneSize || column < middle[1] || column >= middle[1] + zoneSize;
  }

  @Override
  protected ArrayList<Direction> getPossibleDirections() { // change later
    ArrayList<Direction> possibleDirections = new ArrayList<>();
    synchronized (farm) {
      if (row != 1 &&
              farm.getEntity(row - 1, column) == Entities.EMPTY &&
              notInTheCenter(row - 1, column)) {
        possibleDirections.add(Direction.UP);
      }
      if (row != farm.getSize() - 2 &&
              farm.getEntity(row + 1, column) == Entities.EMPTY &&
              notInTheCenter(row + 1, column)) {
        possibleDirections.add(Direction.DOWN);
      }
      if (column != 1 &&
              farm.getEntity(row, column - 1) == Entities.EMPTY &&
              notInTheCenter(row, column - 1)) {
        possibleDirections.add(Direction.LEFT);
      }
      if (column != farm.getSize() - 2 &&
              farm.getEntity(row, column + 1) == Entities.EMPTY &&
              notInTheCenter(row, column + 1)) {
        possibleDirections.add(Direction.RIGHT);
      }
    }

    return possibleDirections;
  }
}
