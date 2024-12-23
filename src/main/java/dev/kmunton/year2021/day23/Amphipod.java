package dev.kmunton.year2021.day23;

public class Amphipod {

  private String letter;
  private Integer number;
  private Integer energyPerStep;
  private Integer burrowX;

  public Amphipod(String letter, Integer number, Integer energyPerStep, Integer burrowX) {
    this.letter = letter;
    this.number = number;
    this.energyPerStep = energyPerStep;
    this.burrowX = burrowX;
  }

  public String getLetter() {
    return letter;
  }

  public void setLetter(String letter) {
    this.letter = letter;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Integer getEnergyPerStep() {
    return energyPerStep;
  }

  public void setEnergyPerStep(Integer energyPerStep) {
    this.energyPerStep = energyPerStep;
  }

  public Integer getBurrowX() {
    return burrowX;
  }

  public void setBurrowX(Integer burrowX) {
    this.burrowX = burrowX;
  }
}
