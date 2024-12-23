package dev.kmunton.year2021.day10;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day10 implements Day<Integer, Long> {

  private List<List<String>> data;
  private final Map<String, String> pairs = new HashMap<>();
  private final Map<String, Integer> points = new HashMap<>();
  private final Map<String, Integer> correctionPoints = new HashMap<>();
  private int size;

  public Day10(List<String> input) {
    processData(input);
    pairs.put("(", ")");
    pairs.put("{", "}");
    pairs.put("[", "]");
    pairs.put("<", ">");
    points.put(")", 3);
    points.put("}", 1197);
    points.put("]", 57);
    points.put(">", 25137);
    correctionPoints.put("(", 1);
    correctionPoints.put("[", 2);
    correctionPoints.put("{", 3);
    correctionPoints.put("<", 4);
  }

  @Override
  public void processData(List<String> input) {
    data = new ArrayList<>();
    input.forEach(s -> {
      List<String> tokens = Arrays.stream(s.split("(?!^)")).toList();
      data.add(tokens);
    });
    size = data.size();
  }

  @Override
  public Integer part1() {
    int syntaxPoints = 0;
    for (List<String> line : data) {
      List<String> stack = new ArrayList<>();
      for (String token : line) {
        if (pairs.containsKey(token)) {
          stack.add(token);
          continue;
        } else if (stack.size() > 0) {
          int last = stack.size() - 1;
          String opening = stack.remove(last);
          if (!Objects.equals(pairs.get(opening), token)) {
            syntaxPoints += points.get(token);
            break;
          }
        } else {
          syntaxPoints += points.get(token);
          break;
        }

      }
    }
    return syntaxPoints;
  }

  @Override
  public Long part2() {
    List<Long> scores = new ArrayList<>();
    for (List<String> line : data) {
      List<String> stack = new ArrayList<>();
      boolean corrupt = false;
      for (String token : line) {
        if (pairs.containsKey(token)) {
          stack.add(token);
          continue;
        } else if (stack.size() > 0) {
          int last = stack.size() - 1;
          String opening = stack.remove(last);
          if (!Objects.equals(pairs.get(opening), token)) {
            corrupt = true;
            break;
          }
        } else {
          corrupt = true;
          break;
        }
      }
      if (corrupt) {
        continue;
      }
      int last = stack.size() - 1;
      long total = 0;
      for (int i = last; i > -1; i--) {
        long score = (total * 5) + correctionPoints.get(stack.get(i));
        total = score;
      }
      scores.add(total);
    }
    scores = scores.stream().sorted().collect(Collectors.toList());
    System.out.println(scores);
    int index = scores.size() / 2;
    System.out.println(scores.size());
    System.out.println(index);
    return scores.get(index);
  }
}
