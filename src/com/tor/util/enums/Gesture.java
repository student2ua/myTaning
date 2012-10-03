package com.tor.util.enums;

/** Enums have behavior! */
public enum Gesture {
 ROCK() {
  // Enums are polymorphic, that's really handy!
  @Override
  public boolean beats(Gesture other) {
   return other == SCISSORS;
  }
 },
 PAPER, SCISSORS;

 // we can implement with the integer representation
 public boolean beats(Gesture other) {
  return ordinal() - other.ordinal() == 1;
 }
}
