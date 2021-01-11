package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeIce extends ThemeBase {

  public ThemeIce() {

    BlockBrush walls = BlockType.SNOW.getBrush();
    StairsBlock stair = StairsBlock.quartz();
    BlockBrush pillar = BlockType.ICE_PACKED.getBrush();

    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = this.primary;
  }
}
