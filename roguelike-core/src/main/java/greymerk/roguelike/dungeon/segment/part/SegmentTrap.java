package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockMapper1_12;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;
import com.github.srwaggon.roguelike.worldgen.block.decorative.TorchBlock;
import com.github.srwaggon.roguelike.worldgen.block.decorative.VineBlock;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.Potion;
import greymerk.roguelike.treasure.loot.PotionForm;
import greymerk.roguelike.treasure.loot.TippedArrow;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock1_2;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentTrap extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    BlockBrush plate = BlockType.PRESSURE_PLATE_STONE.getBrush();
    BlockBrush wire = BlockType.REDSTONE_WIRE.getBrush();
    BlockBrush vine = VineBlock.vine();
    StairsBlock stair = theme.getPrimary().getStair();
    BlockBrush wall = theme.getPrimary().getWall();

    Cardinal[] orth = dir.orthogonals();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, vine);
    start.translate(dir);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, wall);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir, 3);
    SingleBlockBrush.AIR.stroke(editor, cursor);

    for (Cardinal side : orth) {
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(side);
      stair.setUpsideDown(false).setFacing(side.reverse()).stroke(editor, cursor);
      cursor.translate(Cardinal.UP, 2);
      stair.setUpsideDown(true).setFacing(side.reverse()).stroke(editor, cursor);
    }

    start = new Coord(origin);
    end = new Coord(start);
    start.translate(dir);
    end.translate(dir.reverse());

    RectSolid.newRect(start, end).fill(editor, plate);

    end.translate(Cardinal.DOWN, 2);
    start = new Coord(end);
    start.translate(dir, 3);

    RectSolid.newRect(start, end).fill(editor, wire);

    cursor = new Coord(start);
    cursor.translate(dir, 2);
    TorchBlock.redstone().setFacing(dir).stroke(editor, cursor);
    cursor.translate(Cardinal.UP, 2);
    TorchBlock.redstone().setFacing(Cardinal.UP).stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    placeTrap(editor, rand, dir, cursor);
  }

  private void placeTrap(WorldEditor editor, Random rand, Cardinal dir, Coord cursor) {
    Cardinal towardsCenter = dir.reverse();
    BlockType.DISPENSER.getBrush().setFacing(towardsCenter).stroke(editor, cursor);

    for (int i = 0; i < 5; i++) {
      int amount = rand.nextInt(5) + 1;
      ItemStack arrows = TippedArrow.getHarmful(rand, amount);
      editor.setItem(cursor, rand.nextInt(9), arrows);
    }

    editor.setItem(cursor, 5, getPayload(rand));

    unblockDispenser(editor, cursor, towardsCenter);
  }

  private void unblockDispenser(WorldEditor editor, Coord cursor, Cardinal towardsCenter) {
    SingleBlockBrush.AIR.stroke(editor, cursor.translate(towardsCenter));
    SingleBlockBrush.AIR.stroke(editor, cursor.translate(towardsCenter));
    SingleBlockBrush.AIR.stroke(editor, cursor.translate(towardsCenter));
  }

  private ItemStack getPayload(Random rand) {

    switch (rand.nextInt(3)) {
      default:
      case 0:
        return getItem(BlockType.TNT);
      case 1:
        return Potion.getSpecific(PotionForm.SPLASH, Potion.POISON, false, false);
      case 2:
        return Potion.getSpecific(PotionForm.SPLASH, Potion.HARM, false, false);
    }
  }

  private ItemStack getItem(BlockType type) {
    MetaBlock1_2 block = BlockMapper1_12.map(type);
    Block b = block.getBlock();
    Item i = Item.getItemFromBlock(b);
    return new ItemStack(i);
  }
}
