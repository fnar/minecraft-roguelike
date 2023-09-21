package com.github.fnar.util;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Strings {
  public static String commaConcatenate(String str0, String str1) {
    return str0 + "," + str1;
  }

  public static List<String> splitCommas(String commaDelimited) {
    return Lists.newArrayList(commaDelimited.split(","));
  }

  public static List<String> asStrings(Collection<Object> stringables) {
    return stringables.stream().map(Object::toString).collect(Collectors.toList());
  }

  public static boolean startWith(String original, String region) {
    return region.regionMatches(true, 0, original, 0, original.length());
  }
}
