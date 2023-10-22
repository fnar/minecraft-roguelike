package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;
import com.github.fnar.minecraft.material.Wood;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeOak extends Theme {

  public ThemeOak() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.STONE_BRICKS.getBrush(), 30);
    BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();
    walls.addBlock(cracked, 20);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 5);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 1);

    StairsBlock stair = StairsBlock.stoneBrick();
    DoorBlock door = DoorBlock.spruce();
    this.primary = new BlockSet(
        walls,
        walls,
        stair,
        walls,
        door,
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    Wood wood = Wood.OAK;
    BlockBrush pillar = wood.getLog();
    BlockBrush segmentWall = wood.getPlanks();
    StairsBlock segmentStair = StairsBlock.oak();
    this.secondary = new BlockSet(
        segmentWall,
        segmentWall,
        segmentStair,
        pillar,
        door,
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }
}
