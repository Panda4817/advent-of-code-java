package dev.kmunton.year2021.day2;


import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.List;

public class Day2 implements Day<Integer, Integer> {

  private final List<Command> data = new ArrayList<>();

  public Day2(List<String> input) {
    processData(input);
  }


  @Override
  public void processData(List<String> input) {
    input.forEach(s -> {
      String[] parts = s.split(" ");
      data.add(new Command(parts[0], Integer.parseInt(parts[1])));
    });
  }

  @Override
  public Integer part1() {
    Submarine sub = new Submarine();
    sub.setCourseNoAim(data);
    return sub.getDepth() * sub.getHorizontalPosition();

  }

  @Override
  public Integer part2() {
    Submarine sub = new Submarine();
    sub.setCourse(data);
    return sub.getDepth() * sub.getHorizontalPosition();
  }
}
