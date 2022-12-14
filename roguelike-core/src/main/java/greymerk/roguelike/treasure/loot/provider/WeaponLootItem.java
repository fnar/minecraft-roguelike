package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.WeaponType;
import com.github.fnar.roguelike.loot.special.weapons.SpecialBow;
import com.github.fnar.roguelike.loot.special.weapons.SpecialSword;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;


public class WeaponLootItem extends LootItem {

  private Equipment type;
  private boolean enchant;
  private Quality quality;

  public WeaponLootItem(int weight, int level) {
    super(weight, level);
  }

  public WeaponLootItem(int weight, int level, Equipment type, Quality quality, boolean enchant) {
    super(weight, level);
    this.type = type;
    this.enchant = enchant;
    this.quality = quality;
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    Equipment type = Optional.ofNullable(this.type).orElseGet(() -> random.nextInt(8) == 0 ? Equipment.BOW : Equipment.SWORD);
    Quality quality = Optional.ofNullable(this.quality).orElseGet(() -> Equipment.rollQuality(random, level));
    switch (type) {
      case BOW:
        return getBow(random, level, enchant);
      case SWORD:
      default:
        return getSword(random, level, quality, enchant);
    }
  }

  public static RldItemStack getBow(Random random, int level, boolean isEnchanted) {
    return isSpecial(random)
        ? new SpecialBow(random, level).complete()
        : WeaponType.BOW.asItem()
            .plzEnchantAtLevel(getEnchantmentLevel(random, level, isEnchanted))
            .asStack();
  }

  public static RldItemStack getSword(Random random, int level, Quality quality, boolean isEnchanted) {
    return LootItem.isSpecial(random)
        ? new SpecialSword(random, level).complete()
        : WeaponType.SWORD.asItem()
            .withQuality(quality)
            .plzEnchantAtLevel(getEnchantmentLevel(random, level, isEnchanted))
            .asStack();
  }

  private static int getEnchantmentLevel(Random random, int level, boolean isEnchanted) {
    return isEnchanted ? LootItem.getEnchantmentLevel(random, level) : 0;
  }

}
