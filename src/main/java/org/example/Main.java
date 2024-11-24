package org.example;


import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Do you want to start simulation with default settings? (y/n): ");
    String choice = scanner.next();
    if (choice.equalsIgnoreCase("y")) {
      Farm farm = new Farm();
      farm.startSimulation();
    
      scanner.close();
      return;
    }

    int size;
    System.out.println("Enter the size of the farm (multiple of 3 + 2, in [5, 26]): ");
    do {
      size = scanner.nextInt();
    } while (sizeCondition(size));

    int zoneSize = (size - 2) / 3;
    int zoneArea = zoneSize * zoneSize;

    System.out.println("Enter the number of sheep [1, " + zoneArea + "]: ");
    int sheepCount;
    do {
      sheepCount = scanner.nextInt();
    } while (sheepCount < 1 || sheepCount > zoneArea);

    int maxDogs = (zoneSize == 8 ? 2 : 1) * 8;
    System.out.println("Enter the number of dogs [1, " + maxDogs + "]: ");
    int dogCount;
    do {
      dogCount = scanner.nextInt();
    } while (dogCount < 1 || dogCount > maxDogs);

    System.out.println("Enter the sleep time [50, 1000] in milliseconds: ");
    int sleepTime;
    do {
      sleepTime = scanner.nextInt();
    } while (sleepTime < 50 || sleepTime > 1000);

    Farm farm = new Farm(size, sheepCount, dogCount, sleepTime);
    farm.startSimulation();

    scanner.close();
  }

  private static boolean sizeCondition(int size) {
    return size < 5 || size > 26 || (size - 2) % 3 != 0;
  }
}