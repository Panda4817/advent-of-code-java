package dev.kmunton.days.day5;

import static java.util.Collections.min;

import dev.kmunton.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day5 implements Day<Long, Long> {

  private final Map<Integer, List<Integer>> rulesPageAfterWhichPagesMap = new HashMap<>();
  private final List<List<Integer>> updates = new ArrayList<>();

  public Day5(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> {
      if (!line.contains("|") && !line.contains(",")) {
        return;
      }
      if (line.contains("|")) {
        int[] numbers = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray();
        List<Integer> afterKeyList = rulesPageAfterWhichPagesMap.getOrDefault(numbers[0], new ArrayList<>());
        afterKeyList.add(numbers[1]);
        rulesPageAfterWhichPagesMap.put(numbers[0], afterKeyList);
        return;
      }
      List<Integer> update = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
      updates.add(update);
    });
  }

  @Override
  public Long part1() {
    return updates.stream().map(l -> {
      if (isOrderedCorrect(l)) {
        return findMiddle(l);
      }
      return 0L;
    }).reduce(0L, Long::sum);
  }

  @Override
  public Long part2() {
    return updates.stream().map(l -> {
      if (!isOrderedCorrect(l)) {
        List<Integer> newList = orderCorrectly(l);
        return findMiddle(newList);
      }
      return 0L;
    }).reduce(0L, Long::sum);
  }

  private boolean isOrderedCorrect(List<Integer> list) {
    for (Integer i : list) {
      if (rulesPageAfterWhichPagesMap.containsKey(i)) {
        int index1 = list.indexOf(i);
        for (Integer j : rulesPageAfterWhichPagesMap.get(i)) {
          if (list.contains(j) && list.indexOf(j) < index1) {
            return false;
          }
        }
      }
    }
    return true;
  }

  private List<Integer> orderCorrectly(List<Integer> list) {
    List<Integer> newList = new ArrayList<>(list.stream().toList());
    for (Integer i : list) {
      newList.remove(i);
      if (rulesPageAfterWhichPagesMap.containsKey(i)) {
        List<Integer> indexes = new ArrayList<>();
        for (Integer j : rulesPageAfterWhichPagesMap.get(i)) {
          if (newList.contains(j)) {
            indexes.add(newList.indexOf(j));
          }
        }

        if (indexes.isEmpty()) {
          newList.add(i);
          continue;
        }

        int min = min(indexes);
        newList.add(min, i);
      }
    }
    return newList;
  }

  private long findMiddle(List<Integer> list) {
    return list.get(list.size() / 2);
  }

}
