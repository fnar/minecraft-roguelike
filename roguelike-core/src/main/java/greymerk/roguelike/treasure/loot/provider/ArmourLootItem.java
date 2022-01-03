package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.RldBaseItem;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.roguelike.loot.special.armour.SpecialArmour;
import com.github.fnar.util.Color;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class ArmourLootItem extends LootItem {

  private Equipment equipment;
  private boolean enchant;
  private Quality quality;

  public ArmourLootItem(int weight, int level) {
    super(weight, level);
  }

  public ArmourLootItem(JsonObject data, int weight) throws Exception {
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

  public static RldItemStack create(Random random, int level, ArmourType armourType, boolean enchant, Quality quality) {
    RldBaseItem armour = armourType
        .asItem()
        .withQuality(quality)
        .withColor(Color.random(random));
    if (enchant) {
      armour.plzEnchantAtLevel(LootItem.getEnchantmentLevel(random, level));
    }
    return armour.asStack();

  }

  public static ItemStack create(ArmourType armourType, Quality quality, Color color) {
    ItemStack itemStack = new ItemStack(armourType.asItem(quality));
    dyeArmor(quality, itemStack, color);
    return itemStack;
  }

  public static RldItemStack getRandom(Random random, int level, int enchantLevel, ArmourType armourType, Color color) {
    RldBaseItem armour = armourType
        .asItem()
        .withQuality(ArmourQualityOddsTable.rollArmourQuality(random, level))
        .withColor(color);
    if (enchantLevel > 0) {
      armour.plzEnchantAtLevel(LootItem.getEnchantmentLevel(random, level));
    }
    return armour.asStack();

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

  @Override
  public RldItemStack getLootItem(Random rand, int level) {
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
    int enchantLevel = LootItem.getEnchantmentLevel(rand, level);
    boolean isSpecialArmour = enchantLevel > 0 && rand.nextInt(20 + (level * 10)) == 0;
    return isSpecialArmour
        ? SpecialArmour.createArmour(rand, level)
        : getRandom(rand, level, enchantLevel, ArmourType.random(rand), Color.random(rand));
  }

}
