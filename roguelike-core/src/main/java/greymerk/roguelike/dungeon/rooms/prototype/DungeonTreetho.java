package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock;
import com.github.srwaggon.roguelike.worldgen.block.decorative.PumpkinBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.SlabBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class DungeonTreetho extends DungeonBase {

  public DungeonTreetho(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    Cardinal dir = entrances.get(0);


    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-11, -1, -11));
    end.translate(new Coord(11, 8, 11));

    RectHollow.fill(editor, start, end, wall, false, true);

    BlockBrush birchSlab = SlabBlock.birch().setTop(true).setFullBlock(false).setSeamless(false);
    BlockBrush pumpkin = PumpkinBlock.jackOLantern();
    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-9, 8, -9));
    end.translate(new Coord(9, 8, 9));
    RectSolid.fill(editor, start, end, birchSlab);
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, start, end, pumpkin, true, true);

    cursor = new Coord(origin);
    cursor.translate(new Coord(0, 8, 0));
    ceiling(editor, settings, cursor);

    cursor = new Coord(origin);
    treeFarm(editor, cursor, dir);

    for (Cardinal o : dir.orthogonals()) {
      cursor = new Coord(origin);
      cursor.translate(o, 5);
      treeFarm(editor, cursor, dir);
    }


    return this;
  }

  private void treeFarm(WorldEditor editor, Coord origin, Cardinal dir) {
    Coord cursor;
    Coord start;
    Coord end;

    BlockBrush slab = SlabBlock.sandstone();
    BlockBrush light = PumpkinBlock.jackOLantern();
    BlockBrush sapling = BlockType.BIRCH_SAPLING.getBrush();
    BlockBrush glass = ColoredBlock.stainedGlass().setColor(DyeColor.YELLOW);
    BlockBrush dirt = BlockType.DIRT.getBrush();

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());

    start.translate(dir.reverse(), 7);
    end.translate(dir, 7);

    RectSolid.fill(editor, start, end, slab, true, true);

    cursor = new Coord(origin);

    cursor.translate(dir.reverse(), 6);
    for (int i = 0; i <= 12; ++i) {
      if (i % 2 == 0) {
        Coord p = new Coord(cursor);
        if (i % 4 == 0) {
          sapling.stroke(editor, p);
          p.translate(Cardinal.DOWN);
          dirt.stroke(editor, p);
        } else {
          glass.stroke(editor, p);
          p.translate(Cardinal.DOWN);
          light.stroke(editor, p);
        }
      }
      cursor.translate(dir);
    }
  }

  private void ceiling(WorldEditor editor, LevelSettings settings, Coord origin) {

    BlockBrush fill = BlockType.SPRUCE_PLANK.getBrush();

    MinimumSpanningTree tree = new MinimumSpanningTree(editor.getRandom(), 7, 3);
    tree.generate(editor, fill, origin);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      Coord start = new Coord(origin);
      start.translate(dir, 9);
      Coord end = new Coord(start);

      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);

      RectSolid.fill(editor, start, end, fill, true, true);

      Coord cursor = new Coord(origin);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir, 10);
      cursor.translate(dir.antiClockwise(), 10);
      for (int i = 0; i < 5; ++i) {
        pillar(editor, settings, cursor);
        cursor.translate(dir.clockwise(), 4);
      }
    }

  }

  private void pillar(WorldEditor editor, LevelSettings settings, Coord origin) {

    ThemeBase theme = settings.getTheme();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord cursor = new Coord(origin);
    editor.fillDown(cursor, pillar);

    for (Cardinal dir : Cardinal.DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      if (editor.isAirBlock(cursor)) {
        stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
      }
    }
  }

  @Override
  public int getSize() {
    return 12;
  }

}
