package com.github.fnar.roguelike.command.message;

public abstract class BaseMessage implements Message {

  private final String message;
  private String details;

  public BaseMessage(String message) {
    this.message = message;
  }

  public BaseMessage(String message, String details) {
    this.message = message;
    this.details = details;
  }

  public BaseMessage withDetails(String details) {
    this.details = details;
    return this;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public String getDetails() {
    return details;
  }

}
