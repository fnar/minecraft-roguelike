package greymerk.roguelike.treasure.loot;

import com.github.srwaggon.minecraft.item.ItemMapper1_12;
import com.github.srwaggon.minecraft.item.RldItemStack;
import com.github.srwaggon.minecraft.Effect;
import com.github.srwaggon.minecraft.EffectType;
import com.github.srwaggon.minecraft.item.Potion;

import net.minecraft.item.ItemStack;

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

  public static ItemStack chooseRandomBooze(Random rand) {
    return getPotion(rand, chooseRandomAmong(rand, BOOZE));
  }

  public static ItemStack chooseRandomPotion(Random rand) {
    return getPotion(rand, chooseRandomAmong(rand, POTIONS));
  }

  public static PotionMixture chooseRandom(Random random) {
    return chooseRandomAmong(random, values());
  }

  public static PotionMixture chooseRandomAmong(Random random, PotionMixture[] potions) {
    return potions[random.nextInt(potions.length)];
  }

  public static ItemStack getPotion(Random random, PotionMixture type) {
    return ItemMapper1_12.map(getPotionAsRldItemStack(random, type));
  }

  public static RldItemStack getPotionAsRldItemStack(Random random, PotionMixture type) {
    switch (type) {
      case TEQUILA:
        return getTequila(random);
      case LAUDANUM:
        return getLaudanum();
      case MOONSHINE:
        return getMoonshine(random);
      case ABSINTHE:
        return getAbsinthe();
      default:
      case VILE:
        return getVile(random);
      case RAGE:
        return getRage();
      case STAMINA:
        return getStamina();
      case STOUT:
        return getStout();
      case NECTAR:
        return getNectar();
      case COFFEE:
        return getCoffee();
      case AURA:
        return getAura();
    }
  }

   public static RldItemStack getTequila(Random random) {
    return Potion.newPotion()
        .withEffect(Effect.newEffect(EffectType.STRENGTH, 2, 30 + random.nextInt(60)))
        .withEffect(Effect.newEffect(EffectType.FATIGUE, 0, 30 + random.nextInt(60)))
        .asItemStack()
        .withDisplayName("Tequila")
        .withHideFlag(ItemHideFlags.EFFECTS)
        .withTag("CustomPotionColor", DyeColor.RGBToColor(255, 232, 196));
  }

  public static RldItemStack getLaudanum() {
    return Potion.newPotion()
        .withEffect(Effect.newEffect(EffectType.REGEN, 2, 8))
        .withEffect(Effect.newEffect(EffectType.WEAKNESS, 1, 5))
        .withEffect(Effect.newEffect(EffectType.SLOWNESS, 1, 5))
        .withEffect(Effect.newEffect(EffectType.FATIGUE, 1, 5))
        .withEffect(Effect.newEffect(EffectType.NAUSEA, 0, 5))
        .asItemStack()
        .withDisplayName("Laudanum")
        .withDisplayLore("A medicinal tincture.")
        .withHideFlag(ItemHideFlags.EFFECTS)
        .withTag("CustomPotionColor", DyeColor.RGBToColor(150, 50, 0));
  }

  public static RldItemStack getMoonshine(Random random) {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.DAMAGE, 0, 1))
            .withEffect(Effect.newEffect(EffectType.BLINDNESS, 0, 30 + random.nextInt(60)))
            .withEffect(Effect.newEffect(EffectType.RESISTANCE, 1, 30 + random.nextInt(30)))
            .asItemStack()
            .withDisplayName("Moonshine")
            .withHideFlag(ItemHideFlags.EFFECTS)
            .withTag("CustomPotionColor", DyeColor.RGBToColor(250, 240, 230));
  }

  public static RldItemStack getAbsinthe() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.POISON, 0, 3))
            .withEffect(Effect.newEffect(EffectType.NIGHT_VISION, 0, 120))
            .withEffect(Effect.newEffect(EffectType.JUMP, 2, 120))
            .asItemStack()
            .withDisplayName("Absinthe")
            .withHideFlag(ItemHideFlags.EFFECTS)
            .withTag("CustomPotionColor", DyeColor.RGBToColor(200, 250, 150));
  }

  public static RldItemStack getVile(Random random) {
    EffectType randomEffect0 = EffectType.chooseRandom(random);
    EffectType randomEffect1 = EffectType.chooseRandom(random);
    return
        Potion.newPotion()
            .withForm(PotionForm.chooseRandom(random))
            .withType(PotionType.chooseRandom(random))
            .withAmplification(random.nextBoolean())
            .withExtension(random.nextBoolean())
            .withEffect(Effect.newEffect()
                .withType(randomEffect0)
                .withAmplification(Potion.Amplification.chooseRandom(random))
                .withDuration(getSuggestedDuration(random, randomEffect0)))
            .withEffect(Effect.newEffect()
                .withType(randomEffect1)
                .withAmplification(Potion.Amplification.chooseRandom(random))
                .withDuration(getSuggestedDuration(random, randomEffect1)))
            .asItemStack()
            .withDisplayName("Vile Mixture")
            .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getRage() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.STRENGTH, 2, 20))
            .withEffect(Effect.newEffect(EffectType.BLINDNESS, 0, 10))
            .withEffect(Effect.newEffect(EffectType.WITHER, 0, 3))
            .asItemStack()
            .withDisplayName("Animus")
            .withDisplayLore("An unstable mixture.")
            .withHideFlag(ItemHideFlags.EFFECTS)
            .withTag("CustomPotionColor", DyeColor.RGBToColor(255, 0, 0));
  }

  public static RldItemStack getStamina() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.SATURATION, 9, 1))
            .withEffect(Effect.newEffect(EffectType.SPEED, 1, 120))
            .withEffect(Effect.newEffect(EffectType.HASTE, 1, 120))
            .withEffect(Effect.newEffect(EffectType.JUMP, 2, 120))
            .asItemStack()
            .withDisplayName("Vitae")
            .withDisplayLore("Essence of life.")
            .withHideFlag(ItemHideFlags.EFFECTS)
            .withTag("CustomPotionColor", DyeColor.RGBToColor(230, 50, 20));
  }

  public static RldItemStack getStout() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.REGEN, 0, 5))
            .withEffect(Effect.newEffect(EffectType.SATURATION, 1, 1))
            .withEffect(Effect.newEffect(EffectType.HEALTH_BOOST, 1, 120))
            .withEffect(Effect.newEffect(EffectType.RESISTANCE, 0, 120))
            .asItemStack()
            .withDisplayName("Stout")
            .withDisplayLore("\"It's Good for You\"")
            .withHideFlag(ItemHideFlags.EFFECTS)
            .withTag("CustomPotionColor", DyeColor.RGBToColor(50, 40, 20));
  }

  public static RldItemStack getNectar() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.ABSORPTION, 9, 20))
            .withEffect(Effect.newEffect(EffectType.RESISTANCE, 2, 20))
            .withEffect(Effect.newEffect(EffectType.HEALTH, 1, 1))
            .asItemStack()
            .withDisplayName("Nectar")
            .withDisplayLore("A Floral extract.")
            .withHideFlag(ItemHideFlags.EFFECTS)
            .withTag("CustomPotionColor", DyeColor.RGBToColor(250, 150, 250));
  }

  public static RldItemStack getCoffee() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.HASTE, 1, 600))
            .withEffect(Effect.newEffect(EffectType.SPEED, 0, 600))
            .asItemStack()
            .withDisplayName("Coffee")
            .withDisplayLore("A darkroast bean brew.")
            .withHideFlag(ItemHideFlags.EFFECTS)
            .withTag("CustomPotionColor", DyeColor.RGBToColor(20, 20, 10));
  }

  public static RldItemStack getAura() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.GLOWING, 0, 600))
            .asItemStack()
            .withDisplayName("Luma")
            .withDisplayLore("A glowstone extract.")
            .withHideFlag(ItemHideFlags.EFFECTS)
            .withTag("CustomPotionColor", DyeColor.RGBToColor(250, 250, 0));
  }

  public static int getSuggestedDuration(Random random, EffectType type) {
    int duration;
    switch (type) {
      case SATURATION:
      case HEALTH:
      case DAMAGE:
        duration = 1;
        break;
      case REGEN:
        duration = 10 + random.nextInt(20);
        break;
      case HUNGER:
        duration = 5 + random.nextInt(10);
        break;
      case WITHER:
      case POISON:
        duration = 5 + random.nextInt(5);
        break;
      default:
        duration = 60 + random.nextInt(120);
    }
    return duration;
  }

}
