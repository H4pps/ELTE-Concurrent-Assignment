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

  @Override
  protected ArrayList<Direction> getPossibleDirections() { // change later
    ArrayList<Direction> possibleDirections = new ArrayList<>();
    synchronized (farm) {
      if (row != 1 && farm.getEntity(row - 1, column) == Entities.EMPTY) {
        possibleDirections.add(Direction.UP);
      }
      if (row != farm.getSize() - 2 && farm.getEntity(row + 1, column) == Entities.EMPTY) {
        possibleDirections.add(Direction.DOWN);
      }
      if (column != 1 && farm.getEntity(row, column - 1) == Entities.EMPTY) {
        possibleDirections.add(Direction.LEFT);
      }
      if (column != farm.getSize() - 2 && farm.getEntity(row, column + 1) == Entities.EMPTY) {
        possibleDirections.add(Direction.RIGHT);
      }
    }

    return possibleDirections;
  }
}
