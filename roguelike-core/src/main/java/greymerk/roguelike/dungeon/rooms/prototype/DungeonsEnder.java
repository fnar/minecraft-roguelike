package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.Quartz;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import com.github.fnar.minecraft.block.spawner.MobType;

public class DungeonsEnder extends BaseRoom {

  public DungeonsEnder(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Random rand = worldEditor.getRandom();
    BlockBrush black = BlockType.OBSIDIAN.getBrush();
    BlockBrush white = Quartz.SMOOTH.getBrush();

    Coord start;
    Coord end;
    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, 0, -3));
    end.translate(new Coord(3, 2, 3));
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);
    for (Direction dir : Direction.CARDINAL) {

      Direction[] orthogonals = dir.orthogonals();

      start = origin.copy();
      start.translate(dir, 4);
      end = start.copy();
      start.translate(orthogonals[0], 4);
      start.down();
      end.translate(orthogonals[1], 4);
      end.up(5);
      RectSolid.newRect(start, end).fill(worldEditor, black, false, true);

    }

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, 2, -3));
    end.translate(new Coord(3, 10, 3));

    int top = end.getY() - start.getY() + 1;
    for (Coord cell : new RectSolid(start, end)) {
      boolean dissolve = rand.nextInt((cell.getY() - start.getY()) + 1) < 2;
      ((BlockBrush) SingleBlockBrush.AIR).stroke(worldEditor, cell, false, dissolve);
      black.stroke(worldEditor, cell, false, rand.nextInt(top - (cell.getY() - start.getY())) == 0 && !dissolve);
    }

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));

    BlockCheckers checkers = new BlockCheckers(black, white);
    RectSolid.newRect(start, end).fill(worldEditor, checkers);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, 0, -4));
    end.translate(new Coord(4, 0, 4));
    if (RogueConfig.GENEROUS.getBoolean()) {
      addEnderChest(worldEditor, new RectSolid(start, end));
    }
    generateSpawner(origin, MobType.ENDERMAN);

    return this;
  }

  private void addEnderChest(WorldEditor editor, IShape area) {
    for (Coord pos : area) {
      if (!editor.isAirBlock(pos)) {
        continue;
      }

      Coord cursor = pos.copy();
      for (Direction dir : Direction.CARDINAL) {
        cursor.translate(dir);
        if (editor.isOpaqueCubeBlock(cursor)) {
          Direction dir1 = dir.reverse();
          BlockType.ENDER_CHEST.getBrush().setFacing(Direction.CARDINAL.contains(dir1) ? dir1.reverse() : Direction.SOUTH).stroke(editor, pos);
          return;
        }
        cursor.translate(dir.reverse());
      }
    }
  }


  public int getSize() {
    return 7;
  }
}
