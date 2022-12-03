package org.aoc.aoc2022.days;

import static org.aoc.aoc2022.days.Day2.Gesture.PAPER;
import static org.aoc.aoc2022.days.Day2.Gesture.ROCK;
import static org.aoc.aoc2022.days.Day2.Gesture.SCISSORS;
import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.aoc.common.AoCDay;

public class Day2 implements AoCDay {

  enum Gesture {
    ROCK(1), PAPER(2), SCISSORS(3);

    public final int points;

    private Gesture(int points) {
      this.points = points;
    }
  }
  public static final String INPUT_FILE = "Day2_input.txt";
  public static final int YEAR = 2022;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public Number part1() {
    final List<String[]> input = getInputAsList();
    return input.stream()
        .map(s -> rockPaperScissors(mapGesture(s[0]), mapGesture(s[1])))
        .reduce(0, Integer::sum);
  }

  @Override
  public Number part2() {
    final List<String[]> input = getInputAsList();
    return input.stream()
        .map(s -> determineOutcome(mapGesture(s[0]), s[1]))
        .reduce(0, Integer::sum);
  }

  private int determineOutcome(Gesture gesture, String s) {
    return switch (s) {
      case "Y" -> gesture.points + 3;
      case "X" -> lose(gesture).points;
      case "Z" -> win(gesture).points + 6;
      default -> throw new RuntimeException("Unrecognized outcome");
    };
  }

  private Gesture win(Gesture gesture) {
    return gesture.equals(ROCK) ? PAPER : gesture.equals(PAPER) ? SCISSORS : ROCK;
  }

  private Gesture lose(Gesture gesture) {
    return gesture.equals(ROCK) ? SCISSORS : gesture.equals(PAPER) ? ROCK : PAPER;
  }

  private int rockPaperScissors(Gesture opponent, Gesture you) {
    int score = 0;
    if(opponent.equals(you)) {
      score=3;
    }
    else if (you.equals(ROCK) && opponent.equals(SCISSORS) ||
        you.equals(PAPER) && opponent.equals(ROCK) ||
        you.equals(SCISSORS) && opponent.equals(PAPER)) {
      score = 6;
    }

    return score + you.points;
  }

  private Gesture mapGesture(String s){
    return switch (s) {
      case "A", "X" -> ROCK;
      case "B", "Y" -> PAPER;
      case "C", "Z" -> SCISSORS;
      default -> throw new RuntimeException("Unrecognized Gesture");
    };
  }

  private static List<String[]> getInputAsList() {
    List<String[]> data = new ArrayList<>();
    final List<String> input = Arrays.stream(readInputFromFile(YEAR, INPUT_FILE)).toList();
    for(String line : input ) {
        data.add(line.split(" "));
    }
    return data;
  }
}
