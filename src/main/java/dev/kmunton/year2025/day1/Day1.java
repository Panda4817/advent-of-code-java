package dev.kmunton.year2025.day1;

import dev.kmunton.utils.days.Day;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day1 implements Day<Long, Long> {

  private final List<Rotation> rotations = new ArrayList<>();

  public Day1(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
      input.forEach(s -> {
          int dir = 1;
          if (s.charAt(0) == 'L') {
              dir = -1;
          }
          final int clicks = Integer.parseInt(s.substring(1));
          rotations.add(new Rotation(dir, clicks));
      });
  }

  @Override
  public Long part1() {
      int pointer = 50;
      long pointAtZero = 0L;
      for (final Rotation rotation : rotations) {
          int tempPointer = pointer + (rotation.dir() * rotation.clicks());
          pointer = tempPointer;
          if (tempPointer < 0) {
              pointer = tempPointer % 100;
          }
          if (tempPointer > 99) {
              pointer = tempPointer % 100;
          }
          if (pointer == 0) {
              pointAtZero = pointAtZero + 1;
          }
      }
    return pointAtZero;
  }

  @Override
  public Long part2() {
      int pointer = 50;
      long pointAtZero = 0L;
      for (final Rotation rotation : rotations) {
          for (int i = 0; i < rotation.clicks(); i++) {
              pointer += (rotation.dir());
              if (pointer < 0) {
                  pointer = 99;
              }
              if (pointer > 99) {
                  pointer = 0;
              }
              if (pointer == 0) {
                  pointAtZero = pointAtZero + 1;
              }
          }
      }
      return pointAtZero;
  }

}
