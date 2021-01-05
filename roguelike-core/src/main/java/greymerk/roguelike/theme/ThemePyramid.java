package greymerk.roguelike.theme;

import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

public class ThemePyramid extends ThemeBase {

  public ThemePyramid() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.SANDSTONE_SMOOTH.getBrush(), 100);
    walls.addBlock(BlockType.SANDSTONE.getBrush(), 10);
    walls.addBlock(BlockType.SANDSTONE_CHISELED.getBrush(), 5);

    StairsBlock stair = StairsBlock.sandstone();
    BlockBrush pillar = BlockType.SANDSTONE_SMOOTH.getBrush();
    BlockBrush SegmentWall = BlockType.SANDSTONE_CHISELED.getBrush();

    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = new BlockSet(SegmentWall, stair, pillar);

  }

}
