package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.Weapon;
import com.github.fnar.minecraft.item.WeaponType;
import com.github.fnar.roguelike.loot.special.weapons.SpecialBow;
import com.github.fnar.roguelike.loot.special.weapons.SpecialSword;

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

  public static RldItemStack getRandom(Random rand, int rank, boolean enchant) {
    if (rand.nextInt(10) == 0) {
      return WeaponLootItem.getBow(rand, rank, enchant);
    } else {
      return WeaponLootItem.getSword(rand, rank, enchant);
    }
  }

  public static RldItemStack getBow(Random rand, int level, boolean enchant) {
    if (rand.nextInt(20 + (level * 10)) == 0) {
      return new SpecialBow(rand, level).complete();
    }

    Weapon bow = WeaponType.BOW.asItem();
    if (enchant && rand.nextInt(6 - level) == 0) {
      bow.plzEnchantAtLevel(LootItem.getEnchantmentLevel(rand, level));
    }
    return bow.asStack();
  }

  public static RldItemStack getSword(Random random, int level, boolean enchant) {
    if (enchant && random.nextInt(10 + (level * 10)) == 0) {
      return new SpecialSword(random, level).complete();
    }
    Weapon sword = randomSword(random, level);
    if (enchant && random.nextInt(6 - level) == 0) {
      sword.plzEnchantAtLevel(LootItem.getEnchantmentLevel(random, level));
    }
    return sword.asStack();
  }

  public static RldItemStack getSword(Random rand, int level, boolean enchant, Quality quality) {
    Weapon sword = quality == null
        ? randomSword(rand, level)
        : WeaponType.SWORD.asItem().withQuality(quality);

    if (enchant) {
      sword.plzEnchantAtLevel(LootItem.getEnchantmentLevel(rand, level));
    }
    return sword.asStack();
  }

  private static Weapon randomSword(Random random, int level) {
    Quality quality = WeaponQualityOddsTable.rollWeaponQuality(random, level);
    return WeaponType.SWORD.asItem().withQuality(quality);
  }

  @Override
  public RldItemStack getLootItem(Random rand, int level) {
    if (type != null) {
      switch (type) {
        case BOW:
          return getBow(rand, level, enchant);
        case SWORD:
          return getSword(rand, level, enchant, quality);
        default:
          return getSword(rand, level, enchant);
      }
    }
    return getRandom(rand, level, true);
  }

}
