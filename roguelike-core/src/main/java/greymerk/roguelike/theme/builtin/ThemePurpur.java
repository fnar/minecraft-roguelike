package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;

public class ThemePurpur extends Theme {

  public ThemePurpur() {

    BlockBrush walls = BlockType.PURPUR_BLOCK.getBrush();
    StairsBlock stair = StairsBlock.purpur();
    BlockBrush pillar = BlockType.PURPUR_PILLAR.getBrush();

    this.primary = new BlockSet(
        walls,
        walls,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    BlockBrush SegmentWall = BlockType.END_STONE_BRICKS.getBrush();
    this.secondary = new BlockSet(
        SegmentWall,
        SegmentWall,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

  }
}
