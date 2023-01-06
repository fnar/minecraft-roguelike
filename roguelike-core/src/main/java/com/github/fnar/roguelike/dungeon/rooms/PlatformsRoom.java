package com.github.fnar.roguelike.dungeon.rooms;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.roguelike.worldgen.BlockPattern;
import com.github.fnar.roguelike.worldgen.shape.FnarLine;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class PlatformsRoom extends BaseRoom {

  public PlatformsRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.size = 8;
    this.ceilingHeight = 6;
    this.depth = random().nextBoolean() ? 2 : 3;
  }

  @Override
  public void generateDecorations(Coord origin, List<Direction> entrances) {
    Direction front = getEntrance(entrances);
    generateIslands(origin, front);
    theFloorIsLava(origin);
    generateCeilingDecoration(origin);
  }

  private void generateIslands(Coord origin, Direction front) {
    BlockBrush pillar = pillars();

    Map<Character, BlockBrush> blockBrushMap = Stream.of(new Object[][]{
        {'.', SingleBlockBrush.AIR},
        {'x', pillar},
    }).collect(Collectors.toMap(data -> (Character) data[0], data -> (BlockBrush) data[1]));

    for (int x = 0; x < 2; x++) {
      for (int z = 0; z < 2; z++) {

        int xOffset = (x == 0 ? -1 : 1) * (getSize() / 2);
        int zOffset = (z == 0 ? -1 : 1) * (getSize() / 2);

        Coord location = origin.copy()
            .down(2)
            .translate(front.left()).translate(front) // center
            .translate(front.left(), xOffset)
            .translate(front, zOffset);

        BlockPattern blockPattern = new BlockPattern(worldEditor, chooseRandomBlockPattern(), blockBrushMap);
        blockPattern.stroke(location, front, true, false);
        blockPattern.stroke(location.up(), front, true, false);
        if (random().nextBoolean()) {
          blockPattern.stroke(location.up(), front, true, false);
        }
      }
    }
  }

  private String chooseRandomBlockPattern() {
    String[] patterns = blockPatternStrings();
    return patterns[(int) (Math.random() * patterns.length)];
  }

  private String[] blockPatternStrings() {
    String blankPattern = "";

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

    return new String[]{
        blankPattern,
        plusPatternString,
        squarePatternString,
        diamondPatternString
    };
  }

  private void generateCeilingDecoration(Coord origin) {
    Direction.cardinals()
        .forEach(direction -> {

          StairsBlock stair = stairs();

          Coord beamStart = origin.copy().translate(direction, getWallDist() / 2 - 1).translate(direction.left(), getWallDist() - 1).up(getCeilingHeight() - 2);
          Coord beamEnd = origin.copy().translate(direction, getWallDist() / 2 - 1).translate(direction.right(), getWallDist() - 1).up(getCeilingHeight() - 2);

          secondaryPillars().setFacing(direction.right());
          new FnarLine(beamStart, beamEnd).fill(worldEditor, secondaryPillars());

          stair.setUpsideDown(true)
              .setFacing(direction.right())
              .stroke(worldEditor, beamStart.down(2));
          secondaryPillars().stroke(worldEditor, beamStart.up());
          stair.stroke(worldEditor, beamStart.translate(direction.right()));

          stair.setUpsideDown(true)
              .setFacing(direction.left())
              .stroke(worldEditor, beamEnd.down(2));
          secondaryPillars().stroke(worldEditor, beamEnd.up());
          stair.stroke(worldEditor, beamEnd.translate(direction.left()));
        });
  }

}
