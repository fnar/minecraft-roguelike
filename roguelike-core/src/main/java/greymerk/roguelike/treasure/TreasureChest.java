package greymerk.roguelike.treasure;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TreasureChest {

  private ChestType chestType = ChestType.EMPTY;
  private final int level;
  private final Coord pos;
  private final WorldEditor worldEditor;
  private boolean isTrapped;
  private Direction facing;

  public TreasureChest(
      Coord pos,
      WorldEditor worldEditor
  ) {
    this.level = Dungeon.getLevel(pos.getY());
    this.pos = pos.copy();
    this.worldEditor = worldEditor;
  }

  public void setSlot(int slot, RldItemStack itemStack) {
    worldEditor.setItem(pos, slot, itemStack);
  }

  public void setRandomEmptySlot(RldItemStack itemStack) {
    List<Integer> slots = IntStream.range(0, this.worldEditor.getCapacity(this)).boxed().collect(Collectors.toList());
    Collections.shuffle(slots);
    slots.stream()
        .mapToInt(slot -> slot)
        .filter(slot -> worldEditor.isEmptySlot(this, slot))
        .findFirst()
        .ifPresent(value -> setSlot(value, itemStack));
  }

  public ChestType getType() {
    return chestType;
  }

  public Coord getCoord() {
    return pos.copy();
  }

  public int getLevel() {
    return max(0, min(level, 4));
  }

  public void setLootTable(String table) {
    worldEditor.setLootTable(pos, table);
  }

  public boolean isNotEmpty() {
    return !isEmpty();
  }

  private boolean isEmpty() {
    return chestType == null || getType().equals(ChestType.EMPTY);
  }

  public WorldEditor getWorldEditor() {
    return this.worldEditor;
  }

  public TreasureChest withChestType(ChestType chestType) {
    this.chestType = chestType;
    return this;
  }

  public TreasureChest withTrap(int level) {
    this.isTrapped = worldEditor.getRandom().nextInt(30 / (Math.max(1, level))) == 0;
    return this;
  }

  public TreasureChest withTrap(boolean isTrapped) {
    this.isTrapped = isTrapped;
    return this;
  }

  public TreasureChest withFacing(Direction facing) {
    this.facing = facing;
    return this;
  }

  public boolean isValidChestSpace() {
    return !isNextToChest();
  }

  private boolean isNextToChest() {
    return Direction.CARDINAL.stream()
        .anyMatch(this::containsChestBlock);
  }

  private boolean containsChestBlock(Direction dir) {
    return this.worldEditor.isBlockOfTypeAt(BlockType.CHEST, getCoord().add(dir));
  }

  // TODO: Could this class be a block brush?
  public Optional<TreasureChest> stroke(WorldEditor worldEditor, Coord coord) {
    if (!isValidChestSpace()) {
      return Optional.empty();
    }
    BlockBrush chestBlock = (isTrapped ? BlockType.TRAPPED_CHEST : BlockType.CHEST).getBrush().setFacing(facing);
    if (!chestBlock.stroke(worldEditor, coord)) {
      return Optional.empty();
    }
    strokeTrap();
    this.worldEditor.getTreasureManager().addChest(this);
    return Optional.of(this);
  }

  private void strokeTrap() {
    if (!isTrapped) {
      return;
    }
    Coord tntCoord = getCoord().copy().down(2);
    BlockType.TNT.getBrush().stroke(worldEditor, tntCoord);
    if (worldEditor.getRandom().nextBoolean()) {
      BlockType.TNT.getBrush().stroke(worldEditor, tntCoord.down());
    }
  }
}
