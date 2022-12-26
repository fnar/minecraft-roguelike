package greymerk.roguelike.dungeon.rooms.prototype;

import com.google.common.collect.Sets;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.material.Crop;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
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

  @Override
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
    walls().fill(worldEditor, RectSolid.newRect(start, end));

    walls().fill(worldEditor, origin.copy().up(7).newRect(3));

    liquid().fill(worldEditor, origin.copy().up(7).newRect(2));

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -3, 4));
    RectSolid.newRect(start, end).fill(worldEditor, walls(), false, true);

    generateGardenWithCrops(origin.copy().down(2));

    RectSolid chestRectangle = origin.copy().down().newRect(2);
    List<Coord> chestLocations = Coord.randomFrom(chestRectangle.get(), random().nextInt(3) + 1, random());
    ChestType[] chestTypes = new ChestType[]{ChestType.GARDEN, ChestType.SUPPLIES, ChestType.TOOLS};
    generateTrappableChests(chestLocations, getEntrance(entrances).reverse(), chestTypes);

    // todo: super.generate(origin) would place the decorations automatically
    generateDecorations(origin);

    // todo: super.generate(origin) would place the doorways automatically
    generateDoorways(origin, entrances);

    return this;
  }

  @Override
  protected void generateDecorations(Coord origin) {
    for (Direction cardinal : CARDINAL) {
      Coord start = origin.copy().up(5);
      start.translate(cardinal, 4);
      Coord end = start.copy();
      start.translate(cardinal.antiClockwise(), 6);
      end.translate(cardinal.clockwise(), 6);
      walls().fill(worldEditor, RectSolid.newRect(start, end));

      start = origin.copy();
      start.translate(UP, 5);
      start.translate(cardinal, 6);
      end = start.copy();
      start.translate(cardinal.antiClockwise(), 6);
      end.translate(cardinal.clockwise(), 6);
      walls().fill(worldEditor, RectSolid.newRect(start, end));

      start = origin.copy();
      start.translate(DOWN);
      start.translate(cardinal, 4);
      end = start.copy();
      start.translate(cardinal.antiClockwise(), 2);
      end.translate(cardinal.clockwise(), 2);
      stairs().setUpsideDown(false).setFacing(cardinal.reverse()).fill(worldEditor, RectSolid.newRect(start, end));

      Coord cursor = origin.copy();
      cursor.translate(cardinal, 4);
      cursor.translate(cardinal.antiClockwise(), 4);
      supportPillar(cursor);

      for (Direction orthogonal : cardinal.orthogonals()) {
        cursor = origin.copy();
        cursor.translate(cardinal, 7);
        cursor.translate(orthogonal, 2);
        pillar(cursor);
        cursor.translate(orthogonal);
        cursor.translate(orthogonal);
        cursor.translate(orthogonal);
        pillar(cursor);
      }
    }
  }

  private void generateGardenWithCrops(Coord gardenLocation) {
    RectSolid cropRectangle = gardenLocation.copy().up().newRect(4);
    SingleBlockBrush.AIR.fill(worldEditor, cropRectangle);

    RectSolid gardenRectangle = gardenLocation.newRect(4);
    getSoil().fill(worldEditor, gardenRectangle);
    getCrops().fill(worldEditor, cropRectangle);
    liquid().stroke(worldEditor, gardenLocation);
    SingleBlockBrush.AIR.stroke(worldEditor, gardenLocation.copy().up());
  }

  private SingleBlockBrush getSoil() {
    return isHotGarden()
        ? BlockType.SOUL_SAND.getBrush()
        : BlockType.FARMLAND.getBrush();
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

  private BlockBrush selectCrop() {
    if (isHotGarden()) {
      return Crop.NETHER_WART.getBrush();
    } else {
      Set<Crop> eligibleCrops = Sets.newHashSet(Crop.CARROTS, Crop.POTATOES, Crop.WHEAT);
      return new BlockJumble(eligibleCrops.stream().map(Crop::getBrush).collect(Collectors.toList()));
    }
  }

  private void supportPillar(Coord origin) {

    for (Direction dir : CARDINAL) {
      Coord start = origin.copy();
      start.translate(dir);
      Coord end = start.copy();
      end.translate(UP, 5);
      pillars().fill(worldEditor, RectSolid.newRect(start, end));

      Coord cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(UP, 4);
      stairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    }

    Coord start = origin.copy();
    Coord end = start.copy();
    end.translate(UP, 5);
    liquid().fill(worldEditor, RectSolid.newRect(start, end));
    List<Coord> core = RectSolid.newRect(start, end).get();
    Coord spawnerLocation = core.get(random().nextInt(core.size()));
    generateSpawner(spawnerLocation);
  }

  private void pillar(Coord origin) {
    Coord start = origin.copy();
    Coord end = start.copy();
    end.translate(UP, 5);
    pillars().fill(worldEditor, RectSolid.newRect(start, end));

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
