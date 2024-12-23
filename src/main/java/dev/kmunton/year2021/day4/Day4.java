package dev.kmunton.year2021.day4;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 implements Day<Integer, Integer> {

  private List<Board> boards;
  private List<Integer> drawNumbers;
  private final int size;

  public Day4(List<String> input) {
    processData(input);
    size = 5;
  }

  @Override
  public void processData(List<String> input) {
    boards = new ArrayList<>();
    Board board = new Board();
    int i = 0;
    for (String s : input) {
      if (i == 0) {
        List<String> lst = new ArrayList<String>(List.of(s.split(",")));
        drawNumbers = lst.stream().map(Integer::valueOf).collect(Collectors.toList());
        i += 1;
        continue;
      }
      if (s == "") {
        if (board.getNumbers().size() != 0) {
          boards.add(board);
          board = new Board();
        }
        continue;
      }
      List<Integer> row = new ArrayList<String>(List.of(s.split(" "))).stream().filter(c -> c != "").map(Integer::valueOf)
                                                                      .collect(Collectors.toList());
      board.addRow(row);
    }
    boards.add(board);
  }

  private Boolean winnerFound() {
    for (Board b : boards) {
      if (b.isWinner()) {
        return true;
      }
    }
    return false;
  }

  private Board winner() {
    for (Board b : boards) {
      if (b.isWinner()) {
        return b;
      }
    }
    return new Board();
  }

  private List<Integer> winnerIndex() {
    int i = -1;
    List<Integer> winners = new ArrayList<>();
    for (Board b : boards) {
      i += 1;
      if (b.isWinner()) {
        winners.add(i);
      }
    }
    return winners;
  }

  private void markOnBoards(Integer num) {
    for (Board b : boards) {
      b.markIfOnBoard(num);
    }
  }

  @Override
  public Integer part1() {

    Integer drawnNumber = 0;
    while (!winnerFound() && drawNumbers.size() > 0) {

      drawnNumber = drawNumbers.remove(0);
      markOnBoards(drawnNumber);
    }

    int score = winner().calculateScore(drawnNumber);
    return score;
  }

  @Override
  public Integer part2() {
    Integer drawnNumber = 0;
    while (drawNumbers.size() > 0) {

      drawnNumber = drawNumbers.remove(0);
      markOnBoards(drawnNumber);
      List<Integer> indexes = winnerIndex();
      int adjustIndex = 0;
      while (boards.size() > 1 && indexes.size() > 0) {
        int index = indexes.remove(0);
        index -= adjustIndex;
        boards.remove(index);
        adjustIndex += 1;

      }
      if (boards.size() == 1 && winnerFound()) {
        break;
      }
    }

    int score = boards.get(0).calculateScore(drawnNumber);
    return score;
  }
}
