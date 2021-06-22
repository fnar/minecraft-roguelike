package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeTemple extends ThemeBase {

  public ThemeTemple() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.PRISMARINE.getBrush(), 5);
    walls.addBlock(BlockType.PRISMARINE_DARK.getBrush(), 10);
    walls.addBlock(BlockType.PRISMITE.getBrush(), 5);
    walls.addBlock(BlockType.SEA_LANTERN.getBrush(), 3);

    StairsBlock stair = StairsBlock.quartz();
    BlockBrush pillar = BlockType.PRISMARINE_DARK.getBrush();
    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = this.primary;
  }

}
