package com.github.fnar.minecraft.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class BlockParser1_12 {

  public static IBlockState parse(JsonElement e) {
    JsonObject json = e.getAsJsonObject();
    String name = json.get("name").getAsString();
    int meta = json.has("meta") ? json.get("meta").getAsInt() : 0;

    ResourceLocation location = new ResourceLocation(name);
    if (!Block.REGISTRY.containsKey(location)) {
      throw new DungeonSettingParseException("No such block: " + name);
    }

    IBlockState blockState = Block.REGISTRY.getObject(location)
        .getStateFromMeta(meta);

    if (name.contains("leaves")) {
      blockState.withProperty(BlockLeaves.DECAYABLE, false);
    }
    return blockState;
  }

}
