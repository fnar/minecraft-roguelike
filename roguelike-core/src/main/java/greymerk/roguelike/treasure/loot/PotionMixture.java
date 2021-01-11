package greymerk.roguelike.treasure.loot;

import com.github.srwaggon.minecraft.item.potion.EffectType;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.util.DyeColor;

public enum PotionMixture {

  TEQUILA,
  MOONSHINE,
  ABSINTHE,
  VILE,
  LAUDANUM,
  RAGE,
  STOUT,
  STAMINA,
  NECTAR,
  COFFEE,
  AURA;

  public static final PotionMixture[] POTIONS = {LAUDANUM, RAGE, STAMINA, NECTAR, COFFEE, AURA};
  public static final PotionMixture[] BOOZE = {TEQUILA, LAUDANUM, MOONSHINE, ABSINTHE, STOUT};
  public static int TICKS_PER_SECOND = 20;

  public static PotionMixture chooseRandom(Random random) {
    return chooseRandomAmong(random, values());
  }

  public static PotionMixture chooseRandomAmong(Random random, PotionMixture[] potions) {
    return potions[random.nextInt(potions.length)];
  }

  public static ItemStack getPotion(Random random, PotionMixture type) {
    ItemStack potion;
    switch (type) {
      case TEQUILA:
        potion = PotionType.getSpecific(PotionForm.REGULAR, null, false, false);
        addCustomEffect(potion, EffectType.STRENGTH, 3, 30 + random.nextInt(60));
        addCustomEffect(potion, EffectType.FATIGUE, 1, 30 + random.nextInt(60));
        Loot.setItemName(potion, "Tequila");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        setColor(potion, DyeColor.RGBToColor(255, 232, 196));
        return potion;
      case LAUDANUM:
        potion = PotionType.getSpecific(PotionForm.REGULAR, null, false, false);
        addCustomEffect(potion, EffectType.REGEN, 3, 8);
        addCustomEffect(potion, EffectType.WEAKNESS, 2, 5);
        addCustomEffect(potion, EffectType.SLOWNESS, 2, 5);
        addCustomEffect(potion, EffectType.FATIGUE, 2, 5);
        addCustomEffect(potion, EffectType.NAUSEA, 1, 5);
        Loot.setItemName(potion, "Laudanum");
        Loot.setItemLore(potion, "A medicinal tincture.");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        setColor(potion, DyeColor.RGBToColor(150, 50, 0));
        return potion;
      case MOONSHINE:
        potion = PotionType.getSpecific(PotionForm.REGULAR, null, false, false);
        addCustomEffect(potion, EffectType.DAMAGE, 1, 1);
        addCustomEffect(potion, EffectType.BLINDNESS, 1, 30 + random.nextInt(60));
        addCustomEffect(potion, EffectType.RESISTANCE, 2, 30 + random.nextInt(30));
        Loot.setItemName(potion, "Moonshine");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        setColor(potion, DyeColor.RGBToColor(250, 240, 230));
        return potion;
      case ABSINTHE:
        potion = PotionType.getSpecific(PotionForm.REGULAR, null, false, false);
        addCustomEffect(potion, EffectType.POISON, 1, 3);
        addCustomEffect(potion, EffectType.NIGHT_VISION, 1, 120);
        addCustomEffect(potion, EffectType.JUMP, 3, 120);
        Loot.setItemName(potion, "Absinthe");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        setColor(potion, DyeColor.RGBToColor(200, 250, 150));
        return potion;
      case VILE:
        potion = PotionType.getSpecific(
            random,
            PotionForm.chooseRandom(random),
            PotionType.chooseRandom(random)
        );
        addRandomEffects(random, potion, 2 + random.nextInt(2));
        Loot.setItemName(potion, "Vile Mixture");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        return potion;
      case RAGE:
        potion = PotionType.getSpecific(PotionForm.REGULAR, null, false, false);
        addCustomEffect(potion, EffectType.STRENGTH, 3, 20);
        addCustomEffect(potion, EffectType.BLINDNESS, 1, 10);
        addCustomEffect(potion, EffectType.WITHER, 1, 3);
        Loot.setItemName(potion, "Animus");
        Loot.setItemLore(potion, "An unstable mixture.");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        setColor(potion, DyeColor.RGBToColor(255, 0, 0));
        return potion;
      case STAMINA:
        potion = PotionType.getSpecific(PotionForm.REGULAR, null, false, false);
        addCustomEffect(potion, EffectType.SATURATION, 10, 1);
        addCustomEffect(potion, EffectType.SPEED, 2, 120);
        addCustomEffect(potion, EffectType.HASTE, 2, 120);
        addCustomEffect(potion, EffectType.JUMP, 3, 120);
        Loot.setItemName(potion, "Vitae");
        Loot.setItemLore(potion, "Essence of life.");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        setColor(potion, DyeColor.RGBToColor(230, 50, 20));
        return potion;
      case STOUT:
        potion = PotionType.getSpecific(PotionForm.REGULAR, null, false, false);
        addCustomEffect(potion, EffectType.REGEN, 1, 5);
        addCustomEffect(potion, EffectType.SATURATION, 2, 1);
        addCustomEffect(potion, EffectType.HEALTH_BOOST, 2, 120);
        addCustomEffect(potion, EffectType.RESISTANCE, 1, 120);
        Loot.setItemName(potion, "Stout");
        Loot.setItemLore(potion, "\"It's Good for You\"");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        setColor(potion, DyeColor.RGBToColor(50, 40, 20));
        return potion;
      case NECTAR:
        potion = PotionType.getSpecific(PotionForm.REGULAR, null, false, false);
        addCustomEffect(potion, EffectType.ABSORPTION, 10, 20);
        addCustomEffect(potion, EffectType.RESISTANCE, 3, 20);
        addCustomEffect(potion, EffectType.HEALTH, 2, 1);
        Loot.setItemName(potion, "Nectar");
        Loot.setItemLore(potion, "A Floral extract.");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        setColor(potion, DyeColor.RGBToColor(250, 150, 250));
        return potion;
      case COFFEE:
        potion = PotionType.getSpecific(PotionForm.REGULAR, null, false, false);
        addCustomEffect(potion, EffectType.HASTE, 2, 600);
        addCustomEffect(potion, EffectType.SPEED, 1, 600);
        Loot.setItemName(potion, "Coffee");
        Loot.setItemLore(potion, "A darkroast bean brew.");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        setColor(potion, DyeColor.RGBToColor(20, 20, 10));
        return potion;
      case AURA:
        potion = PotionType.getSpecific(PotionForm.REGULAR, null, false, false);
        addCustomEffect(potion, EffectType.GLOWING, 1, 600);
        Loot.setItemName(potion, "Luma");
        Loot.setItemLore(potion, "A glowstone extract.");
        ItemHideFlags.set(ItemHideFlags.EFFECTS, potion);
        setColor(potion, DyeColor.RGBToColor(250, 250, 0));
        return potion;
      default:
    }

    return new ItemStack(Items.GLASS_BOTTLE);
  }

  public static ItemStack getBooze(Random rand) {
    return getPotion(rand, chooseRandomAmong(rand, BOOZE));
  }

  public static ItemStack getRandom(Random rand) {
    return getPotion(rand, chooseRandomAmong(rand, POTIONS));
  }

  public static void addRandomEffects(Random rand, ItemStack potion, int numEffects) {

    List<EffectType> effects = new ArrayList<EffectType>(Arrays.asList(EffectType.values()));
    Collections.shuffle(effects, rand);

    for (int i = 0; i < numEffects; ++i) {

      EffectType type = effects.get(i);
      int duration;
      switch (type) {
        case SATURATION:
        case HEALTH:
        case DAMAGE:
          duration = 1;
          break;
        case REGEN:
          duration = 10 + rand.nextInt(20);
          break;
        case HUNGER:
          duration = 5 + rand.nextInt(10);
          break;
        case WITHER:
        case POISON:
          duration = 5 + rand.nextInt(5);
          break;
        default:
          duration = 60 + rand.nextInt(120);
      }

      addCustomEffect(potion, type, rand.nextInt(3), duration);
    }
  }

  public static void setColor(ItemStack potion, int color) {
    potion.getTagCompound().setInteger("CustomPotionColor", color);
  }

  private static void addCustomEffect(ItemStack potion, EffectType type, int amplifier, int duration) {

    final String CUSTOM = "CustomPotionEffects";

    NBTTagCompound tag = potion.getTagCompound();
    if (tag == null) {
      tag = new NBTTagCompound();
      potion.setTagCompound(tag);
    }


    NBTTagList effects;
    effects = tag.getTagList(CUSTOM, 10);
    if (effects == null) {
      effects = new NBTTagList();
      tag.setTag(CUSTOM, effects);
    }

    NBTTagCompound toAdd = new NBTTagCompound();

    toAdd.setByte("Id", (byte) type.getEffectID());

    // TODO: The passed in values seem to be 0-2. Subtracting 1 is suspicious.
    toAdd.setByte("Amplifier", (byte) (amplifier - 1));
    toAdd.setInteger("Duration", duration * TICKS_PER_SECOND);
    toAdd.setBoolean("Ambient", true);

    effects.appendTag(toAdd);
    tag.setTag(CUSTOM, effects);
    potion.setTagCompound(tag);
  }
}
