package dev.kmunton.year2024.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day11Test {

  private static Day11 day;

  @Test
  void part1_success() {
    List<String> input = """
        125 17""".lines().toList();
    day = new Day11(input);
    assertEquals(55312L, day.part1());
  }
}
