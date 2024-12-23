package dev.kmunton.year2021.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day25Test {

  private static Day25 day;

  @Test
  void part1_success() {
    List<String> input = """
        v...>>.vv>
        .vv>>.vv..
        >>.>v>...v
        >>v>>.>.v.
        v>v.vv.v..
        >.>>..v...
        .vv..>.>v.
        v.v..>>v.v
        ....v..v.>""".lines().toList();
    day = new Day25(input);
    assertEquals(58, day.part1());
  }

}
