package dev.kmunton.year2023.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day25Test {

  private static Day25 day;
  private static final List<String> INPUT = """
      jqt: rhn xhk nvd
      rsh: frs pzl lsr
      xhk: hfx
      cmg: qnr nvd lhk bvb
      rhn: xhk bvb hfx
      bvb: xhk hfx
      pzl: lsr hfx nvd
      qnr: nvd
      ntq: jqt hfx bvb xhk
      nvd: lhk
      lsr: lhk
      rzs: qnr cmg lsr rsh
      frs: qnr lhk lsr
      """.lines().toList();

  @BeforeAll
  static void beforeAll() {
    day = new Day25(INPUT);
  }

  @Test
  void part1() {
    assertEquals(54, day.part1());

  }

  @Test
  void part2() {
    assertEquals(0, day.part2());
  }
}
