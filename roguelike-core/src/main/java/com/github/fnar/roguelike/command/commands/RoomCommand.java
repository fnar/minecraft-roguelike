package com.github.fnar.roguelike.command.commands;

import com.google.common.collect.Lists;

import com.github.fnar.roguelike.command.CommandContext;

import java.util.Optional;

import greymerk.roguelike.dungeon.base.RoomType;
import greymerk.roguelike.dungeon.rooms.Frequency;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class RoomCommand extends BaseRoguelikeCommand {

  private final String roomType;
  private final Coord coord;

  public RoomCommand(CommandContext context, String roomType, Coord coord) {
    super(context);
    this.roomType = roomType;
    this.coord = coord;
  }

  @Override
  public boolean onRun() throws Exception {
    LevelSettings levelSettings = new LevelSettings(0);
    WorldEditor editor = context.createEditor();
    RoomType roomType = RoomType.fromString(this.roomType);
    RoomSetting roomSetting = new RoomSetting(roomType, null, Frequency.RANDOM, 1, 1, Lists.newArrayList(0, 1, 2, 3, 4), Optional.empty());
    roomSetting.instantiate(levelSettings, editor).generate(coord, Direction.CARDINAL);
    return true;
  }
}
