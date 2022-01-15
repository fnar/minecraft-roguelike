package greymerk.roguelike.command;

import com.github.fnar.minecraft.item.RldItemStack;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandContext1_12 {

  private final CommandSender commandSender;

  public CommandContext1_12(CommandSender commandSender) {
    this.commandSender = commandSender;
  }

  public void sendFailure(Exception e) {
    sendFailure(e.getMessage());
    e.printStackTrace();
  }

  public void sendFailure(String message) {
    sendMessage("Failure: " + message, MessageType.ERROR);
  }

  public void sendInfo(String message) {
    sendMessage(message, MessageType.INFO);
  }

  public void sendSpecial(String message) {
    sendMessage(message, MessageType.SPECIAL);
  }

  public void sendSuccess(String message) {
    sendMessage("Success: " + message, MessageType.SUCCESS);
  }

  public void sendMessage(String message, MessageType type) {
    commandSender.sendMessage(message, type);
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
}
