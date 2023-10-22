package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeStone extends Theme {

  public ThemeStone() {
    BlockBrush walls = BlockType.STONE_BRICKS.getBrush();
    StairsBlock stair = StairsBlock.stoneBrick();
    BlockBrush pillar = BlockType.ANDESITE_POLISHED.getBrush();

    this.primary = new BlockSet(
        walls,
        walls,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = primary;
  }
}
