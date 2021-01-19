package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

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
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.carpet;

public class DungeonsMusic extends DungeonBase {

  public DungeonsMusic(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public DungeonBase generate(Coord origin, List<Direction> entrances) {
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

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-6, -1, -6));
    end.translate(new Coord(6, 5, 6));
    RectHollow.newRect(start, end).fill(worldEditor, wall, false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-6, 4, -6));
    end.translate(new Coord(6, 5, 6));
    RectSolid.newRect(start, end).fill(worldEditor, panel);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, 4, -3));
    end.translate(new Coord(3, 4, 3));
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    RectSolid.newRect(start, end).fill(worldEditor, floor);

    for (int i = 2; i >= 0; --i) {
      start = origin.copy();
      end = origin.copy();
      start.translate(new Coord(-i - 1, 0, -i - 1));
      end.translate(new Coord(i + 1, 0, i + 1));
      BlockBrush carpet = carpet().setColor(DyeColor.chooseRandom(rand));
      RectSolid.newRect(start, end).fill(worldEditor, carpet);
    }

    for (Direction dir : Direction.CARDINAL) {

      cursor = origin.copy();
      cursor.translate(dir, 5);
      cursor.up(3);
      panel.stroke(worldEditor, cursor);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 5);
      cursor.translate(dir.antiClockwise(), 5);
      pillar(worldEditor, levelSettings, cursor);

      start = origin.copy();
      start.up(4);
      start.translate(dir, 3);
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      for (Direction o : dir.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(dir, 5);
        cursor.translate(o, 2);
        pillar(worldEditor, levelSettings, cursor);

        cursor = origin.copy();
        cursor.translate(dir, 4);
        cursor.up(3);
        cursor.translate(o);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

        cursor = origin.copy();
        cursor.translate(dir, 5);
        cursor.translate(o, 3);
        cursor.up();
        chests.add(cursor.copy());
        cursor.translate(o);
        chests.add(cursor.copy());

        cursor = origin.copy();
        cursor.translate(dir, 5);
        cursor.translate(o, 3);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o);
        stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.up(2);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        stair.setUpsideDown(true).setFacing(o).stroke(worldEditor, cursor);
        cursor.up();
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

    cursor = origin.copy();
    cursor.up(4);
    BlockType.GLOWSTONE.getBrush().stroke(worldEditor, cursor);

    List<Coord> chestLocations = chooseRandomLocations(1, chests);
    worldEditor.getTreasureChestEditor().createChests(chestLocations, false, levelSettings.getDifficulty(origin), getRoomSetting().getChestType().orElse(ChestType.MUSIC));

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

    start = origin.copy();
    end = start.copy();
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, pillar);
    for (Direction dir : Direction.CARDINAL) {
      cursor = end.copy();
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor, true, false);
      cursor.up();
      panel.stroke(editor, cursor);
    }
  }

  public int getSize() {
    return 7;
  }

}
