package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.loot.special.armour.SpecialArmour;
import com.github.fnar.util.Color;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class ArmourLootItem extends LootItem {

  private Equipment equipment;
  private Quality quality;
  private boolean enchant;

  public ArmourLootItem(int weight, int level) {
    super(weight, level);
  }

  public ArmourLootItem(int weight, int level, Equipment equipment, Quality quality, boolean enchant) {
    super(weight, level);
    this.equipment = equipment;
    this.quality = quality;
    this.enchant = enchant;
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    return isSpecial(random)
        ? SpecialArmour.createArmour(random, getArmourType(random), getQuality(random)).complete()
        : getArmourType(random)
            .asItem()
            .withQuality(getQuality(random))
            .withColor(Color.random(random))
            .plzEnchantAtLevel(this.enchant ? getEnchantmentLevel(random, level) : 0)
            .asStack();
  }

  private Quality getQuality(Random random) {
    return Optional.ofNullable(this.quality).orElseGet(() -> Equipment.rollQuality(random, level));
  }

  private ArmourType getArmourType(Random random) {
    return Optional.ofNullable(equipment).map(Equipment::asArmourType).orElseGet(() -> ArmourType.random(random));
  }
}
