package dev.kmunton.days.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day7Test {

  private static Day7 day;

  @Test
  void part1_success() {
    List<String> input = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20""".lines().toList();
    day = new Day7(input);
    assertEquals(3749L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20""".lines().toList();
    day = new Day7(input);
    assertEquals(11387L, day.part2());
  }

}
