package greymerk.roguelike.treasure.loot;

import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;

import java.util.Random;

import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;

public enum PotionType {

  AWKWARD,
  HEALING,
  HARM,
  REGEN,
  POISON,
  STRENGTH,
  WEAKNESS,
  SLOWNESS,
  SWIFTNESS,
  FIRERESIST;

  public static PotionType chooseRandom(Random rand) {
    return chooseRandomAmong(rand, values());
  }

  public static PotionType chooseRandomAmong(Random rand, PotionType[] potionTypes) {
    return potionTypes[rand.nextInt(potionTypes.length)];
  }

  public static IWeighted<ItemStack> get(JsonObject data, int weight) throws Exception {
    if (!data.has("name")) {
      throw new Exception("Potion missing name field");
    }
    String nameString = data.get("name").getAsString();
    net.minecraft.potion.PotionType type = net.minecraft.potion.PotionType.getPotionTypeForName(nameString);
    ItemStack item = !data.has("form") ? new ItemStack(Items.POTIONITEM)
        : data.get("form").getAsString().toLowerCase().equals("splash") ? new ItemStack(Items.SPLASH_POTION)
            : data.get("form").getAsString().toLowerCase().equals("lingering") ? new ItemStack(Items.LINGERING_POTION)
                : new ItemStack(Items.POTIONITEM);
    return new WeightedChoice<>(PotionUtils.addPotionToItemStack(item, type), weight);
  }

  public static ItemStack getSpecific(PotionForm potionForm, PotionType effect, boolean isAmplified, boolean isExtended) {

    ItemStack potion;

    switch (potionForm) {
      case REGULAR:
        potion = new ItemStack(Items.POTIONITEM);
        break;
      case SPLASH:
        potion = new ItemStack(Items.SPLASH_POTION);
        break;
      case LINGERING:
        potion = new ItemStack(Items.LINGERING_POTION);
        break;
      default:
        potion = new ItemStack(Items.POTIONITEM);
        break;
    }

    net.minecraft.potion.PotionType data = getEffect(effect, isAmplified, isExtended);
    return PotionUtils.addPotionToItemStack(potion, data);
  }

  public static net.minecraft.potion.PotionType getEffect(PotionType effect, boolean upgrade, boolean extend) {

    if (effect == null) {
      return PotionTypes.AWKWARD;
    }

    switch (effect) {
      case HEALING:
        return upgrade ? PotionTypes.STRONG_HEALING : PotionTypes.HEALING;
      case HARM:
        return upgrade ? PotionTypes.STRONG_HARMING : PotionTypes.HARMING;
      case REGEN:
        if (extend) {
          return PotionTypes.LONG_REGENERATION;
        } else {
          return upgrade ? PotionTypes.STRONG_REGENERATION : PotionTypes.REGENERATION;
        }
      case POISON:
        if (extend) {
          return PotionTypes.LONG_POISON;
        } else {
          return upgrade ? PotionTypes.STRONG_POISON : PotionTypes.POISON;
        }
      case STRENGTH:
        if (extend) {
          return PotionTypes.LONG_STRENGTH;
        } else {
          return upgrade ? PotionTypes.STRONG_STRENGTH : PotionTypes.STRENGTH;
        }
      case WEAKNESS:
        if (extend) {
          return PotionTypes.LONG_WEAKNESS;
        } else {
          return PotionTypes.WEAKNESS;
        }
      case SLOWNESS:
        if (extend) {
          return PotionTypes.LONG_SLOWNESS;
        } else {
          return PotionTypes.SLOWNESS;
        }
      case SWIFTNESS:
        if (extend) {
          return PotionTypes.LONG_SWIFTNESS;
        } else {
          return upgrade ? PotionTypes.STRONG_SWIFTNESS : PotionTypes.SWIFTNESS;
        }
      case FIRERESIST:
        if (extend) {
          return PotionTypes.LONG_FIRE_RESISTANCE;
        } else {
          return PotionTypes.FIRE_RESISTANCE;
        }
      default:
        return PotionTypes.AWKWARD;
    }
  }
}
