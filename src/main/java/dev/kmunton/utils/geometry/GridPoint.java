package dev.kmunton.utils.geometry;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record GridPoint(int x, int y) implements PointUtils<GridPoint, Direction2D> {

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  @Override
  public List<GridPoint> getCardinalNeighbors() {
    return Arrays.asList(
        getNeighborGivenDirection(Direction2D.UP),
        getNeighborGivenDirection(Direction2D.DOWN),
        getNeighborGivenDirection(Direction2D.LEFT),
        getNeighborGivenDirection(Direction2D.RIGHT)
    );
  }


  @Override
  public List<GridPoint> getAllNeighbors() {
    return Arrays.asList(
        getNeighborGivenDirection(Direction2D.UP),
        getNeighborGivenDirection(Direction2D.DOWN),
        getNeighborGivenDirection(Direction2D.LEFT),
        getNeighborGivenDirection(Direction2D.RIGHT),
        getNeighborGivenDirection(Direction2D.UP_LEFT),
        getNeighborGivenDirection(Direction2D.UP_RIGHT),
        getNeighborGivenDirection(Direction2D.DOWN_LEFT),
        getNeighborGivenDirection(Direction2D.DOWN_RIGHT)
    );
  }


  public Map<Direction2D, GridPoint> getAllNeighborsWithDirection() {
    return Map.of(
        Direction2D.UP, getNeighborGivenDirection(Direction2D.UP),
        Direction2D.DOWN, getNeighborGivenDirection(Direction2D.DOWN),
        Direction2D.LEFT, getNeighborGivenDirection(Direction2D.LEFT),
        Direction2D.RIGHT, getNeighborGivenDirection(Direction2D.RIGHT),
        Direction2D.UP_LEFT, getNeighborGivenDirection(Direction2D.UP_LEFT),
        Direction2D.UP_RIGHT, getNeighborGivenDirection(Direction2D.UP_RIGHT),
        Direction2D.DOWN_LEFT, getNeighborGivenDirection(Direction2D.DOWN_LEFT),
        Direction2D.DOWN_RIGHT, getNeighborGivenDirection(Direction2D.DOWN_RIGHT)
    );
  }

  public Map<Direction2D, GridPoint> getDiagonalNeighborsWithDirection() {
    return Map.of(
        Direction2D.UP_LEFT, getNeighborGivenDirection(Direction2D.UP_LEFT),
        Direction2D.UP_RIGHT, getNeighborGivenDirection(Direction2D.UP_RIGHT),
        Direction2D.DOWN_LEFT, getNeighborGivenDirection(Direction2D.DOWN_LEFT),
        Direction2D.DOWN_RIGHT, getNeighborGivenDirection(Direction2D.DOWN_RIGHT)
    );
  }

  public Map<Direction2D, GridPoint> getCardinalNeighborsWithDirection() {
    return Map.of(
        Direction2D.UP, getNeighborGivenDirection(Direction2D.UP),
        Direction2D.DOWN, getNeighborGivenDirection(Direction2D.DOWN),
        Direction2D.LEFT, getNeighborGivenDirection(Direction2D.LEFT),
        Direction2D.RIGHT, getNeighborGivenDirection(Direction2D.RIGHT)
    );
  }

  @Override
  public GridPoint getNeighborGivenDirection(Direction2D direction) {
    return moveByGivenDirection(direction);
  }

  @Override
  public boolean isInBounds(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    return this.x >= minX && this.x <= maxX && this.y >= minY && this.y <= maxY;
  }

  public boolean isInBounds(int maxX, int maxY) {
    return isInBounds(0, maxX, 0, maxY, 0, 0);
  }

  @Override
  public boolean isEdge(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    return this.x == minX || this.x == maxX || this.y == minY || this.y == maxY;
  }

  @Override
  public boolean canMoveGivenDirection(Direction2D direction, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    GridPoint neighbor = moveByGivenDirection(direction);
    return neighbor.isInBounds(minX, maxX, minY, maxY, minZ, maxZ);
  }

  public boolean canMoveGivenDirection(Direction2D direction, int maxX, int maxY) {
    GridPoint neighbor = moveByGivenDirection(direction);
    return neighbor.isInBounds(0, maxX, 0, maxY, 0, 0);
  }

  @Override
  public int manhattanDistance(GridPoint point2) {
    return Math.abs(this.x - point2.x) + Math.abs(this.y - point2.y);
  }

  @Override
  public double euclideanDistance(GridPoint point2) {
    return Math.sqrt(Math.pow((double) this.x - point2.x, 2) + Math.pow((double) this.y - point2.y, 2));
  }

  @Override
  public GridPoint moveByGivenDirection(Direction2D direction) {
    return new GridPoint(this.x + direction.getDx(), this.y + direction.getDy());
  }

  @Override
  public GridPoint scale(double scaleFactor) {
    return new GridPoint((int) (this.x * scaleFactor), (int) (this.y * scaleFactor));
  }

  public int differenceX(GridPoint point2) {
    return Math.abs(this.x - point2.x);
  }

  public int differenceY(GridPoint point2) {
    return Math.abs(this.y - point2.y);
  }

  @SafeVarargs
  public final boolean canMoveGivenDirectionAndBlockers(Direction2D dir, int maxX, int maxY, Set<GridPoint>... blockers) {
    GridPoint neighbor = moveByGivenDirection(dir);
    if (!neighbor.isInBounds(0, maxX, 0, maxY, 0, 0)) {
      return false;
    }
    for (var blocker : blockers) {
      if (blocker.contains(neighbor)) {
        return false;
      }
    }
    return true;
  }

}

