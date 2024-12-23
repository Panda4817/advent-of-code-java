package dev.kmunton.year2021.day11;

import dev.kmunton.year2021.day5.Point;
import java.util.Objects;

public class Octopus extends Point {

  private int energy;

  public Octopus(int x, int y, int energy) {
    super(x, y);
    this.energy = energy;
  }

  public int getEnergy() {
    return energy;
  }

  public void setEnergy(int energy) {
    this.energy = energy;
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
    Octopus octopus = (Octopus) o;
    return energy == octopus.energy;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), energy);
  }

  @Override
  public String toString() {
    return "Octopus{" +
        "energy=" + energy +
        '}';
  }
}
