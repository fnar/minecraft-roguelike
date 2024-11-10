package com.github.fnar.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.RoomCommand;

import java.util.Arrays;
import java.util.List;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;

public class RoomCommand1_12 extends BaseCommandRoute {

  @Override
  public void execute(CommandContext commandContext, List<String> args) {
    try {
      ArgumentParser argumentParser = new ArgumentParser(args);
      if (!argumentParser.hasEntry(0)) {
        commandContext.sendInfo("notif.roguelike.usage_", "/roguelike room [setting]");
        listAllRooms(commandContext);
        return;
      }
      String roomType = argumentParser.get(0);
      Coord coord = commandContext.getSenderCoord();
      new RoomCommand(commandContext, roomType, coord).run();
    } catch (Exception e) {
      commandContext.sendFailure(e);
    }
  }

  private void listAllRooms(CommandContext commandContext) {
    commandContext.sendInfo(Arrays.toString(RoomType.values()));
  }
}
