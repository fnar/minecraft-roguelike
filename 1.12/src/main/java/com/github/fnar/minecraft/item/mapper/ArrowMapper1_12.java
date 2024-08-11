package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.minecraft.item.Potion;
import com.github.fnar.minecraft.item.RldItemStack;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;

public class ArrowMapper1_12 extends BaseItemMapper1_12<Arrow> {

  @Override
  public Class<Arrow> getClazz() {
    return Arrow.class;
  }

  public ItemStack map(RldItemStack rldItemStack) {
    return map((Arrow) rldItemStack.getItem());
  }

  public ItemStack map(Arrow arrow) {
    return arrow.getTip()
        .map(potion -> getTippedArrowStack(arrow, potion))
        .orElseGet(() -> new ItemStack(Items.ARROW));
  }

  private ItemStack getTippedArrowStack(Arrow arrow, Potion potion) {
    // TODO: Move amplification and extension onto effect instead of potion
    PotionType potionType = new PotionMapper1_12().mapToPotionType(potion.getEffect(), false, false);
    String tipString = net.minecraft.potion.PotionType.REGISTRY.getNameForObject(potionType).toString();
    ItemStack item = addEnchantmentNbtTags(arrow, Items.TIPPED_ARROW);
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setString("Potion", tipString);
    item.setTagCompound(nbt);
    return item;
  }

}
