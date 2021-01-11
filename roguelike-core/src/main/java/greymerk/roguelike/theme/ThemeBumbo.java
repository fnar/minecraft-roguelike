package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;

import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.concrete;
import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeBumbo extends ThemeBase {
  public ThemeBumbo() {
    BlockBrush green = stainedHardenedClay().setColor(DyeColor.GREEN);
    BlockBrush moustache = stainedHardenedClay().setColor(DyeColor.BLACK);
    BlockBrush black = concrete().setColor(DyeColor.BLACK);
    BlockBrush white = concrete().setColor(DyeColor.WHITE);

    BlockBrush yellow = concrete().setColor(DyeColor.YELLOW);
    BlockBrush red = concrete().setColor(DyeColor.RED);

    primary = new BlockSet(moustache, green, StairsBlock.acacia(), white);
    secondary = new BlockSet(red, yellow, StairsBlock.acacia(), black);
  }
}
