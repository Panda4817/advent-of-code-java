package dev.kmunton.days.day7;

import dev.kmunton.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day7 implements Day<Long, Long> {

  private final List<Equation> equations = new ArrayList<>();

  public Day7(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> {
      String[] parts = line.split(": ");
      List<Long> numbers = Arrays.stream(parts[1].split(" ")).map(Long::parseLong).toList();
      equations.add(new Equation(Long.parseLong(parts[0]), numbers));
    });
  }

  @Override
  public Long part1() {
    return equations.stream()
                    .filter(e -> e.isTrueWithAddOrMultiply(0L, 0))
                    .map(e -> e.value)
                    .reduce(0L, Long::sum);
  }

  @Override
  public Long part2() {
    return equations.stream()
                    .filter(e -> e.isTrueWithAddOrMultiplyOrConcatenation(0L, 0))
                    .map(e -> e.value)
                    .reduce(0L, Long::sum);
  }

  record Equation(Long value, List<Long> numbers) {

    public boolean isTrueWithAddOrMultiply(Long total, int index) {

      if (Objects.equals(total, value()) && index >= numbers.size()) {
        return true;
      }
      if (index >= numbers().size()) {
        return false;
      }
      Long number = numbers().get(index);
      return isTrueWithAddOrMultiply(total + number, index + 1)
          || isTrueWithAddOrMultiply((total == 0 ? 1 : total) * number, index + 1);
    }

    public boolean isTrueWithAddOrMultiplyOrConcatenation(Long total, int index) {

      if (Objects.equals(total, value()) && index >= numbers.size()) {
        return true;
      }
      if (index >= numbers().size()) {
        return false;
      }
      Long number = numbers().get(index);
      return isTrueWithAddOrMultiplyOrConcatenation(total + number, index + 1)
          || isTrueWithAddOrMultiplyOrConcatenation((total == 0 ? 1 : total) * number, index + 1)
          || isTrueWithAddOrMultiplyOrConcatenation(getConcatenatedNumber(total, number), index + 1);
    }

    private Long getConcatenatedNumber(Long a, Long b) {
      if (a == 0) {
        return b;
      }
      return Long.parseLong(a + String.valueOf(b));
    }

  }

}
