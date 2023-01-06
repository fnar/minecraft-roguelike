package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.FlowerPotBlock;
import com.github.fnar.minecraft.block.decorative.TallPlant;
import com.github.fnar.minecraft.block.decorative.TorchBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.carpet;


public class LibraryRoom extends BaseRoom {

  public LibraryRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(new Coord(x - 4, y, z - 4), new Coord(x + 4, y + 3, z + 4)));
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3)));
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(new Coord(x - 2, y + 7, z - 2), new Coord(x + 2, y + 7, z + 2)));

    RectHollow.newRect(new Coord(x - 5, y, z - 5), new Coord(x + 5, y + 4, z + 5)).fill(worldEditor, primaryWallBrush(), false, true);
    RectHollow.newRect(new Coord(x - 4, y + 3, z - 4), new Coord(x + 4, y + 7, z + 4)).fill(worldEditor, primaryWallBrush(), false, true);
    RectHollow.newRect(new Coord(x - 3, y + 6, z - 3), new Coord(x + 3, y + 8, z + 3)).fill(worldEditor, primaryWallBrush(), false, true);

    primaryFloorBrush().fill(worldEditor, RectSolid.newRect(new Coord(x - 5, y - 1, z - 5), new Coord(x + 5, y - 1, z + 5)));

    Coord start = origin.copy();
    start.up(5);
    BlockType.REDSTONE_BLOCK.getBrush().stroke(worldEditor, start);
    start.down();
    BlockType.REDSTONE_LAMP_LIT.getBrush().stroke(worldEditor, start);
    start = origin.copy();
    start.up(6);
    Coord end = start.copy();
    end.up();
    primaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));


    for (Direction dir : Direction.CARDINAL) {
      if (!entrances.contains(dir)) {
        if (random().nextBoolean()) {
          desk(worldEditor, theme(), dir, origin);
        } else {
          plants(worldEditor, theme(), dir, origin);
        }
      }

      start = origin.copy();
      start.translate(dir, 4);
      start.translate(dir.antiClockwise(), 4);
      end = start.copy();
      end.up(4);
      primaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));

      start = origin.copy();
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      start.up(3);
      end = start.copy();
      end.up(3);
      primaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));

      Coord cursor = end.copy();
      cursor.translate(dir.reverse());
      cursor.translate(dir.clockwise());
      cursor.up();
      primaryWallBrush().stroke(worldEditor, cursor);

      for (Direction o : dir.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(dir, 4);
        cursor.translate(o, 3);
        cursor.up(2);

        primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.up();
        primaryWallBrush().stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.up(3);
        cursor.translate(dir.reverse());
        primaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      }

      // Light fixture related stuff
      cursor = origin.copy();
      cursor.up(4);
      cursor.translate(dir);
      primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(dir, 2);
      primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.up();
      start = cursor.copy();
      end = cursor.copy();
      start.translate(dir.reverse(), 2);
      primaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));
      cursor.up();
      primaryWallBrush().stroke(worldEditor, cursor);
      cursor.up();
      cursor.translate(dir.reverse());
      primaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
      cursor.translate(dir.reverse());
      primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    }

    generateDoorways(origin, entrances);

    return this;
  }

  private void desk(WorldEditor editor, Theme theme, Direction dir, Coord pos) {

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
    end.up(2);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));
    start.translate(dir);
    end.translate(dir);
    theme.getPrimary().getWall().fill(editor, RectSolid.newRect(start, end), false, true);

    for (Direction o : dir.orthogonals()) {
      Coord c = cursor.copy();
      c.translate(o, 2);
      c.up(2);
      theme.getPrimary().getStair().setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
      c.translate(dir);
      c.down();
      shelf.stroke(editor, c);
      c.down();
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

    cursor.up();
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, cursor);

    cursor.translate(dir.antiClockwise());
    carpet().setColor(DyeColor.GREEN).stroke(editor, cursor);

    cursor.translate(dir.antiClockwise());
    TorchBlock.torch().setFacing(Direction.UP).stroke(editor, cursor);
  }

  private void plants(WorldEditor editor, Theme theme, Direction dir, Coord origin) {

    Coord cursor = origin.copy();
    cursor.translate(dir, 5);
    Coord start = cursor.copy();
    Coord end = cursor.copy();

    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.up(2);
    SingleBlockBrush.AIR.fill(editor, RectSolid.newRect(start, end));

    start.translate(dir);
    end.translate(dir).up();
    theme.getPrimary().getWall().fill(editor, RectSolid.newRect(start, end), false, true);

    for (Direction o : dir.orthogonals()) {
      Coord c = cursor.copy();
      c.translate(o, 2);
      c.up(2);
      theme.getPrimary().getStair().setUpsideDown(true).setFacing(o.reverse()).stroke(editor, c);
    }

    start = cursor.copy().translate(dir.antiClockwise());
    end = cursor.copy().translate(dir.clockwise());
    RectSolid rect = RectSolid.newRect(start, end);
    for (Coord c : rect) {
      TallPlant.placePlant(editor, c, dir.reverse());
    }
  }

  @Override
  public int getSize() {
    return 8;
  }

}
