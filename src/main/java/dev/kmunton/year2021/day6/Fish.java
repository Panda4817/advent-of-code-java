package dev.kmunton.year2021.day6;

import java.util.Objects;

public class Fish {

  private int timer;
  private boolean young;


  public Fish(int timer) {
    this.timer = timer;
    young = timer == 8;

  }

  public int getTimer() {
    return timer;
  }

  public void setTimer(int timer) {
    this.timer = timer;
  }

  public void decreaseTimer() {
    int newTimer = getTimer() - 1;
    setTimer(newTimer);
    if (getTimer() == -1) {
      setTimer(6);
      if (isYoung()) {
        setYoung(false);
      }
    }
  }

  public boolean isYoung() {
    return young;
  }

  public void setYoung(boolean young) {
    this.young = young;
  }

  @Override
  public String toString() {
    return String.valueOf(getTimer());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Fish fish = (Fish) o;
    return timer == fish.timer && young == fish.young;
  }

  @Override
  public int hashCode() {
    return Objects.hash(timer, young);
  }
}
