package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.block.ColoredBlockMapper1_12;
import com.github.fnar.minecraft.item.Banner;
import com.github.fnar.minecraft.item.Design;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class BannerMapper1_12 extends BaseItemMapper1_12<Banner> {

  @Override
  public Class<Banner> getClazz() {
    return Banner.class;
  }

  public ItemStack map(Banner banner) {
    ItemStack bannerItemStack = new ItemStack(Items.BANNER);

    NBTTagCompound bannerNbtTagCompound = ensureNbtTagCompound(bannerItemStack);

    NBTTagCompound blockEntityTag = ensureBlockEntityTag(bannerNbtTagCompound);

    NBTTagList patternsTag = ensurePatternsTag(blockEntityTag);

    NBTTagCompound patternsAttributes = new NBTTagCompound();

    for (Design bannerDesign : banner.getDesigns()) {
      patternsAttributes.setInteger("Color", ColoredBlockMapper1_12.toEnumDyeColor(bannerDesign.getColor()).getDyeDamage());
      patternsAttributes.setString("Pattern", new DesignMapper1_12().map(bannerDesign.getDesignPattern()).getHashname());
      patternsTag.appendTag(patternsAttributes);
    }
    return bannerItemStack;
  }

  private static NBTTagCompound ensureNbtTagCompound(ItemStack banner) {
    NBTTagCompound nbt = banner.getTagCompound();
    if (nbt != null) {
      return nbt;
    }

    banner.setTagCompound(new NBTTagCompound());
    return banner.getTagCompound();
  }

  private static NBTTagCompound ensureBlockEntityTag(NBTTagCompound nbtTagCompound) {
    if (nbtTagCompound.hasKey("BlockEntityTag")) {
      return nbtTagCompound.getCompoundTag("BlockEntityTag");
    }
    NBTTagCompound tag = new NBTTagCompound();
    nbtTagCompound.setTag("BlockEntityTag", tag);
    return tag;
  }

  private static NBTTagList ensurePatternsTag(NBTTagCompound tag) {
    NBTTagList patterns;

    if (tag.hasKey("Patterns")) {
      patterns = tag.getTagList("Patterns", 10);
    } else {
      patterns = new NBTTagList();
      tag.setTag("Patterns", patterns);
    }
    return patterns;
  }
}
