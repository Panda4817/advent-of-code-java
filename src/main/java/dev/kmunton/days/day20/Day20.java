package dev.kmunton.days.day20;


import static dev.kmunton.utils.algorithms.GraphSearchUtils.aStarSearch;
import static dev.kmunton.utils.algorithms.GraphSearchUtils.aStarSearchVisited;
import static dev.kmunton.utils.algorithms.GraphSearchUtils.findAllStepsToEachPossibleNodeBfs;

import dev.kmunton.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day20 implements Day<Long, Long> {

  private static final Map<GridPoint, String> map = new HashMap<>();
  private GridPoint start;
  private GridPoint end;
  private int maxX;
  private int maxY;
  private static final int CHEAT_DISTANCE_PART1 = 2;
  private static final int CHEAT_DISTANCE_PART2 = 20;
  private static final int SAVINGS_TEST = 50;
  private static final int SAVINGS = 100;
  private static final Map<Long, Long> STEPS_TO_PICOSECONDS = new HashMap<>();

  public Day20(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    maxY = input.size();
    maxX = input.get(0).length();
    for (int y = 0; y < input.size(); y++) {
      for (int x = 0; x < input.get(y).length(); x++) {
        GridPoint point = new GridPoint(x, y);
        String type = String.valueOf(input.get(y).charAt(x));
        if (type.equals("S")) {
          start = point;
          type = ".";
        }
        if (type.equals("E")) {
          end = point;
          type = ".";
        }
        map.put(point, type);
      }
    }
  }

  @Override
  public Long part1() {
    return computeBFSAndAStar(SAVINGS, CHEAT_DISTANCE_PART1);
  }

  public Long part1Test() {
    return computeBFSAndAStar(SAVINGS_TEST, CHEAT_DISTANCE_PART1);
  }

  // Brute force
  private long computeBruteForce(int savings) {
    long picosecondsWithoutCheat = aStarSearch(
        new Node(start, end, map.get(start), 0),
        Node::isGoal,
        n -> n.getNeighborsPart1(map),
        Node::heuristic
    ).size() - 1L;
    log.info(String.valueOf(picosecondsWithoutCheat));
    List<GridPoint> walls = map.entrySet()
                               .stream()
                               .filter(e -> {
                                 if (e.getValue().equals(".")) {
                                   return false;
                                 }
                                 if (e.getKey().isEdge(0, maxX - 1, 0, maxY - 1, 0, 0)) {
                                   return false;
                                 }
                                 List<GridPoint> n = e.getKey()
                                                      .getCardinalNeighbors()
                                                      .stream()
                                                      .filter(p -> map.containsKey(p) && map.get(p).equals("."))
                                                      .toList();
                                 return !n.isEmpty();
                               }).map(Entry::getKey).toList();
    long count = 0;
    for (GridPoint wall : walls) {
      Map<GridPoint, String> newMap = new HashMap<>(map);
      newMap.put(wall, ".");
      long picosecondsWithCheat = aStarSearch(
          new Node(start, end, map.get(start), 0),
          Node::isGoal,
          n -> n.getNeighborsPart1(newMap),
          Node::heuristic
      ).size() - 1L;
      if ((picosecondsWithoutCheat - picosecondsWithCheat) >= savings) {
        count += 1;
      }
    }
    return count;
  }

  @Override
  public Long part2() {
    return computeBFSAndAStar(SAVINGS, CHEAT_DISTANCE_PART2);
  }

  public Long part2Test() {
    return computeBFSAndAStar(SAVINGS_TEST, CHEAT_DISTANCE_PART2);
  }

  private long computeBFSAndAStar(int savings, int cheatDistance) {
    STEPS_TO_PICOSECONDS.clear();
    Map<GridPoint, Integer> steps = findAllStepsToEachPossibleNodeBfs(
        end,
        n -> n.getCardinalNeighbors().stream().filter(p -> map.containsKey(p) && map.get(p).equals(".")).toList()
    );
    aStarSearchVisited(
        new Node(start, end, map.get(start), 0),
        Node::isGoal,
        n -> n.getNeighborsFastWithTeleporting(map, steps, cheatDistance),
        Node::heuristic,
        Node::getCurrentGridpoint
    );
    return STEPS_TO_PICOSECONDS.entrySet().stream().filter(p -> (steps.get(start) - p.getKey()) >= savings).mapToLong(Entry::getValue).sum();
  }

  record Node(GridPoint current, GridPoint goal, String type, int totalCost) {

    public Map<Node, Integer> getNeighborsPart1(Map<GridPoint, String> grid) {
      return current().getCardinalNeighbors().stream()
                      .filter(p -> grid.containsKey(p) && grid.get(p).equals("."))
                      .map(p -> new Node(p, goal(), grid.get(p), 0))
                      .collect(Collectors.toMap(n -> n, n -> 1));
    }

    public Map<Node, Integer> getNeighborsFastWithTeleporting(Map<GridPoint, String> grid, Map<GridPoint, Integer> steps, Integer cheatDistance) {
      List<GridPoint> neighbors = new ArrayList<>();
      for (int x = current().x() - cheatDistance; x <= current().x() + cheatDistance; x++) {
        for (int y = current().y() - cheatDistance; y <= current().y() + cheatDistance; y++) {
          GridPoint neighbor = new GridPoint(x, y);
          if (grid.containsKey(neighbor)
              && grid.get(neighbor).equals(".")) {
            int md = current().manhattanDistance(neighbor);
            if (md == 1) {
              neighbors.add(neighbor);
            }
            if (md <= cheatDistance && md > 1) {
              long stepCount = (long) steps.get(neighbor) + md + totalCost();
              STEPS_TO_PICOSECONDS.put(stepCount, STEPS_TO_PICOSECONDS.getOrDefault(stepCount, 0L) + 1L);
            }

          }
        }
      }

      return neighbors.stream()
                      .map(p -> new Node(p, goal(), grid.get(p), totalCost() + 1))
                      .collect(Collectors.toMap(n -> n, n -> 1));
    }

    public int heuristic() {
      return current().manhattanDistance(goal) + 1;
    }

    public boolean isGoal() {
      return current().equals(goal());
    }

    public GridPoint getCurrentGridpoint() {
      return current();
    }
  }
}
