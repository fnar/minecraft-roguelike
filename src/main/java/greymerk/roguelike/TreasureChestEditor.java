package greymerk.roguelike;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import greymerk.roguelike.treasure.ChestPlacementException;
import greymerk.roguelike.treasure.Inventory;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;

public class TreasureChestEditor {

  private WorldEditor worldEditor;
  private TreasureManager treasureManager;
  private Random random;

  public TreasureChestEditor(WorldEditor worldEditor, Random random) {
    this.worldEditor = worldEditor;
    this.random = random;
    treasureManager = new TreasureManager(random);
  }

  public TreasureManager getTreasureManager() {
    return treasureManager;
  }

  public void createChests(int level, List<Coord> chestLocations, boolean isTrapped, ChestType... chestTypes) {
    chestLocations.forEach(chestLocation ->
        createChest(level, chestLocation, isTrapped, chestTypes));
  }

  public void createChest(int level, Coord chestLocation, boolean isTrapped, ChestType... chestTypes) {
    if (isValidChestSpace(chestLocation, worldEditor)) {
      ChestType type = ChestType.chooseRandomType(this.random, chestTypes);
      safeGenerate(level, chestLocation, isTrapped, type);
    }
  }

  private void safeGenerate(int level, Coord chestLocation, boolean isTrapped, ChestType chestType) {
    try {
      generateTreasureChest(chestLocation, isTrapped, chestType, level);
    } catch (ChestPlacementException ignored) {
    }
  }

  public TreasureChest generateTreasureChest(Coord pos, boolean isTrapped, ChestType chestType, int level) throws ChestPlacementException {
    MetaBlock chestBlock = new MetaBlock(isTrapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST);

    boolean success = chestBlock.set(worldEditor, pos);

    if (!success) {
      throw new ChestPlacementException("Failed to place chest in world");
    }

    TileEntityChest tileEntityChest = (TileEntityChest) worldEditor.getTileEntity(pos);
    int seed = Objects.hash(pos.hashCode(), worldEditor.getSeed());
    TreasureChest treasureChest = new TreasureChest(
        chestType,
        level,
        tileEntityChest,
        seed,
        new Inventory(this.random, tileEntityChest)
    );
    getTreasureManager().addChest(treasureChest);

    return treasureChest;
  }

  private boolean isValidChestSpace(Coord coord, WorldEditor worldEditor) {
    return worldEditor.isAirBlock(coord)
        && !worldEditor.isNonSolidBlock(coord)
        && worldEditor.treasureChestEditor.isNotNextToChest(coord, worldEditor);
  }

  private boolean isNotNextToChest(Coord coord, WorldEditor worldEditor) {
    return Cardinal.DIRECTIONS.stream().noneMatch(dir -> isBesideChest(coord, dir, worldEditor));
  }

  private boolean isBesideChest(Coord coord, Cardinal dir, WorldEditor worldEditor) {
    Coord otherCursor = coord.add(dir);
    return worldEditor.getBlock(otherCursor).getBlock() == Blocks.CHEST;
  }
}
