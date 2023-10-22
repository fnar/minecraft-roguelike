package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.concrete;
import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeBumbo extends Theme {
  public ThemeBumbo() {
    BlockBrush green = stainedHardenedClay().setColor(DyeColor.GREEN);
    BlockBrush moustache = stainedHardenedClay().setColor(DyeColor.BLACK);
    BlockBrush black = concrete().setColor(DyeColor.BLACK);
    BlockBrush white = concrete().setColor(DyeColor.WHITE);

    BlockBrush yellow = concrete().setColor(DyeColor.YELLOW);
    BlockBrush red = concrete().setColor(DyeColor.RED);

    primary = new BlockSet(
        moustache,
        green,
        StairsBlock.acacia(),
        white,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    secondary = new BlockSet(
        red,
        yellow,
        StairsBlock.acacia(),
        black,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }
}
