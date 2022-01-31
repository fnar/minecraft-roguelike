package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class CakeRoom extends BaseRoom {

  public CakeRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    Random random = worldEditor.getRandom();
    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    final int HEIGHT = 3;
    final int WIDTH = random.nextInt(2) + 2;
    final int LENGTH = random.nextInt(2) + 3;


    RectSolid.newRect(
        new Coord(x - WIDTH, y, z - LENGTH),
        new Coord(x + WIDTH, y + HEIGHT, z + LENGTH))
        .fill(worldEditor, SingleBlockBrush.AIR);

    BlockBrush floor = levelSettings.getTheme().getPrimary().getFloor();
    RectHollow.newRect(
        origin.copy().west(WIDTH + 1).north(LENGTH + 1).down(),
        origin.copy().east(WIDTH + 1).south(LENGTH + 1).up(HEIGHT + 1)
    ).fill(worldEditor, floor, false, true);

    BlockBrush pillar = levelSettings.getTheme().getPrimary().getPillar();
    RectSolid.newRect(new Coord(x - WIDTH, y, z - LENGTH), new Coord(x - WIDTH, y + HEIGHT, z - LENGTH)).fill(worldEditor, pillar);
    RectSolid.newRect(new Coord(x - WIDTH, y, z + LENGTH), new Coord(x - WIDTH, y + HEIGHT, z + LENGTH)).fill(worldEditor, pillar);
    RectSolid.newRect(new Coord(x + WIDTH, y, z - LENGTH), new Coord(x + WIDTH, y + HEIGHT, z - LENGTH)).fill(worldEditor, pillar);
    RectSolid.newRect(new Coord(x + WIDTH, y, z + LENGTH), new Coord(x + WIDTH, y + HEIGHT, z + LENGTH)).fill(worldEditor, pillar);

    BlockBrush lightBLock = levelSettings.getTheme().getPrimary().getLightBlock();
    lightBLock.stroke(worldEditor, new Coord(x - WIDTH + 1, y + HEIGHT + 1, z - LENGTH + 1));
    lightBLock.stroke(worldEditor, new Coord(x - WIDTH + 1, y + HEIGHT + 1, z + LENGTH - 1));
    lightBLock.stroke(worldEditor, new Coord(x + WIDTH - 1, y + HEIGHT + 1, z - LENGTH + 1));
    lightBLock.stroke(worldEditor, new Coord(x + WIDTH - 1, y + HEIGHT + 1, z + LENGTH - 1));

    placeCake(origin, pillar);

    List<Coord> spaces = new ArrayList<>();
    spaces.add(new Coord(x - WIDTH, y, z - LENGTH + 1));
    spaces.add(new Coord(x - WIDTH, y, z + LENGTH - 1));
    spaces.add(new Coord(x + WIDTH, y, z - LENGTH + 1));
    spaces.add(new Coord(x + WIDTH, y, z + LENGTH - 1));

    List<Coord> chestLocations = chooseRandomLocations(1, spaces);

    generateChests(chestLocations, entrances.get(0).reverse(), ChestType.FOOD);

    generateDoorways(origin, entrances);
    return this;
  }

  public void placeCake(Coord origin, BlockBrush pillar) {
    Coord cakeStand = origin.copy();
    pillar.stroke(worldEditor, cakeStand);
    BlockType.CAKE.getBrush().stroke(worldEditor, cakeStand.up());
  }

  public int getSize() {
    return 6;
  }

}
