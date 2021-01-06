package com.github.srwaggon.roguelike.worldgen.block.decorative;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.wool;

public class BedBlock extends SingleBlockBrush {

  private DyeColor color = DyeColor.RED;
  private boolean isHead = true;

  public BedBlock() {
    super(BlockType.BED);
  }

  public BedBlock setColor(DyeColor color) {
    this.color = color;
    return this;
  }

  public DyeColor getColor() {
    return color;
  }

  public boolean isHead() {
    return isHead;
  }

  public BedBlock setHead(boolean head) {
    isHead = head;
    return this;
  }

  private BedBlock getFoot() {
    BedBlock bedBlock = bed()
        .setColor(this.getColor())
        .setHead(false);
    bedBlock.setFacing(getFacing());
    return bedBlock;
  }

  private BedBlock getHead() {
    BedBlock bedBlock = bed()
        .setColor(this.getColor())
        .setHead(true);
    bedBlock.setFacing(getFacing());
    return bedBlock;
  }

  @Override
  public boolean stroke(WorldEditor editor, Coord pos, boolean fillAir, boolean replaceSolid) {

    Coord cursor = pos.copy();

    if (!RogueConfig.getBoolean(RogueConfig.FURNITURE)) {
      wool().setColor(DyeColor.WHITE).stroke(editor, cursor);

      cursor.translate(this.getFacing());
      wool().setColor(DyeColor.RED).stroke(editor, cursor);
      return true;
    }
    editor.setBlock(cursor, this, fillAir, replaceSolid);
    editor.setBedColorAt(cursor, color);

    cursor.translate(this.getFacing());
    editor.setBlock(cursor, getFoot(), fillAir, replaceSolid);
    editor.setBedColorAt(cursor, color);
    return true;
  }

  public static BedBlock bed() {
    return new BedBlock();
  }
}
