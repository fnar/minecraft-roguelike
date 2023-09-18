package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.Banner;
import com.github.fnar.minecraft.item.Design;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import greymerk.roguelike.util.DyeColor;

public class BannerMapper1_14 extends BaseItemMapper1_14<Banner> {

  @Override
  public Class<Banner> getClazz() {
    return Banner.class;
  }

  public ItemStack map(Banner banner) {
    ItemStack bannerItemStack = new ItemStack(bannerColorToBlock(banner.getBaseColor()));

    CompoundNBT bannerCompoundNBT = ensureCompoundNBT(bannerItemStack);

    CompoundNBT blockEntityTag = ensureBlockEntityTag(bannerCompoundNBT);

    ListNBT patternsTag = ensurePatternsTag(blockEntityTag);

    CompoundNBT patternsAttributes = new CompoundNBT();

    for (Design bannerDesign : banner.getDesigns()) {
      patternsAttributes.putInt("Color", DyeColorMapper1_14.toEnumDyeColor(bannerDesign.getColor()).getId());
      patternsAttributes.putString("Pattern", new DesignMapper1_14().map(bannerDesign.getDesignPattern()).getHashname());
      patternsTag.add(patternsAttributes);
    }
    return bannerItemStack;
  }

  private static Item bannerColorToBlock(DyeColor bannerColor) {
    switch (DyeColorMapper1_14.toEnumDyeColor(bannerColor)) {
      case WHITE:
      default:
        return Items.WHITE_BANNER;
      case ORANGE:
        return Items.ORANGE_BANNER;
      case MAGENTA:
        return Items.MAGENTA_BANNER;
      case LIGHT_BLUE:
        return Items.LIGHT_BLUE_BANNER;
      case YELLOW:
        return Items.YELLOW_BANNER;
      case LIME:
        return Items.LIME_BANNER;
      case PINK:
        return Items.PINK_BANNER;
      case GRAY:
        return Items.GRAY_BANNER;
      case LIGHT_GRAY:
        return Items.LIGHT_GRAY_BANNER;
      case CYAN:
        return Items.CYAN_BANNER;
      case PURPLE:
        return Items.PURPLE_BANNER;
      case BLUE:
        return Items.BLUE_BANNER;
      case BROWN:
        return Items.BROWN_BANNER;
      case GREEN:
        return Items.GREEN_BANNER;
      case RED:
        return Items.RED_BANNER;
      case BLACK:
        return Items.BLACK_BANNER;
    }
  }

  private static CompoundNBT ensureCompoundNBT(ItemStack banner) {
    CompoundNBT nbt = banner.getTag();
    if (nbt != null) {
      return nbt;
    }

    banner.setTag(new CompoundNBT());
    return banner.getTag();
  }

  private static CompoundNBT ensureBlockEntityTag(CompoundNBT CompoundNBT) {
    if (CompoundNBT.contains("BlockEntityTag")) {
      return CompoundNBT.getCompound("BlockEntityTag");
    }
    CompoundNBT tag = new CompoundNBT();
    CompoundNBT.put("BlockEntityTag", tag);
    return tag;
  }

  private static ListNBT ensurePatternsTag(CompoundNBT tag) {
    ListNBT patterns;

    if (tag.contains("Patterns")) {
      patterns = tag.getList("Patterns", 10);
    } else {
      patterns = new ListNBT();
      tag.put("Patterns", patterns);
    }
    return patterns;
  }
}
