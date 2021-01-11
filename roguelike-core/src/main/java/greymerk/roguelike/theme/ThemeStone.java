package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeStone extends ThemeBase {

  public ThemeStone() {
    BlockBrush walls = BlockType.STONE_BRICK.getBrush();
    StairsBlock stair = StairsBlock.stoneBrick();
    BlockBrush pillar = BlockType.ANDESITE_POLISHED.getBrush();

    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = primary;
  }
}
