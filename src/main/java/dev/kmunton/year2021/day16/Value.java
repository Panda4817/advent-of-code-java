package dev.kmunton.year2021.day16;

public class Value {

  private long val;
  private int index;

  public Value(long value, int index) {
    this.val = value;
    this.index = index;
  }

  public long getVal() {
    return val;
  }

  public void setVal(long val) {
    this.val = val;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
