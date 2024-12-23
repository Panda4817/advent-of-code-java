package dev.kmunton.year2024.day15;


import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.Direction2D;
import dev.kmunton.utils.geometry.GridPoint;
import dev.kmunton.utils.geometry.GridUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day15 implements Day<Long, Long> {

  private static final GridUtils<Integer> GRID_UTILS = new GridUtils<>();
  private final Map<GridPoint, Integer> grid = new HashMap<>();
  private final List<Direction2D> directions = new ArrayList<>();

  public Day15(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    int instructionsY = 0;
    for (int y = 0; y < input.size(); y++) {
      if (input.get(y).isEmpty()) {
        instructionsY = y + 1;
        break;
      }
      List<Integer> row = Arrays.stream(input.get(y).split("")).map(s -> switch (s) {
        case "#" -> -1;
        case "." -> 0;
        case "O" -> 1;
        case "@" -> 2;
        default -> throw new IllegalStateException("Unexpected value: " + s);
      }).toList();
      for (int x = 0; x < row.size(); x++) {
        grid.put(new GridPoint(x, y), row.get(x));
      }
    }
    for (int y = instructionsY; y < input.size(); y++) {
      Arrays.stream(input.get(y).split("")).forEach(s -> {
        Direction2D direction = switch (s) {
          case "^" -> Direction2D.UP;
          case "v" -> Direction2D.DOWN;
          case "<" -> Direction2D.LEFT;
          case ">" -> Direction2D.RIGHT;
          default -> throw new IllegalStateException("Unexpected value: " + s);
        };
        directions.add(direction);
      });
    }
  }

  @Override
  public Long part1() {
    Map<GridPoint, Integer> gridCopy = new HashMap<>(grid);
    doMovements(gridCopy, 2);
    printGridPart1(gridCopy);
    return sumBoxGPSCoordinates(gridCopy);
  }


  @Override
  public Long part2() {
    Map<GridPoint, Integer> gridCopy = new HashMap<>();
    grid.forEach((key, value) -> {
      switch (value) {
        case 1 -> {
          gridCopy.put(new GridPoint(key.x() * 2, key.y()), 1);
          gridCopy.put(new GridPoint((key.x() * 2) + 1, key.y()), 2);
        }
        case 2 -> {
          gridCopy.put(new GridPoint(key.x() * 2, key.y()), 3);
          gridCopy.put(new GridPoint((key.x() * 2) + 1, key.y()), 0);
        }
        default -> {
          gridCopy.put(new GridPoint(key.x() * 2, key.y()), value);
          gridCopy.put(new GridPoint((key.x() * 2) + 1, key.y()), value);
        }
      }
    });

    doMovements(gridCopy, 3);
    printGridPart2(gridCopy);
    return sumBoxGPSCoordinates(gridCopy);

  }


  private void doMovements(Map<GridPoint, Integer> gridCopy, int robotInt) {
    for (Direction2D direction : directions) {
      GridPoint robot = gridCopy.entrySet().stream().filter(e -> e.getValue() == robotInt).findFirst().orElseThrow().getKey();
      List<GridPoint> line = new ArrayList<>();
      List<GridPoint> queue = new ArrayList<>();
      queue.add(robot);
      Set<Boolean> canMove = new HashSet<>();
      while (!queue.isEmpty()) {
        GridPoint g = queue.removeFirst();
        if (!line.contains(g)) {
          line.add(g);
        }
        GridPoint next = g.getNeighborGivenDirection(direction);
        if (gridCopy.containsKey(next) && gridCopy.get(next) == 0) {
          canMove.add(true);
          continue;
        }
        if (gridCopy.containsKey(next) && gridCopy.get(next) == -1) {
          canMove.add(false);
          continue;
        }
        if (gridCopy.containsKey(next)) {
          queue.add(next);
          if (robotInt == 3 && (direction == Direction2D.UP || direction == Direction2D.DOWN)) {
            int val = gridCopy.get(next);
            GridPoint otherSide = null;
            if (val == 2) {
              otherSide = next.moveByGivenDirection(Direction2D.LEFT);
            }
            if (val == 1) {
              otherSide = next.moveByGivenDirection(Direction2D.RIGHT);
            }
            if (!queue.contains(otherSide) && !line.contains(otherSide)) {
              queue.add(otherSide);
            }
          }
        }


      }
      if (canMove.stream().allMatch(b -> b)) {
        line = line.reversed();
        for (GridPoint g : line) {
          if (g.equals(robot)) {
            GridPoint newRobot = g.moveByGivenDirection(direction);
            gridCopy.put(newRobot, robotInt);
            gridCopy.put(g, 0);
          } else if (gridCopy.get(g) == 1) {
            gridCopy.put(g.moveByGivenDirection(direction), 1);
            if (robotInt == 3 && (direction == Direction2D.UP || direction == Direction2D.DOWN)) {
              gridCopy.put(g, 0);
            }
          } else if (robotInt == 3) {
            gridCopy.put(g.moveByGivenDirection(direction), 2);
            if (direction == Direction2D.UP || direction == Direction2D.DOWN) {
              gridCopy.put(g, 0);
            }
          }
        }
      }
    }
  }


  private long sumBoxGPSCoordinates(Map<GridPoint, Integer> grid) {
    return grid.entrySet().stream()
               .filter(e -> e.getValue() == 1)
               .map(e -> (100 * e.getKey().y()) + e.getKey().x())
               .mapToLong(Integer::longValue)
               .sum();
  }

  private void printGridPart1(Map<GridPoint, Integer> grid) {
    printGrid(grid, i -> switch (i) {
      case -1 -> "#";
      case 0 -> ".";
      case 1 -> "O";
      case 2 -> "@";
      default -> throw new IllegalStateException("Unexpected value");
    });
  }

  private void printGridPart2(Map<GridPoint, Integer> grid) {
    printGrid(grid, i -> switch (i) {
      case -1 -> "#";
      case 0 -> ".";
      case 1 -> "[";
      case 2 -> "]";
      case 3 -> "@";
      default -> throw new IllegalStateException("Unexpected value");
    });
  }

  private void printGrid(Map<GridPoint, Integer> grid, IntFunction<String> typeToString) {
    int maxX = GRID_UTILS.maxX(grid);
    int maxY = GRID_UTILS.maxY(grid);
    for (int y = 0; y <= maxY; y++) {
      StringBuilder line = new StringBuilder();
      for (int x = 0; x <= maxX; x++) {
        String s = typeToString.apply(grid.get(new GridPoint(x, y)));
        line.append(s);
      }
      log.info(line.toString());
    }
    log.info("");
  }


}
