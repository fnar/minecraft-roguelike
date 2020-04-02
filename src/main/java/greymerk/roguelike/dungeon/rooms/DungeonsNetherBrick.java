package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

import static greymerk.roguelike.treasure.Treasure.COMMON_TREASURES;
import static greymerk.roguelike.treasure.Treasure.createChests;
import static greymerk.roguelike.worldgen.spawners.Spawner.COMMON_MOBS;

public class DungeonsNetherBrick extends DungeonBase {


  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ITheme theme = settings.getTheme();

    int height = 3;
    int length = 2 + rand.nextInt(3);
    int width = 2 + rand.nextInt(3);

    IBlockFactory walls = theme.getPrimary().getWall();
    RectHollow.fill(editor, rand, new Coord(x - length - 1, y - 1, z - width - 1), new Coord(x + length + 1, y + height + 1, z + width + 1), walls, false, true);


    IBlockFactory floor = theme.getPrimary().getFloor();
    RectSolid.fill(editor, rand, new Coord(x - length - 1, y - 1, z - width - 1), new Coord(x + length + 1, y - 1, z + width + 1), floor);

    // liquid crap under the floor
    BlockWeightedRandom subFloor = new BlockWeightedRandom();
    subFloor.addBlock(theme.getPrimary().getLiquid(), 8);
    subFloor.addBlock(BlockType.get(BlockType.OBSIDIAN), 3);
    RectSolid.fill(editor, rand, new Coord(x - length, y - 5, z - width), new Coord(x + length, y - 2, z + width), subFloor);

    BlockWeightedRandom ceiling = new BlockWeightedRandom();
    ceiling.addBlock(BlockType.get(BlockType.FENCE_NETHER_BRICK), 10);
    ceiling.addBlock(BlockType.get(BlockType.AIR), 5);
    RectSolid.fill(editor, rand, new Coord(x - length, y + height, z - width), new Coord(x + length, y + height, z + width), ceiling);

    List<Coord> chestLocations = chooseRandomLocations(rand, 1, new RectSolid(new Coord(x - length, y, z - width), new Coord(x + length, y, z + width)).get());
    createChests(editor, rand, Dungeon.getLevel(y), chestLocations, false, COMMON_TREASURES);

    final Coord cursor = new Coord(x - length - 1, y + rand.nextInt(2), z - width - 1);
    SpawnerSettings.generate(editor, rand, settings, cursor, settings.getDifficulty(cursor), COMMON_MOBS);
    final Coord cursor1 = new Coord(x - length - 1, y + rand.nextInt(2), z + width + 1);
    SpawnerSettings.generate(editor, rand, settings, cursor1, settings.getDifficulty(cursor1), COMMON_MOBS);
    final Coord cursor2 = new Coord(x + length + 1, y + rand.nextInt(2), z - width - 1);
    SpawnerSettings.generate(editor, rand, settings, cursor2, settings.getDifficulty(cursor2), COMMON_MOBS);
    final Coord cursor3 = new Coord(x + length + 1, y + rand.nextInt(2), z + width + 1);
    SpawnerSettings.generate(editor, rand, settings, cursor3, settings.getDifficulty(cursor3), COMMON_MOBS);

    return true;
  }

  public int getSize() {
    return 6;
  }
}
