package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.BlockType;

import java.util.Random;

import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class VillagerHouseTower implements ITower {

  @Override
  public void generate(WorldEditor editor, Random random, ThemeBase theme, Coord origin) {
    Direction facing = Direction.randomCardinal(random);
    Coord base = Tower.getBaseCoord(editor, origin);
    clearTowerArea(editor, base);
    createFoundation(editor, theme, origin, base, facing);
    createWalls(editor, theme, base, facing);
    createRoof(editor, theme, base);
    createSpiralStaircase(editor, theme, origin, base);
  }

  private void clearTowerArea(WorldEditor editor, Coord origin) {
    RectSolid rect = origin.newRect(4);
    for (int i = 0; i < 6; i++) {
      rect.fill(editor, BlockType.AIR.getBrush());
      rect.translate(Direction.UP, 1);
    }
  }

  private void createFoundation(WorldEditor editor, ThemeBase theme, Coord origin, Coord base, Direction facing) {
    for (Coord cursor = new Coord(base); origin.getY() + 10 <= cursor.getY(); cursor.down()) {
      cursor.newRect(2).fill(editor, theme.getPrimary().getFloor());
    }
    Coord entrance = base.copy().translate(facing, 2);
    theme.getPrimary().getStair().setUpsideDown(false).setFacing(facing).stroke(editor, entrance.translate(facing));
  }

  private void createWalls(WorldEditor editor, ThemeBase theme, Coord origin, Direction facing) {
    for (Direction cardinal : Direction.CARDINAL) {
      Coord wallCenter = origin.copy().translate(cardinal, 2).up();
      Coord left = wallCenter.copy().translate(cardinal.antiClockwise());
      Coord right = wallCenter.copy().translate(cardinal.clockwise());

      RectSolid.newRect(left, right.copy().up(3)).fill(editor, theme.getPrimary().getWall());
      right.translate(cardinal.clockwise());
      RectSolid.newRect(right, right.copy().up(3)).fill(editor, theme.getPrimary().getPillar());

      if (cardinal == facing) {
        createDoorway(editor, theme, origin, facing);
      } else {
        Coord windowCoord = origin.copy().translate(cardinal, 2).up(2);
        BlockType.GLASS_PANE.getBrush().stroke(editor, windowCoord);
      }
    }
  }

  private void createDoorway(WorldEditor editor, ThemeBase theme, Coord origin, Direction facing) {
    Coord doorway = origin.copy().translate(facing, 2).up(1);
    theme.getPrimary().getDoor().setFacing(facing).stroke(editor, doorway);

    Direction inward = facing.reverse();
    Coord torchCoord = doorway.copy().translate(inward).up(2);
    BlockType.TORCH.getBrush().setFacing(inward).stroke(editor, torchCoord);
  }

  public void createRoof(WorldEditor editor, ThemeBase theme, Coord origin) {
    Coord center = origin.copy().up(4);
    center.newRect(2).fill(editor, theme.getSecondary().getPillar());
    center.newRect(1).fill(editor, theme.getSecondary().getFloor());

    center.up();
    center.newRect(2).fill(editor, BlockType.OAK_FENCE.getBrush());
    center.newRect(1).fill(editor, BlockType.AIR.getBrush());
  }

  public void createSpiralStaircase(WorldEditor editor, ThemeBase theme, Coord origin, Coord base) {
    for (Coord cursor = new Coord(base); origin.getY() <= cursor.getY(); cursor.down()) {
      editor.spiralStairStep(editor.getRandom(), cursor, theme.getPrimary().getStair(), theme.getSecondary().getPillar());
    }
  }
}
