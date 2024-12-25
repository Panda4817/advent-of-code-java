package dev.kmunton.year2024.day25;


import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day25 implements Day<Integer, Long> {

  private final List<List<Integer>> locks = new ArrayList<>();
  private final List<List<Integer>> keys = new ArrayList<>();


  public Day25(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    Map<Integer, Integer> colHashCount = new HashMap<>();
    boolean lock = true;
    boolean key = true;
    boolean newSchema = true;
    for (String line : input) {
      List<String> chars = Arrays.stream(line.split("")).toList();
      if (newSchema && chars.stream().allMatch(s -> s.equals("#"))) {
        newSchema = false;
        lock = true;
        key = false;
      }
      if (newSchema && chars.stream().allMatch(s -> s.equals("."))) {
        newSchema = false;
        key = true;
        lock = false;
      }

      if (chars.size() == 1 && chars.get(0).equals("")) {
        populateData(colHashCount, lock, key);
        newSchema = true;
        continue;
      }

      for (int i = 0; i < chars.size(); i++) {
        if (chars.get(i).equals("#")) {
          colHashCount.put(i, colHashCount.getOrDefault(i, 0) + 1);
        }
      }
    }

    populateData(colHashCount, lock, key);

  }

  private void populateData(Map<Integer, Integer> colHashCount, boolean lock, boolean key) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < colHashCount.size(); i++) {
      list.add(colHashCount.get(i));
      colHashCount.put(i, 0);
    }
    if (lock) {
      locks.add(list);
    }
    if (key) {
      keys.add(list);
    }
  }

  @Override
  public Integer part1() {
    int matchingKeysAndLockCounts = 0;
    for (List<Integer> lock : locks) {
      for (List<Integer> key : keys) {
        boolean keyFits = true;
        for (int i = 0; i < lock.size(); i++) {
          if (lock.get(i) + key.get(i) > 7) {
            keyFits = false;
            break;
          }
        }
        if (keyFits) {
          matchingKeysAndLockCounts++;
        }
      }
    }
    return matchingKeysAndLockCounts;
  }


  @Override
  public Long part2() {
    return -1L;
  }

}
