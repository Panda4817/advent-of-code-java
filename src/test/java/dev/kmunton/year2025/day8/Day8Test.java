package dev.kmunton.year2025.day8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {

  private static Day8 day;

  @BeforeEach
  void setUp() {
      List<String> input = """
              162,817,812
              57,618,57
              906,360,560
              592,479,940
              352,342,300
              466,668,158
              542,29,236
              431,825,988
              739,650,466
              52,470,668
              216,146,977
              819,987,18
              117,168,530
              805,96,715
              346,949,466
              970,615,88
              941,993,340
              862,61,35
              984,92,344
              425,690,689""".lines().toList();
      day = new Day8(input);
  }

  @Test
  void part1_success() {
    assertEquals(40L, day.part1_test());
  }

  @Test
  void part2_success() {
    assertEquals(25272L, day.part2());
  }
}
