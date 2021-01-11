package greymerk.roguelike;

import com.github.srwaggon.minecraft.block.BlockType;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.treasure.ChestPlacementException;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

public class TreasureChestEditor {

  private final WorldEditor worldEditor;
  private final TreasureManager treasureManager;
  private final Random random;

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
    BlockBrush chestBlock = (isTrapped ? BlockType.TRAPPED_CHEST : BlockType.CHEST).getBrush();

    boolean success = chestBlock.stroke(worldEditor, pos);

    if (!success) {
      throw new ChestPlacementException("Failed to place chest in world");
    }

    TreasureChest treasureChest = new TreasureChest(
        chestType,
        level,
        pos,
        worldEditor
    );
    getTreasureManager().addChest(treasureChest);

    return treasureChest;
  }

  private boolean isValidChestSpace(Coord coord, WorldEditor worldEditor) {
    return worldEditor.isAirBlock(coord)
        && worldEditor.isSolidBlock(coord.add(Direction.DOWN))
        && worldEditor.getTreasureChestEditor().isNotNextToChest(coord, worldEditor);
  }

  private boolean isNotNextToChest(Coord coord, WorldEditor worldEditor) {
    return Direction.CARDINAL.stream().noneMatch(dir ->
        worldEditor.isBlockOfTypeAt(BlockType.CHEST, coord.add(dir)));
  }

}
