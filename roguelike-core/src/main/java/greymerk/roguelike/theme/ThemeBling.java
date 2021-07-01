package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeBling extends ThemeBase {

  public ThemeBling() {
    primary = new BlockSet(
        createWalls(),
        createWalls(),
        StairsBlock.quartz(),
        BlockType.LAPIS_BLOCK.getBrush(),
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    secondary = primary;
  }

  private BlockWeightedRandom createWalls() {
    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.IRON_BLOCK.getBrush(), 10);
    walls.addBlock(BlockType.GOLD_BLOCK.getBrush(), 3);
    walls.addBlock(BlockType.EMERALD_BLOCK.getBrush(), 10);
    walls.addBlock(BlockType.DIAMOND_BLOCK.getBrush(), 20);
    return walls;
  }
}
