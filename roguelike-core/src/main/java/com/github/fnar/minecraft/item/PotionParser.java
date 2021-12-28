package com.github.fnar.minecraft.item;

import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class PotionParser {
  public ItemStack parsePotion(JsonObject data) throws DungeonSettingParseException {
    if (!data.has("name")) {
      throw new DungeonSettingParseException("Potion missing name field");
    }
    String nameString = data.get("name").getAsString().toUpperCase();

    net.minecraft.potion.PotionType type = net.minecraft.potion.PotionType.getPotionTypeForName(nameString);

    net.minecraft.item.Item item = !data.has("form")
        ? Items.POTIONITEM
        : new PotionMapper1_12().map(Potion.Form.valueOf(data.get("form").getAsString().toUpperCase()));
    ItemStack itemStack = new ItemStack(item);

    return PotionUtils.addPotionToItemStack(itemStack, type);
  }
}
