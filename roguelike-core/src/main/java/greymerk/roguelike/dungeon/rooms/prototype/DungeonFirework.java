package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.redstone.LeverBlock;
import com.github.srwaggon.roguelike.worldgen.block.decorative.TorchBlock;
import com.github.srwaggon.roguelike.worldgen.block.redstone.ComparatorBlock;
import com.github.srwaggon.roguelike.worldgen.block.redstone.RepeaterBlock;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.Firework;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedHardenedClay;

public class DungeonFirework extends DungeonBase {

  public DungeonFirework(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    BlockBrush breadboard = stainedHardenedClay().setColor(DyeColor.GREEN);

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal dir = entrances.get(0);
    start = new Coord(x, y, z);
    end = new Coord(start);
    start.translate(dir.reverse(), 9);
    end.translate(dir, 9);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.UP, 3);
    RectHollow.fill(editor, start, end, stainedHardenedClay().setColor(DyeColor.ORANGE), false, true);

    start = new Coord(x, y, z);
    start.translate(dir.antiClockwise(), 2);
    end = new Coord(start);
    start.translate(dir.reverse(), 3);
    end.translate(dir, 7);
    end.translate(Cardinal.UP);
    RectSolid.newRect(start, end).fill(editor, breadboard);

    start.translate(dir.clockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, breadboard);

    start.translate(dir.clockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, breadboard);

    cursor = new Coord(x, y, z);
    cursor.translate(dir.antiClockwise(), 2);

    launcher(editor, dir, cursor);
    cursor.translate(dir.clockwise(), 2);
    launcher(editor, dir, cursor);
    cursor.translate(dir.clockwise(), 2);
    launcher(editor, dir, cursor);
    cursor.translate(dir, 6);
    launcher(editor, dir, cursor);
    cursor.translate(dir.antiClockwise(), 2);
    launcher(editor, dir, cursor);
    cursor.translate(dir.antiClockwise(), 2);
    launcher(editor, dir, cursor);

    start = new Coord(x, y, z);
    start.translate(dir, 4);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(dir, 2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);

    cursor = new Coord(x, y, z);
    cursor.translate(dir, 2);
    RepeaterBlock.repeater().setFacing(dir).stroke(editor, cursor);
    cursor.translate(dir.antiClockwise(), 2);
    RepeaterBlock.repeater().setFacing(dir).stroke(editor, cursor);
    cursor.translate(dir.clockwise(), 4);
    RepeaterBlock.repeater().setFacing(dir).stroke(editor, cursor);

    cursor = new Coord(x, y, z);
    cursor.translate(dir.reverse(), 3);
    cursor.translate(dir.antiClockwise());
    RepeaterBlock.repeater().setFacing(dir.antiClockwise()).stroke(editor, cursor);
    cursor.translate(dir.clockwise(), 2);
    RepeaterBlock.repeater().setFacing(dir.clockwise()).stroke(editor, cursor);

    BlockBrush wire = BlockType.REDSTONE_WIRE.getBrush();

    start = new Coord(x, y, z);
    start.translate(Cardinal.DOWN, 2);
    start.translate(dir.clockwise());
    start.translate(dir.reverse(), 2);
    end = new Coord(start);
    end.translate(dir.antiClockwise(), 5);
    end.translate(dir.reverse(), 5);
    end.translate(Cardinal.DOWN, 2);
    RectSolid.newRect(start, end).fill(editor, BlockType.COBBLESTONE.getBrush());

    cursor = new Coord(x, y, z);
    cursor.translate(dir.reverse(), 3);
    cursor.translate(Cardinal.DOWN);
    TorchBlock.redstone().setFacing(Cardinal.UP).stroke(editor, cursor);
    cursor.translate(Cardinal.DOWN);
    breadboard.stroke(editor, cursor);
    cursor.translate(dir.antiClockwise());
    TorchBlock.redstone().setFacing(dir.antiClockwise()).stroke(editor, cursor);
    cursor.translate(dir.antiClockwise());
    wire.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    wire.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    wire.stroke(editor, cursor);
    cursor.translate(dir.clockwise());
    wire.stroke(editor, cursor);
    cursor.translate(dir.clockwise());
    wire.stroke(editor, cursor);
    cursor.translate(dir);
    RepeaterBlock.repeater()
        .setDelay(RepeaterBlock.Delay.FOUR)
        .setPowered(true)
        .setFacing(dir)
        .stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    cursor.translate(dir.reverse());
    stainedHardenedClay().setColor(DyeColor.RED).stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    LeverBlock.lever().setActive(true).setFacing(Cardinal.UP).stroke(editor, cursor);

    BlockBrush glowstone = BlockType.GLOWSTONE.getBrush();
    cursor = new Coord(x, y, z);
    cursor.translate(dir.reverse(), 5);
    cursor.translate(Cardinal.UP, 3);
    glowstone.stroke(editor, cursor);
    cursor.translate(dir, 4);
    glowstone.stroke(editor, cursor);
    cursor.translate(dir, 6);
    glowstone.stroke(editor, cursor);

    return this;
  }


  private void launcher(WorldEditor editor, Cardinal dir, Coord pos) {
    Coord cursor = new Coord(pos);
    BlockType.REDSTONE_WIRE.getBrush().stroke(editor, cursor);
    cursor.translate(dir.reverse());
    BlockType.REDSTONE_WIRE.getBrush().stroke(editor, cursor);
    cursor.translate(dir.reverse());
    RepeaterBlock.repeater().setFacing(dir).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    cursor.translate(Cardinal.UP);

    BlockType.DROPPER.getBrush().setFacing(Cardinal.UP).stroke(editor, cursor);
    for (int i = 0; i < 8; ++i) {
      ItemStack stick = new ItemStack(Items.STICK, 1);
      Loot.setItemName(stick, Integer.toString(i));
      Loot.setItemLore(stick, "Random logic unit", TextFormat.DARKGRAY);
      editor.setItem(cursor, i, stick);
    }
    editor.setItem(cursor, 8, new ItemStack(Items.WOODEN_HOE));

    cursor.translate(Cardinal.UP);
    BlockType.HOPPER.getBrush().setFacing(Cardinal.DOWN).stroke(editor, cursor);
    cursor.translate(dir);
    ComparatorBlock.comparator()
        .setFacing(dir)
        .stroke(editor, cursor);
    cursor.translate(dir);
    BlockType.REDSTONE_WIRE.getBrush().stroke(editor, cursor);
    cursor.translate(dir);
    BlockType.REDSTONE_WIRE.getBrush().stroke(editor, cursor);
    cursor.translate(dir);

    Coord top = new Coord(pos.getX(), 80, pos.getZ());
    while (top.getY() > pos.getY()) {
      top.translate(Cardinal.DOWN);
      if (editor.isSolidBlock(top)) {
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

    BlockBrush breadboard = stainedHardenedClay().setColor(DyeColor.GREEN);


    boolean torch = false;
    while (end.getY() < top.getY()) {
      if (torch) {
        TorchBlock.redstone().setFacing(Cardinal.UP).stroke(editor, cursor);
      } else {
        breadboard.stroke(editor, cursor);
      }
      torch = !torch;
      cursor.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
    }

    if (torch) {
      cursor.translate(Cardinal.DOWN);
    }

    BlockType.DISPENSER.getBrush().setFacing(Cardinal.UP).stroke(editor, cursor);
    for (int i = 0; i < 9; i++) {
      editor.setItem(cursor, i, Firework.get(editor.getRandom(), 16 + editor.getRandom().nextInt(16)));
    }

    cursor.translate(Cardinal.UP);
    BlockBrush cob = BlockType.COBBLESTONE.getBrush();
    RectSolid.newRect(start, end).fill(editor, cob);
    start.translate(dir.reverse(), 2);
    end.translate(dir.reverse(), 2);
    RectSolid.newRect(start, end).fill(editor, cob);
    start.translate(dir);
    end.translate(dir);
    Coord above = new Coord(end);
    above.translate(Cardinal.UP, 10);
    for (Coord c : new RectSolid(cursor, above)) {
      if (editor.isSolidBlock(c)) {
        SingleBlockBrush.AIR.stroke(editor, c);
      }
    }
    start.translate(dir.antiClockwise());
    end.translate(dir.antiClockwise());
    RectSolid.newRect(start, end).fill(editor, cob);
    start.translate(dir.clockwise(), 2);
    end.translate(dir.clockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, cob);
  }


  @Override
  public int getSize() {
    return 10;
  }

  @Override
  public boolean validLocation(WorldEditor editor, Cardinal dir, Coord pos) {
    Coord start;
    Coord end;

    start = new Coord(pos);
    end = new Coord(start);
    start.translate(dir.reverse(), 9);
    end.translate(dir, 9);
    Cardinal[] orthogonal = dir.orthogonals();
    start.translate(orthogonal[0], 5);
    end.translate(orthogonal[1], 5);
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
