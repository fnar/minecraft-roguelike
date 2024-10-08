package com.github.fnar.roguelike.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import greymerk.roguelike.command.CommandRoute;


public class RoguelikeCommand1_12 extends CommandBase {

  private final CommandRoute routes;

  public RoguelikeCommand1_12() {
    this.routes = new greymerk.roguelike.command.routes.RoguelikeCommand1_12();
  }

  @Nonnull
  @Override
  public String getName() {
    return "roguelike";
  }

  @Override
  public String getUsage(ICommandSender sender) {
    return "/roguelike";
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
    List<String> argsList = Arrays.asList(args);
    CommandContext context = new CommandContext1_12(sender, argsList);
    routes.execute(context, argsList);
  }

  @Override
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
    return routes.getTabCompletion(Arrays.asList(args));
  }
}
