package dev.kmunton.year2025.day11;

import dev.kmunton.utils.days.Day;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static dev.kmunton.utils.algorithms.DynamicProgrammingUtils.countPathsInDAG;
import static dev.kmunton.utils.algorithms.GraphSearchUtils.findAllPathsWithBfs;

@Slf4j
public class Day11 implements Day<Long, Long> {

    Map<String, List<String>> deviceToOutputs = new HashMap<>();

  public Day11(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
      input.forEach(line -> {
          String[] split = line.split(": ");
          String device = split[0];
          List<String> outputs = Arrays.stream(split[1].split(" ")).toList();
          deviceToOutputs.put(device, outputs);
      });
  }

  @Override
  public Long part1() {
      String start = "you";
      String end = "out";
      List<List<String>> paths = findAllPathsWithBfs(
              start,
              d -> d.equals(end),
              d -> deviceToOutputs.get(d).stream().toList(),
              false
      );
    return (long) paths.size();
  }

  @Override
  public Long part2() {
      String svr = "svr";
      String dac = "dac";
      String fft = "fft";
      String out = "out";

      return countPathsInDAG(
              deviceToOutputs,
              svr,
              out,
              Set.of(dac, fft)
      );

  }


}
