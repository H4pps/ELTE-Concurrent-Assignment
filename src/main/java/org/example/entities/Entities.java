package org.example.entities;

public enum Entities {
  EMPTY('.'),
  SHEEP('S'),
  DOG('D'),
  WALL('#'),
  GATE('^');

  public final Character representation;

  Entities(Character representation) {
    this.representation = representation;
  }
}

