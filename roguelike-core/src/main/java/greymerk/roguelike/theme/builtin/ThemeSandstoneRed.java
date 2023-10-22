package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeSandstoneRed extends Theme {

  public ThemeSandstoneRed() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.RED_SANDSTONE.getBrush(), 100);
    walls.addBlock(BlockType.SAND_RED.getBrush(), 5);

    StairsBlock stair = StairsBlock.redSandstone();

    BlockBrush pillar = BlockType.SMOOTH_RED_SANDSTONE.getBrush();

    this.primary = new BlockSet(
        walls,
        walls,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    BlockBrush segmentWall = BlockType.CHISELED_RED_SANDSTONE.getBrush();

    this.secondary = new BlockSet(
        segmentWall,
        segmentWall,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

  }
}
