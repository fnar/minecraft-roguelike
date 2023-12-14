package com.github.fnar.minecraft.block;

import com.google.gson.JsonElement;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class SingleBlockBrush implements BlockBrush {

  public static final SingleBlockBrush AIR = BlockType.AIR.getBrush();

  private BlockType blockType;
  private Direction facing = Direction.EAST;
  private JsonElement json;
  private Material material;
  private boolean isWaterlogged;

  public SingleBlockBrush(BlockType blockType) {
    this(blockType, Material.NONE);
  }

  public SingleBlockBrush(BlockType blockType, Material material) {
    this.blockType = blockType;
    this.material = material;
  }

  public SingleBlockBrush(JsonElement jsonElement) throws DungeonSettingParseException {
    this.json = jsonElement;
  }

  public boolean isBlockOfType(BlockType blockType) {
    return blockType != null && blockType.equals(getBlockType());
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid) {
    return editor.setBlock(pos, this, fillAir, replaceSolid);
  }

  @Override
  public SingleBlockBrush setFacing(Direction facing) {
    this.facing = facing;
    return this;
  }

  public void setWaterlogged(boolean isWaterlogged) {
    this.isWaterlogged = isWaterlogged;
  }

  public SingleBlockBrush withWaterlogging(boolean isWaterlogged) {
    this.isWaterlogged = isWaterlogged;
    return this;
  }

  public SingleBlockBrush withWaterlogging() {
    return this.withWaterlogging(true);
  }

  public boolean isWaterlogged() {
    return this.isWaterlogged;
  }

  public SingleBlockBrush copy() {
    SingleBlockBrush copy = new SingleBlockBrush(this.blockType);
    copy.setFacing(facing);
    copy.setJson(json);
    copy.setMaterial(material);
    copy.setWaterlogged(isWaterlogged);
    return copy;
  }

}
