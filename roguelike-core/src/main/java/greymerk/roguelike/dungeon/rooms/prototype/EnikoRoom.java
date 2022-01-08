package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.spawners.MobType.COMMON_MOBS;


public class EnikoRoom extends BaseRoom {

  public EnikoRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  private static void pillar(WorldEditor editor, Theme theme, Coord origin) {
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush pillar = theme.getPrimary().getPillar();
    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    end = start.copy();
    end.up(3);
    RectSolid.newRect(start, end).fill(editor, pillar);
    for (Direction dir : Direction.CARDINAL) {
      cursor = end.copy();
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor, true, false);
    }
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    Theme theme = levelSettings.getTheme();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush walls = theme.getPrimary().getWall();
    BlockBrush floor = theme.getPrimary().getFloor();
    Coord start;
    Coord end;
    Coord cursor;
    List<Coord> chests = new ArrayList<>();

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(6, -1, 6));
    end.translate(new Coord(-6, 4, -6));
    RectHollow.newRect(start, end).fill(worldEditor, walls, false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(6, 4, 6));
    end.translate(new Coord(-6, 5, -6));
    RectSolid.newRect(start, end).fill(worldEditor, theme.getSecondary().getWall(), false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(3, 4, 3));
    end.translate(new Coord(-3, 4, -3));
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    RectSolid.newRect(start, end).fill(worldEditor, floor);

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
      cursor.translate(dir, 5);
      for (Direction o : dir.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(o, 2);
        pillar(worldEditor, theme, c);

        c = cursor.copy();
        c.translate(o, 3);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, c);
        c.translate(o);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, c);
        c.up();
        chests.add(c.copy());
        c.translate(o.reverse());
        chests.add(c.copy());
      }

      cursor.translate(dir.antiClockwise(), 5);
      pillar(worldEditor, theme, cursor);

      if (entrances.contains(dir)) {
        start = origin.copy();
        start.down();
        end = start.copy();
        start.translate(dir.antiClockwise());
        end.translate(dir.clockwise());
        end.translate(dir, 6);
        RectSolid.newRect(start, end).fill(worldEditor, floor);
      }
    }

    generateSpawner(origin, COMMON_MOBS);
    List<Coord> chestLocations = chooseRandomLocations(1, chests);
    ChestType chestType = getRoomSetting().getChestType().orElse(ChestType.chooseRandomAmong(worldEditor.getRandom(), ChestType.COMMON_TREASURES));
    worldEditor.getTreasureChestEditor().createChests(chestLocations, false, levelSettings.getDifficulty(origin), entrances.get(0).reverse(), chestType);

    return this;
  }

  public int getSize() {
    return 7;
  }
}
