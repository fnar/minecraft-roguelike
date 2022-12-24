package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.VineBlock;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class HoleTower extends Tower {

  public HoleTower(WorldEditor worldEditor, Theme theme) {
    super(worldEditor, theme);
  }

  @Override
  public void generate(Coord origin) {

    BlockBrush blocks = getPrimaryWall();
    Coord floor = TowerType.getBaseCoord(editor, origin);

    Coord start = floor.copy().north().east().up(3);

    Coord end = origin.copy().south().west();
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    start.north(2).east(2);
    end.south(2).west(2).up();
    RectSolid.newRect(start, end)
        .fill(editor, getRubble(blocks), false, true)
        .fill(editor, VineBlock.vine());
  }

  public BlockJumble getRubble(BlockBrush blocks) {
    BlockJumble rubble = new BlockJumble();
    rubble.addBlock(blocks);
    rubble.addBlock(SingleBlockBrush.AIR);
    rubble.addBlock(BlockType.DIRT.getBrush());
    rubble.addBlock(BlockType.DIRT_COARSE.getBrush());
    rubble.addBlock(BlockType.STONE_SMOOTH.getBrush());
    return rubble;
  }

}
