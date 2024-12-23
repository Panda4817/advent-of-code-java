package dev.kmunton.year2023.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day10Test {

  private static Day10 day;

  @Test
  void part1() {
    var input = """
        7-F7-
        .FJ|7
        SJLL7
        |F--J
        LJ.LJ
        """.lines().toList();
    var day = new Day10(input);
    assertEquals(8, day.part1());

  }

  @Test
  void part1_withJunkPipe() {
    var input = """
        -L|F7
        7S-7|
        L|7||
        -L-J|
        L|-JF
        """.lines().toList();
    var day = new Day10(input);
    assertEquals(4, day.part1());
  }

  @Test
  void part2_edgeCase() {
    var input = """
        .F----7F7F7F7F-7....
        .|F--7||||||||FJ....
        .||.FJ||||||||L7....
        FJL7L7LJLJ||LJ.L-7..
        L--J.L7...LJS7F-7L7.
        ....F-J..F7FJ|L7L7L7
        ....L7.F7||L7|.L7L7|
        .....|FJLJ|FJ|F7|.LJ
        ....FJL-7.||.||||...
        ....L---J.LJ.LJLJ...
        """.lines().toList();
    var day = new Day10(input);
    assertEquals(8, day.part2());
  }

  @Test
  void part2_withJunkPipe() {
    var input = """
        FF7FSF7F7F7F7F7F---7
        L|LJ||||||||||||F--J
        FL-7LJLJ||||||LJL-77
        F--JF--7||LJLJ7F7FJ-
        L---JF-JLJ.||-FJLJJ7
        |F|F-JF---7F7-L7L|7|
        |FFJF7L7F-JF7|JL---7
        7-L-JL7||F7|L7F-7F7|
        L.L7LFJ|||||FJL7||LJ
        L7JLJL-JLJLJL--JLJ.L
         """.lines().toList();
    var day = new Day10(input);
    assertEquals(10, day.part2());
  }

  @Test
  void part2_justLoop() {
    var input = """
        ..........
        .S------7.
        .|F----7|.
        .||....||.
        .||....||.
        .|L-7F-J|.
        .|..||..|.
        .L--JL--J.
        ..........
        """.lines().toList();
    var day = new Day10(input);
    assertEquals(4, day.part2());
  }
}
