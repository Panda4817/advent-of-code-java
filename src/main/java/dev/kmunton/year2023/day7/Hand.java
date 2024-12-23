package dev.kmunton.year2023.day7;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Hand {

  private final List<Integer> cards;
  private final int type;
  private final long bid;
  private final boolean jokerGame;

  public Hand(String cards, long bid, boolean jokerGame) {
    this.jokerGame = jokerGame;
    this.bid = bid;
    this.cards = Arrays.stream(cards.trim().split("")).map(s -> switch (s) {
      case "A" -> 14;
      case "T" -> 10;
      case "J" -> jokerGame ? 1 : 11;
      case "Q" -> 12;
      case "K" -> 13;
      default -> Integer.parseInt(s);
    }).toList();
    this.type = getTypeFromHand();
  }

  private int getTypeFromHand() {
    if (isFiveOfAKind()) {
      return 7;
    }
    if (isFourOfAKind()) {
      return notJokerGame() ? 6 : 7;
    }

    if (isFullHouse()) {
      return notJokerGame() ? 5 : 7;
    }

    if (isThreeOfAKind()) {
      return notJokerGame() ? 4 : 6;
    }

    if (isTwoPair()) {
      if (notJokerGame()) {
        return 3;
      }
      if (cards.stream().filter(i -> i == 1).count() == 2) {
        return 6;
      }
      return 5;
    }

    if (isOnePair()) {
      return notJokerGame() ? 2 : 4;
    }

    return notJokerGame() ? 1 : 2;
  }

  private boolean notJokerGame() {
    return !jokerGame || !cards.contains(1);
  }

  private boolean isFiveOfAKind() {
    return cards.stream().distinct().count() == 1;
  }

  private boolean isFourOfAKind() {
    return cards.stream().distinct().count() == 2
        && cards.stream().anyMatch(i -> cards.stream().filter(j -> Objects.equals(j, i)).count() == 4);
  }

  private boolean isFullHouse() {
    return cards.stream().distinct().count() == 2
        && cards.stream().anyMatch(i -> cards.stream().filter(j -> Objects.equals(j, i)).count() == 3);
  }

  private boolean isThreeOfAKind() {
    return cards.stream().distinct().count() == 3
        && cards.stream().anyMatch(i -> cards.stream().filter(j -> Objects.equals(j, i)).count() == 3);
  }

  private boolean isTwoPair() {
    return cards.stream().distinct().count() == 3
        && cards.stream().anyMatch(i -> cards.stream().filter(j -> Objects.equals(j, i)).count() == 2);
  }

  private boolean isOnePair() {
    return cards.stream().distinct().count() == 4
        && cards.stream().anyMatch(i -> cards.stream().filter(j -> Objects.equals(j, i)).count() == 2);
  }

  public List<Integer> getCards() {
    return cards;
  }

  public int getType() {
    return type;
  }

  public long getBid() {
    return bid;
  }

  @Override
  public String toString() {
    var cardsString = cards.stream().map(String::valueOf).map(s -> switch (s) {
      case "14" -> "A";
      case "10" -> "T";
      case "11", "1" -> "J";
      case "12" -> "Q";
      case "13" -> "K";
      default -> s;
    }).toList();
    var typeString = switch (this.type) {
      case 7 -> "Five of a kind";
      case 6 -> "Four of a kind";
      case 5 -> "Full house";
      case 4 -> "Three of a kind";
      case 3 -> "Two pair";
      case 2 -> "One pair";
      default -> "High card";
    };
    return "Hand{" +
        "cards=" + cardsString +
        ", type=" + typeString +
        ", bid=" + bid +
        '}';
  }
}
