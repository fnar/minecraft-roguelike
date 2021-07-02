package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;

public class ThemeIce extends Theme {

  public ThemeIce() {

    this.primary = new BlockSet(
        BlockType.SNOW.getBrush(),
        BlockType.SNOW.getBrush(),
        StairsBlock.quartz(),
        BlockType.ICE_PACKED.getBrush(),
        DoorBlock.iron(),
        BlockType.SEA_LANTERN.getBrush(),
        BlockType.WATER_FLOWING.getBrush());
    this.secondary = this.primary;
  }
}
