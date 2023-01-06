package greymerk.roguelike.dungeon.rooms.prototype;

import com.google.common.collect.Sets;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.material.Crop;
import com.github.fnar.roguelike.worldgen.generatables.Pillar;

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
import static greymerk.roguelike.worldgen.Direction.UP;

public class NetherFortressRoom extends BaseRoom {

  public NetherFortressRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 8;
    this.ceilingHeight = 7;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {
    super.generate(at, entrances);

    RectSolid chestRectangle = at.copy().down().newRect(2);
    List<Coord> chestLocations = Coord.randomFrom(chestRectangle.get(), random().nextInt(3) + 1, random());
    ChestType[] chestTypes = new ChestType[]{ChestType.GARDEN, ChestType.SUPPLIES, ChestType.TOOLS};
    generateTrappableChests(chestLocations, getEntrance(entrances).reverse(), chestTypes);

    return this;
  }

  @Override
  protected void generateFloor(Coord at, List<Direction> entrances) {
    super.generateFloor(at, entrances);

    primaryFloorBrush().fill(worldEditor, at.newRect(getWallDist()).down());

    generateGardenWithCrops(at.copy().down(2));
  }

  @Override
  protected void generateCeiling(Coord at, List<Direction> entrances) {
    super.generateCeiling(at, entrances);

    Coord ceilingLiquid = at.copy().up(getCeilingHeight());
    primaryWallBrush().fill(worldEditor, ceilingLiquid.newRect(getWallDist()).down());
    primaryLiquidBrush().fill(worldEditor, ceilingLiquid.newRect(3));
  }

  private void generateGardenWithCrops(Coord gardenLocation) {
    RectSolid cropRectangle = gardenLocation.copy().up().newRect(4);
    SingleBlockBrush.AIR.fill(worldEditor, cropRectangle);

    RectSolid gardenRectangle = gardenLocation.newRect(4);
    getSoil().fill(worldEditor, gardenRectangle);
    getCrops().fill(worldEditor, cropRectangle);
    primaryLiquidBrush().stroke(worldEditor, gardenLocation);
    SingleBlockBrush.AIR.stroke(worldEditor, gardenLocation.copy().up());
  }

  private SingleBlockBrush getSoil() {
    return isHotGarden()
        ? BlockType.SOUL_SAND.getBrush()
        : BlockType.FARMLAND.getBrush();
  }

  private boolean isHotGarden() {
    SingleBlockBrush liquid = (SingleBlockBrush) primaryLiquidBrush();
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

  @Override
  protected void generateDecorations(Coord at, List<Direction> entrances) {
    for (Direction cardinal : CARDINAL) {
      primaryWallBrush().fill(worldEditor, RectSolid.newRect(
          at.copy().up(5).translate(cardinal, 4).translate(cardinal.left(), 6),
          at.copy().up(5).translate(cardinal, 4).translate(cardinal.right(), 6)
      ));

      primaryWallBrush().fill(worldEditor, RectSolid.newRect(
          at.copy().up(5).translate(cardinal, 6).translate(cardinal.left(), 6),
          at.copy().up(5).translate(cardinal, 6).translate(cardinal.right(), 6)
      ));

      primaryStairBrush().setUpsideDown(false).setFacing(cardinal.reverse()).fill(worldEditor, RectSolid.newRect(
          at.copy().down().translate(cardinal, 4).translate(cardinal.left(), 2),
          at.copy().down().translate(cardinal, 4).translate(cardinal.right(), 2)
      ));

      supportPillar(at.copy().translate(cardinal, 4).translate(cardinal.antiClockwise(), 4));

      for (Direction orthogonal : cardinal.orthogonals()) {
        Coord pillar = at.copy().translate(cardinal, 7).translate(orthogonal, 2);
        pillar(pillar);
        pillar(pillar.translate(orthogonal, 3));
      }
    }
  }

  private void supportPillar(Coord at) {

    for (Direction dir : CARDINAL) {
      primaryPillarBrush().fill(worldEditor, RectSolid.newRect(
              at.copy(),
              at.copy().translate(UP, 5))
          .translate(dir)
      );

      primaryStairBrush().setUpsideDown(true).setFacing(dir).stroke(worldEditor, at.copy().translate(dir, 2).translate(UP, 4));
    }

    Coord start = at.copy();
    Coord end = start.copy();
    end.translate(UP, 5);
    primaryLiquidBrush().fill(worldEditor, RectSolid.newRect(start, end));
    List<Coord> core = RectSolid.newRect(start, end).get();
    Coord spawnerLocation = core.get(random().nextInt(core.size()));
    generateSpawner(spawnerLocation);
  }

  private void pillar(Coord origin) {
     Pillar.newPillar(worldEditor).withStairs(primaryStairBrush()).withPillar(primaryPillarBrush()).withHeight(5).generate(origin);
  }

}
