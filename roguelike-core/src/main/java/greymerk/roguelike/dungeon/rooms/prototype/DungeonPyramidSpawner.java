package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

import java.util.ArrayList;
import java.util.List;

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
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

import static greymerk.roguelike.worldgen.spawners.MobType.COMMON_MOBS;

public class DungeonPyramidSpawner extends DungeonBase {

  public DungeonPyramidSpawner(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    ThemeBase theme = settings.getTheme();

    BlockBrush blocks = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();

    // fill air inside
    RectSolid.fill(editor, new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3), SingleBlockBrush.AIR);


    // shell
    RectHollow.fill(editor, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4), blocks, false, true);
    RectSolid.fill(editor, new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3), blocks, false, true);
    RectSolid.fill(editor, new Coord(x - 2, y + 4, z - 2), new Coord(x + 2, y + 4, z + 2), SingleBlockBrush.AIR);

    RectSolid.fill(editor, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4), theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 5);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    blocks.stroke(editor, cursor);

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 5);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    SingleBlockBrush.AIR.stroke(editor, cursor);

    // Chests
    List<Coord> space = new ArrayList<>();

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      // pillar
      cursor = new Coord(x, y, z);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      start = new Coord(cursor);
      cursor.translate(Cardinal.UP, 3);
      end = new Coord(cursor);
      RectSolid.fill(editor, start, end, pillar);
      cursor.translate(Cardinal.UP, 1);
      blocks.stroke(editor, cursor);

      cursor = new Coord(x, y, z);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 2);
      blocks.stroke(editor, cursor);
      cursor.translate(dir.antiClockwise(), 2);
      blocks.stroke(editor, cursor);

      cursor = new Coord(x, y, z);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(dir.antiClockwise());
      SingleBlockBrush.AIR.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      SingleBlockBrush.AIR.stroke(editor, cursor);

      for (Cardinal orthogonals : dir.orthogonals()) {

        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 3);
        cursor.translate(orthogonals);
        cursor.translate(dir, 3);
        blocks.stroke(editor, cursor);

        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 4);
        cursor.translate(dir, 2);
        blocks.stroke(editor, cursor);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 2);
        space.add(cursor);
      }
    }

    List<Coord> chestLocations = chooseRandomLocations(editor.getRandom(), 1, space);
    editor.getTreasureChestEditor().createChests(Dungeon.getLevel(origin.getY()), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(editor.getRandom(), ChestType.COMMON_TREASURES)));
    final Coord cursor1 = new Coord(x, y, z);
    SpawnerSettings spawners = settings.getSpawners();
    generateSpawner(editor, cursor1, settings.getDifficulty(cursor1), spawners, COMMON_MOBS);
    return this;
  }

  public int getSize() {
    return 4;
  }
}
