package com.github.fnar.roguelike.command;

import com.github.fnar.minecraft.WorldEditor1_12;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.mapper.ItemMapper1_12;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import greymerk.roguelike.command.CommandSender;
import greymerk.roguelike.command.MessageType;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class CommandSender1_12 implements CommandSender {

  private final ICommandSender commandSender;

  public CommandSender1_12(ICommandSender iCommandSender) {
    this.commandSender = iCommandSender;
  }

  @Override
  public void sendMessage(String message, MessageType type) {
    commandSender.sendMessage(formatMessage(message, type));
  }

  @Override
  public void sendMessage(String message, String details, MessageType type) {
    commandSender.sendMessage(
        formatMessage(message, type)
            .appendText(" ")
            .appendSibling(new TextComponentString(details)));
  }

  private static ITextComponent formatMessage(String message, MessageType type) {
    Style style = new Style().setColor(toTextFormatting(type.getTextFormat()));
    return new TextComponentTranslation(message).setStyle(style);
  }

  @Override
  public void give(RldItemStack item) {
    Entity player = commandSender.getCommandSenderEntity();
    ItemStack mappedItem = new ItemMapper1_12().map(item);
    EntityItem drop = player.entityDropItem(mappedItem, 0);
    drop.setNoPickupDelay();
  }

  @Override
  public WorldEditor createWorldEditor() {
    return new WorldEditor1_12(commandSender.getEntityWorld());
  }

  @Override
  public Coord getPos() {
    BlockPos bp = commandSender.getPosition();
    return new Coord(bp.getX(), bp.getY(), bp.getZ());
  }

  public static TextFormatting toTextFormatting(TextFormat textFormat) {
    if (textFormat.getCodeChar() == TextFormat.BLACK.getCodeChar()) {
      return TextFormatting.BLACK;
    }
    if (textFormat.getCodeChar() == TextFormat.DARKBLUE.getCodeChar()) {
      return TextFormatting.DARK_BLUE;
    }
    if (textFormat.getCodeChar() == TextFormat.DARKGREEN.getCodeChar()) {
      return TextFormatting.DARK_GREEN;
    }
    if (textFormat.getCodeChar() == TextFormat.DARKAQUA.getCodeChar()) {
      return TextFormatting.DARK_AQUA;
    }
    if (textFormat.getCodeChar() == TextFormat.DARKRED.getCodeChar()) {
      return TextFormatting.DARK_RED;
    }
    if (textFormat.getCodeChar() == TextFormat.DARKPURPLE.getCodeChar()) {
      return TextFormatting.DARK_PURPLE;
    }
    if (textFormat.getCodeChar() == TextFormat.GOLD.getCodeChar()) {
      return TextFormatting.GOLD;
    }
    if (textFormat.getCodeChar() == TextFormat.GRAY.getCodeChar()) {
      return TextFormatting.GRAY;
    }
    if (textFormat.getCodeChar() == TextFormat.DARKGRAY.getCodeChar()) {
      return TextFormatting.DARK_GRAY;
    }
    if (textFormat.getCodeChar() == TextFormat.BLUE.getCodeChar()) {
      return TextFormatting.BLUE;
    }
    if (textFormat.getCodeChar() == TextFormat.GREEN.getCodeChar()) {
      return TextFormatting.GREEN;
    }
    if (textFormat.getCodeChar() == TextFormat.AQUA.getCodeChar()) {
      return TextFormatting.AQUA;
    }
    if (textFormat.getCodeChar() == TextFormat.RED.getCodeChar()) {
      return TextFormatting.RED;
    }
    if (textFormat.getCodeChar() == TextFormat.LIGHTPURPLE.getCodeChar()) {
      return TextFormatting.LIGHT_PURPLE;
    }
    if (textFormat.getCodeChar() == TextFormat.YELLOW.getCodeChar()) {
      return TextFormatting.YELLOW;
    }
    if (textFormat.getCodeChar() == TextFormat.WHITE.getCodeChar()) {
      return TextFormatting.WHITE;
    }
    if (textFormat.getCodeChar() == TextFormat.OBFUSCATED.getCodeChar()) {
      return TextFormatting.OBFUSCATED;
    }
    if (textFormat.getCodeChar() == TextFormat.BOLD.getCodeChar()) {
      return TextFormatting.BOLD;
    }
    if (textFormat.getCodeChar() == TextFormat.STRIKETHROUGH.getCodeChar()) {
      return TextFormatting.STRIKETHROUGH;
    }
    if (textFormat.getCodeChar() == TextFormat.UNDERLINE.getCodeChar()) {
      return TextFormatting.UNDERLINE;
    }
    if (textFormat.getCodeChar() == TextFormat.ITALIC.getCodeChar()) {
      return TextFormatting.ITALIC;
    }
    return TextFormatting.RESET;
  }
}
