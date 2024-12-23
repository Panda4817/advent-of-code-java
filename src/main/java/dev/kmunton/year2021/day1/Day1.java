package dev.kmunton.year2021.day1;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.List;

public class Day1 implements Day<Integer, Integer> {

  private final List<Integer> data = new ArrayList<>();

  public Day1(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> data.add(Integer.parseInt(line)));
  }

  public Integer part1() {
    int length = data.size();

    int totalIncreased = 0;

    for (int i = 0; i < length; i = i + 1) {
      if (i == 0) {
        continue;
      }

      if (data.get(i) > data.get(i - 1)) {
        totalIncreased += 1;
      }
    }

    return totalIncreased;

  }

  public Integer part2() {
    int length = data.size();

    int totalIncreased = 0;

    for (int i = 0; i < length; i = i + 1) {
      if (i < 3) {
        continue;
      }

      int window2 = data.get(i) + data.get(i - 1) + data.get(i - 2);
      int window1 = data.get(i - 1) + data.get(i - 2) + data.get(i - 3);
      if (window2 > window1) {
        totalIncreased += 1;
      }
    }

    return totalIncreased;
  }
}
