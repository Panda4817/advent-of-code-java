package dev.kmunton.year2021.day8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Signal {

  private List<String> signal;
  private List<String> output;
  private final Map<Integer, String> mappings = new HashMap<>();
  private Map<String, String> mappingsReversed;

  public Signal(List<String> signal, List<String> output) {
    this.signal = signal.stream().map(s -> sort(s)).collect(Collectors.toList());
    this.output = output.stream().map(s -> sort(s)).collect(Collectors.toList());
    deduce();
  }

  private String sort(String s) {
    char[] chars = s.toCharArray();
    Arrays.sort(chars);
    return new String(chars);
  }

  private void deduce() {
    for (String s : getSignal()) {
      Integer i = s.length();
      if (i == 2 || i == 3 || i == 4 || i == 7) {
        switch (i) {
          case 2 -> mappings.put(1, s);
          case 3 -> mappings.put(7, s);
          case 4 -> mappings.put(4, s);
          case 7 -> mappings.put(8, s);
          default -> {
            break;
          }
        }
      }
    }
    String nine = "";
    String five = "";
    String six = "";
    String zero = "";
    String two = "";
    String three = "";
    for (String s : signal) {
      int length = s.length();
      if (length == 5) {
        StringBuilder tempOne = new StringBuilder(s);
        for (char c : mappings.get(1).toCharArray()) {
          if (!s.contains(String.valueOf(c))) {
            tempOne.append(c);
          }
        }
        StringBuilder tempFour = new StringBuilder(s);
        for (char c : mappings.get(4).toCharArray()) {
          if (!s.contains(String.valueOf(c))) {
            tempFour.append(c);
          }
        }
        if (tempOne.toString().length() == 6 && tempFour.toString().length() == 6) {
          five = s;
        } else if (tempOne.toString().length() == 6 && tempFour.toString().length() == 7) {
          two = s;
        } else {
          three = s;
        }
      }
      if (length == 6) {
        StringBuilder tempSeven = new StringBuilder(s);
        for (char c : mappings.get(7).toCharArray()) {
          if (!s.contains(String.valueOf(c))) {
            tempSeven.append(c);
          }
        }
        StringBuilder tempFour = new StringBuilder(s);
        for (char c : mappings.get(4).toCharArray()) {
          if (!s.contains(String.valueOf(c))) {
            tempFour.append(c);
          }
        }
        if (tempSeven.toString().length() == 6 && tempFour.toString().length() == 7) {
          zero = s;
        } else if (tempSeven.toString().length() == 7 && tempFour.toString().length() == 7) {
          six = s;
        } else {
          nine = s;
        }

      }
    }
    mappings.put(9, nine);
    mappings.put(5, five);
    mappings.put(6, six);
    mappings.put(2, two);
    mappings.put(0, zero);
    mappings.put(3, three);

    mappingsReversed = new HashMap<>();
    for (Map.Entry<Integer, String> entry : mappings.entrySet()) {
      mappingsReversed.put(entry.getValue(), String.valueOf(entry.getKey()));
    }


  }

  public int deduceOutput() {
    String i = "";
    for (String o : output) {
      i += mappingsReversed.get(o);
    }
    return Integer.parseInt(i);
  }

  public List<String> getSignal() {
    return signal;
  }

  public void setSignal(List<String> signal) {
    this.signal = signal;
  }

  public List<String> getOutput() {
    return output;
  }

  public void setOutput(List<String> output) {
    this.output = output;
  }

  @Override
  public String toString() {
    return "Signal{" +
        "signal=" + signal +
        ", output=" + output +
        '}';
  }
}
