package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.StringlyNamedItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class StringlyNamedItemMapper1_14 extends RldBaseItemMapper1_14<StringlyNamedItem> {
  @Override
  public Class<StringlyNamedItem> getClazz() {
    return StringlyNamedItem.class;
  }

  @Override
  public ItemStack map(StringlyNamedItem item) {
    String name = item.getItemName();
      Optional<Item> object = Registry.ITEM.getValue(new ResourceLocation(name));
      if (!object.isPresent()) {
        throw new DungeonSettingParseException("Invalid item: " + name + ". Check for typos, and ensure that it exists in this pack.");
      }
      return new ItemStack(object.get());
  }
}
