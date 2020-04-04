package greymerk.roguelike.dungeon.rooms.prototype;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.Bed;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;

import static greymerk.roguelike.treasure.Treasure.STARTER;
import static greymerk.roguelike.treasure.Treasure.createChest;

@EqualsAndHashCode
public class DungeonBedRoom extends DungeonBase {

  public void pillar(IWorldEditor editor, Random rand, Cardinal dir, ITheme theme, final Coord base) {
    Coord start = new Coord(base);
    Coord end = new Coord(base);

    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getPillar());
    IStair stair = getStairs(theme);
    stair.setOrientation(dir.reverse(), true);
    end.add(dir.reverse());
    stair.set(editor, end);
  }

  @Override
  public IDungeonRoom generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    ITheme theme = settings.getTheme();

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal dir = entrances[0];

    start = new Coord(origin);
    end = new Coord(origin);

    start.add(dir.left(), 4);
    end.add(dir.right(), 4);
    start.add(dir.reverse(), 4);
    end.add(dir, 4);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.UP, 4);

    RectHollow.fill(editor, rand, start, end, theme.getPrimary().getWall(), false, true);

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(dir.left(), 1);
    end.add(dir.right(), 1);
    start.add(dir.reverse(), 2);
    end.add(dir, 2);

    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());

    for (Cardinal o : dir.orthogonal()) {
      IStair stair = getStairs(theme);
      stair.setOrientation(o.reverse(), true);

      start = new Coord(origin);
      start.add(o, 3);
      end = new Coord(start);
      start.add(o.left(), 2);
      end.add(o.right(), 2);

      RectSolid.fill(editor, rand, start, end, stair);
      start.add(Cardinal.UP, 2);
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, stair);
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall());
      start.add(o.reverse());
      end.add(o.reverse());
      RectSolid.fill(editor, rand, start, end, stair, true, true);
    }

    for (Cardinal o : dir.orthogonal()) {
      cursor = new Coord(origin);
      cursor.add(o, 3);
      pillar(editor, rand, o, theme, cursor);
      for (Cardinal p : o.orthogonal()) {
        Coord c = new Coord(cursor);
        c.add(p, 3);
        pillar(editor, rand, o, theme, c);
      }
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 3);
    cursor.add(dir.reverse(), 3);

    for (int i = 0; i < 3; ++i) {
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.add(dir.left(), 2);
      end.add(dir.right(), 2);
      RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());
      cursor.add(dir, 3);
    }

    Cardinal side = rand.nextBoolean() ? dir.left() : dir.right();

    cursor = new Coord(origin);
    cursor.add(dir, 3);
    cursor.add(side, 1);
    DyeColor color = DyeColor.values()[rand.nextInt(DyeColor.values().length)];
    Bed.generate(editor, dir.reverse(), cursor, color);
    cursor.add(side);
    BlockType.get(BlockType.SHELF).set(editor, cursor);
    cursor.add(Cardinal.UP);
    FlowerPot.generate(editor, rand, cursor);
    cursor.add(side.reverse(), 3);
    cursor.add(Cardinal.DOWN);
    IStair stair = getStairs(theme);
    stair.setOrientation(dir.reverse(), true);
    stair.set(editor, cursor);
    cursor.add(Cardinal.UP);
    Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);

    side = dir.orthogonal()[rand.nextBoolean() ? 1 : 0];
    cursor = new Coord(origin);
    cursor.add(dir);
    cursor.add(side, 3);

    createChest(editor, rand, Dungeon.getLevel(cursor.getY()), cursor.newCoord(Cardinal.UP), false, STARTER);

    cursor.add(side.reverse(), 6);
    if (rand.nextBoolean()) {
      cursor.add(Cardinal.UP);
      Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
      cursor.add(Cardinal.DOWN);
      cursor.add(dir);
      BlockType.get(BlockType.CRAFTING_TABLE).set(editor, cursor);
    } else {
      BlockType.get(BlockType.CRAFTING_TABLE).set(editor, cursor);
      cursor.add(dir);
      cursor.add(Cardinal.UP);
      Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
      cursor.add(Cardinal.DOWN);
    }

    side = rand.nextBoolean() ? dir.left() : dir.right();
    cursor = new Coord(origin);
    cursor.add(dir.reverse());
    cursor.add(side, 3);
    if (rand.nextBoolean()) {
      cursor.add(dir.reverse());
    }
    Furnace.generate(editor, new ItemStack(Items.COAL, 2 + rand.nextInt(3)), true, side.reverse(), cursor);


    return this;
  }

  private static IStair getStairs(ITheme theme) {
    return theme.getSecondary().getStair();
  }

  @Override
  public int getSize() {
    return 5;
  }

  @Override
  public boolean validLocation(IWorldEditor editor, Cardinal dir, Coord pos) {
    Coord start;
    Coord end;

    start = new Coord(pos);
    end = new Coord(start);
    start.add(dir.reverse(), 5);
    end.add(dir, 5);
    start.add(dir.left(), 5);
    end.add(dir.right(), 5);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.UP, 3);

    for (Coord c : new RectHollow(start, end)) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }
}
