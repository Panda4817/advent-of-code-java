package dev.kmunton.year2023.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day24Test {

  private static Day24 day;
  private static final List<String> INPUT = """
      19, 13, 30 @ -2,  1, -2
      18, 19, 22 @ -1, -1, -2
      20, 25, 34 @ -2, -2, -4
      12, 31, 28 @ -1, -2, -1
      20, 19, 15 @  1, -5, -3
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day24(INPUT);
  }

  @Test
  void part1() {
    assertEquals(2, day.findIntersectionInTestArea(7, 27));

  }

  @Test
  void part2() {
    assertEquals(47, day.part2());
  }
}
