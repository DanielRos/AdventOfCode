package org.aoc.aoc2021;

import static org.aoc.common.util.Util.getNumbersBetween;

import org.aoc.aoc2021.days.Day10;
import org.aoc.aoc2021.days.Day9;
import org.aoc.common.AoCDay;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.aoc.aoc2021.days.Day1;
import org.aoc.aoc2021.days.Day2;
import org.aoc.aoc2021.days.Day3;
import org.aoc.aoc2021.days.Day4;
import org.aoc.aoc2021.days.Day5;
import org.aoc.aoc2021.days.Day6;
import org.aoc.aoc2021.days.Day7;
import org.aoc.aoc2021.days.Day8;
import com.google.common.flogger.FluentLogger;

public class AoC2021 {

  private AoC2021() {}

  static List<AoCDay> allDays = List.of(new Day1(), new Day2(), new Day3(),
          new Day4(), new Day5(), new Day6(), new Day7(), new Day8(), new Day9(),
          new Day10());

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static void runAoc2021() {
    run(10,2);
    //run(2,3);
    //run(3,3);
    //run(4,3);
    //run(5,3);
    //run(6,3);
    // run(8,2);
  }

  public static void run() {
    getNumbersBetween(1, allDays.size()).forEach(AoC2021::executeDay);
  }

  public static void run(int day, int part) {
    switch(part) {
      case 1 -> executePart1(day);
      case 2 -> executePart2(day);
      default -> executeDay(day);
    }
  }

  private static void executePart1(int day) {
    Instant start = Instant.now();
    final Number result = allDays.get(day-1).part1();
    Instant end = Instant.now();
    logger.atInfo().log("Day %d part %d: %d (Duration: %d ms) %n", day, 1, result, Duration.between(start,end).toMillis());
  }

  private static void executePart2(int day) {
    Instant start = Instant.now();
    final Number result = allDays.get(day-1).part2();
    Instant end = Instant.now();
    logger.atInfo().log("Day %d part %d: %d (Duration: %d ms) \n", day, 2, result, Duration.between(start,end).toMillis());
  }

  private static void executeDay(int day) {
    executePart1(day);
    executePart2(day);
  }
}