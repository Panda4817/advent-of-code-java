package dev.kmunton.year2023.day19;

import java.util.function.Function;

public class Rule {

  private final String conditionLetter;
  private final String conditionOperator;
  private final long conditionValue;
  private final Function<Long, Boolean> condition;
  private final String nextWorkflow;

  public Rule(String conditionLetter, String conditionOperator, long conditionValue,
      Function<Long, Boolean> condition, String nextWorkflow) {
    this.conditionLetter = conditionLetter;
    this.conditionOperator = conditionOperator;
    this.conditionValue = conditionValue;
    this.condition = condition;
    this.nextWorkflow = nextWorkflow;
  }

  public Function<Long, Boolean> getCondition() {
    return condition;
  }

  public String getNextWorkflow() {
    return nextWorkflow;
  }

  public String getConditionLetter() {
    return conditionLetter;
  }

  public String getConditionOperator() {
    return conditionOperator;
  }

  public long getConditionValue() {
    return conditionValue;
  }
}
