package dev.kmunton.year2023.day10;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day10 implements Day<Long, Long> {

  private final List<List<Tile>> grid = new ArrayList<>();
  private GridPoint start;

  public Day10(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    for (var r = 0; r < input.size(); r++) {
      var row = input.get(r).split("");
      var rowList = new ArrayList<Tile>();
      for (var c = 0; c < row.length; c++) {
        var tile = new Tile(row[c], new GridPoint(c, r));
        if (Objects.equals(tile.getType(), "S")) {
          start = new GridPoint(c, r);
        }
        rowList.add(tile);
      }
      grid.add(rowList);
    }

  }

  public Long part1() {
    var mainLoopRowCols = getMainLoopRowCols();
    return (long) (mainLoopRowCols.size() / 2);

  }

  public Long part2() {
    var mainLoopRowCols = getMainLoopRowCols();
    var enclosedTotal = 0;
    for (var row = 0; row < grid.size(); row++) {
      if (row == 0 || row == grid.size() - 1) {
        continue;
      }
      var mainLoopPipeCount = 0;
      for (var col = 0; col < grid.get(row).size(); col++) {
        if (col == grid.get(row).size() - 1) {
          continue;
        }
        var tile = grid.get(row).get(col);
        if (tile.isMainLoop(mainLoopRowCols)) {
          if (tile.getType().equals("|")) {
            mainLoopPipeCount++;
          }
          if (tile.getType().equals("7") || tile.getType().equals("F")) {
            mainLoopPipeCount += 1;
          }
        } else {
          var countMainPipesAfter = mainLoopRowCols.stream()
                                                   .filter(mainLoopRowCol -> mainLoopRowCol.y() == tile.getRowCol().y()
                                                       && mainLoopRowCol.x() > tile.getRowCol().x())
                                                   .count();
          if (countMainPipesAfter == 0) {
            continue;
          }
          if (mainLoopPipeCount % 2 != 0) {
            enclosedTotal++;
          }
        }

      }
    }
    return (long) enclosedTotal;
  }

  private List<GridPoint> getMainLoopRowCols() {
    List<GridPoint> queue = new ArrayList<>();
    queue.add(start);
    var visited = new ArrayList<GridPoint>();
    var mainLoopRowCols = new ArrayList<GridPoint>();
    while (!queue.isEmpty()) {
      var current = queue.remove(0);
      visited.add(current);
      var tile = grid.get(current.y()).get(current.x());

      var adjacentRowCols = tile.getPossibleNeighbours();
      var count = 0;
      for (var adjacentRowCol : adjacentRowCols) {
        if (adjacentRowCol.y() < 0 || adjacentRowCol.y() >= grid.size()) {
          continue;
        }
        if (adjacentRowCol.x() < 0 || adjacentRowCol.x() >= grid.get(adjacentRowCol.y()).size()) {
          continue;
        }
        if (queue.contains(adjacentRowCol) || visited.contains(adjacentRowCol)) {
          count++;
          continue;
        }
        var adjacentTile = grid.get(adjacentRowCol.y()).get(adjacentRowCol.x());
        if (adjacentTile.getPossibleNeighbours().contains(current)) {
          queue.add(adjacentRowCol);
          count++;
        }
      }

      if (count >= 2) {
        mainLoopRowCols.add(current);
      }

    }
    return mainLoopRowCols;
  }

  private void printGrid(List<GridPoint> mainLoopPoints, boolean onlyMainLoop, List<GridPoint> enclosedPoints) {
    for (var row : grid) {
      for (var tile : row) {
        if (onlyMainLoop) {
          System.out.print(tile.isMainLoop(mainLoopPoints) ? tile.getType() : ".");
          continue;
        } else if (enclosedPoints != null) {
          System.out.print(enclosedPoints.contains(tile.getRowCol())
              ? "X"
              : tile.isMainLoop(mainLoopPoints) ? tile.getType() : ".");
          continue;
        }
        System.out.print(tile.getType());
      }
      System.out.println();
    }
  }


}
