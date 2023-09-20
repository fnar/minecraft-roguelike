package com.github.fnar.minecraft.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.github.fnar.minecraft.CouldNotMapException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class BlockParser1_14 {

  public static BlockState parse(JsonElement e) {
    JsonObject json = e.getAsJsonObject();
    String name = json.get("name").getAsString();

    ResourceLocation location = new ResourceLocation(name);
    DefaultedRegistry<Block> registry = Registry.BLOCK;
    if (!registry.containsKey(location)) {
      throw new DungeonSettingParseException("No such block: " + name);
    }

    Optional<Block> value = registry.getValue(location);
    if (!value.isPresent()) {
      throw new CouldNotMapException("Could not map block with name \"" + name + "\"");
    }
    BlockState blockState = value.get().getDefaultState();

    if (name.contains("leaves")) {
      return blockState.with(LeavesBlock.PERSISTENT, true);
    }

    return blockState;
  }

}
