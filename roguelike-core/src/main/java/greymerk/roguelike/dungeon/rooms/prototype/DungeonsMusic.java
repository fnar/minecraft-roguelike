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

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.carpet;

public class DungeonsMusic extends DungeonBase {

  public DungeonsMusic(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {
    Random rand = worldEditor.getRandom();

    ThemeBase theme = levelSettings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    StairsBlock stair = theme.getSecondary().getStair();
    BlockBrush panel = theme.getSecondary().getWall();
    BlockBrush pillar = theme.getSecondary().getPillar();
    BlockBrush floor = theme.getSecondary().getFloor();

    Coord start;
    Coord end;
    Coord cursor;

    List<Coord> chests = new ArrayList<>();

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-6, -1, -6));
    end.translate(new Coord(6, 5, 6));
    RectHollow.newRect(start, end).fill(worldEditor, wall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-6, 4, -6));
    end.translate(new Coord(6, 5, 6));
    RectSolid.newRect(start, end).fill(worldEditor, panel);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, 4, -3));
    end.translate(new Coord(3, 4, 3));
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    RectSolid.newRect(start, end).fill(worldEditor, floor);

    for (int i = 2; i >= 0; --i) {
      start = new Coord(origin);
      end = new Coord(origin);
      start.translate(new Coord(-i - 1, 0, -i - 1));
      end.translate(new Coord(i + 1, 0, i + 1));
      BlockBrush carpet = carpet().setColor(DyeColor.chooseRandom(rand));
      RectSolid.newRect(start, end).fill(worldEditor, carpet);
    }

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      cursor = new Coord(origin);
      cursor.translate(dir, 5);
      cursor.translate(Cardinal.UP, 3);
      panel.stroke(worldEditor, cursor);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      cursor = new Coord(origin);
      cursor.translate(dir, 5);
      cursor.translate(dir.antiClockwise(), 5);
      pillar(worldEditor, levelSettings, cursor);

      start = new Coord(origin);
      start.translate(Cardinal.UP, 4);
      start.translate(dir, 3);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      for (Cardinal o : dir.orthogonals()) {
        cursor = new Coord(origin);
        cursor.translate(dir, 5);
        cursor.translate(o, 2);
        pillar(worldEditor, levelSettings, cursor);

        cursor = new Coord(origin);
        cursor.translate(dir, 4);
        cursor.translate(Cardinal.UP, 3);
        cursor.translate(o);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

        cursor = new Coord(origin);
        cursor.translate(dir, 5);
        cursor.translate(o, 3);
        cursor.translate(Cardinal.UP);
        chests.add(new Coord(cursor));
        cursor.translate(o);
        chests.add(new Coord(cursor));

        cursor = new Coord(origin);
        cursor.translate(dir, 5);
        cursor.translate(o, 3);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(Cardinal.UP, 2);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        stair.setUpsideDown(true).setFacing(o).stroke(worldEditor, cursor);
        cursor.translate(Cardinal.UP);
        panel.stroke(worldEditor, cursor);
        cursor.translate(o);
        panel.stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      }
    }

    BlockType.JUKEBOX.getBrush().stroke(worldEditor, origin);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 4);
    BlockType.GLOWSTONE.getBrush().stroke(worldEditor, cursor);

    List<Coord> chestLocations = chooseRandomLocations(1, chests);
    worldEditor.getTreasureChestEditor().createChests(levelSettings.getDifficulty(origin), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.MUSIC));

    return this;
  }

  private void pillar(WorldEditor editor, LevelSettings settings, Coord origin) {
    ThemeBase theme = settings.getTheme();
    StairsBlock stair = theme.getSecondary().getStair();
    BlockBrush panel = theme.getSecondary().getWall();
    BlockBrush pillar = theme.getSecondary().getPillar();

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(start);
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, pillar);
    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(end);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor, true, false);
      cursor.translate(Cardinal.UP);
      panel.stroke(editor, cursor);
    }
  }

  public int getSize() {
    return 7;
  }

}
