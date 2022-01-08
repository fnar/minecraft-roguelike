package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.minecraft.item.Potion;
import com.github.fnar.minecraft.item.RldItemStack;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;

public class ArrowMapper1_12 {

  public ItemStack map(RldItemStack rldItemStack) {
    return map((Arrow) rldItemStack.getItem());
  }

  private static ItemStack map(Arrow arrow) {
    return arrow.getTip()
        .map(ArrowMapper1_12::getTippedArrowStack)
        .orElseGet(() -> new ItemStack(Items.ARROW));
  }

  private static ItemStack getTippedArrowStack(Potion potion) {
    // TODO: Move amplification and extension onto effect instead of potion
    PotionType potionType = new PotionMapper1_12().mapToPotionType(potion.getEffect(), false, false);
    String tipString = net.minecraft.potion.PotionType.REGISTRY.getNameForObject(potionType).toString();
    ItemStack arrow = new ItemStack(Items.TIPPED_ARROW);
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setString("Potion", tipString);
    arrow.setTagCompound(nbt);
    return arrow;
  }

}
