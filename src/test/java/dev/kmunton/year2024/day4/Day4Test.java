package dev.kmunton.year2024.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day4Test {

  private static Day4 day;

  @Test
  void part1_success() {
    List<String> input = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX""".lines().toList();
    day =  new Day4(input);
    assertEquals(18L, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX""".lines().toList();
    day =  new Day4(input);
    assertEquals(9L, day.part2());
  }

}
