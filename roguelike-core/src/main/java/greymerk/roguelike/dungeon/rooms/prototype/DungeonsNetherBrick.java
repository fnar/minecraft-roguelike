package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;

public class DungeonsNetherBrick extends BaseRoom {

  public DungeonsNetherBrick(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.size = 6;
    this.height = 4;
  }

  public BaseRoom generate(Coord at, List<Direction> entrances) {
    generateWalls(at);
    generateFloor(at);
    generateCeiling(at);
    generateTrappableChest(generateChestLocation(at), getEntrance(entrances));
    generateSpawners(at);
    generateDoorways(at, entrances);

    return this;
  }

  private void generateWalls(Coord at) {
    IShape wallsRect = at.newHollowRect(getWallDist()).withHeight(getCeilingHeight() + 1 + getDepth() + 1).down(getDepth());
    walls().fill(worldEditor, wallsRect, false, true);
  }

  private void generateFloor(Coord at) {
    IShape floorRect = at.newRect(getWallDist()).down(depth);
    floors().fill(worldEditor, floorRect);
  }

  private void generateCeiling(Coord at) {
    IShape ceilingRect = at.newRect(getWallDist()).up(getCeilingHeight());
    ceilingBrush().fill(worldEditor, ceilingRect);
  }

  private BlockWeightedRandom ceilingBrush() {
    BlockWeightedRandom ceilingBrush = new BlockWeightedRandom();
    ceilingBrush.addBlock(BlockType.FENCE_NETHER_BRICK.getBrush(), 10);
    ceilingBrush.addBlock(SingleBlockBrush.AIR, 5);
    return ceilingBrush;
  }

  @Override
  protected Coord generateChestLocation(Coord at) {
    Direction dir0 = Direction.randomCardinal(random());
    Direction dir1 = dir0.orthogonals()[random().nextBoolean() ? 0 : 1];
    return at.copy()
        .translate(dir0, 3)
        .translate(dir1, 2);
  }

  private void generateSpawners(Coord at) {
    int dist = getWallDist();
    for (Direction card : Direction.cardinals()) {
      Coord spawnerCoord = at.copy().translate(card, dist).translate(card.left(), dist).up(random().nextInt(2));
      generateSpawner(spawnerCoord);
    }
  }

}
