package dev.kmunton.year2023.day21;


import static dev.kmunton.utils.geometry.Direction2D.DOWN;
import static dev.kmunton.utils.geometry.Direction2D.LEFT;
import static dev.kmunton.utils.geometry.Direction2D.RIGHT;
import static dev.kmunton.utils.geometry.Direction2D.UP;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day21 implements Day<Long, Long> {

  private int[][] gardenMap;
  private GridPoint start;
  private final Set<GridPoint> rocks = new HashSet<>();
  private int maxX;
  private int maxY;

  private record State(GridPoint position, int steps) {

  }

  private record StateInfiniteGarden(GridPoint positionOnGrid, GridPoint positionOffMap, int steps) {

  }

  public Day21(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    maxX = input.get(0).length() - 1;
    maxY = input.size() - 1;
    gardenMap = new int[input.size()][input.get(0).length()];
    for (var r = 0; r < input.size(); r++) {
      for (var c = 0; c < input.get(r).length(); c++) {
        gardenMap[r][c] = input.get(r).charAt(c) == '#' ? -1 : 0;
        if (input.get(r).charAt(c) == 'S') {
          start = new GridPoint(c, r);
        }
        if (input.get(r).charAt(c) == '#') {
          rocks.add(new GridPoint(c, r));
        }

      }
    }
  }

  public Long part1() {
    return calculateGardenPlotsBySteps(64);

  }

  // Thanks to Reddit AOC thread, it turned out to be a quadratic equation
  public Long part2() {
    long stepsToTake = 26501365;
    long width = 131;
    long x = stepsToTake % width;
    long d = stepsToTake / width;
    var y = calculateGardenPlotsByStepsForInfiniteGarden(x);
    var y1 = calculateGardenPlotsByStepsForInfiniteGarden(x + width);
    var y2 = calculateGardenPlotsByStepsForInfiniteGarden(x + (width * 2L));
    long c = y;
    long a = (y2 + c - (2 * y1)) / 2;
    long b = y1 - y - a;
    return (a * d * d) + (b * d) + c;
  }

  public long calculateGardenPlotsBySteps(int steps) {
    var visited = new HashSet<String>();
    var queue = new LinkedList<State>();
    var plots = 0;
    queue.add(new State(start, 0));
    visited.add(start.toString() + 0);
    while (!queue.isEmpty()) {
      var current = queue.poll();
      if (current.steps() == steps) {
        plots++;
        continue;
      }
      var currentPoint = current.position();
      var nextPoints = new ArrayList<GridPoint>();
      if (currentPoint.canMoveGivenDirectionAndBlockers(UP, maxX, maxY, rocks)) {
        nextPoints.add(currentPoint.moveByGivenDirection(UP));
      }
      if (currentPoint.canMoveGivenDirectionAndBlockers(DOWN, maxX, maxY, rocks)) {
        nextPoints.add(currentPoint.moveByGivenDirection(DOWN));
      }
      if (currentPoint.canMoveGivenDirectionAndBlockers(LEFT, maxX, maxY, rocks)) {
        nextPoints.add(currentPoint.moveByGivenDirection(LEFT));
      }
      if (currentPoint.canMoveGivenDirectionAndBlockers(RIGHT, maxX, maxY, rocks)) {
        nextPoints.add(currentPoint.moveByGivenDirection(RIGHT));
      }
      for (var next : nextPoints) {
        if (!visited.contains(next.toString() + current.steps() + 1)) {
          visited.add(next.toString() + current.steps() + 1);
          queue.add(new State(next, current.steps() + 1));
        }
      }
    }
    return plots;
  }


  public long calculateGardenPlotsByStepsForInfiniteGarden(long steps) {
    var visited = new HashSet<String>();
    var queue = new LinkedList<StateInfiniteGarden>();
    var plots = 0;
    var first = new StateInfiniteGarden(start, start, 0);
    queue.add(first);
    visited.add(first.toString());
    while (!queue.isEmpty()) {
      var current = queue.poll();
      if (current.steps() == steps) {
        plots++;
        continue;
      }
      var currentPoint = current.positionOnGrid();
      var pointOffGrid = current.positionOffMap();
      var nextPoints = new ArrayList<StateInfiniteGarden>();
      nextPoints.add(new StateInfiniteGarden(currentPoint.moveByGivenDirection(UP),
          pointOffGrid.moveByGivenDirection(UP), current.steps() + 1));
      nextPoints.add(new StateInfiniteGarden(currentPoint.moveByGivenDirection(DOWN),
          pointOffGrid.moveByGivenDirection(DOWN), current.steps() + 1));
      nextPoints.add(new StateInfiniteGarden(currentPoint.moveByGivenDirection(LEFT),
          pointOffGrid.moveByGivenDirection(LEFT), current.steps() + 1));
      nextPoints.add(new StateInfiniteGarden(currentPoint.moveByGivenDirection(RIGHT),
          pointOffGrid.moveByGivenDirection(RIGHT), current.steps() + 1));
      for (var next : nextPoints) {
        int adjustedRow;
        int adjustedCol;
        if (next.positionOnGrid.y() == gardenMap.length) {
          adjustedRow = 0;
        } else if (next.positionOnGrid.y() == -1) {
          adjustedRow = gardenMap.length - 1;
        } else {
          adjustedRow = next.positionOnGrid.y();
        }
        if (next.positionOnGrid.x() == gardenMap[0].length) {
          adjustedCol = 0;
        } else if (next.positionOnGrid.x() == -1) {
          adjustedCol = gardenMap[0].length - 1;
        } else {
          adjustedCol = next.positionOnGrid.x();
        }
        var nextOnGrid = new GridPoint(adjustedCol, adjustedRow);
        if (rocks.contains(nextOnGrid)) {
          continue;
        }
        next = new StateInfiniteGarden(nextOnGrid, next.positionOffMap(), next.steps());
        if (!visited.contains(next.toString())) {
          visited.add(next.toString());
          queue.add(next);
        }
      }
    }
    return plots;
  }
}
