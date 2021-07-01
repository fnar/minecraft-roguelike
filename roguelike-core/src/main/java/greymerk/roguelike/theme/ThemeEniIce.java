package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.Quartz;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Direction;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

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

    this.primary = new BlockSet(
        floor,
        ice,
        stair,
        quartzPillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = new BlockSet(
        floor,
        ice,
        stair,
        quartzPillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }
}
