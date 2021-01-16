package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.decorative.TorchBlock;
import com.github.srwaggon.minecraft.block.decorative.VineBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;
import com.github.srwaggon.minecraft.item.Arrow;
import com.github.srwaggon.minecraft.item.Potion;
import com.github.srwaggon.minecraft.item.RldItemStack;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentTrap extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {

    BlockBrush plate = BlockType.PRESSURE_PLATE_STONE.getBrush();
    BlockBrush wire = BlockType.REDSTONE_WIRE.getBrush();
    BlockBrush vine = VineBlock.vine();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush wall = theme.getPrimary().getWall();

    Direction[] orth = dir.orthogonals();

    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, vine);
    start.translate(dir);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, wall);

    cursor = origin.copy();
    cursor.translate(Direction.UP);
    cursor.translate(dir, 3);
    SingleBlockBrush.AIR.stroke(editor, cursor);

    for (Direction side : orth) {
      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.translate(Direction.UP, 2);
      stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);
    }

    start = origin.copy();
    end = start.copy();
    start.translate(dir);
    end.translate(dir.reverse());

    RectSolid.newRect(start, end).fill(editor, plate);

    end.translate(Direction.DOWN, 2);
    start = end.copy();
    start.translate(dir, 3);

    RectSolid.newRect(start, end).fill(editor, wire);

    cursor = start.copy();
    cursor.translate(dir, 2);
    TorchBlock.redstone().setFacing(dir).stroke(editor, cursor);
    cursor.translate(Direction.UP, 2);
    TorchBlock.redstone().setFacing(Direction.UP).stroke(editor, cursor);
    cursor.translate(Direction.UP);
    placeTrap(editor, rand, dir, cursor);
  }

  private void placeTrap(WorldEditor editor, Random rand, Direction dir, Coord cursor) {
    Direction towardsCenter = dir.reverse();
    BlockType.DISPENSER.getBrush().setFacing(towardsCenter).stroke(editor, cursor);

    for (int i = 0; i < 5; i++) {
      int slot = rand.nextInt(9);
      RldItemStack payload = Arrow.newRandomHarmful(rand).asItemStack();
      editor.setItem(cursor, slot, payload);
    }

    editor.setItem(cursor, 5, choosePayload(rand));

    unblockDispenser(editor, cursor, towardsCenter);
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
        return BlockType.TNT.asItemStack();
      case 1:
        return Potion.newPotion().withType(Potion.Type.POISON).withForm(Potion.Form.SPLASH).asItemStack();
      case 2:
        return Potion.newPotion().withType(Potion.Type.HARMING).withForm(Potion.Form.SPLASH).asItemStack();
    }
  }

}
