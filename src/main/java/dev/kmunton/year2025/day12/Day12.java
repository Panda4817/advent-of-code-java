package dev.kmunton.year2025.day12;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.GridPoint;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day12 implements Day<Long, Long> {

    private final Map<Integer, Map<GridPoint, String>> presents = new HashMap<>();
    private final List<List<Integer>> grids  = new ArrayList<>();

  public Day12(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
      boolean isPresents = false;
      int presentId = 0;
      int y = -1;
      Map<GridPoint, String> presentMap = new HashMap<>();
      for (String line : input) {
          if (line.isEmpty()) {
              isPresents = false;
              presents.put(presentId, new HashMap<>(presentMap));
              presentMap.clear();
              y = -1;
              continue;
          }
          if (line.length() == 2) {
              isPresents = true;
              presentId =  Integer.parseInt(line.substring(0, 1));
              continue;
          }
          if (isPresents) {
              y += 1;
              for (int x = 0; x < line.length(); x++) {
                  presentMap.put(new  GridPoint(x, y), line.substring(x, x + 1));
              }
              continue;
          }
          String[] parts = line.split(": ");
          List<Integer> grid = new ArrayList<>();
          Arrays.stream(parts[0].split("x")).forEach(i -> grid.add(Integer.valueOf(i)));
          Arrays.stream(parts[1].split(" ")).forEach(i -> grid.add(Integer.valueOf(i)));
          grids.add(grid);
      }
  }

  @Override
  public Long part1() {
      Map<Integer, Integer> totalSquaresPerPresent = new HashMap<>();
      for (Map.Entry<Integer, Map<GridPoint, String>> entry : presents.entrySet()) {
          Integer key = entry.getKey();
          int totalSquares = getWidthsPerRow(entry.getValue()).stream().mapToInt(Integer::intValue).sum();
          totalSquaresPerPresent.put(key, totalSquares);
      }
      List<List<Integer>> gridsFiltered = new ArrayList<>();
      for (List<Integer> grid : grids) {
          int x = grid.get(0);
          int y = grid.get(1);
          List<Integer> whichPresents = grid.subList(2, grid.size());
          long area = (long) x * y;
          long totalPresentSquares = 0L;
          for (int i = 0; i < whichPresents.size(); i++) {
              long numberOfPresents = whichPresents.get(i);
              long squaresUsed = numberOfPresents * totalSquaresPerPresent.get(i);
              totalPresentSquares += squaresUsed;
          }
          if (totalPresentSquares <= area) {
              gridsFiltered.add(new ArrayList<>(grid));
          }

      }
      log.info("{} {}", grids.size(), gridsFiltered.size());

    return (long) gridsFiltered.size();
  }

  private List<Integer> getWidthsPerRow(Map<GridPoint, String> grid) {
      List<Integer> widths = new ArrayList<>();
      // Assume all presents have 3 rows and columns
      for (int y = 0; y < 3; y++) {
        int width = 0;
        for (int x = 0; x < 3; x++) {
             String s = grid.get(new GridPoint(x, y));
             if (Objects.equals(s, "#")) {
                 width++;
             }
        }
        widths.add(width);
      }
      return widths;
  }

  private void printShape(Map<GridPoint, String> grid) {
      for (int y = 0; y < 3; y++) {
          StringBuilder line = new StringBuilder();
          for (int x = 0; x < 3; x++) {
              line.append(grid.get(new GridPoint(x, y)));
          }
          log.info(line.toString());
      }
  }


  @Override
  public Long part2() {
    return -1L;
  }

}
