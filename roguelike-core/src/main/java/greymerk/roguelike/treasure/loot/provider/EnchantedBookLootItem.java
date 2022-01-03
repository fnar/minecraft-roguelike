package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.Miscellaneous;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

public class EnchantedBookLootItem extends LootItem {

  private final int enchantmentLevel;

  public EnchantedBookLootItem(int weight, int level) {
    this(weight, level, 0);
  }

  public EnchantedBookLootItem(int weight, int level, int enchantmentLevel) {
    super(weight, level);
    this.enchantmentLevel = enchantmentLevel;
  }

  @Override
  public RldItemStack getLootItem(Random rand, int level) {
    int enchantLevel = this.enchantmentLevel != 0 ? this.enchantmentLevel : LootItem.getEnchantmentLevel(rand, level);
    return Miscellaneous.Type.BOOK.asItem().plzEnchantAtLevel(enchantLevel).asStack();
  }

}
