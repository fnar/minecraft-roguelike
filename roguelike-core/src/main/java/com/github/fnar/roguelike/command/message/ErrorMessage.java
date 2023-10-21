package com.github.fnar.roguelike.command.message;

public class ErrorMessage extends BaseMessage {

  public ErrorMessage(String message) {
    super(message);
  }

  @Override
  public MessageType getMessageType() {
    return MessageType.ERROR;
  }
}
