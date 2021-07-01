package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.normal.Wood;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeBases {
  public static ThemeBase OAK = new ThemeBase() {
    {
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
  };
}
