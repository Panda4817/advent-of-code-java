package dev.kmunton.year2023.day20;

import java.util.List;
import java.util.Map;

public class Module {

  private String name;
  private String type;
  private String state;
  private Map<String, String> memory;
  private List<String> destinations;

  public Module(String name, String type, String state, List<String> destinations, Map<String, String> memory) {
    this.name = name;
    this.type = type;
    this.state = state;
    this.destinations = destinations;
    this.memory = memory;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Map<String, String> getMemory() {
    return memory;
  }

  public void setMemory(Map<String, String> memory) {
    this.memory = memory;
  }

  public List<String> getDestinations() {
    return destinations;
  }

  public void setDestinations(List<String> destinations) {
    this.destinations = destinations;
  }

  @Override
  public String toString() {
    return "Module{" +
        "name='" + name + '\'' +
        ", type='" + type + '\'' +
        ", state='" + state + '\'' +
        ", memory=" + memory +
        ", destinations=" + destinations +
        '}';
  }
}
