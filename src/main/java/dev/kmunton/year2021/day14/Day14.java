package dev.kmunton.year2021.day14;

import static java.util.Map.Entry.comparingByValue;

import dev.kmunton.utils.days.Day;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day14 implements Day<Integer, Long> {

  private String template;
  private Map<String, Long> pairs;
  private int size;
  private Map<String, String> rules;
  private final int stepsPart1 = 10;
  private final int stepsPart2 = 40;

  public Day14(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    rules = new HashMap<>();
    pairs = new HashMap<>();
    boolean isTemplate = true;
    for (String s : input) {
      if (Objects.equals(s, "")) {
        isTemplate = false;
        continue;
      }
      if (isTemplate) {
        template = s;
        List<String> initial = List.of(s.split("(?<=\\G.{2})"));
        log.info("{}", initial);
        for (int i = 0; i < initial.size() - 1; i++) {
          pairs.put(initial.get(i), Long.valueOf("1"));
          pairs.put(initial.get(i).charAt(1) + initial.get(i + 1).substring(0, 1), Long.valueOf("1"));
          pairs.put(initial.get(i).charAt(1) + initial.get(i + 1).substring(0, 1), Long.valueOf("1"));
          pairs.put(initial.get(i + 1), Long.valueOf("1"));
        }
        size = template.length();
        log.info("{}", pairs);
      } else {
        String[] parts = s.split(" -> ");
        rules.put(parts[0], parts[1]);
      }
    }
  }


  private void polymerisePairs() {

    Map<String, Long> pairsNew = new HashMap<>();

    for (String pair : pairs.keySet()) {
      String none = "null";
      String output = rules.getOrDefault(pair, none);
      long countCurrent = pairs.getOrDefault(pair, Long.valueOf("0"));
      if (!output.equals(none)) {

        String newPair = pair.charAt(0) + output;
        addToMap(newPair, countCurrent, pairsNew);

        newPair = output + pair.charAt(1);
        addToMap(newPair, countCurrent, pairsNew);

      } else {
        addToMap(pair, countCurrent, pairsNew);
      }

    }
    pairs = pairsNew;

  }

  private void addToMap(String key, Long toAdd, Map<String, Long> map) {
    long count = map.getOrDefault(key, Long.valueOf("0"));
    map.put(key, count + toAdd);
  }

  private long mostCommonSubtractLeastCommon(Map<String, Long> countMap) {
    Set<Map.Entry<String, Long>> entrySet = countMap.entrySet();
    long mostCommon = entrySet.stream().max(comparingByValue()).get().getValue();
    long leastCommon = entrySet.stream().min(comparingByValue()).get().getValue();
    System.out.println(mostCommon + " " + leastCommon);
    return (mostCommon - leastCommon) / 2;
  }

  private long letterCount() {
    Map<String, Long> countMap = new HashMap<>();

    for (String key : pairs.keySet()) {
      long value = pairs.get(key);
      String letter1 = key.substring(0, 1);
      String letter2 = key.substring(1, 2);

      addToMap(letter1, value, countMap);
      addToMap(letter2, value, countMap);
    }

    String first = template.substring(0, 1);
    addToMap(first, Long.valueOf("1"), countMap);

    String last = template.substring(size - 1, size);
    addToMap(last, Long.valueOf("1"), countMap);

    return mostCommonSubtractLeastCommon(countMap);

  }

  @Override
  public Integer part1() {
    int step = 0;
    while (step != stepsPart1) {
      polymerisePairs();
      step += 1;
    }
    return Math.toIntExact(letterCount());
  }


  @Override
  public Long part2() {
    int step = 0;
    while (step != stepsPart2) {
      polymerisePairs();
      step += 1;
    }
    return letterCount();
  }
}
