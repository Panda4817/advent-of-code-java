package dev.kmunton.year2024.day25;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day25Test {

  private static Day25 day;

  @Test
  void part1_success() {
    List<String> input = """
        #####
        .####
        .####
        .####
        .#.#.
        .#...
        .....
                
        #####
        ##.##
        .#.##
        ...##
        ...#.
        ...#.
        .....
                
        .....
        #....
        #....
        #...#
        #.#.#
        #.###
        #####
                
        .....
        .....
        #.#..
        ###..
        ###.#
        ###.#
        #####
                
        .....
        .....
        .....
        #....
        #.#..
        #.#.#
        #####
        """.lines().toList();
    day = new Day25(input);
    assertEquals(3, day.part1());
  }
}
