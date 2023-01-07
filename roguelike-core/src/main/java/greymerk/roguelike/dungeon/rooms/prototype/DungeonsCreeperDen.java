package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.spawner.MobType;

import java.util.List;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsCreeperDen extends BaseRoom {

  public static final BlockBrush TNT_META_BLOCK = BlockType.TNT.getBrush();

  public DungeonsCreeperDen(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 6;
  }

  public BaseRoom generate(Coord at, List<Direction> entrances) {

    BlockWeightedRandom mossy = new BlockWeightedRandom();
    mossy.addBlock(theme().getPrimary().getWall(), 3);
    mossy.addBlock(BlockType.COBBLESTONE_MOSSY.getBrush(), 1);

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(theme().getPrimary().getFloor(), 1);
    mossy.addBlock(BlockType.COBBLESTONE_MOSSY.getBrush(), 1);
    floor.addBlock(BlockType.GRAVEL.getBrush(), 3);

    BlockWeightedRandom subfloor = new BlockWeightedRandom();
    subfloor.addBlock(floor, 3);
    subfloor.addBlock(TNT_META_BLOCK, 1);

    Coord start = at.copy().translate(-4, -4, -4);
    Coord end = at.copy().translate(4, 5, 4);
    RectHollow.newRect(start, end).fill(worldEditor, mossy, false, true);

    start = at.copy().translate(-3, -1, -3);
    end = at.copy().translate(3, -1, 3);
    floor.fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy().translate(-3, -3, -3);
    end = at.copy().translate(3, -2, 3);
    subfloor.fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy().translate(-3, 0, -3);
    end = at.copy().translate(3, 0, 3);

    List<Coord> chestSpaces = RectSolid.newRect(start, end).get();
    Coord.randomFrom(chestSpaces, 3, random())
        .forEach(chestSpace -> new TreasureChest(chestSpace, worldEditor)
            .withChestType(getChestTypeOrUse(ChestType.ORE))
            .withFacing(getEntrance(entrances).reverse())
            .withTrap(true)
            .stroke(worldEditor, chestSpace));

    final Coord cursor = at.copy();
    generateSpawner(cursor, MobType.CREEPER);

    return this;
  }

}
