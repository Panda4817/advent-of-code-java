package dev.kmunton.year2024.day12;


import static dev.kmunton.utils.geometry.Direction2D.DOWN;
import static dev.kmunton.utils.geometry.Direction2D.DOWN_LEFT;
import static dev.kmunton.utils.geometry.Direction2D.DOWN_RIGHT;
import static dev.kmunton.utils.geometry.Direction2D.LEFT;
import static dev.kmunton.utils.geometry.Direction2D.RIGHT;
import static dev.kmunton.utils.geometry.Direction2D.UP;
import static dev.kmunton.utils.geometry.Direction2D.UP_LEFT;
import static dev.kmunton.utils.geometry.Direction2D.UP_RIGHT;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day12 implements Day<Long, Long> {

  private final Map<GridPoint, Plot> garden = new HashMap<>();
  private final Map<String, List<Plot>> plots = new HashMap<>();
  private static final List<List<Direction2D>> ADJACENT_PAIRS = new ArrayList<>();

  static {
    ADJACENT_PAIRS.add(List.of(UP, LEFT, UP_LEFT));
    ADJACENT_PAIRS.add(List.of(DOWN, RIGHT, DOWN_RIGHT));
    ADJACENT_PAIRS.add(List.of(UP, RIGHT, UP_RIGHT));
    ADJACENT_PAIRS.add(List.of(DOWN, LEFT, DOWN_LEFT));
  }

  public Day12(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    for (int y = 0; y < input.size(); y++) {
      String line = input.get(y);
      for (int x = 0; x < line.length(); x++) {
        GridPoint gridPoint = new GridPoint(x, y);
        String plotType = String.valueOf(line.charAt(x));
        Plot plot = new Plot(plotType, gridPoint);
        List<Plot> plotsList = plots.getOrDefault(plotType, new ArrayList<>());
        plotsList.add(plot);
        plots.put(plotType, plotsList);
        garden.put(gridPoint, plot);
      }
    }
  }


  @Override
  public Long part1() {
    return getTotalPrice((neighbors, currentPlot) -> (long) (4 - neighbors.values().size()));
  }

  @Override
  public Long part2() {
    return getTotalPrice(
        (neighbors, currentPlot) -> {
          long sides = 0;
          sides += ADJACENT_PAIRS.stream()
                                 .filter(p -> !neighbors.containsKey(p.getFirst()) && !neighbors.containsKey(p.get(1)))
                                 .count();
          sides += ADJACENT_PAIRS.stream()
                                 .filter(p -> {
                                   if (neighbors.containsKey(p.get(0))
                                       && neighbors.containsKey(p.get(1))) {
                                     GridPoint diagonal = currentPlot.point().moveByGivenDirection(p.get(2));
                                     return garden.containsKey(diagonal)
                                         && !garden.get(diagonal).type().equals(currentPlot.type());
                                   }
                                   return false;
                                 })
                                 .count();
          return sides;
        }
    );
  }

  private long getTotalPrice(BiFunction<Map<Direction2D, Plot>, Plot, Long> calculateFactor) {
    long totalPrice = 0;

    for (Entry<String, List<Plot>> entry : plots.entrySet()) {
      Set<Set<Plot>> groups = new HashSet<>();
      for (Plot plot : entry.getValue()) {
        if (groups.stream().anyMatch(g -> g.contains(plot))) {
          continue;
        }
        Set<Plot> group = new HashSet<>();
        long priceFactor = 0;
        List<Plot> stack = new ArrayList<>();
        stack.add(plot);
        while (!stack.isEmpty()) {
          Plot nextPlot = stack.removeLast();
          group.add(nextPlot);
          Map<Direction2D, Plot> nextPlotNeighbors = nextPlot.point()
                                                             .getCardinalNeighborsWithDirection()
                                                             .entrySet()
                                                             .stream()
                                                             .filter(e -> garden.containsKey(e.getValue()) && Objects.equals(
                                                                 garden.get(e.getValue()).type(), nextPlot.type()))
                                                             .map(e -> Map.entry(e.getKey(), garden.get(e.getValue())))
                                                             .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

          priceFactor += calculateFactor.apply(nextPlotNeighbors, nextPlot);

          stack.addAll(nextPlotNeighbors.values().stream().filter(p -> !group.contains(p) && !stack.contains(p)).toList());
        }
        groups.add(group);
        totalPrice += (group.size() * priceFactor);
      }
    }
    return totalPrice;
  }


  record Plot(String type, GridPoint point) {

  }

}
