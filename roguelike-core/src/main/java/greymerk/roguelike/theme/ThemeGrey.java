package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeGrey extends ThemeBase {

  public ThemeGrey() {
    BlockBrush andesite = BlockType.ANDESITE.getBrush();
    BlockBrush smoothAndesite = BlockType.ANDESITE_POLISHED.getBrush();
    StairsBlock stair = StairsBlock.stoneBrick();

    this.primary = new BlockSet(
        andesite,
        smoothAndesite,
        stair,
        smoothAndesite,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = this.primary;
  }
}
