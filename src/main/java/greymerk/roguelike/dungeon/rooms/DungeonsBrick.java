package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.Spawner;

import static greymerk.roguelike.treasure.Treasure.COMMON_TREASURES;
import static greymerk.roguelike.treasure.Treasure.createChests;
import static greymerk.roguelike.worldgen.Cardinal.UP;
import static greymerk.roguelike.worldgen.Cardinal.directions;
import static greymerk.roguelike.worldgen.spawners.Spawner.COMMON_MOBS;

public class DungeonsBrick extends DungeonBase {


  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    ITheme theme = settings.getTheme();

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory blocks = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    MetaBlock air = BlockType.get(BlockType.AIR);

    // fill air inside
    RectSolid.fill(editor, rand, new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3), air);
    RectSolid.fill(editor, rand, new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 4, z + 1), air);

    // shell
    RectHollow.fill(editor, rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4), blocks, false, true);

    RectSolid.fill(editor, rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4), theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;


    cursor = new Coord(x, y, z);
    cursor.add(UP, 5);
    air.set(editor, cursor);
    cursor.add(UP, 1);
    blocks.set(editor, rand, cursor);

    // Chests
    List<Coord> potentialChestLocations = new ArrayList<>();

    for (Cardinal dir : directions) {

      // top
      cursor = new Coord(x, y, z);
      cursor.add(dir, 1);
      cursor.add(UP, 5);
      stair.setOrientation(dir.reverse(), true);
      stair.set(editor, rand, cursor, false, true);
      cursor.add(dir.left(), 1);
      blocks.set(editor, rand, cursor, false, true);

      cursor = new Coord(x, y, z);
      cursor.add(dir, 2);
      cursor.add(UP, 4);
      air.set(editor, cursor);
      cursor.add(UP, 1);
      blocks.set(editor, rand, cursor, false, true);

      // pillar
      cursor = new Coord(x, y, z);
      cursor.add(dir, 3);
      cursor.add(dir.left(), 3);
      start = new Coord(cursor);
      cursor.add(UP, 2);
      end = new Coord(cursor);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);
      cursor.add(UP, 1);
      blocks.set(editor, rand, cursor);

      // pillar stairs
      for (Cardinal orth : dir.orthogonal()) {
        cursor = new Coord(x, y, z);
        cursor.add(dir, 3);
        cursor.add(orth, 2);
        cursor.add(UP, 3);
        stair.setOrientation(orth.reverse(), true);
        stair.set(editor, rand, cursor);
      }

      // layer above pillars
      cursor = new Coord(x, y, z);
      cursor.add(dir, 2);
      cursor.add(dir.left(), 2);
      cursor.add(UP, 4);
      blocks.set(editor, rand, cursor, false, true);

      for (Cardinal orth : dir.orthogonal()) {
        cursor = new Coord(x, y, z);
        cursor.add(UP, 4);
        cursor.add(dir, 2);
        cursor.add(orth, 1);
        stair.setOrientation(orth.reverse(), true);
        stair.set(editor, rand, cursor, false, true);
      }

      cursor = new Coord(x, y, z);
      cursor.add(dir, 1);
      cursor.add(dir.left(), 1);
      cursor.add(UP, 5);
      blocks.set(editor, rand, cursor, false, true);

      for (Cardinal orth : dir.orthogonal()) {
        cursor = new Coord(x, y, z);
        cursor.add(dir, 3);
        cursor.add(orth, 2);
        potentialChestLocations.add(cursor);
      }
    }

    List<Coord> chestLocations = chooseRandomLocations(rand, 1, potentialChestLocations);
    createChests(editor, rand, Dungeon.getLevel(origin.getY()), chestLocations, false, COMMON_TREASURES);
    Spawner.generate(editor, rand, settings, new Coord(x, y, z), COMMON_MOBS);
    return true;
  }

  public int getSize() {
    return 6;
  }
}
