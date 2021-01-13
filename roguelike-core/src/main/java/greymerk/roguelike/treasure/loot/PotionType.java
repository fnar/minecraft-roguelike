package greymerk.roguelike.treasure.loot;

import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;

import java.util.Random;

import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;

public enum PotionType {

  AWKWARD,
  FIRE_RESISTANCE,
  HARMING,
  HEALING,
  INVISIBILITY,
  LEAPING,
  LEVITATION,
  LUCK,
  NIGHT_VISION,
  POISON,
  REGENERATION,
  SLOWNESS,
  SLOW_FALLING,
  STRENGTH,
  SWIFTNESS,
  TURTLE_MASTER,
  WATER_BREATHING,
  WEAKNESS,
  ;

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

}
