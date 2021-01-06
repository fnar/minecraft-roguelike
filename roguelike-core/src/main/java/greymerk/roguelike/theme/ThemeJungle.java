package greymerk.roguelike.theme;

import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.Wood;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Direction;

public class ThemeJungle extends ThemeBase {

  public ThemeJungle() {
    BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();
    BlockBrush mossy = BlockType.STONE_BRICK_MOSSY.getBrush();
    BlockBrush chisel = BlockType.STONE_BRICK_CHISELED.getBrush();

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.COBBLESTONE_MOSSY.getBrush(), 50);
    walls.addBlock(mossy, 30);
    walls.addBlock(cracked, 20);
    walls.addBlock(chisel, 15);

    StairsBlock stair = StairsBlock.cobble();

    BlockBrush pillar = chisel;
    BlockBrush pillar2 = Wood.JUNGLE.getLog();

    BlockJumble stairJumble = new BlockJumble();
    for (Direction dir : Direction.CARDINAL) {
      stairJumble.addBlock(StairsBlock.stoneBrick().setFacing(dir));
    }

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(stairJumble, 1);
    floor.addBlock(walls, 5);


    this.primary = new BlockSet(floor, walls, stair, pillar);
    this.secondary = new BlockSet(chisel, stair, pillar2);
  }
}
