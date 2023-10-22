package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

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

    this.primary = new BlockSet(
        checks,
        checks,
        stair,
        checks,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    this.secondary = primary;

  }
}
