package greymerk.roguelike.dungeon.towers;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.roguelike.worldgen.generatables.SpiralStairStep;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class VillagerHouseTower extends Tower {

  public VillagerHouseTower(WorldEditor worldEditor, Theme theme) {
    super(worldEditor, theme);
  }

  @Override
  public void generate(Coord origin) {
    Direction facing = Direction.randomCardinal(editor.getRandom());
    Coord base = TowerType.getBaseCoord(editor, origin);
    clearTowerArea(editor, base);
    createFoundation(origin, base, facing);
    createWalls(base, facing);
    createRoof(base);
    createSpiralStaircase(origin, base);
  }

  private void clearTowerArea(WorldEditor editor, Coord origin) {
    RectSolid rect = origin.newRect(2);
    for (int i = 0; i < 6; i++) {
      rect.fill(editor, BlockType.AIR.getBrush());
      rect.translate(Direction.UP, 1);
    }
  }

  private void createFoundation(Coord origin, Coord base, Direction facing) {
    for (Coord cursor = new Coord(base); origin.getY() + 10 <= cursor.getY(); cursor.down()) {
      cursor.newRect(2).fill(editor, theme.getPrimary().getFloor());
    }

    createOutsideStaircase(base, facing);
  }

  private void createOutsideStaircase(Coord base, Direction facing) {
    BlockBrush stairBlock = theme.getPrimary().getStair().setUpsideDown(false).setFacing(facing);
    Coord stairCoord = base.copy().translate(facing, 3);
    do {
      stairBlock.stroke(editor, stairCoord);

      fillBeneathStep(stairCoord);

      stairCoord.translate(facing).down();
    } while (!editor.isValidGroundBlock(stairCoord));
  }

  private void fillBeneathStep(Coord entrance) {
    Coord belowTop = entrance.copy().down();
    Coord belowBottom = belowTop.copy();
    while (!editor.isValidGroundBlock(belowBottom)) {
      belowBottom.down();
    }
    getPrimaryWall().fill(editor, RectSolid.newRect(belowTop, belowBottom));
  }

  private void createWalls(Coord origin, Direction facing) {
    for (Direction cardinal : Direction.CARDINAL) {
      Coord wallCenter = origin.copy().translate(cardinal, 2).up();
      Coord left = wallCenter.copy().translate(cardinal.antiClockwise());
      Coord right = wallCenter.copy().translate(cardinal.clockwise());

      getPrimaryWall().fill(editor, RectSolid.newRect(left, right.copy().up(3)));
      right.translate(cardinal.clockwise());
      getPrimaryPillar().fill(editor, RectSolid.newRect(right, right.copy().up(3)));

      if (cardinal == facing) {
        createDoorway(origin, facing);
      } else {
        Coord windowCoord = origin.copy().translate(cardinal, 2).up(2);
        BlockType.GLASS_PANE.getBrush().stroke(editor, windowCoord);
      }
    }
  }

  private void createDoorway(Coord origin, Direction facing) {
    Coord doorway = origin.copy().translate(facing, 2).up(1);
    getPrimaryDoor().setFacing(facing).stroke(editor, doorway);

    Direction inward = facing.reverse();
    Coord torchCoord = doorway.copy().translate(inward).up(2);
    BlockType.TORCH.getBrush().setFacing(inward).stroke(editor, torchCoord);
  }

  public void createRoof(Coord origin) {
    Coord center = origin.copy().up(4);
    getSecondaryPillar().fill(editor, center.newRect(2));
    getSecondaryFloor().fill(editor, center.newRect(1));

    center.up();
    BlockType.OAK_FENCE.getBrush().fill(editor, center.newRect(2));
    BlockType.AIR.getBrush().fill(editor, center.newRect(1));
  }

  public void createSpiralStaircase(Coord origin, Coord base) {
    int height = base.getY() - origin.getY() + 1;
    new SpiralStairStep(editor, getPrimaryStair(), getPrimaryPillar()).withHeight(height).generate(origin);
  }
}
