package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class SpecialtyLootItem extends LootItem {

  private Equipment type;
  private Quality quality;

  public SpecialtyLootItem(int weight, int level) {
    super(weight, level);
  }

  public SpecialtyLootItem(int weight, int level, Quality q) {
    super(weight, level);
    this.quality = q;
  }

  public SpecialtyLootItem(int weight, int level, Equipment type, Quality quality) {
    super(weight, level);
    this.type = type;
    this.quality = quality;
  }

  public static boolean rollForSpecial(Random random) {
    return random.nextDouble() < .05;
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    Equipment equipmentType = Optional.ofNullable(this.type)
        .orElseGet(() -> Equipment.random(random));

    Quality quality = Optional.ofNullable(this.quality)
        .orElseGet(() -> Equipment.rollQuality(random, level));

    return SpecialEquipment.newRandomSpecialEquipment(random, equipmentType, quality);
  }
}
