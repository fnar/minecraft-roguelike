package greymerk.roguelike.dungeon.segment.part;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.TorchBlock;
import com.github.fnar.minecraft.block.decorative.VineBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.TripwireHookBlock;
import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.minecraft.item.Potion;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentTrap extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, DungeonLevel level, Direction dir, Theme theme, Coord origin) {

    placeFacade(editor, dir, theme, origin);

    placeWiring(editor, dir, origin);

    placeDispenser(editor, dir, origin);
  }

  private void placeFacade(WorldEditor editor, Direction dir, Theme theme, Coord origin) {
    // create a 3x3 wall 3 blocks from center
    Coord facadeCorner0 = origin.copy().translate(dir.left());
    Coord facadeCorner1 = origin.copy().translate(dir.right()).up(2);
    RectSolid facade = RectSolid.newRect(facadeCorner0, facadeCorner1);
    facade.translate(dir, 3);
    getPrimaryWalls(theme).fill(editor, facade);

    // cover in vines
    facade.translate(dir.reverse(), 1);
    VineBlock.vine().fill(editor, facade);

    // stairs on corners
    StairsBlock stair = getPrimaryStairs(theme);
    for (Direction side : dir.orthogonals()) {
      Coord cursor = origin.copy()
          .translate(dir, 2)
          .translate(side);
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.up(2);
      stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);
    }

    // create hole in center
    Coord opening = origin.copy().up().translate(dir, 3);
    SingleBlockBrush.AIR.stroke(editor, opening);
  }

  private void placeWiring(WorldEditor editor, Direction outward, Coord origin) {
    if (editor.isBlockOfTypeAt(BlockType.TRIPWIRE, origin)) {
      placeTripwireTrigger(editor, outward, origin);
    }
    if (editor.isBlockOfTypeAt(BlockType.PRESSURE_PLATE_STONE, origin)) {
      placePressurePlateTrigger(editor, outward, origin);
    }

    if (editor.getRandom().nextDouble() < 0.1) {
      placeTripwireTrigger(editor, outward, origin);
    } else {
      placePressurePlateTrigger(editor, outward, origin);
    }
  }

  private void placeTripwireTrigger(WorldEditor editor, Direction outward, Coord origin) {
    Coord start = origin.copy().translate(outward, 2);
    Coord end = origin.copy().translate(outward.reverse(), 2);
    RectSolid wire = RectSolid.newRect(start, end);
    BlockType.TRIPWIRE.getBrush().fill(editor, wire);
    TripwireHookBlock tripwireHook = new TripwireHookBlock().withIsAttached(true);
    tripwireHook.setFacing(outward).stroke(editor, start.translate(outward));
    tripwireHook.setFacing(outward.reverse()).stroke(editor, end.translate(outward.reverse()));
  }

  private void placePressurePlateTrigger(WorldEditor editor, Direction outward, Coord origin) {
    Coord platesStart = origin.copy().translate(outward);
    Coord platesEnd = origin.copy().translate(outward.reverse());
    RectSolid pressurePlates = RectSolid.newRect(platesStart, platesEnd);
    BlockType.PRESSURE_PLATE_STONE.getBrush().fill(editor, pressurePlates);

    placeRedstoneWiresUnderneath(editor, outward, origin);

    placeRedstoneTorchesUpward(editor, outward, origin);
  }

  private void placeRedstoneWiresUnderneath(WorldEditor editor, Direction outward, Coord origin) {
    Coord wiringStart = origin.copy().down(2).translate(outward, 2);
    Coord wiringEnd = origin.copy().down(2).translate(outward.reverse(), 2);
    RectSolid redstoneWiring = RectSolid.newRect(wiringStart, wiringEnd);
    BlockType.REDSTONE_WIRE.getBrush().fill(editor, redstoneWiring);
  }

  private void placeRedstoneTorchesUpward(WorldEditor editor, Direction outward, Coord origin) {
    Coord redstoneTorch = origin.copy()
        .translate(outward, 4);
    TorchBlock.redstone().stroke(editor, redstoneTorch);
    redstoneTorch.down(2);
    TorchBlock.redstone().setFacing(outward).stroke(editor, redstoneTorch);
  }

  private void placeDispenser(WorldEditor editor, Direction outward, Coord origin) {
    Coord dispenser = origin.copy()
        .translate(outward, 4)
        .up();

    Random random = editor.getRandom();
    Direction towardsCenter = outward.reverse();
    BlockType.DISPENSER.getBrush().setFacing(towardsCenter).stroke(editor, dispenser);

    for (int i = 0; i < 5; i++) {
      int slot = random.nextInt(9);
      RldItemStack payload = Arrow.newRandomHarmful(random).asStack().withCount(random.nextInt(4));
      editor.setItem(dispenser, slot, payload);
    }

    editor.setItem(dispenser, 5, choosePayload(random));

    unblockDispenser(editor, dispenser, towardsCenter);
  }

  private void unblockDispenser(WorldEditor editor, Coord cursor, Direction towardsCenter) {
    SingleBlockBrush.AIR.stroke(editor, cursor.translate(towardsCenter));
    SingleBlockBrush.AIR.stroke(editor, cursor.translate(towardsCenter));
    SingleBlockBrush.AIR.stroke(editor, cursor.translate(towardsCenter));
  }

  private RldItemStack choosePayload(Random rand) {
    switch (rand.nextInt(3)) {
      default:
      case 0:
        return BlockType.TNT.asItem().asStack();
      case 1:
        return Potion.newPotion().withEffect(Potion.Effect.POISON).withForm(Potion.Form.SPLASH).asStack();
      case 2:
        return Potion.newPotion().withEffect(Potion.Effect.HARMING).withForm(Potion.Form.SPLASH).asStack();
    }
  }

}
