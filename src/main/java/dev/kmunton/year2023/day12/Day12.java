package dev.kmunton.year2023.day12;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Day12 implements Day<Long, Long> {

  private final List<Spring> springs = new ArrayList<>();
  private final List<Spring> unfoldedSprings = new ArrayList<>();
  private List<String> input;

  public Day12(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    this.input = input;

  }

  public Long part1() {
    for (int i = 0; i < input.size(); i++) {
      var split = input.get(i).split(" ");
      var arrangement = Arrays.stream(split[1].split(",")).map(Integer::parseInt).toList();
      var condition = new ArrayList<>(convertStringToNumbers(Arrays.stream(split[0].split("")).toList()));
      var spring = new Spring(arrangement, condition, i);
      springs.add(spring);
    }
    return springs.stream().mapToLong(Spring::getValidCombinationsCount).sum();

  }

  public Long part2() {
    ExecutorService threadpool = Executors.newCachedThreadPool();
    CompletionService<Spring> completionService = new ExecutorCompletionService<>(threadpool);
    for (int i = 0; i < input.size(); i++) {
      int id = i;
      completionService.submit(() -> {
        var split = input.get(id).split(" ");
        var astring = split[1] + "," + split[1] + "," + split[1] + "," + split[1] + "," + split[1];
        var arrangement = Arrays.stream(astring.split(",")).map(Integer::parseInt).toList();
        var bstring = split[0] + "?" + split[0] + "?" + split[0] + "?" + split[0] + "?" + split[0];
        var condition = new ArrayList<>(convertStringToNumbers(Arrays.stream(bstring.split("")).toList()));
        return new Spring(arrangement, condition, id);
      });
    }
    int received = 0;
    while (received < input.size()) {
      try {
        Future<Spring> resultFuture = completionService.take();
        Spring spring = resultFuture.get();
        received++;
        unfoldedSprings.add(spring);
      } catch (Exception e) {
        e.printStackTrace();
        break;
      }
    }
    threadpool.shutdown();
    return unfoldedSprings.stream().mapToLong(Spring::getValidCombinationsCount).sum();
  }

  private List<Integer> convertStringToNumbers(List<String> list) {
    return list.stream().map(s -> {
      if (s.contains("?")) {
        return -1;
      }
      if (s.contains("#")) {
        return 1;
      }
      return 0;
    }).toList();
  }
}
