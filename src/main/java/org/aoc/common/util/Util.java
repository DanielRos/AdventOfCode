package org.aoc.common.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Util {

  static final ClassLoader loader = Util.class.getClassLoader();

  public static String readFile(final String fileName) {
    final InputStream is = loader.getResourceAsStream(fileName);
    return convertStreamToString(is);
  }

  static String convertStreamToString(final InputStream is) {
    return StreamToStringConverter(is);
  }

  static String StreamToStringConverter(InputStream inputStream) {
    String streamAsString = "";
    if (inputStream != null) {
      final java.util.Scanner s = new java.util.Scanner(inputStream);
      s.useDelimiter("\\A");

      streamAsString = s.hasNext() ? s.next() : "";
      s.close();
    }
    return streamAsString;
  }

  public static String[] readInputFromFile(int year, String day1Input) {
    return readFile(String.format("aoc%d/%s", year,day1Input)).split("\n");
  }

  public static <T> List<T> firstThreeResults(List<T> inputList) {
    return inputList.stream().sequential().limit(3L).toList();
  }

  public static <T> List<List<T>> partition(List<T> inputList, int partitionSize) {
    AtomicInteger counter = new AtomicInteger();
    return inputList.stream()
        .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / partitionSize))
        .values()
        .stream()
        .toList();
  }

  public static <T> List<T> intersection(List<List<T>> lists) {

    if(lists.size() < 2 ) {
      return Collections.emptyList();
    }

    List<T> intersection = new ArrayList<>(lists.get(0));
    for (ListIterator<List<T>> iter = lists.listIterator(0); iter.hasNext(); ) {
      intersection.retainAll(iter.next());
    }
    return intersection;
  }

  public static List<Integer> rangeToList(int num1, int num2) {
    return IntStream.rangeClosed(num1, num2)
        .boxed()
        .toList();
  }
  public static List<Integer> getNumbersBetween(int num1, int num2) {
    if(num1 > num2) {
      return IntStream.rangeClosed(num2, num1)
              .boxed()
              .sorted(Collections.reverseOrder())
              .toList();
    }else if(num2 > num1) {
      return IntStream.rangeClosed(num1, num2)
              .boxed()
              .toList();
    } else {
      return Collections.emptyList();
    }
  }

  public static int charFrequencyCount(String s, String s1) {
    int count = 0;
    for (char c : s.toCharArray()) {
      for (char c1 : s1.toCharArray()) {
        if(c == c1) {
          count++;
        }
      }
    }
    return count;
  }

  public static String keyOfVal(Map<String, Integer> map, Integer value) {
    return map.entrySet()
            .stream()
            .filter(entry -> value.equals(entry.getValue()))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow();
  }

  public static String sort(String text) {
    char[] chars = text.toCharArray();

    Arrays.sort(chars);
    return new String(chars);
  }

  public static boolean isWithinRange(int n, int start, int end) {
    if(n >= start && n <= end) {
      return true;
    }
    return false;
  }
}
