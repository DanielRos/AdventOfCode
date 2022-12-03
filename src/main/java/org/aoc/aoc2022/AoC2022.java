package org.aoc.aoc2022;

import static org.aoc.common.util.Util.getNumbersBetween;

import com.google.common.flogger.FluentLogger;
import java.util.List;
import java.util.concurrent.Callable;
import org.aoc.aoc2022.days.Day1;
import org.aoc.aoc2022.days.Day2;
import org.aoc.aoc2022.days.Day3;
import org.aoc.common.AoCDay;

public class AoC2022 {
  private AoC2022() {}

  static List<AoCDay> allDays = List.of(new Day1(), new Day2(), new Day3());

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static void runSpecific() throws Exception {
    //warmUp(1, 2);
    //executeDay(1);
    //warmUp(2, 2);
    //executeDay(2);
    warmUp(3, 2);
    executeDay(3);
  }

  public static void run() {
    for (Integer integer : getNumbersBetween(1, allDays.size())) {
    try {
        executeDay(integer);
    } catch (Exception e) {
      logger.atSevere().withCause(e).log("failed to execute aoc day");
      throw new RuntimeException(e);
      }
    }
  }

  public static void warmUp(int day, int seconds) {
    final long end = System.nanoTime() + seconds * 1000 * 1000 * 1000L;
    do {
        allDays.get(day-1).part1();
        allDays.get(day-1).part2();
    } while (System.nanoTime() < end);
  }

  public static void run(int day, int part) throws Exception {
    switch(part) {
      case 1 -> executePart1(day);
      case 2 -> executePart2(day);
      default -> executeDay(day);
    }
  }

  private static void executePart1(int day) throws Exception {
    measurePerformance(() -> allDays.get(day-1).part1(), day);
  }

  private static void executePart2(int day) throws Exception {
    measurePerformance(() -> allDays.get(day-1).part2(), day);
  }

  private static void measurePerformance(Callable<Number> c, int day) throws Exception {
    long start = System.nanoTime();
    final Number result = c.call();
    long end = System.nanoTime();
    logger.atInfo().log("Day %d part %d: %d (Duration: %d ns) %n", day, 1, result, end-start);
  }

  private static void executeDay(int day) throws Exception {
    executePart1(day);
    executePart2(day);
  }
}