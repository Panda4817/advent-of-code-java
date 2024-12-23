package dev.kmunton.year2023.day8;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.MathUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Day8 implements Day<Long, Long> {

  private final List<Integer> instructions = new ArrayList<>();

  private final Map<String, List<String>> nodes = new HashMap<>();

  public Day8(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    Arrays.stream(input.get(0).trim().split("")).forEach(s -> {
      if (s.equals("L")) {
        instructions.add(0);
      } else {
        instructions.add(1);
      }
    });
    input.stream().skip(2).forEach(s -> {
      var parts = s.split(" = ");
      var key = parts[0];
      var value = Arrays.stream(parts[1].replace("(", "").replace(")", "")
                                        .split(", ")).toList();
      nodes.put(key, value);
    });
  }

  public Long part1() {
    return getStepsFromAToZ("AAA", s -> s.equals("ZZZ"));
  }

  public Long part2() {
    var currents = new ArrayList<>(nodes.keySet().stream().filter(s -> s.endsWith("A")).toList());
    Map<String, Long> stepsForEachOne = new HashMap<>();

    for (var current : currents) {
      var steps = getStepsFromAToZ(current, s -> s.endsWith("Z"));
      stepsForEachOne.put(current, steps);
    }

    return stepsForEachOne.values().stream().reduce(1L, MathUtils::lcm);
  }

  private long getStepsFromAToZ(String a, Predicate<String> expression) {
    var steps = 0L;
    var next = 0;
    var node = a;
    while (!expression.test(node)) {
      if (next == instructions.size()) {
        next = 0;
      }
      var instruction = instructions.get(next);
      node = nodes.get(node).get(instruction);
      next += 1;
      steps += 1;
    }
    return steps;
  }
}
