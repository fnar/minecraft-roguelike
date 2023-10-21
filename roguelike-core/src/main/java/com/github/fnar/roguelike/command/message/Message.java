package com.github.fnar.roguelike.command.message;

public interface Message {

  MessageType getMessageType();

  String getMessage();

  String getDetails();

}
