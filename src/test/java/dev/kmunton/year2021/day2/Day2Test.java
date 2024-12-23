package dev.kmunton.year2021.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day2Test {

  private static Day2 day;

  @Test
  void part1_success() {
    List<String> input = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2""".lines().toList();
    day = new Day2(input);
    assertEquals(150, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2""".lines().toList();
    day = new Day2(input);
    assertEquals(900, day.part2());
  }

}
