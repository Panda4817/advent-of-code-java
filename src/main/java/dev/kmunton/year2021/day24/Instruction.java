package dev.kmunton.year2021.day24;

public class Instruction {

  private String name;
  private String a;
  private String b;

  public Instruction(String name, String a, String b) {
    this.name = name;
    this.a = a;
    this.b = b;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getA() {
    return a;
  }

  public void setA(String a) {
    this.a = a;
  }

  public String getB() {
    return b;
  }

  public void setB(String b) {
    this.b = b;
  }

  @Override
  public String toString() {
    return "Instruction{" +
        "name='" + name + '\'' +
        ", a='" + a + '\'' +
        ", b='" + b + '\'' +
        '}';
  }
}
