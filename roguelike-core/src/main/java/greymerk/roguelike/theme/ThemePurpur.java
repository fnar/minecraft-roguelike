package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;

public class ThemePurpur extends ThemeBase {

  public ThemePurpur() {

    BlockBrush walls = BlockType.PURPUR_BLOCK.getBrush();
    StairsBlock stair = StairsBlock.purpur();
    BlockBrush pillar = BlockType.PURPUR_PILLAR.getBrush();

    this.primary = new BlockSet(walls, stair, pillar);
    BlockBrush SegmentWall = BlockType.ENDER_BRICK.getBrush();
    this.secondary = new BlockSet(SegmentWall, stair, pillar);

  }
}
