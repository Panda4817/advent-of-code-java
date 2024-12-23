package dev.kmunton.year2021.day9;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 implements Day<Integer, Integer> {

  private List<List<CavePoint>> map;
  private int height;
  private int width;
  private final int[] dx = {0, 0, -1, 1};
  private final int[] dy = {-1, 1, 0, 0};

  public Day9(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    map = new ArrayList<>();
    int y = 0;
    for (String s : input) {
      List<Integer> parts = (s.chars().mapToObj(c -> (char) c).collect(Collectors.toList())).stream().map(c -> Integer.parseInt(String.valueOf(c)))
                                                                                            .collect(Collectors.toList());
      List<CavePoint> row = new ArrayList<>();
      int x = 0;
      for (Integer height : parts) {
        CavePoint cp = new CavePoint(x, y, height);
        row.add(cp);
        x += 1;
      }
      map.add(row);
      y += 1;
    }
    height = map.size();
    width = map.get(0).size();
  }

  private boolean isLower(CavePoint p) {
    for (int i = 0; i < 4; i++) {
      int y = p.getY() + dy[i];
      int x = p.getX() + dx[i];
      if (x < 0 || x >= width || y < 0 || y >= height) {
        continue;
      }
      int h = map.get(y).get(x).getHeight();
      if (p.getHeight() >= h) {
        return false;
      }
    }
    return true;
  }

  private boolean keepSearching(int x, int y, CavePoint c) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return false;
    }

    return map.get(y).get(x).getHeight() != 9 && map.get(y).get(x).getHeight() > c.getHeight();
  }

  private int bfs(CavePoint start) {
    int basin = 0;
    List<CavePoint> toSearch = new ArrayList<>();
    List<CavePoint> counted = new ArrayList<>();
    toSearch.add(start);

    while (toSearch.size() > 0) {
      CavePoint c = toSearch.remove(0);
      if (counted.contains(c)) {
        continue;
      }
      basin += 1;
      counted.add(c);
      for (int i = 0; i < 4; i++) {
        int y = c.getY() + dy[i];
        int x = c.getX() + dx[i];
        if (keepSearching(x, y, c)) {
          toSearch.add(map.get(y).get(x));
        }
      }
    }

    return basin;
  }

  @Override
  public Integer part1() {
    int sum = 0;
    for (List<CavePoint> row : map) {
      for (CavePoint col : row) {
        if (isLower(col)) {
          sum += (col.getHeight() + 1);

        }
      }
    }

    return sum;

  }

  @Override
  public Integer part2() {
    List<Integer> ints = new ArrayList<>();
    for (List<CavePoint> row : map) {
      for (CavePoint col : row) {
        if (isLower(col)) {
          int basinSize = bfs(col);
          ints.add(basinSize);

        }
      }
    }

    List<Integer> sortedList = ints.stream().sorted().collect(Collectors.toList());
    int size = sortedList.size();
    return (sortedList.get(size - 1) * sortedList.get(size - 2) * sortedList.get(size - 3));
  }
}
