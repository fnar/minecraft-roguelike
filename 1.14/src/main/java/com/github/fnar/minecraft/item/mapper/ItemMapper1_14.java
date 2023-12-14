package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.ItemMapper;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.RldItemStack;

import net.minecraft.item.ItemStack;

public class ItemMapper1_14 implements ItemMapper {

  @Override
  public ItemStack map(RldItemStack rldItemStack) {
    RldItem item = rldItemStack.getItem();
    switch (item.getItemType()) {
      case ARMOUR:
        return new ArmourMapper1_14().map(rldItemStack);
      case ARROW:
        return new ArrowMapper1_14().map(rldItemStack);
      case BOOK:
        return new BookMapper1_14().map(rldItemStack);
      case BANNER:
        return new BannerMapper1_14().map(rldItemStack);
      case BLOCK:
        return new BlockItemMapper1_14().map(rldItemStack);
      case DYE:
        return new DyeMapper1_14().map(rldItemStack);
      case FIREWORK:
        return new FireworkMapper1_14().map(rldItemStack);
      case FOOD:
        return new FoodMapper1_14().map(rldItemStack);
      case INGREDIENT:
        return new IngredientMapper1_14().map(rldItemStack);
      case MATERIAL:
        return new MaterialMapper1_14().map(rldItemStack);
      case MISCELLANEOUS:
        return new MiscellaneousMapper1_14().map(rldItemStack);
      case POTION:
        return new PotionMapper1_14().map(rldItemStack);
      case RECORD:
        return new RecordMapper1_14().map(rldItemStack);
      case SAPLING:
        return new SaplingMapper1_14().map(rldItemStack);
      case SEED:
        return new SeedMapper1_14().map(rldItemStack);
      case SHIELD:
        return new ShieldMapper1_14().map(rldItemStack);
      case STRINGLY_NAMED:
        return new StringlyNamedItemMapper1_14().map(rldItemStack);
      case TOOL:
        return new ToolMapper1_14().map(rldItemStack);
      case WEAPON:
        return new WeaponMapper1_14().map(rldItemStack);
    }
    throw new CouldNotMapItemException(rldItemStack);
  }

}
