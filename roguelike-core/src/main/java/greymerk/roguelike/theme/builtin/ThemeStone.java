package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeStone extends Theme {

  public ThemeStone() {
    BlockBrush walls = BlockType.STONE_BRICK.getBrush();
    StairsBlock stair = StairsBlock.stoneBrick();
    BlockBrush pillar = BlockType.ANDESITE_POLISHED.getBrush();

    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = primary;
  }
}
