package dev.kmunton.year2025.day10;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record Machine(boolean[] currentLights,
                      boolean[] goalLights,
                      List<List<Integer>> buttons,
                      Integer buttonsPressed,
                      int[] joltageGoal,
                      int[] currentJoltage,
                      boolean lightMachine) {

    @Override
    public String toString() {
        if (lightMachine) {
            return Arrays.toString(currentLights) + ", buttonsPressed=" + buttonsPressed;
        }

        return Arrays.toString(currentJoltage) + ", buttonsPressed=" + buttonsPressed;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != Machine.class) return false;
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(currentLights), buttonsPressed, Arrays.hashCode(currentJoltage));
    }


}
