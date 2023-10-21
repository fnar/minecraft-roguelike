package com.github.fnar.roguelike.command.message;

public class WarningMessage extends BaseMessage {

  public WarningMessage(String message) {
    super(message);
  }

  public WarningMessage(String message, String details) {
    super(message, details);
  }

  @Override
  public MessageType getMessageType() {
    return MessageType.WARNING;
  }
}
