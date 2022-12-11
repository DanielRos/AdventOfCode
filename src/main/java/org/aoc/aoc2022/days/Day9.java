package org.aoc.aoc2022.days;

import static org.aoc.common.util.Util.readInputFromFile;

import com.google.common.flogger.FluentLogger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import org.aoc.common.AoCDay;

public class Day9 implements AoCDay {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static final String INPUT_FILE = "Day9_input.txt";
  //public static final String INPUT_FILE = "test.txt";
  public static final int YEAR = 2022;
  public static List<String[]> input = getInput();

  @Override
  public Number part1() {
    return uniqueTailPositions(1);
  }

  @Override
  public Number part2() {
    return uniqueTailPositions(9);
  }

  private long uniqueTailPositions(int nrOfTails) {
    Position head = new Position(0, 0);
    Position[] tails = createTails(nrOfTails);
    Set<Position> tailPositions = new HashSet<>();
    tailPositions.add(new Position(0, 0));
    for (String[] instruction : input) {
      final char direction = instruction[0].charAt(0);
      final int steps = Integer.parseInt(instruction[1]);
      IntStream.rangeClosed(0, steps - 1)
          .boxed()
          .forEach(step -> tailPositions.add(moveHead(head, direction, tails)));
    }
    return tailPositions.stream().distinct().count();
  }

  private Position[] createTails(int n) {
    final Position[] positions = new Position[n];
    IntStream.rangeClosed(0, n - 1).boxed().forEach(i -> positions[i] = new Position(0, 0));
    return positions;
  }

  private Position moveHead(Position head, char direction, Position[] tails) {
    head.move(direction);

    return moveTails(head, tails);
  }

  private Position moveTails(Position head, Position[] tails) {
    Position follows = head;
    for (Position tail : tails) {
      if (follows.equals(tail)
          || Math.abs(follows.x - tail.x) <= 1 && Math.abs(follows.y - tail.y) <= 1) {
      } else {
        tail.x = follows.x == tail.x ? tail.x : follows.x - tail.x > 0 ? tail.x + 1 : tail.x - 1;
        tail.y = follows.y == tail.y ? tail.y : follows.y - tail.y > 0 ? tail.y + 1 : tail.y - 1;
      }

      if (tail == tails[tails.length - 1]) {
        return new Position(tail.x, tail.y);
      }
      follows = tail;
    }

    return null;
  }

  private static List<String[]> getInput() {
    return Arrays.stream(readInputFromFile(YEAR, INPUT_FILE))
        .map(s -> s.split(" "))
        .toList();
  }

  private static class Position {

    int x;

    int y;

    public Position(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public void move(char direction) {
      switch (direction) {
        case 'R' -> x += 1;
        case 'L' -> x -= 1;
        case 'U' -> y += 1;
        case 'D' -> y -= 1;
        default -> throw new IllegalArgumentException("direction not recognized");
      }
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Position position = (Position) o;

      if (x != position.x) {
        return false;
      }
      return y == position.y;
    }

    @Override
    public int hashCode() {
      int result = x;
      result = 31 * result + y;
      return result;
    }
  }
}
