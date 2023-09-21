package com.github.fnar.roguelike.command;

import com.github.fnar.forge.ModLoader;
import com.github.fnar.minecraft.item.RldItemStack;

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
    sendFailure("", e.getLocalizedMessage());
  }

  public void sendFailure(String message) {
    sendMessage("notif.roguelike.failure_" + message, MessageType.ERROR);
  }

  public void sendFailure(String message, String details) {
    sendMessage("notif.roguelike.failure_" + message, details, MessageType.ERROR);
  }

  public void sendInfo(String message) {
    sendMessage(message, MessageType.INFO);
  }

  public void sendInfo(String message, String details) {
    sendMessage(message, details, MessageType.INFO);
  }

  public void sendSpecial(String message) {
    sendMessage(message, MessageType.SPECIAL);
  }

  public void sendSpecial(String message, String details) {
    sendMessage(message, details, MessageType.SPECIAL);
  }

  public void sendSuccess(String message) {
    sendMessage("notif.roguelike.success_" + message, MessageType.SUCCESS);
  }

  public void sendSuccess(String message, String details) {
    sendMessage("notif.roguelike.success_" + message, details, MessageType.SUCCESS);
  }

  public void sendMessage(String message, MessageType type) {
    getCommandSender().sendMessage(message, type);
  }

  public void sendMessage(String message, String details, MessageType type) {
    getCommandSender().sendMessage(message, details, type);
  }

  public WorldEditor createEditor() {
    return getCommandSender().createWorldEditor();
  }

  public Coord getPos() {
    return getCommandSender().getPos();
  }

  public void give(RldItemStack item) {
    getCommandSender().give(item);
  }

  public ModLoader getModLoader() {
    return requiredModName -> false;
  }

  public String getArgument(String argumentName) {
    return this.contextHolder.getArgument(argumentName);
  }

  public Coord getArgumentAsCoord(String position) {
    return this.contextHolder.getArgumentAsCoord(position);
  }
}
