package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.SlabBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeEniko2 extends Theme {

  public ThemeEniko2() {

    BlockStripes floor = new BlockStripes();
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.BLUE));
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.LIGHT_GRAY));

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.STONE_BRICKS.getBrush(), 20);
    walls.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush(), 10);
    walls.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush(), 5);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 3);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 1);

    StairsBlock stair = StairsBlock.stoneBrick();
    BlockBrush pillar = SlabBlock.stone().setTop(false).setFullBlock(true).setSeamless(true);

    this.primary = new BlockSet(
        floor,
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
