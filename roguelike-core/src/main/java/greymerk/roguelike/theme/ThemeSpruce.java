package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.normal.Wood;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeSpruce extends ThemeBase {

  public ThemeSpruce() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.STONE_BRICK.getBrush(), 20);
    BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();

    walls.addBlock(cracked, 10);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 5);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 1);

    StairsBlock stair = StairsBlock.stoneBrick();

    this.primary = new BlockSet(
        walls,
        walls,
        stair,
        walls,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    Wood wood = Wood.SPRUCE;
    BlockBrush segmentWall = wood.getPlanks();
    StairsBlock segmentStair = StairsBlock.spruce();

    BlockBrush pillar = wood.getLog();
    this.secondary = new BlockSet(
        segmentWall,
        segmentWall,
        segmentStair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

  }
}
