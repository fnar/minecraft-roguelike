package greymerk.roguelike.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandContext {

  MinecraftServer server;
  ICommandSender sender;

  public CommandContext(MinecraftServer server, ICommandSender sender) {
    this.server = server;
    this.sender = sender;
  }

  public void sendFailure(Exception e) {
    sendFailure(e.getMessage());
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
    String formattedMessage = type.apply(message);
    TextComponentString text = new TextComponentString(formattedMessage);
    sender.sendMessage(text);
  }

  public WorldEditor createEditor() {
    World world = sender.getEntityWorld();
    return new WorldEditor(world);
  }

  public Coord getPos() {
    BlockPos bp = sender.getPosition();
    return new Coord(bp.getX(), bp.getY(), bp.getZ());
  }

  public void give(ItemStack item) {
    Entity player = sender.getCommandSenderEntity();
    EntityItem drop = player.entityDropItem(item, 0);
    drop.setNoPickupDelay();
  }
}
