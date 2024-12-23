package dev.kmunton.year2021.day9;


import dev.kmunton.year2021.day5.Point;
import java.util.Objects;

public class CavePoint extends Point {

  private int height;

  public CavePoint(int x, int y, int height) {
    super(x, y);
    this.height = height;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    CavePoint cavePoint = (CavePoint) o;
    return height == cavePoint.height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), height);
  }

  @Override
  public String toString() {
    return "CavePoint{" +
        "height=" + height +
        '}';
  }
}
