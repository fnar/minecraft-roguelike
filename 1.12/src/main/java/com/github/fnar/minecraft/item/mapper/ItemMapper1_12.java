package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.CouldNotMapItemException;
import com.github.fnar.minecraft.item.ItemMapper;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.RldItemStack;

import net.minecraft.item.ItemStack;

public class ItemMapper1_12 implements ItemMapper {

  @Override
  public ItemStack map(RldItemStack rldItemStack) {
    RldItem item = rldItemStack.getItem();
    switch (item.getItemType()) {
      case ARMOUR:
        return new ArmourMapper1_12().map(rldItemStack);
      case ARROW:
        return new ArrowMapper1_12().map(rldItemStack);
      case BOOK:
        return new BookMapper1_12().map(rldItemStack);
      case BANNER:
        return new BannerMapper1_12().map(rldItemStack);
      case BLOCK:
        return new BlockItemMapper1_12().map(rldItemStack);
      case DYE:
        return new DyeMapper1_12().map(rldItemStack);
      case FIREWORK:
        return new FireworkMapper1_12().map(rldItemStack);
      case FOOD:
        return new FoodMapper1_12().map(rldItemStack);
      case INGREDIENT:
        return new IngredientMapper1_12().map(rldItemStack);
      case MATERIAL:
        return new MaterialMapper1_12().map(rldItemStack);
      case MISCELLANEOUS:
        return new MiscellaneousMapper1_12().map(rldItemStack);
      case POTION:
        return new PotionMapper1_12().map(rldItemStack);
      case RECORD:
        return new RecordMapper1_12().map(rldItemStack);
      case SAPLING:
        return new SaplingMapper1_12().map(rldItemStack);
      case SEED:
        return new SeedMapper1_12().map(rldItemStack);
      case SHIELD:
        return new ShieldMapper1_12().map(rldItemStack);
      case STRINGLY_NAMED:
        return new StringlyNamedItemMapper1_12().map(rldItemStack);
      case TOOL:
        return new ToolMapper1_12().map(rldItemStack);
      case WEAPON:
        return new WeaponMapper1_12().map(rldItemStack);
    }
    throw new CouldNotMapItemException(rldItemStack);
  }

}
