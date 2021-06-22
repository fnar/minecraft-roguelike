package com.github.fnar.roguelike.dungeon.rooms;

import com.github.fnar.minecraft.worldgen.BlockPattern;
import com.github.fnar.minecraft.worldgen.shape.FnarLine;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class PlatformsRoom extends DungeonBase {

  public PlatformsRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    Direction front = entrances.get(0);

    generateWalls(origin, front);
    generateDoorways(origin, entrances);
    generateIslands(origin, front);
    theFloorIsLava(origin, front);
    generateCeilingDecoration(origin, front);

    return null;
  }

  private void generateIslands(Coord origin, Direction front) {
    String plusPatternString = "" +
        ". x . \n" +
        "x x x \n" +
        ". x . \n";

    String squarePatternString = "" +
        "x x x \n" +
        "x x x \n" +
        "x x x \n";

    String diamondPatternString = "" +
        ". x x \n" +
        "x x x \n" +
        "x x . \n";

    BlockBrush pillar = levelSettings.getTheme().getPrimary().getPillar();
    Map<Character, BlockBrush> blockBrushMap = Stream.of(new Object[][]{
        {'.', SingleBlockBrush.AIR},
        {'x', pillar},
    }).collect(Collectors.toMap(data -> (Character) data[0], data -> (BlockBrush) data[1]));

    BlockPattern plusPatternBlocks = new BlockPattern(worldEditor, plusPatternString, blockBrushMap);
    BlockPattern squarePatternBlocks = new BlockPattern(worldEditor, squarePatternString, blockBrushMap);
    BlockPattern diamondPatternBlocks = new BlockPattern(worldEditor, diamondPatternString, blockBrushMap);

    for (int x = 0; x < 3; x++) {
      for (int z = 0; z < 3; z++) {

        if (worldEditor.getRandom().nextBoolean() && worldEditor.getRandom().nextBoolean()) {
          continue;
        }

        int xOffset = (x - 1) * (getSize() * 2 / 3);
        int zOffset = (z - 1) * (getSize() * 2 / 3);

        Coord location = origin.copy()
            .down(2)
            .translate(front.left()).translate(front) // center
            .translate(front.left(), xOffset)
            .translate(front, zOffset);

        BlockPattern blockPattern;
        if (worldEditor.getRandom().nextBoolean()) {
          blockPattern = plusPatternBlocks;
        } else if (worldEditor.getRandom().nextBoolean()) {
          blockPattern = squarePatternBlocks;
        } else {
          blockPattern = diamondPatternBlocks;
        }

        blockPattern.paintPattern(location, front);
        blockPattern.paintPattern(location.up(), front);
        if (worldEditor.getRandom().nextBoolean()) {
          blockPattern.paintPattern(location.up(), front);
        }

        // the middle island is elevated
        if (x == 0 && z == 0) {
          blockPattern.paintPattern(location.up(), front);
        }
      }
    }
  }

  private void generateWalls(Coord origin, Direction front) {
    int depth = worldEditor.getRandom().nextBoolean() ? 2 : 3;
    BlockBrush walls = levelSettings.getTheme().getPrimary().getWall();
    RectHollow.newRect(
        origin.copy().translate(front.left(), getSize()).translate(front, getSize()).copy().down(depth),
        origin.copy().translate(front.right(), getSize()).translate(front.back(), getSize()).copy().up(getHeight())
    ).fill(worldEditor, walls);
  }

  private void theFloorIsLava(Coord origin, Direction front) {
    BlockBrush liquid = levelSettings.getTheme().getPrimary().getLiquid();
    RectSolid.newRect(
        origin.copy().translate(front, getSize() - 1).translate(front.left(), getSize() - 1).down(),
        origin.copy().translate(front.back(), getSize() - 1).translate(front.right(), getSize() - 1).down(2)
    ).fill(worldEditor, liquid, true, false);
  }

  private void generateDoorways(Coord origin, List<Direction> entrances) {
    for (Direction entrance : entrances) {
      Coord doorwayOrigin = origin.copy().translate(entrance, getSize());
      generateDoorway(doorwayOrigin, entrance);
    }
  }

  private void generateDoorway(Coord origin, Direction facing) {
    RectSolid.newRect(
        origin.copy().translate(facing.left()),
        origin.copy().translate(facing.right()).up(2)
    ).fill(worldEditor, SingleBlockBrush.AIR);
  }

  private void generateCeilingDecoration(Coord origin, Direction front) {
    BlockBrush pillar = levelSettings.getTheme().getPrimary().getPillar();
    StairsBlock stair = levelSettings.getTheme().getPrimary().getStair();
    Direction.cardinals()
        .forEach(direction -> {

          Coord beamStart = origin.copy().translate(direction, getSize() / 2 - 1).translate(direction.left(), getSize() - 1).up(getHeight() - 2);
          Coord beamEnd = origin.copy().translate(direction, getSize() / 2 - 1).translate(direction.right(), getSize() - 1).up(getHeight() - 2);

          new FnarLine(beamStart, beamEnd).fill(worldEditor, pillar);
          stair.setUpsideDown(true);
          stair.setFacing(direction.right()).stroke(worldEditor, beamStart.down());
          stair.setFacing(direction.left()).stroke(worldEditor, beamEnd.down());
        });
  }

  private int getHeight() {
    return 6;
  }

  @Override
  public int getSize() {
    return 9;
  }
}
