package com.github.fnar.minecraft.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ShieldMapper1_12 extends BaseItemMapper1_12<Shield> {

  @Override
  public Class<Shield> getClazz() {
    return Shield.class;
  }

  @Override
  public ItemStack map(Shield shield) {
    Banner banner = new Banner().withDesigns(shield.getDesigns());
    ItemStack shieldItemStack = new ItemStack(Items.SHIELD, 1, 0);
    applyDesign(new BannerMapper1_12().map(banner), shieldItemStack);
    return shieldItemStack;
  }

  private void applyDesign(ItemStack banner, ItemStack shield) {
    NBTTagCompound bannerNBT = banner.getSubCompound("BlockEntityTag");
    NBTTagCompound shieldNBT = bannerNBT == null ? new NBTTagCompound() : bannerNBT.copy();
    shieldNBT.setInteger("Base", banner.getMetadata() & 15);
    shield.setTagInfo("BlockEntityTag", shieldNBT);
  }
}
