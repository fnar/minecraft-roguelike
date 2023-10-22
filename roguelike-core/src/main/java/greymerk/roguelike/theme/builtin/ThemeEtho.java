package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;
import com.github.fnar.minecraft.material.Wood;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeEtho extends Theme {

  public ThemeEtho() {

    BlockBrush floor = BlockType.GRASS_BLOCK.getBrush();

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
