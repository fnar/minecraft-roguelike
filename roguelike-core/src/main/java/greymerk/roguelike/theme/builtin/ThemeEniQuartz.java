package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.Quartz;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Direction;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeEniQuartz extends Theme {

  public ThemeEniQuartz() {

    BlockWeightedRandom red = new BlockWeightedRandom();
    red.addBlock(stainedHardenedClay().setColor(DyeColor.RED), 100);
    red.addBlock(BlockType.WATER_FLOWING.getBrush(), 5);
    red.addBlock(BlockType.REDSTONE_BLOCK.getBrush(), 1);

    BlockStripes floor = new BlockStripes();
    floor.addBlock(red);
    floor.addBlock(BlockType.OBSIDIAN.getBrush());

    BlockBrush walls = BlockType.BRICK.getBrush();

    StairsBlock stair = StairsBlock.brick();

    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        walls,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    BlockBrush quartz = BlockType.QUARTZ.getBrush();
    StairsBlock quartzStair = StairsBlock.quartz();
    BlockBrush quartzPillar = Quartz.PILLAR.getBrush().setFacing(Direction.UP);

    this.secondary = new BlockSet(
        floor,
        quartz,
        quartzStair,
        quartzPillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }
}
