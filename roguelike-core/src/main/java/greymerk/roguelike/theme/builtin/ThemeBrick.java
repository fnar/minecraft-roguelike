package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;
import com.github.fnar.minecraft.material.Wood;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeBrick extends Theme {

  public ThemeBrick() {
    BlockBrush walls = BlockType.BRICK.getBrush();
    StairsBlock stair = StairsBlock.brick();
    BlockBrush pillar = Wood.SPRUCE.getLog();
    BlockBrush segmentWall = Wood.SPRUCE.getPlanks();
    this.primary = new BlockSet(
        walls,
        walls,
        stair,
        walls,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = new BlockSet(
        segmentWall,
        segmentWall,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

  }
}
