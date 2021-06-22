package com.github.fnar.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.ThemeBase;

public class NetherFortressTheme extends ThemeBase {

  public NetherFortressTheme() {
    this.primary = new BlockSet(
        BlockType.NETHERBRICK.getBrush(),
        BlockType.NETHERBRICK.getBrush(),
        StairsBlock.netherBrick(),
        BlockType.OBSIDIAN.getBrush(),
        DoorBlock.iron(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.LAVA_FLOWING.getBrush()
    );
    this.secondary = new BlockSet(
        BlockType.NETHERBRICK.getBrush(),
        BlockType.NETHERBRICK.getBrush(),
        StairsBlock.netherBrick(),
        BlockType.NETHERBRICK.getBrush(),
        DoorBlock.iron(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.LAVA_FLOWING.getBrush()
    );
  }
}
