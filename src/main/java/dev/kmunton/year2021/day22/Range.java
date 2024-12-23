package dev.kmunton.year2021.day22;

import java.util.Optional;

public class Range {

  private final Cube from;
  private final Cube to;
  private final Boolean isOn;

  public Range(Cube from, Cube to, Boolean isOn) {
    this.from = from;
    this.to = to;
    this.isOn = isOn;
  }

  public Range(Range r) {
    this.from = new Cube(r.getFrom());
    this.to = new Cube(r.getTo());
    this.isOn = r.getOn();
  }

  public long numberOfCubes() {
    return ((long) (getTo().getX() + 1 - getFrom().getX()) * (getTo().getY() + 1 - getFrom().getY()) * (getTo().getZ() + 1 - getFrom().getZ()));
  }


  public Optional<Range> getOverlapping(Range other) {
    Cube from = new Cube(Math.max(getFrom().getX(), other.getFrom().getX()), Math.max(getFrom().getY(), other.getFrom().getY()),
        Math.max(getFrom().getZ(), other.getFrom().getZ()));
    Cube to = new Cube(Math.min(getTo().getX(), other.getTo().getX()), Math.min(getTo().getY(), other.getTo().getY()),
        Math.min(getTo().getZ(), other.getTo().getZ()));
    boolean state = !getOn();
    if (from.getX() <= to.getX() && from.getY() <= to.getY() && from.getZ() <= to.getZ()) {
      return Optional.of(new Range(from, to, state));
    }

    return Optional.ofNullable(null);
  }


  public Cube getFrom() {
    return from;
  }


  public Cube getTo() {
    return to;
  }


  public Boolean getOn() {
    return isOn;
  }


  @Override
  public String toString() {
    return "Range{" +
        "from=" + from +
        ", to=" + to +
        ", isOn=" + isOn +
        '}';
  }
}
