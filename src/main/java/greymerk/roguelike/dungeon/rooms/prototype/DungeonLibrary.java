package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.TallPlant;
import greymerk.roguelike.worldgen.blocks.Trapdoor;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class DungeonLibrary extends DungeonBase {

  public DungeonLibrary(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    IBlockFactory walls = settings.getTheme().getPrimary().getWall();

    IStair stair = settings.getTheme().getPrimary().getStair();

    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor;
    Coord start;
    Coord end;


    RectSolid.fill(editor, rand, new Coord(x - 4, y, z - 4), new Coord(x + 4, y + 3, z + 4), air);
    RectSolid.fill(editor, rand, new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3), air);
    RectSolid.fill(editor, rand, new Coord(x - 2, y + 7, z - 2), new Coord(x + 2, y + 7, z + 2), air);

    RectHollow.fill(editor, rand, new Coord(x - 5, y, z - 5), new Coord(x + 5, y + 4, z + 5), walls, false, true);
    RectHollow.fill(editor, rand, new Coord(x - 4, y + 3, z - 4), new Coord(x + 4, y + 7, z + 4), walls, false, true);
    RectHollow.fill(editor, rand, new Coord(x - 3, y + 6, z - 3), new Coord(x + 3, y + 8, z + 3), walls, false, true);

    RectSolid.fill(editor, rand, new Coord(x - 5, y - 1, z - 5), new Coord(x + 5, y - 1, z + 5), settings.getTheme().getPrimary().getFloor(), true, true);

    start = new Coord(origin);
    start.translate(Cardinal.UP, 5);
    BlockType.get(BlockType.REDSTONE_BLOCK).set(editor, start);
    start.translate(Cardinal.DOWN);
    BlockType.get(BlockType.REDSTONE_LAMP_LIT).set(editor, start);
    start = new Coord(origin);
    start.translate(Cardinal.UP, 6);
    end = new Coord(start);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, settings.getTheme().getPrimary().getPillar(), true, true);


    for (Cardinal dir : Cardinal.directions) {

      if (Arrays.asList(entrances).contains(dir)) {
        door(editor, rand, settings.getTheme(), dir, origin);
      } else {
        if (rand.nextBoolean()) {
          desk(editor, rand, settings.getTheme(), dir, origin);
        } else {
          plants(editor, rand, settings.getTheme(), dir, origin);
        }

      }

      start = new Coord(origin);
      start.translate(dir, 4);
      start.translate(dir.antiClockwise(), 4);
      end = new Coord(start);
      end.translate(Cardinal.UP, 4);
      RectSolid.fill(editor, rand, start, end, settings.getTheme().getPrimary().getPillar(), true, true);

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      start.translate(Cardinal.UP, 3);
      end = new Coord(start);
      end.translate(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, settings.getTheme().getPrimary().getPillar(), true, true);

      cursor = new Coord(end);
      cursor.translate(dir.reverse());
      cursor.translate(dir.clockwise());
      cursor.translate(Cardinal.UP);
      walls.set(editor, rand, cursor);

      for (Cardinal o : dir.orthogonal()) {
        cursor = new Coord(origin);
        cursor.translate(dir, 4);
        cursor.translate(o, 3);
        cursor.translate(Cardinal.UP, 2);

        stair.setOrientation(o.reverse(), true).set(editor, cursor);
        cursor.translate(Cardinal.UP);
        walls.set(editor, rand, cursor);
        cursor.translate(o.reverse());
        stair.setOrientation(o.reverse(), true).set(editor, cursor);
        cursor.translate(Cardinal.UP, 3);
        cursor.translate(dir.reverse());
        stair.setOrientation(o.reverse(), true).set(editor, cursor);
      }

      // Light fixture related stuff
      cursor = new Coord(origin);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir);
      stair.setOrientation(dir, true).set(editor, cursor);
      cursor.translate(dir, 2);
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(Cardinal.UP);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.reverse(), 2);
      RectSolid.fill(editor, rand, start, end, walls);
      cursor.translate(Cardinal.UP);
      walls.set(editor, rand, cursor);
      cursor.translate(Cardinal.UP);
      cursor.translate(dir.reverse());
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(dir.reverse());
      stair.setOrientation(dir, true).set(editor, cursor);
    }


    return this;
  }

  private void door(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord pos) {
    Coord start;
    Coord end;

    start = new Coord(pos);
    start.translate(dir, 7);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(Cardinal.UP, 2);

    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());

    Coord cursor = new Coord(pos);
    cursor.translate(dir, 7);
    theme.getPrimary().getDoor().generate(editor, cursor, dir, false);

    for (Cardinal o : dir.orthogonal()) {

      cursor = new Coord(pos);
      cursor.translate(dir, 5);
      cursor.translate(o);
      cursor.translate(Cardinal.UP, 2);

      IStair stair = theme.getPrimary().getStair();
      stair.setOrientation(dir.reverse(), true).set(editor, cursor);
      cursor.translate(dir);
      stair.setOrientation(o.reverse(), true).set(editor, cursor);
    }
  }

  private void desk(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord pos) {

    Coord cursor;
    Coord start;
    Coord end;
    MetaBlock shelf = BlockType.get(BlockType.SHELF);

    cursor = new Coord(pos);
    cursor.translate(dir, 5);
    start = new Coord(cursor);
    end = new Coord(cursor);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(Cardinal.UP, 2);
    BlockType.get(BlockType.AIR).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    theme.getPrimary().getWall().fill(editor, rand, new RectSolid(start, end), false, true);

    for (Cardinal o : dir.orthogonal()) {
      Coord c = new Coord(cursor);
      c.translate(o, 2);
      c.translate(Cardinal.UP, 2);
      theme.getPrimary().getStair().setOrientation(o.reverse(), true).set(editor, c);
      c.translate(dir);
      c.translate(Cardinal.DOWN);
      shelf.set(editor, c);
      c.translate(Cardinal.DOWN);
      shelf.set(editor, c);
    }

    cursor = new Coord(pos);
    cursor.translate(dir, 4);

    IStair stair = new MetaStair(StairType.OAK);
    stair.setOrientation(dir, false).set(editor, cursor);

    cursor.translate(dir);
    stair.setOrientation(dir.reverse(), true).set(editor, cursor);

    cursor.translate(dir.antiClockwise());
    stair.setOrientation(dir.clockwise(), true).set(editor, cursor);

    cursor.translate(dir.clockwise(), 2);
    stair.setOrientation(dir.antiClockwise(), true).set(editor, cursor);

    cursor.translate(Cardinal.UP);
    FlowerPot.generate(editor, rand, cursor);

    cursor.translate(dir.antiClockwise());
    ColorBlock.get(ColorBlock.CARPET, DyeColor.GREEN).set(editor, cursor);

    cursor.translate(dir.antiClockwise());
    Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
  }

  private void plants(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    cursor.translate(dir, 5);
    start = new Coord(cursor);
    end = new Coord(cursor);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.antiClockwise(), 2);
    end.translate(Cardinal.UP, 2);
    BlockType.get(BlockType.AIR).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir);
    end.translate(dir);
    theme.getPrimary().getWall().fill(editor, rand, new RectSolid(start, end), false, true);

    for (Cardinal o : dir.orthogonal()) {
      Coord c = new Coord(cursor);
      c.translate(o, 2);
      c.translate(Cardinal.UP, 2);
      theme.getPrimary().getStair().setOrientation(o.reverse(), true).set(editor, c);
    }

    start = new Coord(cursor);
    end = new Coord(cursor);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    RectSolid rect = new RectSolid(start, end);
    for (Coord c : rect) {
      plant(editor, rand, theme, c);
    }
  }

  private void plant(WorldEditor editor, Random rand, ITheme theme, Coord origin) {
    Coord cursor;
    BlockType.get(BlockType.DIRT_PODZOL).set(editor, origin);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.translate(dir);
      Trapdoor.get(Trapdoor.OAK, dir.reverse(), true, true).set(editor, rand, cursor, true, false);
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    TallPlant[] plants = new TallPlant[]{TallPlant.FERN, TallPlant.ROSE, TallPlant.PEONY};
    TallPlant.generate(editor, plants[rand.nextInt(plants.length)], cursor);
  }


  @Override
  public int getSize() {
    return 8;
  }

}
