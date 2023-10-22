package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeTower extends Theme {

  public ThemeTower() {

    BlockJumble stone = new BlockJumble();
    stone.addBlock(BlockType.STONE_BRICKS.getBrush());
    stone.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush());
    stone.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush());

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(SingleBlockBrush.AIR, 5);
    walls.addBlock(stone, 30);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 10);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 5);

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
    this.secondary = this.primary;

  }
}
