package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.Difficulty;
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
    ToolType toolType = Optional.ofNullable(this.type).map(Equipment::asToolType).orElseGet(() -> ToolType.random(random));
    Quality quality = Optional.ofNullable(this.quality).orElseGet(() -> Equipment.rollQuality(random, level));
    return get(random, level, toolType, quality, this.enchant);
  }

  public static RldItemStack get(Random random, int level, int difficulty) {
    boolean enchanted = isEnchanted(Difficulty.fromInt(difficulty), random, level);
    return get(random, level, enchanted);
  }

  private static RldItemStack get(Random random, int level, boolean enchant) {
    ToolType toolType = ToolType.random(random);
    Quality quality = Equipment.rollQuality(random, level);
    return get(random, level, toolType, quality, enchant);
  }

  private static RldItemStack get(Random random, int level, ToolType toolType, Quality quality, boolean enchant) {
    return isSpecial(random)
        ? SpecialTool.createTool(random, quality)
        : toolType
            .asItem()
            .withQuality(quality)
            .plzEnchantAtLevel(enchant ? LootItem.getEnchantmentLevel(random, level) : 0)
            .asStack();
  }


}
