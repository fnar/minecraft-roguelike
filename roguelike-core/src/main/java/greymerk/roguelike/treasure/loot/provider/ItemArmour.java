package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import com.github.fnar.roguelike.loot.special.armour.SpecialArmour;
import com.github.fnar.util.Color;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Slot;

public class ItemArmour extends ItemBase {

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

  public static ItemStack create(Random rand, int level, Quality quality, Equipment armorEquipment, boolean enchant) {
    Quality ensuredQuality = Optional.ofNullable(quality).orElseGet(() -> Quality.get(level));
    ItemStack armorItem = armorEquipment.get(ensuredQuality);
    if (enchant) {
      return Enchant.enchantItem(rand, armorItem, Enchant.getLevel(rand, level));
    }
    return armorItem;
  }

  public static ItemStack getRandom(Random rand, int level, boolean enchant) {
    int enchantLevel = enchant ? Enchant.getLevel(rand, level) : 0;
    Slot slotByNumber = Slot.getSlotByNumber(rand.nextInt(4) + 1);
    return getRandom(rand, level, slotByNumber, enchantLevel);
  }

  public static ItemStack getRandom(Random rand, int level, Slot slot, boolean enchant) {
    return getRandom(rand, level, slot, enchant ? Enchant.getLevel(rand, level) : 0);
  }

  @SuppressWarnings("incomplete-switch")
  private static ItemStack getRandom(Random random, int level, Slot slot, int enchantLevel) {

    if (enchantLevel > 0 && random.nextInt(20 + (level * 10)) == 0) {
      return SpecialArmour.createArmour(random, level);
    }

    Quality armourQuality = Quality.rollArmourQuality(random, level);
    ItemStack item = create(slot, armourQuality);
    if (armourQuality == Quality.WOOD) {
      dyeArmor(item, Color.random(random));
    }

    if (enchantLevel > 0) {
      Enchant.enchantItem(random, item, enchantLevel);
    }

    return item;

  }

  public static ItemStack create(Slot slot, Quality quality, Color color) {
    ItemStack itemStack = create(slot, quality);
    if (quality == Quality.WOOD) {
      dyeArmor(itemStack, color);
    }
    return itemStack;
  }

  public static ItemStack create(Slot slot, Quality quality) {
    switch (slot) {
      case HEAD:
        switch (quality) {

          case DIAMOND:
            return new ItemStack(Items.DIAMOND_HELMET);
          case GOLD:
            return new ItemStack(Items.GOLDEN_HELMET);
          case IRON:
            return new ItemStack(Items.IRON_HELMET);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_HELMET);
          default:
            return new ItemStack(Items.LEATHER_HELMET);
        }

      case FEET:
        switch (quality) {

          case DIAMOND:
            return new ItemStack(Items.DIAMOND_BOOTS);
          case GOLD:
            return new ItemStack(Items.GOLDEN_BOOTS);
          case IRON:
            return new ItemStack(Items.IRON_BOOTS);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_BOOTS);
          default:
            return new ItemStack(Items.LEATHER_BOOTS);
        }

      case CHEST:
        switch (quality) {

          case DIAMOND:
            return new ItemStack(Items.DIAMOND_CHESTPLATE);
          case GOLD:
            return new ItemStack(Items.GOLDEN_CHESTPLATE);
          case IRON:
            return new ItemStack(Items.IRON_CHESTPLATE);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_CHESTPLATE);
          default:
            return new ItemStack(Items.LEATHER_CHESTPLATE);
        }
      case LEGS:
        switch (quality) {

          case DIAMOND:
            return new ItemStack(Items.DIAMOND_LEGGINGS);
          case GOLD:
            return new ItemStack(Items.GOLDEN_LEGGINGS);
          case IRON:
            return new ItemStack(Items.IRON_LEGGINGS);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_LEGGINGS);
          default:
            return new ItemStack(Items.LEATHER_LEGGINGS);
        }
    }
    return null;
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
  public ItemStack getLootItem(Random rand, int level) {
    if (equipment != null || quality != null) {
      return create(rand, level, quality, equipment, enchant);
    }
    return getRandom(rand, level, true);
  }


}
