package dev.kmunton.year2023.day23;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

public class Day23 implements Day<Long, Long> {

  private final List<List<Long>> map = new ArrayList<>();
  private int maxRow;
  private int maxCol;

  private record State(GridPoint position, long steps, Set<String> visited) {

  }

  public Day23(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    maxRow = input.size();
    maxCol = input.get(0).length();
    for (var line : input) {
      var row = new ArrayList<Long>();
      for (var c : line.split("")) {
        switch (c) {
          case "#":
            row.add(-1L);
            break;
          case ".":
            row.add(0L);
            break;
          case ">":
            row.add(1L);
            break;
          case "<":
            row.add(2L);
            break;
          case "^":
            row.add(3L);
            break;
          case "v":
            row.add(4L);
            break;
          default:
            throw new RuntimeException("Unknown character: " + c);
        }
      }
      map.add(row);
    }

  }

  public Long part1() {
    var start = new GridPoint(1, 0);
    var longestPath = 0L;
    var queue = new PriorityQueue<State>(Comparator.comparing(State::steps).reversed());
    queue.add(new State(start, 0L, Set.of(start.toString())));
    while (!queue.isEmpty()) {
      var current = queue.poll();
      if (isAtEnd(current.position())) {
        longestPath = Math.max(longestPath, current.steps());
        continue;
      }

      var point = current.position();
      var tileType = map.get(point.y()).get(point.x());
      var newSteps = current.steps() + 1;
      var visited = current.visited();
      var nextTiles = new ArrayList<GridPoint>();
      if (tileType == 1L) {
        nextTiles.add(point.moveByGivenDirection(Direction2D.RIGHT));
      } else if (tileType == 2L) {
        nextTiles.add(point.moveByGivenDirection(Direction2D.LEFT));
      } else if (tileType == 3L) {
        nextTiles.add(point.moveByGivenDirection(Direction2D.UP));
      } else if (tileType == 4L) {
        nextTiles.add(point.moveByGivenDirection(Direction2D.DOWN));
      } else {
        nextTiles.add(point.moveByGivenDirection(Direction2D.UP));
        nextTiles.add(point.moveByGivenDirection(Direction2D.DOWN));
        nextTiles.add(point.moveByGivenDirection(Direction2D.LEFT));
        nextTiles.add(point.moveByGivenDirection(Direction2D.RIGHT));
      }

      for (var nextTile : nextTiles) {
        if (nextTile.isInBounds(maxCol - 1, maxRow - 1)
            && isNotForest(nextTile)
            && isNotVisited(nextTile, visited)) {
          queue.add(new State(nextTile, newSteps, getDeepCopyOfVisited(visited, nextTile)));
        }
      }
    }

    return longestPath;

  }


  public Long part2() {
    var start = new GridPoint(1, 0);
    var end = new GridPoint(maxCol - 2, maxRow - 1);
    var longestPath = 0L;

    var junctions = new HashMap<String, GridPoint>();
    junctions.put(start.toString(), start);
    junctions.put(end.toString(), end);
    for (var r = 0; r < maxRow; r++) {
      for (var c = 0; c < maxCol; c++) {
        var point = new GridPoint(r, c);
        var nextTiles = Stream.of(
            point.moveByGivenDirection(Direction2D.UP),
            point.moveByGivenDirection(Direction2D.LEFT),
            point.moveByGivenDirection(Direction2D.DOWN),
            point.moveByGivenDirection(Direction2D.RIGHT)
        ).filter(p -> p.isInBounds(maxCol - 1, maxRow - 1) && isNotForest(p)).count();
        if (nextTiles > 2) {
          junctions.put(point.toString(), point);
        }
      }
    }
    record NodeState(GridPoint position, long steps) {

    }
    var nodeMap = new HashMap<String, Map<String, NodeState>>();
    for (var entry : junctions.entrySet()) {
      var queue = new ArrayList<State>();
      queue.add(new State(entry.getValue(), 0L, Set.of(entry.getKey())));
      nodeMap.put(entry.getKey(), new HashMap<>());
      while (!queue.isEmpty()) {
        var current = queue.remove(0);
        if (junctions.containsKey(current.position().toString())
            && !current.position().equals(entry.getValue())) {
          nodeMap
              .get(entry.getKey())
              .put(current.position().toString(), new NodeState(current.position(), current.steps()));
          continue;
        }

        var point = current.position();
        var nextTiles = Stream.of(
            point.moveByGivenDirection(Direction2D.UP),
            point.moveByGivenDirection(Direction2D.LEFT),
            point.moveByGivenDirection(Direction2D.DOWN),
            point.moveByGivenDirection(Direction2D.RIGHT)
        ).filter(p -> p.isInBounds(maxCol - 1, maxRow - 1) && isNotForest(p) && isNotVisited(p, current.visited())).toList();
        for (var nextTile : nextTiles) {
          queue.add(new State(nextTile, current.steps() + 1, getDeepCopyOfVisited(current.visited(), nextTile)));
        }
      }
    }

    var queue = new PriorityQueue<>(Comparator.comparing(State::steps).reversed());
    queue.add(new State(start, 0L, Set.of(start.toString())));
    while (!queue.isEmpty()) {
      var current = queue.poll();
      if (isAtEnd(current.position())) {
        if (current.steps() > longestPath) {
          longestPath = current.steps();
        }
        continue;
      }

      var point = current.position();
      var visited = current.visited();
      nodeMap.get(point.toString()).entrySet().stream()
             .filter(e -> !visited.contains(e.getKey()))
             .map(e -> new State(e.getValue().position(), current.steps() + e.getValue().steps(),
                 getDeepCopyOfVisited(visited, e.getValue().position())))
             .forEach(queue::add);
    }

    return longestPath;
  }


  private boolean isNotForest(GridPoint point) {
    return map.get(point.y()).get(point.x()) != -1L;
  }

  private boolean isNotVisited(GridPoint point, Set<String> visited) {
    return !visited.contains(point.toString());
  }

  private boolean isAtEnd(GridPoint point2D) {
    return point2D.y() == maxRow - 1 && point2D.x() == maxCol - 2;
  }

  private Set<String> getDeepCopyOfVisited(Set<String> visited, GridPoint point) {
    var copy = new HashSet<>(visited);
    copy.add(point.toString());
    return copy;
  }
}
