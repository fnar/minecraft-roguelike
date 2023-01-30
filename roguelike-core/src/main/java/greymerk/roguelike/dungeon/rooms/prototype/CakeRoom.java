package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class CakeRoom extends BaseRoom {

  private final int width;
  private final int length;

  public CakeRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    width = random().nextInt(2) + 2;
    length = random().nextInt(2) + 3;
    this.wallDist = Math.max(width, length);
    this.ceilingHeight = 4;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {
    super.generate(at, entrances);
    int x = at.getX();
    int y = at.getY();
    int z = at.getZ();

    int height = 3;

    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(
        new Coord(x - width, y, z - length),
        new Coord(x + width, y + height, z + length)));

    RectHollow floorAccentRect = RectHollow.newRect(
        at.copy().west(width + 1).north(length + 1).down(),
        at.copy().east(width + 1).south(length + 1).up(height + 1)
    );
    secondaryFloorBrush().fill(worldEditor, floorAccentRect, false, true);

    secondaryPillarBrush().fill(worldEditor, RectSolid.newRect(new Coord(x - width, y, z - length), new Coord(x - width, y + height, z - length)));
    secondaryPillarBrush().fill(worldEditor, RectSolid.newRect(new Coord(x - width, y, z + length), new Coord(x - width, y + height, z + length)));
    secondaryPillarBrush().fill(worldEditor, RectSolid.newRect(new Coord(x + width, y, z - length), new Coord(x + width, y + height, z - length)));
    secondaryPillarBrush().fill(worldEditor, RectSolid.newRect(new Coord(x + width, y, z + length), new Coord(x + width, y + height, z + length)));

    generateLightAccents(at, getEntrance(entrances));

    placeCake(at);

    Coord coord = generateChestLocation(at);
    new TreasureChest(coord, worldEditor)
        .withChestType(getChestTypeOrUse(ChestType.FOOD))
        .withFacing(getEntrance(entrances).reverse())
        .withTrap(false)
        .stroke(worldEditor, coord);

    return this;
  }

  private void generateLightAccents(Coord at, Direction entrance) {
    for (Direction orthogonal : entrance.orthogonals()) {
      Coord lightCoord = at.copy().translate(orthogonal, width - 1).down();
      Coord leftLight = lightCoord.copy().translate(orthogonal.left(), length - 1);
      Coord rightLight = lightCoord.copy().translate(orthogonal.right(), length - 1);
      primaryLightBrush().stroke(worldEditor, leftLight);
      primaryLightBrush().stroke(worldEditor, rightLight);
      primaryLightBrush().stroke(worldEditor, leftLight.copy().up(1 + ceilingHeight));
      primaryLightBrush().stroke(worldEditor, rightLight.copy().up(1 + ceilingHeight));
    }
  }

  private void placeCake(Coord at) {
    primaryPillarBrush().stroke(worldEditor, at);
    BlockType.CAKE.getBrush().stroke(worldEditor, at.copy().up());
  }

}
