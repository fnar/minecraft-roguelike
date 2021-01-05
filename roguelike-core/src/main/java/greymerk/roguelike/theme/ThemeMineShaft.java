package greymerk.roguelike.theme;

import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.Wood;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeMineShaft extends ThemeBase {

  public ThemeMineShaft() {

    BlockJumble floor = new BlockJumble();
    floor.addBlock(BlockType.GRAVEL.getBrush());
    floor.addBlock(BlockType.DIRT.getBrush());
    floor.addBlock(BlockType.COBBLESTONE.getBrush());

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.STONE_SMOOTH.getBrush(), 50);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 15);
    walls.addBlock(BlockType.ORE_REDSTONE.getBrush(), 1);

    this.primary = getPrimaryBlockSet(floor, walls);
    this.secondary = getSecondaryBlockSet(floor);
  }

  private BlockSet getPrimaryBlockSet(BlockJumble floor, BlockWeightedRandom walls) {
    StairsBlock stair = StairsBlock.cobble();
    BlockBrush pillar = BlockType.COBBLESTONE_WALL.getBrush();
    return new BlockSet(floor, walls, stair, pillar);
  }

  private BlockSet getSecondaryBlockSet(BlockJumble floor) {
    Wood oak = Wood.OAK;
    BlockBrush walls2 = oak.getPlanks();
    StairsBlock stair2 = oak.getStairs();
    BlockBrush pillar2 = oak.getLog();
    return new BlockSet(floor, walls2, stair2, pillar2);
  }
}
