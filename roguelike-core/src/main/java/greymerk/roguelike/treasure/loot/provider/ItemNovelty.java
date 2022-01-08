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
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(3))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(1))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(2))
        .asStack()
        .withDisplayName("Greymerk's Hatchet")
        .withDisplayLore(TextFormat.DARKGREEN.apply("Pointlessly sharp"));
  }

  public static RldItemStack nebrisCrown() {
    return ArmourType.HELMET.asItem().golden()
        .withEnchantment(Enchantment.Effect.PROTECTION.asEnchantment().withLevel(4))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(3))
        .asStack()
        .withDisplayName("Nebrian Crown of Justice").withDisplayLore(TextFormat.DARKGREEN.apply("Adorned with precious gemstones"));
  }

  public static RldItemStack nullPointer() {
    return WeaponType.SWORD.asItem().diamond()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(5))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(3))
        .asStack()
        .withDisplayName("Null Pointer")
        .withDisplayLore(TextFormat.DARKGREEN.apply("Exceptional"));
  }

  public static RldItemStack manPants() {
    return ArmourType.LEGGINGS.asItem().leather()
        .withColor(Color.SMOKED_SALMON)
        .withEnchantment(Enchantment.Effect.FIRE_PROTECTION.asEnchantment().withLevel(4))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(3))
        .asStack()
        .withDisplayName("Man Pants")
        .withDisplayLore(TextFormat.DARKGREEN.apply("Yessss, Manpants!"));
  }

  public static RldItemStack zisteauSign() {
    return BlockType.SIGN.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(5))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(3))
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.asEnchantment().withLevel(1))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Battle Sign"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"That's what you get!\""));
  }

  public static RldItemStack avidyasWhiteRussian() {
    return Food.Type.MILK_BUCKET.asItem()
        .withEnchantment(Enchantment.Effect.BANE_OF_ARTHROPODS.asEnchantment().withLevel(4))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(1))
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.asEnchantment().withLevel(1))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("White Russian"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("The dude's favourite"));
  }

  public static RldItemStack ashleasOatmealCookie() {
    return Food.Type.COOKIE.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(1))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Ashlea's Oatmeal Cookie"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("Perfect for elevensies"));
  }

  public static RldItemStack farlandTravellers() {
    return ArmourType.BOOTS.asItem()
        .leather()
        .withColor(Color.HARISSA_RED)
        .withEnchantment(Enchantment.Effect.PROTECTION.asEnchantment().withLevel(3))
        .withEnchantment(Enchantment.Effect.FEATHER_FALLING.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(3))
        .asStack()
        .withDisplayName("Farland Travellers")
        .withDisplayLore(TextFormat.DARKGREEN.apply("Indeed!"));
  }

  public static RldItemStack lascerator() {
    return ToolType.SHEARS.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(3))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.asEnchantment().withLevel(1))
        .asStack()
        .withDisplayName("Lascerator")
        .withDisplayLore(TextFormat.DARKGREEN.apply("The wool collector"));
  }

  public static RldItemStack cleophianDiggingFeesh() {
    return Food.Type.RAW_COD.asItem()
        .withEnchantment(Enchantment.Effect.EFFICIENCY.asEnchantment().withLevel(10))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(5))
        .withEnchantment(Enchantment.Effect.FORTUNE.asEnchantment().withLevel(5))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(10))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Cleophian Digging Feesh"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("Feesh are not efeeshent for digging"));
  }

  public static RldItemStack bDoubleOspinkSweater() {
    return ArmourType.CHESTPLATE.asItem()
        .withQuality(Quality.WOOD)
        .withColor(Color.CHINESE_RED)
        .asStack()
        .withDisplayName("Pink Sweater")
        .withDisplayLore("\"It's chinese red!\"");
  }

  public static RldItemStack bDoubleOsDigJob() {
    return ToolType.SHOVEL.asItem().diamond()
        .withEnchantment(Enchantment.Effect.EFFICIENCY.asEnchantment().withLevel(5))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(3))
        .asStack()
        .withDisplayName("Dig Job")
        .withDisplayLore(TextFormat.DARKGREEN.apply("Recovered from hell's blazes"));
  }

  public static RldItemStack boulderfistianGoldenRecord() {
    return Record.newRecord().withSong(Record.Song.THIRTEEN)
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(3))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(1))
        .withEnchantment(Enchantment.Effect.BLAST_PROTECTION.asEnchantment().withLevel(3))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Boulderfistian Golden Record"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"You're Watching Guude Boulderfist...\""));
  }

  public static RldItemStack rleahianBattleSub() {
    return Food.Type.BREAD.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(1))
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.asEnchantment().withLevel(2))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Rleahian battle sub"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("With extra pastrami"));
  }

  public static RldItemStack ethosYourMomJoke() {
    return ToolType.PICKAXE.asItem().wooden()
        .withEnchantment(Enchantment.Effect.EFFICIENCY.asEnchantment().withLevel(5))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(3))
        .asStack()
        .withDisplayName("Your Mum")
        .withDisplayLore(TextFormat.DARKGREEN.apply("The original"));
  }

  public static RldItemStack enikosStringTheory() {
    return WeaponType.BOW.asItem()
        .withEnchantment(Enchantment.Effect.POWER.asEnchantment().withLevel(5))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.INFINITY.asEnchantment().withLevel(1))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(3))
        .asStack()
        .withDisplayName("Eniko's String Theory")
        .withDisplayLore(TextFormat.DARKGREEN.apply("For Science!"));
  }

  public static RldItemStack enikosEarring() {
    return WeaponType.SWORD.asItem().diamond()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(5))
        .withEnchantment(Enchantment.Effect.LOOTING.asEnchantment().withLevel(3))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(3))
        .asStack()
        .withDisplayName("Eniko's Earring")
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"She do the loot take boogie\""));
  }

  public static RldItemStack bajsLastResort() {
    return ToolType.HOE.asItem().golden()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(1))
        .withEnchantment(Enchantment.Effect.FORTUNE.asEnchantment().withLevel(5))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Baj's Last Resort"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"Starvation could be fatal\""));
  }

  public static RldItemStack docmRodOfCommand() {
    return ToolType.FISHING_ROD.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(3))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(1))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Rod of Command"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"Get to the dang land!\""));
  }

  public static RldItemStack gingerSpiceChicken() {
    return Food.Type.COOKED_CHICKEN.asItem()
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(1))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(3))
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(1))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Spice Chicken"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"Kung Pao!\""));
  }

  public static RldItemStack vechsLegendaryStick() {
    return Material.Type.STICK.asItem()
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(1))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Legendary Stick"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"Really?!\""));
  }

  public static RldItemStack notchsApple() {
    return Food.Type.APPLE.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(10))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(10))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Notch's apple"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("Imbued with the creator's power"));
  }

  public static RldItemStack quantumleapsSwissCheese() {
    return BlockType.SPONGE.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(4))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("QuantumLeap's Swiss Cheese"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"Oh boy\""));
  }

  public static RldItemStack generikBsHotPotato() {
    return Food.Type.BAKED_POTATO.asItem()
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.asEnchantment().withLevel(3))
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(1))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Hot Potato"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("All a hermit needs"));
  }

  public static RldItemStack fourlesDarkroastBeans() {
    return new Dye(DyeColor.BROWN)
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(2))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Fourles Darkroast Beans"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"Mmmm... Dark Roast\""));
  }

  public static RldItemStack oldDinnerbone() {
    return Material.Type.BONE.asItem()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(3))
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.asEnchantment().withLevel(2))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Old Dinnerbone"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"Dang Skellies!\""));
  }

  public static RldItemStack grimChewToy() {
    return Food.Type.ROTTEN_FLESH.asItem()
        .withEnchantment(Enchantment.Effect.SMITE.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.LOOTING.asEnchantment().withLevel(1))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("Grim chew-toy"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"Come on Grim, let's do this!\""));
  }

  public static RldItemStack mmillssSpiderBane() {
    return BlockType.CACTUS.asItem()
        .withEnchantment(Enchantment.Effect.BANE_OF_ARTHROPODS.asEnchantment().withLevel(4))
        .withEnchantment(Enchantment.Effect.THORNS.asEnchantment().withLevel(2))
        .withEnchantment(Enchantment.Effect.LOOTING.asEnchantment().withLevel(1))
        .asStack()
        .withDisplayName(TextFormat.DARKPURPLE.apply("MMillssian spider bane"))
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"I really don't need anymore string...\""));
  }

  public static RldItemStack valandrahsKiss() {
    return WeaponType.SWORD.asItem().iron()
        .withEnchantment(Enchantment.Effect.SHARPNESS.asEnchantment().withLevel(4))
        .withEnchantment(Enchantment.Effect.FIRE_ASPECT.asEnchantment().withLevel(1))
        .withEnchantment(Enchantment.Effect.KNOCKBACK.asEnchantment().withLevel(1))
        .withEnchantment(Enchantment.Effect.UNBREAKING.asEnchantment().withLevel(2))
        .asStack()
        .withDisplayName("Valandrah's Kiss")
        .withDisplayLore(TextFormat.DARKGREEN.apply("\"Feel the kiss of my blade\""));
  }

}
