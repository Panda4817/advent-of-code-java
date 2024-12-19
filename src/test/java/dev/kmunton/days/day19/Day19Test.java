package dev.kmunton.days.day19;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day19Test {

  private static Day19 day;

  @Test
  void part1_success() {
    List<String> input = """
        r, wr, b, g, bwu, rb, gb, br
                
        brwrr
        bggr
        gbbr
        rrbgbr
        ubwu
        bwurrg
        brgr
        bbrgwb""".lines().toList();
    day = new Day19(input);
    assertEquals(6L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        r, wr, b, g, bwu, rb, gb, br
                
        brwrr
        bggr
        gbbr
        rrbgbr
        ubwu
        bwurrg
        brgr
        bbrgwb""".lines().toList();
    day = new Day19(input);
    assertEquals(16L, day.part2());
  }

}
