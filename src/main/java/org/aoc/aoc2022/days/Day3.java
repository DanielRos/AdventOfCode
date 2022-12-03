package org.aoc.aoc2022.days;

import static org.aoc.common.util.Util.intersection;
import static org.aoc.common.util.Util.partition;
import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.aoc.common.AoCDay;

public class Day3 implements AoCDay {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static final String INPUT_FILE = "Day3_input.txt";
  //public static final String INPUT_FILE = "test.txt";
  public static final int YEAR = 2022;

  @Override
  public Number part1() {
    return getInput()
        .stream()
        .map(Rucksack::findCommonItem)
        .map(this::findItemPriority)
        .reduce(0, Integer::sum);
  }

  @Override
  public Number part2() {
    return partition(getInput(), 3)
        .stream()
        .map(this::findGroupBadge)
        .map(this::findItemPriority)
        .reduce(0, Integer::sum);
  }

  private char findGroupBadge(List<Rucksack> rucksacks) {
    final List<List<Character>> groupRucksackContents = rucksacks.stream().map(Rucksack::getAllItems).toList();
    return intersection(groupRucksackContents)
        .stream()
        .findFirst()
        .orElseThrow();
  }

  private List<Rucksack> getInput() {
    return Arrays.stream(readInputFromFile(YEAR, INPUT_FILE)).map(Rucksack::new).toList();
  }

  private Integer findItemPriority(Character item) {
    final boolean upperCase = Character.isUpperCase(item);
    final int itemASCII = item;

    if(upperCase) {
      int uppercaseOffset = 64;
      return itemASCII - uppercaseOffset  + 26;
    }
    int lowercaseOffset = 96;
    return itemASCII - lowercaseOffset;
  }

  static class Rucksack {
    private final List<Character> firstCompartment;
    private final List<Character> secondCompartment;

    Rucksack(String contents) {
      final int contentSize = contents.length() -1;
      firstCompartment = createCompartment(contents, 0, contentSize/2);
      secondCompartment = createCompartment(contents, contentSize/2 + 1, contentSize);
    }

    private List<Character> createCompartment(String contents, int start, int size) {
      final List<Character> chars = contents.codePoints()
          .mapToObj(c -> (char) c)
          .toList();

      return IntStream
          .rangeClosed(start, size)
          .mapToObj(chars::get)
          .toList();
    }

    public Character findCommonItem() {
      return firstCompartment.stream()
          .filter(secondCompartment::contains)
          .findFirst()
          .orElseThrow();
    }

    @Override
    public String toString() {
      return "Rucksack{" +
          "firstCompartment=" + firstCompartment +
          ", secondCompartment=" + secondCompartment +
          '}';
    }

    public List<Character> getAllItems() {
      List<Character> all = new ArrayList();
      all.addAll(firstCompartment);
      all.addAll(secondCompartment);
      return all;
    }
  }
}
