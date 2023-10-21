package com.github.fnar.roguelike.command.message;

public class InfoMessage extends BaseMessage {

  public InfoMessage(String message) {
    super(message);
  }

  @Override
  public MessageType getMessageType() {
    return MessageType.INFO;
  }

}
