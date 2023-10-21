package com.github.fnar.roguelike.command.message;

public class SuccessMessage extends BaseMessage {

  public SuccessMessage(String message) {
    super(message);
  }

  @Override
  public MessageType getMessageType() {
    return MessageType.SUCCESS;
  }
}
