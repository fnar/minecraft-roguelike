package greymerk.roguelike.theme;

import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.Wood;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;
import com.github.srwaggon.roguelike.worldgen.block.redstone.DoorBlock;

import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeDarkOak extends ThemeBase {

  public ThemeDarkOak() {
    BlockBrush walls = BlockType.DARK_OAK_PLANK.getBrush();
    StairsBlock stair = StairsBlock.darkOak();
    BlockBrush pillar = Wood.DARK_OAK.getLog();
    DoorBlock door = DoorBlock.darkOak();

    this.primary = new BlockSet(walls, walls, stair, pillar, door);
    this.secondary = primary;
  }
}
