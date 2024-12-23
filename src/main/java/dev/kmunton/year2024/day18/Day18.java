package dev.kmunton.year2024.day18;


import static dev.kmunton.utils.algorithms.GraphSearchUtils.aStarSearch;
import static dev.kmunton.utils.algorithms.GraphSearchUtils.dfs;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day18 implements Day<Long, String> {

  private final List<GridPoint> points = new ArrayList<>();


  public Day18(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> {
      List<Integer> numbers = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
      GridPoint point = new GridPoint(numbers.get(0), numbers.get(1));
      points.add(point);
    });
  }

  @Override
  public Long part1() {
    Map<GridPoint, Integer> memory = getMemory(1024, 70);
    return (long) getShortestPath(memory, new GridPoint(0, 0), new GridPoint(70, 70)).size() - 1;
  }

  public Long part1Test() {
    Map<GridPoint, Integer> memory = getMemory(12, 6);
    return (long) getShortestPath(memory, new GridPoint(0, 0), new GridPoint(6, 6)).size() - 1;
  }

  private Map<GridPoint, Integer> getMemory(int numberOfFallingBytes, int size) {
    Map<GridPoint, Integer> memory = new HashMap<>();
    for (int y = 0; y < size + 1; y++) {
      for (int x = 0; x < size + 1; x++) {
        GridPoint point = new GridPoint(x, y);
        memory.put(point, 0);
      }
    }
    for (int i = 0; i < numberOfFallingBytes; i++) {
      memory.put(points.get(i), -1);
    }
    return memory;
  }

  private List<Historian> getShortestPath(Map<GridPoint, Integer> memory, GridPoint start, GridPoint end) {
    return aStarSearch(
        new Historian(start, end),
        Historian::isGoal,
        h -> h.getNeighbors(memory),
        Historian::getHeuristic
    );

  }

  record Historian(GridPoint current, GridPoint end) {
    public Map<Historian, Integer> getNeighbors(Map<GridPoint, Integer> memory) {
      Map<Historian, Integer> neighbors = new HashMap<>();
      current.getCardinalNeighbors()
             .stream()
             .filter(g -> memory.containsKey(g) && memory.get(g) == 0)
             .forEach(neighbor -> neighbors.put(new Historian(neighbor, end), 1));
      return neighbors;
    }
    public int getHeuristic() {
      return end.manhattanDistance(current) + 1;
    }
    public boolean isGoal() {
      return current.equals(end);
    }
  }


  @Override
  public String part2() {
    return getBlockingByte(1024, 70);

  }

  public String part2Test() {
    return getBlockingByte(13, 6);

  }

  private String getBlockingByte(int fallingBytes, int size) {
    fallingBytes += 1;
    Map<GridPoint, Integer> memory = getMemory(fallingBytes, size);
    while (pathExists(memory, new GridPoint(0, 0), new GridPoint(size, size))) {
      fallingBytes += 1;
      memory = getMemory(fallingBytes, size);
    }
    return points.get(fallingBytes-1).toString();
  }

  private boolean pathExists(Map<GridPoint, Integer> memory, GridPoint start, GridPoint end) {
    return dfs(
        new Historian(start, end),
        Historian::isGoal,
        h -> h.getNeighbors(memory).keySet().stream().toList()
    );
  }
}
