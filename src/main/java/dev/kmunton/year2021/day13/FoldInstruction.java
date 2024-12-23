package dev.kmunton.year2021.day13;

import dev.kmunton.year2021.day5.Point;

public class FoldInstruction extends Point {


  public FoldInstruction(int x, int y) {
    super(x, y);
  }

  @Override
  public String toString() {
    if (getX() == 0) {
      return "fold along y=" + getY();
    } else {
      return "fold along x=" + getX();
    }

  }
}
