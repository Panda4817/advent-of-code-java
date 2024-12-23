package dev.kmunton.year2023.day9;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 implements Day<Long, Long> {

  private final List<List<Long>> sequences = new ArrayList<>();

  public Day9(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    input.forEach(line ->
        sequences.add(Arrays.stream(line.split(" ")).map(Long::parseLong).toList())
    );
  }

  public Long part1() {
    long result = 0;
    for (List<Long> sequence : sequences) {
      List<List<Long>> patterns = getAllPatterns(sequence);
      for (var i = patterns.size() - 1; i > 0; i--) {
        var nextPattern = patterns.get(i - 1);
        var currentPattern = patterns.get(i);
        var value = nextPattern.get(nextPattern.size() - 1) + currentPattern.get(currentPattern.size() - 1);
        nextPattern.add(value);
      }
      result += sequence.get(sequence.size() - 1) + patterns.get(0).get(patterns.get(0).size() - 1);
    }
    return result;

  }

  public Long part2() {
    long result = 0;
    for (List<Long> sequence : sequences) {
      List<List<Long>> patterns = getAllPatterns(sequence);
      for (var i = patterns.size() - 1; i > 0; i--) {
        var nextPattern = patterns.get(i - 1);
        var currentPattern = patterns.get(i);
        var value = nextPattern.get(0) - currentPattern.get(0);
        nextPattern.add(0, value);
      }
      result += (sequence.get(0) - patterns.get(0).get(0));
    }
    return result;
  }

  private List<List<Long>> getAllPatterns(List<Long> sequence) {
    List<List<Long>> patterns = new ArrayList<>();
    List<Long> current = sequence;
    while (isNotAllSame(current)) {
      current = findPattern(current);
      patterns.add(current);
    }
    return patterns;
  }

  private List<Long> findPattern(List<Long> sequence) {
    List<Long> pattern = new ArrayList<>();
    for (int i = 1; i < sequence.size(); i++) {
      pattern.add(sequence.get(i) - sequence.get(i - 1));
    }
    return pattern;
  }

  private boolean isNotAllSame(List<Long> pattern) {
    return !pattern.stream().allMatch(pattern.get(0)::equals);
  }
}
