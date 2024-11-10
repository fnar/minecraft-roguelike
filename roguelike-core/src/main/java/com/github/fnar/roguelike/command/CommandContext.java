package com.github.fnar.roguelike.command;

import com.github.fnar.forge.ModLoader;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.command.message.ErrorMessage;
import com.github.fnar.roguelike.command.message.InfoMessage;
import com.github.fnar.roguelike.command.message.Message;
import com.github.fnar.roguelike.command.message.SpecialMessage;
import com.github.fnar.roguelike.command.message.SuccessMessage;
import com.github.fnar.roguelike.command.message.WarningMessage;
import com.github.fnar.util.Exceptions;

import java.util.Optional;

import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandContext {

  private final ContextHolder contextHolder;

  public CommandContext(ContextHolder contextHolder) {
    this.contextHolder = contextHolder;
  }

  private CommandSender getCommandSender() {
    return contextHolder.getCommandSender();
  }

  public void sendFailure(Exception e) {
    sendFailure("", Exceptions.asString(e));
  }

  public void sendFailure(String message) {
    sendMessage(new ErrorMessage("notif.roguelike.failure_" + message));
  }

  public void sendFailure(String message, String details) {
    sendMessage(new ErrorMessage("notif.roguelike.failure_" + message).withDetails(details));
  }

  public void sendInfo(String message) {
    sendMessage(new InfoMessage(message));
  }

  public void sendInfo(String message, String details) {
    sendMessage(new InfoMessage(message).withDetails(details));
  }

  public void sendSpecial(String message) {
    sendMessage(new SpecialMessage(message));
  }

  public void sendSpecial(String message, String details) {
    sendMessage(new SpecialMessage(message).withDetails(details));
  }

  public void sendSuccess(String message) {
    sendMessage(new SuccessMessage("notif.roguelike.success_" + message));
  }

  public void sendSuccess(String message, String details) {
    sendMessage(new SuccessMessage("notif.roguelike.success_" + message).withDetails(details));
  }

  public void sendWarning(String message) {
    sendMessage(new WarningMessage(message));
  }

  public void sendWarning(String message, String details) {
    sendMessage(new WarningMessage(message).withDetails(details));
  }

  public void sendMessage(Message message) {
    getCommandSender().sendMessage(message);
  }

  public WorldEditor createEditor() {
    return getCommandSender().createWorldEditor();
  }

  public Coord getSenderCoord() {
    return getCommandSender().getCoord();
  }

  public void give(RldItemStack item) {
    getCommandSender().give(item);
  }

  public ModLoader getModLoader() {
    return createEditor().getModLoader();
  }

  public Optional<String> getArgument(int argumentIndex) {
    return this.contextHolder.getArgument(argumentIndex);
  }

  public Optional<String> getArgument(String argumentName) {
    return this.contextHolder.getArgument(argumentName);
  }

  public Optional<SettingIdentifier> getArgumentAsSettingIdentifier(String argumentName) {
    return this.contextHolder.getArgumentAsSettingIdentifier(argumentName);
  }

  public Optional<Coord> getArgumentAsCoord(int argumentIndex) {
    return this.contextHolder.getArgumentAsCoord(argumentIndex);
  }

  public Optional<Coord> getArgumentAsCoord(String position) {
    return this.contextHolder.getArgumentAsCoord(position);
  }

  public Coord parseNearbyOrXZCoord(int argumentIndex) {
    if (isNearby(argumentIndex)) {
      return getSenderXZ();
    }
    return parseXZCoordMaybe(argumentIndex).orElse(getSenderXZ());
  }

  private Coord getSenderXZ() {
    return getSenderCoord().setY(0);
  }

  private Optional<Coord> parseXZCoordMaybe(int argumentIndex) {
    try {
      return this.contextHolder.getArgumentAsXZCoord(argumentIndex);
    } catch (IllegalArgumentException ignored) {
    }
    return Optional.empty();
  }

  public boolean isNearby(int argumentIndex) {
    return getArgument(argumentIndex)
        .filter(string -> string.equals("here") || string.equals("nearby"))
        .isPresent();
  }
}
