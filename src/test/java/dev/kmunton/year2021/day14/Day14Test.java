package dev.kmunton.year2021.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day14Test {

  private static Day14 day;

  @Test
  void part1_success() {
    List<String> input = """
        NNCB
                
        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C""".lines().toList();
    day = new Day14(input);
    assertEquals(1588, day.part1());
  }

  @Test
  void part2_success() {
    List<String> input = """
        NNCB
                
        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C""".lines().toList();
    day = new Day14(input);
    assertEquals(2188189693529L, day.part2());
  }

}
