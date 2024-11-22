package org.example;

import org.example.entities.Dog;
import org.example.entities.Entities;
import org.example.entities.Sheep;
import org.example.util.DogThreadFactory;
import org.example.util.SheepThreadFactory;
import org.example.util.SubmitData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Farm {
  public final int DEFAULT_SLEEP_TIME;
  public final int centralZoneNumber = 4;
  public final int zoneSize;

  private final Random random = new Random();
  private ScheduledExecutorService sheepExecutor;
  private ScheduledExecutorService dogExecutor;

  private final int size;
  private final int sheepCount, dogCount;

  private final AtomicBoolean isRunning;

  private Sheep[] sheeps; // I am sorry for this name
  private Dog[] dogs;
  private Entities[][] field;

  public Farm() {
    this(14, 10, 5, 200);
  }

  public Farm(int size, int sheepCount, int dogCount, int sleepTime) {
    this.size = size;
    zoneSize = (size - 2) / 3;
    this.sheepCount = sheepCount;
    this.dogCount = dogCount;
    DEFAULT_SLEEP_TIME = sleepTime;

    isRunning = new AtomicBoolean(false);
    generateField();
  }

  public Entities[][] getField() {
    return field;
  }
  public Entities getEntity(int row, int column) {
    return field[row][column];
  }
  public int getSize() {
    return size;
  }

  public void submitChanges(SubmitData data) { // sync later (if I will need that)
    field[data.from[0]][data.from[1]] = Entities.EMPTY;
    field[data.to[0]][data.to[1]] = data.entityType;
    display();
  }

  public void endSimulation() {
    if (!isRunning.get()) {
      return;
    }
    isRunning.set(false);
    // Do I have to shut down more?
    sheepExecutor.shutdown();
    dogExecutor.shutdown();
    System.out.println("Simulation ended");
  }

  public void startSimulation() {
    isRunning.set(true);
    sheepExecutor = Executors.newScheduledThreadPool(10, new SheepThreadFactory());
    dogExecutor = Executors.newScheduledThreadPool(5, new DogThreadFactory());

    for (Sheep sheep : sheeps) {
      sheepExecutor.scheduleAtFixedRate(sheep, 0, DEFAULT_SLEEP_TIME, TimeUnit.MILLISECONDS);
    }
    for (Dog dog : dogs) {
      dogExecutor.scheduleAtFixedRate(dog, 0, DEFAULT_SLEEP_TIME, TimeUnit.MILLISECONDS);
    }
  }

  private void generateField() {
    initField();

    field[0][generateRandomNumber(1, size - 1)] = Entities.GATE;
    field[size - 1][generateRandomNumber(1, size - 1)] = Entities.GATE;
    field[generateRandomNumber(1, size - 1)][0] = Entities.GATE;
    field[generateRandomNumber(1, size - 1)][size - 1] = Entities.GATE;

    generateSheep();
    generateDogs();
  }
  private void initField() {
    field = new Entities[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (i != 0 && j != 0 && i != size - 1 && j != size - 1) {
          field[i][j] = Entities.EMPTY;
        } else {
          field[i][j] = Entities.WALL;
        }
      }
    }
  }
  private void generateSheep() {
    int[] centralOrigin = getZoneOrigin(centralZoneNumber);
    int availablePlaces = zoneSize * zoneSize;
    if (sheepCount > availablePlaces) {
      throw new RuntimeException("Not enough space for sheep");
    }

    sheeps = new Sheep[sheepCount];
    for (int i = 0; i < sheepCount; ++i) {
      int[] position = generateRandomPosition(centralOrigin[0], centralOrigin[0] + zoneSize,
              centralOrigin[1], centralOrigin[1] + zoneSize);

      sheeps[i] = new Sheep(position[0], position[1], this);
      field[position[0]][position[1]] = Entities.SHEEP;
    }
  }
  private void generateDogs() {
    dogs = new Dog[dogCount];

    int[] zoneOrder = new int[]{1, 3, 5, 7, 0, 2, 6, 8}; // specifies the order of zones to generate dogs in
    for (
            int dogsGenerated = 0, zoneInd = 0;
            dogsGenerated < dogCount;
            ++dogsGenerated, zoneInd = (zoneInd + 1) % zoneOrder.length
    ) {
      int[] zoneOrigin = getZoneOrigin(zoneOrder[zoneInd]);
      int[] position = generateRandomPosition(zoneOrigin[0], zoneOrigin[0] + zoneSize,
              zoneOrigin[1], zoneOrigin[1] + zoneSize);

      dogs[dogsGenerated] = new Dog(position[0], position[1], this);
      field[position[0]][position[1]] = Entities.DOG;
    }
  }

  // returns the coordinate of the top-left corner of the zone (the number of the zone is [0, 8])
  public int[] getZoneOrigin(int number) {
    return new int[]{1 + zoneSize * (number / 3), 1 + zoneSize * (number % 3)};
  }

  private int generateRandomNumber(int min, int max) {
    return random.nextInt(min, max);
  }
  private int generateRandomNumber(int max) {
    return generateRandomNumber(0, max);
  }

  private int[] generateRandomPosition(int rowMin, int rowMax, int columnMin, int columnMax) {
    int row, column;
    do {
      row = generateRandomNumber(rowMin, rowMax);
      column = generateRandomNumber(columnMin, columnMax);
    } while (field[row][column] != Entities.EMPTY);

    return new int[]{row, column};
  }
  private int[] generateRandomPosition(int min, int max) {
    return generateRandomPosition(min, max, min, max);
  }
  private int[] generateRandomPosition() {
    return generateRandomPosition(0, size);
  }

  private int generateRowPosition(int min, int max, int column) {
    int row;
    do {
      row = generateRandomNumber(min, max);
    } while (field[row][column] != Entities.EMPTY);

    return row;
  }
  private int generateColumnPosition(int min, int max, int row) {
    int column;
    do {
      column = generateRandomNumber(min, max);
    } while (field[row][column] != Entities.EMPTY);

    return column;
  }

  private void cleanTerminal() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    System.out.print("\u001B[0;0H");
    System.out.flush();
  }

  public void display() {
    cleanTerminal();
    System.out.println(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      sb.append(i + (i < 10 ? ":  " : ": "));
      for (int j = 0; j < size; j++) {
        sb.append(field[i][j].representation);
      }
      sb.append("\n");
    }

    return sb.toString();
  }
}