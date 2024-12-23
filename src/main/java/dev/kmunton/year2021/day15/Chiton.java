package dev.kmunton.year2021.day15;

import dev.kmunton.year2021.day5.Point;
import java.util.Objects;

public class Chiton extends Point {

  private int risk;

  public Chiton(int x, int y, int risk) {
    super(x, y);
    this.risk = risk;
  }

  public int getRisk() {
    return risk;
  }

  public void setRisk(int risk) {
    this.risk = risk;
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
    Chiton chiton = (Chiton) o;
    return risk == chiton.risk;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), risk);
  }

  @Override
  public String toString() {
    return "<" + getX() + "," + getY() + " " + getRisk() + ">";
  }
}
