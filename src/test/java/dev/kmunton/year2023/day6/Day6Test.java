package dev.kmunton.year2023.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day6Test {

  private static Day6 day;
  private static final List<String> INPUT = """
      Time:      7  15   30
      Distance:  9  40  200
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day6(INPUT);
  }

  @Test
  void part1() {
    assertEquals(288, day.part1());

  }

  @Test
  void part2() {
    assertEquals(71503, day.part2());
  }
}
