package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.CropBlock;
import com.github.fnar.minecraft.material.Crop;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
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

public class NetherFortressRoom extends BaseRoom {

  public NetherFortressRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Coord start = origin.copy();
    Coord end = origin.copy();
    start.translate(new Coord(-8, -1, -8));
    end.translate(new Coord(8, 6, 8));
    RectHollow.newRect(start, end).fill(worldEditor, walls(), false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, 6, -4));
    end.translate(new Coord(4, 6, 4));
    RectSolid.newRect(start, end).fill(worldEditor, walls());

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, 7, -3));
    end.translate(new Coord(3, 7, 3));
    RectSolid.newRect(start, end).fill(worldEditor, walls());

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-2, 7, -2));
    end.translate(new Coord(2, 7, 2));
    RectSolid.newRect(start, end).fill(worldEditor, liquid());

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -3, 4));
    RectSolid.newRect(start, end).fill(worldEditor, walls(), false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, -2, -3));
    end.translate(new Coord(3, -2, 3));

    SingleBlockBrush soil = isHotGarden()
        ? BlockType.SOUL_SAND.getBrush()
        : BlockType.FARMLAND.getBrush();
    soil.fill(worldEditor, new RectSolid(start, end), true, true);
    liquid().stroke(worldEditor, origin.copy().down(2));

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    BlockWeightedRandom crop = getCrops();
    RectSolid cropsRectangle = RectSolid.newRect(start, end);
    cropsRectangle.fill(worldEditor, crop, true, true);

    List<Coord> chestLocations = chooseRandomLocations(random().nextInt(3) + 1, cropsRectangle.get());
    generateTrappableChests(chestLocations, getEntrance(entrances).reverse(), ChestType.UNCOMMON_TREASURES);

    for (Direction dir : CARDINAL) {

      start = origin.copy();
      start.translate(UP, 5);
      start.translate(dir, 4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 6);
      end.translate(dir.clockwise(), 6);
      RectSolid.newRect(start, end).fill(worldEditor, walls());

      start = origin.copy();
      start.translate(UP, 5);
      start.translate(dir, 6);
      end = start.copy();
      start.translate(dir.antiClockwise(), 6);
      end.translate(dir.clockwise(), 6);
      RectSolid.newRect(start, end).fill(worldEditor, walls());

      start = origin.copy();
      start.translate(DOWN);
      start.translate(dir, 4);
      end = start.copy();
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      stairs().setUpsideDown(false).setFacing(dir.reverse()).fill(worldEditor, new RectSolid(start, end));

      Coord cursor = origin.copy();
      cursor.translate(dir, 4);
      cursor.translate(dir.antiClockwise(), 4);
      supportPillar(cursor);

      for (Direction o : dir.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(dir, 7);
        cursor.translate(o, 2);
        pillar(cursor);
        cursor.translate(o);
        cursor.translate(o);
        cursor.translate(o);
        pillar(cursor);
      }
    }

    generateDoorways(origin, entrances);

    return this;
  }

  private boolean isHotGarden() {
    SingleBlockBrush liquid = (SingleBlockBrush) liquid();
    boolean isBlockTypeLava = liquid.getBlockType() != null && liquid.getBlockType().equals(LAVA_FLOWING);
    boolean hasJsonLava = liquid.getJson() != null && liquid.getJson().toString().toLowerCase().contains("lava");
    return isBlockTypeLava || hasJsonLava;
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
      return eligibleCrops[random().nextInt(eligibleCrops.length)].getBrush();
    }
  }

  private void supportPillar(Coord origin) {

    for (Direction dir : CARDINAL) {
      Coord start = origin.copy();
      start.translate(dir);
      Coord end = start.copy();
      end.translate(UP, 5);
      RectSolid.newRect(start, end).fill(worldEditor, pillars());

      Coord cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(UP, 4);
      stairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    }

    Coord start = origin.copy();
    Coord end = start.copy();
    end.translate(UP, 5);
    RectSolid.newRect(start, end).fill(worldEditor, liquid());
    List<Coord> core = new RectSolid(start, end).get();
    Coord spawnerLocation = core.get(random().nextInt(core.size()));
    generateSpawner(spawnerLocation);
  }

  private void pillar(Coord origin) {
    Coord start = origin.copy();
    Coord end = start.copy();
    end.translate(UP, 5);
    RectSolid.newRect(start, end).fill(worldEditor, pillars());

    for (Direction dir : CARDINAL) {
      Coord cursor = origin.copy();
      cursor.translate(UP, 4);
      cursor.translate(dir);
      stairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor, true, false);
      cursor.translate(UP);
      walls().stroke(worldEditor, cursor);
    }
  }

  public int getSize() {
    return 10;
  }

}
