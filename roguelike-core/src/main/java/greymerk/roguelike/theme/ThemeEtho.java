package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.normal.Wood;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeEtho extends ThemeBase {

  public ThemeEtho() {

    BlockBrush floor = BlockType.GRASS.getBrush();

    BlockBrush walls = Wood.OAK.getPlanks();

    StairsBlock stair = StairsBlock.oak();
    BlockBrush pillar = Wood.OAK.getLog();

    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    this.secondary = primary;
  }
}
