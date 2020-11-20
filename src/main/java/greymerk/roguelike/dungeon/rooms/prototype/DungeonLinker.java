package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonLinker extends DungeonBase {

  public DungeonLinker() {

  }

  public DungeonLinker(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    ITheme theme = settings.getTheme();

    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory floor = theme.getPrimary().getFloor();
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);

    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, 9, 4));
    RectHollow.fill(editor, rand, start, end, wall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, 9, -4));
    end.translate(new Coord(4, 9, 4));
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));
    RectSolid.fill(editor, rand, start, end, floor);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      start = new Coord(origin);
      start.translate(dir, 4);
      end = new Coord(start);
      end.translate(Cardinal.UP, 8);
      start.translate(Cardinal.DOWN);
      start.translate(dir.antiClockwise(), 4);
      end.translate(dir.clockwise(), 4);
      RectSolid.fill(editor, rand, start, end, bars, true, false);

      start = new Coord(origin);
      end = new Coord(origin);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir, 4);
      end.translate(dir.antiClockwise(), 4);
      end.translate(Cardinal.UP, 8);
      RectSolid.fill(editor, rand, start, end, pillar);
    }


    return this;
  }

  @Override
  public int getSize() {
    return 6;
  }

}
