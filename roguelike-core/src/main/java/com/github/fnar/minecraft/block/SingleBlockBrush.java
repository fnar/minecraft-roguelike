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

  @Override
  public boolean stroke(WorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid) {
    return editor.setBlock(pos, this, fillAir, replaceSolid);
  }

  @Override
  public SingleBlockBrush setFacing(Direction facing) {
    this.facing = facing;
    return this;
  }

  public SingleBlockBrush copy() {
    SingleBlockBrush copy = new SingleBlockBrush(this.blockType, this.material);
    copy.setFacing(facing);
    copy.setJson(json);
    return copy;
  }

  public boolean isValidPosition(WorldEditor editor, Coord coord) {
    return editor.isValidPosition(this, coord);
  }

}
