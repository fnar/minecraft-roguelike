package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.EnderChest;
import greymerk.roguelike.worldgen.blocks.Quartz;
import greymerk.roguelike.worldgen.shapes.IShape;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;

public class DungeonsEnder extends DungeonBase {


  public DungeonsEnder(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(WorldEditor editor, Random inRandom, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    MetaBlock black = BlockType.get(BlockType.OBSIDIAN);
    MetaBlock white = Quartz.get(Quartz.SMOOTH);
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord start;
    Coord end;
    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, 0, -3));
    end.translate(new Coord(3, 2, 3));
    RectSolid.fill(editor, inRandom, start, end, air);
    for (Cardinal dir : Cardinal.DIRECTIONS) {

      Cardinal[] orth = dir.orthogonal();

      start = new Coord(origin);
      start.translate(dir, 4);
      end = new Coord(start);
      start.translate(orth[0], 4);
      start.translate(Cardinal.DOWN, 1);
      end.translate(orth[1], 4);
      end.translate(Cardinal.UP, 5);
      RectSolid.fill(editor, inRandom, start, end, black, false, true);

    }

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, 2, -3));
    end.translate(new Coord(3, 10, 3));

    int top = end.getY() - start.getY() + 1;
    for (Coord cell : new RectSolid(start, end)) {
      boolean disolve = inRandom.nextInt((cell.getY() - start.getY()) + 1) < 2;
      air.set(editor, inRandom, cell, false, disolve);
      black.set(editor, inRandom, cell, false, inRandom.nextInt(top - (cell.getY() - start.getY())) == 0 && !disolve);
    }

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));

    BlockCheckers checkers = new BlockCheckers(black, white);
    RectSolid.fill(editor, inRandom, start, end, checkers);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, 0, -4));
    end.translate(new Coord(4, 0, 4));
    if (RogueConfig.getBoolean(RogueConfig.GENEROUS)) {
      addEnderChest(editor, new RectSolid(start, end));
    }
    SpawnerSettings spawners = settings.getSpawners();
    generateSpawner(editor, inRandom, origin, settings.getDifficulty(origin), spawners, MobType.ENDERMAN);

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
        if (editor.getBlock(cursor).isOpaqueCube()) {
          EnderChest.set(editor, dir.reverse(), pos);
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
