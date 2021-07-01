package greymerk.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.normal.Wood;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.concrete;

public class ThemeRainbow extends ThemeBase {

  public ThemeRainbow() {

    BlockStripes rainbow = new BlockStripes();
    for (DyeColor color : DyeColor.values()) {
      rainbow.addBlock(concrete().setColor(color));
    }

    StairsBlock stair = StairsBlock.acacia();
    BlockBrush pillar = Wood.ACACIA.getLog();
    BlockBrush planks = Wood.ACACIA.getPlanks();

    this.primary = new BlockSet(
        rainbow,
        rainbow,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = new BlockSet(
        planks,
        planks,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

  }
}
