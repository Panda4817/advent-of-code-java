package dev.kmunton.year2021.day4;

import java.util.ArrayList;
import java.util.List;

public class Board {

  private List<List<Integer>> numbers;
  private final List<List<Boolean>> marked;
  private final int size;

  public Board() {
    numbers = new ArrayList<>();
    marked = new ArrayList<>();
    size = 5;
  }

  public List<List<Integer>> getNumbers() {
    return numbers;
  }

  public void addRow(List<Integer> row) {
    numbers.add(row);
    Boolean[] bools = {false, false, false, false, false};
    marked.add(new ArrayList<Boolean>(List.of(bools)));
  }

  public Boolean isWinner() {
    for (List<Boolean> row : marked) {
      if (row.stream().filter(b -> b).count() == size) {
        return true;
      }
    }

    for (int i = 0; i < size; i++) {
      List<Boolean> col = new ArrayList<>();
      for (List<Boolean> row : marked) {
        if (!row.get(i)) {
          break;
        }
        col.add(row.get(i));
      }

      if (col.size() == size) {
        return true;
      }

    }

    return false;
  }

  public void markIfOnBoard(Integer num) {
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        Integer n = numbers.get(r).get(c);
        if (n == num) {
          marked.get(r).set(c, true);
        }
      }
    }
  }

  public Integer calculateScore(Integer lastDrawn) {
    Integer sum = 0;
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        Boolean bool = marked.get(r).get(c);
        if (!bool) {
          sum += numbers.get(r).get(c);
        }
      }
    }
    System.out.println(sum + " " + lastDrawn);
    return sum * lastDrawn;
  }

  public void setNumbers(List<List<Integer>> numbers) {
    this.numbers = numbers;
  }

  @Override
  public String toString() {
    String res = "";
    for (List<Integer> lst : numbers) {
      for (Integer i : lst) {
        res += i + " ";
      }
      res += "\n";
    }
    res += "\n";
    for (List<Boolean> lst : marked) {
      for (Boolean bool : lst) {
        res += bool + " ";
      }
      res += "\n";
    }
    return res;
  }
}
