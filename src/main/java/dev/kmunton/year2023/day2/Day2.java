package dev.kmunton.year2023.day2;

import static java.lang.Integer.parseInt;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 implements Day<Long, Long> {

  private static final String RED = "red";
  private static final String GREEN = "green";
  private static final String BLUE = "blue";

  Map<Integer, List<Map<String, Integer>>> data = new HashMap<>();

  public Day2(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    input.forEach(line -> {
      var gameSubsets = line.split(":");
      var key = parseInt(gameSubsets[0].split(" ")[1]);
      data.put(key, new ArrayList<>());
      var subsets = gameSubsets[1].split(";");
      for (var subset : subsets) {
        var subsetMap = new HashMap<String, Integer>();
        var subsetValues = subset.split(", ");
        for (var subsetValue : subsetValues) {
          var subsetValueSplit = subsetValue.trim().split(" ");
          subsetMap.put(subsetValueSplit[1], parseInt(subsetValueSplit[0]));
        }
        data.get(key).add(subsetMap);
      }
    });
  }

  public Long part1() {
    var red = 12;
    var green = 13;
    var blue = 14;

    var sumOfIds = 0;

    for (var key : data.keySet()) {
      var game = data.get(key);
      var possibleSubsets = 0;
      for (var subset : game) {
        var colours = subset.keySet();
        var count = 0;
        if (subset.containsKey(RED) && subset.get(RED) <= red) {
          count++;
        }
        if (subset.containsKey(GREEN) && subset.get(GREEN) <= green) {
          count++;
        }
        if (subset.containsKey(BLUE) && subset.get(BLUE) <= blue) {
          count++;
        }
        if (count == colours.size()) {
          possibleSubsets++;
        }
      }
      if (possibleSubsets == game.size()) {
        sumOfIds += key;
      }
    }

    return (long) sumOfIds;

  }

  public Long part2() {
    var sumOfPower = 0;

    for (var key : data.keySet()) {
      var game = data.get(key);
      var red = 0;
      var green = 0;
      var blue = 0;
      for (var subset : game) {
        if (subset.containsKey(RED) && subset.get(RED) > red) {
          red = subset.get(RED);
        }
        if (subset.containsKey(GREEN) && subset.get(GREEN) > green) {
          green = subset.get(GREEN);
        }
        if (subset.containsKey(BLUE) && subset.get(BLUE) > blue) {
          blue = subset.get(BLUE);
        }
      }
      sumOfPower += (red * green * blue);
    }

    return (long) sumOfPower;
  }

}
