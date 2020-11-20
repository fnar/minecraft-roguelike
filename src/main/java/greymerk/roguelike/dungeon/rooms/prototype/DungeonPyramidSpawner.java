package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

import static greymerk.roguelike.worldgen.spawners.MobType.COMMON_MOBS;

public class DungeonPyramidSpawner extends DungeonBase {

  public DungeonPyramidSpawner(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    ITheme theme = settings.getTheme();

    IBlockFactory blocks = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    MetaBlock air = BlockType.get(BlockType.AIR);

    // fill air inside
    RectSolid.fill(editor, rand, new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3), air);


    // shell
    RectHollow.fill(editor, rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4), blocks, false, true);
    RectSolid.fill(editor, rand, new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3), blocks, false, true);
    RectSolid.fill(editor, rand, new Coord(x - 2, y + 4, z - 2), new Coord(x + 2, y + 4, z + 2), air);

    RectSolid.fill(editor, rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4), theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 5);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP, 1);
    blocks.set(editor, rand, cursor);

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 5);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    air.set(editor, cursor);

    // Chests
    List<Coord> space = new ArrayList<>();

    for (Cardinal dir : Cardinal.directions) {

      // pillar
      cursor = new Coord(x, y, z);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      start = new Coord(cursor);
      cursor.translate(Cardinal.UP, 3);
      end = new Coord(cursor);
      RectSolid.fill(editor, rand, start, end, pillar);
      cursor.translate(Cardinal.UP, 1);
      blocks.set(editor, rand, cursor);

      cursor = new Coord(x, y, z);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 2);
      blocks.set(editor, rand, cursor);
      cursor.translate(dir.antiClockwise(), 2);
      blocks.set(editor, rand, cursor);

      cursor = new Coord(x, y, z);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(dir.antiClockwise());
      air.set(editor, cursor);
      cursor.translate(Cardinal.UP);
      air.set(editor, cursor);

      for (Cardinal orth : dir.orthogonal()) {

        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 3);
        cursor.translate(orth);
        cursor.translate(dir, 3);
        blocks.set(editor, rand, cursor);

        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 4);
        cursor.translate(dir, 2);
        blocks.set(editor, rand, cursor);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 3);
        cursor.translate(orth, 2);
        space.add(cursor);
      }
    }

    List<Coord> chestLocations = chooseRandomLocations(rand, 1, space);
    editor.treasureChestEditor.createChests(Dungeon.getLevel(origin.getY()), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(rand, ChestType.COMMON_TREASURES)));
    final Coord cursor1 = new Coord(x, y, z);
    SpawnerSettings spawners = settings.getSpawners();
    generateSpawner(editor, rand, cursor1, settings.getDifficulty(cursor1), spawners, COMMON_MOBS);
    return this;
  }

  public int getSize() {
    return 4;
  }
}
