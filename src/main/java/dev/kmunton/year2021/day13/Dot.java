package dev.kmunton.year2021.day13;

import dev.kmunton.year2021.day5.Point;

public class Dot extends Point {

  public Dot(int x, int y) {
    super(x, y);
  }

  @Override
  public String toString() {
    return getX() + "," + getY();
  }


}
