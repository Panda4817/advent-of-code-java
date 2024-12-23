package dev.kmunton.year2023.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day1Test {

  private static Day1 day1;

  @Test
  void part1() {
    List<String> input = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet""".lines().toList();
    day1 = new Day1(input);
    assertEquals(142, day1.part1());
  }

  @Test
  void part2() {
    List<String> input = """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
        seight2three""".lines().toList();
    day1 = new Day1(input);
    assertEquals(364, day1.part2());
  }
}
