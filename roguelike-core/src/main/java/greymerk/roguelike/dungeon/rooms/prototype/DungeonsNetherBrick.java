package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsNetherBrick extends BaseRoom {

  public DungeonsNetherBrick(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Random random = random();

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    int length = 2 + random.nextInt(3);
    int width = 2 + random.nextInt(3);

    generateWalls(x, y, z, length, width);
    generateFloor(origin, x, y, z, length, width);
    generateCeiling(x, y, z, length, width);
    generateTrappableChest(generateChestLocation(origin), getEntrance(entrances));
    generateSpawners(origin, random, length, width);

    return this;
  }

  private int getHeight() {
    return 3;
  }

  private void generateWalls(int x, int y, int z, int length, int width) {
    RectHollow.newRect(new Coord(x - length - 1, y - 1, z - width - 1), new Coord(x + length + 1, y + getHeight() + 1, z + width + 1)).fill(worldEditor, walls(), false, true);
  }

  private void generateFloor(Coord origin, int x, int y, int z, int length, int width) {
    BlockBrush floor = floors();
    RectSolid.newRect(new Coord(x - length - 1, y - 1, z - width - 1), new Coord(x + length + 1, y - 1, z + width + 1)).fill(worldEditor, floor);
    generateCrapUnderneath(origin, length, width);
  }

  private void generateCeiling(int x, int y, int z, int length, int width) {
    BlockWeightedRandom ceiling = new BlockWeightedRandom();
    ceiling.addBlock(BlockType.FENCE_NETHER_BRICK.getBrush(), 10);
    ceiling.addBlock(SingleBlockBrush.AIR, 5);
    RectSolid.newRect(new Coord(x - length, y + getHeight(), z - width), new Coord(x + length, y + getHeight(), z + width)).fill(worldEditor, ceiling);
  }

  private void generateCrapUnderneath(Coord origin, int length, int width) {
    // liquid crap under the floor
    BlockWeightedRandom subFloor = new BlockWeightedRandom();
    subFloor.addBlock(liquid(), 8);
    subFloor.addBlock(BlockType.OBSIDIAN.getBrush(), 3);
    RectSolid.newRect(
        origin.copy().translate(-length, -5, -width),
        origin.copy().translate(length, -2, width)
    ).fill(worldEditor, subFloor);
  }

  @Override
  protected Coord generateChestLocation(Coord origin) {
    Direction dir0 = Direction.randomCardinal(random());
    Direction dir1 = dir0.orthogonals()[random().nextBoolean() ? 0 : 1];
    return origin.copy()
        .translate(dir0, 3)
        .translate(dir1, 2);
  }

  private void generateSpawners(Coord origin, Random random, int length, int width) {
    int negX = -length - 1;
    int posX = length + 1;
    int negZ = -width - 1;
    int posZ = width + 1;
    generateSpawner(origin.copy().translate(negX, random.nextInt(2), negZ));
    generateSpawner(origin.copy().translate(negX, random.nextInt(2), posZ));
    generateSpawner(origin.copy().translate(posX, random.nextInt(2), negZ));
    generateSpawner(origin.copy().translate(posX, random.nextInt(2), posZ));
  }

  public int getSize() {
    return 6;
  }
}
