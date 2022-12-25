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
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class DungeonsEnchant extends BaseRoom {

  public DungeonsEnchant(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.size = 8;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {
    Direction entrance = getEntrance(entrances);

    generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(at, entrances, entrance.reverse());

    return this;
  }

  private void generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(Coord at, List<Direction> entrances, Direction entrance) {
    Coord start = at.copy();
    start.translate(entrance, 5);
    Coord end = start.copy();
    start.translate(new Coord(-2, 0, -2));
    end.translate(new Coord(2, 3, 2));
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    end = at.copy();
    start.translate(entrance.reverse(), 2);
    start.translate(entrance.antiClockwise(), 4);
    end.translate(entrance, 2);
    end.translate(entrance.clockwise(), 4);
    end.up(3);
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    start.translate(entrance.reverse(), 2);
    end = start.copy();
    end.translate(entrance.reverse());
    start.translate(entrance.antiClockwise(), 2);
    end.translate(entrance.clockwise(), 2);
    end.up(3);
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    start.up(4);
    end = start.copy();
    end.translate(entrance, 3);
    start.translate(entrance.antiClockwise(), 3);
    end.translate(entrance.clockwise(), 3);
    walls().fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    start.down();
    end = start.copy();
    start.translate(entrance.antiClockwise(), 4);
    end.translate(entrance.clockwise(), 4);
    start.translate(entrance.reverse(), 2);
    end.translate(entrance, 2);
    theme().getPrimary().getFloor().fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    start.translate(entrance.reverse(), 4);
    end = start.copy();
    end.up(3);
    start.translate(entrance.antiClockwise());
    end.translate(entrance.clockwise());
    RectSolid.newRect(start, end).fill(worldEditor, walls(), false, true);

    Coord cursor = at.copy();
    cursor.translate(entrance, 5);
    for (Direction d : Direction.CARDINAL) {
      start = cursor.copy();
      start.translate(d, 2);
      start.translate(d.antiClockwise(), 2);
      end = start.copy();
      end.up(3);
      pillars().fill(worldEditor, RectSolid.newRect(start, end));

      if (d == entrance.reverse()) {
        continue;
      }

      start = cursor.copy();
      start.translate(d, 3);
      end = start.copy();
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      end.up(2);
      stainedHardenedClay().setColor(DyeColor.PURPLE).fill(worldEditor, RectSolid.newRect(start, end));

      start = cursor.copy();
      start.translate(d, 2);
      start.up(3);
      end = start.copy();
      start.translate(d.antiClockwise());
      end.translate(d.clockwise());
      stairs().setUpsideDown(true).setFacing(d.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
      start.translate(d.reverse());
      start.up();
      end.translate(d.reverse());
      end.up();
      stairs().setUpsideDown(true).setFacing(d.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
    }

    cursor = at.copy();
    cursor.translate(entrance, 5);
    cursor.up(4);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    lights().stroke(worldEditor, cursor);
    cursor.down();
    cursor.translate(entrance.reverse());
    stairs().setUpsideDown(true).setFacing(entrance).stroke(worldEditor, cursor);
    cursor.translate(entrance.reverse());
    walls().stroke(worldEditor, cursor);
    cursor.translate(entrance.reverse());
    stairs().setUpsideDown(true).setFacing(entrance.reverse()).stroke(worldEditor, cursor);

    cursor = at.copy();
    cursor.up(5);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    walls().stroke(worldEditor, cursor);

    for (Direction d : Direction.CARDINAL) {

      start = at.copy();
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
        stairs().setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, RectSolid.newRect(s, e));
      }

      Coord s = start.copy();
      s.translate(d);
      Coord e = end.copy();
      s.up();
      e.up();
      walls().fill(worldEditor, RectSolid.newRect(s, e));

      cursor = at.copy();
      cursor.up(5);
      cursor.translate(d);
      stairs().setUpsideDown(true).setFacing(d.reverse()).stroke(worldEditor, cursor);
      cursor.translate(d.antiClockwise());
      walls().stroke(worldEditor, cursor);

    }

    start = at.copy();
    start.down();
    end = start.copy();
    start.translate(entrance, 3);
    end.translate(entrance, 7);
    start.translate(entrance.antiClockwise(), 2);
    end.translate(entrance.clockwise(), 2);
    stainedHardenedClay().setColor(DyeColor.PURPLE).fill(worldEditor, RectSolid.newRect(start, end));

    for (Direction o : entrance.orthogonals()) {
      start = at.copy();
      start.translate(entrance.reverse(), 3);
      start.translate(o, 3);
      end = start.copy();
      end.translate(entrance.reverse());
      end.up(3);
      pillars().fill(worldEditor, RectSolid.newRect(start, end));

      start = at.copy();
      start.translate(entrance, 3);
      start.translate(o, 3);
      end = start.copy();
      end.up(3);
      walls().fill(worldEditor, RectSolid.newRect(start, end));
      cursor = end.copy();
      cursor.translate(entrance.reverse());
      stairs().setUpsideDown(true).setFacing(entrance.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      stairs().setUpsideDown(true).setFacing(entrance.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o.reverse());
      stairs().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(entrance);
      stairs().setUpsideDown(true).setFacing(entrance).stroke(worldEditor, cursor);

      start = at.copy();
      start.translate(o, 4);
      end = start.copy();
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      stairs().setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
      start.up(3);
      end.up(3);
      stairs().setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
      start.translate(o.reverse());
      start.up();
      end.translate(o.reverse());
      end.up();
      stairs().setUpsideDown(true).setFacing(o.reverse()).fill(worldEditor, RectSolid.newRect(start, end));

      for (Direction r : o.orthogonals()) {
        start = at.copy();
        start.translate(o, 4);
        start.translate(r, 2);
        end = start.copy();
        end.up(3);
        pillars().fill(worldEditor, RectSolid.newRect(start, end));
      }

      start = at.copy();
      start.translate(o, 5);
      end = start.copy();
      start.translate(o.antiClockwise());
      end.translate(o.clockwise());
      start.up();
      end.up(2);
      stainedHardenedClay().setColor(DyeColor.PURPLE).fill(worldEditor, RectSolid.newRect(start, end));

      start = at.copy();
      start.translate(entrance.reverse(), 3);
      start.translate(o, 2);
      end = start.copy();
      end.up(3);
      pillars().fill(worldEditor, RectSolid.newRect(start, end));
      cursor = end.copy();
      cursor.translate(o.reverse());
      stairs().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(entrance);
      stairs().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
      cursor.translate(o);
      stairs().setUpsideDown(true).setFacing(entrance).stroke(worldEditor, cursor);
      cursor.translate(o);
      stairs().setUpsideDown(true).setFacing(entrance).stroke(worldEditor, cursor);

      cursor = at.copy();
      cursor.translate(entrance.reverse(), 2);
      cursor.translate(o);
      cursor.up(4);
      walls().stroke(worldEditor, cursor);
      cursor.translate(entrance);
      stairs().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
    }

    cursor = at.copy();
    cursor.translate(entrance.reverse(), 2);
    cursor.up(4);
    stairs().setUpsideDown(true).setFacing(entrance).stroke(worldEditor, cursor);
    cursor.translate(entrance.reverse());
    walls().stroke(worldEditor, cursor);

    cursor = at.copy();
    cursor.translate(entrance, 5);
    BlockType.ENCHANTING_TABLE.getBrush().stroke(worldEditor, cursor);

    generateChest(generateChestLocation(at.copy().up()), getEntrance(entrances), ChestType.ENCHANTING);
  }

  @Override
  protected Coord generateChestLocation(Coord origin) {
    Direction dir0 = Direction.randomCardinal(random());
    Direction dir1 = dir0.orthogonals()[random().nextBoolean() ? 0 : 1];
    return origin.copy()
        .translate(dir0, 4)
        .translate(dir1, 1);
  }

}
