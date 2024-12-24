package dev.kmunton.year2024.day24;


import static java.lang.String.format;

import dev.kmunton.utils.days.Day;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiFunction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day24 implements Day<Long, String> {

  private final Map<String, Boolean> wireToOutput = new HashMap<>();
  private Map<String, Boolean> wireToOutputPart1 = new HashMap<>();
  private final Set<Gate> gates = new HashSet<>();
  private String highestZ = "z00";


  public Day24(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    boolean gateSection = false;
    int i = 1;
    for (String line : input) {
      if (line.equals("")) {
        gateSection = true;
        continue;
      }
      if (gateSection) {
        String[] parts = line.split(" -> ");
        if (parts[0].contains("AND")) {
          String[] wires = parts[0].split(" AND ");
          Gate gate = new Gate(wires[0], wires[1], parts[1], (a, b) -> a && b, "AND", i);
          gates.add(gate);
        }
        if (parts[0].contains(" OR ")) {
          String[] wires = parts[0].split(" OR ");
          Gate gate = new Gate(wires[0], wires[1], parts[1], (a, b) -> a || b, "OR", i);
          gates.add(gate);
        }
        if (parts[0].contains(" XOR ")) {
          String[] wires = parts[0].split(" XOR ");
          Gate gate = new Gate(wires[0], wires[1], parts[1], (a, b) -> a ^ b, "XOR", i);
          gates.add(gate);
        }
        i += 1;
        continue;
      }

      String[] parts = line.split(": ");
      wireToOutput.put(parts[0], parts[1].equals("1"));

      if (parts[0].startsWith("z") && Integer.parseInt(parts[0].substring(1)) > Integer.parseInt(highestZ.substring(1))) {
        highestZ = parts[0];
      }

    }

  }

  @Override
  public Long part1() {
    Set<Gate> done = new HashSet<>();
    wireToOutputPart1 = new HashMap<>(wireToOutput);
    while (done.size() < gates.size()) {
      gates.forEach(g -> {
        if (wireToOutputPart1.containsKey(g.wireOne) && wireToOutputPart1.containsKey(g.wireTwo)) {
          boolean output = g.getOutput(wireToOutputPart1.get(g.wireOne), wireToOutputPart1.get(g.wireTwo));
          wireToOutputPart1.put(g.outputWire, output);
          done.add(g);
        }
      });
    }
    String binary = getBinaryGivenLetter("z");

    return Long.parseLong(binary, 2);
  }

  private String getBinaryGivenLetter(String letter) {
    SortedMap<String, String> sortedMap = new TreeMap<>(Collections.reverseOrder());
    wireToOutputPart1.forEach((key, value) -> {
      if (key.startsWith(letter)) {
        sortedMap.put(key, value ? "1" : "0");
      }
    });
    return String.join("", sortedMap.values());
  }


  @Override
  // Programmatic solution with the help of reddit
  public String part2() {
    Set<String> wrong = new HashSet<>();
    for (Gate gate : gates) {
      String res = gate.outputWire;

      if (res.startsWith("z") && !res.equals(highestZ) && !gate.label.equals("XOR")) {
        wrong.add(res);
      }

      if (gate.label.equals("XOR") && !res.startsWith("x") && !res.startsWith("y") && !res.startsWith("z") &&
          !gate.wireOne.startsWith("x") && !gate.wireTwo.startsWith("x") &&
          !gate.wireOne.startsWith("y") && !gate.wireTwo.startsWith("y") &&
          !gate.wireOne.startsWith("z") && !gate.wireTwo.startsWith("z")) {
        wrong.add(res);
      }

      if (gate.label.equals("AND") && !gate.wireOne.equals("x00") && !gate.wireTwo.equals("x00")) {
        for (Gate subGate : gates) {
          if ((res.equals(subGate.wireOne) || res.equals(subGate.wireTwo)) && !subGate.label.equals("OR")) {
            wrong.add(res);
          }
        }
      }

      if (gate.label.equals("XOR")) {
        for (Gate subGate : gates) {
          if ((res.equals(subGate.wireOne) || res.equals(subGate.wireTwo)) && subGate.label.equals("OR")) {
            wrong.add(res);
          }
        }
      }
    }

    return String.join(",", wrong.stream().sorted().toList());
  }

  private void swapGates(int indexA, int indexB) {
    log.info("swapping gates {} and {}", indexA, indexB);
    Gate gateA = gates.stream().filter(g -> g.index == indexA).findFirst().get();
    Gate gateB = gates.stream().filter(g -> g.index == indexB).findFirst().get();
    Gate newGateA = gateA.copyWithNewOutputWire(gateB.outputWire());
    Gate newGateB = gateB.copyWithNewOutputWire(gateA.outputWire());
    gates.remove(gateA);
    gates.remove(gateB);
    gates.add(newGateA);
    gates.add(newGateB);
  }

  // Initially worked the answer out using graphViz
  private String manualPart2() {
    for (Gate gate : gates) {
      // Print for visualizing the gates using graphViz
      System.out.println(gate.stringForGraphViz());
    }

    // Initial look at how the z value differs from when x and y are added together
    wireToOutputPart1 = new HashMap<>(wireToOutput);
    part1();
    String binaryX = getBinaryGivenLetter("x");
    String binaryY = getBinaryGivenLetter("y");
    String binaryZ = getBinaryGivenLetter("z");
    log.info("binaryX: {}", binaryX);
    log.info("binaryY: {}", binaryY);
    log.info("x + y =: {}", Long.toBinaryString(Long.parseLong(binaryX, 2) + Long.parseLong(binaryY, 2)));
    log.info("binaryZ: {}", binaryZ);

    // swap gates
    // Gate outputs to swap determined by GraphViz
    swapGates(210, 182);
    swapGates(61, 67);
    swapGates(14, 81);
    swapGates(136, 187);

    // Try it out
    wireToOutputPart1 = new HashMap<>(wireToOutput);
    part1();
    binaryX = getBinaryGivenLetter("x");
    binaryY = getBinaryGivenLetter("y");
    binaryZ = getBinaryGivenLetter("z");
    log.info("binaryX: {}", binaryX);
    log.info("binaryY: {}", binaryY);
    log.info("x + y =: {}", Long.toBinaryString(Long.parseLong(binaryX, 2) + Long.parseLong(binaryY, 2)));
    log.info("binaryZ: {}", binaryZ);

    return String.join(",", gates.stream()
                                 .filter(g -> g.index == 210 || g.index == 182
                                     || g.index == 61 || g.index == 67
                                     || g.index == 14 || g.index == 81
                                     || g.index == 136 || g.index == 187)
                                 .map(g -> g.outputWire).sorted().toList());
  }


  record Gate(String wireOne, String wireTwo, String outputWire, BiFunction<Boolean, Boolean, Boolean> calculateOutput, String label, int index) {

    public Boolean getOutput(Boolean valOne, Boolean valTwo) {
      return calculateOutput.apply(valOne, valTwo);
    }

    public String stringForGraphViz() {
      String gate = format("%s [label=\"%s-%s\"]", index, index, label);
      String sb = wireOne
          + " -> "
          + index
          + "\n"
          + wireTwo
          + " -> "
          + index
          + "\n"
          + index
          + " -> "
          + outputWire
          + "\n"
          + gate;
      return sb;
    }

    public Gate copyWithNewOutputWire(String newOutputWire) {
      return new Gate(wireOne, wireTwo, newOutputWire, calculateOutput, label, index);
    }
  }

}
