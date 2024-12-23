package dev.kmunton.year2021.day2;

public class Command {

  private String direction;
  private int amount;

  public Command(String direction, int amount) {
    this.direction = direction;
    this.amount = amount;
  }


  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "Command{" +
        "direction='" + direction + '\'' +
        ", amount=" + amount +
        '}';
  }
}
