package dev.kmunton.year2025.day6;

import dev.kmunton.utils.days.Day;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day6 implements Day<Long, Long> {

    private final Map<Integer, Problem> problems = new HashMap<>();

  public Day6(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
      // Get column width for each problem using the operator line (last line in input)
      final String operatorLine = input.getLast();
      int width = 0;
      int col = 0;
      String prev = "";
      String operator = "";
      for (String c : operatorLine.split("")) {
          if (prev.equals(" ") && !c.equals(" ")) {
              final Problem p = problems.getOrDefault(col, new Problem());
              // width is -1 to remove extra space between columns
              p.setColumnWidth(width-1);
              p.setOperator(operator);
              problems.put(col, p);
              width = 0;
              col++;
          }
          if (!c.equals(" ")) {
              operator = c;
          }
          prev = c;
          width++;
      }
      final Problem problem = problems.getOrDefault(col, new Problem());
      problem.setColumnWidth(width);
      problem.setOperator(operator);
      problems.put(col, problem);

      // Get numbers for each column
      int row = 0;
      final int totalRows = input.size();
      for (String line : input) {
          if (row == totalRows -1) {
              continue;
          }
          int startIndex = 0;
          int endIndex = 0;
          List<String> lineChars = Arrays.asList(line.split(""));
          for (final Integer column : problems.keySet().stream().sorted().toList()) {
              Problem p = problems.get(column);
              endIndex = startIndex + p.getColumnWidth();
              List<String> number = lineChars.subList(startIndex, endIndex);
              p.getStringNumbers().add(number);
              p.getNumbers().add(Long.parseLong(number.stream().filter(s -> !s.isBlank()).collect(Collectors.joining())));
              problems.put(column, p);
              // +1 added to not include space between columns
              startIndex = endIndex + 1;
          }
          row++;
      }
  }

  @Override
  public Long part1() {
    return getGrandTotal(problems.values());
  }

  @Override
  public Long part2() {
      // Read numbers vertically
    final List<Problem> newProblems = new ArrayList<>();
    for  (Problem p : problems.values()) {
        List<List<String>> numbers = p.getStringNumbers();
        int currentNumberLength = p.getColumnWidth();
        List<Long> newNumbers = new ArrayList<>();
        while (currentNumberLength > 0) {
            StringBuilder newNumber = new StringBuilder();
            for (List<String> number : numbers) {
                newNumber.append(number.get(currentNumberLength -1));
            }
            newNumbers.add(Long.parseLong(newNumber.toString().trim()));
            currentNumberLength --;
        }
        newProblems.add(new Problem(newNumbers, p.getOperator()));
    }
    return getGrandTotal(newProblems);
  }

  private long getGrandTotal(Collection<Problem> problems) {
      return problems.stream().mapToLong(p -> {
          if (p.getOperator().equals("+")) {
              return p.getNumbers().stream().mapToLong(Long::valueOf).sum();
          }
          if (p.getOperator().equals("*")) {
              return p.getNumbers().stream().mapToLong(Long::valueOf).reduce(1L, (a, b) -> a * b);
          }
          throw new IllegalArgumentException("Invalid operator: " + p.getOperator());
      }).sum();
  }

}
