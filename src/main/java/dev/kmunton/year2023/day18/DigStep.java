package dev.kmunton.year2023.day18;

import dev.kmunton.utils.geometry.Direction2D;

public record DigStep(Direction2D direction, int steps, String hexColour) {

  @Override
  public String toString() {
    return "DigStep{" +
        "direction=" + direction +
        ", steps=" + steps +
        ", hexColour='" + hexColour + '\'' +
        '}';
  }
}
