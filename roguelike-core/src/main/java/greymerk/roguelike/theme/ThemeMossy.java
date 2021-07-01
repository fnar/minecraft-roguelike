package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.InfestedBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeMossy extends ThemeBase {

  public ThemeMossy() {

    BlockBrush mossBrick = BlockType.STONE_BRICK_MOSSY.getBrush();
    BlockBrush mossy = BlockType.COBBLESTONE_MOSSY.getBrush();
    BlockBrush cobble = BlockType.COBBLESTONE.getBrush();
    BlockBrush egg = InfestedBlock.getJumble();
    BlockBrush gravel = BlockType.GRAVEL.getBrush();

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(cobble, 60);
    walls.addBlock(mossBrick, 30);
    walls.addBlock(egg, 5);
    walls.addBlock(mossy, 10);
    walls.addBlock(gravel, 15);

    BlockWeightedRandom pillar = new BlockWeightedRandom();
    pillar.addBlock(mossBrick, 20);
    pillar.addBlock(cobble, 5);
    pillar.addBlock(egg, 3);
    pillar.addBlock(mossy, 5);

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(mossy, 10);
    floor.addBlock(mossBrick, 4);
    floor.addBlock(cobble, 2);
    floor.addBlock(gravel, 1);

    StairsBlock stair = StairsBlock.cobble();
    DoorBlock door = DoorBlock.iron();
    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        walls,
        door,
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = this.primary;
  }
}
