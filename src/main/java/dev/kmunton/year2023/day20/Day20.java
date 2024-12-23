package dev.kmunton.year2023.day20;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.geometry.MathUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day20 implements Day<Long, Long> {

  private static final String LOW = "LOW";
  private static final String HIGH = "HIGH";
  private static final String OFF = "OFF";
  private static final String ON = "ON";
  private static final String BROADCASTER = "broadcaster";
  private static final String INVERTER = "inverter";
  private static final String SWITCH = "switch";

  Map<String, Module> defaultModules = new HashMap<>();

  private long totalLow = 0;
  private long totalHigh = 0;

  private record ModuleAndNextPulse(String module, String nextPulse) {

  }

  private final Map<String, Long> part2ModulesToHigh = new HashMap<>();

  public Day20(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    for (String line : input) {
      String[] parts = line.split(" -> ");
      var destinations = Arrays.stream(parts[1].split(", ")).toList();
      if (parts[0].charAt(0) == '%') {
        var m = new Module(parts[0].substring(1).trim(), SWITCH, OFF, destinations, null);
        defaultModules.put(m.getName(), m);
      } else if (parts[0].charAt(0) == '&') {
        var m = new Module(parts[0].substring(1).trim(), INVERTER, null, destinations, new HashMap<>());
        defaultModules.put(m.getName(), m);
      } else {
        var m = new Module(parts[0].trim(), BROADCASTER, null, destinations, null);
        defaultModules.put(m.getName(), m);
      }
    }
    for (var module : defaultModules.values()) {
      if (module.getType().equals(INVERTER)) {
        for (var module2 : defaultModules.values()) {
          if (module2.getName().equals(module.getName())) {
            continue;
          }
          if (module2.getDestinations().contains(module.getName())) {
            module.getMemory().put(module2.getName(), LOW);
          }
        }
      }
    }
  }

  public Long part1() {
    var buttonPushes = 1000;
    var currentButtonPush = 0;
    var modules = deepCopyModules(defaultModules);
    while (currentButtonPush < buttonPushes) {
      invokeButtonPush(modules, currentButtonPush);
      currentButtonPush++;
    }
    return totalLow * totalHigh;

  }

  public Long part2() {
    // rx parent is an inverter that takes 4 other inverters as input
    for (var module : defaultModules.values()) {
      if (module.getDestinations().contains("rx")) {
        for (var key : module.getMemory().keySet()) {
          part2ModulesToHigh.put(key, 0L);
        }
      }
    }
    var modules = deepCopyModules(defaultModules);
    var buttonPushes = 0L;
    while (part2ModulesToHigh.values().stream().anyMatch(l -> l == 0L)) {
      buttonPushes++;
      invokeButtonPush(modules, buttonPushes);
    }
    return part2ModulesToHigh.values().stream().reduce(1L, MathUtils::lcm);
  }

  private void invokeButtonPush(HashMap<String, Module> modules, long buttonPushes) {
    totalLow++;
    var currentModules = new ArrayList<ModuleAndNextPulse>();
    currentModules.add(new ModuleAndNextPulse(BROADCASTER, LOW));
    while (!currentModules.isEmpty()) {
      var moduleAndNextPulse = currentModules.remove(0);
      var module = modules.get(moduleAndNextPulse.module());
      if (part2ModulesToHigh.containsKey(module.getName())
          && moduleAndNextPulse.nextPulse().equals(HIGH)) {
        part2ModulesToHigh.put(module.getName(), buttonPushes);
      }
      for (var destination : module.getDestinations()) {
        var currentPulse = moduleAndNextPulse.nextPulse();
        if (Objects.equals(currentPulse, HIGH)) {
          totalHigh++;
        } else {
          totalLow++;
        }

        var m = modules.get(destination);
        if (m == null) {
          continue;
        }
        var type = m.getType();
        if (type.equals(INVERTER)) {
          m.getMemory().put(module.getName(), currentPulse);
          modules.put(destination, m);
          var nextPulse = modules.get(destination).getMemory().values().stream().allMatch(HIGH::equals) ? LOW : HIGH;
          currentModules.add(new ModuleAndNextPulse(destination, nextPulse));
        } else if (type.equals(SWITCH)) {
          if (Objects.equals(currentPulse, LOW)) {
            String nextPulse;
            if (m.getState().equals(OFF)) {
              m.setState(ON);
              nextPulse = HIGH;
            } else {
              m.setState(OFF);
              nextPulse = LOW;
            }
            modules.put(destination, m);
            currentModules.add(new ModuleAndNextPulse(destination, nextPulse));
          }

        } else {
          currentModules.add(new ModuleAndNextPulse(destination, moduleAndNextPulse.nextPulse()));
        }

      }
    }
  }

  private HashMap<String, Module> deepCopyModules(Map<String, Module> modules) {
    var newModules = new HashMap<String, Module>();
    for (var module : modules.values()) {
      var newModule = new Module(module.getName(), module.getType(), module.getState(), module.getDestinations(), module.getMemory());
      newModules.put(newModule.getName(), newModule);
    }
    return newModules;
  }

}
