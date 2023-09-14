package greymerk.roguelike.command;

import com.github.fnar.forge.ModLoader;
import com.github.fnar.minecraft.item.RldItemStack;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandContext1_12 {

  private final CommandSender commandSender;

  public CommandContext1_12(CommandSender commandSender) {
    this.commandSender = commandSender;
  }

  public void sendFailure(Exception e) {
    //sendFailure(e.getMessage());

    sendFailure("", e.getLocalizedMessage());
    e.printStackTrace();
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
    commandSender.sendMessage(message, type);
  }

  public void sendMessage(String message, String details, MessageType type) {
    commandSender.sendMessage(message, details, type);
  }

  public WorldEditor createEditor() {
    return commandSender.createWorldEditor();
  }

  public Coord getPos() {
    return commandSender.getPos();
  }

  public void give(RldItemStack item) {
    commandSender.give(item);
  }

  public ModLoader getModLoader() {
    return requiredModName -> false;
  }
}
