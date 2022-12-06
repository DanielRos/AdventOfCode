package org.aoc.aoc2022.days;

import com.google.common.flogger.FluentLogger;
import org.aoc.common.AoCDay;
import org.aoc.common.util.Util;

public class Day6 implements AoCDay {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static final String INPUT_FILE = "Day6_input.txt";
  //public static final String INPUT_FILE = "test.txt";
  public static final int YEAR = 2022;

  @Override
  public Number part1() {
    return startOfMessageIndex(getInput(), 4);
  }

  @Override
  public Number part2() {
    return startOfMessageIndex(getInput(), 14);
  }

  private Integer startOfMessageIndex(String input, int chunkSize) {
    for (int i = chunkSize; i < input.length(); i++) {
      if (isUnique(input.substring(i - chunkSize, i))) {
        return i;
      }
    }
    return null;
  }

  private boolean isUnique(String sub) {
    char[] ch = sub.toCharArray();
    for (int i = 0; i < ch.length; i++) {
      for (int j = i + 1; j < ch.length; j++) {
        if (ch[i] == ch[j]) {
          return false;
        }
      }
    }
    return true;
  }

  public String getInput() {
    return Util.readFile(String.format("aoc%d/%s", YEAR, INPUT_FILE));
  }
}
