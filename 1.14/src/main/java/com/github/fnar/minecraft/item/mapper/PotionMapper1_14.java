package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.Effect;
import com.github.fnar.minecraft.item.Potion;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;

import java.util.Optional;

public class PotionMapper1_14 extends BaseItemMapper1_14<Potion> {

  @Override
  public Class<Potion> getClazz() {
    return Potion.class;
  }

  @Override
  public ItemStack map(Potion potion) {
    Item potionForm = mapForm(potion.getForm());
    net.minecraft.potion.Potion potionType = mapPotionType(potion);
    ItemStack itemStack = PotionUtils.addPotionToItemStack(new ItemStack(potionForm), potionType);

    CompoundNBT tags = getCompoundNBT(itemStack);

    Optional.ofNullable(potion.getColor()).ifPresent(color -> tags.putInt("CustomPotionColor", color.asInt()));

    potion.getEffects()
        .stream().map(this::mapEffectToTag)
        .forEach(effectTag -> mapEffectTags(effectTag, tags));

    return itemStack;
  }

  private CompoundNBT getCompoundNBT(ItemStack itemStack) {
    CompoundNBT tags = Optional.ofNullable(itemStack.getTag()).orElseGet(CompoundNBT::new);
    itemStack.setTag(tags);
    return tags;
  }

  private net.minecraft.potion.Potion mapPotionType(Potion potion) {
    return mapToPotionType(potion.getEffect(), potion.isAmplified(), potion.isExtended());
  }

  private Item mapForm(Potion.Form form) {
    return form == Potion.Form.REGULAR
        ? Items.POTION
        : form == Potion.Form.SPLASH
            ? Items.SPLASH_POTION
            : Items.LINGERING_POTION;
  }

  private CompoundNBT mapEffectToTag(Effect effect) {
    final int ticksPerSecond = 20;
    CompoundNBT effectTag = new CompoundNBT();
    effectTag.putByte("Id", (byte) effect.getType().getEffectID());
    effectTag.putByte("Amplifier", (byte) effect.getAmplification());
    effectTag.putInt("Duration", effect.getDuration() * ticksPerSecond);
    effectTag.putBoolean("Ambient", false);
    return effectTag;
  }

  private static void mapEffectTags(CompoundNBT effectTag, CompoundNBT tags) {
    final String customPotionEffectsTag = "CustomPotionEffects";
    ListNBT effects = tags.getList(customPotionEffectsTag, 10);
    effects.add(effectTag);
    tags.put(customPotionEffectsTag, effects);
  }

  public net.minecraft.potion.Potion mapToPotionType(
      Potion.Effect effect,
      boolean isAmplified,
      boolean isExtended
  ) {
    switch (effect) {
      case HEALING:
        return isAmplified ? Potions.STRONG_HEALING : Potions.HEALING;
      case HARMING:
        return isAmplified ? Potions.STRONG_HARMING
            : Potions.HARMING;
      case REGENERATION:
        return isExtended ? Potions.LONG_REGENERATION
            : isAmplified ? Potions.STRONG_REGENERATION
                : Potions.REGENERATION;
      case POISON:
        return isExtended ? Potions.LONG_POISON
            : isAmplified ? Potions.STRONG_POISON
                : Potions.POISON;
      case STRENGTH:
        return isExtended ? Potions.LONG_STRENGTH
            : isAmplified ? Potions.STRONG_STRENGTH
                : Potions.STRENGTH;
      case WEAKNESS:
        return isExtended ? Potions.LONG_WEAKNESS
            : Potions.WEAKNESS;
      case SLOWNESS:
        return isExtended ? Potions.LONG_SLOWNESS
            : Potions.SLOWNESS;
      case SWIFTNESS:
        return isExtended ? Potions.LONG_SWIFTNESS
            : isAmplified ? Potions.STRONG_SWIFTNESS
                : Potions.SWIFTNESS;
      case FIRE_RESISTANCE:
        return isExtended ? Potions.LONG_FIRE_RESISTANCE
            : Potions.FIRE_RESISTANCE;
      case INVISIBILITY:
        return isExtended ? Potions.LONG_INVISIBILITY
            : Potions.INVISIBILITY;
      case LEAPING:
        return isExtended ? Potions.LONG_LEAPING
            : Potions.LEAPING;
      case NIGHT_VISION:
        return isExtended ? Potions.LONG_NIGHT_VISION
            : Potions.NIGHT_VISION;
      case WATER_BREATHING:
        return isExtended ? Potions.LONG_WATER_BREATHING
            : Potions.WATER_BREATHING;
      case LUCK:
//        return PotionTypes.LUCK; // introduced in 1.9. Not sure why I can't find it in Forge.
      case LEVITATION:
//        return PotionTypes.LEVITATION; // introduced in 1.9. Not sure why I can't find it in Forge.
      case TURTLE_MASTER:
//        return PotionTypes.TURTLE_MASTER; // introduced in 1.13
      case SLOW_FALLING:
//        return PotionTypes.SLOW_FALLING; // introduced in 1.13
      case AWKWARD:
      default:
        return Potions.AWKWARD;
    }
  }

}
