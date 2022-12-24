package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class DungeonsEnchant extends BaseRoom {

  public DungeonsEnchant(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {

    Direction entrance = getEntrance(entrances).reverse();

    generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(origin, entrances, entrance);

    return this;
  }

  private void generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(Coord origin, List<Direction> entrances, Direction entrance) {
    BlockBrush wall = theme().getPrimary().getWall();
    BlockBrush pillar = pillars();
    BlockBrush panel = stainedHardenedClay().setColor(DyeColor.PURPLE);
    StairsBlock stair = theme().getPrimary().getStair();

    List<Coord> chests = new ArrayList<>();

    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    start.translate(entrance, 5);
    end = start.copy();
    start.translate(new Coord(-2, 0, -2));
    end.translate(new Coord(2, 3, 2));
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy();
    end = origin.copy();
    start.translate(entrance.reverse(), 2);
    start.translate(entrance.antiClockwise(), 4);
    end.translate(entrance, 2);
    end.translate(entrance.clockwise(), 4);
    end.up(3);
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(entrance.reverse(), 2);
    end = start.copy();
    end.translate(entrance.reverse());
    start.translate(entrance.antiClockwise(), 2);
    end.translate(entrance.clockwise(), 2);
    end.up(3);
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.up(4);
    end = start.copy();
    end.translate(entrance, 3);
    start.translate(entrance.antiClockwise(), 3);
    end.translate(entrance.clockwise(), 3);
    wall.fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.down();
    end = start.copy();
    start.translate(entrance.antiClockwise(), 4);
    end.translate(entrance.clockwise(), 4);
    start.translate(entrance.reverse(), 2);
    end.translate(entrance, 2);
    theme().getPrimary().getFloor().fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.translate(entrance.reverse(), 4);
    end = start.copy();
    end.up(3);
    start.translate(entrance.antiClockwise());
    end.translate(entrance.clockwise());
    RectSolid.newRect(start, end).fill(worldEditor, wall, false, true);

    cursor = origin.copy();
    cursor.translate(entrance, 5);
    for (Direction d : Direction.CARDINAL) {
      start = cursor.copy();
      start.translate(d, 2);
      start.translate(d.antiClockwise(), 2);
      end = start.copy();
      end.up(3);
      pillar.fill(worldEditor, RectSolid.newRect(start, end));

      if (d == entrance.reverse()) {
        continue;
      }

      start = cursor.copy();
      start.translate(d, 3);
      end = start.copy();
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      end.up(2);
      panel.fill(worldEditor, RectSolid.newRect(start, end));

      start = cursor.copy();
      start.translate(d, 2);
      start.up(3);
      end = start.copy();
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
      start.translate(d.reverse());
      start.up();
      end.translate(d.reverse());
      end.up();
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
    }

    cursor = origin.copy();
    cursor.translate(entrance, 5);
    cursor.up(4);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    lights().stroke(worldEditor, cursor);
    cursor.down();
    cursor.translate(entrance.reverse());
    stair.setUpsideDown(true).setFacing(entrance).stroke(worldEditor, cursor);
    cursor.translate(entrance.reverse());
    wall.stroke(worldEditor, cursor);
    cursor.translate(entrance.reverse());
    stair.setUpsideDown(true).setFacing(entrance.reverse()).stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.up(5);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    wall.stroke(worldEditor, cursor);

    for (Direction d : Direction.CARDINAL) {

      start = origin.copy();
      start.up(4);
      end = start.copy();
      if (d == entrance) {
        end.translate(d);
      } else {
        end.translate(d, 2);
      }

      SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

      for (Direction o : d.orthogonals()) {
        Coord s = start.copy();
        s.translate(d);
        Coord e = end.copy();
        s.translate(o);
        e.translate(o);
        stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, RectSolid.newRect(s, e));
      }

      Coord s = start.copy();
      s.translate(d);
      Coord e = end.copy();
      s.up();
      e.up();
      wall.fill(worldEditor, RectSolid.newRect(s, e));

      cursor = origin.copy();
      cursor.up(5);
      cursor.translate(d);
      stair.setUpsideDown(true).setFacing(d.reverse()).stroke(worldEditor, cursor);
      cursor.translate(d.antiClockwise());
      wall.stroke(worldEditor, cursor);

    }

    start = origin.copy();
    start.down();
    end = start.copy();
    start.translate(entrance, 3);
    end.translate(entrance, 7);
    start.translate(entrance.antiClockwise(), 2);
    end.translate(entrance.clockwise(), 2);
    panel.fill(worldEditor, RectSolid.newRect(start, end));

    for (Direction o : entrance.orthogonals()) {
      start = origin.copy();
      start.translate(entrance.reverse(), 3);
      start.translate(o, 3);
      end = start.copy();
      end.translate(entrance.reverse());
      end.up(3);
      pillar.fill(worldEditor, RectSolid.newRect(start, end));

      start = origin.copy();
      start.translate(entrance, 3);
      start.translate(o, 3);
      end = start.copy();
      end.up(3);
      wall.fill(worldEditor, RectSolid.newRect(start, end));
      cursor = end.copy();
      cursor.translate(entrance.reverse());
      stair.setUpsideDown(true).setFacing(entrance.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(entrance.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(entrance);
      stair.setUpsideDown(true).setFacing(entrance).stroke(worldEditor, cursor);

      start = origin.copy();
      start.translate(o, 4);
      end = start.copy();
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
      start.up(3);
      end.up(3);
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
      start.translate(o.reverse());
      start.up();
      end.translate(o.reverse());
      end.up();
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, RectSolid.newRect(start, end));

      for (Direction r : o.orthogonals()) {
        start = origin.copy();
        start.translate(o, 4);
        start.translate(r, 2);
        end = start.copy();
        end.up(3);
        pillar.fill(worldEditor, RectSolid.newRect(start, end));
      }

      start = origin.copy();
      start.translate(o, 5);
      end = start.copy();
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      start.up();
      end.up(2);
      panel.fill(worldEditor, RectSolid.newRect(start, end));

      start = origin.copy();
      start.translate(entrance.reverse(), 3);
      start.translate(o, 2);
      end = start.copy();
      end.up(3);
      pillar.fill(worldEditor, RectSolid.newRect(start, end));
      cursor = end.copy();
      cursor.translate(o.reverse());
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(entrance);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(entrance).stroke(worldEditor, cursor);
      cursor.translate(o);
      stair.setUpsideDown(true).setFacing(entrance).stroke(worldEditor, cursor);

      cursor = origin.copy();
      cursor.translate(entrance.reverse(), 2);
      cursor.translate(o);
      cursor.up(4);
      wall.stroke(worldEditor, cursor);
      cursor.translate(entrance);
      stair.setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
    }

    cursor = origin.copy();
    cursor.translate(entrance.reverse(), 2);
    cursor.up(4);
    stair.setUpsideDown(true).setFacing(entrance).stroke(worldEditor, cursor);
    cursor.translate(entrance.reverse());
    wall.stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.translate(entrance, 5);
    BlockType.ENCHANTING_TABLE.getBrush().stroke(worldEditor, cursor);

    generateChest(generateChestLocation(origin.copy().up()), getEntrance(entrances), ChestType.ENCHANTING);
  }

  @Override
  protected Coord generateChestLocation(Coord origin) {
    Direction dir0 = Direction.randomCardinal(random());
    Direction dir1 = dir0.orthogonals()[random().nextBoolean() ? 0 : 1];
    return origin.copy()
        .translate(dir0, 4)
        .translate(dir1, 1);
  }

  @Override
  public boolean isValidLocation(Direction dir, Coord pos) {
    Coord start;
    Coord end;

    start = pos.copy();
    end = start.copy();
    start.translate(dir.reverse(), 4);
    end.translate(dir, 8);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    start.down();
    end.up(2);

    for (Coord c : new RectHollow(start, end)) {
      if (worldEditor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }

  public int getSize() {
    return 6;
  }
}
