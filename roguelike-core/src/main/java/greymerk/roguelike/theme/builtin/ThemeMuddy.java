package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;
import com.github.fnar.minecraft.material.Wood;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeMuddy extends Theme {

  public ThemeMuddy() {

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(BlockType.SOUL_SAND.getBrush(), 1);
    floor.addBlock(BlockType.CLAY.getBrush(), 4);
    floor.addBlock(BlockType.DIRT.getBrush(), 3);
    floor.addBlock(BlockType.MYCELIUM.getBrush(), 1);
    floor.addBlock(BlockType.GRAVEL.getBrush(), 3);
    floor.addBlock(BlockType.DIRT_COARSE.getBrush(), 1);

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 50);
    walls.addBlock(BlockType.COBBLESTONE_MOSSY.getBrush(), 30);
    BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();
    walls.addBlock(cracked, 10);
    walls.addBlock(BlockType.DIRT.getBrush(), 15);
    walls.addBlock(BlockType.CLAY.getBrush(), 30);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 15);

    StairsBlock stair = StairsBlock.cobble();
    BlockBrush mossy = BlockType.STONE_BRICK_MOSSY.getBrush();

    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        mossy,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    BlockBrush segmentWall = Wood.DARK_OAK.getLog();
    StairsBlock segmentStair = StairsBlock.darkOak();

    this.secondary = new BlockSet(
        floor,
        segmentWall,
        segmentStair,
        segmentWall,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }
}
