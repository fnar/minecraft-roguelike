package com.github.fnar.roguelike.command.commands;

public interface RoguelikeCommand extends Runnable {

  void onRun() throws Exception;

  void onSuccess();

  void onException(Exception exception);

}
