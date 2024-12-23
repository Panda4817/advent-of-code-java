package dev.kmunton.year2021.day18;

import java.util.Optional;

public class SnailfishNumber {

  private long magnitude;
  private Optional<SnailfishNumber> left;
  private Optional<SnailfishNumber> right;
  private Optional<Integer> leftNum;
  private Optional<Integer> rightNum;
  private Optional<SnailfishNumber> parent;

  public SnailfishNumber(SnailfishNumber left, SnailfishNumber right, Integer leftNum, Integer rightNum) {
    this.left = Optional.ofNullable(left);
    this.right = Optional.ofNullable(right);
    this.leftNum = Optional.ofNullable(leftNum);
    this.rightNum = Optional.ofNullable(rightNum);
    magnitude = 0;

  }

  public SnailfishNumber(SnailfishNumber that) {
    this.left = that.getLeft().isPresent() ? Optional.of(new SnailfishNumber(that.getLeft().get())) : Optional.ofNullable(null);
    this.left.ifPresent(snailfishNumber -> snailfishNumber.setParent(this));
    this.right = that.getRight().isPresent() ? Optional.of(new SnailfishNumber(that.getRight().get())) : Optional.ofNullable(null);
    this.right.ifPresent(snailfishNumber -> snailfishNumber.setParent(this));
    this.leftNum = that.getLeftNum().isPresent() ? that.getLeftNum() : Optional.ofNullable(null);
    this.rightNum = that.getRightNum().isPresent() ? that.getRightNum() : Optional.ofNullable(null);

  }

  public SnailfishNumber(Optional<SnailfishNumber> left, Optional<SnailfishNumber> right, Optional<Integer> leftNum, Optional<Integer> rightNum) {
    this.left = left;
    this.right = right;
    this.leftNum = leftNum;
    this.rightNum = rightNum;
    magnitude = 0;
  }

  public long calculateMagnitude(SnailfishNumber node) {
    if (isLeaf(node)) {
      return (node.getLeftNum().get() * 3L) + (node.getRightNum().get() * 2L);
    }

    if (node.getLeftNum().isPresent()) {
      return (node.getLeftNum().get() * 3L) + (calculateMagnitude(node.getRight().get()) * 2L);
    }

    if (node.getRightNum().isPresent()) {
      return (calculateMagnitude(node.getLeft().get()) * 3L) + (node.getRightNum().get() * 2L);
    }

    return (calculateMagnitude(node.getLeft().get()) * 3L) + (calculateMagnitude(node.getRight().get()) * 2L);
  }

  public Boolean split(SnailfishNumber node) {
    if (node.getLeftNum().isPresent() && node.getLeftNum().get() > 9) {
      int numberToSplit = node.getLeftNum().get();
      int leftNum = numberToSplit / 2;
      int rightNum;
      if (numberToSplit % 2 == 0) {
        rightNum = leftNum;
      } else {
        rightNum = leftNum + 1;
      }

      node.setLeftNum(null);
      node.setLeft(new SnailfishNumber(null, null, leftNum, rightNum));
      node.getLeft().get().setParent(node);
      return true;
    }

    if (node.getRightNum().isPresent() && node.getRightNum().get() > 9) {
      int numberToSplit = node.getRightNum().get();
      int leftNum = numberToSplit / 2;
      int rightNum;
      if (numberToSplit % 2 == 0) {
        rightNum = leftNum;
      } else {
        rightNum = leftNum + 1;
      }

      node.setRightNum(null);
      node.setRight(new SnailfishNumber(null, null, leftNum, rightNum));
      node.getRight().get().setParent(node);
      return true;
    }
    return false;
  }


  public void reduction() {
    boolean hasExploded = true;
    boolean hasSplit = true;

    while (hasExploded || hasSplit) {
      hasExploded = recurseExplode(this, 0);

      if (hasExploded) {
        continue;
      }
      hasSplit = recurseSplit(this);


    }
  }

  private boolean isLeaf(SnailfishNumber node) {
    return node.getLeft().isEmpty() && node.getRight().isEmpty();
  }

  public Boolean recurseExplode(SnailfishNumber node, Integer depth) {

    boolean done = false;
    if (!done && isLeaf(node) && depth >= 4) {
      Integer l = node.getLeftNum().get();
      Integer r = node.getRightNum().get();
      findNextLeft(node, l);
      findNextRight(node, r);
      SnailfishNumber parent = node.getParent().get();

      if (parent.getLeft().isPresent() && isLeaf(parent.getLeft().get())) {
        parent.setLeftNum(0);
        parent.setLeft(null);
      } else if (parent.getRight().isPresent() && isLeaf(parent.getRight().get())) {
        parent.setRightNum(0);
        parent.setRight(null);
      }

      done = true;
    }

    if (!done && node.getLeft().isPresent()) {
      done = recurseExplode(node.getLeft().get(), depth + 1);
    }
    if (!done && node.getRight().isPresent()) {
      done = recurseExplode(node.getRight().get(), depth + 1);
    }

    return done;


  }

  public Boolean recurseSplit(SnailfishNumber node) {
    boolean done = false;
    if (node.getLeftNum().isPresent() && node.getLeftNum().get() > 9) {
      int numberToSplit = node.getLeftNum().get();
      int leftNum = numberToSplit / 2;
      int rightNum;
      if (numberToSplit % 2 == 0) {
        rightNum = leftNum;
      } else {
        rightNum = leftNum + 1;
      }

      node.setLeftNum(null);
      node.setLeft(new SnailfishNumber(null, null, leftNum, rightNum));
      node.getLeft().get().setParent(node);
      return true;
    } else if (node.getLeft().isPresent()) {
      done = recurseSplit(node.getLeft().get());
      if (done) {
        return true;
      }
    }

    if (node.getRightNum().isPresent() && node.getRightNum().get() > 9) {
      int numberToSplit = node.getRightNum().get();
      int leftNum = numberToSplit / 2;
      int rightNum;
      if (numberToSplit % 2 == 0) {
        rightNum = leftNum;
      } else {
        rightNum = leftNum + 1;
      }

      node.setRightNum(null);
      node.setRight(new SnailfishNumber(null, null, leftNum, rightNum));
      node.getRight().get().setParent(node);
      return true;
    } else if (node.getRight().isPresent()) {
      done = recurseSplit(node.getRight().get());
      if (done) {
        return true;
      }
    }

    return done;


  }

  private void findNextLeft(SnailfishNumber node, int l) {
    if (node.getParent() == null) {
      return;
    }
    SnailfishNumber current = node;
    SnailfishNumber parent = current.getParent().get();
    boolean found = false;

    while (!found && parent != null) {

      if (parent.getLeft().isPresent() && !current.equals(parent.getLeft().get())) {
        found = traverseLeft(parent.getLeft().get(), l);
      } else if (parent.getLeftNum().isPresent()) {
        parent.setLeftNum(parent.getLeftNum().get() + l);
        found = true;
      }

      current = parent;
      parent = current.getParent().isPresent() ? current.getParent().get() : null;
    }

  }

  private void findNextRight(SnailfishNumber node, int r) {
    if (node.getParent() == null) {
      return;
    }
    SnailfishNumber current = node;
    SnailfishNumber parent = current.getParent().get();

    boolean found = false;

    while (!found && parent != null) {

      if (parent.getRight().isPresent() && !current.equals(parent.getRight().get())) {
        found = traverseRight(parent.getRight().get(), r);
      } else if (parent.getRightNum().isPresent()) {
        parent.setRightNum(parent.getRightNum().get() + r);
        found = true;
      }

      current = parent;
      parent = current.getParent().isPresent() ? current.getParent().get() : null;

    }

  }

  private boolean traverseRight(SnailfishNumber node, int r) {
    boolean found = false;
//        if (node.getParent().isEmpty()) {
//            found = true;
//        }

    if (!found && node.getLeftNum().isPresent()) {
      node.setLeftNum(node.getLeftNum().get() + r);
      found = true;
    }

    if (!found && node.getLeft().isPresent()) {
      found = traverseRight(node.getLeft().get(), r);
    }
    if (!found && node.getRightNum().isPresent()) {
      node.setRightNum(node.getRightNum().get() + r);
      found = true;
    }
    if (!found && node.getRight().isPresent()) {
      found = traverseRight(node.getRight().get(), r);
    }

    return found;
  }

  private boolean traverseLeft(SnailfishNumber node, int l) {
    boolean found = false;
//        if (node.getParent().isEmpty()) {
//            found = true;
//        }

    if (!found && node.getRightNum().isPresent()) {
      node.setRightNum(node.getRightNum().get() + l);
      found = true;
    }
    if (!found && node.getRight().isPresent()) {
      found = traverseLeft(node.getRight().get(), l);
    }
    if (!found && node.getLeftNum().isPresent()) {
      node.setLeftNum(node.getLeftNum().get() + l);
      found = true;
    }
    if (!found && node.getLeft().isPresent()) {
      found = traverseLeft(node.getLeft().get(), l);
    }

    return found;
  }


  public long getMagnitude() {
    return magnitude;
  }

  public void setMagnitude(long magnitude) {
    this.magnitude = magnitude;
  }

  public Optional<SnailfishNumber> getLeft() {
    return left;
  }

  public void setLeft(SnailfishNumber left) {
    this.left = Optional.ofNullable(left);
  }

  public Optional<SnailfishNumber> getRight() {
    return right;
  }

  public void setRight(SnailfishNumber right) {
    this.right = Optional.ofNullable(right);
  }

  public Optional<Integer> getLeftNum() {
    return leftNum;
  }

  public void setLeftNum(Integer leftNum) {
    this.leftNum = Optional.ofNullable(leftNum);
  }

  public Optional<Integer> getRightNum() {
    return rightNum;
  }

  public void setRightNum(Integer rightNum) {
    this.rightNum = Optional.ofNullable(rightNum);
  }


  @Override
  public String toString() {
    String s = "[";
    if (getLeftNum().isPresent()) {
      s += getLeftNum().get() + ",";
    } else {
      s += getLeft().get() + ",";
    }

    if (getRightNum().isPresent()) {
      s += getRightNum().get() + "]";
    } else {
      s += getRight().get() + "]";
    }
    return s;
  }

  public Optional<SnailfishNumber> getParent() {
    return parent;
  }

  public void setParent(SnailfishNumber parent) {
    this.parent = Optional.ofNullable(parent);
  }

//    @Override
//    public Object clone() {
//        try {
//            return (SnailfishNumber) super.clone();
//        } catch (CloneNotSupportedException e) {
//            Integer left = this.getLeftNum().isPresent() ? this.getLeftNum().get() : null;
//            Integer right = this.getRightNum().isPresent() ? this.getRightNum().get() : null;
//            return new SnailfishNumber(
//                    this.getLeft().isPresent() ? (SnailfishNumber) this.getLeft().get().clone() : null,
//                    this.getRight().isPresent()? (SnailfishNumber) this.getRight().get().clone() : null,
//                    left,
//                    right);
//        }
//
//        }
}
