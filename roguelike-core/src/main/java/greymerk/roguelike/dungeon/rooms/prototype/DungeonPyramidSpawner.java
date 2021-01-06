package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

import java.util.ArrayList;
import java.util.List;

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
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static greymerk.roguelike.worldgen.spawners.MobType.COMMON_MOBS;

public class DungeonPyramidSpawner extends DungeonBase {

  public DungeonPyramidSpawner(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    ThemeBase theme = levelSettings.getTheme();

    BlockBrush blocks = theme.getPrimary().getWall();
    BlockBrush pillar = theme.getPrimary().getPillar();

    // fill air inside
    RectSolid.newRect(new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3)).fill(worldEditor, SingleBlockBrush.AIR);


    // shell
    RectHollow.newRect(new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4)).fill(worldEditor, blocks, false, true);
    RectSolid.newRect(new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3)).fill(worldEditor, blocks, false, true);
    RectSolid.newRect(new Coord(x - 2, y + 4, z - 2), new Coord(x + 2, y + 4, z + 2)).fill(worldEditor, SingleBlockBrush.AIR);

    RectSolid.newRect(new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4)).fill(worldEditor, theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 5);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.translate(Cardinal.UP, 1);
    blocks.stroke(worldEditor, cursor);

    cursor = new Coord(x, y, z);
    cursor.translate(Cardinal.UP, 5);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.translate(Cardinal.UP);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);

    // Chests
    List<Coord> space = new ArrayList<>();

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      // pillar
      cursor = new Coord(x, y, z);
      cursor.translate(dir, 3);
      cursor.translate(dir.antiClockwise(), 3);
      start = cursor.copy();
      cursor.translate(Cardinal.UP, 3);
      end = cursor.copy();
      RectSolid.newRect(start, end).fill(worldEditor, pillar);
      cursor.translate(Cardinal.UP, 1);
      blocks.stroke(worldEditor, cursor);

      cursor = new Coord(x, y, z);
      cursor.translate(Cardinal.UP, 4);
      cursor.translate(dir, 2);
      blocks.stroke(worldEditor, cursor);
      cursor.translate(dir.antiClockwise(), 2);
      blocks.stroke(worldEditor, cursor);

      cursor = new Coord(x, y, z);
      cursor.translate(Cardinal.UP, 5);
      cursor.translate(dir.antiClockwise());
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);
      cursor.translate(Cardinal.UP);
      SingleBlockBrush.AIR.stroke(worldEditor, cursor);

      for (Cardinal orthogonals : dir.orthogonals()) {

        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 3);
        cursor.translate(orthogonals);
        cursor.translate(dir, 3);
        blocks.stroke(worldEditor, cursor);

        cursor = new Coord(x, y, z);
        cursor.translate(Cardinal.UP, 4);
        cursor.translate(dir, 2);
        blocks.stroke(worldEditor, cursor);

        cursor = new Coord(x, y, z);
        cursor.translate(dir, 3);
        cursor.translate(orthogonals, 2);
        space.add(cursor);
      }
    }

    List<Coord> chestLocations = chooseRandomLocations(1, space);
    worldEditor.getTreasureChestEditor().createChests(Dungeon.getLevel(origin.getY()), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.chooseRandomType(worldEditor.getRandom(), ChestType.COMMON_TREASURES)));
    final Coord cursor1 = new Coord(x, y, z);
    generateSpawner(cursor1, COMMON_MOBS);
    return this;
  }

  public int getSize() {
    return 4;
  }
}
