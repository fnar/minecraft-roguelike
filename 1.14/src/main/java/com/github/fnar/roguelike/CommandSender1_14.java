package com.github.fnar.roguelike;

import com.github.fnar.roguelike.worldgen.WorldEditor1_14;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

import greymerk.roguelike.command.CommandSender;
import greymerk.roguelike.command.MessageType;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandSender1_14 implements CommandSender {

  private CommandSource commandSource;

  public CommandSender1_14(CommandSource commandSource) {
    this.commandSource = commandSource;
  }

  @Override
  public void sendMessage(String message, MessageType type) {
    String formattedMessage = type.apply(message);
    StringTextComponent text = new StringTextComponent(formattedMessage);
    commandSource.getEntity().sendMessage(text);
  }

  @Override
  public void give(ItemStack item) {
    Entity player = commandSource.getEntity();
    ItemEntity itemEntity = player.entityDropItem(item, 0);
    itemEntity.setNoPickupDelay();
  }

  @Override
  public WorldEditor createWorldEditor() {
    return new WorldEditor1_14(commandSource.getWorld());
  }

  @Override
  public Coord getPos() {
    BlockPos pos = commandSource.getEntity().getPosition();
    return new Coord(pos.getX(), pos.getY(), pos.getZ());
  }
}
