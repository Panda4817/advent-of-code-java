package dev.kmunton.year2023.day11;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.List;

public class Day11 implements Day<Long, Long> {

  private final List<GridPoint> galaxies = new ArrayList<>();
  private final List<GridPoint> space = new ArrayList<>();
  private final List<Integer> emptyRows = new ArrayList<>();
  private final List<Integer> emptyCols = new ArrayList<>();

  public Day11(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    for (var row = 0; row < input.size(); row++) {
      var cols = input.get(row).split("");
      for (var col = 0; col < cols.length; col++) {
        if (cols[col].equals(".")) {
          space.add(new GridPoint(col, row));
        } else {
          galaxies.add(new GridPoint(col, row));
        }
      }
    }

    var initialRows = input.size();
    var initialCols = input.get(0).length();

    for (var row = 0; row < initialRows; row++) {
      var rowFinal = row;
      if (space.stream().filter(s -> s.y() == rowFinal).count() == initialCols) {
        emptyRows.add(row);
      }
    }
    for (var col = 0; col < initialCols; col++) {
      var colFinal = col;
      if (space.stream().filter(s -> s.x() == colFinal).count() == initialRows) {
        emptyCols.add(col);
      }
    }
  }

  public Long part1() {
    return getTotalSteps(1);

  }

  public Long part2() {
    return getTotalSteps(999999);
  }

  public long getTotalSteps(int universeRate) {
    var pairs = getPairs();
    var totalOfSteps = 0L;
    for (var pair : pairs) {
      var pair1 = new GridPoint(pair.get(0).x(), pair.get(0).y());
      var pair2 = new GridPoint(pair.get(1).x(), pair.get(1).y());
      pair1 = adjustRowsByUniversalRate(pair1, pair.get(0), universeRate);
      pair2 = adjustRowsByUniversalRate(pair2, pair.get(1), universeRate);
      pair1 = adjustColsByUniversalRate(pair1, pair.get(0), universeRate);
      pair2 = adjustColsByUniversalRate(pair2, pair.get(1), universeRate);
      totalOfSteps += pair1.manhattanDistance(pair2);
    }
    return totalOfSteps;
  }

  private List<List<GridPoint>> getPairs() {
    var pairs = new ArrayList<List<GridPoint>>();
    for (var i = 0; i < galaxies.size(); i++) {
      for (var j = i + 1; j < galaxies.size(); j++) {
        pairs.add(List.of(galaxies.get(i), galaxies.get(j)));
      }
    }
    return pairs;
  }

  private GridPoint adjustRowsByUniversalRate(GridPoint newPoint, GridPoint oldPoint, int universeRate) {
    int y = newPoint.y();
    for (var row : emptyRows) {
      if (oldPoint.y() >= row) {
        y += universeRate;
        newPoint = new GridPoint(newPoint.x(), y);
      }
    }
    return newPoint;
  }

  private GridPoint adjustColsByUniversalRate(GridPoint newPoint, GridPoint oldPoint, int universeRate) {
    int x = newPoint.x();
    for (var col : emptyCols) {
      if (oldPoint.x() >= col) {
        x += universeRate;
        newPoint = new GridPoint(x, newPoint.y());
      }
    }
    return newPoint;
  }


}
