package dev.kmunton.days.day21;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day21Test {

  private static Day21 day;

  @Test
  void part1_success() {
    List<String> input = """
        029A
        980A
        179A
        456A
        379A""".lines().toList();
    day = new Day21(input);
    assertEquals(126384L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        029A
        980A
        179A
        456A
        379A""".lines().toList();
    day = new Day21(input);
    assertEquals(154115708116294L, day.part2());
  }

}
