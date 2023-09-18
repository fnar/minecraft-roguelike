package com.github.fnar.roguelike.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ILocationArgument;

import org.apache.commons.lang3.NotImplementedException;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.CommandSender;

class BiomeCommand {

  static RequiredArgumentBuilder<CommandSource, String> biomeCommand() {
    return Commands.argument("biome", StringArgumentType.string())
        .executes(getBiome())
        .then(atBlockPosition());
  }

  private static Command<CommandSource> getBiome() {
    return context -> {
      CommandSource source = context.getSource();
      CommandSender commandSender = new CommandSender1_14(source);
      CommandContext commandContext = new CommandContext(commandSender);
      throw new NotImplementedException("1.14 upgrade");
//      GetBiome.getBiome(commandContext, new Coord(source.getEntity().getPosition().getX(), source.getEntity().getPosition().getY(), source.getEntity().getPosition().getZ()));
//      return 0;
    };
  }

  private static RequiredArgumentBuilder<CommandSource, ILocationArgument> atBlockPosition() {
    throw new NotImplementedException("1.14 upgrade");
//    return Commands.argument("position", BlockPosArgument.blockPos())
//        .executes(context -> {
//          CommandSource source = context.getSource();
//          CommandSender commandSender = new CommandSender1_14(source);
//          CommandContext commandContext = new CommandContext(commandSender);
//          BlockPos position = BlockPosArgument.getLoadedBlockPos(context, "position");
//          GetBiome.getBiome(commandContext, new Coord(position.getX(), position.getY(), position.getZ()));
//          return 0;
//        });
  }
}
