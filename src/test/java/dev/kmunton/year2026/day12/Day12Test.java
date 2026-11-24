package dev.kmunton.year2026.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day12Test {

  private Day12 day;

  @BeforeEach
  void setUp() {
    List<String> input = List.of();
    day = new Day12(input);
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
