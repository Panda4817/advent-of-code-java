package dev.kmunton.year2023.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day12Test {

  private static Day12 day;
  private static final List<String> INPUT = """
      ???.### 1,1,3
      .??..??...?##. 1,1,3
      ?#?#?#?#?#?#?#? 1,3,1,6
      ????.#...#... 4,1,1
      ????.######..#####. 1,6,5
      ?###???????? 3,2,1
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day12(INPUT);
  }

  @Test
  void part1() {
    assertEquals(21, day.part1());

  }

  @Test
  void part2() {
    assertEquals(525152, day.part2());
  }
}
