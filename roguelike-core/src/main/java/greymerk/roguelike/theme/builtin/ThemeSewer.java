package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeSewer extends Theme {

  public ThemeSewer() {

    BlockWeightedRandom wall = new BlockWeightedRandom();
    wall.addBlock(BlockType.STONE_BRICKS.getBrush(), 10);
    wall.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush(), 4);
    wall.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush(), 1);

    BlockBrush floor = BlockType.STONE_BRICKS.getBrush();

    StairsBlock stair = StairsBlock.stoneBrick();


    this.primary = new BlockSet(
        floor,
        wall,
        stair,
        wall,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    this.secondary = this.primary;
  }
}
