package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeCrypt extends Theme {

  public ThemeCrypt() {

    BlockJumble stone = new BlockJumble();
    stone.addBlock(BlockType.STONE_BRICKS.getBrush());
    stone.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush());
    stone.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush());

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(stone, 100);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 10);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 5);

    BlockBrush andesite = BlockType.ANDESITE.getBrush();
    BlockBrush smoothAndesite = BlockType.ANDESITE_POLISHED.getBrush();
    BlockCheckers pillar = new BlockCheckers(andesite, smoothAndesite);

    StairsBlock stair = StairsBlock.stoneBrick();

    this.primary = new BlockSet(walls, walls, stair, pillar, DoorBlock.iron());
    this.secondary = this.primary;

  }
}
