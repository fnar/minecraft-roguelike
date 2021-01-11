package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeSandstone extends ThemeBase {

  public ThemeSandstone() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.SANDSTONE.getBrush(), 100);
    walls.addBlock(BlockType.SAND.getBrush(), 5);

    StairsBlock stair = StairsBlock.sandstone();
    BlockBrush pillar = BlockType.SANDSTONE_SMOOTH.getBrush();

    this.primary = new BlockSet(walls, stair, pillar);

    BlockBrush segmentWall = BlockType.SANDSTONE_CHISELED.getBrush();

    this.secondary = new BlockSet(segmentWall, stair, pillar);

  }
}
