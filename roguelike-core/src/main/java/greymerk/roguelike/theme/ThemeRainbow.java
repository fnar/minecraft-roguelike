package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.normal.StairsBlock;
import com.github.srwaggon.minecraft.block.normal.Wood;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;

import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.concrete;

public class ThemeRainbow extends ThemeBase {

  public ThemeRainbow() {

    BlockStripes rainbow = new BlockStripes();
    for (DyeColor color : DyeColor.values()) {
      rainbow.addBlock(concrete().setColor(color));
    }

    StairsBlock stair = StairsBlock.acacia();
    BlockBrush pillar = Wood.ACACIA.getLog();
    BlockBrush planks = Wood.ACACIA.getPlanks();

    this.primary = new BlockSet(rainbow, stair, pillar);
    this.secondary = new BlockSet(planks, stair, pillar);

  }
}
