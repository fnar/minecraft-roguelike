package com.github.fnar.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static greymerk.roguelike.dungeon.Dungeon.MOD_ID;

public class TimedTask implements Runnable {

  private static final Logger logger = LogManager.getLogger(MOD_ID);
  private final String name;
  private final Runnable runnable;

  public TimedTask(String name, Runnable runnable) {
    this.name = name;
    this.runnable = runnable;
  }

  public void run() {
    long start = System.currentTimeMillis();
    try {
      runnable.run();
    } catch (Exception ignored) {

    }

    long end = System.currentTimeMillis();
    long runTime = end - start;

    logger.info(String.format("Task %s completed in %d milliseconds.", name, runTime));
  }
}
