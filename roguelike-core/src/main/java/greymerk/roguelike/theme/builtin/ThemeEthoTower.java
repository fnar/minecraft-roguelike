package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeEthoTower extends Theme {

  public ThemeEthoTower() {

    BlockBrush primaryWall = stainedHardenedClay().setColor(DyeColor.CYAN);
    StairsBlock stair = StairsBlock.sandstone();
    BlockBrush secondaryWall = BlockType.SANDSTONE_SMOOTH.getBrush();

    this.primary = new BlockSet(primaryWall, primaryWall, stair, primaryWall);
    this.secondary = new BlockSet(secondaryWall, secondaryWall, stair, secondaryWall);
  }

}
