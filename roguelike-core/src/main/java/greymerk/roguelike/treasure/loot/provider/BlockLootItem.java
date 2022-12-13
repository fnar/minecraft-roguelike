package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.item.RldBaseItem;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Objects;
import java.util.Random;

import greymerk.roguelike.util.WeightedRandomizer;

public class BlockLootItem extends LootItem {

  private final WeightedRandomizer<RldBaseItem> loot;

  public BlockLootItem(int weight, int level) {
    super(weight, level);
    this.loot = new WeightedRandomizer<>();
    this.loot.add(BlockType.ANDESITE_POLISHED.asItem(), 1);
    this.loot.add(BlockType.GRANITE_POLISHED.asItem(), 1);
    this.loot.add(BlockType.DIORITE_POLISHED.asItem(), 1);
    this.loot.add(BlockType.COBBLESTONE.asItem(), 10);
    this.loot.add(BlockType.STONE_BRICK.asItem(), 5);
    this.loot.add(BlockType.STONE_BRICK_MOSSY.asItem(), 1);
    this.loot.add(BlockType.STONE_BRICK_CRACKED.asItem(), 1);
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    return this.loot.get(random).asStack().withCount(8 + random.nextInt(24));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BlockLootItem itemBlock = (BlockLootItem) o;
    return Objects.equals(loot, itemBlock.loot);
  }

  @Override
  public int hashCode() {
    return Objects.hash(loot);
  }
}
