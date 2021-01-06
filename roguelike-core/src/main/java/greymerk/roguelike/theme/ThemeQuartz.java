package greymerk.roguelike.theme;

import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Direction;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.Quartz;

public class ThemeQuartz extends ThemeBase {

  public ThemeQuartz() {

    BlockBrush walls = BlockType.QUARTZ.getBrush();
    StairsBlock stair = StairsBlock.quartz();
    BlockBrush pillar = Quartz.PILLAR.getBrush().setFacing(Direction.UP);

    this.primary = new BlockSet(walls, stair, pillar);
    BlockBrush SegmentWall = Quartz.CHISELED.getBrush();
    this.secondary = new BlockSet(SegmentWall, stair, pillar);

  }
}
