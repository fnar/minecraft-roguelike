package com.github.fnar.roguelike.command.routes;

import com.github.fnar.roguelike.command.CommandContext;
import com.github.fnar.roguelike.command.commands.RoomCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import greymerk.roguelike.command.BaseCommandRoute;
import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.worldgen.Coord;

public class RoomCommand1_12 extends BaseCommandRoute {

  private static final int roomTypeArgumentIndex = 1;

  @Override
  public void execute(CommandContext commandContext, List<String> args) {
    if (hasArgumentForRoomType(commandContext)) {
      generateRoom(commandContext);
    } else {
      sendUsage(commandContext);
      listAllRooms(commandContext);
    }
  }

  private static boolean hasArgumentForRoomType(CommandContext commandContext) {
    return commandContext.getArgument(roomTypeArgumentIndex).isPresent();
  }

  private static void generateRoom(CommandContext commandContext) {
    Coord coord = commandContext.getSenderCoord();
    Optional<String> roomTypeMaybe = commandContext.getArgument(roomTypeArgumentIndex);
    roomTypeMaybe.ifPresent(roomType -> new RoomCommand(commandContext, coord, roomType).run());
  }

  private static void sendUsage(CommandContext commandContext) {
    commandContext.sendInfo("notif.roguelike.usage_", "/roguelike room [setting]");
  }

  private void listAllRooms(CommandContext commandContext) {
    commandContext.sendInfo(Arrays.toString(RoomType.values()));
  }
}
