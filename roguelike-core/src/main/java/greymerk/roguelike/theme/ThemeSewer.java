package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeSewer extends ThemeBase {

  public ThemeSewer() {

    BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();
    BlockBrush mossy = BlockType.STONE_BRICK_MOSSY.getBrush();

    BlockWeightedRandom wall = new BlockWeightedRandom();
    wall.addBlock(BlockType.STONE_BRICK.getBrush(), 10);
    wall.addBlock(mossy, 4);
    wall.addBlock(cracked, 1);

    BlockBrush floor = BlockType.STONE_BRICK.getBrush();

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
