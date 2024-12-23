package dev.kmunton.year2024.day3;

import dev.kmunton.utils.days.Day;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day3 implements Day<Long, Long> {

  private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d){1,3},(\\d){1,3}\\)");

  private String memory = "";

  public Day3(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(s -> memory += s);
  }

  @Override
  public Long part1() {
    Matcher matcher = MUL_PATTERN.matcher(memory);
    return matcher.results().map(m -> evaluateMul(m.group())).reduce(0L, Long::sum);
  }

  @Override
  public Long part2() {
    Pattern pattern = Pattern.compile("((do\\(\\).*?(?=mul))|(don't\\(\\).*?(?=mul)))?(mul\\((\\d){1,3},(\\d){1,3}\\))");
    Matcher matcher = pattern.matcher(memory);
    AtomicBoolean doMul = new AtomicBoolean(true);
    return matcher.results().map(MatchResult::group).map(g -> {
      if (g.startsWith("do()")) {
        doMul.set(true);
        Matcher mulMatcher = MUL_PATTERN.matcher(g);
        String mul = mulMatcher.results().map(MatchResult::group).findFirst().orElseThrow();
        return evaluateMul(mul);
      }
      if (g.startsWith("don't()")) {
        doMul.set(false);
        return 0L;
      }
      if (doMul.get()) {
        return evaluateMul(g);
      }
      return 0L;
    }).reduce(0L, Long::sum);
  }

  private long evaluateMul(String mul) {
    return Arrays.stream(mul.substring(4, mul.length() - 1).split(","))
                 .mapToLong(Long::parseLong).reduce(1L, (a, b) -> a * b);
  }

}
