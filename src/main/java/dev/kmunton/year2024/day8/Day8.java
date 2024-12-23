package dev.kmunton.year2024.day8;


import static dev.kmunton.utils.data.CollectionsUtils.generateCombinations;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import dev.kmunton.utils.geometry.GridUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day8 implements Day<Long, Long> {

  private final Map<GridPoint, String> city = new HashMap<>();
  private final Map<String, List<GridPoint>> antennas = new HashMap<>();
  private static final GridUtils GRID_UTILS = new GridUtils();
  private int maxX;
  private int maxY;

  public Day8(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    for (int y = 0; y < input.size(); y++) {
      List<String> row = Arrays.stream(input.get(y).split("")).toList();
      for (int x = 0; x < row.size(); x++) {
        city.put(new GridPoint(x, y), row.get(x));
      }
    }
    for (Entry<GridPoint, String> entry : city.entrySet()) {
      if (entry.getValue().equals(".")) {
        continue;
      }
      List<GridPoint> points = antennas.getOrDefault(entry.getValue(), new ArrayList<>());
      points.add(entry.getKey());
      antennas.put(entry.getValue(), points);
    }
    maxX = GRID_UTILS.maxX(city);
    maxY = GRID_UTILS.maxY(city);
  }

  @Override
  public Long part1() {
    Set<GridPoint> antiNodes = new HashSet<>();
    antennas.values().forEach(row -> {
      List<List<GridPoint>> combinations = generateCombinations(row, 2);
      combinations.forEach(combination -> {
        combination = orderBasedOnYAxis(combination);
        addAntiNodes(antiNodes, combination, true);
      });
    });
    return (long) antiNodes.size();
  }

  @Override
  public Long part2() {
    Set<GridPoint> antiNodes = new HashSet<>();
    antennas.values().forEach(row -> {
      List<List<GridPoint>> combinations = generateCombinations(row, 2);
      combinations.forEach(combination -> {
        combination = orderBasedOnYAxis(combination);
        antiNodes.addAll(combination);
        addAntiNodes(antiNodes, combination, false);
      });
    });
    return (long) antiNodes.size();
  }

  private List<GridPoint> orderBasedOnYAxis(List<GridPoint> combination) {
    GridPoint first = combination.get(0);
    GridPoint second = combination.get(1);
    if (first.y() > second.y()) {
      first = combination.get(1);
      second = combination.get(0);
    }
    return Arrays.asList(first, second);
  }

  private void addAntiNodes(Set<GridPoint> antiNodes, List<GridPoint> combination, boolean onlyOne) {
    GridPoint first = combination.get(0);
    GridPoint second = combination.get(1);
    int diffX = first.differenceX(second);
    int diffY = first.differenceY(second);
    if (second.x() > first.x()) {
      addAntiNodes(antiNodes, first, -diffX, -diffY, onlyOne);
      addAntiNodes(antiNodes, second, diffX, diffY, onlyOne);
      return;
    }
    addAntiNodes(antiNodes, first, diffX, -diffY, onlyOne);
    addAntiNodes(antiNodes, second, -diffX, diffY, onlyOne);
  }

  private void addAntiNodes(Set<GridPoint> antinodes, GridPoint point, int diffX, int diffY, boolean onlyOne) {
    GridPoint antinode = new GridPoint(point.x() + diffX, point.y() + diffY);
    while (antinode.isInBounds(maxX, maxY)) {
      antinodes.add(antinode);
      if (onlyOne) {
        break;
      }
      antinode = new GridPoint(antinode.x() + diffX, antinode.y() + diffY);
    }
  }

}
