package com.github.fnar.util;

import com.google.common.collect.Lists;

import java.util.List;

public class Strings {
  public static String commaConcatenate(String str0, String str1) {
    return str0 + "," + str1;
  }

  public static List<String> splitCommas(String commaDelimited) {
    return Lists.newArrayList(commaDelimited.split(","));
  }
}
