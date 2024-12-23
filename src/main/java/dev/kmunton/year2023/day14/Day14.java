package dev.kmunton.year2023.day14;


import static dev.kmunton.utils.geometry.Direction2D.DOWN;
import static dev.kmunton.utils.geometry.Direction2D.LEFT;
import static dev.kmunton.utils.geometry.Direction2D.RIGHT;
import static dev.kmunton.utils.geometry.Direction2D.UP;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day14 implements Day<Long, Long> {

  private Set<GridPoint> rocks = new HashSet<>();
  private final Set<GridPoint> walls = new HashSet<>();

  private record State(long cycle, HashSet<GridPoint> rocks) {

  }

  private final Map<Integer, Integer> rowToLoad = new HashMap<>();
  private int maxRows = 0;
  private int maxCols = 0;

  public Day14(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    var load = input.size() + 1;
    maxRows = input.size();
    maxCols = input.get(0).length();
    for (int r = 0; r < input.size(); r++) {
      load--;
      rowToLoad.put(r, load);

      for (int c = 0; c < input.get(r).length(); c++) {
        if (input.get(r).charAt(c) == 'O') {
          rocks.add(new GridPoint(c, r));
        }
        if (input.get(r).charAt(c) == '#') {
          walls.add(new GridPoint(c, r));
        }
      }
    }
  }

  public Long part1() {
    tiltRocksUp();
    return (long) rocks.stream().mapToInt(r -> rowToLoad.get(r.y())).sum();

  }

  public Long part2() {
    var cycle = 1000000000;
    var states = new HashMap<String, State>();
    long i = 1;
    while (i <= cycle) {
      var key = rocks.stream().map(GridPoint::toString).reduce("", String::concat);
      if (states.containsKey(key)) {
        rocks = new HashSet<>(states.get(key).rocks);
        i += ((cycle - i) / (i - states.get(key).cycle) * (i - states.get(key).cycle)) + 1;
        continue;
      }

      tiltRocksUp();
      tiltRocksLeft();
      tiltRocksDown();
      tiltRocksRight();

      states.put(key, new State(i, new HashSet<>(rocks)));
      i++;

    }
    return (long) rocks.stream().mapToInt(r -> rowToLoad.get(r.y())).sum();
  }

  private void tiltRocksUp() {
    for (var r = 0; r < maxRows; r++) {
      for (var c = 0; c < maxCols; c++) {
        var rock = new GridPoint(c, r);
        if (walls.contains(rock)) {
          continue;
        }
        if (!rocks.contains(rock)) {
          continue;
        }
        rocks.remove(rock);
        while (rock.canMoveGivenDirectionAndBlockers(UP, maxCols - 1, maxRows - 1, walls, rocks)) {
          rock = rock.moveByGivenDirection(UP);
        }
        rocks.add(rock);
      }
    }
  }

  private void tiltRocksLeft() {
    for (var c = 1; c < maxCols; c++) {
      for (var r = 0; r < maxRows; r++) {
        var rock = new GridPoint(c, r);
        if (walls.contains(rock)) {
          continue;
        }
        if (!rocks.contains(rock)) {
          continue;
        }
        rocks.remove(rock);
        while (rock.canMoveGivenDirectionAndBlockers(LEFT, maxCols - 1, maxRows - 1, walls, rocks)) {
          rock = rock.moveByGivenDirection(LEFT);
        }
        rocks.add(rock);
      }
    }
  }

  private void tiltRocksDown() {
    for (var r = maxRows - 2; r >= 0; r--) {
      for (var c = 0; c < maxCols; c++) {
        var rock = new GridPoint(c, r);
        if (walls.contains(rock)) {
          continue;
        }
        if (!rocks.contains(rock)) {
          continue;
        }
        rocks.remove(rock);
        while (rock.canMoveGivenDirectionAndBlockers(DOWN, maxCols - 1, maxRows - 1, walls, rocks)) {
          rock = rock.moveByGivenDirection(DOWN);
        }
        rocks.add(rock);
      }
    }
  }

  private void tiltRocksRight() {
    for (var c = maxCols - 2; c >= 0; c--) {
      for (var r = 0; r < maxRows; r++) {
        var rock = new GridPoint(c, r);
        if (walls.contains(rock)) {
          continue;
        }
        if (!rocks.contains(rock)) {
          continue;
        }
        rocks.remove(rock);
        while (rock.canMoveGivenDirectionAndBlockers(RIGHT, maxCols - 1, maxRows - 1, walls, rocks)) {
          rock = rock.moveByGivenDirection(RIGHT);
        }
        rocks.add(rock);
      }
    }
  }
}
