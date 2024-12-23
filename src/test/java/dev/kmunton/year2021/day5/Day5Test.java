package dev.kmunton.year2021.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day5Test {

  private static Day5 day;

  @Test
  void part1_success() {
    List<String> input = """
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2""".lines().toList();
    day = new Day5(input);
    assertEquals(5, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2""".lines().toList();
    day = new Day5(input);
    assertEquals(12, day.part2());
  }
}
