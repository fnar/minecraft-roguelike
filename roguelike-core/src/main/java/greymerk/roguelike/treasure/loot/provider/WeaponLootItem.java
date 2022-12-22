package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.loot.special.weapons.SpecialBow;
import com.github.fnar.roguelike.loot.special.weapons.SpecialSword;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;


public class WeaponLootItem extends LootItem {

  private Equipment type;
  private boolean isEnchanted;
  private Quality quality;

  public WeaponLootItem(int weight, int level) {
    super(weight, level);
  }

  public WeaponLootItem(int weight, int level, Equipment type, Quality quality, boolean isEnchanted) {
    super(weight, level);
    this.type = type;
    this.isEnchanted = isEnchanted;
    this.quality = quality;
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    return !SpecialtyLootItem.rollForSpecial(random)
        ? Equipment.asWeaponType(getType(random))
        .asItem()
        .withQuality(getQuality(random))
        .plzEnchantAtLevel(getEnchantLevel(random, level))
        .asStack()
        : random.nextBoolean()
            ? SpecialBow.newSpecialBow(random, getQuality(random))
            : SpecialSword.newSpecialSword(random, getQuality(random));
  }

  private Equipment getType(Random random) {
    return Optional.ofNullable(this.type).orElseGet(() -> random.nextInt(8) == 0 ? Equipment.BOW : Equipment.SWORD);
  }

  private Quality getQuality(Random random) {
    return Optional.ofNullable(this.quality).orElseGet(() -> Equipment.rollQuality(random, level));
  }

  private int getEnchantLevel(Random random, int level) {
    return isEnchanted ? LootItem.getEnchantmentLevel(random, level) : 0;
  }

}
