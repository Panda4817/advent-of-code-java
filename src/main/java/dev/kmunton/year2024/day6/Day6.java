package dev.kmunton.year2024.day6;

import static dev.kmunton.utils.data.CopyUtils.copy;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day6 implements Day<Long, Long> {

  private static final String UNEXPECTED_VALUE = "Unexpected value: ";
  private final Map<GridPoint, Integer> lab = new HashMap<>();
  private GridPoint start;
  private int maxy;
  private int maxx;

  public Day6(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    for (int y = 0; y < input.size(); y++) {
      List<String> row = Arrays.stream(input.get(y).split("")).toList();
      for (int x = 0; x < row.size(); x++) {
        switch (row.get(x)) {
          case "." -> lab.put(new GridPoint(x, y), 0);
          case "#" -> lab.put(new GridPoint(x, y), -1);
          case "^" -> {
            lab.put(new GridPoint(x, y), 1);
            start = new GridPoint(x, y);
          }
          default -> throw new IllegalStateException(UNEXPECTED_VALUE + row.get(x));
        }
      }
    }
    maxy = input.size() - 1;
    maxx = input.get(0).length() - 1;
  }

  @Override
  public Long part1() {
    Map<GridPoint, Integer> newLab = mapOutGuardPath();
    return newLab.values().stream().filter(v -> v == 1).count();
  }

  private Map<GridPoint, Integer> mapOutGuardPath() {
    Direction2D currentDir = Direction2D.UP;
    GridPoint current = start;
    Map<GridPoint, Integer> newLab = copy(lab);
    while (current.canMoveGivenDirection(currentDir, maxx, maxy)) {
      GridPoint next = current.getNeighborGivenDirection(currentDir);
      switch (newLab.get(next)) {
        case 0 -> {
          newLab.put(next, 1);
          current = next;
        }
        case 1 -> current = next;
        case -1 -> currentDir = currentDir.getNewDirection(90);
        default -> throw new IllegalStateException(UNEXPECTED_VALUE + lab.get(next));
      }
    }
    return newLab;
  }

  @Override
  public Long part2() {
    Set<GridPoint> pointsToTry = mapOutGuardPath().entrySet().stream()
                                                  .filter(e -> e.getValue() == 1 && e.getKey() != start)
                                                  .map(Entry::getKey).collect(Collectors.toSet());
    return pointsToTry.stream().map(p -> {
      Map<GridPoint, Integer> newLab = copy(lab);
      newLab.put(p, -1);
      if (isCyclic(newLab)) {
        return 1L;
      }
      return 0L;
    }).reduce(0L, Long::sum);
  }

  private boolean isCyclic(Map<GridPoint, Integer> newLab) {
    Direction2D currentDir = Direction2D.UP;
    GridPoint current = start;
    Set<GridPointWithDirection> visited = new HashSet<>();
    visited.add(new GridPointWithDirection(current, currentDir));
    while (current.canMoveGivenDirection(currentDir, maxx, maxy)) {
      GridPoint next = current.getNeighborGivenDirection(currentDir);
      if (visited.contains(new GridPointWithDirection(next, currentDir))) {
        return true;
      }
      switch (newLab.get(next)) {
        case 0 -> {
          newLab.put(next, 1);
          current = next;
          visited.add(new GridPointWithDirection(next, currentDir));
        }
        case 1 -> {
          current = next;
          visited.add(new GridPointWithDirection(next, currentDir));
        }
        case -1 -> {
          currentDir = currentDir.getNewDirection(90);
          visited.add(new GridPointWithDirection(current, currentDir));
        }
        default -> throw new IllegalStateException(UNEXPECTED_VALUE + lab.get(next));
      }
    }
    return false;
  }


  record GridPointWithDirection(GridPoint point, Direction2D direction) {

  }


}
