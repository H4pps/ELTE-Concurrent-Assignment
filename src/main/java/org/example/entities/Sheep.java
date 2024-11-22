package org.example.entities;

import org.example.Direction;
import org.example.Farm;

import java.util.ArrayList;

public class Sheep extends Entity{
//  private static int sheepCount = 0;
//  private int id;

  public Sheep(int row, int column, Farm farm) {
    super(row, column, farm, Entities.SHEEP);
//    id = sheepCount++;
  }

  private Entities[] getEntitiesInDirection(Direction direction) {
    Entities[] directionEntities = new Entities[3];
    switch (direction) {
      case UP:
        directionEntities[0] = farm.getEntity(row - 1, column - 1);
        directionEntities[1] = farm.getEntity(row - 1, column);
        directionEntities[2] = farm.getEntity(row - 1, column + 1);
        break;
      case DOWN:
        directionEntities[0] = farm.getEntity(row + 1, column - 1);
        directionEntities[1] = farm.getEntity(row + 1, column);
        directionEntities[2] = farm.getEntity(row + 1, column + 1);
        break;
      case LEFT:
        directionEntities[0] = farm.getEntity(row - 1, column - 1);
        directionEntities[1] = farm.getEntity(row, column - 1);
        directionEntities[2] = farm.getEntity(row + 1, column - 1);
        break;
      case RIGHT:
        directionEntities[0] = farm.getEntity(row - 1, column + 1);
        directionEntities[1] = farm.getEntity(row, column + 1);
        directionEntities[2] = farm.getEntity(row + 1, column + 1);
        break;
    }
    return directionEntities;
  }

  private boolean hasDog(Entities[] directionEntities) {
    for (Entities entity : directionEntities) {
      if (entity == Entities.DOG) {
        return true;
      }
    }
    return false;
  }

  private boolean directionCheck(Direction direction) {
    Entities[] directionEntities = getEntitiesInDirection(direction);
    return switch (direction) {
      case UP -> row == 1 && directionEntities[1] == Entities.GATE ||
              row != 1 && directionEntities[1] == Entities.EMPTY &&
              !hasDog(directionEntities);
      case DOWN -> row == farm.getSize() - 2 && directionEntities[1] == Entities.GATE ||
              row != farm.getSize() - 2 && directionEntities[1] == Entities.EMPTY &&
              !hasDog(directionEntities);
      case LEFT -> column == 1 && directionEntities[1] == Entities.GATE ||
              column != 1 && directionEntities[1] == Entities.EMPTY &&
              !hasDog(directionEntities);
      case RIGHT -> column == farm.getSize() - 2 && directionEntities[1] == Entities.GATE ||
              column != farm.getSize() - 2 && directionEntities[1] == Entities.EMPTY &&
              !hasDog(directionEntities);
    };
  }

  @Override
  protected ArrayList<Direction> getPossibleDirections() { // change later
    ArrayList<Direction> possibleDirections = new ArrayList<>();
    synchronized (farm) {
      if (directionCheck(Direction.UP)) {
        possibleDirections.add(Direction.UP);
      }
      if (directionCheck(Direction.DOWN)) {
        possibleDirections.add(Direction.DOWN);
      }
      if (directionCheck(Direction.LEFT)) {
        possibleDirections.add(Direction.LEFT);
      }
      if (directionCheck(Direction.RIGHT)) {
        possibleDirections.add(Direction.RIGHT);
      }
    }

    return possibleDirections;
  }
}
