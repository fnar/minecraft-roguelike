package greymerk.roguelike.dungeon.segment.part;

import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.Potion;
import greymerk.roguelike.treasure.loot.PotionForm;
import greymerk.roguelike.treasure.loot.TippedArrow;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.redstone.Dispenser;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentTrap extends SegmentBase {

  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Cardinal dir, ThemeBase theme, Coord origin) {

    MetaBlock plate = BlockType.get(BlockType.PRESSURE_PLATE_STONE);
    MetaBlock wire = BlockType.get(BlockType.REDSTONE_WIRE);
    MetaBlock vine = BlockType.get(BlockType.VINE);
    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory wall = theme.getPrimary().getWall();

    Cardinal[] orth = dir.orthogonal();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(dir, 2);
    end = new Coord(start);
    start.translate(orth[0]);
    end.translate(orth[1]);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, vine);
    start.translate(dir);
    end.translate(dir);
    RectSolid.fill(editor, rand, start, end, wall);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir, 3);
    air.set(editor, cursor);

    for (Cardinal side : orth) {
      cursor = new Coord(origin);
      cursor.translate(dir, 2);
      cursor.translate(side);
      stair.setOrientation(side.reverse(), false).set(editor, cursor);
      cursor.translate(Cardinal.UP, 2);
      stair.setOrientation(side.reverse(), true).set(editor, cursor);
    }

    start = new Coord(origin);
    end = new Coord(start);
    start.translate(dir);
    end.translate(dir.reverse());

    RectSolid.fill(editor, rand, start, end, plate);

    end.translate(Cardinal.DOWN, 2);
    start = new Coord(end);
    start.translate(dir, 3);

    RectSolid.fill(editor, rand, start, end, wire);

    cursor = new Coord(start);
    cursor.translate(dir, 2);
    Torch.generate(editor, Torch.REDSTONE, dir, cursor);
    cursor.translate(Cardinal.UP, 2);
    Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
    cursor.translate(Cardinal.UP);
    Dispenser.generate(editor, dir.reverse(), cursor);

    for (int i = 0; i < 5; i++) {
      int amount = rand.nextInt(5) + 1;
      ItemStack arrows = TippedArrow.getHarmful(rand, amount);
      Dispenser.add(editor, cursor, rand.nextInt(9), arrows);
    }

    Dispenser.add(editor, cursor, 5, getPayload(rand));
  }

  private ItemStack getPayload(Random rand) {

    switch (rand.nextInt(3)) {
      case 0:
        return BlockType.getItem(BlockType.TNT);
      case 1:
        return Potion.getSpecific(PotionForm.SPLASH, Potion.POISON, false, false);
      case 2:
        return Potion.getSpecific(PotionForm.SPLASH, Potion.HARM, false, false);
      default:
        return BlockType.getItem(BlockType.TNT);
    }
  }
}
