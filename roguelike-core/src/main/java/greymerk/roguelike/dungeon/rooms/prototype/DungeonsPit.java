package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.TorchBlock;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;

public class DungeonsPit extends BaseRoom {
  int originX;
  int originY;
  int originZ;
  byte dungeonHeight;
  int dungeonLength;
  int dungeonWidth;
  int woolColor;
  int numChests;
  BlockBrush blocks;

  public DungeonsPit(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    dungeonHeight = 3;
    dungeonLength = 2;
    dungeonWidth = 2;
  }

  public BaseRoom generate(Coord origin, List<Direction> entrances) {
    originX = origin.getX();
    originY = origin.getY();
    originZ = origin.getZ();

    blocks = walls();

    buildWalls();
    buildFloor();
    buildRoof();
    buildPit();


    for (Direction dir : Direction.CARDINAL) {
      setTrap(dir, origin);
    }

    List<Coord> spaces = new ArrayList<>();
    spaces.add(new Coord(originX - 2, originY, originZ - 2));
    spaces.add(new Coord(originX - 2, originY, originZ + 2));
    spaces.add(new Coord(originX + 2, originY, originZ - 2));
    spaces.add(new Coord(originX + 2, originY, originZ + 2));

    generateTrappableChest(randomFrom(spaces), getEntrance(entrances));

    return this;
  }

  protected void buildWalls() {
    for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++) {
      for (int blockY = originY + dungeonHeight; blockY >= originY - 1; blockY--) {
        for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
          if (blockX == originX - dungeonLength - 1
              || blockZ == originZ - dungeonWidth - 1
              || blockX == originX + dungeonLength + 1
              || blockZ == originZ + dungeonWidth + 1) {

            if (blockY >= 0 && !worldEditor.isSolidBlock(new Coord(blockX, blockY - 1, blockZ))) {
              SingleBlockBrush.AIR.stroke(worldEditor, new Coord(blockX, blockY, blockZ));
              continue;
            }

            if (!worldEditor.isSolidBlock(new Coord(blockX, blockY, blockZ))) {
              continue;
            }

            blocks.stroke(worldEditor, new Coord(blockX, blockY, blockZ));

          } else {
            SingleBlockBrush.AIR.stroke(worldEditor, new Coord(blockX, blockY, blockZ));
          }
        }
      }
    }
  }

  protected void buildFloor() {

    for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++) {
      for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
        blocks.stroke(worldEditor, new Coord(blockX, originY - 1, blockZ));
      }
    }
  }

  protected void buildRoof() {
    for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++) {
      for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
        blocks.stroke(worldEditor, new Coord(blockX, dungeonHeight + 1, blockZ));
      }
    }
  }

  private void buildPit() {

    for (int x = originX - 2; x <= originX + 2; x++) {
      for (int z = originZ - 2; z <= originZ + 2; z++) {
        for (int y = originY - 1; y > 0; y--) {

          Coord pos = new Coord(x, y, z);

          if (worldEditor.isAirBlock(pos)) {
            continue;
          }

          if (y < random().nextInt(5) && worldEditor.isBlockOfTypeAt(BlockType.BEDROCK, pos)) {
            continue;
          }

          if (x == originX - 2
              || x == originX + 2
              || z == originZ - 2
              || z == originZ + 2) {

            blocks.stroke(worldEditor, pos);
            continue;
          }

          if (y < 10) {
            BlockType.WATER_FLOWING.getBrush().stroke(worldEditor, pos);
            continue;
          }

          SingleBlockBrush.AIR.stroke(worldEditor, pos);
        }
      }
    }
  }

  private void setTrap(Direction dir, Coord origin) {
    BlockBrush plate = BlockType.PRESSURE_PLATE_STONE.getBrush();
    BlockBrush wire = BlockType.REDSTONE_WIRE.getBrush();

    Coord start = origin.copy();
    start.translate(dir, 3);
    start.down();
    start.translate(dir.antiClockwise());
    Coord end = origin.copy();
    end.translate(dir, 6);
    end.up(3);
    end.translate(dir.clockwise());

    for (Coord cell : new RectHollow(start, end)) {
      if (worldEditor.isAirBlock(cell)) {
        return;
      }
      walls().stroke(worldEditor, cell);
    }

    Coord cursor = origin.copy();
    cursor.translate(dir, 2);
    plate.stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.down();
    cursor.translate(dir, 3);
    TorchBlock.redstone().setFacing(dir).stroke(worldEditor, cursor);
    cursor.translate(dir);
    wire.stroke(worldEditor, cursor);
    cursor.up();
    cursor.translate(dir);
    TorchBlock.redstone().extinguish().setFacing(Direction.UP).stroke(worldEditor, cursor);
    cursor.translate(dir.reverse());
    cursor.up();
    BlockType.STICKY_PISTON.getBrush().setFacing(dir).stroke(worldEditor, cursor);
  }

  public int getSize() {
    return 4;
  }
}
