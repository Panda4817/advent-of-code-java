package dev.kmunton.year2024.day10;


import static dev.kmunton.utils.algorithms.GraphSearchUtils.findAllPathsWithBfs;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day10 implements Day<Long, Long> {

  private final Map<GridPoint, Node> nodes = new HashMap<>();
  private int maxX;
  private int maxY;

  public Day10(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    maxY = input.size() - 1;
    maxX = input.get(0).length() - 1;
    for (int y = 0; y < input.size(); y++) {
      for (int x = 0; x < input.get(y).length(); x++) {
        GridPoint point = new GridPoint(x, y);
        nodes.put(point, new Node(Integer.parseInt(String.valueOf(input.get(y).charAt(x))), new GridPoint(x, y)));
      }
    }
  }

  @Override
  public Long part1() {
    return getScore(true);
  }

  @Override
  public Long part2() {
    return getScore(false);
  }

  private long getScore(boolean distinct) {
    List<Node> starts = nodes.values().stream().filter(node -> node.value() == 0).toList();
    long sum = 0;
    for (Node node : starts) {
      sum += findAllPathsWithBfs(node,
          n -> n.value() == 9,
          n -> n.point().getCardinalNeighbors().stream()
                .filter(p -> p.isInBounds(maxX, maxY))
                .map(nodes::get)
                .filter(Objects::nonNull)
                .filter(neighbour -> neighbour.value() == n.value() + 1)
                .toList(), distinct)
          .size();
    }
    return sum;
  }

  record Node(int value, GridPoint point) {

  }

}
