package greymerk.roguelike.dungeon.rooms.prototype;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.ChestPlacementException;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.Record;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.BrewingStand;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Trapdoor;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonBTeam extends DungeonBase {

  public DungeonBTeam(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal[] entrances) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = new MetaStair(StairType.SPRUCE);
    MetaBlock log = Log.get(Wood.OAK, Cardinal.UP);
    MetaBlock stonebrick = BlockType.get(BlockType.STONE_BRICK);
    MetaBlock cyan = ColorBlock.get(ColorBlock.CLAY, DyeColor.CYAN);
    MetaBlock slab = Slab.get(Slab.STONE, false, true, true);
    MetaBlock cobble = BlockType.get(BlockType.COBBLESTONE);
    MetaBlock lamp = BlockType.get(BlockType.REDSTONE_LAMP);

    Cardinal dir = entrances[0];

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(dir, 5);
    end.translate(dir.reverse(), 4);
    start.translate(dir.antiClockwise(), 6);
    end.translate(dir.clockwise(), 6);
    end.translate(Cardinal.UP, 5);
    start.translate(Cardinal.DOWN);
    RectHollow.fill(editor, rand, start, end, stonebrick);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir, 4);
    end.translate(dir.reverse(), 3);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    RectSolid.fill(editor, rand, start, end, cobble);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir, 3);
    end.translate(dir.reverse(), 2);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    RectSolid.fill(editor, rand, start, end, cyan);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir, 2);
    end.translate(dir.reverse(), 1);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 3);
    RectSolid.fill(editor, rand, start, end, slab);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 4);
    logWall(editor, rand, dir, cursor);
    cursor.translate(dir, 9);
    logWall(editor, rand, dir.reverse(), cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.antiClockwise(), 6);
    tvWall(editor, rand, dir.antiClockwise(), cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.clockwise(), 6);
    bWall(editor, rand, dir.clockwise(), cursor);

    table(editor, rand, dir, origin);

    start = new Coord(origin);
    start.translate(dir.reverse(), 4);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, cobble);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 4);
    air.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    air.set(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse());
    cursor.translate(dir.antiClockwise(), 3);
    cursor.translate(Cardinal.UP, 5);
    log.set(editor, cursor);
    cursor.translate(dir, 3);
    log.set(editor, cursor);
    cursor.translate(dir.clockwise(), 6);
    log.set(editor, cursor);
    cursor.translate(dir.reverse(), 3);
    log.set(editor, cursor);

    start = new Coord(origin);
    start.translate(dir.reverse());
    start.translate(Cardinal.UP, 5);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));
    start.translate(dir, 3);
    end.translate(dir, 3);
    stair.setOrientation(dir.reverse(), true).fill(editor, rand, new RectSolid(start, end));

    for (Cardinal d : dir.orthogonal()) {
      start = new Coord(origin);
      start.translate(Cardinal.UP, 5);
      start.translate(d, 3);
      end = new Coord(start);
      end.translate(dir);
      stair.setOrientation(d.reverse(), true).fill(editor, rand, new RectSolid(start, end));
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 5);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(dir);
    RectSolid.fill(editor, rand, start, end, lamp);

    cursor = new Coord(origin);
    cursor.translate(dir, 4);
    cursor.translate(dir.clockwise(), 5);
    BlockType.get(BlockType.SHELF).set(editor, cursor);
    cursor.translate(Cardinal.UP);
    BrewingStand.generate(editor, cursor);
    BrewingStand.add(editor, cursor, BrewingStand.MIDDLE, PotionMixture.getPotion(rand, PotionMixture.MOONSHINE));

    cursor = new Coord(origin);
    cursor.translate(dir, 4);
    cursor.translate(dir.antiClockwise(), 4);
    BlockType.get(BlockType.JUKEBOX).set(editor, cursor);
    cursor.translate(dir.antiClockwise());
    int level = settings.getDifficulty(cursor);
    Treasure treasureType = Treasure.EMPTY;
    try {
      TreasureChest stal = editor.generateTreasureChest(rand, cursor, false, treasureType, level);
      stal.setSlot(stal.getSize() / 2, Record.getRecord(Record.STAL));
    } catch (ChestPlacementException cpe) {
      // do nothing
    }

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 3);
    cursor.translate(dir.antiClockwise(), 4);
    try {
      TreasureChest bdub = editor.generateTreasureChest(rand, cursor, false, treasureType, level);
      bdub.setSlot((bdub.getSize() / 2) - 2, ItemNovelty.getItem(ItemNovelty.BDOUBLEO));
      ItemStack shirt = new ItemStack(Items.LEATHER_CHESTPLATE);
      Loot.setItemName(shirt, "Pink Sweater", null);
      Loot.setItemLore(shirt, "\"It's chinese red!\"");
      ItemArmour.dyeArmor(shirt, 250, 96, 128);
      bdub.setSlot((bdub.getSize() / 2) + 2, shirt);
    } catch (ChestPlacementException cpe) {
      // do nothing
    }

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 3);
    cursor.translate(dir.clockwise(), 4);
    try {
      TreasureChest genny = editor.generateTreasureChest(rand, cursor, false, treasureType, level);
      genny.setSlot(genny.getSize() / 2, ItemNovelty.getItem(ItemNovelty.GENERIKB));
    } catch (ChestPlacementException cpe) {
      // do nothing
    }


    return this;
  }

  private void table(WorldEditor editor, Random rand, Cardinal dir, Coord origin) {

    IStair stair = new MetaStair(StairType.SPRUCE);
    IStair chair = new MetaStair(StairType.NETHERBRICK);
    MetaBlock slab = Slab.get(Slab.SPRUCE, true, false, false);

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    start.translate(dir.antiClockwise());
    end = new Coord(origin);
    end.translate(dir.clockwise());
    end.translate(dir);
    RectSolid.fill(editor, rand, start, end, slab);

    for (Cardinal d : dir.orthogonal()) {
      start = new Coord(origin);
      start.translate(d, 2);
      end = new Coord(start);
      end.translate(dir);
      stair.setOrientation(d, true).fill(editor, rand, new RectSolid(start, end));
    }

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 2);
    for (Cardinal d : dir.orthogonal()) {
      Coord c = new Coord(cursor);
      c.translate(d);
      chair.setOrientation(dir, false).set(editor, c);
    }

    cursor.translate(dir, 5);
    for (Cardinal d : dir.orthogonal()) {
      Coord c = new Coord(cursor);
      c.translate(d);
      chair.setOrientation(dir.reverse(), false).set(editor, c);
    }
  }

  private void lamp(WorldEditor editor, Random rand, Cardinal dir, Coord origin) {
    MetaBlock fence = BlockType.get(BlockType.FENCE);
    MetaBlock plank = Wood.getPlank(Wood.SPRUCE);

    Coord cursor;

    cursor = new Coord(origin);
    plank.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    fence.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);
    for (Cardinal d : Cardinal.directions) {
      if (d == dir.reverse()) {
        continue;
      }

      Coord c = new Coord(cursor);
      c.translate(d);
      Trapdoor.get(Trapdoor.OAK, d.reverse(), false, true).set(editor, c);
    }
    cursor.translate(Cardinal.UP);
    fence.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    plank.set(editor, cursor);
    cursor.translate(Cardinal.UP);
    plank.set(editor, cursor);
  }

  private void logWall(WorldEditor editor, Random rand, Cardinal dir, Coord origin) {

    Coord start;
    Coord end;
    Coord cursor;

    IStair stair = new MetaStair(StairType.SPRUCE);
    MetaBlock plank = Wood.getPlank(Wood.SPRUCE);
    BlockCheckers checkers = new BlockCheckers(
        Log.get(Wood.SPRUCE, Cardinal.UP),
        Log.get(Wood.SPRUCE, dir.antiClockwise())
    );

    start = new Coord(origin);
    start.translate(Cardinal.UP);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, checkers);

    start = new Coord(origin);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    RectSolid.fill(editor, rand, start, end, plank);
    start.translate(dir);
    end.translate(dir);
    start.translate(Cardinal.UP, 4);
    end.translate(Cardinal.UP, 4);
    stair.setOrientation(dir, true).fill(editor, rand, new RectSolid(start, end));

    for (Cardinal d : dir.orthogonal()) {
      start = new Coord(origin);
      start.translate(d, 5);
      start.translate(Cardinal.UP);
      end = new Coord(start);
      end.translate(Cardinal.UP, 2);
      Log.get(Wood.SPRUCE, Cardinal.UP).fill(editor, rand, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(dir);
      cursor.translate(d, 3);
      lamp(editor, rand, dir, cursor);
    }


  }

  private void bWall(WorldEditor editor, Random rand, Cardinal dir, Coord origin) {
    BlockJumble bricks = new BlockJumble();
    bricks.addBlock(BlockType.get(BlockType.STONE_BRICK));
    bricks.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED));
    bricks.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY));
    MetaBlock plank = Wood.getPlank(Wood.SPRUCE);
    MetaBlock b = RogueConfig.getBoolean(RogueConfig.GENEROUS)
        ? BlockType.get(BlockType.EMERALD_BLOCK)
        : ColorBlock.get(ColorBlock.GLASS, DyeColor.LIME);

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(start);
    start.translate(dir.clockwise(), 3);
    end.translate(dir.antiClockwise(), 4);
    RectSolid.fill(editor, rand, start, end, plank);

    start = new Coord(origin);
    start.translate(Cardinal.UP);
    end = new Coord(start);
    start.translate(dir.clockwise(), 3);
    end.translate(dir.antiClockwise(), 4);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, bricks);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse());
    for (int i = 0; i < 5; ++i) {

      if (i % 2 == 0) {
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(dir.antiClockwise(), 2);
        RectSolid.fill(editor, rand, start, end, b);
      } else {
        Coord c = new Coord(cursor);
        c.translate(dir.clockwise());
        b.set(editor, c);
        c.translate(dir.antiClockwise(), 3);
        b.set(editor, c);
      }

      cursor.translate(Cardinal.UP);
    }

  }

  private void tvWall(WorldEditor editor, Random rand, Cardinal dir, Coord origin) {

    Coord start;
    Coord end;
    Coord cursor;

    MetaBlock plank = Wood.getPlank(Wood.SPRUCE);
    MetaBlock shelf = BlockType.get(BlockType.SHELF);
    MetaBlock jungle = Log.get(Wood.JUNGLE, dir);
    MetaBlock note = BlockType.get(BlockType.NOTEBLOCK);
    MetaBlock black = ColorBlock.get(ColorBlock.WOOL, DyeColor.BLACK);
    MetaBlock bean = Crops.getCocao(dir);
    MetaBlock slab = Slab.get(Slab.SPRUCE, true, false, false);

    start = new Coord(origin);
    start.translate(dir.reverse());
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 3);
    RectSolid.fill(editor, rand, start, end, slab);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 4);
    RectSolid.fill(editor, rand, start, end, plank);
    start.translate(dir.clockwise(), 2);
    end.translate(dir.antiClockwise(), 2);
    RectSolid.fill(editor, rand, start, end, note);
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, black);

    start = new Coord(origin);
    start.translate(dir.antiClockwise(), 2);
    start.translate(Cardinal.UP);
    end = new Coord(start);
    end.translate(dir.antiClockwise());
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, shelf);
    cursor = new Coord(start);
    cursor.translate(Cardinal.UP);
    jungle.set(editor, cursor);
    cursor.translate(dir.reverse());
    bean.set(editor, cursor);

    start = new Coord(origin);
    start.translate(dir.clockwise(), 3);
    start.translate(Cardinal.UP);
    end = new Coord(start);
    end.translate(dir.clockwise());
    end.translate(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, shelf);
    cursor = new Coord(start);
    cursor.translate(Cardinal.UP);
    jungle.set(editor, cursor);
    cursor.translate(dir.reverse());
    bean.set(editor, cursor);

  }

  public int getSize() {
    return 8;
  }

  public boolean validLocation(WorldEditor editor, Cardinal dir, int x, int y, int z) {

    for (Coord pos : new RectHollow(new Coord(x - 7, y - 2, z - 7), new Coord(x + 7, y + 5, z + 7))) {
      MetaBlock b = editor.getBlock(pos);
      if (!b.getMaterial().isSolid()) {
        return false;
      }
    }

    return true;
  }


}
