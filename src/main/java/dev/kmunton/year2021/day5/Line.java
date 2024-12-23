package dev.kmunton.year2021.day5;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class Line {

  private final int startX;
  private final int startY;
  private final int endX;
  private final int endY;

  public Line(int startX, int startY, int endX, int endY) {
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
  }

  public List<Point> getAllPoints() {
    int sx = min(startX, endX);
    int sy = min(startY, endY);
    int ex = max(startX, endX);
    int ey = max(startY, endY);
    List<Point> points = new ArrayList<>();
    for (int y = sy; y < (ey + 1); y++) {
      for (int x = sx; x < (ex + 1); x++) {
        points.add(new Point(x, y));
      }
    }
    return points;
  }

  public List<Point> getAllDiagonalPoints() {
    List<Point> points = new ArrayList<>();
    if (startY < endY && startX < endX) {
      int x = startX;
      int y = startY;
      while (x != endX + 1 && y != endY + 1) {
        points.add(new Point(x, y));
        y += 1;
        x += 1;
      }
    } else if (startY > endY && startX < endX) {
      int x = startX;
      int y = startY;
      while (x != endX + 1 && y != endY - 1) {
        points.add(new Point(x, y));
        y -= 1;
        x += 1;
      }
    } else if (startY < endY && startX > endX) {
      int x = startX;
      int y = startY;
      while (x != endX - 1 && y != endY + 1) {
        points.add(new Point(x, y));
        y += 1;
        x -= 1;
      }
    } else if (startY > endY && startX > endX) {
      int x = startX;
      int y = startY;
      while (x != endX - 1 && y != endY - 1) {
        points.add(new Point(x, y));
        y -= 1;
        x -= 1;
      }
    }

    return points;

  }

  public Boolean isHorizontal() {
    return startX == endX;
  }

  public Boolean isVertical() {
    return startY == endY;
  }

  public Boolean isDiagonal() {
    return startX != endX && startY != endY;
  }

  @Override
  public String toString() {
    return "Line{" +
        "startX=" + startX +
        ", startY=" + startY +
        ", endX=" + endX +
        ", endY=" + endY +
        '}';
  }
}
