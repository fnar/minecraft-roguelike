package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.decorative.Skull;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonOssuary extends DungeonBase {

  public DungeonOssuary(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {
    ThemeBase theme = levelSettings.getTheme();
    BlockBrush walls = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    end = origin.copy();
    start.translate(Cardinal.DOWN);
    start.translate(Cardinal.NORTH, 8);
    start.translate(Cardinal.EAST, 8);
    end.translate(Cardinal.SOUTH, 8);
    end.translate(Cardinal.WEST, 8);
    end.translate(Cardinal.UP, 6);
    RectHollow.newRect(start, end).fill(worldEditor, walls, false, true);

    // entrance arches
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = origin.copy();
      cursor.translate(dir, 7);
      for (Cardinal o : dir.orthogonals()) {
        start = cursor.copy();
        start.translate(o, 2);
        end = start.copy();
        end.translate(Cardinal.UP, 5);
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, walls);

        start = cursor.copy();
        start.translate(o, 2);
        start.translate(Cardinal.UP, 2);
        start.translate(dir.reverse(), 2);
        end = start.copy();
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(worldEditor, walls);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, start);

        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, walls);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, start);

        start.translate(dir.reverse());
        start.translate(Cardinal.UP);
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, walls);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, start);

        start = cursor.copy();
        start.translate(Cardinal.UP, 3);
        end = start.copy();
        start.translate(dir.antiClockwise());
        end.translate(dir.clockwise());
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(worldEditor, walls);
        start.translate(Cardinal.UP);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, walls);
        start.translate(Cardinal.UP);
        start.translate(dir.reverse());
        end.translate(dir.reverse());
        RectSolid.newRect(start, end).fill(worldEditor, walls);

        Coord c = cursor.copy();
        c.translate(o);
        c.translate(Cardinal.UP, 2);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, c);
        c.translate(dir.reverse());
        c.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, c);
        c.translate(dir.reverse());
        c.translate(Cardinal.UP);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, c);
        c.translate(dir.reverse());
      }

      Coord c = origin.copy();
      c.translate(dir, 7);
      c.translate(Cardinal.UP, 3);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, c);
      c.translate(dir.reverse());
      c.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, c);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = origin.copy();
      cursor.translate(dir, 4);
      cursor.translate(Cardinal.UP, 5);
      start = cursor.copy();
      start.translate(Cardinal.NORTH);
      start.translate(Cardinal.EAST);
      end = cursor.copy();
      end.translate(Cardinal.SOUTH);
      end.translate(Cardinal.WEST);
      RectSolid.newRect(start, end).fill(worldEditor, walls);
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);
      for (Cardinal d : Cardinal.DIRECTIONS) {
        Coord c = cursor.copy();
        c.translate(d);
        stair.setUpsideDown(true).setFacing(d.reverse()).stroke(worldEditor, c);
      }
    }

    // corner pillars
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      start = origin.copy();
      start.translate(dir, 6);
      start.translate(dir.antiClockwise(), 6);
      end = start.copy();
      end.translate(dir);
      end.translate(dir.antiClockwise());
      end.translate(Cardinal.UP, 6);
      RectSolid.newRect(start, end).fill(worldEditor, walls);
    }

    // central ceiling
    cursor = origin.copy();
    cursor.translate(Cardinal.UP, 6);
    start = cursor.copy();
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.EAST, 2);
    end = cursor.copy();
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.WEST, 2);
    RectSolid.newRect(start, end).fill(worldEditor, walls);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.DOWN);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    for (Cardinal d : Cardinal.DIRECTIONS) {
      Coord c = cursor.copy();
      c.translate(d);
      stair.setUpsideDown(true).setFacing(d.reverse()).stroke(worldEditor, c);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      Cardinal[] orthogonal = dir.orthogonals();
      cursor = origin.copy();
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(dir, 2);
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);
      for (Cardinal o : orthogonal) {
        Coord c = cursor.copy();
        c.translate(o);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, c);
      }
      cursor.translate(orthogonal[0], 2);
      walls.stroke(worldEditor, cursor);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = origin.copy();
      cursor.translate(dir, 4);
      cursor.translate(dir.antiClockwise(), 4);
      cursor.translate(Cardinal.UP, 5);
      start = cursor.copy();
      start.translate(Cardinal.NORTH);
      start.translate(Cardinal.EAST);
      end = cursor.copy();
      end.translate(Cardinal.SOUTH);
      end.translate(Cardinal.WEST);
      RectSolid.newRect(start, end).fill(worldEditor, walls);
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);
      for (Cardinal d : Cardinal.DIRECTIONS) {
        Coord c = cursor.copy();
        c.translate(d);
        stair.setUpsideDown(true).setFacing(d.reverse()).stroke(worldEditor, c);
      }

      for (Cardinal d : new Cardinal[]{dir, dir.antiClockwise()}) {
        cursor = origin.copy();
        cursor.translate(dir, 4);
        cursor.translate(dir.antiClockwise(), 4);
        cursor.translate(Cardinal.UP, 4);
        cursor.translate(d, 2);
        SingleBlockBrush.AIR.stroke(worldEditor, cursor);
        for (Cardinal o : d.orthogonals()) {
          Coord c = cursor.copy();
          c.translate(o);
          stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, c);
        }

        start = origin.copy();
        start.translate(dir, 4);
        start.translate(dir.antiClockwise(), 4);
        start.translate(d, 3);
        end = start.copy();
        end.translate(Cardinal.UP, 4);
        RectSolid.newRect(start, end).fill(worldEditor, walls);
        start = end.copy();
        start.translate(d.orthogonals()[0]);
        end.translate(d.orthogonals()[1]);
        end.translate(Cardinal.UP, 2);
        RectSolid.newRect(start, end).fill(worldEditor, walls);
        start.translate(d.reverse());
        end.translate(d.reverse());
        start.translate(Cardinal.UP);
        RectSolid.newRect(start, end).fill(worldEditor, walls);

        for (Cardinal o : d.orthogonals()) {
          cursor = origin.copy();
          cursor.translate(dir, 4);
          cursor.translate(dir.antiClockwise(), 4);
          cursor.translate(d, 3);
          cursor.translate(o);
          walls.stroke(worldEditor, cursor);
          cursor.translate(Cardinal.UP);
          skull(worldEditor, d.reverse(), cursor);
          cursor.translate(Cardinal.UP);
          walls.stroke(worldEditor, cursor);
          cursor.translate(Cardinal.UP);
          skull(worldEditor, d.reverse(), cursor);
          cursor.translate(Cardinal.UP);
          cursor.translate(d.reverse());
          stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        }
      }

      cursor.translate(dir, 2);
    }

    return this;
  }

  private void skull(WorldEditor editor, Cardinal dir, Coord origin) {
    if (editor.getRandom().nextInt(3) == 0) {
      return;
    }

    Coord cursor = origin.copy();
    cursor.translate(Cardinal.DOWN);
    if (editor.isAirBlock(cursor)) {
      return;
    }

    Skull skull = editor.getRandom().nextInt(15) == 0 ? Skull.WITHER : Skull.SKELETON;
    editor.setSkull(editor, origin, dir, skull);
  }

  @Override
  public int getSize() {
    return 10;
  }
}
