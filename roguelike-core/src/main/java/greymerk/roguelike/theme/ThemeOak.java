package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;
import com.github.srwaggon.minecraft.block.normal.Wood;
import com.github.srwaggon.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeOak extends ThemeBase {

  public ThemeOak() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.STONE_BRICK.getBrush(), 30);
    BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();
    walls.addBlock(cracked, 20);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 5);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 1);

    StairsBlock stair = StairsBlock.stoneBrick();
    DoorBlock door = DoorBlock.spruce();
    this.primary = new BlockSet(walls, walls, stair, walls, door);

    Wood wood = Wood.OAK;
    BlockBrush pillar = wood.getLog();
    BlockBrush segmentWall = wood.getPlanks();
    StairsBlock segmentStair = StairsBlock.oak();
    this.secondary = new BlockSet(segmentWall, segmentWall, segmentStair, pillar, door);
  }
}
