package greymerk.roguelike.theme;

import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

public class ThemeStone extends ThemeBase {

  public ThemeStone() {
    BlockBrush walls = BlockType.STONE_BRICK.getBrush();
    StairsBlock stair = StairsBlock.stoneBrick();
    BlockBrush pillar = BlockType.ANDESITE_POLISHED.getBrush();

    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = primary;
  }
}
