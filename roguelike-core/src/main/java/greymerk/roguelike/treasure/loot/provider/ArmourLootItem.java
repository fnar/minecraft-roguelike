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
  public RldItemStack getLootItem(Random random, int level) {
    ArmourType armourType = Optional.ofNullable(equipment).map(Equipment::asArmourType).orElseGet(() -> ArmourType.random(random));
    Quality quality = Optional.ofNullable(this.quality).orElseGet(() -> Equipment.rollQuality(random, level));
    return get(random, level, armourType, quality, Color.random(random), this.enchant);
  }

  public static RldItemStack get(Random rand, int level, ArmourType armourType, Color color, int difficulty) {
    boolean isEnchanted = isEnchanted(difficulty, rand, level);
    return get(rand, level, armourType, color, isEnchanted);
  }

  private static RldItemStack get(Random rand, int level, ArmourType armourType, Color color, boolean isEnchanted) {
    Quality quality = Equipment.rollQuality(rand, level);
    return get(rand, level, armourType, quality, color, isEnchanted);
  }

  private static RldItemStack get(Random rand, int level, ArmourType armourType, Quality quality, Color color, boolean isEnchanted) {
    return isSpecial(rand, level)
        ? SpecialArmour.createArmour(rand, quality).complete()
        : armourType
            .asItem()
            .withQuality(quality)
            .withColor(color)
            .plzEnchantAtLevel(isEnchanted ? getEnchantmentLevel(rand, level) : 0)
            .asStack();
  }
}
