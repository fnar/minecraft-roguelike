package greymerk.roguelike.dungeon.rooms.prototype;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;


public class DungeonsCreeperDen extends DungeonBase {

  public static final MetaBlock TNT_META_BLOCK = BlockType.get(BlockType.TNT);

  public DungeonsCreeperDen(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public DungeonBase generate(WorldEditor editor, Random random, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    ITheme theme = settings.getTheme();

    Coord start;
    Coord end;

    BlockWeightedRandom mossy = new BlockWeightedRandom();
    mossy.addBlock(theme.getPrimary().getWall(), 3);
    mossy.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 1);

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(theme.getPrimary().getFloor(), 1);
    mossy.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 1);
    floor.addBlock(BlockType.get(BlockType.GRAVEL), 3);

    BlockWeightedRandom subfloor = new BlockWeightedRandom();
    subfloor.addBlock(floor, 3);
    subfloor.addBlock(TNT_META_BLOCK, 1);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -4, -4));
    end.translate(new Coord(4, 5, 4));
    RectHollow.fill(editor, random, start, end, mossy, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    RectSolid.fill(editor, random, start, end, floor, true, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, -3, -3));
    end.translate(new Coord(3, -2, 3));
    RectSolid.fill(editor, random, start, end, subfloor, true, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-3, 0, -3));
    end.translate(new Coord(3, 0, 3));

    List<Coord> chestSpaces = new RectSolid(start, end).get();
    chooseRandomLocations(random, 3, chestSpaces).stream()
        .peek(chestSpace -> editor.treasureChestEditor.createChest(random, settings.getDifficulty(chestSpace), chestSpace, true))
        .forEach(chestSpace -> spawnTntBeneath(editor, chestSpace));

    final Coord cursor = new Coord(origin);
    SpawnerSettings spawners = settings.getSpawners();
    generateSpawner(editor, random, cursor, settings.getDifficulty(cursor), spawners, MobType.CREEPER);

    return this;
  }

  private void spawnTntBeneath(WorldEditor editor, Coord coord) {
    Coord cursor = new Coord(coord);
    cursor.translate(Cardinal.DOWN, 2);
    TNT_META_BLOCK.set(editor, cursor);
  }

  public int getSize() {
    return 7;
  }
}
