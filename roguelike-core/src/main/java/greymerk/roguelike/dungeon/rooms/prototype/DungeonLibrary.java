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
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.carpet;


public class DungeonLibrary extends DungeonBase {

  public DungeonLibrary(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    Random rand = worldEditor.getRandom();
    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    BlockBrush walls = levelSettings.getTheme().getPrimary().getWall();

    StairsBlock stair = levelSettings.getTheme().getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;


    RectSolid.newRect(new Coord(x - 4, y, z - 4), new Coord(x + 4, y + 3, z + 4)).fill(worldEditor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3)).fill(worldEditor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 2, y + 7, z - 2), new Coord(x + 2, y + 7, z + 2)).fill(worldEditor, SingleBlockBrush.AIR);

    RectHollow.newRect(new Coord(x - 5, y, z - 5), new Coord(x + 5, y + 4, z + 5)).fill(worldEditor, walls, false, true);
    RectHollow.newRect(new Coord(x - 4, y + 3, z - 4), new Coord(x + 4, y + 7, z + 4)).fill(worldEditor, walls, false, true);
    RectHollow.newRect(new Coord(x - 3, y + 6, z - 3), new Coord(x + 3, y + 8, z + 3)).fill(worldEditor, walls, false, true);

    RectSolid.newRect(new Coord(x - 5, y - 1, z - 5), new Coord(x + 5, y - 1, z + 5)).fill(worldEditor, levelSettings.getTheme().getPrimary().getFloor());

    start = origin.copy();
    start.translate(Direction.UP, 5);
    BlockType.REDSTONE_BLOCK.getBrush().stroke(worldEditor, start);
    start.translate(Direction.DOWN);
    BlockType.REDSTONE_LAMP_LIT.getBrush().stroke(worldEditor, start);
    start = origin.copy();
    start.translate(Direction.UP, 6);
    end = start.copy();
    end.translate(Direction.UP);
    RectSolid.newRect(start, end).fill(worldEditor, levelSettings.getTheme().getPrimary().getPillar());


    for (Direction dir : Direction.CARDINAL) {

      if (entrances.contains(dir)) {
        door(worldEditor, levelSettings.getTheme(), dir, origin);
      } else {
        if (rand.nextBoolean()) {
          desk(worldEditor, levelSettings.getTheme(), dir, origin);
        } else {
          plants(worldEditor, levelSettings.getTheme(), dir, origin);
        }

      }

      start = origin.copy();
      start.translate(dir, 4);
      start.translate(dir.antiClockwise(), 4);
      end = start.copy();
      end.translate(Direction.UP, 4);
      RectSolid.newRect(start, end).fill(worldEditor, levelSettings.getTheme().getPrimary().getPillar());

      start = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      start.translate(Direction.UP, 3);
      end = start.copy();
      end.translate(Direction.UP, 3);
      RectSolid.newRect(start, end).fill(worldEditor, levelSettings.getTheme().getPrimary().getPillar());

      cursor = end.copy();
      cursor.translate(dir.reverse());
      cursor.translate(dir.clockwise());
      cursor.translate(Direction.UP);
      walls.stroke(worldEditor, cursor);

      for (Direction o : dir.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(dir, 4);
        cursor.translate(o, 3);
        cursor.translate(Direction.UP, 2);

        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.translate(Direction.UP);
        walls.stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.translate(Direction.UP, 3);
        cursor.translate(dir.reverse());
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      }

      // Light fixture related stuff
      cursor = origin.copy();
      cursor.translate(Direction.UP, 4);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(dir, 2);
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.translate(Direction.UP);
      start = cursor.copy();
      end = cursor.copy();
      start.translate(dir.reverse(), 2);
      RectSolid.newRect(start, end).fill(worldEditor, walls);
      cursor.translate(Direction.UP);
      walls.stroke(worldEditor, cursor);
      cursor.translate(Direction.UP);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.translate(dir.reverse());
      stair.setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    }


    return this;
  }

  private void door(WorldEditor editor, ThemeBase theme, Direction dir, Coord pos) {
    Coord start;
    Coord end;

    start = pos.copy();
    start.translate(dir, 7);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(Direction.UP, 2);

    RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getWall());

    Coord cursor = pos.copy();
    cursor.translate(dir, 7);
    theme.getPrimary().getDoor().setFacing(dir).stroke(editor, cursor);

    for (Direction o : dir.orthogonals()) {

      cursor = pos.copy();
      cursor.translate(dir, 5);
      cursor.translate(o);
      cursor.translate(Direction.UP, 2);

      StairsBlock stair = theme.getPrimary().getStair();
      stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
    }
  }

  private void desk(WorldEditor editor, ThemeBase theme, Direction dir, Coord pos) {

    Coord cursor;
    Coord start;
    Coord end;
    BlockBrush shelf = BlockType.BOOKSHELF.getBrush();

    cursor = pos.copy();
    cursor.translate(dir, 5);
    start = cursor.copy();
    end = cursor.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(Direction.UP, 2);
    SingleBlockBrush.AIR.fill(editor, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    theme.getPrimary().getWall().fill(editor, new RectSolid(start, end), false, true);

    for (Direction o : dir.orthogonals()) {
      Coord c = cursor.copy();
      c.translate(o, 2);
      c.translate(Direction.UP, 2);
      theme.getPrimary().getStair().setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
      c.translate(dir);
      c.translate(Direction.DOWN);
      shelf.stroke(editor, c);
      c.translate(Direction.DOWN);
      shelf.stroke(editor, c);
    }

    cursor = pos.copy();
    cursor.translate(dir, 4);

    StairsBlock stair = StairsBlock.oak();
    stair.setUpsideDown(false).setFacing(dir).stroke(editor, cursor);

    cursor.translate(dir);
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(editor, cursor);

    cursor.translate(dir.antiClockwise());
    stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(editor, cursor);

    cursor.translate(dir.clockwise(), 2);
    stair.setUpsideDown(true).setFacing(dir.antiClockwise()).stroke(editor, cursor);

    cursor.translate(Direction.UP);
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, cursor);

    cursor.translate(dir.antiClockwise());
    carpet().setColor(DyeColor.GREEN).stroke(editor, cursor);

    cursor.translate(dir.antiClockwise());
    TorchBlock.torch().setFacing(Direction.UP).stroke(editor, cursor);
  }

  private void plants(WorldEditor editor, ThemeBase theme, Direction dir, Coord origin) {

    Coord cursor = origin.copy();
    cursor.translate(dir, 5);
    Coord start = cursor.copy();
    Coord end = cursor.copy();

    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(Direction.UP, 2);
    SingleBlockBrush.AIR.fill(editor, new RectSolid(start, end));

    start.translate(dir);
    end.translate(dir).up(1);
    theme.getPrimary().getWall().fill(editor, new RectSolid(start, end), false, true);

    for (Direction o : dir.orthogonals()) {
      Coord c = cursor.copy();
      c.translate(o, 2);
      c.translate(Direction.UP, 2);
      theme.getPrimary().getStair().setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
    }

    start = cursor.copy().translate(dir.antiClockwise());
    end = cursor.copy().translate(dir.clockwise());
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
