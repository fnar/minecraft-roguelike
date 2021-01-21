package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.decorative.PumpkinBlock;
import com.github.srwaggon.minecraft.block.normal.ColoredBlock;
import com.github.srwaggon.minecraft.block.normal.SlabBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class DungeonTreetho extends DungeonBase {

  public DungeonTreetho(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    ThemeBase theme = levelSettings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    Direction dir = entrances.get(0);


    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-11, -1, -11));
    end.translate(new Coord(11, 8, 11));

    RectHollow.newRect(start, end).fill(worldEditor, wall, false, true);

    BlockBrush birchSlab = SlabBlock.birch().setTop(true).setFullBlock(false).setSeamless(false);
    BlockBrush pumpkin = PumpkinBlock.jackOLantern();
    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-9, 8, -9));
    end.translate(new Coord(9, 8, 9));
    RectSolid.newRect(start, end).fill(worldEditor, birchSlab);
    start.up();
    end.up();
    RectSolid.newRect(start, end).fill(worldEditor, pumpkin);

    cursor = origin.copy();
    cursor.translate(new Coord(0, 8, 0));
    ceiling(worldEditor, levelSettings, cursor);

    cursor = origin.copy();
    treeFarm(worldEditor, cursor, dir);

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(o, 5);
      treeFarm(worldEditor, cursor, dir);
    }


    return this;
  }

  private void treeFarm(WorldEditor editor, Coord origin, Direction dir) {
    Coord cursor;
    Coord start;
    Coord end;

    BlockBrush slab = SlabBlock.sandstone();
    BlockBrush light = PumpkinBlock.jackOLantern();
    BlockBrush sapling = BlockType.BIRCH_SAPLING.getBrush();
    BlockBrush glass = ColoredBlock.stainedGlass().setColor(DyeColor.YELLOW);
    BlockBrush dirt = BlockType.DIRT.getBrush();

    start = origin.copy();
    end = origin.copy();

    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());

    start.translate(dir.reverse(), 7);
    end.translate(dir, 7);

    RectSolid.newRect(start, end).fill(editor, slab);

    cursor = origin.copy();

    cursor.translate(dir.reverse(), 6);
    for (int i = 0; i <= 12; ++i) {
      if (i % 2 == 0) {
        Coord p = cursor.copy();
        if (i % 4 == 0) {
          sapling.stroke(editor, p);
          p.down();
          dirt.stroke(editor, p);
        } else {
          glass.stroke(editor, p);
          p.down();
          light.stroke(editor, p);
        }
      }
      cursor.translate(dir);
    }
  }

  private void ceiling(WorldEditor editor, LevelSettings settings, Coord origin) {

    BlockBrush fill = BlockType.SPRUCE_PLANK.getBrush();

    MinimumSpanningTree tree = new MinimumSpanningTree(editor.getRandom(origin), 7, 3);
    tree.generate(editor, fill, origin);

    for (Direction dir : Direction.CARDINAL) {
      Coord start = origin.copy();
      start.translate(dir, 9);
      Coord end = start.copy();

      start.translate(dir.antiClockwise(), 9);
      end.translate(dir.clockwise(), 9);

      RectSolid.newRect(start, end).fill(editor, fill);

      Coord cursor = origin.copy();
      cursor.down();
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

    Coord cursor = origin.copy();
    editor.fillDown(cursor, pillar);

    for (Direction dir : Direction.CARDINAL) {
      cursor = origin.copy();
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
