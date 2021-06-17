package greymerk.roguelike.treasure.loot.provider;


import com.google.gson.JsonObject;

import com.github.fnar.util.Colors;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.util.WeightedChoice;

public enum ItemNovelty {

  GREYMERK,
  NEBRISCROWN,
  NULL,
  MANPANTS,
  ZISTEAUSIGN,
  AVIDYA,
  ASHLEA,
  KURT,
  AMLP,
  CLEO,
  ENIKOSWORD,
  ENIKOBOW,
  BDOUBLEO,
  GUUDE,
  RLEAHY,
  ETHO,
  BAJ,
  DOCM,
  GINGER,
  VECHS,
  NOTCH,
  QUANTUMLEAP,
  GENERIKB,
  FOURLES,
  DINNERBONE,
  GRIM,
  MMILLSS,
  VALANDRAH;

  public static final Map<String, ItemNovelty> names;

  static {
    names = new HashMap<>();
    names.put("greymerk", ItemNovelty.GREYMERK);
    names.put("nebriscrown", ItemNovelty.NEBRISCROWN);
    names.put("nebrissword", ItemNovelty.NULL);
    names.put("zisteaupants", ItemNovelty.MANPANTS);
    names.put("zisteausign", ItemNovelty.ZISTEAUSIGN);
    names.put("avidya", ItemNovelty.AVIDYA);
    names.put("ashlea", ItemNovelty.ASHLEA);
    names.put("kurt", ItemNovelty.KURT);
    names.put("amlp", ItemNovelty.AMLP);
    names.put("cleo", ItemNovelty.CLEO);
    names.put("enikosword", ItemNovelty.ENIKOSWORD);
    names.put("enikobow", ItemNovelty.ENIKOBOW);
    names.put("bdoubleo", ItemNovelty.BDOUBLEO);
    names.put("guude", ItemNovelty.GUUDE);
    names.put("rleahy", ItemNovelty.RLEAHY);
    names.put("etho", ItemNovelty.ETHO);
    names.put("baj", ItemNovelty.BAJ);
    names.put("docm", ItemNovelty.DOCM);
    names.put("ginger", ItemNovelty.GINGER);
    names.put("vechs", ItemNovelty.VECHS);
    names.put("notch", ItemNovelty.NOTCH);
    names.put("quantumleap", ItemNovelty.QUANTUMLEAP);
    names.put("generikb", ItemNovelty.GENERIKB);
    names.put("fourles", ItemNovelty.FOURLES);
    names.put("dinnerbone", ItemNovelty.DINNERBONE);
    names.put("grim", ItemNovelty.GRIM);
    names.put("mmillss", ItemNovelty.MMILLSS);
    names.put("valandrah", ItemNovelty.VALANDRAH);
  }

  public static ItemStack getItemByName(String name) {
    if (!names.containsKey(name)) {
      return null;
    }
    return getItem(names.get(name));
  }

  public static IWeighted<ItemStack> get(JsonObject data, int weight) throws Exception {
    if (!data.has("name")) {
      throw new Exception("Novelty item requires a name");
    }
    String name = data.get("name").getAsString();
    ItemStack item = getItemByName(name);
    if (item == null) {
      throw new Exception("No such novelty name: " + name);
    }
    return new WeightedChoice<>(item, weight);
  }

  public static IWeighted<ItemStack> get(ItemNovelty choice, int weight) {
    return new WeightedChoice<>(getItem(choice), weight);
  }

  public static ItemStack getItem(ItemNovelty choice) {

    ItemStack item;

    switch (choice) {

      case GREYMERK:
        item = new ItemStack(Items.IRON_AXE);
        item.setStackDisplayName("Greymerk's Hatchet");
        Loot.setItemLore(item, "Pointlessly sharp", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 2);
        return item;
      case NEBRISCROWN:
        item = new ItemStack(Items.GOLDEN_HELMET);
        item.setStackDisplayName("Nebrian Crown of Justice");
        Loot.setItemLore(item, "Adorned with precious gemstones", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), 4);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
        return item;
      case NULL:
        item = new ItemStack(Items.DIAMOND_SWORD);
        item.setStackDisplayName("Null Pointer");
        Loot.setItemLore(item, "Exceptional", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
        return item;
      case MANPANTS:
        item = new ItemStack(Items.LEATHER_LEGGINGS);
        item.setStackDisplayName("Man Pants");
        Loot.setItemLore(item, "Yessss, Manpants!", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.FIREPROTECTION), 4);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
        ItemArmour.dyeArmor(item, Colors.SMOKED_SALMON);
        return item;
      case ZISTEAUSIGN:
        item = new ItemStack(Items.SIGN);
        Loot.setItemName(item, "Battle Sign", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"That's what you get!\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 3);
        item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
        return item;
      case AVIDYA:
        item = new ItemStack(Items.MILK_BUCKET);
        Loot.setItemName(item, "White Russian", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "The dude's favourite", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.ARTHOPODS), 4);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
        return item;
      case ASHLEA:
        item = new ItemStack(Items.COOKIE);
        Loot.setItemName(item, "Ashlea's Oatmeal Cookie", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "Perfect for elevensies", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
        return item;
      case KURT:
        item = new ItemStack(Items.LEATHER_BOOTS);
        item.setStackDisplayName("Farland Travellers");
        Loot.setItemLore(item, "Indeed!", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), 3);
        item.addEnchantment(Enchant.getEnchant(Enchant.FEATHERFALLING), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
        ItemArmour.dyeArmor(item, Colors.HARISSA_RED);
        return item;
      case AMLP:
        item = new ItemStack(Items.SHEARS);
        item.setStackDisplayName("Lascerator");
        Loot.setItemLore(item, "The wool collector", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
        return item;
      case CLEO:
        item = new ItemStack(Items.FISH);
        Loot.setItemName(item, "Cleophian Digging Feesh", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "Feesh are not efeeshent for digging", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 10);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 5);
        item.addEnchantment(Enchant.getEnchant(Enchant.FORTUNE), 5);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 10);
        return item;
      case BDOUBLEO:
        item = new ItemStack(Items.DIAMOND_SHOVEL);
        item.setStackDisplayName("Dig Job");
        Loot.setItemLore(item, "Recovered from hell's blazes", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 5);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
        return item;
      case GUUDE:
        item = new ItemStack(Items.RECORD_13);
        Loot.setItemName(item, "Boulderfistian Golden Record", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"You're Watching Guude Boulderfist...\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), 3);
        return item;
      case RLEAHY:
        item = new ItemStack(Items.BREAD);
        Loot.setItemName(item, "Rleahian battle sub", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "With extra pastrami", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 2);
        return item;
      case ETHO:
        item = new ItemStack(Items.WOODEN_PICKAXE);
        item.setStackDisplayName("Your Mum");
        Loot.setItemLore(item, "The original", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 5);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
        return item;
      case ENIKOBOW:
        item = new ItemStack(Items.BOW);
        item.setStackDisplayName("Eniko's String Theory");
        Loot.setItemLore(item, "For Science!", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.POWER), 5);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.INFINITY), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
        return item;
      case ENIKOSWORD:
        item = new ItemStack(Items.DIAMOND_SWORD);
        item.setStackDisplayName("Eniko's Earring");
        Loot.setItemLore(item, "\"She do the loot take boogie\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 5);
        item.addEnchantment(Enchant.getEnchant(Enchant.LOOTING), 3);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
        return item;
      case BAJ:
        item = new ItemStack(Items.GOLDEN_HOE);
        Loot.setItemName(item, "Baj's Last Resort", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"Starvation could be fatal\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.FORTUNE), 5);
        return item;
      case DOCM:
        item = new ItemStack(Items.FISHING_ROD);
        Loot.setItemName(item, "Rod of Command", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"Get to the dang land!\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
        return item;
      case GINGER:
        item = new ItemStack(Items.COOKED_CHICKEN);
        Loot.setItemName(item, "Spice Chicken", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"Kung Pao!\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 1);
        return item;
      case VECHS:
        item = new ItemStack(Items.STICK);
        Loot.setItemName(item, "Legendary Stick", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"Really?!\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 1);
        return item;
      case NOTCH:
        item = new ItemStack(Items.APPLE);
        Loot.setItemName(item, "Notch's apple", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "Imbued with the creator's power", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 10);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 10);
        return item;
      case QUANTUMLEAP:
        item = new ItemStack(Blocks.SPONGE);
        Loot.setItemName(item, "QuantumLeap's Swiss Cheese", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"Oh boy\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 4);
        return item;
      case GENERIKB:
        item = new ItemStack(Items.BAKED_POTATO);
        Loot.setItemName(item, "Hot Potato", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "All a hermit needs", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 3);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
        return item;
      case FOURLES:
        item = new ItemStack(Items.DYE, 1, 3);
        Loot.setItemName(item, "Fourles Darkroast Beans", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"Mmmm... Dark Roast\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 2);
        return item;
      case DINNERBONE:
        item = new ItemStack(Items.BONE, 1);
        Loot.setItemName(item, "Old Dinnerbone", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"Dang Skellies!\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3);
        item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 2);
        return item;
      case GRIM:
        item = new ItemStack(Items.ROTTEN_FLESH);
        Loot.setItemName(item, "Grim chew-toy", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"Come on Grim, let's do this!\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SMITE), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.LOOTING), 1);
        return item;
      case MMILLSS:
        item = new ItemStack(Blocks.CACTUS);
        Loot.setItemName(item, "MMillssian spider bane", TextFormat.DARKPURPLE);
        Loot.setItemLore(item, "\"I really don't need anymore string...\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.ARTHOPODS), 4);
        item.addEnchantment(Enchant.getEnchant(Enchant.THORNS), 2);
        item.addEnchantment(Enchant.getEnchant(Enchant.LOOTING), 1);
        return item;
      case VALANDRAH:
        item = new ItemStack(Items.IRON_SWORD);
        item.setStackDisplayName("Valandrah's Kiss");
        Loot.setItemLore(item, "\"Feel the kiss of my blade\"", TextFormat.DARKGREEN);
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 4);
        item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.KNOCKBACK), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 2);
        return item;
      default:
        return null;

    }
  }


}
