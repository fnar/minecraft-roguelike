package greymerk.roguelike;

import com.github.srwaggon.minecraft.block.BlockType;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

  public List<TreasureChest> createChests(List<Coord> chestLocations, boolean isTrapped, int level, Direction facing, ChestType... chestTypes) {
    return chestLocations.stream()
        .map(chestLocation -> createChest(chestLocation, isTrapped, level, facing, chestTypes))
        .collect(Collectors.toList());
  }

  public TreasureChest createChest(Coord chestLocation, boolean isTrapped, int level, Direction facing, ChestType... chestTypes) {
//    if (!isValidChestSpace(chestLocation, worldEditor)) {
//      return null;
//    }
    try {
      return generateTreasureChest(chestLocation, isTrapped, ChestType.chooseRandomAmong(this.random, chestTypes), facing);
    } catch (ChestPlacementException chestPlacementException ) {
      chestPlacementException.printStackTrace();
    }
    return null;
  }

  private TreasureChest generateTreasureChest(Coord pos, boolean isTrapped, ChestType chestType, Direction facing) throws ChestPlacementException {
    BlockBrush chestBlock = (isTrapped ? BlockType.TRAPPED_CHEST : BlockType.CHEST).getBrush().setFacing(facing);

    boolean success = chestBlock.stroke(worldEditor, pos);

    if (!success) {
      // todo: this is thrown and immediately caught and ignored.
      throw new ChestPlacementException("Failed to place chest in world");
    }

    TreasureChest treasureChest = new TreasureChest(
        chestType,
        pos,
        worldEditor
    );
    getTreasureManager().addChest(treasureChest);

    return treasureChest;
  }

  private boolean isValidChestSpace(Coord coord, WorldEditor worldEditor) {
//    return worldEditor.isAirBlock(coord) &&
    return worldEditor.isSolidBlock(coord.add(Direction.DOWN))
        && worldEditor.getTreasureChestEditor().isNotNextToChest(coord, worldEditor);
  }

  private boolean isNotNextToChest(Coord coord, WorldEditor worldEditor) {
    return Direction.CARDINAL.stream().noneMatch(dir ->
        worldEditor.isBlockOfTypeAt(BlockType.CHEST, coord.add(dir)));
  }

}
