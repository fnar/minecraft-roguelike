package com.github.fnar.minecraft.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class StringlyNamedItemMapper1_12 extends RldBaseItemMapper1_12<StringlyNamedItem> {
  @Override
  public Class<StringlyNamedItem> getClazz() {
    return StringlyNamedItem.class;
  }

  @Override
  public ItemStack map(StringlyNamedItem item) {
    String name = item.getItemName();
      Item object = Item.REGISTRY.getObject(new ResourceLocation(name));
      if (object == null) {
        throw new DungeonSettingParseException("Invalid item: " + name + ". Check for typos, and ensure that it exists in this pack.");
      }
      return new ItemStack(object);
  }
}
