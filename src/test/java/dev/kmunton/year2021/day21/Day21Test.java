package dev.kmunton.year2021.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day21Test {

  private static Day21 day;

  @Test
  void part1_success() {
    List<String> input = """
        Player 1 starting position: 4
        Player 2 starting position: 8""".lines().toList();
    day = new Day21(input);
    assertEquals(739785, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        Player 1 starting position: 4
        Player 2 starting position: 8""".lines().toList();
    day = new Day21(input);
    assertEquals(444356092776315L, day.part2());
  }

}
