package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.material.Wood;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeDarkOak extends Theme {

  public ThemeDarkOak() {
    BlockBrush walls = BlockType.DARK_OAK_PLANK.getBrush();
    StairsBlock stair = StairsBlock.darkOak();
    BlockBrush pillar = Wood.DARK_OAK.getLog();
    DoorBlock door = DoorBlock.darkOak();

    this.primary = new BlockSet(walls, walls, stair, pillar, door);
    this.secondary = primary;
  }
}
