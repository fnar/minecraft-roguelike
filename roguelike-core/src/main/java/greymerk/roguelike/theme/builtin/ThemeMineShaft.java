package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;
import com.github.fnar.minecraft.material.Wood;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeMineShaft extends Theme {

  public ThemeMineShaft() {

    BlockJumble floor = new BlockJumble();
    floor.addBlock(BlockType.GRAVEL.getBrush());
    floor.addBlock(BlockType.DIRT.getBrush());
    floor.addBlock(BlockType.COBBLESTONE.getBrush());

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.STONE_SMOOTH.getBrush(), 50);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 15);
    walls.addBlock(BlockType.ORE_REDSTONE.getBrush(), 1);

    this.primary = getPrimaryBlockSet(floor, walls);
    this.secondary = getSecondaryBlockSet(floor);
  }

  private BlockSet getPrimaryBlockSet(BlockJumble floor, BlockWeightedRandom walls) {
    StairsBlock stair = StairsBlock.cobble();
    BlockBrush pillar = BlockType.COBBLESTONE_WALL.getBrush();
    return new BlockSet(
        floor,
        walls,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }

  private BlockSet getSecondaryBlockSet(BlockJumble floor) {
    Wood oak = Wood.OAK;
    return new BlockSet(
        floor,
        oak.getPlanks(),
        oak.getStairs(),
        oak.getPlanks(),
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }
}
