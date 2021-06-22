package greymerk.roguelike.worldgen.filter;

import com.github.fnar.minecraft.block.BlockType;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.Shape;

public class MudFilter implements IFilter {

  @Override
  public void apply(WorldEditor editor, Random rand, ThemeBase theme, IBounded box) {
    for (Coord pos : box.getShape(Shape.RECTSOLID)) {
      if (rand.nextInt(40) != 0) {
        continue;
      }
      if (!validLocation(editor, pos)) {
        continue;
      }
      generate(editor, rand, pos, rand.nextInt(3) + 2);
    }
  }

  private void generate(WorldEditor editor, Random rand, Coord pos, int counter) {
    if (counter <= 0) {
      return;
    }

    for (Direction dir : Direction.CARDINAL) {
      if (rand.nextBoolean()) {
        continue;
      }
      Coord next = pos.copy();
      next.translate(dir);
      generate(editor, rand, next, counter - 1);
    }

    if (!validLocation(editor, pos)) {
      return;
    }

    BlockJumble wet = new BlockJumble();
    wet.addBlock(BlockType.CLAY.getBrush());
    wet.addBlock(BlockType.SOUL_SAND.getBrush());
    wet.addBlock(BlockType.MYCELIUM.getBrush());

    BlockJumble dry = new BlockJumble();
    dry.addBlock(BlockType.DIRT_PODZOL.getBrush());
    dry.addBlock(BlockType.DIRT.getBrush());
    dry.addBlock(BlockType.DIRT_COARSE.getBrush());

    switch (counter) {
      case 5:
      case 4:
        BlockType.DIRT.getBrush().stroke(editor, pos);
      case 3:
        if (rand.nextBoolean()) {
          BlockType.DIRT_COARSE.getBrush().stroke(editor, pos);
          break;
        }
      case 2:
        wet.stroke(editor, pos);
        break;
      case 1:
        if (rand.nextBoolean()) {
          wet.stroke(editor, pos);
          break;
        }
      default:
        BlockType.WATER_FLOWING.getBrush().stroke(editor, pos);
        return;
    }


    if (rand.nextInt(6) != 0) {
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
