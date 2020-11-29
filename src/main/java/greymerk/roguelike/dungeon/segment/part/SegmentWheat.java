package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentWheat extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir, 3);
    BlockType.get(BlockType.WATER_FLOWING).set(editor, cursor);

    Cardinal[] orth = dir.orthogonal();
    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    start.translate(Cardinal.UP, 2);
    end.translate(dir);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orth[0], 1);
    end.translate(orth[1], 1);
    end.translate(Cardinal.UP, 1);
    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
    start.translate(Cardinal.DOWN, 1);
    end.translate(Cardinal.DOWN, 2);

    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.FARMLAND));
    start.translate(Cardinal.UP, 1);
    end.translate(Cardinal.UP, 1);
    BlockJumble crops = new BlockJumble();
    crops.addBlock(Crops.get(Crops.WHEAT));
    crops.addBlock(Crops.get(Crops.CARROTS));
    crops.addBlock(Crops.get(Crops.POTATOES));
    RectSolid.fill(editor, rand, start, end, crops);

    cursor = new Coord(origin);
    cursor.translate(dir, 3);
    cursor.translate(Cardinal.UP, 1);
    MetaBlock pumpkin = Crops.getPumpkin(dir.reverse(), true);
    pumpkin.set(editor, cursor);

    IStair stair = theme.getSecondary().getStair();

    for (Cardinal d : orth) {
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      cursor.translate(Cardinal.UP, 1);
      stair.setOrientation(d.reverse(), true);
      stair.set(editor, cursor);
    }
  }
}
