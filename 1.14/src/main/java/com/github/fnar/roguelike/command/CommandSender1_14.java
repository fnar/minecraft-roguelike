package com.github.fnar.roguelike.command;

import com.github.fnar.minecraft.WorldEditor1_14;
import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.mapper.ItemMapper1_14;
import com.github.fnar.minecraft.world.BlockPosMapper1_14;
import com.github.fnar.roguelike.command.message.ErrorMessage;
import com.github.fnar.roguelike.command.message.Message;
import com.github.fnar.roguelike.command.message.MessageType;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandSender1_14 implements CommandSender {

  private static final Logger logger = LogManager.getLogger(CommandSender1_14.class);

  private final CommandSource commandSource;

  public CommandSender1_14(CommandSource commandSource) {
    this.commandSource = commandSource;
  }

  @Override
  public void sendMessage(Message message) {
    commandSource.sendFeedback(
        formatMessage(message.getMessage(), message.getMessageType())
            .appendText(" ")
            .appendSibling(new StringTextComponent(message.getDetails())), true);
  }

  private static ITextComponent formatMessage(String message, MessageType type) {
    Style style = new Style().setColor(TextFormattingMapper1_14.toTextFormatting(type.getTextFormat().getCodeChar()));
    return new TranslationTextComponent(message).setStyle(style);
  }

  @Override
  public void give(RldItemStack item) {
    Entity player = commandSource.getEntity();
    try {
      ItemStack mappedItem = new ItemMapper1_14().map(item);
      ItemEntity itemEntity = player.entityDropItem(mappedItem, 0);
      itemEntity.setNoPickupDelay();
    } catch (CouldNotMapItemException e) {
      logger.info(e);
      sendMessage(new ErrorMessage(e.getMessage()));
    }
  }

  @Override
  public WorldEditor createWorldEditor() {
    return new WorldEditor1_14(commandSource.getWorld());
  }

  @Override
  public Coord getCoord() {
    return BlockPosMapper1_14.map(
        Optional.ofNullable(commandSource.getEntity())
            .map(Entity::getPosition)
            .orElse(BlockPos.ZERO));
  }

}
