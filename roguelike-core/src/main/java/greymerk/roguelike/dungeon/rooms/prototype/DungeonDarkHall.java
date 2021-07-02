package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonDarkHall extends DungeonBase {

  public DungeonDarkHall(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {
    Theme theme = levelSettings.getTheme();
    BlockBrush outerWall = theme.getPrimary().getWall();
    BlockBrush wall = theme.getSecondary().getWall();
    BlockBrush floor = theme.getPrimary().getFloor();

    BlockBrush pillar = theme.getSecondary().getPillar();
    BlockBrush accentFloor = theme.getSecondary().getFloor();

    RectHollow r1 = RectHollow.newRect(
        origin.copy().north(7).west(7).down(),
        origin.copy().south(7).east(7).up(7));
    outerWall.fill(worldEditor, r1, false, true);

    RectHollow r2 = RectHollow.newRect(
        origin.copy().north(4).west(4).up(6),
        origin.copy().south(4).east(4).up(9));
    outerWall.fill(worldEditor, r2, false, true);

    RectSolid r3 = RectSolid.newRect(
        origin.copy().north(6).west(6).down(),
        origin.copy().south(6).east(6).down());
    floor.fill(worldEditor, r3, false, true);

    for (Direction entrance : entrances) {
      RectSolid r4 = RectSolid.newRect(
          origin.copy().translate(entrance.left()),
          origin.copy().translate(entrance.right()).translate(entrance, 7));
      accentFloor.fill(worldEditor, r4, false, true);
    }

    for (Direction side : Direction.CARDINAL) {
      generateAccentPillar(origin.copy().translate(side, 6).translate(side.left(), 6), 5);
      generateBeam(side, origin.copy().translate(side, 6).up(6), 13);
      generateBeam(side, origin.copy().translate(side, 3).up(6), 7);
      generateBeam(side, origin.copy().translate(side, 3).up(8), 7);
      generateBeam(side, origin.copy().up(8), 7);

      pillar.stroke(worldEditor, origin.copy().translate(side, 3).up(7));

      if (!entrances.contains(side)) {
        pillar(worldEditor, levelSettings, side.reverse(), origin.copy().translate(side, 6));
      } else {
        generateEntranceArchway(origin.copy().translate(side, 7), side, levelSettings.getTheme().getSecondary());
      }

      wall.fill(worldEditor, RectSolid.newRect(
          origin.copy().translate(side, 6).up(6),
          origin.copy().translate(side, 4).up(6)));

      for (Direction orthogonal : side.orthogonals()) {
        Coord cursor = origin.copy().translate(side, 6).translate(orthogonal, 3);
        pillar(worldEditor, levelSettings, side.reverse(), cursor);
        wall.fill(worldEditor, RectSolid.newRect(
            cursor.copy().up(6),
            cursor.copy().up(6).translate(side.reverse(), 6)));
      }
    }

    return this;
  }

  private void generateEntranceArchway(Coord origin, Direction side, BlockSet blockBrush) {
    BlockBrush wall = blockBrush.getWall();
    BlockBrush pillar = blockBrush.getPillar();
    StairsBlock stair = blockBrush.getStair();

    Coord aboveOrigin = origin.copy().up(2);

    wall.fill(worldEditor, RectSolid.newRect(
        aboveOrigin.copy().translate(side.left(), 2),
        aboveOrigin.copy().translate(side.right(), 2).up(3)));

    SingleBlockBrush.AIR.stroke(worldEditor, aboveOrigin);

    for (Direction orthogonal : side.orthogonals()) {
      stair.setUpsideDown(true).setFacing(orthogonal.reverse()).stroke(worldEditor, aboveOrigin.copy().translate(orthogonal));
      pillar(worldEditor, levelSettings, orthogonal.reverse(), origin.copy().translate(side.back()).translate(orthogonal, 3));
      Coord cursor = origin.copy().translate(orthogonal, 2);
      pillar.stroke(worldEditor, cursor);
      pillar.stroke(worldEditor, cursor.up());
    }
  }

  private void generateBeam(Direction dir, Coord origin, int width) {
    BlockBrush wall = levelSettings.getTheme().getSecondary().getWall();
    int left = width / 2;
    int right = width - left - 1;
    Coord beamLeftAnchor = origin.copy().translate(dir.left(), left);
    Coord beamRightAnchor = origin.copy().translate(dir.right(), right);
    RectSolid beam = RectSolid.newRect(beamLeftAnchor, beamRightAnchor);
    wall.fill(worldEditor, beam);
  }

  public void generateAccentPillar(Coord origin, int height) {
    BlockBrush pillar = levelSettings.getTheme().getSecondary().getPillar();
    RectSolid.newRect(
        origin.copy(),
        origin.copy().up(height)
    ).fill(worldEditor, pillar);
  }

  private void pillar(WorldEditor editor, LevelSettings settings, Direction dir, Coord origin) {
    BlockBrush wall = settings.getTheme().getSecondary().getWall();
    StairsBlock stair = settings.getTheme().getSecondary().getStair();

    generateAccentPillar(origin, 5);

    Coord cursor = origin.copy().up(3).translate(dir);
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor.up());
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor.translate(dir));
    stair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, cursor.up());
    cursor.translate(dir);
    if (editor.isAirBlock(cursor)) {
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    } else {
      wall.stroke(editor, cursor);
    }
  }

  @Override
  public int getSize() {
    return 9;
  }


}
