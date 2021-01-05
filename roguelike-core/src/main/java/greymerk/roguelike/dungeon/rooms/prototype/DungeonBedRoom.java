package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.block.decorative.BedBlock;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.decorative.FlowerPotBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;
import com.github.srwaggon.roguelike.worldgen.block.decorative.TorchBlock;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class DungeonBedRoom extends DungeonBase {

  public DungeonBedRoom(RoomSetting roomSetting) {
    super(roomSetting);
  }

  public void pillar(WorldEditor editor, Cardinal dir, ThemeBase theme, final Coord base) {
    Coord start = new Coord(base);
    Coord end = new Coord(base);

    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getPillar());
    StairsBlock stair = theme.getSecondary().getStair();
    stair.setUpsideDown(true).setFacing(dir.reverse());
    end.translate(dir.reverse());
    stair.stroke(editor, end);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {
    Random rand = editor.getRandom();
    ThemeBase theme = settings.getTheme();

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal dir = entrances.get(0);

    start = new Coord(origin);
    end = new Coord(origin);

    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    start.translate(dir.reverse(), 4);
    end.translate(dir, 4);
    start.translate(Cardinal.DOWN);
    end.translate(Cardinal.UP, 4);

    RectHollow.newRect(start, end).fill(editor, theme.getPrimary().getWall(), false, true);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 1);
    end.translate(dir.clockwise(), 1);
    start.translate(dir.reverse(), 2);
    end.translate(dir, 2);

    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall());

    for (Cardinal o : dir.orthogonals()) {
      StairsBlock stair = theme.getSecondary().getStair();
      stair.setUpsideDown(true).setFacing(o.reverse());

      start = new Coord(origin);
      start.translate(o, 3);
      end = new Coord(start);
      start.translate(o.antiClockwise(), 2);
      end.translate(o.clockwise(), 2);

      RectSolid.newRect(start, end).fill(editor, stair);
      start.translate(Cardinal.UP, 2);
      end.translate(Cardinal.UP, 2);
      RectSolid.newRect(start, end).fill(editor, stair);
      start.translate(Cardinal.UP);
      end.translate(Cardinal.UP);
      RectSolid.newRect(start, end).fill(editor, theme.getPrimary().getWall());
      start.translate(o.reverse());
      end.translate(o.reverse());
      RectSolid.newRect(start, end).fill(editor, stair);
    }

    for (Cardinal o : dir.orthogonals()) {
      cursor = new Coord(origin);
      cursor.translate(o, 3);
      pillar(editor, o, theme, cursor);
      for (Cardinal p : o.orthogonals()) {
        Coord c = new Coord(cursor);
        c.translate(p, 3);
        pillar(editor, o, theme, c);
      }
    }

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 3);
    cursor.translate(dir.reverse(), 3);

    for (int i = 0; i < 3; ++i) {
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall());
      cursor.translate(dir, 3);
    }

    Cardinal side = rand.nextBoolean() ? dir.antiClockwise() : dir.clockwise();

    cursor = new Coord(origin);
    cursor.translate(dir, 3);
    BedBlock.bed().setColor(DyeColor.chooseRandom(editor.getRandom())).setFacing(dir.reverse()).stroke(editor, cursor);
    cursor.translate(side, 2);
    BlockType.BOOKSHELF.getBrush().stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, cursor);
    cursor.translate(side.reverse(), 3);
    cursor.translate(Cardinal.DOWN);
    StairsBlock stair = theme.getSecondary().getStair();
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    TorchBlock.torch().setFacing(Cardinal.UP).stroke(editor, cursor);

    side = dir.orthogonals()[rand.nextBoolean() ? 1 : 0];
    cursor = new Coord(origin);
    cursor.translate(dir);
    cursor.translate(side, 3);

    editor.getTreasureChestEditor().createChest(Dungeon.getLevel(cursor.getY()), cursor.add(Cardinal.UP), false, getRoomSetting().getChestType().orElse(ChestType.STARTER));

    cursor.translate(side.reverse(), 6);
    if (rand.nextBoolean()) {
      cursor.translate(Cardinal.UP);
      TorchBlock.torch().setFacing(Cardinal.UP).stroke(editor, cursor);
      cursor.translate(Cardinal.DOWN);
      cursor.translate(dir);
      BlockType.CRAFTING_TABLE.getBrush().stroke(editor, cursor);
    } else {
      BlockType.CRAFTING_TABLE.getBrush().stroke(editor, cursor);
      cursor.translate(dir);
      cursor.translate(Cardinal.UP);
      TorchBlock.torch().setFacing(Cardinal.UP).stroke(editor, cursor);
      cursor.translate(Cardinal.DOWN);
    }

    side = rand.nextBoolean() ? dir.antiClockwise() : dir.clockwise();
    cursor = new Coord(origin);
    cursor.translate(dir.reverse());
    cursor.translate(side, 3);
    if (rand.nextBoolean()) {
      cursor.translate(dir.reverse());
    }
    BlockType.FURNACE.getBrush().setFacing(side.reverse()).stroke(editor, cursor);
    editor.setItem(cursor, WorldEditor.FURNACE_FUEL_SLOT, new ItemStack(Items.COAL, 2 + rand.nextInt(3)));
    return this;
  }

  @Override
  public int getSize() {
    return 5;
  }

  @Override
  public boolean validLocation(WorldEditor editor, Cardinal dir, Coord pos) {
    Coord start;
    Coord end;

    start = new Coord(pos);
    end = new Coord(start);
    start.translate(dir.reverse(), 5);
    end.translate(dir, 5);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
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
