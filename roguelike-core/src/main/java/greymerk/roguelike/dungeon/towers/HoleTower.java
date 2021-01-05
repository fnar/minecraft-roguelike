package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.decorative.VineBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class HoleTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random rand, ThemeBase theme, Coord origin) {

    BlockBrush blocks = theme.getPrimary().getWall();
    Coord floor = Tower.getBaseCoord(editor, origin);

    Coord start;
    Coord end;

    start = new Coord(floor);
    start.translate(Cardinal.NORTH);
    start.translate(Cardinal.EAST);
    start.translate(Cardinal.UP, 3);
    end = new Coord(origin);
    end.translate(Cardinal.SOUTH);
    end.translate(Cardinal.WEST);

    RectSolid.fill(editor, start, end, SingleBlockBrush.AIR);
    start.translate(Cardinal.NORTH, 2);
    start.translate(Cardinal.EAST, 2);
    end.translate(Cardinal.SOUTH, 2);
    end.translate(Cardinal.WEST, 2);
    end.translate(Cardinal.UP);

    BlockJumble rubble = new BlockJumble();
    rubble.addBlock(blocks);
    rubble.addBlock(SingleBlockBrush.AIR);
    rubble.addBlock(BlockType.DIRT.getBrush());
    rubble.addBlock(BlockType.DIRT_COARSE.getBrush());
    rubble.addBlock(BlockType.STONE_SMOOTH.getBrush());

    RectSolid.fill(editor, start, end, rubble, false, true);
    VineBlock.vine().fill(editor, new RectSolid(start, end));
  }

}
