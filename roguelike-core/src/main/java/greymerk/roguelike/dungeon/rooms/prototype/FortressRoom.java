package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.decorative.Crop;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.Cardinal.DIRECTIONS;
import static greymerk.roguelike.worldgen.Cardinal.DOWN;
import static greymerk.roguelike.worldgen.Cardinal.UP;

public class FortressRoom extends DungeonBase {

  public FortressRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {
    ThemeBase theme = levelSettings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush liquid = theme.getPrimary().getLiquid();
    BlockWeightedRandom netherwart = new BlockWeightedRandom();
    netherwart.addBlock(SingleBlockBrush.AIR, 3);
    netherwart.addBlock(Crop.NETHER_WART.getBrush(), 1);

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-8, -1, -8));
    end.translate(new Coord(8, 6, 8));
    RectHollow.newRect(start, end).fill(worldEditor, wall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, 6, -4));
    end.translate(new Coord(4, 6, 4));
    RectSolid.newRect(start, end).fill(worldEditor, wall);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, 7, -3));
    end.translate(new Coord(3, 7, 3));
    RectSolid.newRect(start, end).fill(worldEditor, wall);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-2, 7, -2));
    end.translate(new Coord(2, 7, 2));
    RectSolid.newRect(start, end).fill(worldEditor, liquid);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -3, 4));
    RectSolid.newRect(start, end).fill(worldEditor, wall, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, -2, -3));
    end.translate(new Coord(3, -2, 3));
    BlockType.SOUL_SAND.getBrush().fill(worldEditor, new RectSolid(start, end), false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    RectSolid.newRect(start, end).fill(worldEditor, netherwart, false, true);
    List<Coord> chests = (new RectSolid(start, end).get());

    List<Coord> chestLocations = chooseRandomLocations(worldEditor.getRandom(), worldEditor.getRandom().nextInt(3) + 1, chests);
    worldEditor.getTreasureChestEditor().createChests(levelSettings.getDifficulty(origin), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(worldEditor.getRandom(), ChestType.RARE_TREASURES)));

    for (Cardinal dir : DIRECTIONS) {

      start = new Coord(origin);
      start.translate(UP, 5);
      start.translate(dir, 4);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 6);
      end.translate(dir.clockwise(), 6);
      RectSolid.newRect(start, end).fill(worldEditor, wall);

      start = new Coord(origin);
      start.translate(UP, 5);
      start.translate(dir, 6);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 6);
      end.translate(dir.clockwise(), 6);
      RectSolid.newRect(start, end).fill(worldEditor, wall);

      start = new Coord(origin);
      start.translate(DOWN);
      start.translate(dir, 4);
      end = new Coord(start);
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      stair.setUpsideDown(false).setFacing(dir.reverse()).fill(worldEditor, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(dir, 4);
      cursor.translate(dir.antiClockwise(), 4);
      supportPillar(worldEditor, worldEditor.getRandom(), levelSettings, cursor);

      for (Cardinal o : dir.orthogonals()) {
        cursor = new Coord(origin);
        cursor.translate(dir, 7);
        cursor.translate(o, 2);
        pillar(worldEditor, levelSettings, cursor);
        cursor.translate(o);
        cursor.translate(o);
        cursor.translate(o);
        pillar(worldEditor, levelSettings, cursor);
      }
    }

    return this;
  }

  private void supportPillar(WorldEditor editor, Random rand, LevelSettings levelSettings, Coord origin) {

    ThemeBase theme = levelSettings.getTheme();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush lava = BlockType.LAVA_FLOWING.getBrush();

    Coord start;
    Coord end;
    Coord cursor;

    for (Cardinal dir : DIRECTIONS) {
      start = new Coord(origin);
      start.translate(dir);
      end = new Coord(start);
      end.translate(UP, 5);
      RectSolid.newRect(start, end).fill(editor, pillar);

      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(UP, 4);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    }

    start = new Coord(origin);
    end = new Coord(start);
    end.translate(UP, 5);
    RectSolid.newRect(start, end).fill(editor, lava);
    List<Coord> core = new RectSolid(start, end).get();
    Coord spawnerLocation = core.get(rand.nextInt(core.size()));
    int difficulty = levelSettings.getDifficulty(spawnerLocation);
    generateSpawner(editor, spawnerLocation, difficulty, levelSettings.getSpawners());
  }

  private void pillar(WorldEditor editor, LevelSettings settings, Coord origin) {
    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(start);
    end.translate(UP, 5);
    RectSolid.newRect(start, end).fill(editor, pillar);

    for (Cardinal dir : DIRECTIONS) {
      cursor = new Coord(origin);
      cursor.translate(UP, 4);
      cursor.translate(dir);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor, true, false);
      cursor.translate(UP);
      wall.stroke(editor, cursor);
    }


  }

  public int getSize() {
    return 10;
  }


}
