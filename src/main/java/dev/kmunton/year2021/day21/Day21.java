package dev.kmunton.year2021.day21;

import dev.kmunton.utils.days.Day;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 implements Day<Integer, Long> {

  private Player one;
  private Player two;
  private DeterministicDice deterministicDice;
  private long playerOneWins;
  private long playerTwoWins;
  private final Map<Integer, Long> diracDice = new HashMap<>();

  public Day21(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    one = new Player(Integer.parseInt(input.get(0).split(": ")[1]), 1);
    two = new Player(Integer.parseInt(input.get(1).split(": ")[1]), 1);
    deterministicDice = new DeterministicDice();
    playerOneWins = 0L;
    playerTwoWins = 0L;
    diracDice.put(3, 1L);
    diracDice.put(9, 1L);
    diracDice.put(4, 3L);
    diracDice.put(5, 6L);
    diracDice.put(6, 7L);
    diracDice.put(7, 6L);
    diracDice.put(8, 3L);


  }

  private boolean isWinner(int score) {
    return one.getScore() >= score || two.getScore() >= score;
  }

  @Override
  public Integer part1() {
    int rolls = 0;
    boolean player1turn = true;
    while (!isWinner(1000)) {
      rolls += 3;
      if (player1turn) {
        one.update(deterministicDice.addRolls());
        player1turn = false;
      } else {
        two.update(deterministicDice.addRolls());
        player1turn = true;
      }
      deterministicDice.nextRoll();
    }
    int part1;
    if (!one.isWinner(1000)) {
      part1 = (rolls) * one.getScore();
    } else {
      part1 = (rolls) * two.getScore();
    }
    return part1;
  }

  private synchronized void updateP1wins(Long uCount) {
    playerOneWins += uCount;
  }

  private synchronized void updateP2wins(Long uCount) {
    playerTwoWins += uCount;
  }

  private void recurse(long uCount, Player one, Player two, boolean isPlayerOneTurn) {
    if (one.isWinner(21)) {
      updateP1wins(uCount);
      return;
    } else if (two.isWinner(21)) {
      updateP2wins(uCount);
      return;
    }

    if (isPlayerOneTurn) {
      for (int d = 3; d < 10; d++) {
        Player p = new Player(one);
        p.update(d);
        recurse(diracDice.get(d) * uCount, p, new Player(two), false);
      }
    } else {
      for (int d = 3; d < 10; d++) {
        Player p = new Player(two);
        p.update(d);
        recurse(diracDice.get(d) * uCount, new Player(one), p, true);
      }
    }

  }

  @Override
  public Long part2() {
    recurse(1L, one, two, true);

    System.out.println("PLAYER 1 WINS: " + playerOneWins + " games");
    System.out.println("PLAYER 2 WINS: " + playerTwoWins + " games");
    return Math.max(playerOneWins, playerTwoWins);
  }
}
