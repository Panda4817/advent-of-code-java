package dev.kmunton.year2021.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day3Test {

  private static Day3 day;

  @Test
  void part1_success() {
    List<String> input = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010""".lines().toList();
    day = new Day3(input);
    assertEquals(198, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010""".lines().toList();
    day = new Day3(input);
    assertEquals(230, day.part2());
  }

}
