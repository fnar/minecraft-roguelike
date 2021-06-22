package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeCave extends ThemeBase {

  public ThemeCave() {

    BlockJumble floor = new BlockJumble();
    floor.addBlock(BlockType.GRAVEL.getBrush());
    floor.addBlock(BlockType.DIRT.getBrush());
    floor.addBlock(BlockType.COBBLESTONE.getBrush());

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.STONE_SMOOTH.getBrush(), 1000);
    walls.addBlock(BlockType.DIRT.getBrush(), 100);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 50);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 20);
    walls.addBlock(BlockType.ORE_COAL.getBrush(), 10);
    walls.addBlock(BlockType.ORE_IRON.getBrush(), 5);
    walls.addBlock(BlockType.ORE_EMERALD.getBrush(), 2);
    walls.addBlock(BlockType.ORE_DIAMOND.getBrush(), 1);
    walls.addBlock(BlockType.WATER_FLOWING.getBrush(), 3);
    walls.addBlock(BlockType.LAVA_FLOWING.getBrush(), 1);

    StairsBlock stair = StairsBlock.cobble();
    BlockBrush pillar = BlockType.COBBLESTONE.getBrush();

    this.primary = new BlockSet(floor, walls, stair, pillar);

    this.secondary = primary;
  }
}
