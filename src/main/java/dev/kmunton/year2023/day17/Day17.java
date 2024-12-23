package dev.kmunton.year2023.day17;

import static dev.kmunton.utils.geometry.Direction2D.DOWN;
import static dev.kmunton.utils.geometry.Direction2D.LEFT;
import static dev.kmunton.utils.geometry.Direction2D.RIGHT;
import static dev.kmunton.utils.geometry.Direction2D.UP;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Day17 implements Day<Long, Long> {

  private final List<List<Long>> heatGrid = new ArrayList<>();
  private int maxRow;
  private int maxCol;

  public Day17(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    maxRow = input.size();
    maxCol = input.get(0).length();
    for (int row = 0; row < maxRow; row++) {
      var rowList = new ArrayList<Long>();
      var rowArray = input.get(row).split("");
      for (int col = 0; col < maxCol; col++) {
        long heat = Long.parseLong(rowArray[col]);
        rowList.add(heat);
      }
      heatGrid.add(rowList);
    }
  }

  public Long part1() {
    return solve(3, 0);
  }

  public Long part2() {
    return solve(10, 4);
  }

  private long solve(int maxStraightSteps, int minStraightSteps) {
    var queue = new PriorityQueue<>(Comparator.comparing(Crucible::getTotalHeatLoss));
    var start1 = new Crucible(1, 0, DOWN, getHeatLoss(new GridPoint(0, 1)), 1);
    var start2 = new Crucible(0, 1, RIGHT, getHeatLoss(new GridPoint(1, 0)), 1);
    queue.add(start1);
    queue.add(start2);
    var visited = new HashMap<String, Long>();
    visited.put(start1.getKey(), 0L);
    visited.put(start2.getKey(), 0L);
    var minHeatLoss = Long.MAX_VALUE;
    while (!queue.isEmpty()) {
      var crucible = queue.remove();
      if (isAtEnd(crucible) && crucible.getStraightSteps() >= minStraightSteps) {
        minHeatLoss = Math.min(minHeatLoss, crucible.getTotalHeatLoss());
        continue;
      }
      switch (crucible.getDirection()) {
        case RIGHT:
          addTurnIfValid(queue, visited, crucible, minStraightSteps, UP);
          addTurnIfValid(queue, visited, crucible, minStraightSteps, DOWN);
          addSameDirectionIfValid(queue, visited, maxStraightSteps, crucible, RIGHT);
          break;
        case DOWN:
          addTurnIfValid(queue, visited, crucible, minStraightSteps, LEFT);
          addTurnIfValid(queue, visited, crucible, minStraightSteps, RIGHT);
          addSameDirectionIfValid(queue, visited, maxStraightSteps, crucible, DOWN);
          break;
        case LEFT:
          addTurnIfValid(queue, visited, crucible, minStraightSteps, UP);
          addTurnIfValid(queue, visited, crucible, minStraightSteps, DOWN);
          addSameDirectionIfValid(queue, visited, maxStraightSteps, crucible, LEFT);
          break;
        case UP:
          addTurnIfValid(queue, visited, crucible, minStraightSteps, LEFT);
          addTurnIfValid(queue, visited, crucible, minStraightSteps, RIGHT);
          addSameDirectionIfValid(queue, visited, maxStraightSteps, crucible, UP);
          break;
      }

    }
    return minHeatLoss;
  }

  private void addTurnIfValid(PriorityQueue<Crucible> queue, HashMap<String, Long> visited, Crucible crucible,
      int minStraightSteps, Direction2D direction) {
    var point = crucible.moveByGivenDirection(direction);
    if (point.isInBounds(maxCol - 1, maxRow - 1) && crucible.getStraightSteps() >= minStraightSteps) {
      var heat = crucible.getTotalHeatLoss() + getHeatLoss(point);
      var newCrucible = new Crucible(point.y(), point.x(), direction, heat, 1);
      var key = newCrucible.getKey();
      if (notVisited(visited, key, heat)) {
        visited.put(key, heat);
        queue.add(newCrucible);
      }
    }
  }

  private void addSameDirectionIfValid(PriorityQueue<Crucible> queue, HashMap<String, Long> visited,
      int maxStraightSteps, Crucible crucible, Direction2D direction) {
    var point = crucible.moveByGivenDirection(direction);
    if (point.isInBounds(maxCol - 1, maxRow - 1) && crucible.getStraightSteps() < maxStraightSteps) {
      var heat = crucible.getTotalHeatLoss() + getHeatLoss(point);
      var newSteps = crucible.getStraightSteps() + 1;
      var newCrucible = new Crucible(point.y(), point.x(), direction, heat, newSteps);
      var key = newCrucible.getKey();
      if (notVisited(visited, key, heat)) {
        visited.put(key, heat);
        queue.add(newCrucible);
      }
    }
  }

  private boolean isAtEnd(Crucible crucible) {
    return crucible.getRow() == maxRow - 1 && crucible.getCol() == maxCol - 1;
  }

  private long getHeatLoss(GridPoint point) {
    return heatGrid.get(point.y()).get(point.x());
  }

  private boolean notVisited(Map<String, Long> visited, String key, long heatLoss) {
    if (!visited.containsKey(key)) {
      return true;
    }
    return visited.get(key) > heatLoss;
  }
}
