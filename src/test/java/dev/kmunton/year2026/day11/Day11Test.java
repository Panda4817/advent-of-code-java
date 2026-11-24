package dev.kmunton.year2026.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day11Test {

  private Day11 day;

  @BeforeEach
  void setUp() {
    List<String> input = List.of();
    day = new Day11(input);
  }

  @Test
  void part1_success() {
    assertEquals(-1L, day.part1());
  }

  @Test
  void part2_success() {
    assertEquals(-1L, day.part2());
  }
}
