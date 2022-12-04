package org.aoc.aoc2022.days;

import static java.util.stream.Collectors.toList;
import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.Arrays;
import java.util.List;
import org.aoc.common.AoCDay;
import org.aoc.common.util.Util;

public class Day4 implements AoCDay {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static final String INPUT_FILE = "Day4_input.txt";
  //public static final String INPUT_FILE = "test.txt";
  public static final int YEAR = 2022;

  @Override
  public Number part1() {
    final int[][] input = getInput();

    int result = 0;

    for (int[] row : input) {
      if(firstContainedInSecond(row) || secondContainedInFirst(row)) {
        result++;
      }
    }

    return result;
  }

  @Override
  public Number part2() {
    final int[][] input = getInput();
    int result = 0;

    for (int[] row : input) {
      final List<Integer> firstRange = Util.rangeToList(row[0], row[1]);
      final List<Integer> secondRange = Util.rangeToList(row[2], row[3]);
      result += Util.intersection(List.of(firstRange, secondRange)).isEmpty() ? 0 : 1;
    }
    return result;
  }

  private boolean secondContainedInFirst(int[] row) {
    return Util.isWithinRange(row[2], row[0], row[1]) && Util.isWithinRange(row[3], row[0], row[1]);
  }

  private static boolean firstContainedInSecond(int[] row) {
    return Util.isWithinRange(row[0], row[2], row[3]) && Util.isWithinRange(row[1], row[2], row[3]);
  }

  private int[][] getInput() {
    final List<String[]> splitLines = Arrays.stream(readInputFromFile(YEAR, INPUT_FILE))
        .map(s -> s.split("[,-]"))
        .toList();

    final int inputColumns = 4;
    int[][] input = new int[splitLines.size()][inputColumns];
    for(int i = 0; i < splitLines.size(); i++) {
      final String[] strings = splitLines.get(i);
      for (int j = 0; j < inputColumns; j++) {
       input[i][j] = Integer.parseInt(strings[j]);
      }
    }
    return input;
  }
}
