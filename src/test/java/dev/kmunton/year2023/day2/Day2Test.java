package dev.kmunton.year2023.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day2Test {

  private static Day2 day;

  private static final List<String> INPUT = """
      Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
      Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
      Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
      Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
      Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day2(INPUT);
  }

  @Test
  void part1() {
    assertEquals(8, day.part1());
  }

  @Test
  void part2() {
    assertEquals(2286, day.part2());
  }
}
