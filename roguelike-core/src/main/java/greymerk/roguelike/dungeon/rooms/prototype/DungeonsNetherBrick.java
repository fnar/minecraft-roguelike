package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class DungeonsNetherBrick extends DungeonBase {

  public DungeonsNetherBrick(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {
    Random random = editor.getRandom();

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ThemeBase theme = settings.getTheme();

    int height = 3;
    int length = 2 + random.nextInt(3);
    int width = 2 + random.nextInt(3);

    BlockBrush walls = theme.getPrimary().getWall();
    RectHollow.newRect(new Coord(x - length - 1, y - 1, z - width - 1), new Coord(x + length + 1, y + height + 1, z + width + 1)).fill(editor, walls, false, true);


    BlockBrush floor = theme.getPrimary().getFloor();
    RectSolid.newRect(new Coord(x - length - 1, y - 1, z - width - 1), new Coord(x + length + 1, y - 1, z + width + 1)).fill(editor, floor);

    // liquid crap under the floor
    BlockWeightedRandom subFloor = new BlockWeightedRandom();
    subFloor.addBlock(theme.getPrimary().getLiquid(), 8);
    subFloor.addBlock(BlockType.OBSIDIAN.getBrush(), 3);
    RectSolid.newRect(new Coord(x - length, y - 5, z - width), new Coord(x + length, y - 2, z + width)).fill(editor, subFloor);

    BlockWeightedRandom ceiling = new BlockWeightedRandom();
    ceiling.addBlock(BlockType.FENCE_NETHER_BRICK.getBrush(), 10);
    ceiling.addBlock(SingleBlockBrush.AIR, 5);
    RectSolid.newRect(new Coord(x - length, y + height, z - width), new Coord(x + length, y + height, z + width)).fill(editor, ceiling);

    List<Coord> chestLocations = chooseRandomLocations(random, 1, new RectSolid(new Coord(x - length, y, z - width), new Coord(x + length, y, z + width)).get());
    editor.getTreasureChestEditor().createChests(Dungeon.getLevel(y), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(random, ChestType.COMMON_TREASURES)));

    final Coord cursor = new Coord(x - length - 1, y + random.nextInt(2), z - width - 1);
    SpawnerSettings spawners3 = settings.getSpawners();
    generateSpawner(editor, cursor, settings.getDifficulty(cursor), spawners3, MobType.COMMON_MOBS);
    final Coord cursor1 = new Coord(x - length - 1, y + random.nextInt(2), z + width + 1);
    SpawnerSettings spawners2 = settings.getSpawners();
    generateSpawner(editor, cursor1, settings.getDifficulty(cursor1), spawners2, MobType.COMMON_MOBS);
    final Coord cursor2 = new Coord(x + length + 1, y + random.nextInt(2), z - width - 1);
    SpawnerSettings spawners1 = settings.getSpawners();
    generateSpawner(editor, cursor2, settings.getDifficulty(cursor2), spawners1, MobType.COMMON_MOBS);
    final Coord cursor3 = new Coord(x + length + 1, y + random.nextInt(2), z + width + 1);
    SpawnerSettings spawners = settings.getSpawners();
    generateSpawner(editor, cursor3, settings.getDifficulty(cursor3), spawners, MobType.COMMON_MOBS);

    return this;
  }

  public int getSize() {
    return 6;
  }
}
