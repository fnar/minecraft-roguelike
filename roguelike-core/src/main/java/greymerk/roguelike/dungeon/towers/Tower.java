package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.roguelike.worldgen.SpiralStairStep;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public abstract class Tower implements ITower {
  protected StairsBlock getStair(Theme theme) {
    return theme.getPrimary().getStair();
  }

  protected BlockBrush getPillar(Theme theme) {
    return theme.getPrimary().getWall();
  }

  protected void generateStaircase(WorldEditor editor, Theme theme, Coord topStep, Coord bottomStep) {
    int staircaseHeight = topStep.getY() - bottomStep.getY();
    new SpiralStairStep(editor, bottomStep, getStair(theme), getPillar(theme)).generate(staircaseHeight);
  }
}
