package com.github.fnar.roguelike.command.message;

public class SpecialMessage extends BaseMessage {

  public SpecialMessage(String message) {
    super(message);
  }

  @Override
  public MessageType getMessageType() {
    return MessageType.SPECIAL;
  }
}
