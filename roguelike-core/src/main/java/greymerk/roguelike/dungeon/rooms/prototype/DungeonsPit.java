package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.decorative.TorchBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;

public class DungeonsPit extends DungeonBase {
  Random rand;
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

  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

    ThemeBase theme = levelSettings.getTheme();

    rand = worldEditor.getRandom();
    originX = origin.getX();
    originY = origin.getY();
    originZ = origin.getZ();

    blocks = theme.getPrimary().getWall();

    buildWalls();
    buildFloor();
    buildRoof();
    buildPit();


    for (Cardinal dir : Cardinal.DIRECTIONS) {
      setTrap(worldEditor, levelSettings, dir, origin);
    }

    List<Coord> spaces = new ArrayList<>();
    spaces.add(new Coord(originX - 2, originY, originZ - 2));
    spaces.add(new Coord(originX - 2, originY, originZ + 2));
    spaces.add(new Coord(originX + 2, originY, originZ - 2));
    spaces.add(new Coord(originX + 2, originY, originZ + 2));

    List<Coord> chestLocations = chooseRandomLocations(1, spaces);
    worldEditor.getTreasureChestEditor().createChests(Dungeon.getLevel(originY), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(rand, ChestType.COMMON_TREASURES)));

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

          if (y < rand.nextInt(5) && worldEditor.isBlockOfTypeAt(BlockType.BEDROCK, pos)) {
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

  private void setTrap(WorldEditor editor, LevelSettings settings, Cardinal dir, Coord origin) {
    ThemeBase theme = settings.getTheme();
    BlockBrush walls = theme.getPrimary().getWall();
    BlockBrush plate = BlockType.PRESSURE_PLATE_STONE.getBrush();
    BlockBrush wire = BlockType.REDSTONE_WIRE.getBrush();
    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    start.translate(dir, 3);
    start.translate(Cardinal.DOWN);
    start.translate(dir.antiClockwise());
    end = new Coord(origin);
    end.translate(dir, 6);
    end.translate(Cardinal.UP, 3);
    end.translate(dir.clockwise());

    for (Coord cell : new RectHollow(start, end)) {
      if (editor.isAirBlock(cell)) {
        return;
      }
      walls.stroke(editor, cell);
    }

    cursor = new Coord(origin);
    cursor.translate(dir, 2);
    plate.stroke(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.DOWN);
    cursor.translate(dir, 3);
    TorchBlock.redstone().setFacing(dir).stroke(editor, cursor);
    cursor.translate(dir);
    wire.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir);
    TorchBlock.redstone().extinguish().setFacing(Cardinal.UP).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    cursor.translate(Cardinal.UP);
    BlockType.STICKY_PISTON.getBrush().setFacing(dir).stroke(editor, cursor);
  }

  public int getSize() {
    return 4;
  }
}
