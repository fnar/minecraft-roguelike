package com.github.fnar.minecraft.block.normal;

import com.google.gson.JsonElement;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.Material;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class StairsBlock extends SingleBlockBrush {

  private boolean isUpsideDown;

  public StairsBlock(BlockType blockType, Material material) {
    super(blockType, material);
  }

  public StairsBlock(JsonElement jsonElement) throws DungeonSettingParseException {
    super(jsonElement);
  }

  public StairsBlock setUpsideDown(boolean isUpsideDown) {
    this.isUpsideDown = isUpsideDown;
    return this;
  }

  public boolean isUpsideDown() {
    return isUpsideDown;
  }

  public static StairsBlock cobble() {
    return new StairsBlock(BlockType.STONE_STAIRS, Material.STONE);
  }

  public static StairsBlock stone() {
    return new StairsBlock(BlockType.STONE_STAIRS, Material.STONE);
  }

  public static StairsBlock stoneBrick() {
    return new StairsBlock(BlockType.STONE_BRICK_STAIRS, Material.STONE);
  }

  public static StairsBlock brick() {
    return new StairsBlock(BlockType.BRICK_STAIRS, Material.STONE);
  }

  public static StairsBlock sandstone() {
    return new StairsBlock(BlockType.SANDSTONE_STAIRS, Material.STONE);
  }

  public static StairsBlock redSandstone() {
    return new StairsBlock(BlockType.RED_SANDSTONE_STAIRS, Material.STONE);
  }

  public static StairsBlock quartz() {
    return new StairsBlock(BlockType.QUARTZ_STAIRS, Material.STONE);
  }

  public static StairsBlock netherBrick() {
    return new StairsBlock(BlockType.NETHER_BRICK_STAIRS, Material.STONE);
  }

  public static StairsBlock oak() {
    return new StairsBlock(BlockType.OAK_STAIRS, Material.WOOD);
  }

  public static StairsBlock spruce() {
    return new StairsBlock(BlockType.SPRUCE_STAIRS, Material.WOOD);
  }

  public static StairsBlock birch() {
    return new StairsBlock(BlockType.BIRCH_STAIRS, Material.WOOD);
  }

  public static StairsBlock jungle() {
    return new StairsBlock(BlockType.JUNGLE_STAIRS, Material.WOOD);
  }

  public static StairsBlock acacia() {
    return new StairsBlock(BlockType.ACACIA_STAIRS, Material.WOOD);
  }

  public static StairsBlock darkOak() {
    return new StairsBlock(BlockType.DARK_OAK_STAIRS, Material.WOOD);
  }

  public static StairsBlock purpur() {
    return new StairsBlock(BlockType.PURPUR_STAIRS, Material.NONE);
  }
}
