package dev.kmunton.year2024.day16;


import static dev.kmunton.utils.algorithms.GraphSearchUtils.aStarSearch;
import static dev.kmunton.utils.algorithms.GraphSearchUtils.dijkstraSearchAllPathsGivenKnownMaxCost;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day16 implements Day<Integer, Integer> {

  private static final Map<GridPoint, Node> grid = new HashMap<>();
  private GridPoint start;
  private static GridPoint end;
  private int maxX;
  private int maxY;

  public Day16(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    maxY = input.size() - 1;
    maxX = input.get(0).length() - 1;
    for (int y = 0; y <= maxY; y++) {
      for (int x = 0; x <= maxX; x++) {
        GridPoint gridPoint = new GridPoint(x, y);
        String type = String.valueOf(input.get(y).charAt(x));
        Node node = new Node(type, gridPoint, 0, Direction2D.RIGHT, 0);
        grid.put(gridPoint, node);
        if ("S".equals(type)) {
          start = gridPoint;
        }
        if ("E".equals(type)) {
          end = gridPoint;
        }
      }
    }
  }

  @Override
  public Integer part1() {
    return aStarSearch(
        grid.get(start),
        n -> n.point().equals(grid.get(end).point()),
        Node::getNeighbors,
        Node::heuristic
    )
        .stream()
        .mapToInt(Node::cost).sum();
  }


  @Override
  public Integer part2() {
    long maxPoints = part1();
    return dijkstraSearchAllPathsGivenKnownMaxCost(
        grid.get(start),
        n -> n.point().equals(grid.get(end).point()),
        Node::getNeighbors,
        (int) maxPoints
    ).stream()
     .filter(l -> l.getLast().point().equals(end))
     .flatMap(l -> l.stream().map(Node::point))
     .collect(Collectors.toSet()).size();

  }

  record Node(String type, GridPoint point, Integer cost, Direction2D facing, Integer totalCost) {

    public Map<Node, Integer> getNeighbors() {
      return point().getCardinalNeighborsWithDirection()
                    .entrySet()
                    .stream()
                    .filter(e -> grid.containsKey(e.getValue())
                        && (grid.get(e.getValue()).type().equals(".") || grid.get(e.getValue()).type().equals("E")))
                    .map(e -> {
                      int neighborCost = 1;
                      if (!e.getKey().equals(facing())) {
                        neighborCost += 1000;
                      }
                      return new Node(grid.get(e.getValue()).type(), e.getValue(), neighborCost, e.getKey(), cost + neighborCost);
                    })
                    .collect(Collectors.toMap(n -> n, Node::cost));

    }

    public int heuristic() {
      return (end.manhattanDistance(point())) + cost();
    }

  }

  // Used for debugging
  private void printGrid(Set<GridPoint> points) {
    for (int y = 0; y <= maxY; y++) {
      StringBuilder line = new StringBuilder();
      for (int x = 0; x <= maxX; x++) {
        GridPoint gridPoint = new GridPoint(x, y);
        if (points.contains(gridPoint)) {
          line.append("O");
        } else {
          line.append(grid.get(gridPoint).type());
        }

      }
      log.info(line.toString());
    }
  }


}
