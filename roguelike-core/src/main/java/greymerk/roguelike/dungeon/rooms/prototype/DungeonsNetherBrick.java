package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;

public class DungeonsNetherBrick extends BaseRoom {

  public DungeonsNetherBrick(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Random random = worldEditor.getRandom();

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    Theme theme = levelSettings.getTheme();

    int height = 3;
    int length = 2 + random.nextInt(3);
    int width = 2 + random.nextInt(3);

    BlockBrush walls = theme.getPrimary().getWall();
    RectHollow.newRect(new Coord(x - length - 1, y - 1, z - width - 1), new Coord(x + length + 1, y + height + 1, z + width + 1)).fill(worldEditor, walls, false, true);


    BlockBrush floor = theme.getPrimary().getFloor();
    RectSolid.newRect(new Coord(x - length - 1, y - 1, z - width - 1), new Coord(x + length + 1, y - 1, z + width + 1)).fill(worldEditor, floor);

    // liquid crap under the floor
    BlockWeightedRandom subFloor = new BlockWeightedRandom();
    subFloor.addBlock(theme.getPrimary().getLiquid(), 8);
    subFloor.addBlock(BlockType.OBSIDIAN.getBrush(), 3);
    RectSolid.newRect(new Coord(x - length, y - 5, z - width), new Coord(x + length, y - 2, z + width)).fill(worldEditor, subFloor);

    BlockWeightedRandom ceiling = new BlockWeightedRandom();
    ceiling.addBlock(BlockType.FENCE_NETHER_BRICK.getBrush(), 10);
    ceiling.addBlock(SingleBlockBrush.AIR, 5);
    RectSolid.newRect(new Coord(x - length, y + height, z - width), new Coord(x + length, y + height, z + width)).fill(worldEditor, ceiling);

    List<Coord> chestLocations = chooseRandomLocations(1, new RectSolid(new Coord(x - length, y, z - width), new Coord(x + length, y, z + width)).get());
    worldEditor.getTreasureChestEditor().createChests(chestLocations, false, Dungeon.getLevel(y), entrances.get(0), getRoomSetting().getChestType().orElse(ChestType.chooseRandomAmong(random, ChestType.COMMON_TREASURES)));

    final Coord cursor = new Coord(x - length - 1, y + random.nextInt(2), z - width - 1);
    generateSpawner(cursor, MobType.COMMON_MOBS);
    final Coord cursor1 = new Coord(x - length - 1, y + random.nextInt(2), z + width + 1);
    generateSpawner(cursor1, MobType.COMMON_MOBS);
    final Coord cursor2 = new Coord(x + length + 1, y + random.nextInt(2), z - width - 1);
    generateSpawner(cursor2, MobType.COMMON_MOBS);
    final Coord cursor3 = new Coord(x + length + 1, y + random.nextInt(2), z + width + 1);
    generateSpawner(cursor3, MobType.COMMON_MOBS);

    return this;
  }

  public int getSize() {
    return 6;
  }
}
