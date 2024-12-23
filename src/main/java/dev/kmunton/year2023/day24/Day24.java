package dev.kmunton.year2023.day24;

import static dev.kmunton.utils.geometry.MathUtils.calculateIntersectionPoint;

import dev.kmunton.utils.days.Day;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sosy_lab.common.ShutdownManager;
import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.log.LogManager;
import org.sosy_lab.java_smt.SolverContextFactory;
import org.sosy_lab.java_smt.SolverContextFactory.Solvers;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.BooleanFormulaManager;
import org.sosy_lab.java_smt.api.FormulaManager;
import org.sosy_lab.java_smt.api.IntegerFormulaManager;
import org.sosy_lab.java_smt.api.Model;
import org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula;
import org.sosy_lab.java_smt.api.ProverEnvironment;
import org.sosy_lab.java_smt.api.SolverContext;
import org.sosy_lab.java_smt.api.SolverContext.ProverOptions;
import org.sosy_lab.java_smt.api.SolverException;

public class Day24 implements Day<Long, Long> {

  private final Map<Integer, Hailstorm> hailstorms = new HashMap<>();

  public Day24(List<String> input) {
    processData(input);
  }

  public void processData(List<String> input) {
    var id = 1;
    for (var line : input) {
      var parts = line.split(" @ ");
      var p = Arrays.stream(parts[0].split(", "))
                    .map(String::trim)
                    .map(Long::parseLong).toArray(Long[]::new);
      var v = Arrays.stream(parts[1].split(", "))
                    .map(String::trim)
                    .map(Double::parseDouble).toArray(Double[]::new);
      var hailstorm = new Hailstorm(p[0], p[1], p[2], v[0], v[1], v[2]);
      hailstorms.put(id, hailstorm);
      id++;
    }
  }

  public Long part1() {
    return findIntersectionInTestArea(200000000000000L, 400000000000000L);
  }

  public long findIntersectionInTestArea(long min, long max) {
    var answer = 0L;
    for (var id = 1; id <= hailstorms.size() - 1; id++) {
      for (var id2 = id + 1; id2 <= hailstorms.size(); id2++) {
        var h1 = hailstorms.get(id);
        var h2 = hailstorms.get(id2);
        var intersection = calculateIntersectionPoint(
            h1.getM(),
            h1.getB(),
            h2.getM(),
            h2.getB()
        );
        if (intersection.isEmpty()) {
          continue;
        }
        var point = intersection.get();
        var t1 = (point.y() - h1.getPy()) / h1.getVy();
        var t2 = (point.y() - h2.getPy()) / h2.getVy();
        if (t1 < 0 || t2 < 0) {
          continue;
        }
        if (point.x() >= min && point.x() <= max && point.y() >= min && point.y() <= max) {
          answer++;
        }
      }
    }
    return answer;
  }

  // -1 6 -8
  public Long part2() {
    try (SolverContext context = SolverContextFactory.createSolverContext(
        Configuration.defaultConfiguration(),
        LogManager.createNullLogManager(),
        ShutdownManager.create().getNotifier(), Solvers.PRINCESS)) {

      FormulaManager fmgr = context.getFormulaManager();
      BooleanFormulaManager bmgr = fmgr.getBooleanFormulaManager();
      IntegerFormulaManager imgr = fmgr.getIntegerFormulaManager();

      IntegerFormula t0 = imgr.makeVariable("t0");
      IntegerFormula t1 = imgr.makeVariable("t1");
      IntegerFormula t2 = imgr.makeVariable("t2");
      IntegerFormula x = imgr.makeVariable("x");
      IntegerFormula y = imgr.makeVariable("y");
      IntegerFormula z = imgr.makeVariable("z");
      IntegerFormula vx = imgr.makeVariable("vx");
      IntegerFormula vy = imgr.makeVariable("vy");
      IntegerFormula vz = imgr.makeVariable("vz");
      BooleanFormula constraint = bmgr.and(
          imgr.greaterOrEquals(t0, imgr.makeNumber(0)),
          imgr.greaterOrEquals(t1, imgr.makeNumber(0)),
          imgr.greaterOrEquals(t2, imgr.makeNumber(0)),
          imgr.equal(
              imgr.add(imgr.makeNumber(hailstorms.get(1).getPx()), imgr.multiply(imgr.makeNumber(hailstorms.get(1).getVx()), t0)),
              imgr.add(x, imgr.multiply(vx, t0))
          ),
          imgr.equal(
              imgr.add(imgr.makeNumber(hailstorms.get(1).getPy()), imgr.multiply(imgr.makeNumber(hailstorms.get(1).getVy()), t0)),
              imgr.add(y, imgr.multiply(vy, t0))
          ),
          imgr.equal(
              imgr.add(imgr.makeNumber(hailstorms.get(1).getPz()), imgr.multiply(imgr.makeNumber(hailstorms.get(1).getVz()), t0)),
              imgr.add(z, imgr.multiply(vz, t0))
          ),
          imgr.equal(
              imgr.add(imgr.makeNumber(hailstorms.get(2).getPx()), imgr.multiply(imgr.makeNumber(hailstorms.get(2).getVx()), t1)),
              imgr.add(x, imgr.multiply(vx, t1))
          ),
          imgr.equal(
              imgr.add(imgr.makeNumber(hailstorms.get(2).getPy()), imgr.multiply(imgr.makeNumber(hailstorms.get(2).getVy()), t1)),
              imgr.add(y, imgr.multiply(vy, t1))
          ),
          imgr.equal(
              imgr.add(imgr.makeNumber(hailstorms.get(2).getPz()), imgr.multiply(imgr.makeNumber(hailstorms.get(2).getVz()), t1)),
              imgr.add(z, imgr.multiply(vz, t1))
          ),
          imgr.equal(
              imgr.add(imgr.makeNumber(hailstorms.get(3).getPx()), imgr.multiply(imgr.makeNumber(hailstorms.get(3).getVx()), t2)),
              imgr.add(x, imgr.multiply(vx, t2))
          ),
          imgr.equal(
              imgr.add(imgr.makeNumber(hailstorms.get(3).getPy()), imgr.multiply(imgr.makeNumber(hailstorms.get(3).getVy()), t2)),
              imgr.add(y, imgr.multiply(vy, t2))
          ),
          imgr.equal(
              imgr.add(imgr.makeNumber(hailstorms.get(3).getPz()), imgr.multiply(imgr.makeNumber(hailstorms.get(3).getVz()), t2)),
              imgr.add(z, imgr.multiply(vz, t2))
          )
      );

      try (ProverEnvironment prover = context.newProverEnvironment(ProverOptions.GENERATE_MODELS)) {
        prover.addConstraint(constraint);
        boolean isUnsat = prover.isUnsat();
        if (!isUnsat) {
          Model model = prover.getModel();
          System.out.println(model);
          return model.asList().stream()
                      .filter(s -> s.getKey().toString().equals("x") || s.getKey().toString().equals("y") || s.getKey().toString().equals("z"))
                      .map(s -> (BigInteger) s.getValue()).mapToLong(BigInteger::longValue).sum();
        }
        throw new SolverException("isUnsat is true");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return 0L;
  }

}
