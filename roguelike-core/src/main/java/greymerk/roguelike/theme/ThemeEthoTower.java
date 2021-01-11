package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;

import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeEthoTower extends ThemeBase {

  public ThemeEthoTower() {

    BlockBrush primaryWall = stainedHardenedClay().setColor(DyeColor.CYAN);
    StairsBlock stair = StairsBlock.sandstone();
    BlockBrush secondaryWall = BlockType.SANDSTONE_SMOOTH.getBrush();

    this.primary = new BlockSet(primaryWall, primaryWall, stair, primaryWall);
    this.secondary = new BlockSet(secondaryWall, secondaryWall, stair, secondaryWall);
  }

}
