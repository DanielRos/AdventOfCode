package org.aoc.aoc2022.days;

import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.Arrays;
import org.aoc.common.AoCDay;

public class Day6 implements AoCDay {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  //public static final String INPUT_FILE = "Day6_input.txt";
  public static final String INPUT_FILE = "test.txt";
  public static final int YEAR = 2022;

  @Override
  public Number part1() {
    final String input = getInput();
    return findUniqueSequenceInChunksOf(input, 4);
  }

  @Override
  public Number part2() {
    final String input = getInput();
    return findUniqueSequenceInChunksOf(input, 14);
  }

  private Integer findUniqueSequenceInChunksOf(String input, int chunkSize) {
    for (int i = 0; i < input.length(); i++) {
      if(i - chunkSize >= 0) {
        String sub = input.substring(i- chunkSize, i);
        if(!containsDuplicates(sub)) {
          return i;
        }
      }
    }
    return null;
  }

  private boolean containsDuplicates(String sub) {
    char[] ch = sub.toCharArray();
    for(int i=0;i<ch.length;i++) {
      for(int j=i+1;j<ch.length;j++) {
        if(ch[i]==ch[j]) {
          return true;
        }
      }
    }
    return false;
  }

  public String getInput() {
    return Arrays.stream(readInputFromFile(YEAR, INPUT_FILE))
        .reduce("", (s1, s2) -> s1 + s2);
  }
}
