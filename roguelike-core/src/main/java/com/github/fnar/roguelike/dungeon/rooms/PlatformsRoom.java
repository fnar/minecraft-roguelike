package com.github.fnar.roguelike.dungeon.rooms;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.worldgen.BlockPattern;
import com.github.fnar.minecraft.worldgen.shape.FnarLine;

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
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class PlatformsRoom extends BaseRoom {

  public PlatformsRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Direction front = entrances.get(0);

    generateWalls(origin, front);
    generateDoorways(origin, entrances, getSize());
    generateIslands(origin, front);
    theFloorIsLava(origin, front);
    generateCeilingDecoration(origin);

    return null;
  }

  private void generateIslands(Coord origin, Direction front) {
    BlockBrush pillar = levelSettings.getTheme().getPrimary().getPillar();

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
        if (worldEditor.getRandom().nextBoolean()) {
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

  private void generateCeilingDecoration(Coord origin) {
    BlockBrush pillar = levelSettings.getTheme().getSecondary().getPillar();
    StairsBlock stair = levelSettings.getTheme().getPrimary().getStair();
    Direction.cardinals()
        .forEach(direction -> {

          Coord beamStart = origin.copy().translate(direction, getSize() / 2 - 1).translate(direction.left(), getSize() - 1).up(getHeight() - 2);
          Coord beamEnd = origin.copy().translate(direction, getSize() / 2 - 1).translate(direction.right(), getSize() - 1).up(getHeight() - 2);

          pillar.setFacing(direction.right());
          new FnarLine(beamStart, beamEnd).fill(worldEditor, pillar);

          stair.setUpsideDown(true);
          stair.setFacing(direction.right());
          stair.stroke(worldEditor, beamStart.down(2));
          pillar.stroke(worldEditor, beamStart.up());
          stair.stroke(worldEditor, beamStart.translate(direction.right()));

          stair.setUpsideDown(true);
          stair.setFacing(direction.left());
          stair.stroke(worldEditor, beamEnd.down(2));
          pillar.stroke(worldEditor, beamEnd.up());
          stair.stroke(worldEditor, beamEnd.translate(direction.left()));
        });
  }

  private int getHeight() {
    return 7;
  }

  @Override
  public int getSize() {
    return 7;
  }
}
