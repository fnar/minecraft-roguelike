package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.roguelike.loot.special.armour.SpecialArmour;
import com.github.fnar.util.Color;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class ItemArmour extends ItemBase {

  private static final Map<Integer, IWeighted<Quality>> armourQuality = new HashMap<>();
  static {
    ItemArmour.loadArmourQualityOddsTable();
  }

  private Equipment equipment;
  private boolean enchant;
  private Quality quality;

  public ItemArmour(int weight, int level) {
    super(weight, level);
  }

  public ItemArmour(JsonObject data, int weight) throws Exception {
    super(weight);

    this.enchant = !data.has("ench") || data.get("ench").getAsBoolean();
    if (!data.has("level")) {
      throw new Exception("Armour requires a level");
    }
    this.level = data.get("level").getAsInt();

    if (data.has("equipment")) {
      try {
        this.equipment = Equipment.valueOf(data.get("equipment").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Equipment as: " + data.get("equipment").getAsString());
      }
    }

    if (data.has("quality")) {
      this.level = data.get("level").getAsInt();

      try {
        this.quality = Quality.valueOf(data.get("quality").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Quality as: " + data.get("quality").getAsString());
      }
    }
  }

  public static ItemStack create(Random random, int level, ArmourType armourType, boolean enchant, Quality quality) {
    ItemStack itemStack = new ItemStack(armourType.asItem(quality));
    dyeArmor(quality, itemStack, Color.random(random));
    if (enchant) {
      return Enchant.enchantItem(random, itemStack, Enchant.getLevel(random, level));
    }
    return itemStack;
  }

  public static ItemStack create(ArmourType armourType, Quality quality, Color color) {
    ItemStack itemStack = new ItemStack(armourType.asItem(quality));
    dyeArmor(quality, itemStack, color);
    return itemStack;
  }

  public static ItemStack getRandom(Random random, int level, int enchantLevel, ArmourType armourType, Color color) {
    Quality quality = rollArmourQuality(random, level);
    ItemStack itemStack = new ItemStack(armourType.asItem(quality));
    dyeArmor(quality, itemStack, color);

    if (enchantLevel > 0) {
      Enchant.enchantItem(random, itemStack, enchantLevel);
    }

    return itemStack;
  }

  private static void dyeArmor(Quality armourQuality, ItemStack item, Color random2) {
    if (armourQuality == Quality.WOOD) {
      dyeArmor(item, random2);
    }
  }

  public static ItemStack dyeArmor(ItemStack armor, Color color) {
    NBTTagCompound nbtdata = armor.getTagCompound();

    if (nbtdata == null) {
      nbtdata = new NBTTagCompound();
      armor.setTagCompound(nbtdata);
    }

    NBTTagCompound nbtDisplay = nbtdata.getCompoundTag("display");

    if (!nbtdata.hasKey("display")) {
      nbtdata.setTag("display", nbtDisplay);
    }

    nbtDisplay.setInteger("color", color.asInt());

    return armor;
  }

  public static void loadArmourQualityOddsTable() {
    for (int i = 0; i < 5; ++i) {
      WeightedRandomizer<Quality> armour = new WeightedRandomizer<>();
      switch (i) {
        case 0:
          armour.add(new WeightedChoice<>(Quality.WOOD, 250));
          armour.add(new WeightedChoice<>(Quality.STONE, 50));
          armour.add(new WeightedChoice<>(Quality.IRON, 20));
          armour.add(new WeightedChoice<>(Quality.GOLD, 3));
          armour.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 1:
          armour.add(new WeightedChoice<>(Quality.WOOD, 150));
          armour.add(new WeightedChoice<>(Quality.STONE, 30));
          armour.add(new WeightedChoice<>(Quality.IRON, 10));
          armour.add(new WeightedChoice<>(Quality.GOLD, 3));
          armour.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 2:
          armour.add(new WeightedChoice<>(Quality.WOOD, 50));
          armour.add(new WeightedChoice<>(Quality.STONE, 30));
          armour.add(new WeightedChoice<>(Quality.IRON, 20));
          armour.add(new WeightedChoice<>(Quality.GOLD, 3));
          armour.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 3:
          armour.add(new WeightedChoice<>(Quality.WOOD, 20));
          armour.add(new WeightedChoice<>(Quality.STONE, 10));
          armour.add(new WeightedChoice<>(Quality.IRON, 10));
          armour.add(new WeightedChoice<>(Quality.GOLD, 5));
          armour.add(new WeightedChoice<>(Quality.DIAMOND, 3));
          break;
        case 4:
          armour.add(new WeightedChoice<>(Quality.WOOD, 2));
          armour.add(new WeightedChoice<>(Quality.STONE, 3));
          armour.add(new WeightedChoice<>(Quality.IRON, 10));
          armour.add(new WeightedChoice<>(Quality.GOLD, 3));
          armour.add(new WeightedChoice<>(Quality.DIAMOND, 3));
          break;
      }
      armourQuality.put(i, armour);
    }
  }

  public static Quality rollArmourQuality(Random rand, int level) {
    return armourQuality.get(level).get(rand);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    if (equipment != null || quality != null) {
      Quality quality = Optional.ofNullable(this.quality).orElseGet(() -> Quality.get(level));
      ArmourType armourType;
      if (equipment == null) {
        armourType = ArmourType.random(rand);
      } else {
        armourType = equipment.asArmourType();
      }

      if (armourType == null) {
        armourType = ArmourType.random(rand);
      }
      return create(rand, level, armourType, enchant, quality);
    }
    int enchantLevel = Enchant.getLevel(rand, level);
    boolean isSpecialArmour = enchantLevel > 0 && rand.nextInt(20 + (level * 10)) == 0;
    return isSpecialArmour
        ? SpecialArmour.createArmour(rand, level)
        : getRandom(rand, level, enchantLevel, ArmourType.random(rand), Color.random(rand));
  }

}
