package com.github.fnar.roguelike.command.commands;

public interface RoguelikeCommand extends Runnable {

  boolean onRun() throws Exception;

  void onSuccess();

  void onException(Exception exception);

}
