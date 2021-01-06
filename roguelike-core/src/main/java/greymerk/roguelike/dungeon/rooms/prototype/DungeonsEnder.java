package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.Quartz;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class DungeonsEnder extends DungeonBase {

  public DungeonsEnder(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {
    Random rand = editor.getRandom();
    BlockBrush black = BlockType.OBSIDIAN.getBrush();
    BlockBrush white = Quartz.SMOOTH.getBrush();

    Coord start;
    Coord end;
    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, 0, -3));
    end.translate(new Coord(3, 2, 3));
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orthogonals = dir.orthogonals();

      start = new Coord(origin);
      start.translate(dir, 4);
      end = new Coord(start);
      start.translate(orthogonals[0], 4);
      start.translate(Cardinal.DOWN, 1);
      end.translate(orthogonals[1], 4);
      end.translate(Cardinal.UP, 5);
      RectSolid.newRect(start, end).fill(editor, black, false, true);

    }

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, 2, -3));
    end.translate(new Coord(3, 10, 3));

    int top = end.getY() - start.getY() + 1;
    for (Coord cell : new RectSolid(start, end)) {
      boolean dissolve = rand.nextInt((cell.getY() - start.getY()) + 1) < 2;
      ((BlockBrush) SingleBlockBrush.AIR).stroke(editor, cell, false, dissolve);
      black.stroke(editor, cell, false, rand.nextInt(top - (cell.getY() - start.getY())) == 0 && !dissolve);
    }

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));

    BlockCheckers checkers = new BlockCheckers(black, white);
    RectSolid.newRect(start, end).fill(editor, checkers);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, 0, -4));
    end.translate(new Coord(4, 0, 4));
    if (RogueConfig.getBoolean(RogueConfig.GENEROUS)) {
      addEnderChest(editor, new RectSolid(start, end));
    }
    SpawnerSettings spawners = settings.getSpawners();
    generateSpawner(editor, origin, settings.getDifficulty(origin), spawners, MobType.ENDERMAN);

    return this;
  }

  private void addEnderChest(WorldEditor editor, IShape area) {
    for (Coord pos : area) {
      if (!editor.isAirBlock(pos)) {
        continue;
      }

      Coord cursor = new Coord(pos);
      for (Cardinal dir : Cardinal.DIRECTIONS) {
        cursor.translate(dir);
        if (editor.isOpaqueCubeBlock(cursor)) {
          Cardinal dir1 = dir.reverse();
          BlockType.ENDER_CHEST.getBrush().setFacing(Cardinal.DIRECTIONS.contains(dir1) ? dir1.reverse() : Cardinal.SOUTH).stroke(editor, pos);
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
