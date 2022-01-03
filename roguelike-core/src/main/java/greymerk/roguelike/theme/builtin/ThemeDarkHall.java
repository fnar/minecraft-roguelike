package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.material.Wood;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeDarkHall extends Theme {

  public ThemeDarkHall() {

    BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 30);
    walls.addBlock(BlockType.COBBLESTONE_MOSSY.getBrush(), 10);
    walls.addBlock(BlockType.STONE_BRICK.getBrush(), 20);
    walls.addBlock(cracked, 10);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 5);
    StairsBlock stair = StairsBlock.stoneBrick();
    BlockBrush pillar = BlockType.STONE_BRICK_MOSSY.getBrush();

    BlockBrush walls2 = Wood.DARK_OAK.getPlanks();
    StairsBlock stair2 = StairsBlock.darkOak();
    BlockBrush pillar2 = Wood.DARK_OAK.getLog();

    DoorBlock door = DoorBlock.darkOak();

    this.primary = new BlockSet(walls, walls, stair, pillar, door);
    this.secondary = new BlockSet(walls2, walls2, stair2, pillar2, door);

  }
}
