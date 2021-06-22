package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.Crop;
import com.github.fnar.minecraft.block.decorative.CropBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.BlockType.LAVA_FLOWING;
import static greymerk.roguelike.worldgen.Direction.CARDINAL;
import static greymerk.roguelike.worldgen.Direction.DOWN;
import static greymerk.roguelike.worldgen.Direction.UP;

public class NetherFortressRoom extends DungeonBase {

  public NetherFortressRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public DungeonBase generate(Coord origin, List<Direction> entrances) {
    ThemeBase theme = levelSettings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    StairsBlock stair = theme.getPrimary().getStair();
    SingleBlockBrush liquid = (SingleBlockBrush) theme.getPrimary().getLiquid();

    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-8, -1, -8));
    end.translate(new Coord(8, 6, 8));
    RectHollow.newRect(start, end).fill(worldEditor, wall, false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, 6, -4));
    end.translate(new Coord(4, 6, 4));
    RectSolid.newRect(start, end).fill(worldEditor, wall);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, 7, -3));
    end.translate(new Coord(3, 7, 3));
    RectSolid.newRect(start, end).fill(worldEditor, wall);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-2, 7, -2));
    end.translate(new Coord(2, 7, 2));
    RectSolid.newRect(start, end).fill(worldEditor, liquid);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -3, 4));
    RectSolid.newRect(start, end).fill(worldEditor, wall, false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, -2, -3));
    end.translate(new Coord(3, -2, 3));

    SingleBlockBrush soil = isHotGarden()
        ? BlockType.SOUL_SAND.getBrush()
        : BlockType.FARMLAND.getBrush();
    soil.fill(worldEditor, new RectSolid(start, end), false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    BlockWeightedRandom crop = getCrops();
    RectSolid.newRect(start, end).fill(worldEditor, crop, true, true);
    List<Coord> chests = (new RectSolid(start, end).get());

    List<Coord> chestLocations = chooseRandomLocations(worldEditor.getRandom(origin).nextInt(3) + 1, chests);
    ChestType chestType = getRoomSetting().getChestType().orElse(ChestType.chooseRandomAmong(worldEditor.getRandom(origin), ChestType.RARE_TREASURES));
    worldEditor.getTreasureChestEditor().createChests(chestLocations, false, levelSettings.getDifficulty(origin), entrances.get(0).reverse(), chestType);

    for (Direction dir : CARDINAL) {

      start = origin.copy();
      start.translate(UP, 5);
      start.translate(dir, 4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 6);
      end.translate(dir.clockwise(), 6);
      RectSolid.newRect(start, end).fill(worldEditor, wall);

      start = origin.copy();
      start.translate(UP, 5);
      start.translate(dir, 6);
      end = start.copy();
      start.translate(dir.antiClockwise(), 6);
      end.translate(dir.clockwise(), 6);
      RectSolid.newRect(start, end).fill(worldEditor, wall);

      start = origin.copy();
      start.translate(DOWN);
      start.translate(dir, 4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      stair.setUpsideDown(false).setFacing(dir.reverse()).fill(worldEditor, new RectSolid(start, end));

      cursor = origin.copy();
      cursor.translate(dir, 4);
      cursor.translate(dir.antiClockwise(), 4);
      supportPillar(worldEditor, worldEditor.getRandom(cursor), levelSettings, cursor);

      for (Direction o : dir.orthogonals()) {
        cursor = origin.copy();
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

  private boolean isHotGarden() {
    SingleBlockBrush liquid = (SingleBlockBrush) levelSettings.getTheme().getPrimary().getLiquid();
    return liquid.getBlockType().equals(LAVA_FLOWING);
  }

  private BlockWeightedRandom getCrops() {
    BlockWeightedRandom crops = new BlockWeightedRandom();
    crops.addBlock(SingleBlockBrush.AIR, 3);
    crops.addBlock(selectCrop(), 1);
    return crops;
  }

  private CropBlock selectCrop() {
    if (isHotGarden()) {
      return Crop.NETHER_WART.getBrush();
    } else {
      Crop[] eligibleCrops = {Crop.CARROTS, Crop.POTATOES, Crop.WHEAT};
      return eligibleCrops[worldEditor.getRandom().nextInt(eligibleCrops.length)].getBrush();
    }
  }

  private void supportPillar(WorldEditor editor, Random rand, LevelSettings levelSettings, Coord origin) {

    ThemeBase theme = levelSettings.getTheme();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush lava = LAVA_FLOWING.getBrush();

    Coord start;
    Coord end;
    Coord cursor;

    for (Direction dir : CARDINAL) {
      start = origin.copy();
      start.translate(dir);
      end = start.copy();
      end.translate(UP, 5);
      RectSolid.newRect(start, end).fill(editor, pillar);

      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(UP, 4);
      stair.setUpsideDown(true).setFacing(dir).stroke(editor, cursor);
    }

    start = origin.copy();
    end = start.copy();
    end.translate(UP, 5);
    RectSolid.newRect(start, end).fill(editor, lava);
    List<Coord> core = new RectSolid(start, end).get();
    Coord spawnerLocation = core.get(rand.nextInt(core.size()));
    generateSpawner(spawnerLocation);
  }

  private void pillar(WorldEditor editor, LevelSettings settings, Coord origin) {
    ThemeBase theme = settings.getTheme();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    end = start.copy();
    end.translate(UP, 5);
    RectSolid.newRect(start, end).fill(editor, pillar);

    for (Direction dir : CARDINAL) {
      cursor = origin.copy();
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
