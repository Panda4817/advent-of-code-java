package dev.kmunton.year2021.day7;

import dev.kmunton.utils.days.Day;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 implements Day<Integer, Integer> {

  private static final boolean ASC = true;
  List<Integer> data;
  Map<Integer, Integer> mapData;

  public Day7(List<String> input) {
    processData(input);
  }


  @Override
  public void processData(List<String> input) {
    mapData = new HashMap<>();
    data = Stream.of(input.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
    for (Integer i : data) {
      int count = mapData.getOrDefault(i, 0);
      mapData.put(i, (int) data.stream().filter(n -> n == i).count());
    }
  }

  @Override
  public Integer part1() {
    int currentLowest = 1000000000;
    int totalFuel = 0;
    for (int i : mapData.keySet()) {
      totalFuel = 0;
      for (int j : data) {
        totalFuel += Math.abs(i - j);
      }
      if (totalFuel < currentLowest) {
        currentLowest = totalFuel;
      }
    }
    return currentLowest;
  }

  @Override
  public Integer part2() {
    int max = data.stream().max(Integer::max).get();
    int currentLowest = Integer.MAX_VALUE;
    int totalFuel;
    for (int i = 0; i < max + 1; i++) {
      totalFuel = 0;
      for (int j : data) {
        int steps = Math.abs(i - j);
        for (int x = 0; x < steps; x++) {
          totalFuel += x + 1;
        }
      }
      if (totalFuel < currentLowest) {
        currentLowest = totalFuel;
      }
    }
    return currentLowest;
  }
}
