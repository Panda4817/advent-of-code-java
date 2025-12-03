package dev.kmunton.year2025.day3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

  private static Day3 day;

  @Test
  void part1_success() {
    List<String> input = """
           987654321111111
           811111111111119
           234234234234278
           818181911112111""".lines().toList();
    day = new Day3(input);
    assertEquals(357L, day.part1());
  }

  @Test
  void part2_easyCases_success() {
    List<String> input = """
           987654321111111
           811111111111119""".lines().toList();
    day = new Day3(input);
    assertEquals(987654321111L + 811111111119L, day.part2());
  }

    @Test
    void part2_hardCaseOne_success() {
        List<String> input = """
           234234234234278""".lines().toList();
        day = new Day3(input);
        assertEquals(434234234278L, day.part2());
    }

    @Test
    void part2_hardCaseTwo_success() {
        List<String> input = """
           818181911112111""".lines().toList();
        day = new Day3(input);
        assertEquals(888911112111L, day.part2());
    }
}
