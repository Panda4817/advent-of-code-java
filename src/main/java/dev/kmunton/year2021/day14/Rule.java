package dev.kmunton.year2021.day14;

public class Rule {

  private String letter1;
  private String letter2;
  private String output;

  public Rule(String letter1, String letter2, String output) {
    this.letter1 = letter1;
    this.letter2 = letter2;
    this.output = output;
  }

  public String getLetter1() {
    return letter1;
  }

  public void setLetter1(String letter1) {
    this.letter1 = letter1;
  }

  public String getLetter2() {
    return letter2;
  }

  public void setLetter2(String letter2) {
    this.letter2 = letter2;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }
}
