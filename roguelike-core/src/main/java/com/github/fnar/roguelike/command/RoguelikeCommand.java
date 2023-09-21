package com.github.fnar.roguelike.command;

public interface RoguelikeCommand extends Runnable {

  void onRun();

  void onSuccess();

  void onException(Exception exception);

}
