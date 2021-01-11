package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.ColoredBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Direction;

public class ThemeTerracotta extends ThemeBase {

  public ThemeTerracotta() {

    BlockJumble blocks = new BlockJumble();
    for (Direction dir : Direction.CARDINAL) {
      blocks.addBlock(ColoredBlock.terracotta().setColor(DyeColor.MAGENTA).setFacing(dir));
    }

    StairsBlock stair = StairsBlock.purpur();
    BlockBrush pillar = BlockType.PURPUR_PILLAR.getBrush();
    BlockBrush deco = BlockType.PURPUR_DOUBLE_SLAB.getBrush();

    this.primary = new BlockSet(blocks, stair, pillar);
    this.secondary = new BlockSet(deco, stair, pillar);

  }
}
