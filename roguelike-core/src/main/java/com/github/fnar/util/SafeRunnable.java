package com.github.fnar.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SafeRunnable implements Runnable {

  private static final Logger logger = LogManager.getLogger(SafeRunnable.class);

  public static void run(Runnable runnable) {
    new SafeRunnable(runnable).run();
  }

  private final Runnable runnable;

  public SafeRunnable(Runnable runnable) {
    this.runnable = runnable;
  }

  @Override
  public void run() {
    try {
      runnable.run();
    } catch (Exception e) {
      logger.error(e);
    }
  }
}
