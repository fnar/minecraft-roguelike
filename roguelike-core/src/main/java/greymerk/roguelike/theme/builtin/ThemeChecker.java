package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockCheckers;

public class ThemeChecker extends Theme {

  public ThemeChecker() {
    BlockBrush one = BlockType.OBSIDIAN.getBrush();
    BlockBrush two = BlockType.QUARTZ.getBrush();
    BlockBrush checks = new BlockCheckers(one, two);
    StairsBlock stair = StairsBlock.quartz();

    this.primary = new BlockSet(checks, stair, checks);

    this.secondary = primary;

  }
}
