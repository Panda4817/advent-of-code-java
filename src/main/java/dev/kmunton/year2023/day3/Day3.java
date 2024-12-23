package dev.kmunton.year2023.day3;

import static java.lang.Integer.parseInt;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day3 implements Day<Long, Long> {

  private final List<List<String>> data = new ArrayList<>();

  public Day3(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    data.addAll(input.stream().map(line -> Arrays.stream(line.split("")).toList()).toList());
  }

  public Long part1() {
    var numbers = getNumbersWithNeighbours();
    var numbersToAdd = new ArrayList<Integer>();
    numbers.forEach((key, value) -> {
      if (value.stream().anyMatch(neighbour -> {
        var neighbourData = data.get(neighbour.get(0)).get(neighbour.get(1));
        return !Objects.equals(neighbourData, ".") && !neighbourData.matches("\\d");
      })) {
        numbersToAdd.add(parseNumberFromKey(key));
      }
    });
    return numbersToAdd.stream().mapToLong(Integer::longValue).sum();
  }

  public Long part2() {
    List<List<Integer>> stars = findAllStars();
    Map<String, List<List<Integer>>> numbers = getNumbersWithNeighbours();
    var sumOfGearRatios = 0;
    final var maxNumberOfGears = 2;
    for (var star : stars) {
      var starRow = star.get(0);
      var starCol = star.get(1);
      var adjacentNumbers = new ArrayList<Integer>();
      for (var entry : numbers.entrySet()) {
        if (adjacentNumbers.size() > maxNumberOfGears) {
          break;
        }
        if (entry.getValue().stream().anyMatch(numberNeighbour ->
            Objects.equals(numberNeighbour.get(0), starRow)
                && Objects.equals(numberNeighbour.get(1), starCol))) {
          adjacentNumbers.add(parseNumberFromKey(entry.getKey()));
        }
      }
      if (adjacentNumbers.size() == maxNumberOfGears) {
        sumOfGearRatios += (adjacentNumbers.get(0) * adjacentNumbers.get(1));
      }
    }

    return (long) sumOfGearRatios;
  }

  private List<List<Integer>> getNeighbours(int row, int startCol, int endCol) {
    var neighbours = new ArrayList<List<Integer>>();
    for (int i = row - 1; i <= row + 1; i++) {
      if (i < 0 || i >= data.size()) {
        continue;
      }
      for (int j = startCol - 1; j <= endCol + 1; j++) {
        if (j < 0 || j >= data.get(i).size()) {
          continue;
        }
        if (i == row && j >= startCol && j <= endCol) {
          continue;
        }
        neighbours.add(List.of(i, j));
      }
    }
    return neighbours;
  }

  private Map<String, List<List<Integer>>> getNumbersWithNeighbours() {
    var numbers = new HashMap<String, List<List<Integer>>>();
    for (int row = 0; row < data.size(); row++) {
      var rowData = data.get(row);
      var startCol = -1;
      var endCol = -1;
      StringBuilder number = new StringBuilder();
      for (int col = 0; col < rowData.size(); col++) {
        var colData = rowData.get(col);
        if (!colData.matches("\\d")) {
          endCol = col - 1;
          if (!number.isEmpty()) {
            numbers.put(row + String.valueOf(startCol) + endCol + "_" + number,
                getNeighbours(row, startCol, endCol));
          }

          number = new StringBuilder();
          continue;
        }
        if (number.isEmpty()) {
          startCol = col;
        }
        number.append(colData);
      }

      endCol = rowData.size() - 1;
      if (!number.isEmpty()) {
        numbers.put(row + String.valueOf(startCol) + endCol + "_" + number,
            getNeighbours(row, startCol, endCol));
      }


    }
    return numbers;
  }

  private List<List<Integer>> findAllStars() {
    var stars = new ArrayList<List<Integer>>();
    for (int row = 0; row < data.size(); row++) {
      var rowData = data.get(row);
      for (int col = 0; col < rowData.size(); col++) {
        var colData = rowData.get(col);
        if (colData.equals("*")) {
          stars.add(List.of(row, col));
        }
      }
    }
    return stars;
  }

  private int parseNumberFromKey(String key) {
    return parseInt(key.split("_")[1]);
  }


}
