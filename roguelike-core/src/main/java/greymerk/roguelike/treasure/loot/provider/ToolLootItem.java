package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.ToolType;
import com.github.fnar.roguelike.loot.special.tools.SpecialTool;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class ToolLootItem extends LootItem {

  private Equipment type;
  private Quality quality;
  private boolean enchant;

  public ToolLootItem(int weight, int level) {
    super(weight, level);
  }

  public ToolLootItem(int weight, int level, Equipment type, Quality quality, boolean enchant) {
    super(weight, level);
    this.type = type;
    this.enchant = enchant;
    this.quality = quality;
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    return SpecialtyLootItem.rollForSpecial(random)
        ? SpecialTool.createTool(random, getQuality(random))
        : getToolType(random)
            .asItem()
            .withQuality(getQuality(random))
            .plzEnchantAtLevel(getEnchantLevel(random))
            .asStack();
  }

  private int getEnchantLevel(Random random) {
    return this.enchant ? LootItem.getEnchantmentLevel(random, level) : 0;
  }

  private Quality getQuality(Random random) {
    return Optional.ofNullable(this.quality).orElseGet(() -> Equipment.rollQuality(random, level));
  }

  private ToolType getToolType(Random random) {
    return Optional.ofNullable(this.type).map(Equipment::asToolType).orElseGet(() -> ToolType.random(random));
  }


}
