package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
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

import static greymerk.roguelike.worldgen.Cardinal.DIRECTIONS;
import static greymerk.roguelike.worldgen.Cardinal.UP;

public class BrickRoom extends DungeonBase {

  public BrickRoom(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    ThemeBase theme = settings.getTheme();

    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush blocks = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();

    // fill air inside
    RectSolid.newRect(new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3)).fill(editor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 4, z + 1)).fill(editor, SingleBlockBrush.AIR);

    // shell
    RectHollow.newRect(new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4)).fill(editor, blocks, false, true);

    RectSolid.newRect(new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4)).fill(editor, theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;


    cursor = new Coord(x, y, z);
    cursor.translate(UP, 5);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(UP, 1);
    blocks.stroke(editor, cursor);

    // Chests
    List<Coord> potentialChestLocations = new ArrayList<>();

    for (Cardinal dir : DIRECTIONS) {

      // top
      cursor = new Coord(x, y, z);
      cursor.translate(dir, 1);
      cursor.translate(UP, 5);
      stair.setUpsideDown(true).setFacing(dir.reverse());
      stair.stroke(editor, cursor, false, true);
      cursor.translate(dir.antiClockwise(), 1);
      blocks.stroke(editor, cursor, false, true);

      cursor = new Coord(x, y, z);
      cursor.translate(dir, 2);
      cursor.translate(UP, 4);
      SingleBlockBrush.AIR.stroke(editor, cursor);
      cursor.translate(UP, 1);
      blocks.stroke(editor, cursor, false, true);

      // pillar
      cursor = new Coord(x, y, z);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      start = new Coord(cursor);
      cursor.translate(UP, 2);
      end = new Coord(cursor);
      RectSolid.newRect(start, end).fill(editor, pillar);
      cursor.translate(UP, 1);
      blocks.stroke(editor, cursor);

      // pillar stairs
      for (Cardinal orthogonals : dir.orthogonals()) {
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 2);
        cursor.translate(UP, 3);
        stair.setUpsideDown(true).setFacing(orthogonals.reverse());
        stair.stroke(editor, cursor);
      }

      // layer above pillars
      cursor = new Coord(x, y, z);
      cursor.translate(dir, 2);
      cursor.translate(dir.antiClockwise(), 2);
      cursor.translate(UP, 4);
      blocks.stroke(editor, cursor, false, true);

      for (Cardinal orthogonals : dir.orthogonals()) {
        cursor = new Coord(x, y, z);
        cursor.translate(UP, 4);
        cursor.translate(dir, 2);
        cursor.translate(orthogonals, 1);
        stair.setUpsideDown(true).setFacing(orthogonals.reverse());
        stair.stroke(editor, cursor, false, true);
      }

      cursor = new Coord(x, y, z);
      cursor.translate(dir, 1);
      cursor.translate(dir.antiClockwise(), 1);
      cursor.translate(UP, 5);
      blocks.stroke(editor, cursor, false, true);

      for (Cardinal orthogonals : dir.orthogonals()) {
        cursor = new Coord(x, y, z);
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 2);
        potentialChestLocations.add(cursor);
      }
    }

    Random random = editor.getRandom();
    List<Coord> chestLocations = chooseRandomLocations(random, 1, potentialChestLocations);
    int level = Dungeon.getLevel(origin.getY());
    editor.getTreasureChestEditor().createChests(level, chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(random, ChestType.COMMON_TREASURES)));

    Coord spawnerLocation = new Coord(x, y, z);
    generateSpawner(editor, spawnerLocation, Dungeon.getLevel(origin.getY()), settings.getSpawners());
    return this;
  }

  public int getSize() {
    return 6;
  }
}
