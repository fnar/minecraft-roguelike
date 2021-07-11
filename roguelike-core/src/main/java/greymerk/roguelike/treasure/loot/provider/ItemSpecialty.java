package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import com.github.fnar.util.Color;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

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

  public static ItemStack getRandomItem(Random random, int level) {
    Equipment equipment = Equipment.random(random);
    return getRandomItem(random, equipment, level);
  }

  public static ItemStack getRandomItem(Random random, Equipment type, int level) {
    Quality quality = Quality.rollRandomQualityByLevel(random, level, type);
    return getRandomItem(random, type, quality);
  }

  public static ItemStack getRandomItem(Random random, Equipment type, Quality quality) {

    switch (type) {
      case SWORD:
        return createSword(random, quality);
      case BOW:
        return createBow(random, quality);
      case HELMET:
        return createHelmet(random, quality);
      case CHEST:
        return createChestplate(random, quality);
      case LEGS:
        return createLeggings(random, quality);
      case FEET:
        return createBoots(random, quality);
      case PICK:
        return createPickaxe(random, quality);
      case AXE:
        return createAxe(random, quality);
      case SHOVEL:
        return createShovel(random, quality);
      default:
        return null;
    }
  }

  public static ItemStack createArmour(Random random, int level) {
    Quality quality = Quality.rollArmourQuality(random, level);
    return createArmour(random, quality);
  }

  public static ItemStack createArmour(Random random, Quality quality) {
    switch (random.nextInt(4)) {
      case 0:
        return createHelmet(random, quality);
      case 1:
        return createChestplate(random, quality);
      case 2:
        return createLeggings(random, quality);
      case 3:
        return createBoots(random, quality);
      default:
        return null;
    }
  }

  public static ItemStack createTool(Random random, int level) {
    Quality quality = Quality.rollToolQuality(random, level);
    return createTool(random, quality);
  }

  public static ItemStack createTool(Random random, Quality quality) {
    switch (random.nextInt(3)) {
      case 0:
        return createPickaxe(random, quality);
      case 1:
        return createAxe(random, quality);
      case 2:
        return createShovel(random, quality);
      default:
        return null;
    }
  }

  public static ItemStack createShovel(Random random, int level) {
    Quality quality = Quality.rollWeaponQuality(random, level);
    return createShovel(random, quality);
  }

  private static ItemStack createShovel(Random random, Quality quality) {
    String name = quality.getDescriptor() + " Spade";
    switch (quality) {
      case DIAMOND:
        return enchantAndRenameTool(Items.DIAMOND_SHOVEL, name, random);
      case GOLD:
        return enchantAndRenameTool(Items.GOLDEN_SHOVEL, name, random);
      case IRON:
        return enchantAndRenameTool(Items.IRON_SHOVEL, "Grave Spade", random);
      case STONE:
        return enchantAndRenameTool(Items.STONE_SHOVEL, name, random);
      case WOOD:
      default:
        return enchantAndRenameTool(Items.WOODEN_SHOVEL, name, random);
    }
  }

  public static ItemStack createAxe(Random random, int level) {
    Quality quality = Quality.rollWeaponQuality(random, level);
    return createAxe(random, quality);
  }

  private static ItemStack createAxe(Random random, Quality quality) {
    String name = quality.getDescriptor() + " Axe";
    switch (quality) {
      case DIAMOND:
        return enchantAndRenameTool(Items.DIAMOND_AXE, name, random);
      case GOLD:
        return enchantAndRenameTool(Items.GOLDEN_AXE, name, random);
      case IRON:
        return enchantAndRenameTool(Items.IRON_AXE, "Woodland Hatchet", random);
      case STONE:
        return enchantAndRenameTool(Items.STONE_AXE, name, random);
      case WOOD:
      default:
        return enchantAndRenameTool(Items.WOODEN_AXE, name, random);
    }
  }

  public static ItemStack createPickaxe(Random random, int level) {
    Quality quality = Quality.rollWeaponQuality(random, level);
    return createPickaxe(random, quality);
  }

  private static ItemStack createPickaxe(Random random, Quality quality) {
    String name = quality.getDescriptor() + " Pick";
    switch (quality) {
      case DIAMOND:
        return enchantAndRenameTool(Items.DIAMOND_PICKAXE, name, random);
      case GOLD:
        return enchantAndRenameTool(Items.GOLDEN_PICKAXE, name, random);
      case IRON:
        return enchantAndRenameTool(Items.IRON_PICKAXE, name, random);
      case STONE:
        return enchantAndRenameTool(Items.STONE_PICKAXE, name, random);
      case WOOD:
      default:
        return enchantAndRenameTool(Items.WOODEN_PICKAXE, name, random);
    }
  }

  private static ItemStack enchantAndRenameTool(Item tool, String name, Random random) {
    ItemStack itemStack = new ItemStack(tool);
    name = rollToolEnchantments(itemStack, name, random);
    name = rollGeneralEnchantments(itemStack, name, random);
    itemStack.setStackDisplayName(name);
    return itemStack;
  }

  private static String rollToolEnchantments(ItemStack itemStack, String name, Random random) {
    name = rollEfficiency(itemStack, name, random);
    name = rollSilkTouch(itemStack, name, random);
    name = rollFortune(itemStack, name, random);
    return name;
  }

  private static String rollEfficiency(ItemStack itemStack, String name, Random random) {
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return name;
    }
    itemStack.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), enchantmentLevel);
    return enchantmentLevel >= 3 ? "Masterwork " + name : name;
  }

  private static String rollFortune(ItemStack item, String name, Random random) {
    if (random.nextInt(10) != 0) {
      return name;
    }
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return name;
    }
    item.addEnchantment(Enchant.getEnchant(Enchant.FORTUNE), enchantmentLevel);
    return name + " of Prospecting";
  }

  private static String rollSilkTouch(ItemStack item, String name, Random random) {
    if (random.nextInt(10) != 0) {
      return name;
    }
    item.addEnchantment(Enchant.getEnchant(Enchant.SILKTOUCH), 1);
    name = "Precision " + name;
    return name;
  }

  private static String rollUnbreaking(ItemStack itemStack, String name, Random random) {
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return name;
    }
    itemStack.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), enchantmentLevel);
    return enchantmentLevel >= 3 ? "Reinforced " + name : name;
  }

  private static String rollGeneralEnchantments(ItemStack itemStack, String name, Random random) {
    name = rollUnbreaking(itemStack, name, random);
    name = rollMending(itemStack, name, random);
    return name;
  }

  private static String rollMending(ItemStack item, String name, Random random) {
    if (random.nextInt(10) != 0) {
      return name;
    }
    item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
    return "Prideful " + name;
  }

  public static ItemStack createSword(Random random, int level) {
    Quality quality = Quality.rollWeaponQuality(random, level);
    return createSword(random, quality);
  }

  public static ItemStack createSword(Random random, Quality quality) {
    String name = quality.getDescriptor() + " Blade";
    switch (quality) {
      default:
      case WOOD:
        return enchantAndRenameSword(Items.WOODEN_SWORD, name, random);
      case STONE:
        return enchantAndRenameSword(Items.STONE_SWORD, name, random);
      case IRON:
        return enchantAndRenameSword(Items.IRON_SWORD, name, random);
      case GOLD:
        return enchantAndRenameSword(Items.GOLDEN_SWORD, name, random);
      case DIAMOND:
        return enchantAndRenameSword(Items.DIAMOND_SWORD, name, random);
    }
  }

  private static ItemStack enchantAndRenameSword(Item sword, String name, Random random) {
    ItemStack itemStack = new ItemStack(sword);
    name = rollSwordEnchantments(itemStack, name, random);
    name = rollGeneralEnchantments(itemStack, name, random);
    itemStack.setStackDisplayName(name);
    return itemStack;
  }

  private static String rollSwordEnchantments(ItemStack item, String name, Random random) {
    name = rollSharpness(item, name, random);
    name = rollLooting(item, name, random);
    name = rollFiery(item, name, random);
    return name;
  }

  private static String rollSharpness(ItemStack item, String name, Random random) {
    if (random.nextBoolean()) {
      return name;
    }
    int enchantmentLevel = random.nextInt(6);
    if (enchantmentLevel <= 0) {
      return name;
    }
    item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), enchantmentLevel);
    return name;
  }

  private static String rollLooting(ItemStack item, String name, Random random) {
    if (random.nextBoolean()) {
      return name;
    }
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return name;
    }
    item.addEnchantment(Enchant.getEnchant(Enchant.LOOTING), enchantmentLevel);
    if (enchantmentLevel < 3) {
      return "Burglar's " + name;
    }
    Loot.setItemLore(item, "Once belonged to a king of hidden desert thieves.", TextFormat.DARKGREEN);
    return "Bandit King's " + name;
  }

  private static String rollFiery(ItemStack item, String name, Random random) {
    if (random.nextBoolean()) {
      return name;
    }
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return name;
    }
    item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), enchantmentLevel);
    if (enchantmentLevel == 1) {
      Loot.setItemLore(item, "Warm to the touch", TextFormat.YELLOW);
      return "Ember " + name;
    }

    if (enchantmentLevel == 2) {
      Loot.setItemLore(item, "Almost too hot to hold", TextFormat.RED);
      return "Fiery " + name;
    }

    if (enchantmentLevel == 3) {
      Loot.setItemLore(item, "From the fiery depths", TextFormat.DARKRED);
      return name + " of the Inferno";
    }
    return name;
  }

  public static ItemStack createBow(Random random, int level) {
    Quality quality = Quality.rollWeaponQuality(random, level);
    return createBow(random, quality);
  }

  private static ItemStack createBow(Random random, Quality quality) {
    String name;
    ItemStack item = new ItemStack(Items.BOW);

    rollPower(random, item);

    switch (quality) {
      case WOOD:
      case STONE:
        name = "Yew Longbow";
        name = rollUnbreaking(item, name, random);
        Loot.setItemLore(item, "Superior craftsmanship", TextFormat.DARKGREEN);
        item.setStackDisplayName(name);
        return item;
      default:
      case IRON:
        name = "Laminated Bow";
        name = rollUnbreaking(item, name, random);
        item.setStackDisplayName(name);
        Loot.setItemLore(item, "Highly polished", TextFormat.DARKGREEN);
        return item;
      case GOLD:
        if (random.nextBoolean()) {
          item.addEnchantment(Enchant.getEnchant(Enchant.INFINITY), 1);
          name = "Elven Bow";
          name = rollUnbreaking(item, name, random);
          item.setStackDisplayName(name);
          Loot.setItemLore(item, "Beautifully crafted", TextFormat.DARKGREEN);
          return item;
        }

        if (random.nextBoolean()) {
          item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
          name = "Faerie Bow";
          name = rollUnbreaking(item, name, random);
          item.setStackDisplayName(name);
          Loot.setItemLore(item, "Beautifully crafted", TextFormat.DARKGREEN);
          return item;
        }
        name = "Recurve Bow";
        name = rollUnbreaking(item, name, random);
        item.setStackDisplayName(name);
        Loot.setItemLore(item, "Curves outward toward the target", TextFormat.DARKGREEN);
        return item;
      case DIAMOND:
        item.addEnchantment(Enchant.getEnchant(Enchant.FLAME), 1);
        name = "Eldritch Bow";
        name = rollUnbreaking(item, name, random);
        item.setStackDisplayName(name);
        Loot.setItemLore(item, "Warm to the touch", TextFormat.DARKGREEN);
        return item;
    }
  }

  private static void rollPower(Random random, ItemStack item) {
    int enchantmentLevel = random.nextInt(4);
    if (enchantmentLevel <= 0) {
      return;
    }
    item.addEnchantment(Enchant.getEnchant(Enchant.POWER), enchantmentLevel);
  }

  public static ItemStack createHelmet(Random random, int level) {
    Quality quality = Quality.rollRandomQualityByLevel(random, level, Equipment.HELMET);
    return ItemSpecialty.createHelmet(random, quality);
  }

  private static ItemStack createHelmet(Random random, Quality quality) {
    ItemStack item;

    String canonical = "";

    switch (quality) {
      case WOOD:
        item = new ItemStack(Items.LEATHER_HELMET);
        ItemArmour.dyeArmor(item, Color.random(random));
        canonical = "Skullcap";
        break;
      case STONE:
        item = new ItemStack(Items.CHAINMAIL_HELMET);
        canonical = "Coif";
        break;
      case IRON:
        item = new ItemStack(Items.IRON_HELMET);
        canonical = "Sallet";
        break;
      case GOLD:
        item = new ItemStack(Items.GOLDEN_HELMET);
        canonical = "Crown";
        break;
      case DIAMOND:
        item = new ItemStack(Items.DIAMOND_HELMET);
        canonical = "Helm";
        break;
      default:
        item = new ItemStack(Items.LEATHER_HELMET);
    }


    String suffix = "";

    if (random.nextInt(20) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, random));
      item.addEnchantment(Enchant.getEnchant(Enchant.RESPIRATION), 3);
      item.addEnchantment(Enchant.getEnchant(Enchant.AQUAAFFINITY), 1);
      suffix = "of Diving";
    } else if (random.nextInt(3) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, random));
      suffix = "of Deflection";
    } else {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, random));
      suffix = "of Defense";
    }

    return commonArmorThingy(random, quality, item, canonical, suffix);
  }

  private static ItemStack commonArmorThingy(Random random, Quality quality, ItemStack item, String canonical, String suffix) {
    String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
    name = rollUnbreaking(item, name, random);
    name = rollMending(item, name, random);
    item.setStackDisplayName(name);
    return item;
  }

  public static ItemStack createBoots(Random random, int level) {
    Quality quality = Quality.rollRandomQualityByLevel(random, level, Equipment.FEET);
    return ItemSpecialty.createBoots(random, quality);
  }

  private static ItemStack createBoots(Random random, Quality quality) {
    ItemStack item;

    String canonical = "";

    switch (quality) {
      case WOOD:
        item = new ItemStack(Items.LEATHER_BOOTS);
        ItemArmour.dyeArmor(item, Color.random());
        canonical = "Shoes";
        break;
      case STONE:
        item = new ItemStack(Items.CHAINMAIL_BOOTS);
        canonical = "Greaves";
        break;
      case IRON:
      case GOLD:
        item = new ItemStack(Items.IRON_BOOTS);
        canonical = "Sabatons";
        break;
      case DIAMOND:
        item = new ItemStack(Items.DIAMOND_BOOTS);
        canonical = "Boots";
        break;
      default:
        item = new ItemStack(Items.LEATHER_BOOTS);
        canonical = "Shoes";
    }

    String suffix = "";

    if (random.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), getProtectionLevel(quality, random));
      suffix = "of Warding";
    } else if (random.nextInt(5) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, random));
      item.addEnchantment(Enchant.getEnchant(Enchant.FEATHERFALLING), quality == Quality.DIAMOND ? 4 : 1 + random.nextInt(3));
      suffix = "of Lightness";
    } else if (random.nextInt(3) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, random));
      suffix = "of Deflection";
    } else {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, random));
      suffix = "of Defense";
    }

    return commonArmorThingy(random, quality, item, canonical, suffix);
  }

  public static ItemStack createLeggings(Random random, int level) {
    Quality quality = Quality.rollRandomQualityByLevel(random, level, Equipment.LEGS);
    return ItemSpecialty.createLeggings(random, quality);
  }

  private static ItemStack createLeggings(Random random, Quality quality) {
    ItemStack item;

    String canonical = "";

    switch (quality) {
      case WOOD:
        item = new ItemStack(Items.LEATHER_LEGGINGS);
        ItemArmour.dyeArmor(item, Color.random());
        canonical = "Pantaloons";
        break;
      case STONE:
        item = new ItemStack(Items.CHAINMAIL_LEGGINGS);
        canonical = "Chausses";
        break;
      case IRON:
      case GOLD:
        item = new ItemStack(Items.IRON_LEGGINGS);
        canonical = "Leg-plates";
        break;
      case DIAMOND:
        item = new ItemStack(Items.DIAMOND_LEGGINGS);
        canonical = "Leggings";
        break;
      default:
        item = new ItemStack(Items.LEATHER_LEGGINGS);
    }

    String suffix = "";

    if (random.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.FIREPROTECTION), getProtectionLevel(quality, random));
      suffix = "of Warding";
    } else if (random.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), getProtectionLevel(quality, random));
      suffix = "of Integrity";
    } else if (random.nextInt(3) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, random));
      suffix = "of Deflection";
    } else {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, random));
      suffix = "of Defense";
    }

    return commonArmorThingy(random, quality, item, canonical, suffix);
  }

  public static ItemStack createChestplate(Random random, int level) {
    Quality quality = Quality.rollRandomQualityByLevel(random, level, Equipment.CHEST);
    return ItemSpecialty.createChestplate(random, quality);
  }

  private static ItemStack createChestplate(Random random, Quality quality) {
    ItemStack item;

    String canonical = "";

    switch (quality) {
      case WOOD:
        item = new ItemStack(Items.LEATHER_CHESTPLATE);
        ItemArmour.dyeArmor(item, Color.random());
        canonical = "Tunic";
        break;
      case STONE:
        item = new ItemStack(Items.CHAINMAIL_CHESTPLATE);
        canonical = "Hauberk";
        break;
      case IRON:
      case GOLD:
        item = new ItemStack(Items.IRON_CHESTPLATE);
        canonical = "Cuirass";
        break;
      case DIAMOND:
        item = new ItemStack(Items.DIAMOND_CHESTPLATE);
        canonical = "Plate";
        break;
      default:
        item = new ItemStack(Items.LEATHER_CHESTPLATE);
        canonical = "Tunic";
    }

    String suffix = "";

    if (random.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.FIREPROTECTION), getProtectionLevel(quality, random));
      suffix = "of Flamewarding";
    } else if (random.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), getProtectionLevel(quality, random));
      suffix = "of Integrity";
    } else if (random.nextInt(3) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, random));
      suffix = "of Deflection";
    } else {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, random));
      suffix = "of Defense";
    }

    return commonArmorThingy(random, quality, item, canonical, suffix);
  }

  private static int getProtectionLevel(Quality quality, Random rand) {

    int value = 1;

    switch (quality) {
      case WOOD:
        if (rand.nextInt(3) == 0) {
          value++;
        }
        break;
      case STONE:
        if (rand.nextBoolean()) {
          value++;
        }
        break;
      case IRON:
      case GOLD:
        value += rand.nextInt(3);
        break;
      case DIAMOND:
        value += 2 + rand.nextInt(2);
        break;
    }

    return value;
  }

  private static String getArmourPrefix(Quality quality) {
    switch (quality) {
      case WOOD:
        return "Surplus";
      case STONE:
        return "Riveted";
      case IRON:
        return "Gothic";
      case GOLD:
        return "Jewelled";
      case DIAMOND:
        return "Crystal";
      default:
        return "Strange";
    }
  }

  @Override
  public ItemStack get(Random random) {
    Equipment equipmentType = Optional.ofNullable(this.type)
        .orElseGet(() -> Equipment.random(random));

    Quality quality = Optional.ofNullable(this.quality)
        .orElseGet(() -> Quality.rollRandomQualityByLevel(random, level, equipmentType));

    return getRandomItem(random, equipmentType, quality);
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
