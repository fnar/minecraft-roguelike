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

  public DungeonsEnchant(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {
    Cardinal dir = entrances.get(0);
    Random rand = editor.getRandom();
    ThemeBase theme = settings.getTheme();
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
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(dir.reverse(), 2);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir, 2);
    end.translate(dir.clockwise(), 4);
    end.translate(Cardinal.UP, 3);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = new Coord(origin);
    start.translate(dir.reverse(), 2);
    end = new Coord(start);
    end.translate(dir.reverse());
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(Cardinal.UP, 3);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    start = new Coord(origin);
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    end.translate(dir, 3);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 3);
    RectSolid.newRect(start, end).fill(editor, wall);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    start.translate(dir.reverse(), 2);
    end.translate(dir, 2);
    theme.getPrimary().getFloor().fill(editor, new RectSolid(start, end));

    start = new Coord(origin);
    start.translate(dir.reverse(), 4);
    end = new Coord(start);
    end.translate(Cardinal.UP, 3);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid.newRect(start, end).fill(editor, wall, false, true);

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    for (Cardinal d : Cardinal.DIRECTIONS) {
      start = new Coord(cursor);
      start.translate(d, 2);
      start.translate(d.antiClockwise(), 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, pillar);

      if (d == dir.reverse()) {
        continue;
      }

      start = new Coord(cursor);
      start.translate(d, 3);
      end = new Coord(start);
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      end.translate(Cardinal.UP, 2);
      RectSolid.newRect(start, end).fill(editor, panel);

      start = new Coord(cursor);
      start.translate(d, 2);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(editor, new RectSolid(start, end));
      start.translate(d.reverse());
      start.translate(Cardinal.UP);
      end.translate(d.reverse());
      end.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(editor, new RectSolid(start, end));
    }

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    cursor.translate(Cardinal.UP, 4);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.GLOWSTONE.getBrush().stroke(editor, cursor);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    wall.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 5);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    wall.stroke(editor, cursor);

    for (Cardinal d : Cardinal.DIRECTIONS) {

      start = new Coord(origin);
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      if (d == dir) {
        end.translate(d);
      } else {
        end.translate(d, 2);
      }

      RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

      for (Cardinal o : d.orthogonals()) {
        Coord s = new Coord(start);
        s.translate(d);
        Coord e = new Coord(end);
        s.translate(o);
        e.translate(o);
        stair.setUpsideDown(true).setFacing(o.reverse()).fill(editor, new RectSolid(s, e));
      }

      Coord s = new Coord(start);
      s.translate(d);
      Coord e = new Coord(end);
      s.translate(Cardinal.UP);
      e.translate(Cardinal.UP);
      RectSolid.newRect(s, e).fill(editor, wall);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(d);
      stair.setUpsideDown(true).setFacing(d.reverse()).stroke(editor, cursor);
      cursor.translate(d.antiClockwise());
      wall.stroke(editor, cursor);

    }

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir, 3);
    end.translate(dir, 7);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, panel);

    for (Cardinal o : dir.orthogonals()) {
      start = new Coord(origin);
      start.translate(dir.reverse(), 3);
      start.translate(o, 3);
      end = new Coord(start);
      end.translate(dir.reverse());
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, pillar);

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(o, 3);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, wall);
      cursor = new Coord(end);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

      start = new Coord(origin);
      start.translate(o, 4);
      end = new Coord(start);
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(editor, new RectSolid(start, end));
      start.translate(Cardinal.UP, 3);
      end.translate(Cardinal.UP, 3);
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(editor, new RectSolid(start, end));
      start.translate(o.reverse());
      start.translate(Cardinal.UP);
      end.translate(o.reverse());
      end.translate(Cardinal.UP);
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(editor, new RectSolid(start, end));

      for (Cardinal r : o.orthogonals()) {
        start = new Coord(origin);
        start.translate(o, 4);
        start.translate(r, 2);
        end = new Coord(start);
        end.translate(Cardinal.UP, 3);
        RectSolid.newRect(start, end).fill(editor, pillar);
      }

      start = new Coord(origin);
      start.translate(o, 5);
      end = new Coord(start);
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP, 2);
      RectSolid.newRect(start, end).fill(editor, panel);

      start = new Coord(origin);
      start.translate(dir.reverse(), 3);
      start.translate(o, 2);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, pillar);
      cursor = new Coord(end);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir.reverse(), 2);
      cursor.translate(o);
      cursor.translate(Cardinal.UP, 4);
      wall.stroke(editor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);

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
    stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    wall.stroke(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    BlockType.ENCHANTING_TABLE.getBrush().stroke(editor, cursor);

    List<Coord> chestLocations = chooseRandomLocations(rand, 1, chests);
    editor.getTreasureChestEditor().createChests(settings.getDifficulty(origin), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.ENCHANTING));

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
