package dev.kmunton.year2021.day21;

public class Player {

  private int number;
  private int space;
  private int score;

  public Player(int space, int number) {
    this.number = number;
    this.space = space;
    this.score = 0;
  }

  public Player(Player other) {
    this.number = other.getNumber();
    this.space = other.getSpace();
    this.score = other.getScore();
  }

  public boolean isWinner(int score) {
    return getScore() >= score;
  }

  public void update(int rollSum) {
    setSpace(getSpace() + rollSum);
    while (getSpace() > 10) {
      setSpace((getSpace() - 10));
    }
    setScore(getScore() + getSpace());
  }

  public int getSpace() {
    return space;
  }

  public void setSpace(int space) {
    this.space = space;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return "Player{" +
        "number=" + number +
        ", space=" + space +
        ", score=" + score +
        '}';
  }
}
