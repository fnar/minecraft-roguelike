package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class CakeRoom extends BaseRoom {

  private int width;
  private int length;

  public CakeRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 5;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {
    int x = at.getX();
    int y = at.getY();
    int z = at.getZ();
    width = random().nextInt(2) + 2;
    length = random().nextInt(2) + 3;
    int height = 3;

    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(
        new Coord(x - width, y, z - length),
        new Coord(x + width, y + height, z + length)));

    RectHollow.newRect(
        at.copy().west(width + 1).north(length + 1).down(),
        at.copy().east(width + 1).south(length + 1).up(height + 1)
    ).fill(worldEditor, primaryFloorBrush(), false, true);

    primaryPillarBrush().fill(worldEditor, RectSolid.newRect(new Coord(x - width, y, z - length), new Coord(x - width, y + height, z - length)));
    primaryPillarBrush().fill(worldEditor, RectSolid.newRect(new Coord(x - width, y, z + length), new Coord(x - width, y + height, z + length)));
    primaryPillarBrush().fill(worldEditor, RectSolid.newRect(new Coord(x + width, y, z - length), new Coord(x + width, y + height, z - length)));
    primaryPillarBrush().fill(worldEditor, RectSolid.newRect(new Coord(x + width, y, z + length), new Coord(x + width, y + height, z + length)));

    primaryLightBrush().stroke(worldEditor, new Coord(x - width + 1, y + height + 1, z - length + 1));
    primaryLightBrush().stroke(worldEditor, new Coord(x - width + 1, y + height + 1, z + length - 1));
    primaryLightBrush().stroke(worldEditor, new Coord(x + width - 1, y + height + 1, z - length + 1));
    primaryLightBrush().stroke(worldEditor, new Coord(x + width - 1, y + height + 1, z + length - 1));

    placeCake(at, primaryPillarBrush());

    Coord coord = generateChestLocation(at);
    new TreasureChest(coord, worldEditor)
        .withChestType(getChestTypeOrUse(ChestType.FOOD))
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
        .translate(dir0, width)
        .translate(dir1, length);
  }

  public void placeCake(Coord origin, BlockBrush pillar) {
    Coord cakeStand = origin.copy();
    pillar.stroke(worldEditor, cakeStand);
    BlockType.CAKE.getBrush().stroke(worldEditor, cakeStand.up());
  }

}
