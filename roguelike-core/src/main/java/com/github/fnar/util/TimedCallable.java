package com.github.fnar.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

import static greymerk.roguelike.dungeon.Dungeon.MOD_ID;

public class TimedCallable<V> implements Callable<V> {

  private static final Logger logger = LogManager.getLogger(MOD_ID);
  private final String name;
  private final Callable<V> callable;

  public TimedCallable(String name, Callable<V> callable) {
    this.name = name;
    this.callable = callable;
  }

  @Override
  public V call() {
    long start = System.currentTimeMillis();
    V result = null;
    try {
      result = callable.call();
    } catch (Exception exception) {
      logger.error(exception);
    }

    long end = System.currentTimeMillis();
    long runTime = end - start;

    logger.info(String.format("Task %s completed in %d milliseconds.", name, runTime));
    return result;
  }
}
