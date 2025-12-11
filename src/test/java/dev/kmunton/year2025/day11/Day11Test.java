package dev.kmunton.year2025.day11;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

  private static Day11 day;

  @Test
  void part1_success() {
      List<String> input = """
              aaa: you hhh
              you: bbb ccc
              bbb: ddd eee
              ccc: ddd eee fff
              ddd: ggg
              eee: out
              fff: out
              ggg: out
              hhh: ccc fff iii
              iii: out""".lines().toList();
      day = new Day11(input);
    assertEquals(5L, day.part1());
  }

  @Test
  void part2_success() {
      List<String> input = """
              svr: aaa bbb
              aaa: fft
              fft: ccc
              bbb: tty
              tty: ccc
              ccc: ddd eee
              ddd: hub
              hub: fff
              eee: dac
              dac: fff
              fff: ggg hhh
              ggg: out
              hhh: out""".lines().toList();
      day = new Day11(input);
    assertEquals(2L, day.part2());
  }
}
