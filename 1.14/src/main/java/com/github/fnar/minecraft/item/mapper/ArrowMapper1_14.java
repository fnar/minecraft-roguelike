package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.minecraft.item.Potion;
import com.github.fnar.minecraft.item.RldItemStack;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.registry.Registry;

public class ArrowMapper1_14 {

  public ItemStack map(RldItemStack rldItemStack) {
    return map((Arrow) rldItemStack.getItem());
  }

  public ItemStack map(Arrow arrow) {
    return arrow.getTip()
        .map(ArrowMapper1_14::getTippedArrowStack)
        .orElseGet(() -> new ItemStack(Items.ARROW));
  }

  private static ItemStack getTippedArrowStack(Potion potion) {
    // TODO: Move amplification and extension onto effect instead of potion
    net.minecraft.potion.Potion potionType = new PotionMapper1_14().mapToPotionType(potion.getEffect(), false, false);
    String tipString = Registry.POTION.getKey(potionType).getPath();
    ItemStack arrow = new ItemStack(Items.TIPPED_ARROW);
    CompoundNBT nbt = new CompoundNBT();
    nbt.putString("Potion", tipString);
    arrow.setTag(nbt);
    return arrow;
  }

}
