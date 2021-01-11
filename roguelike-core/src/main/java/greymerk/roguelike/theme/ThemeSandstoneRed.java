package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeSandstoneRed extends ThemeBase {

  public ThemeSandstoneRed() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.SANDSTONE_RED.getBrush(), 100);
    walls.addBlock(BlockType.SAND_RED.getBrush(), 5);

    StairsBlock stair = StairsBlock.redSandstone();

    BlockBrush pillar = BlockType.SANDSTONE_RED_SMOOTH.getBrush();

    this.primary = new BlockSet(walls, stair, pillar);

    BlockBrush segmentWall = BlockType.SANDSTONE_RED_CHISELED.getBrush();

    this.secondary = new BlockSet(segmentWall, stair, pillar);

  }
}
