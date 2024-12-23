package dev.kmunton.year2021.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day6Test {

  private static Day6 day;

  @Test
  void part1_success() {
    List<String> input = """
        3,4,3,1,2""".lines().toList();
    day = new Day6(input);
    assertEquals(5934, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        3,4,3,1,2""".lines().toList();
    day = new Day6(input);
    assertEquals(26984457539L, day.part2());
  }

}
