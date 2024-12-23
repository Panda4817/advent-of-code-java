package dev.kmunton.year2023.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spring {

  private final int id;
  private final List<Integer> arrangement;
  private final List<Integer> condition;

  private final List<Integer> unknownIndexes = new ArrayList<>();

  private final List<List<Integer>> validCombinations = new ArrayList<>();

  private long validCombinationsCount = 0;

  private final Map<String, Long> cache = new HashMap<>();


  public Spring(List<Integer> arrangement, List<Integer> condition, int id) {
    this.id = id;
    this.arrangement = arrangement;
    this.condition = condition;
    // Part 1 - brute force
//        calculateValidCombinations();
//
    // Part 2 - dynamic programming
//        calculateValidCombinationsUsingDynamicProgramming();

    // Part 2 - recursion
    validCombinationsCount = getValidCombinationsUsingRecursion(condition, arrangement);
  }

  public long getValidCombinationsCount() {
    return validCombinationsCount;
  }

  @Override
  public String toString() {
    return "Spring{" +
        "arrangement=" + arrangement +
        ", condition=" + condition +
        '}';
  }


  public void calculateValidCombinations() {
    StringBuilder regex = new StringBuilder("^(0*)");
    for (int i = 0; i < arrangement.size(); i++) {
      regex.append("(1{").append(arrangement.get(i)).append("})");
      if (i < arrangement.size() - 1) {
        regex.append("(0+)");
      } else {
        regex.append("(0*)$");
      }
    }
    Pattern patternRegex = Pattern.compile(regex.toString());
    for (int i = 0; i < condition.size(); i++) {
      if (condition.get(i) == -1) {
        unknownIndexes.add(i);
      }
    }

    var totalCombinations = (long) Math.pow(2, unknownIndexes.size());
    var unknowns = unknownIndexes.size();
    for (int i = 0; i < totalCombinations; i++) {
      var s = ("%" + unknowns + "s").formatted(Integer.toBinaryString(i)).replace(' ', '0');
      var combination = Arrays.stream(s.split("")).map(Integer::parseInt).toList();
      var conditionCopy = new ArrayList<>(this.condition);
      for (int j = 0; j < unknowns; j++) {
        conditionCopy.set(unknownIndexes.get(j), combination.get(j));
      }
      var conditionString = conditionCopy.stream().map(String::valueOf).reduce("", String::concat);
      Matcher matcher = patternRegex.matcher(conditionString);
      if (matcher.matches()) {
        validCombinationsCount++;
        validCombinations.add(conditionCopy);
      }
    }
  }

  public long getValidCombinationsUsingRecursion(List<Integer> conditions, List<Integer> numbers) {
    if (conditions.isEmpty()) {
      return numbers.isEmpty() ? 1 : 0;
    }

    if (numbers.isEmpty()) {
      return conditions.contains(1) ? 0 : 1;
    }
    var key =
        conditions.stream().map(String::valueOf).reduce("", String::concat) + "/" + numbers.stream().map(String::valueOf).reduce("", String::concat);

    if (cache.containsKey(key)) {
      return cache.get(key);
    }

    var result = 0L;

    if (conditions.get(0) == 0 || conditions.get(0) == -1) {
      result += getValidCombinationsUsingRecursion(
          new ArrayList<>(conditions.subList(1, conditions.size())),
          new ArrayList<>(numbers)
      );
    }

    if (conditions.get(0) == 1 || conditions.get(0) == -1) {
      if (numbers.get(0) <= conditions.size() && !conditions.subList(0, numbers.get(0)).contains(0)
          && (numbers.get(0) == conditions.size() || conditions.get(numbers.get(0)) != 1)) {
        result += getValidCombinationsUsingRecursion(
            new ArrayList<>(numbers.get(0) == conditions.size() ? new ArrayList<>() : conditions.subList(numbers.get(0) + 1, conditions.size())),
            new ArrayList<>(numbers.subList(1, numbers.size()))
        );
      }
    }

    cache.put(key, result);
    return result;

  }

  public void calculateValidCombinationsUsingDynamicProgramming() {
    condition.add(0, 0);
    condition.add(0);
    var expandedArrangement = new ArrayList<Integer>();
    expandedArrangement.add(0);
    for (var a : arrangement) {
      for (int i = 0; i < a; i++) {
        expandedArrangement.add(1);
      }
      expandedArrangement.add(0);
    }

    long[][] dp = new long[condition.size() + 1][expandedArrangement.size() + 1];
    dp[condition.size()][expandedArrangement.size()] = 1;
    for (int i = condition.size() - 1; i >= 0; i--) {
      for (int j = expandedArrangement.size() - 1; j >= 0; j--) {
        boolean damaged = condition.get(i) == -1 || condition.get(i) == 1;
        boolean operational = condition.get(i) == -1 || condition.get(i) == 0;

        long sum = 0;
        if (damaged && expandedArrangement.get(j) == 1) {
          sum += dp[i + 1][j + 1];
        } else if (operational && expandedArrangement.get(j) == 0) {
          sum += dp[i + 1][j + 1] + dp[i + 1][j];
        }
        dp[i][j] = sum;
      }
    }
    validCombinationsCount += dp[0][0];
  }

}
