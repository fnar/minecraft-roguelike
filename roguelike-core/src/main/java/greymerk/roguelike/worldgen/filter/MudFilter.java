package greymerk.roguelike.worldgen.filter;

import com.google.common.collect.Lists;

import com.github.fnar.minecraft.block.BlockType;

import java.util.ArrayList;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Bounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.Shape;

public class MudFilter implements IFilter {

  @Override
  public void apply(WorldEditor editor, Theme theme, Bounded box) {
    for (Coord pos : box.getShape(Shape.RECTSOLID)) {
      if (editor.getRandom().nextInt(40) != 0) {
        continue;
      }
      if (!validLocation(editor, pos)) {
        continue;
      }
      generate(editor, pos, editor.getRandom().nextInt(3) + 2);
    }
  }

  private void generate(WorldEditor editor, Coord pos, int counter) {
    if (counter <= 0) {
      return;
    }

    for (Direction dir : Direction.CARDINAL) {
      if (editor.getRandom().nextBoolean()) {
        continue;
      }
      Coord next = pos.copy();
      next.translate(dir);
      generate(editor, next, counter - 1);
    }

    if (!validLocation(editor, pos)) {
      return;
    }

    ArrayList<BlockBrush> wetBlocks = Lists.newArrayList(
        BlockType.CLAY.getBrush(),
        BlockType.SOUL_SAND.getBrush(),
        BlockType.MYCELIUM.getBrush());
    BlockJumble wet = new BlockJumble(wetBlocks);

    ArrayList<BlockBrush> dryBlocks = Lists.newArrayList(
        BlockType.DIRT_PODZOL.getBrush(),
        BlockType.DIRT.getBrush(),
        BlockType.DIRT_COARSE.getBrush());
    BlockJumble dry = new BlockJumble(dryBlocks);

    switch (counter) {
      case 5:
      case 4:
        BlockType.DIRT.getBrush().stroke(editor, pos);
      case 3:
        if (editor.getRandom().nextBoolean()) {
          BlockType.DIRT_COARSE.getBrush().stroke(editor, pos);
          break;
        }
      case 2:
        wet.stroke(editor, pos);
        break;
      case 1:
        if (editor.getRandom().nextBoolean()) {
          wet.stroke(editor, pos);
          break;
        }
      default:
        BlockType.WATER_FLOWING.getBrush().stroke(editor, pos);
        return;
    }


    if (editor.getRandom().nextInt(6) != 0) {
      return;
    }

    BlockJumble plants = new BlockJumble();
    plants.addBlock(BlockType.BROWN_MUSHROOM.getBrush());
    plants.addBlock(BlockType.RED_MUSHROOM.getBrush());

    Coord cursor = pos.copy();
    cursor.up();
    plants.stroke(editor, cursor);
  }

  private boolean validLocation(WorldEditor editor, Coord pos) {

    if (!editor.isOpaqueCubeBlock(pos)) {
      return false;
    }

    Coord cursor = pos.copy();
    cursor.up();
    if (!editor.isAirBlock(cursor)) {
      return false;
    }

    cursor.down(2);
    if (editor.isAirBlock(cursor)) {
      return false;
    }

    cursor.up();

    for (Direction dir : Direction.values()) {
      cursor.translate(dir);
      if (!editor.isOpaqueCubeBlock(pos)) {
        return false;
      }
      cursor.translate(dir.reverse());
    }

    return true;
  }
}
