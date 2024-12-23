package dev.kmunton.year2023.day15;

public class Lens {

  private final String label;
  private long focalLength;

  public Lens(String label, long focalLength) {
    this.label = label;
    this.focalLength = focalLength;
  }

  public long focalLength() {
    return focalLength;
  }

  public String label() {
    return label;
  }

  public void setFocalLength(long focalLength) {
    this.focalLength = focalLength;
  }

  @Override
  public String toString() {
    return "Lens{" +
        "label='" + label + '\'' +
        ", focalLength=" + focalLength +
        '}';
  }
}
