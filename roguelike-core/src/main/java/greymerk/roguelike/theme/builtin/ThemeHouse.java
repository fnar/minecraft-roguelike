package greymerk.roguelike.theme.builtin;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;
import com.github.fnar.minecraft.material.Wood;

import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;

public class ThemeHouse extends Theme {

  public ThemeHouse() {
    BlockBrush walls = BlockType.BRICK.getBrush();
    StairsBlock stair = StairsBlock.brick();
    BlockBrush pillar = BlockType.GRANITE_POLISHED.getBrush();
    BlockBrush floor = BlockType.GRANITE_POLISHED.getBrush();
    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    Wood wood = Wood.OAK;
    BlockBrush secondaryWalls = wood.getPlanks();
    BlockBrush secondaryFloor = BlockType.ANDESITE_POLISHED.getBrush();
    StairsBlock secondaryStair = StairsBlock.oak();
    BlockBrush secondaryPillar = wood.getLog();
    this.secondary = new BlockSet(
        secondaryFloor,
        secondaryWalls,
        secondaryStair,
        secondaryPillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

  }
}
