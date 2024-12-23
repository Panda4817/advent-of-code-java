package dev.kmunton.year2024.day7;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
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
                    .filter(e -> e.isTrueWithAddOrMultiplyOrConcatenate(0L, 0, List.of(e::add, e::multiply)))
                    .map(e -> e.value)
                    .reduce(0L, Long::sum);
  }

  @Override
  public Long part2() {
    return equations.stream()
                    .filter(e -> e.isTrueWithAddOrMultiplyOrConcatenate(0L, 0, List.of(e::add, e::multiply, e::concatenate)))
                    .map(e -> e.value)
                    .reduce(0L, Long::sum);
  }

  record Equation(Long value, List<Long> numbers) {

    public boolean isTrueWithAddOrMultiplyOrConcatenate(Long total, int index, List<BiFunction<Long, Long, Long>> functions) {
      if (Objects.equals(total, value()) && index >= numbers.size()) {
        return true;
      }
      if (index >= numbers().size() || (total >= value() && index < numbers().size() - 1)) {
        return false;
      }
      Long number = numbers().get(index);
      return functions.stream()
                      .anyMatch(f -> isTrueWithAddOrMultiplyOrConcatenate(f.apply(total, number), index + 1, functions));
    }

    private Long concatenate(Long a, Long b) {
      if (a == 0) {
        return b;
      }
      return Long.parseLong(a + String.valueOf(b));
    }

    private Long add(Long a, Long b) {
      return a + b;
    }

    private Long multiply(Long a, Long b) {
      if (a == 0) {
        return b;
      }
      return a * b;
    }

  }

}
