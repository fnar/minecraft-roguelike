package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockBrush;

import static java.util.Optional.ofNullable;

public class BlockSet {

  private BlockBrush floor = BlockType.STONE_BRICK.getBrush();
  private BlockBrush walls = BlockType.STONE_BRICK.getBrush();
  private StairsBlock stair = StairsBlock.stoneBrick();
  private BlockBrush pillar = BlockType.STONE_BRICK.getBrush();
  private DoorBlock door = DoorBlock.oak();
  private BlockBrush lightBlock = BlockType.GLOWSTONE.getBrush();
  private BlockBrush liquid = BlockType.WATER_FLOWING.getBrush();

  private BlockSet() {
  }

  public BlockSet(
      BlockBrush floor,
      BlockBrush walls,
      StairsBlock stair,
      BlockBrush pillar,
      DoorBlock door,
      BlockBrush lightBlock,
      BlockBrush liquid
  ) {
    this.floor = floor;
    this.walls = walls;
    this.stair = stair;
    this.pillar = pillar;
    this.door = door;
    this.lightBlock = lightBlock;
    this.liquid = liquid;
  }

  static BlockSet inherit(
      BlockSet parentBlockSet,
      BlockSet childBlockSet
  ) {
    if (parentBlockSet == null && childBlockSet == null) {
      return new BlockSet();
    }
    if (parentBlockSet == null) {
      return childBlockSet;
    }
    if (childBlockSet == null) {
      return parentBlockSet;
    }
    return new BlockSet(
        ofNullable(childBlockSet.getFloor()).orElse(parentBlockSet.getFloor()),
        ofNullable(childBlockSet.getWall()).orElse(parentBlockSet.getWall()),
        ofNullable(childBlockSet.getStair()).orElse(parentBlockSet.getStair()),
        ofNullable(childBlockSet.getPillar()).orElse(parentBlockSet.getPillar()),
        ofNullable(childBlockSet.getDoor()).orElse(parentBlockSet.getDoor()),
        ofNullable(childBlockSet.getLightBlock()).orElse(parentBlockSet.getLightBlock()),
        ofNullable(childBlockSet.getLiquid()).orElse(parentBlockSet.getLiquid()));
  }

  public BlockBrush getWall() {
    return walls.copy();
  }

  public StairsBlock getStair() {
    return stair.copy();
  }

  public BlockBrush getPillar() {
    return ofNullable(pillar).orElse(getWall()).copy();
  }

  public BlockBrush getFloor() {
    return ofNullable(floor).orElse(getWall()).copy();
  }

  public DoorBlock getDoor() {
    return door.copy();
  }

  public BlockBrush getLightBlock() {
    return lightBlock.copy();
  }

  public BlockBrush getLiquid() {
    return liquid.copy();
  }
}
