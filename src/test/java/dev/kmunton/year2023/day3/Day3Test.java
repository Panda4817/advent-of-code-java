package dev.kmunton.year2023.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day3Test {

  private static Day3 day;
  private static final List<String> INPUT = """
      467..114..
      ...*......
      ..35..633.
      ......#...
      617*......
      .....+.58.
      ..592.....
      ......755.
      ...$.*.+10
      .664.598..
        """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day3(INPUT);
  }

  @Test
  void part1() {
    assertEquals(4371, day.part1());
  }

  @Test
  void part2() {
    assertEquals(467835, day.part2());
  }
}
