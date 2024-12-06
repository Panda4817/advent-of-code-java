package dev.kmunton.utils.geometry;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum Direction2D implements Direction {
  UP(0, -1),
  DOWN(0, 1),
  LEFT(-1, 0),
  RIGHT(1, 0),
  UP_LEFT(-1, -1),
  UP_RIGHT(1, -1),
  DOWN_LEFT(-1, 1),
  DOWN_RIGHT(1, 1);

  private final int dx;
  private final int dy;

  Direction2D(int dx, int dy) {
    this.dx = dx;
    this.dy = dy;
  }

  private static final EnumMap<Direction2D, Integer> directionToAngle = new EnumMap<>(Direction2D.class);
  private static final Map<Integer, Direction2D> angleToDirection = new HashMap<>();

  static {
    // Map cardinal directions to angles
    directionToAngle.put(Direction2D.UP, 0);
    directionToAngle.put(Direction2D.RIGHT, 90);
    directionToAngle.put(Direction2D.DOWN, 180);
    directionToAngle.put(Direction2D.LEFT, 270);
    directionToAngle.put(Direction2D.UP_RIGHT, 45);
    directionToAngle.put(Direction2D.DOWN_RIGHT, 135);
    directionToAngle.put(Direction2D.DOWN_LEFT, 225);
    directionToAngle.put(Direction2D.UP_LEFT, 315);

    // Reverse mapping from angles to directions
    directionToAngle.forEach((direction, angle) -> angleToDirection.put(angle, direction));
  }

  public Direction2D getNewDirection(int rotationDegrees) {
    // Get the current angle of the direction
    int currentAngle = directionToAngle.get(this);

    // Normalize the rotation
    int newAngle = (currentAngle + rotationDegrees) % 360;
    if (newAngle < 0) {
      newAngle += 360;
    }

    // Find and return the direction corresponding to the new angle
    return angleToDirection.get(newAngle);
  }

}

