package dev.kmunton.year2023.day18;


import static dev.kmunton.utils.geometry.Direction2D.DOWN;
import static dev.kmunton.utils.geometry.Direction2D.LEFT;
import static dev.kmunton.utils.geometry.Direction2D.RIGHT;
import static dev.kmunton.utils.geometry.Direction2D.UP;
import static dev.kmunton.utils.geometry.MathUtils.polygonArea;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day18 implements Day<Long, Long> {

  private final List<DigStep> instructions = new ArrayList<>();

  public Day18(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    for (var line : input) {
      var parts = line.split(" ");
      Direction2D direction = switch (parts[0]) {
        case "U" -> UP;
        case "D" -> DOWN;
        case "R" -> RIGHT;
        case "L" -> LEFT;
        default -> throw new RuntimeException("Unknown direction: " + parts[0]);
      };
      var steps = Integer.parseInt(parts[1]);
      var hexColour = parts[2].substring(1, parts[2].length() - 1);
      instructions.add(new DigStep(direction, steps, hexColour));
    }
  }

  public Long part1() {
    var start = new GridPoint(0, 0);
    Set<GridPoint> edge = new HashSet<>();
    for (var step : instructions) {
      for (var i = 0; i < step.steps(); i++) {
        start = start.moveByGivenDirection(step.direction());
        edge.add(new GridPoint(start.y(), start.x()));

      }
    }
    var minRow = edge.stream().mapToInt(GridPoint::y).min().getAsInt();
    var maxRow = edge.stream().mapToInt(GridPoint::y).max().getAsInt();
    var minCol = edge.stream().mapToInt(GridPoint::x).min().getAsInt();
    var maxCol = edge.stream().mapToInt(GridPoint::x).max().getAsInt();
    var result = 0L;
    for (var r = minRow; r < maxRow + 1; r++) {
      var crossed = 0;
      for (var c = minCol; c < maxCol + 1; c++) {
        if (edge.contains(new GridPoint(c, r))) {
          result++;
          if (!edge.contains(new GridPoint(c - 1, r)) && !edge.contains(new GridPoint(c + 1, r))
              || (!edge.contains(new GridPoint(c - 1, r)) && edge.contains(new GridPoint(c, r + 1)) && edge.contains(new GridPoint(c + 1, r)))
              || (!edge.contains(new GridPoint(c + 1, r)) && edge.contains(new GridPoint(c, r + 1)) && edge.contains(new GridPoint(c - 1, r)))) {
            crossed++;
          }
          continue;
        }
        if (crossed % 2 != 0) {
          result++;
        }

      }
    }
    return result;

  }

  public Long part2() {
    var newDigSteps = new ArrayList<DigStep>();
    for (var step : instructions) {
      var hex = step.hexColour();
      var number = Integer.parseInt(hex.substring(1, hex.length() - 1), 16);
      var direction = switch (hex.charAt(hex.length() - 1)) {
        case '0' -> RIGHT;
        case '1' -> DOWN;
        case '2' -> LEFT;
        case '3' -> UP;
        default -> throw new RuntimeException("Unknown direction: " + hex.charAt(hex.length() - 1));
      };
      newDigSteps.add(new DigStep(direction, number, hex));
    }

    var start = new GridPoint(0, 0);
    List<GridPoint> corners = new ArrayList<>();
    for (var step : newDigSteps) {
      for (var i = 0; i < step.steps(); i++) {
        start = start.moveByGivenDirection(step.direction());
      }
      corners.add(new GridPoint(start.x(), start.y()));
    }
    var maxRight = newDigSteps.stream().filter(d -> d.direction().equals(RIGHT)).mapToLong(DigStep::steps).sum();
    var maxDown = newDigSteps.stream().filter(d -> d.direction().equals(DOWN)).mapToLong(DigStep::steps).sum();
    corners.remove(new GridPoint(0, 0));
    corners.add(0, new GridPoint(0, 0));
    var rows = corners.stream().map(GridPoint::y).map(r -> (double) r).toList();
    var cols = corners.stream().map(GridPoint::x).map(c -> (double) c).toList();
    var n = corners.size();

    return (long) polygonArea(cols, rows, n) + (maxRight + maxDown + 1);
  }
}
