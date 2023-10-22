package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.ColoredBlock;
import com.github.fnar.minecraft.block.normal.SlabBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Direction;

public class ThemeTerracotta extends Theme {

  public ThemeTerracotta() {

    BlockJumble blocks = new BlockJumble();
    DyeColor color = DyeColor.chooseRandom();
    for (Direction dir : Direction.CARDINAL) {
      blocks.addBlock(ColoredBlock.terracotta().setColor(color).setFacing(dir));
    }

    StairsBlock stair = StairsBlock.purpur();
    BlockBrush pillar = BlockType.PURPUR_PILLAR.getBrush();
    BlockBrush deco = SlabBlock.purpur().setFullBlock(true);

    this.primary = new BlockSet(
        blocks,
        blocks,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = new BlockSet(
        deco,
        deco,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

  }
}
