package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.MobType;

public class DungeonsCreeperDen extends DungeonBase {

  public static final BlockBrush TNT_META_BLOCK = BlockType.TNT.getBrush();

  public DungeonsCreeperDen(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    Theme theme = levelSettings.getTheme();

    Coord start;
    Coord end;

    BlockWeightedRandom mossy = new BlockWeightedRandom();
    mossy.addBlock(theme.getPrimary().getWall(), 3);
    mossy.addBlock(BlockType.COBBLESTONE_MOSSY.getBrush(), 1);

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(theme.getPrimary().getFloor(), 1);
    mossy.addBlock(BlockType.COBBLESTONE_MOSSY.getBrush(), 1);
    floor.addBlock(BlockType.GRAVEL.getBrush(), 3);

    BlockWeightedRandom subfloor = new BlockWeightedRandom();
    subfloor.addBlock(floor, 3);
    subfloor.addBlock(TNT_META_BLOCK, 1);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-4, -4, -4));
    end.translate(new Coord(4, 5, 4));
    RectHollow.newRect(start, end).fill(worldEditor, mossy, false, true);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, -1, -3));
    end.translate(new Coord(3, -1, 3));
    RectSolid.newRect(start, end).fill(worldEditor, floor);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, -3, -3));
    end.translate(new Coord(3, -2, 3));
    RectSolid.newRect(start, end).fill(worldEditor, subfloor);

    start = origin.copy();
    end = origin.copy();
    start.translate(new Coord(-3, 0, -3));
    end.translate(new Coord(3, 0, 3));

    List<Coord> chestSpaces = new RectSolid(start, end).get();
    chooseRandomLocations(3, chestSpaces).stream()
        .peek(chestSpace -> worldEditor.getTreasureChestEditor().createChest(chestSpace, true, levelSettings.getDifficulty(chestSpace), entrances.get(0).reverse(), getRoomSetting().getChestType().orElse(ChestType.ORE)))
        .forEach(chestSpace -> spawnTntBeneath(worldEditor, chestSpace));

    final Coord cursor = origin.copy();
    generateSpawner(cursor, MobType.CREEPER);

    return this;
  }

  private void spawnTntBeneath(WorldEditor editor, Coord coord) {
    Coord cursor = coord.copy();
    cursor.down(2);
    TNT_META_BLOCK.stroke(editor, cursor);
  }

  public int getSize() {
    return 7;
  }
}
