package dev.kmunton.year2023.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day7Test {

  private static Day7 day;
  private static final List<String> INPUT = """
      32T3K 765
      T55J5 684
      KK677 28
      KTJJT 220
      QQQJA 483
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day7(INPUT);
  }

  @Test
  void part1() {
    assertEquals(6440, day.part1());

  }

  @Test
  void part2() {
    assertEquals(5905, day.part2());
  }
}
