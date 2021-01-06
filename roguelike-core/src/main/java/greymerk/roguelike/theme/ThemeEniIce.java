package greymerk.roguelike.theme;

import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Direction;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.Quartz;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeEniIce extends ThemeBase {

  public ThemeEniIce() {

    BlockBrush ice = BlockType.ICE_PACKED.getBrush();
    BlockBrush purple = stainedHardenedClay().setColor(DyeColor.PURPLE);

    BlockWeightedRandom light = new BlockWeightedRandom();
    light.addBlock(purple, 100);
    light.addBlock(BlockType.WATER_FLOWING.getBrush(), 5);
    light.addBlock(BlockType.LAPIS_BLOCK.getBrush(), 1);

    BlockWeightedRandom dark = new BlockWeightedRandom();
    dark.addBlock(BlockType.OBSIDIAN.getBrush(), 100);
    dark.addBlock(BlockType.LAVA_FLOWING.getBrush(), 5);
    dark.addBlock(BlockType.REDSTONE_BLOCK.getBrush(), 1);

    BlockStripes floor = new BlockStripes();
    floor.addBlock(light);
    floor.addBlock(dark);

    StairsBlock stair = StairsBlock.quartz();
    BlockBrush quartzPillar = Quartz.PILLAR.getBrush().setFacing(Direction.UP);

    this.primary = new BlockSet(floor, ice, stair, quartzPillar);
    this.secondary = new BlockSet(floor, ice, stair, quartzPillar);
  }
}
