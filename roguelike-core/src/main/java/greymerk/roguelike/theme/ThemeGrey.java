package greymerk.roguelike.theme;

import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

public class ThemeGrey extends ThemeBase {

  public ThemeGrey() {
    BlockBrush andesite = BlockType.ANDESITE.getBrush();
    BlockBrush smoothAndesite = BlockType.ANDESITE_POLISHED.getBrush();
    StairsBlock stair = StairsBlock.stoneBrick();

    this.primary = new BlockSet(andesite, smoothAndesite, stair, smoothAndesite);
    this.secondary = this.primary;
  }
}
