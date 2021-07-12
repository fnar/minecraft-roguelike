package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import com.github.fnar.roguelike.loot.special.SpecialEquipment;

import net.minecraft.item.ItemStack;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class ItemSpecialty extends ItemBase {

  private Equipment type;
  private Quality quality;

  public ItemSpecialty(int weight, int level) {
    super(weight, level);
  }

  public ItemSpecialty(JsonObject data, int weight) throws Exception {
    super(weight);
    if (!data.has("level")) {
      throw new Exception("Item requires a level");
    }
    this.level = data.get("level").getAsInt();

    if (data.has("quality")) {
      try {
        this.quality = Quality.valueOf(data.get("quality").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Quality as: " + data.get("quality").getAsString());
      }
    }

    if (data.has("equipment")) {
      try {
        this.type = Equipment.valueOf(data.get("equipment").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Equipment as: " + data.get("equipment").getAsString());
      }
    }
  }

  public ItemSpecialty(int weight, int level, Equipment type, Quality q) {
    super(weight, level);
    this.type = type;
    this.quality = q;
  }

  public ItemSpecialty(int weight, int level, Quality q) {
    super(weight, level);
    this.quality = q;
  }

  @Override
  public ItemStack get(Random random) {
    Equipment equipmentType = Optional.ofNullable(this.type)
        .orElseGet(() -> Equipment.random(random));

    Quality quality = Optional.ofNullable(this.quality)
        .orElseGet(() -> Quality.rollRandomQualityByLevel(random, level, equipmentType));

    return SpecialEquipment.getRandomEquipment(random, equipmentType, quality);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    // I think this isn't actually used.
    // The invoker of getLootItem() is the base class's get() method, which is overwritten here.
//    Equipment equipmentType = Equipment.random(rand);
//    Quality quality = Quality.get(level);
//    return getRandomItem(rand, equipmentType, quality);
    return null;
  }
}
