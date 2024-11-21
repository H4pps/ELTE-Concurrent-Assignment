package org.example.entities;

public enum Entities {
  EMPTY("."),
  SHEEP("S"),
  DOG("D"),
  WALL("#"),
  GATE("^");

  public final String representation;

  Entities(String representation) {
    this.representation = representation;
  }
}

