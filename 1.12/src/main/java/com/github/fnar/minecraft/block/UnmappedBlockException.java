package com.github.fnar.minecraft.block;

public class UnmappedBlockException extends RuntimeException {

  public UnmappedBlockException(BlockType blockType) {
    super("Unmapped block: "  + blockType);
  }

}
