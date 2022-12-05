package org.aoc.aoc2022.days;

import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.aoc.common.AoCDay;

public class Day5 implements AoCDay {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static final String INPUT_FILE = "Day5_input.txt";
  //public static final String INPUT_FILE = "test.txt";
  public static final int YEAR = 2022;

  @Override
  public Number part1() {
    final SupplyStack supplyStack = createSupplyStack();
    supplyStack.getRearrangementProcedures()
        .forEach(procedure -> supplyStack.moveCrates(procedure[0], procedure[1], procedure[2]));

    logger.atInfo().log(supplyStack.getCratesOnTop());
    return 0;
  }

  @Override
  public Number part2() {
    final SupplyStack supplyStack = createSupplyStack();
    supplyStack.getRearrangementProcedures()
        .forEach(procedure -> supplyStack.moveCratesRetainingOrder(procedure[0], procedure[1], procedure[2]));

    logger.atInfo().log(supplyStack.getCratesOnTop());
    return 0;
  }

  private SupplyStack createSupplyStack() {
    final String[] inputLines = readInputFromFile(YEAR, INPUT_FILE);

    int columnCount = getColumnCount(inputLines);

    final Pattern containsCrates = Pattern.compile("[A-Z]");

    SupplyStack supplyStack = new SupplyStack();

    for (String line : inputLines) {
      if (containsCrates.matcher(line).find()) {
        for (int col = 1; col <= columnCount; col++) {
          final int index = ((col - 1) * 4) + 1;
          if (index < line.length()) {
            final char crate = line.charAt(index);
            supplyStack.addCrate(col, crate);
          }
        }
      }
      if (line.startsWith("move")) {
        supplyStack.addRearrangement(line);
      }
    }

    return supplyStack;
  }

  private static Matcher matchRegex(String regex, String procedure) {
    final Pattern compile = Pattern.compile(regex);
    return compile.matcher(procedure);
  }

  private static int getColumnCount(String[] inputLines) {
    int columnCount = 0;
    for (String line : inputLines) {
      if (line.startsWith(" 1")) {
        final Matcher matcher = matchRegex("\\d", line);
        while (matcher.find()) {
          columnCount++;
        }
        break;
      }
    }
    return columnCount;
  }

  private static class SupplyStack {

    private final Map<Integer, Deque<Character>> crates;

    private final List<int[]> rearrangementProcedure;

    public SupplyStack() {
      this.crates = new HashMap<>();
      this.rearrangementProcedure = new ArrayList<>();
    }

    public List<int[]> getRearrangementProcedures() {
      return rearrangementProcedure;
    }

    public void addCrate(int column, Character crate) {
      crates.computeIfAbsent(column, k -> new ArrayDeque<>());
      if (crate != ' ') {
        crates.get(column).add(crate);
      }
    }

    public void addRearrangement(String procedure) {
      final Matcher matcher = matchRegex("\\d+", procedure);
      int[] rearrangementInstructions = new int[3];
      int counter = 0;
      while (matcher.find()) {
        rearrangementInstructions[counter] = Integer.parseInt(matcher.group());
        counter++;
      }
      rearrangementProcedure.add(rearrangementInstructions);
    }

    public void moveCrates(int amount, int from, int to) {
      final Deque<Character> fromStack = crates.get(from);
      final Deque<Character> toStack = crates.get(to);

      for (int i = 0; i < amount; i++) {
        toStack.addFirst(fromStack.pop());
      }
    }

    public void moveCratesRetainingOrder(int amount, int from, int to) {
      final Deque<Character> fromStack = crates.get(from);
      final Deque<Character> toStack = crates.get(to);

      Deque<Character> removedCrates = new ArrayDeque<>();
      for (int i = 0; i < amount; i++) {
        removedCrates.addLast(fromStack.pop());
      }
      final Iterator<Character> removedCratesIterator = removedCrates.descendingIterator();
      while (removedCratesIterator.hasNext()) {
        toStack.addFirst(removedCratesIterator.next());
      }
    }

    @Override
    public String toString() {
      return "SupplyStack{" +
          "crates=" + crates +
          ", rearrangementProcedure=" + rearrangementProcedure +
          '}';
    }

    public String getCratesOnTop() {

      return crates.values().stream()
          .map(Deque::getFirst)
          .map(String::valueOf)
          .reduce("", (s1, s2) -> s1 + s2);
    }
  }
}
