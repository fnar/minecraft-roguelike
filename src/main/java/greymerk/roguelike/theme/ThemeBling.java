package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeBling extends ThemeBase {

  public ThemeBling() {
    primary = new BlockSet(createWalls(),
        new MetaStair(StairType.QUARTZ),
        BlockType.get(BlockType.LAPIS_BLOCK));
    secondary = primary;
  }

  private BlockWeightedRandom createWalls() {
    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.IRON_BLOCK), 10);
    walls.addBlock(BlockType.get(BlockType.GOLD_BLOCK), 3);
    walls.addBlock(BlockType.get(BlockType.EMERALD_BLOCK), 10);
    walls.addBlock(BlockType.get(BlockType.DIAMOND_BLOCK), 20);
    return walls;
  }
}
