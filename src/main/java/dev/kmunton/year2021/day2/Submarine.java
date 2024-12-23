package dev.kmunton.year2021.day2;

import java.util.List;

public class Submarine {

  private int horizontalPosition;
  private int depth;
  private int aim;

  public Submarine() {
    horizontalPosition = 0;
    depth = 0;
    aim = 0;
  }

  public void increasePosition(Command command) {
    int amount = command.getAmount();
    String direction = command.getDirection();
    switch (direction) {
      case "forward":
        int newHorizontalPosition = getHorizontalPosition() + amount;
        int newDepth = getDepth() + (getAim() * amount);
        setHorizontalPosition(newHorizontalPosition);
        setDepth(newDepth);
        break;
      case "down":
        int newAim = getAim() + amount;
        setAim(newAim);
        break;
      case "up":
        newAim = getAim() - amount;
        setAim(newAim);
        break;
      default:
        break;
    }
  }

  public void increasePositionNoAim(Command command) {
    int amount = command.getAmount();
    String direction = command.getDirection();
    switch (direction) {
      case "forward":
        int newHorizontalPosition = getHorizontalPosition() + amount;
        setHorizontalPosition(newHorizontalPosition);
        break;
      case "down":
        int newDepth = getDepth() + amount;
        setDepth(newDepth);
        break;
      case "up":
        newDepth = getDepth() - amount;
        setDepth(newDepth);
        break;
      default:
        break;
    }
  }

  public void setCourse(List<Command> commands) {
    for (Command c : commands) {
      increasePosition(c);
    }
  }

  public void setCourseNoAim(List<Command> commands) {
    for (Command c : commands) {
      increasePositionNoAim(c);
    }
  }

  public int getHorizontalPosition() {
    return horizontalPosition;
  }

  public void setHorizontalPosition(int horizontalPosition) {
    this.horizontalPosition = horizontalPosition;
  }

  public int getDepth() {
    return depth;
  }

  public void setDepth(int depth) {
    this.depth = depth;
  }

  public int getAim() {
    return aim;
  }

  public void setAim(int aim) {
    this.aim = aim;
  }

  @Override
  public String toString() {
    return "Submarine{" +
        "horizontalPosition=" + horizontalPosition +
        ", depth=" + depth +
        ", aim=" + aim +
        '}';
  }
}
