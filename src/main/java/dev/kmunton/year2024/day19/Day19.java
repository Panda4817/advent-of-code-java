package dev.kmunton.year2024.day19;


import dev.kmunton.utils.days.Day;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day19 implements Day<Long, Long> {


  private final Set<String> patterns = new HashSet<>();
  private final Set<String> designs = new HashSet<>();

  public Day19(List<String> input) {
    processData(input);
  }

  @Override
  public void processData(List<String> input) {
    input.forEach(line -> {
      if (line.isEmpty()) {
        return;
      }
      String[] parts = line.split(", ");
      if (parts.length > 1) {
        patterns.addAll(List.of(parts));
        return;
      }
      designs.addAll(Arrays.asList(parts));
    });
  }

  @Override
  public Long part1() {
    return designs.stream()
                  .filter(d -> getDesignIndexToMatchingPatterns(d).getOrDefault(d.length(), 0L) != 0)
                  .count();
  }

  private Map<Integer, Long> getDesignIndexToMatchingPatterns(String design) {
    Map<Integer, Long> designIndexToMatchingPatterns = new HashMap<>();
    designIndexToMatchingPatterns.put(0, 1L);
    for (int i = 0; i < design.length(); i++) {
      int current = i;
      if (designIndexToMatchingPatterns.containsKey(current)) {
        patterns.forEach(p -> {
          if (current + p.length() <= design.length() && design.startsWith(p, current)) {
            if (!designIndexToMatchingPatterns.containsKey(current + p.length())) {
              designIndexToMatchingPatterns.put(current + p.length(), 0L);
            }
            designIndexToMatchingPatterns.put(current + p.length(),
                designIndexToMatchingPatterns.get(current + p.length()) + designIndexToMatchingPatterns.getOrDefault(current, 1L));
          }
        });
      }
    }
    return designIndexToMatchingPatterns;
  }

  @Override
  public Long part2() {
    long sum = 0;
    for (String design : designs) {
      Map<Integer, Long> designIndexToMatchingPatterns = getDesignIndexToMatchingPatterns(design);
      long validPatterns = designIndexToMatchingPatterns.getOrDefault(design.length(), 0L);
      if (validPatterns == 0L) {
        continue;
      }

      sum += validPatterns;
    }
    return sum;

  }
}
