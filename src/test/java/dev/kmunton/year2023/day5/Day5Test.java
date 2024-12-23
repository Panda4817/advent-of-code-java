package dev.kmunton.year2023.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day5Test {

  private static Day5 day;
  private static final List<String> INPUT = """
      seeds: 79 14 55 13
              
      seed-to-soil map:
      50 98 2
      52 50 48
              
      soil-to-fertilizer map:
      0 15 37
      37 52 2
      39 0 15
              
      fertilizer-to-water map:
      49 53 8
      0 11 42
      42 0 7
      57 7 4
              
      water-to-light map:
      88 18 7
      18 25 70
              
      light-to-temperature map:
      45 77 23
      81 45 19
      68 64 13
              
      temperature-to-humidity map:
      0 69 1
      1 0 69
              
      humidity-to-location map:
      60 56 37
      56 93 4
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day5(INPUT);
  }

  @Test
  void part1() {
    assertEquals(35, day.part1());
  }

  @Test
  void part2() {
    assertEquals(46, day.part2());
  }
}
