package org.example;

import org.example.entities.Dog;
import org.example.entities.Entities;
import org.example.entities.Sheep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Farm {
  private final int size;
  private final Sheep[] sheeps;
  private final Dog[] dogs;
//  private final Entities[][] field;

  public Farm(int size) {
    this.size = size;
    sheeps = new Sheep[10];
    dogs = new Dog[5];

  }

  private Entities[][] generateField() {
    Entities[][] field = new Entities[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        field[i][j] = Entities.EMPTY;
      }
    }
    return field;
  }

  public void startSimulation() {
    ExecutorService sheepExecutor = Executors.newScheduledThreadPool(10);
  }

//  @Override
//  public String toString() {
//  }
}