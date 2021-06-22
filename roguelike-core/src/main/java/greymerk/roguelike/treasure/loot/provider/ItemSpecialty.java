package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import com.github.fnar.util.Color;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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

  public static ItemStack getRandomItem(Random rand, int level) {
    return getRandomItem(Equipment.values()[rand.nextInt(Equipment.values().length)], rand, level);
  }

  public static ItemStack getRandomItem(Equipment type, Random rand, int level) {
    return getRandomItem(type, rand, Quality.get(rand, level, type));
  }

  public static ItemStack getRandomItem(Equipment type, Random random, Quality quality) {

    switch (type) {
      case SWORD:
        return getSword(random, quality);
      case BOW:
        return getBow(random, quality);
      case HELMET:
        return getHelmet(random, quality);
      case CHEST:
        return getChest(random, quality);
      case LEGS:
        return getLegs(random, quality);
      case FEET:
        return getBoots(random, quality);
      case PICK:
        return getPick(random, quality);
      case AXE:
        return getAxe(random, quality);
      case SHOVEL:
        return getShovel(random, quality);
      default:
        return null;
    }
  }

  public static ItemStack getRandomArmour(Random rand, Quality quality) {
    switch (rand.nextInt(4)) {
      case 0:
        return getRandomItem(Equipment.HELMET, rand, quality);
      case 1:
        return getRandomItem(Equipment.CHEST, rand, quality);
      case 2:
        return getRandomItem(Equipment.LEGS, rand, quality);
      case 3:
        return getRandomItem(Equipment.FEET, rand, quality);
      default:
        return null;
    }
  }

  public static ItemStack getRandomTool(Random rand, Quality quality) {
    switch (rand.nextInt(3)) {
      case 0:
        return getRandomItem(Equipment.PICK, rand, quality);
      case 1:
        return getRandomItem(Equipment.AXE, rand, quality);
      case 2:
        return getRandomItem(Equipment.SHOVEL, rand, quality);
      default:
        return null;
    }
  }

  private static ItemStack getShovel(Random random, Quality quality) {
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

  private static ItemStack getAxe(Random random, Quality quality) {
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

  private static ItemStack getPick(Random random, Quality quality) {
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

  private static ItemStack getSword(Random random, Quality quality) {
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

  private static ItemStack getBow(Random random, Quality quality) {
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

  private static ItemStack getHelmet(Random random, Quality quality) {
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

  private static ItemStack getBoots(Random random, Quality quality) {
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

  private static ItemStack getLegs(Random random, Quality quality) {
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

  private static ItemStack getChest(Random random, Quality quality) {
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
  public ItemStack get(Random rand) {
    Equipment t = this.type == null ? Equipment.values()[rand.nextInt(Equipment.values().length)] : this.type;
    Quality q = this.quality == null ? Quality.get(rand, level, t) : this.quality;
    return getRandomItem(t, rand, q);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {

    Quality q;
    switch (level) {
      case 0:
        q = Quality.WOOD;
        break;
      case 1:
        q = Quality.STONE;
        break;
      case 2:
        q = Quality.IRON;
        break;
      case 3:
        q = Quality.GOLD;
        break;
      case 4:
        q = Quality.DIAMOND;
        break;
      default:
        q = Quality.WOOD;
        break;
    }

    return getRandomItem(Equipment.values()[rand.nextInt(Equipment.values().length)], rand, q);
  }
}
