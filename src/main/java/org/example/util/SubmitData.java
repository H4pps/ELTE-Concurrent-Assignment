package org.example.util;

import org.example.entities.Entities;

public class SubmitData {
  public final int[] from;
  public final int[] to;
  public final Entities entityType;

  public SubmitData(int[] from, int[] to, Entities entityType) {
    this.from = from;
    this.to = to;
    this.entityType = entityType;
  }
}
