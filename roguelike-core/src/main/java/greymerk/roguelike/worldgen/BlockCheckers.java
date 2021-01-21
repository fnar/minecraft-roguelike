package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockCheckers implements BlockBrush {

  private final BlockBrush fillOne;
  private final BlockBrush fillTwo;
  private Coord offset;


  public BlockCheckers(BlockBrush fillOne, BlockBrush fillTwo, Coord offset) {
    this.fillOne = fillOne;
    this.fillTwo = fillTwo;
    this.offset = offset.copy();
  }

  public BlockCheckers(BlockBrush fillOne, BlockBrush fillTwo) {
    this(fillOne, fillTwo, new Coord(0, 0, 0));
  }

  public BlockCheckers(JsonElement json) {
    JsonArray arr = (JsonArray) json;
    List<BlockBrush> blocks = new ArrayList<>();

    for (JsonElement jsonElement : arr) {
      if (jsonElement.isJsonNull()) {
        continue;
      }
      blocks.add(BlockProvider.create(jsonElement.getAsJsonObject()));
    }

    this.fillOne = blocks.get(0);
    this.fillTwo = blocks.get(1);
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord origin, boolean fillAir, boolean replaceSolid) {
    int x = origin.getX() - this.offset.getX();
    int y = origin.getY() - this.offset.getY();
    int z = origin.getZ() - this.offset.getY();

    if (x % 2 == 0) {
      if (z % 2 == 0) {
        if (y % 2 == 0) {
          return fillOne.stroke(editor, origin.copy(), fillAir, replaceSolid);
        } else {
          return fillTwo.stroke(editor, origin.copy(), fillAir, replaceSolid);
        }
      } else {
        if (y % 2 == 0) {
          return fillTwo.stroke(editor, origin.copy(), fillAir, replaceSolid);
        } else {
          return fillOne.stroke(editor, origin.copy(), fillAir, replaceSolid);
        }
      }
    } else {
      if (z % 2 == 0) {
        if (y % 2 == 0) {
          return fillTwo.stroke(editor, origin.copy(), fillAir, replaceSolid);
        } else {
          return fillOne.stroke(editor, origin.copy(), fillAir, replaceSolid);
        }
      } else {
        if (y % 2 == 0) {
          return fillOne.stroke(editor, origin.copy(), fillAir, replaceSolid);
        } else {
          return fillTwo.stroke(editor, origin.copy(), fillAir, replaceSolid);
        }
      }
    }
  }
}
