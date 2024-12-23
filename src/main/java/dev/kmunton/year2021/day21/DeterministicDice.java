package dev.kmunton.year2021.day21;

public class DeterministicDice {

  private int one;
  private int two;
  private int three;

  public DeterministicDice() {
    one = 1;
    two = 2;
    three = 3;
  }

  public void nextRoll() {
    setOne(getOne() + 3);
    if (getOne() > 9) {
      setOne(getOne() - 10);
    }
    setTwo(getTwo() + 3);
    if (getTwo() > 9) {
      setTwo(getTwo() - 10);
    }
    setThree(getThree() + 3);
    if (getThree() > 9) {
      setThree(getThree() - 10);
    }
  }

  public int addRolls() {
    return one + two + three;
  }

  public int getOne() {
    return one;
  }

  public void setOne(int one) {
    this.one = one;
  }

  public int getTwo() {
    return two;
  }

  public void setTwo(int two) {
    this.two = two;
  }

  public int getThree() {
    return three;
  }

  public void setThree(int three) {
    this.three = three;
  }

  @Override
  public String toString() {
    return "Dice{" +
        "one=" + one +
        ", two=" + two +
        ", three=" + three +
        '}';
  }
}
