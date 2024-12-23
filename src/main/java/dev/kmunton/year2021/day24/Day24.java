package dev.kmunton.year2021.day24;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day24 implements Day<String, String> {

  Map<String, Long> registers = new HashMap<>();
  List<Instruction> instructionList = new ArrayList<>();
  List<Long> inputValues = new ArrayList<>();
  Long[] modelNumber = new Long[14];

  // Hard coded numbers from reverse engineering input
  Long[][] myInputValues = {
      {1L, 13L, 8L},
      {1L, 12L, 13L},
      {1L, 12L, 8L},
      {1L, 10L, 10L},
      {26L, -11L, 12L},
      {26L, -13L, 1L},
      {1L, 15L, 13L},
      {1L, 10L, 5L},
      {26L, -2L, 10L},
      {26L, -6L, 3L},
      {1L, 14L, 2L},
      {26L, 0L, 2L},
      {26L, -15L, 12L},
      {26L, -4L, 7L}
  };

  public Day24(List<String> input) {
    processData(input);
    registers.put("w", 0L);
    registers.put("x", 0L);
    registers.put("y", 0L);
    registers.put("z", 0L);

  }

  @Override
  public void processData(List<String> input) {
    input.forEach(s -> {
      String[] parts = s.split(" ");
      if (parts.length == 3) {
        instructionList.add(new Instruction(parts[0], parts[1], parts[2]));
      } else {
        instructionList.add(new Instruction(parts[0], parts[1], ""));
      }
    });

  }

  private long returnB(String b) {
    String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    if (Arrays.stream(numbers).toList().contains(b.substring(0, 1)) || b.charAt(0) == '-') {
      return Long.parseLong(b);
    }
    return registers.get(b);
  }

  private void readWrite(String a) {
    Long bNum = inputValues.remove(0);
    registers.put(a, bNum);
  }

  private void add(String a, String b) {
    Long bNum = returnB(b);
    Long aNum = registers.get(a);
    registers.put(a, bNum + aNum);
  }

  private void mul(String a, String b) {
    Long bNum = returnB(b);
    Long aNum = registers.get(a);
    registers.put(a, bNum * aNum);
  }

  private void div(String a, String b) throws Exception {
    Long bNum = returnB(b);
    Long aNum = registers.get(a);
    if (bNum == 0) {
      throw new Exception("dividing by 0");
    }
    registers.put(a, aNum / bNum);
  }

  private void mod(String a, String b) throws Exception {
    Long bNum = returnB(b);
    Long aNum = registers.get(a);
    if (aNum < 0 || bNum <= 0) {
      throw new Exception("modula by 0 or less than 0");
    }
    registers.put(a, aNum % bNum);
  }

  private void eql(String a, String b) {
    Long bNum = returnB(b);
    Long aNum = registers.get(a);
    registers.put(a, bNum == aNum ? 1L : 0L);
  }

  private void readInstruction(Instruction instruction) throws Exception {
    try {
      switch (instruction.getName()) {
        case "inp" -> readWrite(instruction.getA());
        case "add" -> add(instruction.getA(), instruction.getB());
        case "mul" -> mul(instruction.getA(), instruction.getB());
        case "div" -> div(instruction.getA(), instruction.getB());
        case "mod" -> mod(instruction.getA(), instruction.getB());
        case "eql" -> eql(instruction.getA(), instruction.getB());
      }
    } catch (Exception e) {
      throw e;
    }

  }

  private void reset() {
    registers.put("w", 0L);
    registers.put("x", 0L);
    registers.put("y", 0L);
    registers.put("z", 0L);
  }

  private long func(long w, long z, long a, long b, long c) {
    long x = (z % 26L) + b;
    z /= a;

    if (x != w) {
      z *= 26L;
      z += w + c;

    }
    return z;
  }

  private void findHigher(Long n, int i1, int i2) {
    Long v1;
    Long v2;
    if (n < 0) {
      v1 = 9L;
      v2 = v1 - Math.abs(n);

    } else if (n > 0) {
      v2 = 9L;
      v1 = v2 - n;

    } else {
      v1 = 9L;
      v2 = 9L;
    }

    modelNumber[i1] = v1;
    modelNumber[i2] = v2;
  }

  private void findLower(Long n, int i1, int i2) {
    Long v1;
    Long v2;
    if (n < 0) {
      v1 = 1L;
      v2 = v1 - Math.abs(n);
      while (v2 < 1L) {
        v1 += 1L;
        v2 = v1 - Math.abs(n);
      }

    } else if (n > 0) {
      v2 = 1L;
      v1 = v2 - n;
      while (v1 < 1L) {
        v2 += 1;
        v1 = v2 - n;
      }

    } else {
      v1 = 1L;
      v2 = 1L;
    }

    modelNumber[i1] = v1;
    modelNumber[i2] = v2;
  }

  private Long bruteForce() {
    long n;
    int index = 0;
    for (n = 99999999999999L; n >= 11111111111111L; n--) {
      String[] nString = String.valueOf(n).split("(?<=\\G.)");
      if (Arrays.asList(nString).contains("0")) {
        continue;
      }

      long z = 0;
      index = 0;
      for (String w : nString) {
        z = func(Long.parseLong(w), z, myInputValues[index][0], myInputValues[index][1], myInputValues[index][2]);
        index += 1;
      }

      if (z == 0) {
        System.out.println(n);
        break;
      }
    }
    return n;
  }


  @Override
  public String part1() {
    // From input, get the three changing values and store them in a list of {div value, value added to x, value added to y}
    // if the first value is 1, push 3rd value into stack
    // otherwise pop last thing off stack and subtract current 2nd value from it to find difference between those indices in model number
    Stack<Map<Long, Integer>> pushStack = new Stack<>();
    int index = 0;
    for (Long[] values : myInputValues) {
      if (values[0] == 1L) {
        Map<Long, Integer> pair = new HashMap<>();
        pair.put(values[2], index);
        pushStack.push(pair);
      } else {
        Map<Long, Integer> pair = pushStack.pop();
        Long n = new ArrayList<>(pair.keySet()).get(0) - Math.abs(values[1]);
        findHigher(n, new ArrayList<>(pair.values()).get(0), index);
      }
      index += 1;
    }

    return Arrays.stream(modelNumber).map(String::valueOf).collect(Collectors.joining());
  }

  @Override
  public String part2() {
    Stack<Map<Long, Integer>> pushStack = new Stack<>();
    int index = 0;
    for (Long[] values : myInputValues) {
      if (values[0] == 1L) {
        Map<Long, Integer> pair = new HashMap<>();
        pair.put(values[2], index);
        pushStack.push(pair);
      } else {
        Map<Long, Integer> pair = pushStack.pop();
        Long n = new ArrayList<>(pair.keySet()).get(0) - Math.abs(values[1]);
        findLower(n, new ArrayList<>(pair.values()).get(0), index);
      }
      index += 1;
    }

    return Arrays.stream(modelNumber).map(String::valueOf).collect(Collectors.joining());
  }
}
