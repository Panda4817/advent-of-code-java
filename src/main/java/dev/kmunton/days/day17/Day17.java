package dev.kmunton.days.day17;


import dev.kmunton.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day17 implements Day<String, Long> {

  private long a;
  private long b;
  private long c;
  private final Map<Integer, Supplier<Long>> combo = new HashMap<>();
  private final Map<Integer, Opcode> opcodes = new HashMap<>();
  private final List<Integer> program = new ArrayList<>();


  public Day17(List<String> input) {
    combo.put(0, () -> 0L);
    combo.put(1, () -> 1L);
    combo.put(2, () -> 2L);
    combo.put(3, () -> 3L);
    combo.put(4, () -> a);
    combo.put(5, () -> b);
    combo.put(6, () -> c);

    opcodes.put(0, new Opcode(
        0,
        (i, j) -> {
          a = (long) (i / (Math.pow(2, j)));
          return a;
        },
        () -> a,
        i -> combo.get(i).get()
    ));

    opcodes.put(1, new Opcode(
        1,
        (i, j) -> {
          b = i ^ j;
          return b;
        },
        () -> b,
        Long::valueOf
    ));

    opcodes.put(2, new Opcode(
        2,
        (i, j) -> {
          b = j % i;
          return b;
        },
        () -> 8L,
        i -> combo.get(i).get()
    ));
    opcodes.put(4, new Opcode(
        4,
        (i, j) -> {
          b = i ^ j;
          return b;
        },
        () -> b,
        i -> c
    ));

    opcodes.put(5, new Opcode(
        5,
        (i, j) -> j % i,
        () -> 8L,
        i -> combo.get(i).get()
    ));

    opcodes.put(6, new Opcode(
        6,
        (i, j) -> {
          b = (long) (i / (Math.pow(2, j)));
          return b;
        },
        () -> a,
        i -> combo.get(i).get()
    ));

    opcodes.put(7, new Opcode(
        7,
        (i, j) -> {
          c = (long) (i / (Math.pow(2, j)));
          return c;
        },
        () -> a,
        i -> combo.get(i).get()
    ));
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> {
      if (line.startsWith("Register")) {
        String[] register = line.split(": ");
        switch (register[0].split(" ")[1]) {
          case "A" -> a = Long.parseLong(register[1]);
          case "B" -> b = Long.parseLong(register[1]);
          case "C" -> c = Long.parseLong(register[1]);
          default -> log.error("Unknown register type: {}", register[0]);
        }
      } else if (line.startsWith("Program")) {
        Arrays.stream(line.split(": ")[1].split(",")).map(Integer::parseInt).forEach(program::add);
      }

    });
  }

  @Override
  public String part1() {
    return doProgram(a, b, c);
  }


  @Override
  public Long part2() {
    // Specific to my input program
    // [2, 4, 1, 3, 7, 5, 0, 3, 4, 1, 1, 5, 5, 5, 3, 0]
    // Graph Viz to visualise it - https://is.gd/U9yMtx
    // digraph G {
    //  a -> b1  [ label="% 8" ]
    //  b1 -> b2 [ label="^ 3"]
    //  b2 -> c [label= "a1 / pow(2, b)"]
    //  a -> nextA [label="a1 / pow(2,3)"]
    //  c -> b3 [label="b2 ^ c1"]
    //  b3 -> b4 [label="^ 5"]
    //  b4 -> out [label="% 8"]
    //};
    LongUnaryOperator getB1 = aVal -> aVal % 8;
    LongUnaryOperator getB2 = aVal -> getB1.applyAsLong(aVal) ^ 3;
    LongUnaryOperator getC = aVal -> aVal / ((long) Math.pow(2, getB2.applyAsLong(aVal)));
    LongUnaryOperator getB3 = aVal -> getB2.applyAsLong(aVal) ^ getC.applyAsLong(aVal);
    LongUnaryOperator getB4 = aVal -> getB3.applyAsLong(aVal) ^ 5;
    LongUnaryOperator getBOut = aVal -> getB4.applyAsLong(aVal) % 8;
    return getAWhichOutputsSameProgram(getBOut, new Node(0, 15)).aVal();

  }

  private Node getAWhichOutputsSameProgram(LongUnaryOperator getB, Node currentNode) {
    if (currentNode.foundSolution()) {
      return new Node(currentNode.aVal / 8, currentNode.currentIndex());
    }
    long val = program.get(currentNode.currentIndex());
    for (long i = currentNode.aVal(); i < currentNode.aVal() + 8; i++) {
      if (i == 0) {
        continue;
      }
      if (getB.applyAsLong(i) == val) {
        Node n = getAWhichOutputsSameProgram(getB, new Node(i * 8, currentNode.currentIndex() - 1));
        if (n.foundSolution()) {
          return n;
        }
      }
    }
    return currentNode;
  }

  record Node(long aVal, int currentIndex) {

    public boolean foundSolution() {
      return currentIndex == -1;
    }
  }

  private String doProgram(long startA, long startB, long startC) {
    a = startA;
    b = startB;
    c = startC;
    List<Long> outputs = new ArrayList<>();
    int pointer = 0;
    while (pointer < program.size()) {
      int opcode = program.get(pointer);
      int operand = program.get(pointer + 1);

      // Check jump opcode
      if (opcode == 3 && a != 0) {
        pointer = operand;
        continue;
      } else if (opcode == 3) {
        pointer += 2;
        continue;
      }

      // All other opcodes
      Opcode o = opcodes.get(opcode);
      long out = o.doInstruction(operand);

      // Capture outputs
      if (opcode == 5) {
        outputs.add(out);
      }

      pointer += 2;
    }
    return outputs.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  record Opcode(int opcode, BiFunction<Long, Long, Long> instruction, Supplier<Long> first, Function<Integer, Long> second) {

    public long doInstruction(int operand) {
      return instruction.apply(first.get(), second.apply(operand));
    }

  }

}
