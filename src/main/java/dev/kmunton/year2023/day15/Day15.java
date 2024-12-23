package dev.kmunton.year2023.day15;

import dev.kmunton.utils.days.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day15 implements Day<Long, Long> {

  private final List<String> steps = new ArrayList<>();

  public Day15(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    this.steps.addAll(Arrays.stream(input.get(0).split(",")).toList());
  }

  public Long part1() {
    return steps.stream().mapToLong(this::getHASH).sum();
  }

  public Long part2() {
    var boxes = new HashMap<Long, List<Lens>>();

    for (var step : steps) {
      if (step.contains("=")) {
        var label = step.split("=")[0];
        var focalLength = Long.parseLong(step.split("=")[1]);
        var hash = getHASH(label);
        if (boxes.containsKey(hash)) {
          var added = false;
          for (var i = 0; i < boxes.get(hash).size(); i++) {
            var lens = boxes.get(hash).get(i);
            if (lens.label().equals(label)) {
              boxes.get(hash).get(i).setFocalLength(focalLength);
              added = true;
              break;
            }
          }
          if (!added) {
            boxes.get(hash).add(new Lens(label, focalLength));
          }
        } else {
          boxes.put(hash, new ArrayList<>(List.of(new Lens(label, focalLength))));
        }
      } else {
        var label = step.split("-")[0];
        var hash = getHASH(label);
        if (boxes.containsKey(hash)) {
          boxes.get(hash).removeIf(lens -> lens.label().equals(label));
        }
      }
    }

    var sum = 0L;
    for (var entry : boxes.entrySet()) {
      var box = entry.getKey();
      var lenses = entry.getValue();
      for (var slot = 0; slot < lenses.size(); slot++) {
        var lens = lenses.get(slot);
        sum += (1 + box) * lens.focalLength() * (slot + 1);
      }
    }
    return sum;
  }

  private long getHASH(String input) {
    var value = 0L;
    for (char c : input.toCharArray()) {
      value += c;
      value *= 17;
      value = value % 256;
    }
    return value;
  }
}
