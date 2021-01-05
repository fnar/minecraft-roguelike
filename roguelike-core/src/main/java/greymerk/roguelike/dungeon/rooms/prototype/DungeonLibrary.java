package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.decorative.FlowerPotBlock;
import com.github.srwaggon.roguelike.worldgen.block.decorative.TallPlant;
import com.github.srwaggon.roguelike.worldgen.block.decorative.TorchBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.carpet;


public class DungeonLibrary extends DungeonBase {

  public DungeonLibrary(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    Random rand = editor.getRandom();
    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    BlockBrush walls = settings.getTheme().getPrimary().getWall();

    StairsBlock stair = settings.getTheme().getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;


    RectSolid.newRect(new Coord(x - 4, y, z - 4), new Coord(x + 4, y + 3, z + 4)).fill(editor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3)).fill(editor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 2, y + 7, z - 2), new Coord(x + 2, y + 7, z + 2)).fill(editor, SingleBlockBrush.AIR);

    RectHollow.fill(editor, new Coord(x - 5, y, z - 5), new Coord(x + 5, y + 4, z + 5), walls, false, true);
    RectHollow.fill(editor, new Coord(x - 4, y + 3, z - 4), new Coord(x + 4, y + 7, z + 4), walls, false, true);
    RectHollow.fill(editor, new Coord(x - 3, y + 6, z - 3), new Coord(x + 3, y + 8, z + 3), walls, false, true);

    RectSolid.newRect(new Coord(x - 5, y - 1, z - 5), new Coord(x + 5, y - 1, z + 5)).fill(editor, settings.getTheme().getPrimary().getFloor());

    start = new Coord(origin);
    start.translate(Cardinal.UP, 5);
    BlockType.REDSTONE_BLOCK.getBrush().stroke(editor, start);
    start.translate(Cardinal.DOWN);
    BlockType.REDSTONE_LAMP_LIT.getBrush().stroke(editor, start);
    start = new Coord(origin);
    start.translate(Cardinal.UP, 6);
    end = new Coord(start);
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(editor, settings.getTheme().getPrimary().getPillar());


    for (Cardinal dir : Cardinal.DIRECTIONS) {

      if (entrances.contains(dir)) {
        door(editor, settings.getTheme(), dir, origin);
      } else {
        if (rand.nextBoolean()) {
          desk(editor, settings.getTheme(), dir, origin);
        } else {
          plants(editor, settings.getTheme(), dir, origin);
        }

      }

      start = new Coord(origin);
      start.translate(dir, 4);
      start.translate(dir.antiClockwise(), 4);
      end = new Coord(start);
      end.translate(Cardinal.UP, 4);
      RectSolid.newRect(start, end).fill(editor, settings.getTheme().getPrimary().getPillar());

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.newRect(start, end).fill(editor, settings.getTheme().getPrimary().getPillar());

      cursor = new Coord(end);
      cursor.translate(dir.reverse());
      cursor.translate(dir.clockwise());
      cursor.translate(Cardinal.UP);
      walls.stroke(editor, cursor);

      for (Cardinal o : dir.orthogonals()) {
        cursor = new Coord(origin);
        cursor.translate(dir, 4);
        cursor.translate(o, 3);
        cursor.translate(Cardinal.UP, 2);

        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        walls.stroke(editor, cursor);
        cursor.translate(o.reverse());
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
        cursor.translate(Cardinal.UP, 3);
        cursor.translate(dir.reverse());
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      }

      // Light fixture related stuff
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
      cursor.translate(dir, 2);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.reverse(), 2);
      RectSolid.newRect(start, end).fill(editor, walls);
      cursor.translate(Cardinal.UP);
      walls.stroke(editor, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    }


    return this;
  }

  private void door(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord pos) {
    Coord start;
    Coord end;

    start = new Coord(pos);
    start.translate(dir, 7);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(Cardinal.UP, 2);

    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getWall());

    Coord cursor = new Coord(pos);
    cursor.translate(dir, 7);
    theme.getPrimary().getDoor().setFacing(dir).stroke(editor, cursor);

    for (Cardinal o : dir.orthogonals()) {

      cursor = new Coord(pos);
      cursor.translate(dir, 5);
      cursor.translate(o);
      cursor.translate(Cardinal.UP, 2);

      StairsBlock stair = theme.getPrimary().getStair();
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
    }
  }

  private void desk(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord pos) {

    Coord cursor;
    Coord start;
    Coord end;
    BlockBrush shelf = BlockType.BOOKSHELF.getBrush();

    cursor = new Coord(pos);
    cursor.translate(dir, 5);
    start = new Coord(cursor);
    end = new Coord(cursor);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(Cardinal.UP, 2);
    SingleBlockBrush.AIR.fill(editor, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    theme.getPrimary().getWall().fill(editor, new RectSolid(start, end), false, true);

    for (Cardinal o : dir.orthogonals()) {
      Coord c = new Coord(cursor);
      c.translate(o, 2);
      c.translate(Cardinal.UP, 2);
      theme.getPrimary().getStair().setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
      c.translate(dir);
      c.translate(Cardinal.DOWN);
      shelf.stroke(editor, c);
      c.translate(Cardinal.DOWN);
      shelf.stroke(editor, c);
    }

    cursor = new Coord(pos);
    cursor.translate(dir, 4);

    StairsBlock stair = StairsBlock.oak();
    stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);

    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    cursor.translate(dir.antiClockwise());
    stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);

    cursor.translate(dir.clockwise(), 2);
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);

    cursor.translate(Cardinal.UP);
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, cursor);

    cursor.translate(dir.antiClockwise());
    carpet().setColor(DyeColor.GREEN).stroke(editor, cursor);

    cursor.translate(dir.antiClockwise());
    TorchBlock.torch().setFacing(Cardinal.UP).stroke(editor, cursor);
  }

  private void plants(WorldEditor editor, ThemeBase theme, Cardinal dir, Coord origin) {

    Coord cursor = new Coord(origin);
    cursor.translate(dir, 5);
    Coord start = new Coord(cursor);
    Coord end = new Coord(cursor);

    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(Cardinal.UP, 2);
    SingleBlockBrush.AIR.fill(editor, new RectSolid(start, end));

    start.translate(dir);
    end.translate(dir).up(1);
    theme.getPrimary().getWall().fill(editor, new RectSolid(start, end), false, true);

    for (Cardinal o : dir.orthogonals()) {
      Coord c = new Coord(cursor);
      c.translate(o, 2);
      c.translate(Cardinal.UP, 2);
      theme.getPrimary().getStair().setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
    }

    start = new Coord(cursor).translate(dir.antiClockwise());
    end = new Coord(cursor).translate(dir.clockwise());
    RectSolid rect = new RectSolid(start, end);
    for (Coord c : rect) {
      TallPlant.placePlant(editor, c, dir.reverse());
    }
  }

  @Override
  public int getSize() {
    return 8;
  }

}
