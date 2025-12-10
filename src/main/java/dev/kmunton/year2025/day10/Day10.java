package dev.kmunton.year2025.day10;

import dev.kmunton.utils.days.Day;
import lombok.extern.slf4j.Slf4j;
import org.sosy_lab.common.ShutdownManager;
import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.common.log.BasicLogManager;
import org.sosy_lab.common.log.LogManager;
import org.sosy_lab.java_smt.SolverContextFactory;
import org.sosy_lab.java_smt.api.*;

import java.util.*;

import static dev.kmunton.utils.algorithms.GraphSearchUtils.*;

@Slf4j
public class Day10 implements Day<Long, Long> {

    private final List<Machine> machines = new ArrayList<>();

    public Day10(List<String> input) {
        processData(input);
    }

    @Override
    public void processData(List<String> input) {
        input.forEach(line -> {
            String[] split = line.split(" ");
            boolean[] lights = new boolean[0];
            List<List<Integer>> buttons = new ArrayList<>();
            List<Integer> joltageList = new ArrayList<>();
            for (String s : split) {
                char[] chars = s.toCharArray();
                if (chars[0] == '[') {
                    int light = 0;
                    lights = new boolean[chars.length - 2];
                    for (char c : chars) {
                        if (c == '[' || c == ']') {
                            continue;
                        }
                        if (c == '#') {
                            lights[light] = true;
                        }
                        light++;
                    }
                    continue;
                }
                if (chars[0] == '(') {
                    String[] numbers = s.substring(1, chars.length - 1).split(",");
                    List<Integer> button = new ArrayList<>();
                    for (String number : numbers) {
                        button.add(Integer.parseInt(number));

                    }
                    buttons.add(button);
                    continue;
                }
                if (chars[0] == '{') {
                    String[] numbers = s.substring(1, chars.length - 1).split(",");
                    for (String number : numbers) {
                        joltageList.add(Integer.parseInt(number));
                    }
                }
            }
            int[] joltageGoal = new int[joltageList.size()];
            for (int i = 0; i < joltageList.size(); i++) {
                joltageGoal[i] = joltageList.get(i);
            }
            machines.add(new Machine(new boolean[lights.length], lights, buttons, 0, joltageGoal, new int[joltageList.size()], false));
        });
    }

    @Override
    public Long part1() {
        long shortestPathSum = 0L;
        for (Machine machine : machines) {
            List<Machine> path = aStarSearch(
                    new Machine(machine.currentLights(), machine.goalLights(), machine.buttons(), 0, null, null, true),
                    m -> Arrays.equals(m.currentLights(), m.goalLights()),
                    m -> {
                        Map<Machine, Integer> neighbours = new HashMap<>();
                        for (List<Integer> button : m.buttons()) {
                            boolean[] newLights = Arrays.copyOf(m.currentLights(), m.currentLights().length);
                            for (Integer buttonId : button) {
                                newLights[buttonId] = !newLights[buttonId];
                            }
                            Machine newMachineState = new Machine(newLights, m.goalLights(), m.buttons(), m.buttonsPressed() + 1, m.joltageGoal(), m.currentJoltage(), m.lightMachine());
                            neighbours.put(newMachineState, 1);
                        }
                        return neighbours;
                    },
                    m -> 0
            );
            shortestPathSum += path.getLast().buttonsPressed();
        }
        return shortestPathSum;
    }

    @Override
    public Long part2() {
        long shortestPathSum = 0L;
        for (Machine machine : machines) {
            Configuration config = Configuration.defaultConfiguration();
            LogManager logger;
            try {
                logger = BasicLogManager.create(config);
            } catch (InvalidConfigurationException e) {
                log.warn(e.getMessage());
                return -1L;
            }

            ShutdownManager shutdownManager = ShutdownManager.create();

            try (SolverContext ctx = SolverContextFactory.createSolverContext(
                    config, logger, shutdownManager.getNotifier(), SolverContextFactory.Solvers.PRINCESS)) {
                shortestPathSum += minPressesPrincess(ctx, machine.buttons(), machine.joltageGoal(), Arrays.toString(machine.joltageGoal()));
            } catch (Exception ex) {
                log.warn(ex.getMessage());
                return -1L;
            }
            // A* works but too slow
//            machine.buttons().sort(Comparator.comparingInt(List::size));
//            log.info("{} {}", machine.joltageGoal(), machine.buttons().reversed());
//            List<Machine> path = aStarSearchWithCustomGCostTracker(
//                    new Machine(null, null, machine.buttons().reversed(), 0, machine.joltageGoal(), machine.currentJoltage(), false),
//                    m -> Arrays.equals(m.currentJoltage(), m.joltageGoal()),
//                    m -> {
//                        Map<Machine, Integer> neighbours = new HashMap<>();
//                        for (List<Integer> button : m.buttons()) {
//                            int[] newJoltage = Arrays.copyOf(m.currentJoltage(), m.currentJoltage().length);
//                            boolean invalidButton = false;
//                            for (Integer buttonId : button) {
//                                newJoltage[buttonId] = newJoltage[buttonId] + 1;
//                                if (newJoltage[buttonId] > m.joltageGoal()[buttonId]) {
//                                    invalidButton = true;
//                                    break;
//                                }
//                            }
//                            if (invalidButton) {
//                                continue;
//                            }
//                            Machine newMachineState = new Machine(m.currentLights(), m.goalLights(), m.buttons(), m.buttonsPressed() + 1, m.joltageGoal(), newJoltage, m.lightMachine());
//                            neighbours.put(newMachineState, 1);
//                        }
//                        return neighbours;
//                    },
//                    m ->
//                    {
//                        int sum = 0;
//                        for (int i = 0; i < m.currentJoltage().length; i++) {
//                            sum += (m.joltageGoal()[i] - m.currentJoltage()[i]);
//                        }
//                        return sum;
//                    },
//                    Machine::currentJoltage
//            );
//            shortestPathSum += path.getLast().buttonsPressed();
        }
        return shortestPathSum;
    }

    public static long minPressesPrincess(
            SolverContext ctx,
            List<List<Integer>> buttons,
            int[] goal,
            String machineId) throws Exception {

        FormulaManager fm = ctx.getFormulaManager();
        IntegerFormulaManager ifm = fm.getIntegerFormulaManager();

        final int counters = goal.length;
        final int numButtons = buttons.size();

        // Build coefficient matrix A[r][b]
        int[][] A = new int[counters][numButtons];
        for (int b = 0; b < numButtons; b++) {
            for (int idx : buttons.get(b)) {
                if (idx < 0 || idx >= counters) {
                    throw new IllegalArgumentException("Button index out of range: " + idx);
                }
                A[idx][b]++;
            }
        }

        // Create integer variables with descriptive names
        List<NumeralFormula.IntegerFormula> xVars = new ArrayList<>(numButtons);
        for (int b = 0; b < numButtons; b++) {
            StringBuilder sb = new StringBuilder();
            sb.append("press_");
            if (machineId != null && !machineId.isEmpty()) {
                sb.append(machineId).append("_");
            }
            sb.append("button").append(b).append("_increments");
            if (buttons.get(b).isEmpty()) sb.append("_none");
            else for (int idx : buttons.get(b)) sb.append("_").append(idx);

            xVars.add(ifm.makeVariable(sb.toString()));
        }

        // Pre-build the counter equalities A*x == goal
        List<BooleanFormula> counterEqualities = new ArrayList<>();
        for (int r = 0; r < counters; r++) {
            List<NumeralFormula.IntegerFormula> terms = new ArrayList<>();
            for (int b = 0; b < numButtons; b++) {
                int coef = A[r][b];
                if (coef != 0) {
                    terms.add(ifm.multiply(ifm.makeNumber(coef), xVars.get(b)));
                }
            }
            NumeralFormula.IntegerFormula sum = terms.isEmpty() ? ifm.makeNumber(0) : ifm.sum(terms);
            counterEqualities.add(ifm.equal(sum, ifm.makeNumber(goal[r])));
        }

        // Fast upper bound = sum(goal)
        int sumGoal = 0;
        for (int v : goal) sumGoal += v;

        // Check feasibility at all (ignore bound)
        if (!isFeasibleWithBound(ctx, xVars, counterEqualities, ifm, Integer.MAX_VALUE)) {
            throw new IllegalStateException("No feasible solution exists for this machine.");
        }

        // Binary search for minimal total presses
        int left = 0, right = sumGoal;
        while (left < right) {
            int mid = left + (right - left) / 2;
            boolean feasible = isFeasibleWithBound(ctx, xVars, counterEqualities, ifm, mid);
            if (feasible) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        // left == minimal feasible sum(x)
        return left;
    }

    /**
     * Check if there is a solution with sum(x) <= bound.
     */
    private static boolean isFeasibleWithBound(
            SolverContext ctx,
            List<NumeralFormula.IntegerFormula> xVars,
            List<BooleanFormula> counterEqualities,
            IntegerFormulaManager ifm,
            int bound) throws Exception {

        try (ProverEnvironment prover = ctx.newProverEnvironment()) {

            // Add counter equalities
            for (BooleanFormula c : counterEqualities) prover.addConstraint(c);

            // x >= 0
            for (NumeralFormula.IntegerFormula xv : xVars) {
                prover.addConstraint(ifm.greaterOrEquals(xv, ifm.makeNumber(0)));
            }

            // sum(x) <= bound
            NumeralFormula.IntegerFormula sum = ifm.sum(xVars);
            prover.addConstraint(ifm.lessOrEquals(sum, ifm.makeNumber(bound)));

            // SAT if not UNSAT
            return !prover.isUnsat();
        }
    }



}
