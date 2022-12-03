package org.aoc.aoc2021.days;

import org.aoc.common.AoCDay;
import org.aoc.common.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.aoc.common.util.Util.getNumbersBetween;

public class Day5 implements AoCDay {
  public static final String INPUT_TXT = "Day5_input.txt";
  public static final int YEAR = 2021;

  @Override
  public Integer part1() {
    return findOverlappingCoordinateCount(extractInputData(), Line::getStraightLinePoints);
  }

  @Override
  public Integer part2() {
    return findOverlappingCoordinateCount(extractInputData(), Line::getAllLinePoints);
  }

  private int findOverlappingCoordinateCount(List<Line> lines, Function<Line, List<Coordinate>> getStraightLinePoints) {
    return (int) lines.stream()
            .map(getStraightLinePoints)
            .flatMap(List::stream)
            .collect(groupingBy(identity(), counting())) // group by duplicate count
            .entrySet()
            .stream()
            .filter(m -> m.getValue() > 1) // only keep those with more than one count
            .count();
  }

  private List<Line> extractInputData() {
    final List<String> lineInput = Arrays.stream(Util.readInputFromFile(YEAR, INPUT_TXT)).toList();

    List<Line> result = new ArrayList<>();
    Pattern p = Pattern.compile("(\\d*),(\\d*)\\s->\\s(\\d*),(\\d*)");
    for (String line : lineInput) {
      Matcher m = p.matcher(line);
      if (m.matches()) {
        result.add(
            new Line(
                new Coordinate(m.group(1), m.group(2)), new Coordinate(m.group(3), m.group(4))));
      }
    }
    return result;
  }
}

class Line {
  Coordinate start;
  Coordinate end;

  public Line(Coordinate start, Coordinate end) {
    this.start = start;
    this.end = end;
  }

  public List<Coordinate> getStraightLinePoints() {
    List<Coordinate> linePoints = new ArrayList<>();
    if (start.getX() == end.getX()) {
      getNumbersBetween(start.getY(), end.getY())
          .forEach(
              num -> {
                linePoints.add(new Coordinate(start.getX(), num));
              });
    } else if (start.getY() == end.getY()) {
      getNumbersBetween(start.getX(), end.getX())
          .forEach(
              num -> {
                linePoints.add(new Coordinate(num, start.getY()));
              });
    }
    return linePoints;
  }

  public List<Coordinate> getAllLinePoints() {
    final List<Coordinate> linePoints = getStraightLinePoints();
    if(start.getY() != end.getY() && start.getX() != end.getX()) {
      final List<Integer> xNumbers = getNumbersBetween(start.getX(), end.getX());
      final List<Integer> yNumbers = getNumbersBetween(start.getY(), end.getY());
      for (int i = 0; i < xNumbers.size(); i++) {
          linePoints.add(new Coordinate(xNumbers.get(i), yNumbers.get(i)));
      }
    }
    return linePoints;
  }
}

class Coordinate {
  int x;
  int y;

  public Coordinate(String x, String y) {
    this(Integer.parseInt(x), Integer.parseInt(y));
  }

  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public String toString() {
    return "AoC2021.days.Coordinate{" + "x=" + x + ", y=" + y + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Coordinate that = (Coordinate) o;

    if (x != that.x) return false;
    return y == that.y;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }
}
