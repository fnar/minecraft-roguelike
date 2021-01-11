package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;
import com.github.srwaggon.minecraft.block.normal.Wood;

import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeEtho extends ThemeBase {

  public ThemeEtho() {

    BlockBrush floor = BlockType.GRASS.getBrush();

    BlockBrush walls = Wood.OAK.getPlanks();

    StairsBlock stair = StairsBlock.oak();
    BlockBrush pillar = Wood.OAK.getLog();

    this.primary = new BlockSet(floor, walls, stair, pillar);

    this.secondary = primary;
  }
}
