package dev.kmunton.year2021.day11;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 implements Day<Integer, Integer> {

  List<List<Octopus>> data;
  private int height;
  private int width;
  private final int steps = 100;
  private final int[] dx = {0, 0, -1, 1, -1, 1, -1, 1};
  private final int[] dy = {-1, 1, 0, 0, -1, -1, 1, 1};

  public Day11(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    data = new ArrayList<>();
    int y = 0;
    for (String s : input) {
      List<Integer> parts = (s.chars().mapToObj(c -> (char) c).collect(Collectors.toList())).stream().map(c -> Integer.parseInt(String.valueOf(c)))
                                                                                            .collect(Collectors.toList());
      List<Octopus> row = new ArrayList<>();
      int x = 0;
      for (Integer height : parts) {
        Octopus o = new Octopus(x, y, height);
        row.add(o);
        x += 1;
      }
      data.add(row);
      y += 1;
    }
    height = data.size();
    width = data.get(0).size();
  }

  @Override
  public Integer part1() {
    int step = 0;
    int countFlash = 0;
    while (step < 100) {
      List<Octopus> q = new ArrayList<>();
      for (List<Octopus> lst : data) {
        for (Octopus o : lst) {
          int newEnergy = o.getEnergy() + 1;
          if (newEnergy == 10) {
            o.setEnergy(0);
            q.add(o);
          } else {
            o.setEnergy(newEnergy);
          }

        }
      }

      while (q.size() > 0) {
        Octopus o = q.remove(0);
        countFlash += 1;
        for (int i = 0; i < 8; i++) {
          int r = dy[i] + o.getY();
          int c = dx[i] + o.getX();
          if (c < 0 || c >= width || r < 0 || r >= height) {
            continue;
          }
          Octopus n = data.get(r).get(c);
          if (n.getEnergy() == 0) {
            continue;
          }
          int newEnergy = n.getEnergy() + 1;
          if (newEnergy == 10) {
            n.setEnergy(0);
            q.add(n);
          } else {
            n.setEnergy(newEnergy);
          }
        }
      }

      step += 1;

    }
    return countFlash;
  }

  @Override
  public Integer part2() {
    int step = 0;
    int countFlash = 0;
    while (true) {
      int flashes = 0;
      List<Octopus> q = new ArrayList<>();
      for (List<Octopus> lst : data) {
        for (Octopus o : lst) {
          int newEnergy = o.getEnergy() + 1;
          if (newEnergy == 10) {
            o.setEnergy(0);
            q.add(o);
          } else {
            o.setEnergy(newEnergy);
          }

        }
      }

      while (q.size() > 0) {
        Octopus o = q.remove(0);
        countFlash += 1;
        flashes += 1;
        for (int i = 0; i < 8; i++) {
          int r = dy[i] + o.getY();
          int c = dx[i] + o.getX();
          if (c < 0 || c >= width || r < 0 || r >= height) {
            continue;
          }
          Octopus n = data.get(r).get(c);
          if (n.getEnergy() == 0) {
            continue;
          }
          int newEnergy = n.getEnergy() + 1;
          if (newEnergy == 10) {
            n.setEnergy(0);
            q.add(n);
          } else {
            n.setEnergy(newEnergy);
          }
        }
      }

      step += 1;
      if (flashes == 100) {
        break;
      }

    }
    return step + 100; // 100 steps completed in part1;
  }

  public int part2Test() {
    return part2() - 100; // Data is loaded again for each test
  }

}
