package greymerk.roguelike.dungeon.towers;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.decorative.VineBlock;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Direction;
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

    start = floor.copy();
    start.translate(Direction.NORTH);
    start.translate(Direction.EAST);
    start.translate(Direction.UP, 3);
    end = origin.copy();
    end.translate(Direction.SOUTH);
    end.translate(Direction.WEST);

    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(Direction.NORTH, 2);
    start.translate(Direction.EAST, 2);
    end.translate(Direction.SOUTH, 2);
    end.translate(Direction.WEST, 2);
    end.translate(Direction.UP);

    BlockJumble rubble = new BlockJumble();
    rubble.addBlock(blocks);
    rubble.addBlock(SingleBlockBrush.AIR);
    rubble.addBlock(BlockType.DIRT.getBrush());
    rubble.addBlock(BlockType.DIRT_COARSE.getBrush());
    rubble.addBlock(BlockType.STONE_SMOOTH.getBrush());

    RectSolid.newRect(start, end).fill(editor, rubble, false, true);
    VineBlock.vine().fill(editor, new RectSolid(start, end));
  }

}
