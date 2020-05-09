package greymerk.roguelike.dungeon.rooms.prototype;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.Firework;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.redstone.Comparator;
import greymerk.roguelike.worldgen.redstone.Dispenser;
import greymerk.roguelike.worldgen.redstone.Dropper;
import greymerk.roguelike.worldgen.redstone.Hopper;
import greymerk.roguelike.worldgen.redstone.Lever;
import greymerk.roguelike.worldgen.redstone.Repeater;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonFirework extends DungeonBase {

  public DungeonFirework(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    MetaBlock breadboard = ColorBlock.get(ColorBlock.CLAY, DyeColor.GREEN);

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal dir = entrances[0];
    start = new Coord(x, y, z);
    end = new Coord(start);
    start.translate(dir.reverse(), 9);
    end.translate(dir, 9);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.UP, 3);
    RectHollow.fill(editor, rand, start, end, ColorBlock.get(ColorBlock.CLAY, DyeColor.ORANGE), false, true);

    start = new Coord(x, y, z);
    start.translate(dir.antiClockwise(), 2);
    end = new Coord(start);
    start.translate(dir.reverse(), 3);
    end.translate(dir, 7);
    end.translate(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, breadboard);

    start.translate(dir.clockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.fill(editor, rand, start, end, breadboard, true, true);

    start.translate(dir.clockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.fill(editor, rand, start, end, breadboard, true, true);

    cursor = new Coord(x, y, z);
    cursor.translate(dir.antiClockwise(), 2);

    launcher(editor, rand, dir, cursor);
    cursor.translate(dir.clockwise(), 2);
    launcher(editor, rand, dir, cursor);
    cursor.translate(dir.clockwise(), 2);
    launcher(editor, rand, dir, cursor);
    cursor.translate(dir, 6);
    launcher(editor, rand, dir, cursor);
    cursor.translate(dir.antiClockwise(), 2);
    launcher(editor, rand, dir, cursor);
    cursor.translate(dir.antiClockwise(), 2);
    launcher(editor, rand, dir, cursor);

    start = new Coord(x, y, z);
    start.translate(dir, 4);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(dir, 2);
    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR), true, true);

    cursor = new Coord(x, y, z);
    cursor.translate(dir, 2);
    Repeater.generate(editor, rand, dir, 0, cursor);
    cursor.translate(dir.antiClockwise(), 2);
    Repeater.generate(editor, rand, dir, 0, cursor);
    cursor.translate(dir.clockwise(), 4);
    Repeater.generate(editor, rand, dir, 0, cursor);

    cursor = new Coord(x, y, z);
    cursor.translate(dir.reverse(), 3);
    cursor.translate(dir.antiClockwise());
    Repeater.generate(editor, rand, dir.antiClockwise(), 0, cursor);
    cursor.translate(dir.clockwise(), 2);
    Repeater.generate(editor, rand, dir.clockwise(), 0, cursor);

    MetaBlock wire = BlockType.get(BlockType.REDSTONE_WIRE);

    start = new Coord(x, y, z);
    start.translate(Cardinal.DOWN, 2);
    start.translate(dir.clockwise());
    start.translate(dir.reverse(), 2);
    end = new Coord(start);
    end.translate(dir.antiClockwise(), 5);
    end.translate(dir.reverse(), 5);
    end.translate(Cardinal.DOWN, 2);
    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.COBBLESTONE), true, true);

    cursor = new Coord(x, y, z);
    cursor.translate(dir.reverse(), 3);
    cursor.translate(Cardinal.DOWN);
    Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
    cursor.translate(Cardinal.DOWN);
    breadboard.set(editor, cursor);
    cursor.translate(dir.antiClockwise());
    Torch.generate(editor, Torch.REDSTONE_UNLIT, dir.antiClockwise(), cursor);
    cursor.translate(dir.antiClockwise());
    wire.set(editor, cursor);
    cursor.translate(dir.reverse());
    wire.set(editor, cursor);
    cursor.translate(dir.reverse());
    wire.set(editor, cursor);
    cursor.translate(dir.clockwise());
    wire.set(editor, cursor);
    cursor.translate(dir.clockwise());
    wire.set(editor, cursor);
    cursor.translate(dir);
    Repeater.generate(editor, rand, dir, 4, true, cursor);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir.reverse());
    ColorBlock.get(ColorBlock.CLAY, DyeColor.RED).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    Lever.generate(editor, Cardinal.UP, cursor, true);

    MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);
    cursor = new Coord(x, y, z);
    cursor.translate(dir.reverse(), 5);
    cursor.translate(Cardinal.UP, 3);
    glowstone.set(editor, cursor);
    cursor.translate(dir, 4);
    glowstone.set(editor, cursor);
    cursor.translate(dir, 6);
    glowstone.set(editor, cursor);

    return this;
  }


  private void launcher(IWorldEditor editor, Random rand, Cardinal dir, Coord pos) {
    Coord cursor = new Coord(pos);
    BlockType.get(BlockType.REDSTONE_WIRE).set(editor, cursor);
    cursor.translate(dir.reverse());
    BlockType.get(BlockType.REDSTONE_WIRE).set(editor, cursor);
    cursor.translate(dir.reverse());
    Repeater.generate(editor, rand, dir, 0, cursor);
    cursor.translate(dir.reverse());
    cursor.translate(Cardinal.UP);

    Dropper dropper = new Dropper();
    dropper.generate(editor, Cardinal.UP, cursor);
    for (int i = 0; i < 8; ++i) {
      ItemStack stick = new ItemStack(Items.STICK, 1);
      Loot.setItemName(stick, Integer.toString(i));
      Loot.setItemLore(stick, "Random logic unit", TextFormat.DARKGRAY);
      dropper.add(editor, cursor, i, stick);
    }
    dropper.add(editor, cursor, 8, new ItemStack(Items.WOODEN_HOE));

    cursor.translate(Cardinal.UP);
    Hopper.generate(editor, Cardinal.DOWN, cursor);
    cursor.translate(dir);
    Comparator.generate(editor, rand, dir, false, cursor);
    cursor.translate(dir);
    BlockType.get(BlockType.REDSTONE_WIRE).set(editor, cursor);
    cursor.translate(dir);
    BlockType.get(BlockType.REDSTONE_WIRE).set(editor, cursor);
    cursor.translate(dir);

    Coord top = new Coord(pos.getX(), 80, pos.getZ());
    while (top.getY() > pos.getY()) {
      top.translate(Cardinal.DOWN);
      if (editor.getBlock(top).getMaterial().isSolid()) {
        break;
      }
    }

    if (top.getY() >= 100) {
      return;
    }

    Coord start = new Coord(cursor);
    start.translate(Cardinal.UP);


    start.translate(dir);
    Coord end = new Coord(start);

    MetaBlock breadboard = ColorBlock.get(ColorBlock.CLAY, DyeColor.GREEN);


    boolean torch = false;
    while (end.getY() < top.getY()) {
      if (torch) {
        Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
      } else {
        breadboard.set(editor, cursor);
      }
      torch = !torch;
      cursor.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
    }

    if (torch) {
      cursor.translate(Cardinal.DOWN);
    }

    Dispenser.generate(editor, Cardinal.UP, cursor);
    for (int i = 0; i < 9; i++) {
      Dispenser.add(editor, cursor, i, Firework.get(rand, 16 + rand.nextInt(16)));
    }

    cursor.translate(Cardinal.UP);
    MetaBlock cob = BlockType.get(BlockType.COBBLESTONE);
    RectSolid.fill(editor, rand, start, end, cob, true, true);
    start.translate(dir.reverse(), 2);
    end.translate(dir.reverse(), 2);
    RectSolid.fill(editor, rand, start, end, cob, true, true);
    start.translate(dir);
    end.translate(dir);
    Coord above = new Coord(end);
    above.translate(Cardinal.UP, 10);
    MetaBlock air = BlockType.get(BlockType.AIR);
    for (Coord c : new RectSolid(cursor, above)) {
      if (editor.getBlock(c).getMaterial().isSolid()) {
        air.set(editor, c);
      }
    }
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    RectSolid.fill(editor, rand, start, end, cob, true, true);
    start.translate(dir.clockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.fill(editor, rand, start, end, cob, true, true);
  }


  @Override
  public int getSize() {
    return 10;
  }

  @Override
  public boolean validLocation(IWorldEditor editor, Cardinal dir, Coord pos) {
    Coord start;
    Coord end;

    start = new Coord(pos);
    end = new Coord(start);
    start.translate(dir.reverse(), 9);
    end.translate(dir, 9);
    Cardinal[] orth = dir.orthogonal();
    start.translate(orth[0], 5);
    end.translate(orth[1], 5);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.UP, 3);

    for (Coord c : new RectHollow(start, end)) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }

}
