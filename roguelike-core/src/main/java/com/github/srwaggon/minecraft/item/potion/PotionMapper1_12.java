package com.github.srwaggon.minecraft.item.potion;

import com.github.srwaggon.minecraft.item.RldItem;
import com.github.srwaggon.minecraft.item.RldItemStack;
import com.github.srwaggon.minecraft.tag.TagMapper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import greymerk.roguelike.treasure.loot.PotionType;

public class PotionMapper1_12 {

  public static ItemStack map(RldItemStack potionItemStack) {
    RldItem item = potionItemStack.getItem();
    Potion potion = (Potion) item;
    ItemStack itemStack = PotionType.getSpecific(potion.getForm(), potion.getType(), potion.isAmplified(), potion.isExtended());

    itemStack.getTagCompound().merge(TagMapper.map(potionItemStack.getTags()));

    potion.getEffects()
        .forEach(effect ->
            addCustomEffect(itemStack, effect.getType(), effect.getAmplification(), effect.getDuration()));

    return itemStack;
  }

  private static void addCustomEffect(ItemStack itemStack, EffectType type, int amplifier, int duration) {
    final int ticksPerSecond = 20;
    final String CUSTOM = "CustomPotionEffects";

    NBTTagCompound tag = itemStack.getTagCompound();
    if (tag == null) {
      tag = new NBTTagCompound();
      itemStack.setTagCompound(tag);
    }


    NBTTagList effects = tag.getTagList(CUSTOM, 10);
    if (effects == null) {
      effects = new NBTTagList();
      tag.setTag(CUSTOM, effects);
    }

    NBTTagCompound toAdd = new NBTTagCompound();

    toAdd.setByte("Id", (byte) type.getEffectID());
    toAdd.setByte("Amplifier", (byte) (amplifier));
    toAdd.setInteger("Duration", duration * ticksPerSecond);
    toAdd.setBoolean("Ambient", true);

    effects.appendTag(toAdd);
    tag.setTag(CUSTOM, effects);
    itemStack.setTagCompound(tag);
  }


}
