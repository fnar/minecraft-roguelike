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
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    end = origin.copy();
    start.translate(entrance.reverse(), 2);
    start.translate(entrance.antiClockwise(), 4);
    end.translate(entrance, 2);
    end.translate(entrance.clockwise(), 4);
    end.up(3);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    start.translate(entrance.reverse(), 2);
    end = start.copy();
    end.translate(entrance.reverse());
    start.translate(entrance.antiClockwise(), 2);
    end.translate(entrance.clockwise(), 2);
    end.up(3);
    RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

    start = origin.copy();
    start.up(4);
    end = start.copy();
    end.translate(entrance, 3);
    start.translate(entrance.antiClockwise(), 3);
    end.translate(entrance.clockwise(), 3);
    RectSolid.newRect(start, end).fill(worldEditor, wall);

    start = origin.copy();
    start.down();
    end = start.copy();
    start.translate(entrance.antiClockwise(), 4);
    end.translate(entrance.clockwise(), 4);
    start.translate(entrance.reverse(), 2);
    end.translate(entrance, 2);
    theme().getPrimary().getFloor().fill(worldEditor, new RectSolid(start, end));

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
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      if (d == entrance.reverse()) {
        continue;
      }

      start = cursor.copy();
      start.translate(d, 3);
      end = start.copy();
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      end.up(2);
      RectSolid.newRect(start, end).fill(worldEditor, panel);

      start = cursor.copy();
      start.translate(d, 2);
      start.up(3);
      end = start.copy();
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(worldEditor, new RectSolid(start, end));
      start.translate(d.reverse());
      start.up();
      end.translate(d.reverse());
      end.up();
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(worldEditor, new RectSolid(start, end));
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

      RectSolid.newRect(start, end).fill(worldEditor, SingleBlockBrush.AIR);

      for (Direction o : d.orthogonals()) {
        Coord s = start.copy();
        s.translate(d);
        Coord e = end.copy();
        s.translate(o);
        e.translate(o);
        stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, new RectSolid(s, e));
      }

      Coord s = start.copy();
      s.translate(d);
      Coord e = end.copy();
      s.up();
      e.up();
      RectSolid.newRect(s, e).fill(worldEditor, wall);

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
    RectSolid.newRect(start, end).fill(worldEditor, panel);

    for (Direction o : entrance.orthogonals()) {
      start = origin.copy();
      start.translate(entrance.reverse(), 3);
      start.translate(o, 3);
      end = start.copy();
      end.translate(entrance.reverse());
      end.up(3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);

      start = origin.copy();
      start.translate(entrance, 3);
      start.translate(o, 3);
      end = start.copy();
      end.up(3);
      RectSolid.newRect(start, end).fill(worldEditor, wall);
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
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, new RectSolid(start, end));
      start.up(3);
      end.up(3);
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, new RectSolid(start, end));
      start.translate(o.reverse());
      start.up();
      end.translate(o.reverse());
      end.up();
      stair.setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, new RectSolid(start, end));

      for (Direction r : o.orthogonals()) {
        start = origin.copy();
        start.translate(o, 4);
        start.translate(r, 2);
        end = start.copy();
        end.up(3);
        RectSolid.newRect(start, end).fill(worldEditor, pillar);
      }

      start = origin.copy();
      start.translate(o, 5);
      end = start.copy();
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      start.up();
      end.up(2);
      RectSolid.newRect(start, end).fill(worldEditor, panel);

      start = origin.copy();
      start.translate(entrance.reverse(), 3);
      start.translate(o, 2);
      end = start.copy();
      end.up(3);
      RectSolid.newRect(start, end).fill(worldEditor, pillar);
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

      start = origin.copy();
      start.up();
      start.translate(o, 4);
      end = start.copy();
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      chests.addAll(new RectSolid(start, end).get());
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

    generateChest(Coord.randomFrom(chests, random()), getEntrance(entrances), ChestType.ENCHANTING);
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
