package dev.kmunton.year2023.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day9Test {

  private static Day9 day;
  private static final List<String> INPUT = """
      0 3 6 9 12 15
      1 3 6 10 15 21
      10 13 16 21 30 45
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day9(INPUT);
  }

  @Test
  void part1() {
    assertEquals(114, day.part1());

  }

  @Test
  void part2() {
    assertEquals(2, day.part2());
  }
}
