package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.carpet;

public class DungeonsMusic extends BaseRoom {

  public DungeonsMusic(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 7;
  }

  public BaseRoom generate(Coord at, List<Direction> entrances) {
    Coord start = at.copy();
    Coord end = at.copy();
    start.translate(new Coord(-6, -1, -6));
    end.translate(new Coord(6, 5, 6));
    RectHollow.newRect(start, end).fill(worldEditor, primaryWallBrush(), false, true);

    start = at.copy();
    end = at.copy();
    start.translate(new Coord(-6, 4, -6));
    end.translate(new Coord(6, 5, 6));
    secondaryWallBrush().fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    end = at.copy();
    start.translate(new Coord(-3, 4, -3));
    end.translate(new Coord(3, 4, 3));
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    end = at.copy();
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    secondaryFloorBrush().fill(worldEditor, RectSolid.newRect(start, end));

    for (int i = 2; i >= 0; --i) {
      start = at.copy();
      end = at.copy();
      start.translate(new Coord(-i - 1, 0, -i - 1));
      end.translate(new Coord(i + 1, 0, i + 1));
      BlockBrush carpet = carpet().setColor(DyeColor.chooseRandom(random()));
      carpet.fill(worldEditor, RectSolid.newRect(start, end));
    }

    Coord cursor;
    for (Direction dir : Direction.CARDINAL) {

      cursor = at.copy();
      cursor.translate(dir, 5);
      cursor.up(3);
      secondaryWallBrush().stroke(worldEditor, cursor);
      cursor.translate(dir.reverse());
      secondaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      cursor = at.copy();
      cursor.translate(dir, 5);
      cursor.translate(dir.antiClockwise(), 5);
      pillar(cursor);

      start = at.copy();
      start.up(4);
      start.translate(dir, 3);
      end = start.copy();
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir.clockwise(), 3);
      secondaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));

      cursor = at.copy();
      cursor.up(4);
      cursor.translate(dir);
      secondaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
      cursor.translate(dir);
      secondaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      for (Direction o : dir.orthogonals()) {
        cursor = at.copy();
        cursor.translate(dir, 5);
        cursor.translate(o, 2);
        pillar(cursor);

        cursor = at.copy();
        cursor.translate(dir, 4);
        cursor.up(3);
        cursor.translate(o);
        secondaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

        cursor = at.copy();
        cursor.translate(dir, 5);
        cursor.translate(o, 3);
        secondaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o);
        secondaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.up(2);
        secondaryStairBrush().setUpsideDown(true).setFacing(o.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        secondaryStairBrush().setUpsideDown(true).setFacing(o).stroke(worldEditor, cursor);
        cursor.up();
        secondaryWallBrush().stroke(worldEditor, cursor);
        cursor.translate(o);
        secondaryWallBrush().stroke(worldEditor, cursor);
        cursor.translate(dir.reverse());
        secondaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);
        cursor.translate(o.reverse());
        secondaryStairBrush().setUpsideDown(true).setFacing(dir.reverse()).stroke(worldEditor, cursor);

      }
    }

    BlockType.JUKEBOX.getBrush().stroke(worldEditor, at);

    cursor = at.copy();
    cursor.up(4);
    primaryLightBrush().stroke(worldEditor, cursor);

    Coord coord = generateChestLocation(at.copy().up());
    new TreasureChest(coord, worldEditor)
        .withChestType(getChestTypeOrUse(ChestType.MUSIC))
        .withFacing(getEntrance(entrances).reverse())
        .withTrap(false)
        .stroke(worldEditor, coord);

    generateDoorways(at, entrances);

    return this;
  }

  @Override
  protected Coord generateChestLocation(Coord origin) {
    Direction dir0 = Direction.randomCardinal(random());
    Direction dir1 = dir0.orthogonals()[random().nextBoolean() ? 0 : 1];
    return origin.copy()
        .translate(dir0, 5)
        .translate(dir1, 3 + (random().nextBoolean() ? 1 : 0));
  }

  private void pillar(Coord origin) {
    Coord start = origin.copy();
    Coord end = start.copy();
    end.up(2);
    secondaryPillarBrush().fill(worldEditor, RectSolid.newRect(start, end));
    for (Direction dir : Direction.CARDINAL) {
      Coord cursor = end.copy();
      cursor.translate(dir);
      secondaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor, true, false);
      cursor.up();
      secondaryWallBrush().stroke(worldEditor, cursor);
    }
  }

}
