package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class DungeonTreetho extends DungeonBase {

  public DungeonTreetho(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    Cardinal dir = entrances[0];


    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-11, -1, -11));
    end.translate(new Coord(11, 8, 11));

    RectHollow.fill(editor, rand, start, end, wall, false, true);

    MetaBlock birchSlab = Slab.get(Slab.BIRCH, true, false, false);
    MetaBlock pumpkin = Crops.get(Crops.PUMPKIN);
    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-9, 8, -9));
    end.translate(new Coord(9, 8, 9));
    RectSolid.fill(editor, rand, start, end, birchSlab);
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, pumpkin, true, true);

    cursor = new Coord(origin);
    cursor.translate(new Coord(0, 8, 0));
    ceiling(editor, rand, settings, cursor);

    cursor = new Coord(origin);
    treeFarm(editor, rand, settings, cursor, dir);

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.translate(o, 5);
      treeFarm(editor, rand, settings, cursor, dir);
    }


    return this;
  }

  private void treeFarm(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {
    Coord cursor;
    Coord start;
    Coord end;

    MetaBlock slab = Slab.get(Slab.SANDSTONE);
    MetaBlock light = BlockType.get(BlockType.PUMPKIN_LIT);
    MetaBlock sapling = Wood.getSapling(Wood.BIRCH);
    MetaBlock glass = ColorBlock.get(ColorBlock.GLASS, DyeColor.YELLOW);
    MetaBlock dirt = BlockType.get(BlockType.DIRT);

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(dir.left());
    end.translate(dir.right());

    start.translate(dir.reverse(), 7);
    end.translate(dir, 7);

    RectSolid.fill(editor, rand, start, end, slab, true, true);

    cursor = new Coord(origin);

    cursor.translate(dir.reverse(), 6);
    for (int i = 0; i <= 12; ++i) {
      if (i % 2 == 0) {
        Coord p = new Coord(cursor);
        if (i % 4 == 0) {
          sapling.set(editor, p);
          p.translate(Cardinal.DOWN);
          dirt.set(editor, p);
        } else {
          glass.set(editor, p);
          p.translate(Cardinal.DOWN);
          light.set(editor, p);
        }
      }
      cursor.translate(dir);
    }


  }

  private void ceiling(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin) {

    MetaBlock fill = Wood.getPlank(Wood.SPRUCE);

    MinimumSpanningTree tree = new MinimumSpanningTree(rand, 7, 3);
    tree.generate(editor, rand, fill, origin);

    for (Cardinal dir : Cardinal.directions) {
      Coord start = new Coord(origin);
      start.translate(dir, 9);
      Coord end = new Coord(start);

      start.translate(dir.left(), 9);
      end.translate(dir.right(), 9);

      RectSolid.fill(editor, rand, start, end, fill, true, true);

      Coord cursor = new Coord(origin);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir, 10);
      cursor.translate(dir.left(), 10);
      for (int i = 0; i < 5; ++i) {
        pillar(editor, rand, settings, cursor);
        cursor.translate(dir.right(), 4);
      }
    }

  }

  private void pillar(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin) {

    ITheme theme = settings.getTheme();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();

    Coord cursor = new Coord(origin);
    editor.fillDown(rand, cursor, pillar);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      if (editor.isAirBlock(cursor)) {
        stair.setOrientation(dir, true).set(editor, cursor);
      }
    }
  }

  @Override
  public int getSize() {
    return 12;
  }

}
