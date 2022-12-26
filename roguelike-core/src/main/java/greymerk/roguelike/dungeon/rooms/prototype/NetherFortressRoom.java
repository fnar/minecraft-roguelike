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
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.BlockType.LAVA_FLOWING;
import static greymerk.roguelike.worldgen.Direction.CARDINAL;
import static greymerk.roguelike.worldgen.Direction.DOWN;
import static greymerk.roguelike.worldgen.Direction.UP;

public class NetherFortressRoom extends BaseRoom {

  public NetherFortressRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.size = 9;
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    super.generate(origin, entrances);

    RectSolid chestRectangle = origin.copy().down().newRect(2);
    List<Coord> chestLocations = Coord.randomFrom(chestRectangle.get(), random().nextInt(3) + 1, random());
    ChestType[] chestTypes = new ChestType[]{ChestType.GARDEN, ChestType.SUPPLIES, ChestType.TOOLS};
    generateTrappableChests(chestLocations, getEntrance(entrances).reverse(), chestTypes);

    return this;
  }

  @Override
  protected void generateCeiling(Coord at) {
    super.generateCeiling(at);

    Coord ceilingLiquid = at.copy().up(getCeilingHeight());
    walls().fill(worldEditor, ceilingLiquid.newRect(getWallDist()).down());
    liquid().fill(worldEditor, ceilingLiquid.newRect(3));
  }

  @Override
  protected void generateDecorations(Coord at) {
    generateGardenWithCrops(at.copy().down(2));

    for (Direction cardinal : CARDINAL) {
      walls().fill(worldEditor, RectSolid.newRect(
          at.copy().up(5).translate(cardinal, 4).translate(cardinal.left(), 6),
          at.copy().up(5).translate(cardinal, 4).translate(cardinal.right(), 6)
      ));

      walls().fill(worldEditor, RectSolid.newRect(
          at.copy().up(5).translate(cardinal, 6).translate(cardinal.left(), 6),
          at.copy().up(5).translate(cardinal, 6).translate(cardinal.right(), 6)
      ));

      stairs().setUpsideDown(false).setFacing(cardinal.reverse()).fill(worldEditor, RectSolid.newRect(
          at.copy().down().translate(cardinal, 4).translate(cardinal.left(), 2),
          at.copy().down().translate(cardinal, 4).translate(cardinal.right(), 2)
      ));

      supportPillar(at.copy().translate(cardinal, 4).translate(cardinal.antiClockwise(), 4));

      for (Direction orthogonal : cardinal.orthogonals()) {
        pillar(at.copy().translate(cardinal, 7).translate(orthogonal, 2));
        pillar(at.copy().translate(cardinal, 7).translate(orthogonal, 2).translate(orthogonal, 3));
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

  private void supportPillar(Coord at) {

    for (Direction dir : CARDINAL) {
      Coord start = at.copy();
      start.translate(dir);
      Coord end = start.copy();
      end.translate(UP, 5);
      pillars().fill(worldEditor, RectSolid.newRect(start, end));

      Coord cursor = at.copy();
      cursor.translate(dir, 2);
      cursor.translate(UP, 4);
      stairs().setUpsideDown(true).setFacing(dir).stroke(worldEditor, cursor);
    }

    Coord start = at.copy();
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

}
