package dev.kmunton.year2023.day10;

import dev.kmunton.utils.geometry.GridPoint;
import java.util.List;

public class Tile {

  private final String type;
  private final GridPoint point;
  private final List<GridPoint> possibleNeighbours;

  public Tile(String type, GridPoint point) {
    this.type = type;
    this.point = point;
    this.possibleNeighbours = setPossibleNeighbours(point);
  }

  private List<GridPoint> setPossibleNeighbours(GridPoint point) {
    return switch (type) {
      case "S" -> List.of(
          new GridPoint(point.x(), point.y() - 1),
          new GridPoint(point.x(), point.y() + 1),
          new GridPoint(point.x() - 1, point.y()),
          new GridPoint(point.x() + 1, point.y())
      );
      case "|" -> List.of(
          new GridPoint(point.x(), point.y() - 1),
          new GridPoint(point.x(), point.y() + 1)
      );
      case "-" -> List.of(
          new GridPoint(point.x() - 1, point.y()),
          new GridPoint(point.x() + 1, point.y())
      );
      case "F" -> List.of(
          new GridPoint(point.x(), point.y() + 1),
          new GridPoint(point.x() + 1, point.y())
      );
      case "L" -> List.of(
          new GridPoint(point.x(), point.y() - 1),
          new GridPoint(point.x() + 1, point.y())
      );
      case "J" -> List.of(
          new GridPoint(point.x(), point.y() - 1),
          new GridPoint(point.x() - 1, point.y())
      );
      case "7" -> List.of(
          new GridPoint(point.x(), point.y() + 1),
          new GridPoint(point.x() - 1, point.y())
      );
      default -> List.of();
    };
  }

  public String getType() {
    return type;
  }

  public List<GridPoint> getPossibleNeighbours() {
    return possibleNeighbours;
  }

  public boolean isMainLoop(List<GridPoint> mainLoopPoints) {
    return mainLoopPoints.contains(point);
  }

  @Override
  public String toString() {
    return "Tile{" +
        "type=" + type +
        ", point=" + point +
        ", possibleNeighbours=" + possibleNeighbours +
        '}';
  }

  public GridPoint getRowCol() {
    return point;
  }
}
