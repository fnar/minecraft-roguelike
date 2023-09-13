package greymerk.roguelike.treasure.loot.provider;


import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.Dye;
import com.github.fnar.minecraft.item.Enchantment;
import com.github.fnar.minecraft.item.Food;
import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.Record;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.ToolType;
import com.github.fnar.minecraft.item.WeaponType;
import com.github.fnar.util.Color;

import java.util.HashMap;
import java.util.Map;

import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.util.TextFormat;

public enum ItemNovelty {

  AMLP,
  ASHLEA,
  AVIDYA,
  BAJ,
  BDOUBLEO,
  CLEO,
  DINNERBONE,
  DOCM,
  ENIKOBOW,
  ENIKOSWORD,
  ETHO,
  FOURLES,
  GENERIKB,
  GINGER,
  GREYMERK,
  GRIM,
  GUUDE,
  KURT,
  MANPANTS,
  MMILLSS,
  NEBRISCROWN,
  NOTCH,
  NULL,
  QUANTUMLEAP,
  RLEAHY,
  VALANDRAH,
  VECHS,
  ZISTEAUSIGN;

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

  public static RldItemStack getItemByName(String name) {
    if (!names.containsKey(name)) {
      return null;
    }
    return getItem(names.get(name));
  }

  public static RldItemStack getItem(ItemNovelty choice) {

    switch (choice) {
      case GREYMERK:
        return greymerksHatchet();
      case NEBRISCROWN:
        return nebrisCrown();
      case NULL:
        return nullPointer();
      case MANPANTS:
        return manPants();
      case ZISTEAUSIGN:
        return zisteauSign();
      case AVIDYA:
        return avidyasWhiteRussian();
      case ASHLEA:
        return ashleasOatmealCookie();
      case KURT:
        return farlandTravellers();
      case AMLP:
        return lascerator();
      case CLEO:
        return cleophianDiggingFeesh();
      case BDOUBLEO:
        return bDoubleOsDigJob();
      case GUUDE:
        return boulderfistianGoldenRecord();
      case RLEAHY:
        return rleahianBattleSub();
      case ETHO:
        return ethosYourMomJoke();
      case ENIKOBOW:
        return enikosStringTheory();
      case ENIKOSWORD:
        return enikosEarring();
      case BAJ:
        return bajsLastResort();
      case DOCM:
        return docmRodOfCommand();
      case GINGER:
        return gingerSpiceChicken();
      case VECHS:
        return vechsLegendaryStick();
      case NOTCH:
        return notchsApple();
      case QUANTUMLEAP:
        return quantumleapsSwissCheese();
      case GENERIKB:
        return generikBsHotPotato();
      case FOURLES:
        return fourlesDarkroastBeans();
      case DINNERBONE:
        return oldDinnerbone();
      case GRIM:
        return grimChewToy();
      case MMILLSS:
        return mmillssSpiderBane();
      case VALANDRAH:
        return valandrahsKiss();
      default:
        return null;
    }
  }

  public static RldItemStack greymerksHatchet() {
    return ToolType.AXE.asItem().withQuality(Quality.IRON)
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(3))
        .withEnchantment(Enchantment.Effect.KNOCKBACK)
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(2))
        .asStack()
        .withDisplayName("Greymerk's Hatchet")
        //.withDisplayLore(TextFormat.DARKGREEN.apply("Pointlessly sharp"));
        .withDisplayLore("lore.roguelike.greymerks_hatchet");
  }

  public static RldItemStack nebrisCrown() {
    return ArmourType.HELMET.asItem().golden()
        .withEnchantment(Enchantment.Effect.PROTECTION.atLevel(4))
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(3))
        .asStack()
        .withDisplayName("Nebrian Crown of Justice").withDisplayLore(TextFormat.DARKGREEN.apply("Adorned with precious gemstones"));
  }

  public static RldItemStack nullPointer() {
    return WeaponType.SWORD.asItem().diamond()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(5))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.atLevel(2))
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(3))
        .asStack()
        .withDisplayName("Null Pointer")
        //.withDisplayLore(TextFormat.DARKGREEN.apply("Exceptional"));
        .withDisplayLore("lore.roguelike.null_pointer");
  }

  public static RldItemStack manPants() {
    return ArmourType.LEGGINGS.asItem().leather()
        .withColor(Color.SMOKED_SALMON)
        .withEnchantment(Enchantment.Effect.FIRE_PROTECTION.atLevel(4))
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(3))
        .asStack()
        .withDisplayName("Man Pants")
        //.withDisplayLore(TextFormat.DARKGREEN.apply("Yessss, Manpants!"));
        .withDisplayLore("lore.roguelike.man_pants");
  }

  public static RldItemStack zisteauSign() {
    return BlockType.SIGN.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(5))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.atLevel(3))
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT)
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Battle Sign"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"That's what you get!\""));
        .withDisplayLore("lore.roguelike.battle_sign");
  }

  public static RldItemStack avidyasWhiteRussian() {
    return Food.Type.MILK_BUCKET.asItem()
        .withEnchantment(Enchantment.Effect.BANE_OF_ARTHROPODS.atLevel(4))
        .withEnchantment(Enchantment.Effect.KNOCKBACK)
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT)
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("White Russian"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("The dude's favourite"));
        .withDisplayLore("lore.roguelike.white_russian");
  }

  public static RldItemStack ashleasOatmealCookie() {
    return Food.Type.COOKIE.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(2))
        .withEnchantment(Enchantment.Effect.KNOCKBACK)
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Ashlea's Oatmeal Cookie"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("Perfect for elevensies"));
        .withDisplayLore("lore.roguelike.oatmeal_cookie");
  }

  public static RldItemStack farlandTravellers() {
    return ArmourType.BOOTS.asItem()
        .leather()
        .withColor(Color.HARISSA_RED)
        .withEnchantment(Enchantment.Effect.PROTECTION.atLevel(3))
        .withEnchantment(Enchantment.Effect.FEATHER_FALLING.atLevel(2))
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(3))
        .asStack()
        .withDisplayName("Farland Travellers")
        //.withDisplayLore(TextFormat.DARKGREEN.apply("Indeed!"));
        .withDisplayLore("lore.roguelike.farland_travellers");
  }

  public static RldItemStack lascerator() {
    return ToolType.SHEARS.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(3))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.atLevel(2))
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT)
        .asStack()
        .withDisplayName("Lascerator")
        //.withDisplayLore(TextFormat.DARKGREEN.apply("The wool collector"));
        .withDisplayLore("lore.roguelike.lascerator");
  }

  public static RldItemStack cleophianDiggingFeesh() {
    return Food.Type.RAW_COD.asItem()
        .withEnchantment(Enchantment.Effect.EFFICIENCY.atLevel(10))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.atLevel(5))
        .withEnchantment(Enchantment.Effect.FORTUNE.atLevel(5))
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(10))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Cleophian Digging Feesh"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("Feesh are not efeeshent for digging"));
        .withDisplayLore("lore.roguelike.digging_feesh");
  }

  public static RldItemStack bDoubleOspinkSweater() {
    return ArmourType.CHESTPLATE.asItem()
        .withQuality(Quality.WOOD)
        .withColor(Color.CHINESE_RED)
        .asStack()
        .withDisplayName("Pink Sweater")
        //.withDisplayLore("\"It's chinese red!\"");
        .withDisplayLore("lore.roguelike.pink_sweater");
  }

  public static RldItemStack bDoubleOsDigJob() {
    return ToolType.SHOVEL.asItem().diamond()
        .withEnchantment(Enchantment.Effect.EFFICIENCY.atLevel(5))
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(3))
        .asStack()
        .withDisplayName("Dig Job")
        //.withDisplayLore(TextFormat.DARKGREEN.apply("Recovered from hell's blazes"));
        .withDisplayLore("lore.roguelike.dig_job");
  }

  public static RldItemStack boulderfistianGoldenRecord() {
    return Record.newRecord().withSong(Record.Song.THIRTEEN)
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(3))
        .withEnchantment(Enchantment.Effect.KNOCKBACK)
        .withEnchantment(Enchantment.Effect.BLAST_PROTECTION.atLevel(3))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Boulderfistian Golden Record"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"You're Watching Guude Boulderfist...\""));
        .withDisplayLore("lore.roguelike.golden_record");
  }

  public static RldItemStack rleahianBattleSub() {
    return Food.Type.BREAD.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(2))
        .withEnchantment(Enchantment.Effect.KNOCKBACK)
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.atLevel(2))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Rleahian battle sub"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("With extra pastrami"));
        .withDisplayLore("lore.roguelike.battle_sub");
  }

  public static RldItemStack ethosYourMomJoke() {
    return ToolType.PICKAXE.asItem().wooden()
        .withEnchantment(Enchantment.Effect.EFFICIENCY.atLevel(5))
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(3))
        .asStack()
        .withDisplayName("Your Mum")
        //.withDisplayLore(TextFormat.DARKGREEN.apply("The original"));
        .withDisplayLore("lore.roguelike.your_mum");
  }

  public static RldItemStack enikosStringTheory() {
    return WeaponType.BOW.asItem()
        .withEnchantment(Enchantment.Effect.POWER.atLevel(5))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.atLevel(2))
        .withEnchantment(Enchantment.Effect.INFINITY)
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(3))
        .asStack()
        .withDisplayName("Eniko's String Theory")
        //.withDisplayLore(TextFormat.DARKGREEN.apply("For Science!"));
        .withDisplayLore("lore.roguelike.string_theory");
  }

  public static RldItemStack enikosEarring() {
    return WeaponType.SWORD.asItem().diamond()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(5))
        .withEnchantment(Enchantment.Effect.LOOTING.atLevel(3))
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(3))
        .asStack()
        .withDisplayName("Eniko's Earring")
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"She do the loot take boogie\""));
        .withDisplayLore("lore.roguelike.enikos_earring");
  }

  public static RldItemStack bajsLastResort() {
    return ToolType.HOE.asItem().golden()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(2))
        .withEnchantment(Enchantment.Effect.KNOCKBACK)
        .withEnchantment(Enchantment.Effect.FORTUNE.atLevel(5))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Baj's Last Resort"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"Starvation could be fatal\""));
        .withDisplayLore("lore.roguelike.last_resort");
  }

  public static RldItemStack docmRodOfCommand() {
    return ToolType.FISHING_ROD.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(3))
        .withEnchantment(Enchantment.Effect.KNOCKBACK)
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Rod of Command"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"Get to the dang land!\""));
        .withDisplayLore("lore.roguelike.rod_command");
  }

  public static RldItemStack gingerSpiceChicken() {
    return Food.Type.COOKED_CHICKEN.asItem()
        .withEnchantment(Enchantment.Effect.KNOCKBACK)
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(3))
        .withEnchantment(Enchantment.Effect.SHARPNESS)
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Spice Chicken"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"Kung Pao!\""));
        .withDisplayLore("lore.roguelike.spice_chicken");
  }

  public static RldItemStack vechsLegendaryStick() {
    return Material.Type.STICK.asItem()
        .withEnchantment(Enchantment.Effect.UNBREAKING)
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Legendary Stick"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"Really?!\""));
        .withDisplayLore("lore.roguelike.legendary_stick");
  }

  public static RldItemStack notchsApple() {
    return Food.Type.APPLE.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(10))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.atLevel(10))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Notch's apple"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("Imbued with the creator's power"));
        .withDisplayLore("lore.roguelike.notch_apple");
  }

  public static RldItemStack quantumleapsSwissCheese() {
    return BlockType.SPONGE.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(4))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("QuantumLeap's Swiss Cheese"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"Oh boy\""));
        .withDisplayLore("lore.roguelike.swiss_cheese");
  }

  public static RldItemStack generikBsHotPotato() {
    return Food.Type.BAKED_POTATO.asItem()
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.atLevel(3))
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(2))
        .withEnchantment(Enchantment.Effect.KNOCKBACK)
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Hot Potato"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("All a hermit needs"));
        .withDisplayLore("lore.roguelike.hot_potato");
  }

  public static RldItemStack fourlesDarkroastBeans() {
    return new Dye(DyeColor.BROWN)
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.atLevel(2))
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(2))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Fourles Darkroast Beans"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"Mmmm... Dark Roast\""));
        .withDisplayLore("lore.roguelike.darkroast_beans");
  }

  public static RldItemStack oldDinnerbone() {
    return Material.Type.BONE.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(3))
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.atLevel(2))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Old Dinnerbone"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"Dang Skellies!\""));
        .withDisplayLore("lore.roguelike.old_dinnerbone");
  }

  public static RldItemStack grimChewToy() {
    return Food.Type.ROTTEN_FLESH.asItem()
        .withEnchantment(Enchantment.Effect.SMITE.atLevel(2))
        .withEnchantment(Enchantment.Effect.LOOTING)
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Grim chew-toy"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"Come on Grim, let's do this!\""));
        .withDisplayLore("lore.roguelike.chew_toy");
  }

  public static RldItemStack mmillssSpiderBane() {
    return BlockType.CACTUS.asItem()
        .withEnchantment(Enchantment.Effect.BANE_OF_ARTHROPODS.atLevel(4))
        .withEnchantment(Enchantment.Effect.THORNS.atLevel(2))
        .withEnchantment(Enchantment.Effect.LOOTING)
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("MMillssian spider bane"))
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"I really don't need anymore string...\""));
        .withDisplayLore("lore.roguelike.spider_bane");
  }

  public static RldItemStack valandrahsKiss() {
    return WeaponType.SWORD.asItem().iron()
        .withEnchantment(Enchantment.Effect.SHARPNESS.atLevel(4))
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT)
        .withEnchantment(Enchantment.Effect.KNOCKBACK)
        .withEnchantment(Enchantment.Effect.UNBREAKING.atLevel(2))
        .asStack()
        .withDisplayName("Valandrah's Kiss")
        //.withDisplayLore(TextFormat.DARKGREEN.apply("\"Feel the kiss of my blade\""));
        .withDisplayLore("lore.roguelike.valandrahs_kiss");
  }

}
