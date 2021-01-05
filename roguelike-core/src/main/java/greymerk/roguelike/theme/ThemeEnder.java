package greymerk.roguelike.theme;

import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeEnder extends ThemeBase {

  public ThemeEnder() {

    BlockStripes floor = new BlockStripes();
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.BROWN));
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.YELLOW));

    BlockBrush walls = BlockType.ENDER_BRICK.getBrush();
    StairsBlock stair = StairsBlock.sandstone();
    BlockBrush pillar = BlockType.OBSIDIAN.getBrush();
    this.primary = new BlockSet(floor, walls, stair, pillar);

    this.secondary = new BlockSet(
        floor,
        BlockType.SANDSTONE_CHISELED.getBrush(),
        stair,
        pillar
    );
  }
}
