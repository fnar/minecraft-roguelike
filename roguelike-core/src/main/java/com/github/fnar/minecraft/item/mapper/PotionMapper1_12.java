package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.Effect;
import com.github.fnar.minecraft.item.Potion;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

import java.util.Optional;

public class PotionMapper1_12 extends BaseItemMapper1_12<Potion> {

  @Override
  public Class<Potion> getClazz() {
    return Potion.class;
  }

  @Override
  public ItemStack map(Potion potion) {
    ItemPotion potionForm = mapForm(potion.getForm());
    PotionType potionType = mapPotionType(potion);
    ItemStack itemStack = PotionUtils.addPotionToItemStack(new ItemStack(potionForm), potionType);

    NBTTagCompound tags = getNbtTagCompound(itemStack);

    Optional.ofNullable(potion.getColor()).ifPresent(color -> tags.setInteger("CustomPotionColor", color.asInt()));

    potion.getEffects()
        .stream().map(this::mapEffectToTag)
        .forEach(effectTag -> mapEffectTags(effectTag, tags));

    return itemStack;
  }

  private NBTTagCompound getNbtTagCompound(ItemStack itemStack) {
    NBTTagCompound tags = Optional.ofNullable(itemStack.getTagCompound()).orElseGet(NBTTagCompound::new);
    itemStack.setTagCompound(tags);
    return tags;
  }

  private PotionType mapPotionType(Potion potion) {
    return mapToPotionType(potion.getEffect(), potion.isAmplified(), potion.isExtended());
  }

  private ItemPotion mapForm(Potion.Form form) {
    return form == Potion.Form.REGULAR ? Items.POTIONITEM
        : form == Potion.Form.SPLASH ? Items.SPLASH_POTION
            : Items.LINGERING_POTION;
  }

  private NBTTagCompound mapEffectToTag(Effect effect) {
    final int ticksPerSecond = 20;
    NBTTagCompound effectTag = new NBTTagCompound();
    effectTag.setByte("Id", (byte) effect.getType().getEffectID());
    effectTag.setByte("Amplifier", (byte) effect.getAmplification());
    effectTag.setInteger("Duration", effect.getDuration() * ticksPerSecond);
    effectTag.setBoolean("Ambient", false);
    return effectTag;
  }

  private static void mapEffectTags(NBTTagCompound effectTag, NBTTagCompound tags) {
    final String customPotionEffectsTag = "CustomPotionEffects";
    NBTTagList effects = tags.getTagList(customPotionEffectsTag, 10);
    effects.appendTag(effectTag);
    tags.setTag(customPotionEffectsTag, effects);
  }

  public net.minecraft.potion.PotionType mapToPotionType(
      Potion.Effect effect,
      boolean isAmplified,
      boolean isExtended
  ) {
    switch (effect) {
      case HEALING:
        return isAmplified ? PotionTypes.STRONG_HEALING
            : PotionTypes.HEALING;
      case HARMING:
        return isAmplified ? PotionTypes.STRONG_HARMING
            : PotionTypes.HARMING;
      case REGENERATION:
        return isExtended ? PotionTypes.LONG_REGENERATION
            : isAmplified ? PotionTypes.STRONG_REGENERATION
                : PotionTypes.REGENERATION;
      case POISON:
        return isExtended ? PotionTypes.LONG_POISON
            : isAmplified ? PotionTypes.STRONG_POISON
                : PotionTypes.POISON;
      case STRENGTH:
        return isExtended ? PotionTypes.LONG_STRENGTH
            : isAmplified ? PotionTypes.STRONG_STRENGTH
                : PotionTypes.STRENGTH;
      case WEAKNESS:
        return isExtended ? PotionTypes.LONG_WEAKNESS
            : PotionTypes.WEAKNESS;
      case SLOWNESS:
        return isExtended ? PotionTypes.LONG_SLOWNESS
            : PotionTypes.SLOWNESS;
      case SWIFTNESS:
        return isExtended ? PotionTypes.LONG_SWIFTNESS
            : isAmplified ? PotionTypes.STRONG_SWIFTNESS
                : PotionTypes.SWIFTNESS;
      case FIRE_RESISTANCE:
        return isExtended ? PotionTypes.LONG_FIRE_RESISTANCE
            : PotionTypes.FIRE_RESISTANCE;
      case INVISIBILITY:
        return isExtended ? PotionTypes.LONG_INVISIBILITY
            : PotionTypes.INVISIBILITY;
      case LEAPING:
        return isExtended ? PotionTypes.LONG_LEAPING
            : PotionTypes.LEAPING;
      case NIGHT_VISION:
        return isExtended ? PotionTypes.LONG_NIGHT_VISION
            : PotionTypes.NIGHT_VISION;
      case WATER_BREATHING:
        return isExtended ? PotionTypes.LONG_WATER_BREATHING
            : PotionTypes.WATER_BREATHING;
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
        return PotionTypes.AWKWARD;
    }
  }

}
