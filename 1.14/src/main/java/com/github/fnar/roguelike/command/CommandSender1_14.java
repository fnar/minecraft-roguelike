package com.github.fnar.roguelike.command;

import com.github.fnar.minecraft.WorldEditor1_14;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.mapper.ItemMapper1_14;

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

  private final CommandSource commandSource;

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
  public void sendMessage(String message, String details, MessageType type) {
  }

  @Override
  public void give(RldItemStack item) {
    Entity player = commandSource.getEntity();
    ItemStack mappedItem = new ItemMapper1_14().map(item);
    ItemEntity itemEntity = player.entityDropItem(mappedItem, 0);
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
