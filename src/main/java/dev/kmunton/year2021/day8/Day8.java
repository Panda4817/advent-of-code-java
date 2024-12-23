package dev.kmunton.year2021.day8;


import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 implements Day<Integer, Integer> {

  private List<Signal> data;
  private int size;

  public Day8(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    data = new ArrayList<>();
    input.forEach(s -> {
      String[] parts = s.split(" \\| ");
      List<String> signal = Stream.of(parts[0].split("\\s+")).collect(Collectors.toList());
      List<String> output = Stream.of(parts[1].split("\\s+")).collect(Collectors.toList());
      Signal sig = new Signal(signal, output);
      data.add(sig);
    });
    size = data.size();
  }


  @Override
  public Integer part1() {
    int count = 0;
    for (Signal s : data) {
      for (String o : s.getOutput()) {
        Integer i = o.length();
        if (i == 2 || i == 4 || i == 7 | i == 3) {
          count += 1;
        }
      }
    }
    return count;
  }

  @Override
  public Integer part2() {
    int sum = 0;
    for (Signal s : data) {
      sum += s.deduceOutput();
    }
    return sum;
  }
}
