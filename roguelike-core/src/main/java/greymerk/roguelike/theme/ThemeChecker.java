package greymerk.roguelike.theme;

import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockCheckers;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

public class ThemeChecker extends ThemeBase {

  public ThemeChecker() {
    BlockBrush one = BlockType.OBSIDIAN.getBrush();
    BlockBrush two = BlockType.QUARTZ.getBrush();
    BlockBrush checks = new BlockCheckers(one, two);
    StairsBlock stair = StairsBlock.quartz();

    this.primary = new BlockSet(checks, stair, checks);

    this.secondary = primary;

  }
}
