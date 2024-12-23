package dev.kmunton.year2023.day19;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.Range;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day19 implements Day<Long, Long> {

  private final Map<String, List<Rule>> workflows = new HashMap<>();
  private final List<Part> parts = new ArrayList<>();

  List<Map<String, Range>> validRanges = new ArrayList<>();

  public Day19(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    var parsingWorkflows = true;
    for (var line : input) {
      if (line.isBlank()) {
        parsingWorkflows = false;
        continue;
      }
      if (parsingWorkflows) {
        var split1 = line.split("\\{");
        var name = split1[0];
        var split2 = split1[1].split("}")[0].split(",");
        var rules = new ArrayList<Rule>();
        for (var rule : split2) {
          if (rule.contains(":")) {
            var split3 = rule.split(":");
            var condition = split3[0];
            var nextWorkflow = split3[1];
            if (condition.contains(">")) {
              var split4 = condition.split(">");
              var conditionLetter = split4[0];
              var conditionValue = Long.parseLong(split4[1]);
              Function<Long, Boolean> function = (Long l) -> l > conditionValue;
              rules.add(new Rule(conditionLetter, ">", conditionValue, function, nextWorkflow));
            } else {
              var split4 = condition.split("<");
              var conditionLetter = split4[0];
              var conditionValue = Long.parseLong(split4[1]);
              Function<Long, Boolean> function = (Long l) -> l < conditionValue;
              rules.add(new Rule(conditionLetter, "<", conditionValue, function, nextWorkflow));
            }
          } else {
            rules.add(new Rule(null, null, 0, null, rule));
          }
        }
        workflows.put(name, rules);
      } else {
        var split1 = line.split("\\{")[1].split("}")[0].split(",");
        var x = Long.parseLong(split1[0].split("=")[1]);
        var m = Long.parseLong(split1[1].split("=")[1]);
        var a = Long.parseLong(split1[2].split("=")[1]);
        var s = Long.parseLong(split1[3].split("=")[1]);
        parts.add(new Part(x, m, a, s));
      }
    }
  }

  public Long part1() {
    for (var part : parts) {
      setAcceptedOrRejected(part);
    }
    return parts.stream().filter(Part::isAccepted).mapToLong(Part::getRating).sum();

  }

  public Long part2() {
    var minNum = 1;
    var maxNum = 4000;

    var startingMap = new HashMap<String, Range>();
    startingMap.put("x", new Range(minNum, maxNum));
    startingMap.put("m", new Range(minNum, maxNum));
    startingMap.put("a", new Range(minNum, maxNum));
    startingMap.put("s", new Range(minNum, maxNum));
    populateValidRangesForAcceptedState(startingMap, "in");
    var result = 0L;
    for (var validRange : validRanges) {
      var n = 1L;
      for (var entry : validRange.entrySet()) {
        n *= entry.getValue().max() - entry.getValue().min() + 1;
      }
      result += n;
    }
    return result;
  }

  private void populateValidRangesForAcceptedState(Map<String, Range> rangeMap, String workflow) {
    if (workflow.equals("A")) {
      validRanges.add(rangeMap);
      return;
    }
    if (workflow.equals("R")) {
      return;
    }
    var rules = workflows.get(workflow);
    for (var rule : rules) {
      if (rule.getCondition() == null) {
        populateValidRangesForAcceptedState(rangeMap, rule.getNextWorkflow());
        break;
      }
      var conditionLetter = rule.getConditionLetter();
      var conditionOperator = rule.getConditionOperator();
      var conditionValue = rule.getConditionValue();
      var nextWorkflow = rule.getNextWorkflow();
      if (conditionOperator.equals(">")) {
        var newRange = new Range(conditionValue + 1, rangeMap.get(conditionLetter).max());
        var newMap = new HashMap<>(rangeMap);
        newMap.put(conditionLetter, newRange);
        populateValidRangesForAcceptedState(newMap, nextWorkflow);
        rangeMap.put(conditionLetter, new Range(rangeMap.get(conditionLetter).min(), conditionValue));
      } else {
        var newRange = new Range(rangeMap.get(conditionLetter).min(), conditionValue - 1);
        var newMap = new HashMap<>(rangeMap);
        newMap.put(conditionLetter, newRange);
        populateValidRangesForAcceptedState(newMap, nextWorkflow);
        rangeMap.put(conditionLetter, new Range(conditionValue, rangeMap.get(conditionLetter).max()));
      }
    }
  }

  private void setAcceptedOrRejected(Part part) {
    var currentWorkflow = "in";
    while (!currentWorkflow.equals("R") && !currentWorkflow.equals("A")) {
      var rules = workflows.get(currentWorkflow);
      for (var rule : rules) {
        if (rule.getCondition() == null) {
          currentWorkflow = rule.getNextWorkflow();
          break;
        }
        if (Boolean.TRUE.equals(rule.getCondition().apply(part.getValueGivenletter(rule.getConditionLetter())))) {
          currentWorkflow = rule.getNextWorkflow();
          break;
        }
      }
    }
    if (currentWorkflow.equals("A")) {
      part.setAccepted(true);
    }
    if (currentWorkflow.equals("R")) {
      part.setRejected(true);
    }
  }
}
