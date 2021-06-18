package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;
import com.github.srwaggon.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeNether extends ThemeBase {

  public ThemeNether() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.NETHERRACK.getBrush(), 200);
    walls.addBlock(BlockType.ORE_QUARTZ.getBrush(), 30);
    walls.addBlock(BlockType.SOUL_SAND.getBrush(), 30);
    walls.addBlock(BlockType.COAL_BLOCK.getBrush(), 5);

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(walls, 2000);
    floor.addBlock(BlockType.REDSTONE_BLOCK.getBrush(), 50);
    if (RogueConfig.PRECIOUSBLOCKS.getBoolean()) {
      floor.addBlock(BlockType.GOLD_BLOCK.getBrush(), 2);
    }
    if (RogueConfig.PRECIOUSBLOCKS.getBoolean()) {
      floor.addBlock(BlockType.DIAMOND_BLOCK.getBrush(), 1);
    }

    this.primary = new BlockSet(
        floor,
        walls,
        StairsBlock.netherBrick(),
        BlockType.OBSIDIAN.getBrush(),
        DoorBlock.iron(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.LAVA_FLOWING.getBrush());

  this.secondary = new BlockSet(
        floor,
        walls,
        StairsBlock.netherBrick(),
        BlockType.NETHERBRICK.getBrush(),
        DoorBlock.iron(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.LAVA_FLOWING.getBrush());
  }
}
