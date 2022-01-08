package greymerk.roguelike.treasure.loot;

import com.github.fnar.minecraft.Effect;
import com.github.fnar.minecraft.EffectType;
import com.github.fnar.minecraft.item.Potion;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.util.Color;

import java.util.Random;

public enum PotionMixture {

  ABSINTHE,
  AURA,
  COFFEE,
  LAUDANUM,
  MOONSHINE,
  NECTAR,
  RAGE,
  STAMINA,
  STOUT,
  TEQUILA,
  VILE;

  public static final PotionMixture[] POTIONS = {LAUDANUM, RAGE, STAMINA, NECTAR, COFFEE, AURA};
  public static final PotionMixture[] BOOZE = {TEQUILA, LAUDANUM, MOONSHINE, ABSINTHE, STOUT};

  public static RldItemStack chooseRandomBooze(Random random) {
    return getPotion(random, chooseRandomAmong(random, BOOZE));
  }

  public static RldItemStack chooseRandomPotion(Random random) {
    return getPotion(random, chooseRandomAmong(random, POTIONS));
  }

  public static PotionMixture chooseRandom(Random random) {
    return chooseRandomAmong(random, values());
  }

  public static PotionMixture chooseRandomAmong(Random random, PotionMixture[] potions) {
    return potions[random.nextInt(potions.length)];
  }

  public static RldItemStack getPotion(Random random, PotionMixture type) {
    return getPotionAsRldItemStack(random, type);
  }

  public static RldItemStack getPotionAsRldItemStack(Random random, PotionMixture type) {
    switch (type) {
      case ABSINTHE:
        return getAbsinthe();
      case AURA:
        return getAura();
      case COFFEE:
        return getCoffee();
      case LAUDANUM:
        return getLaudanum();
      case MOONSHINE:
        return getMoonshine(random);
      case NECTAR:
        return getNectar();
      case RAGE:
        return getRage();
      case STAMINA:
        return getStamina();
      case STOUT:
        return getStout();
      case TEQUILA:
        return getTequila(random);
      default:
      case VILE:
        return getVile(random);
    }
  }

  public static RldItemStack getTequila(Random random) {
    return Potion.newPotion()
        .withEffect(Effect.newEffect(EffectType.STRENGTH, 2, 30 + random.nextInt(60)))
        .withEffect(Effect.newEffect(EffectType.FATIGUE, 0, 30 + random.nextInt(60)))
        .withColor(Color.POPCORN)
        .asStack()
        .withDisplayName("Tequila")
        .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getLaudanum() {
    return Potion.newPotion()
        .withEffect(Effect.newEffect(EffectType.REGEN, 2, 8))
        .withEffect(Effect.newEffect(EffectType.WEAKNESS, 1, 5))
        .withEffect(Effect.newEffect(EffectType.SLOWNESS, 1, 5))
        .withEffect(Effect.newEffect(EffectType.FATIGUE, 1, 5))
        .withEffect(Effect.newEffect(EffectType.NAUSEA, 0, 5))
        .withColor(Color.SPLIT_PEA_SOUP)
        .asStack()
        .withDisplayName("Laudanum")
        .withDisplayLore("A medicinal tincture.")
        .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getMoonshine(Random random) {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.DAMAGE, 0, 1))
            .withEffect(Effect.newEffect(EffectType.BLINDNESS, 0, 30 + random.nextInt(60)))
            .withEffect(Effect.newEffect(EffectType.RESISTANCE, 1, 30 + random.nextInt(30)))
            .withColor(Color.ICE_FISHING)
            .asStack()
            .withDisplayName("Moonshine")
            .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getAbsinthe() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.POISON, 0, 3))
            .withEffect(Effect.newEffect(EffectType.NIGHT_VISION, 0, 120))
            .withEffect(Effect.newEffect(EffectType.JUMP, 2, 120))
            .withColor(Color.ADVERTISING_GREEN)
            .asStack()
            .withDisplayName("Absinthe")
            .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getVile(Random random) {
    EffectType randomEffect0 = EffectType.chooseRandom(random);
    EffectType randomEffect1 = EffectType.chooseRandom(random);
    return
        Potion.newPotion()
            .withForm(Potion.Form.chooseRandom(random))
            .withEffect(Potion.Effect.chooseRandom(random))
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
            .withColor(Color.random(random))
            .asStack()
            .withDisplayName("Vile Mixture")
            .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getRage() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.STRENGTH, 2, 20))
            .withEffect(Effect.newEffect(EffectType.BLINDNESS, 0, 10))
            .withEffect(Effect.newEffect(EffectType.WITHER, 0, 3))
            .withColor(Color.BLUE)
            .asStack()
            .withDisplayName("Animus")
            .withDisplayLore("An unstable mixture.")
            .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getStamina() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.SATURATION, 9, 1))
            .withEffect(Effect.newEffect(EffectType.SPEED, 1, 120))
            .withEffect(Effect.newEffect(EffectType.HASTE, 1, 120))
            .withEffect(Effect.newEffect(EffectType.JUMP, 2, 120))
            .withColor(Color.WHITE)
            .asStack()
            .withDisplayName("Vitae")
            .withDisplayLore("Essence of life.")
            .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getStout() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.REGEN, 0, 5))
            .withEffect(Effect.newEffect(EffectType.SATURATION, 1, 1))
            .withEffect(Effect.newEffect(EffectType.HEALTH_BOOST, 1, 120))
            .withEffect(Effect.newEffect(EffectType.RESISTANCE, 0, 120))
            .withColor(Color.ENGLISH_BREAKFAST)
            .asStack()
            .withDisplayName("Stout")
            .withDisplayLore("\"It's Good for You\"")
            .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getNectar() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.ABSORPTION, 9, 20))
            .withEffect(Effect.newEffect(EffectType.RESISTANCE, 2, 20))
            .withEffect(Effect.newEffect(EffectType.HEALTH, 1, 1))
            .withColor(Color.SUNNY_MOOD)
            .asStack()
            .withDisplayName("Nectar")
            .withDisplayLore("A Floral extract.")
            .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getCoffee() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.HASTE, 1, 600))
            .withEffect(Effect.newEffect(EffectType.SPEED, 0, 600))
            .withColor(Color.COFFEE)
            .asStack()
            .withDisplayName("Coffee")
            .withDisplayLore("A darkroast bean brew.")
            .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static RldItemStack getAura() {
    return
        Potion.newPotion()
            .withEffect(Effect.newEffect(EffectType.GLOWING, 0, 600))
            .withColor(Color.DELAYED_YELLOW)
            .asStack()
            .withDisplayName("Luma")
            .withDisplayLore("A glowstone extract.")
            .withHideFlag(ItemHideFlags.EFFECTS);
  }

  public static int getSuggestedDuration(Random random, EffectType type) {
    switch (type) {
      case SATURATION:
      case HEALTH:
      case DAMAGE:
        return 1;
      case REGEN:
        return 10 + random.nextInt(20);
      case HUNGER:
        return 5 + random.nextInt(10);
      case WITHER:
      case POISON:
        return 5 + random.nextInt(5);
      default:
        return 60 + random.nextInt(120);
    }
  }

}
