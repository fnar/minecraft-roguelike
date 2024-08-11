package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.StringlyNamedItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class StringlyNamedItemMapper1_12 extends BaseItemMapper1_12<StringlyNamedItem> {
  @Override
  public Class<StringlyNamedItem> getClazz() {
    return StringlyNamedItem.class;
  }

  @Override
  public ItemStack map(StringlyNamedItem stringlyNamedItem) {
    String name = stringlyNamedItem.getItemName();
    Item item = Item.REGISTRY.getObject(new ResourceLocation(name));
    if (item == null) {
      throw new DungeonSettingParseException("Invalid rldItem: " + name + ". Check for typos, and ensure that it exists in this pack.");
    }
    return addEnchantmentNbtTags(stringlyNamedItem, item);
  }
}
