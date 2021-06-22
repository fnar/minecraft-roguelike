package com.github.fnar.util;

public class ReportThisIssueException extends RuntimeException {
  public ReportThisIssueException(Throwable cause) {
    super(String.format("Please report this issue at %s: ", "https://github.com/fnar/minecraft-roguelike/issues"), cause);
  }
}
