package dev.kmunton.year2021.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day7Test {

  private static Day7 day;

  @Test
  void part1_success() {
    List<String> input = """
        16,1,2,0,4,2,7,1,2,14""".lines().toList();
    day = new Day7(input);
    assertEquals(37, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        16,1,2,0,4,2,7,1,2,14""".lines().toList();
    day = new Day7(input);
    assertEquals(168, day.part2());
  }
}
