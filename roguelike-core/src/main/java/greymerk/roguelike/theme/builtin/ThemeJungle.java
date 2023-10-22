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
import greymerk.roguelike.worldgen.Direction;

public class ThemeJungle extends Theme {

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

    BlockBrush pillar2 = Wood.JUNGLE.getLog();

    BlockJumble stairJumble = new BlockJumble();
    for (Direction dir : Direction.CARDINAL) {
      stairJumble.addBlock(StairsBlock.stoneBrick().setFacing(dir));
    }

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(stairJumble, 1);
    floor.addBlock(walls, 5);


    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        chisel,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = new BlockSet(
        chisel,
        chisel,
        stair,
        pillar2,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }
}
