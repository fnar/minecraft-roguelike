package com.github.fnar.minecraft.block.redstone;

import com.google.gson.JsonElement;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.Material;
import com.github.fnar.minecraft.block.SingleBlockBrush;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoorBlock extends SingleBlockBrush {

  private boolean isOpen;
  private boolean isTop;
  private boolean isHingeLeft;

  public DoorBlock(BlockType blockType, Material material) {
    super(blockType, material);
  }

  public DoorBlock(JsonElement jsonElement) throws DungeonSettingParseException {
    super(jsonElement);
    super.setBlockType(BlockType.OAK_DOOR);
  }

  public DoorBlock setOpen() {
    this.isOpen = true;
    return this;
  }

  public DoorBlock setTop() {
    this.isTop = true;
    return this;
  }

  public boolean isTop() {
    return isTop;
  }

  public DoorBlock setHingeLeft() {
    this.isHingeLeft = true;
    return this;
  }

  public static DoorBlock iron() {
    return new DoorBlock(BlockType.IRON_DOOR, Material.METAL);
  }

  public static DoorBlock oak() {
    return new DoorBlock(BlockType.OAK_DOOR, Material.WOOD);
  }

  public static DoorBlock birch() {
    return new DoorBlock(BlockType.BIRCH_DOOR, Material.WOOD);
  }

  public static DoorBlock spruce() {
    return new DoorBlock(BlockType.SPRUCE_DOOR, Material.WOOD);
  }

  public static DoorBlock jungle() {
    return new DoorBlock(BlockType.JUNGLE_DOOR, Material.WOOD);
  }

  public static DoorBlock acacia() {
    return new DoorBlock(BlockType.ACACIA_DOOR, Material.WOOD);
  }

  public static DoorBlock darkOak() {
    return new DoorBlock(BlockType.DARK_OAK_DOOR, Material.WOOD);
  }

  public static DoorBlock warpedDoor() {
    return new DoorBlock(BlockType.WARPED_DOOR, Material.WOOD);
  }

  private DoorBlock getTop() {
    DoorBlock topBlock = new DoorBlock(getBlockType(), getMaterial())
        .setTop();
    topBlock.setJson(this.getJson());
    if (this.isOpen()) {
      topBlock.setOpen();
    }
    if (this.isHingeLeft()) {
      topBlock.setHingeLeft();
    }
    topBlock.setFacing(this.getFacing());
    return topBlock;
  }

  @Override
  public boolean stroke(WorldEditor worldEditor, Coord pos, boolean fillAir, boolean replaceSolid) {
    worldEditor.setBlock(pos, this, fillAir, replaceSolid);
    if (!isTop()) {
      Coord above = pos.copy().up();
      worldEditor.setBlock(above, getTop(), fillAir, replaceSolid);
    }
    return true;
  }

  @Override
  public DoorBlock copy() {
    JsonElement json = getJson();
    DoorBlock copy = (json != null)
        ? new DoorBlock(json)
        : new DoorBlock(getBlockType(), getMaterial());
    copy.setFacing(getFacing());
    copy.setHingeLeft(isHingeLeft);
    copy.setOpen(isOpen);
    copy.setTop(isTop);
    return copy;
  }
}
