package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeEnder extends Theme {

  public ThemeEnder() {

    BlockStripes floor = new BlockStripes();
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.BROWN));
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.YELLOW));

    BlockBrush walls = BlockType.END_STONE_BRICKS.getBrush();
    StairsBlock stair = StairsBlock.sandstone();
    BlockBrush pillar = BlockType.OBSIDIAN.getBrush();
    this.primary = new BlockSet(floor, walls, stair, pillar);

    this.secondary = new BlockSet(
        floor,
        BlockType.CHISELED_SANDSTONE.getBrush(),
        stair,
        pillar
    );
  }
}
