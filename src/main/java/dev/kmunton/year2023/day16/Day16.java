package dev.kmunton.year2023.day16;


import static dev.kmunton.utils.geometry.Direction2D.DOWN;
import static dev.kmunton.utils.geometry.Direction2D.LEFT;
import static dev.kmunton.utils.geometry.Direction2D.RIGHT;
import static dev.kmunton.utils.geometry.Direction2D.UP;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day16 implements Day<Long, Long> {

  private final List<List<Integer>> grid = new ArrayList<>();
  private int maxRow;
  private int maxCol;

  public Day16(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    maxRow = input.size();
    maxCol = input.get(0).length();
    for (var i = 0; i < input.size(); i++) {
      var row = input.get(i);
      var rowList = new ArrayList<Integer>();
      for (var j = 0; j < row.length(); j++) {
        var c = row.charAt(j);
        if (c == '|') {
          rowList.add(1);
        } else if (c == '-') {
          rowList.add(2);
        } else if (c == '\\') {
          rowList.add(3);
        } else if (c == '/') {
          rowList.add(4);
        } else {
          rowList.add(0);
        }
      }
      grid.add(rowList);
    }
  }

  public Long part1() {
    return getTotalEnergisedTiles(new Beam(0, 0, RIGHT));

  }

  public Long part2() {
    long max = 0;
    for (var i = 0; i < maxRow; i++) {
      for (var j = 0; j < maxCol; j++) {
        if ((j > 0 && j < maxCol - 1) && (i > 0 && i < maxRow - 1)) {
          continue;
        }

        if (i == 0) {
          var total = getTotalEnergisedTiles(new Beam(i, j, DOWN));
          if (total > max) {
            max = total;
          }
        }
        if (i == maxRow - 1) {
          var total = getTotalEnergisedTiles(new Beam(i, j, UP));
          if (total > max) {
            max = total;
          }
        }

        if (j == 0) {
          var total = getTotalEnergisedTiles(new Beam(i, j, RIGHT));
          if (total > max) {
            max = total;
          }
        }

        if (j == maxCol - 1) {
          var total = getTotalEnergisedTiles(new Beam(i, j, LEFT));
          if (total > max) {
            max = total;
          }
        }
      }
    }
    return max;
  }


  private long getTotalEnergisedTiles(Beam start) {
    List<Beam> beams = new ArrayList<>();
    beams.add(new Beam(start.getRow(), start.getCol(), start.getDirection()));
    Map<String, Boolean> visitedMap = new HashMap<>();

    while (!beams.isEmpty()) {
      var beam = beams.remove(0);
      if (visitedMap.containsKey(beam.getKey())) {
        continue;
      }
      visitedMap.put(beam.getKey(), true);
      var split = false;
      var type = grid.get(beam.getRow()).get(beam.getCol());
      switch (beam.getDirection()) {
        case RIGHT:
          if (type == 1) {
            splitBeamsVertical(beams, beam);
            split = true;
          } else if (type == 3) {
            beam.setDirection(DOWN);
          } else if (type == 4) {
            beam.setDirection(UP);
          }
          break;
        case LEFT:
          if (type == 1) {
            splitBeamsVertical(beams, beam);
            split = true;
          } else if (type == 3) {
            beam.setDirection(UP);
          } else if (type == 4) {
            beam.setDirection(DOWN);
          }
          break;
        case UP:
          if (type == 2) {
            splitBeamsHorizontal(beams, beam);
            split = true;
          } else if (type == 3) {
            beam.setDirection(LEFT);
          } else if (type == 4) {
            beam.setDirection(RIGHT);
          }
          break;
        case DOWN:
          if (type == 2) {
            splitBeamsHorizontal(beams, beam);
            split = true;
          } else if (type == 3) {
            beam.setDirection(RIGHT);
          } else if (type == 4) {
            beam.setDirection(LEFT);
          }
          break;
      }
      if (split) {
        continue;
      }
      var next = beam.moveByGivenDirection(beam.getDirection());
      if (next.isInBounds(maxCol - 1, maxRow - 1)) {
        beams.add(new Beam(next.y(), next.x(), beam.getDirection()));
      }

    }

    Set<GridPoint> visitedPoints = new HashSet<>();
    for (var entry : visitedMap.entrySet()) {
      var parts = entry.getKey().split("_");
      visitedPoints.add(new GridPoint(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
    }
    return visitedPoints.size();
  }

  private void splitBeamsHorizontal(List<Beam> beams, Beam beam) {
    var nextRight = beam.moveByGivenDirection(RIGHT);
    if (nextRight.isInBounds(maxCol - 1, maxRow - 1)) {
      beams.add(new Beam(nextRight.y(), nextRight.x(), RIGHT));
    }
    var nextLeft = beam.moveByGivenDirection(LEFT);
    if (nextLeft.isInBounds(maxCol - 1, maxRow - 1)) {
      beams.add(new Beam(nextLeft.y(), nextLeft.x(), LEFT));
    }
  }

  private void splitBeamsVertical(List<Beam> beams, Beam beam) {
    var nextUp = beam.moveByGivenDirection(UP);
    if (nextUp.isInBounds(maxCol - 1, maxRow - 1)) {
      beams.add(new Beam(nextUp.y(), nextUp.x(), UP));
    }
    var nextDown = beam.moveByGivenDirection(DOWN);
    if (nextDown.isInBounds(maxCol - 1, maxRow - 1)) {
      beams.add(new Beam(nextDown.y(), nextDown.x(), DOWN));
    }
  }
}
