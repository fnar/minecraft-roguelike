package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.Banner;
import com.github.fnar.minecraft.item.Shield;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

public class ShieldMapper1_14 extends BaseItemMapper1_14<Shield> {

  @Override
  public Class<Shield> getClazz() {
    return Shield.class;
  }

  @Override
  public ItemStack map(Shield shield) {
    Banner banner = new Banner().withDesigns(shield.getDesigns());
    ItemStack shieldItemStack = new ItemStack(Items.SHIELD);
    applyDesign(new BannerMapper1_14().map(banner), shieldItemStack);
    return shieldItemStack;
  }

  private void applyDesign(ItemStack banner, ItemStack shield) {
    CompoundNBT bannerNBT = banner.getChildTag("BlockEntityTag");
    CompoundNBT shieldNBT = bannerNBT == null ? new CompoundNBT() : bannerNBT.copy();
    shieldNBT.putInt("Base", banner.getDamage() & 15);
    shield.setTagInfo("BlockEntityTag", shieldNBT);
  }
}
