package org.aoc.aoc2022;

import static org.aoc.common.util.Util.rangeToList;

import com.google.common.flogger.FluentLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.aoc.aoc2022.days.Day1;
import org.aoc.aoc2022.days.Day2;
import org.aoc.aoc2022.days.Day3;
import org.aoc.aoc2022.days.Day4;
import org.aoc.aoc2022.days.Day5;
import org.aoc.aoc2022.days.Day6;
import org.aoc.aoc2022.days.Day7;
import org.aoc.aoc2022.days.Day8;
import org.aoc.common.AoCDay;

public class AoC2022 {
  private AoC2022() {}

  static List<AoCDay> allDays;

  static {
    allDays = new ArrayList<>();
    allDays.add(new Day1());
    allDays.add(new Day2());
    allDays.add(new Day3());
    allDays.add(new Day4());
    allDays.add(new Day5());
    allDays.add(new Day6());
    allDays.add(new Day7());
    allDays.add(new Day8());
  }

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static void runSpecific() throws Exception {
    //warmUp(1, 10);
    //executeDay(1);  // 160k ns + 380k ns
    //warmUp(2, 2);
    //executeDay(2); // 300k ns + 400k ns
    //warmUp(3, 2);
    //executeDay(3); // 336k ns + 766k ns
    //warmUp(4, 2);
    //executeDay(4); // 575k ns + 14261k ns
    //warmUp(5, 2);
    //executeDay(5); // 332k ns + 1236k ns
    //warmUp(6, 2);
    //executeDay(6); // 58k ns + 165k ns
    //warmUp(7, 2);
    //executeDay(7); // 400k ns + 1500k ns
    //warmUp(7, 2);
    warmUp(8, 2);
    executeDay(8); // 491k ns + 958k ns
  }

  public static void run() {
    for (Integer integer : rangeToList(1, allDays.size())) {
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
