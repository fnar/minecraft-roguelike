package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeGrey extends ThemeBase {

  public ThemeGrey() {
    BlockBrush andesite = BlockType.ANDESITE.getBrush();
    BlockBrush smoothAndesite = BlockType.ANDESITE_POLISHED.getBrush();
    StairsBlock stair = StairsBlock.stoneBrick();

    this.primary = new BlockSet(andesite, smoothAndesite, stair, smoothAndesite);
    this.secondary = this.primary;
  }
}
