package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedHardenedClay;

public class DungeonsEnchant extends DungeonBase {

  public DungeonsEnchant(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {
    Cardinal dir = entrances.get(0);
    Random rand = worldEditor.getRandom();
    ThemeBase theme = levelSettings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush panel = stainedHardenedClay().setColor(DyeColor.PURPLE);
    StairsBlock stair = theme.getPrimary().getStair();

    List<Coord> chests = new ArrayList<>();

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    start.translate(dir, 5);
    end = new Coord(start);
    start.translate(new Coord(-2, 0, -2));
    end.translate(new Coord(2, 3, 2));
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(dir.reverse(), 2);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir, 2);
    end.translate(dir.clockwise(), 4);
    end.translate(Cardinal.UP, 3);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = new Coord(origin);
    start.translate(dir.reverse(), 2);
    end = new Coord(start);
    end.translate(dir.reverse());
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(Cardinal.UP, 3);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = new Coord(origin);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    end.translate(dir, 3);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 3);
    RectSolid.newRect(start, end).fill(worldEditor, wall);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    start.translate(dir.reverse(), 2);
    end.translate(dir, 2);
    theme.getPrimary().getFloor().fill(worldEditor, new RectSolid(start, end));

    start = new Coord(origin);
    start.translate(dir.reverse(), 4);
    end = new Coord(start);
    end.translate(Cardinal.UP, 3);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.newRect(start, end).fill(worldEditor, wall, false, true);

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    for (Cardinal d : Cardinal.DIRECTIONS) {
      start = new Coord(cursor);
      start.translate(d, 2);
      start.translate(d.antiClockwise(), 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      if (d == dir.reverse()) {
        continue;
      }

      start = new Coord(cursor);
      start.translate(d, 3);
      end = new Coord(start);
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      end.translate(Cardinal.UP, 2);
      RectSolid.newRect(start, end).fill(worldEditor, panel);

      start = new Coord(cursor);
      start.translate(d, 2);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(worldEditor, new RectSolid(start, end));
      start.translate(d.reverse());
      start.translate(Cardinal.UP);
      end.translate(d.reverse());
      end.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(worldEditor, new RectSolid(start, end));
    }

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    cursor.translate(Cardinal.UP, 4);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.GLOWSTONE.getBrush().stroke(worldEditor, cursor);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    cursor.translate(dir.reverse());
    wall.stroke(worldEditor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 5);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.translate(Cardinal.UP);
    wall.stroke(worldEditor, cursor);

    for (Cardinal d : Cardinal.DIRECTIONS) {

      start = new Coord(origin);
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      if (d == dir) {
        end.translate(d);
      } else {
        end.translate(d, 2);
      }

      RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

      for (Cardinal o : d.orthogonals()) {
        Coord s = new Coord(start);
        s.translate(d);
        Coord e = new Coord(end);
        s.translate(o);
        e.translate(o);
        stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, new RectSolid(s, e));
      }

      Coord s = new Coord(start);
      s.translate(d);
      Coord e = new Coord(end);
      s.translate(Cardinal.UP);
      e.translate(Cardinal.UP);
      RectSolid.newRect(s, e).fill(worldEditor, wall);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(d);
      stair.setUpsideDown(true).setFacing(d.reverse()).stroke(worldEditor, cursor);
      cursor.translate(d.antiClockwise());
      wall.stroke(worldEditor, cursor);

    }

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir, 3);
    end.translate(dir, 7);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(worldEditor, panel);

    for (Cardinal o : dir.orthogonals()) {
      start = new Coord(origin);
      start.translate(dir.reverse(), 3);
      start.translate(o, 3);
      end = new Coord(start);
      end.translate(dir.reverse());
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(o, 3);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(worldEditor, wall);
      cursor = new Coord(end);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

      start = new Coord(origin);
      start.translate(o, 4);
      end = new Coord(start);
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, new RectSolid(start, end));
      start.translate(Cardinal.UP, 3);
      end.translate(Cardinal.UP, 3);
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, new RectSolid(start, end));
      start.translate(o.reverse());
      start.translate(Cardinal.UP);
      end.translate(o.reverse());
      end.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, new RectSolid(start, end));

      for (Cardinal r : o.orthogonals()) {
        start = new Coord(origin);
        start.translate(o, 4);
        start.translate(r, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(worldEditor, pillar);
      }

      start = new Coord(origin);
      start.translate(o, 5);
      end = new Coord(start);
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP, 2);
      RectSolid.newRect(start, end).fill(worldEditor, panel);

      start = new Coord(origin);
      start.translate(dir.reverse(), 3);
      start.translate(o, 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);
      cursor = new Coord(end);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir.reverse(), 2);
      cursor.translate(o);
      cursor.translate(Cardinal.UP, 4);
      wall.stroke(worldEditor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);

      start = new Coord(origin);
      start.translate(Cardinal.UP);
      start.translate(o, 4);
      end = new Coord(start);
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      chests.addAll(new RectSolid(start, end).get());
    }

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 2);
    cursor.translate(Cardinal.UP, 4);
    stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    cursor.translate(dir.reverse());
    wall.stroke(worldEditor, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    BlockType.ENCHANTING_TABLE.getBrush().stroke(worldEditor, cursor);

    List<Coord> chestLocations = chooseRandomLocations(1, chests);
    worldEditor.getTreasureChestEditor().createChests(levelSettings.getDifficulty(origin), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.ENCHANTING));

    return this;
  }

  @Override
  public boolean validLocation(WorldEditor editor, Cardinal dir, Coord pos) {
    Coord start;
    Coord end;

    start = new Coord(pos);
    end = new Coord(start);
    start.translate(dir.reverse(), 4);
    end.translate(dir, 8);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.UP, 2);

    for (Coord c : new RectHollow(start, end)) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }

  public int getSize() {
    return 6;
  }
}
