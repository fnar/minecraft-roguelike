package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.spawners.MobType.COMMON_MOBS;


public class DungeonEniko extends DungeonBase {

  public DungeonEniko(RoomSetting roomSetting) {
    super(roomSetting);
  }

  private static void pillar(WorldEditor editor, ThemeBase theme, Coord origin) {
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush pillar = theme.getPrimary().getPillar();
    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(start);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, start, end, pillar, true, true);
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(end);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor, true, false);
    }
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    ThemeBase theme = settings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush walls = theme.getPrimary().getWall();
    BlockBrush floor = theme.getPrimary().getFloor();
    Coord start;
    Coord end;
    Coord cursor;
    List<Coord> chests = new ArrayList<>();

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(6, -1, 6));
    end.translate(new Coord(-6, 4, -6));
    RectHollow.fill(editor, start, end, walls, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(6, 4, 6));
    end.translate(new Coord(-6, 5, -6));
    RectSolid.fill(editor, start, end, theme.getSecondary().getWall(), false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(3, 4, 3));
    end.translate(new Coord(-3, 4, -3));
    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR, true, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    RectSolid.fill(editor, start, end, floor, true, true);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir, 5);
      for (Cardinal o : dir.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(o, 2);
        pillar(editor, theme, c);

        c = new Coord(cursor);
        c.translate(o, 3);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, c);
        c.translate(o);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, c);
        c.translate(Cardinal.UP);
        chests.add(new Coord(c));
        c.translate(o.reverse());
        chests.add(new Coord(c));
      }

      cursor.translate(dir.antiClockwise(), 5);
      pillar(editor, theme, cursor);

      if (entrances.contains(dir)) {
        start = new Coord(origin);
        start.translate(Cardinal.DOWN);
        end = new Coord(start);
        start.translate(dir.antiClockwise());
        end.translate(dir.clockwise());
        end.translate(dir, 6);
        RectSolid.fill(editor, start, end, floor, true, true);
      }
    }

    generateSpawner(editor, origin, settings.getDifficulty(origin), settings.getSpawners(), COMMON_MOBS);
    List<Coord> chestLocations = chooseRandomLocations(editor.getRandom(), 1, chests);
    editor.getTreasureChestEditor().createChests(settings.getDifficulty(origin), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(editor.getRandom(), ChestType.COMMON_TREASURES)));

    return this;
  }

  public int getSize() {
    return 7;
  }
}
