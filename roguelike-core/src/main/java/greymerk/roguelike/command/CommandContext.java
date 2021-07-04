package greymerk.roguelike.command;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandContext {

  private CommandSender commandSender;

  public CommandContext(CommandSender commandSender) {
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

  public void give(ItemStack item) {
    commandSender.give(item);
  }
}
