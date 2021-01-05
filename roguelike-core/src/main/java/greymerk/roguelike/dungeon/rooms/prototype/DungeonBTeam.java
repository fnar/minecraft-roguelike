package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.decorative.Crop;
import com.github.srwaggon.roguelike.worldgen.block.normal.Wood;
import com.github.srwaggon.roguelike.worldgen.block.decorative.BrewingStand;
import com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.SlabBlock;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;
import com.github.srwaggon.roguelike.worldgen.block.redstone.TrapdoorBlock;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.ChestPlacementException;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.Record;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedGlass;
import static com.github.srwaggon.roguelike.worldgen.block.normal.ColoredBlock.stainedHardenedClay;

public class DungeonBTeam extends DungeonBase {

  public DungeonBTeam(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {

    StairsBlock stair = StairsBlock.spruce();
    BlockBrush log = Wood.OAK.getLog();
    BlockBrush stonebrick = BlockType.STONE_BRICK.getBrush();
    BlockBrush cyan = stainedHardenedClay().setColor(DyeColor.CYAN);
    BlockBrush slab = SlabBlock.stone().setTop(false).setFullBlock(true).setSeamless(true);
    BlockBrush cobble = BlockType.COBBLESTONE.getBrush();
    BlockBrush lamp = BlockType.REDSTONE_LAMP.getBrush();

    Cardinal dir = entrances.get(0);

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
    RectHollow.fill(editor, start, end, stonebrick);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir, 4);
    end.translate(dir.reverse(), 3);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    RectSolid.newRect(start, end).fill(editor, cobble);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir, 3);
    end.translate(dir.reverse(), 2);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    RectSolid.newRect(start, end).fill(editor, cyan);

    start = new Coord(origin);
    start.translate(Cardinal.DOWN);
    end = new Coord(start);
    start.translate(dir, 2);
    end.translate(dir.reverse(), 1);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 3);
    RectSolid.newRect(start, end).fill(editor, slab);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 4);
    logWall(editor, dir, cursor);
    cursor.translate(dir, 9);
    logWall(editor, dir.reverse(), cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.antiClockwise(), 6);
    tvWall(editor, dir.antiClockwise(), cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.clockwise(), 6);
    bWall(editor, dir.clockwise(), cursor);

    table(editor, dir, origin);

    start = new Coord(origin);
    start.translate(dir.reverse(), 4);
    end = new Coord(start);
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, cobble);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 4);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    SingleBlockBrush.AIR.stroke(editor, cursor);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse());
    cursor.translate(dir.antiClockwise(), 3);
    cursor.translate(Cardinal.UP, 5);
    log.stroke(editor, cursor);
    cursor.translate(dir, 3);
    log.stroke(editor, cursor);
    cursor.translate(dir.clockwise(), 6);
    log.stroke(editor, cursor);
    cursor.translate(dir.reverse(), 3);
    log.stroke(editor, cursor);

    start = new Coord(origin);
    start.translate(dir.reverse());
    start.translate(Cardinal.UP, 5);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    stair.setUpsideDown(true).setFacing(dir).fill(editor, new RectSolid(start, end));
    start.translate(dir, 3);
    end.translate(dir, 3);
    stair.setUpsideDown(true).setFacing(dir.reverse()).fill(editor, new RectSolid(start, end));

    for (Cardinal d : dir.orthogonals()) {
      start = new Coord(origin);
      start.translate(Cardinal.UP, 5);
      start.translate(d, 3);
      end = new Coord(start);
      end.translate(dir);
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(editor, new RectSolid(start, end));
    }

    start = new Coord(origin);
    start.translate(Cardinal.UP, 5);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, lamp);

    cursor = new Coord(origin);
    cursor.translate(dir, 4);
    cursor.translate(dir.clockwise(), 5);
    BlockType.BOOKSHELF.getBrush().stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.BREWING_STAND.getBrush().stroke(editor, cursor);
    editor.setItem(cursor, BrewingStand.Slot.MIDDLE, PotionMixture.getPotion(editor.getRandom(), PotionMixture.MOONSHINE));

    cursor = new Coord(origin);
    cursor.translate(dir, 4);
    cursor.translate(dir.antiClockwise(), 4);
    BlockType.JUKEBOX.getBrush().stroke(editor, cursor);
    cursor.translate(dir.antiClockwise());
    int level = settings.getDifficulty(cursor);
    ChestType chestType = ChestType.EMPTY;
    try {
      TreasureChest stal = editor.getTreasureChestEditor().generateTreasureChest(cursor, false, chestType, level);
      stal.setSlot(stal.getSize() / 2, Record.getRecord(Record.STAL));
    } catch (ChestPlacementException cpe) {
      // do nothing
    }

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 3);
    cursor.translate(dir.antiClockwise(), 4);
    try {
      TreasureChest bdub = editor.getTreasureChestEditor().generateTreasureChest(cursor, false, chestType, level);
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
      TreasureChest genny = editor.getTreasureChestEditor().generateTreasureChest(cursor, false, chestType, level);
      genny.setSlot(genny.getSize() / 2, ItemNovelty.getItem(ItemNovelty.GENERIKB));
    } catch (ChestPlacementException cpe) {
      // do nothing
    }


    return this;
  }

  private void table(WorldEditor editor, Cardinal dir, Coord origin) {
    StairsBlock stair = StairsBlock.spruce();
    StairsBlock chair = StairsBlock.netherBrick();
    BlockBrush slab = SlabBlock.spruce().setTop(true).setFullBlock(false).setSeamless(false);

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    start.translate(dir.antiClockwise());
    end = new Coord(origin);
    end.translate(dir.clockwise());
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, slab);

    for (Cardinal d : dir.orthogonals()) {
      start = new Coord(origin);
      start.translate(d, 2);
      end = new Coord(start);
      end.translate(dir);
      stair.setUpsideDown(true).setFacing(d).fill(editor, new RectSolid(start, end));
    }

    cursor = new Coord(origin);
    cursor.translate(dir.reverse(), 2);
    for (Cardinal d : dir.orthogonals()) {
      Coord c = new Coord(cursor);
      c.translate(d);
      chair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
    }

    cursor.translate(dir, 5);
    for (Cardinal d : dir.orthogonals()) {
      Coord c = new Coord(cursor);
      c.translate(d);
      chair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, c);
    }
  }

  private void lamp(WorldEditor editor, Cardinal dir, Coord origin) {
    BlockBrush fence = Wood.OAK.getFence();
    BlockBrush plank = Wood.SPRUCE.getPlanks();

    Coord cursor;

    cursor = new Coord(origin);
    plank.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    fence.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    BlockType.GLOWSTONE.getBrush().stroke(editor, cursor);
    for (Cardinal d : Cardinal.DIRECTIONS) {
      if (d == dir.reverse()) {
        continue;
      }

      Coord c = new Coord(cursor);
      c.translate(d);

      TrapdoorBlock.wood()
          .setOpen()
          .setFacing(d.reverse())
          .stroke(editor, c);

//      Trapdoor.get(Trapdoor.OAK, d.reverse(), false, true).stroke(editor, c);
    }
    cursor.translate(Cardinal.UP);
    fence.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    plank.stroke(editor, cursor);
    cursor.translate(Cardinal.UP);
    plank.stroke(editor, cursor);
  }

  private void logWall(WorldEditor editor, Cardinal dir, Coord origin) {

    Coord start;
    Coord end;
    Coord cursor;

    Wood wood = Wood.SPRUCE;

    StairsBlock stair = wood.getStairs();
    BlockBrush plank = wood.getPlanks();

    BlockCheckers checkers = new BlockCheckers(
        wood.getLog().setFacing(Cardinal.UP),
        wood.getLog().setFacing(dir.antiClockwise()));

    start = new Coord(origin);
    start.translate(Cardinal.UP);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, checkers);

    start = new Coord(origin);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    RectSolid.newRect(start, end).fill(editor, plank);
    start.translate(dir);
    end.translate(dir);
    start.translate(Cardinal.UP, 4);
    end.translate(Cardinal.UP, 4);
    stair.setUpsideDown(true).setFacing(dir).fill(editor, new RectSolid(start, end));

    for (Cardinal d : dir.orthogonals()) {
      start = new Coord(origin);
      start.translate(d, 5);
      start.translate(Cardinal.UP);
      end = new Coord(start);
      end.translate(Cardinal.UP, 2);
      wood.getLog().setFacing(Cardinal.UP).fill(editor, new RectSolid(start, end));

      cursor = new Coord(origin);
      cursor.translate(dir);
      cursor.translate(d, 3);
      lamp(editor, dir, cursor);
    }


  }

  private void bWall(WorldEditor editor, Cardinal dir, Coord origin) {
    BlockJumble bricks = new BlockJumble();
    bricks.addBlock(BlockType.STONE_BRICK.getBrush());
    bricks.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush());
    bricks.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush());
    BlockBrush plank = Wood.SPRUCE.getPlanks();
    BlockBrush b = RogueConfig.getBoolean(RogueConfig.GENEROUS)
        ? BlockType.EMERALD_BLOCK.getBrush()
        : stainedGlass().setColor(DyeColor.LIME);

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(start);
    start.translate(dir.clockwise(), 3);
    end.translate(dir.antiClockwise(), 4);
    RectSolid.newRect(start, end).fill(editor, plank);

    start = new Coord(origin);
    start.translate(Cardinal.UP);
    end = new Coord(start);
    start.translate(dir.clockwise(), 3);
    end.translate(dir.antiClockwise(), 4);
    end.translate(Cardinal.UP, 3);
    RectSolid.newRect(start, end).fill(editor, bricks);

    cursor = new Coord(origin);
    cursor.translate(dir.reverse());
    for (int i = 0; i < 5; ++i) {

      if (i % 2 == 0) {
        start = new Coord(cursor);
        end = new Coord(start);
        end.translate(dir.antiClockwise(), 2);
        RectSolid.newRect(start, end).fill(editor, b);
      } else {
        Coord c = new Coord(cursor);
        c.translate(dir.clockwise());
        b.stroke(editor, c);
        c.translate(dir.antiClockwise(), 3);
        b.stroke(editor, c);
      }

      cursor.translate(Cardinal.UP);
    }

  }

  private void tvWall(WorldEditor editor, Cardinal dir, Coord origin) {

    Coord start;
    Coord end;
    Coord cursor;

    BlockBrush plank = Wood.SPRUCE.getPlanks();
    BlockBrush shelf = BlockType.BOOKSHELF.getBrush();
    BlockBrush jungle = Wood.JUNGLE.getLog().setFacing(dir);
    BlockBrush note = BlockType.NOTEBLOCK.getBrush();
    BlockBrush black = ColoredBlock.wool().setColor(DyeColor.BLACK);
    BlockBrush bean = Crop.COCOA.getBrush().setFacing(dir);
    BlockBrush slab = Wood.SPRUCE.getSlabs().setTop(true).setFullBlock(false).setSeamless(false);

    start = new Coord(origin);
    start.translate(dir.reverse());
    start.translate(Cardinal.UP, 4);
    end = new Coord(start);
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 3);
    RectSolid.newRect(start, end).fill(editor, slab);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 4);
    RectSolid.newRect(start, end).fill(editor, plank);
    start.translate(dir.clockwise(), 2);
    end.translate(dir.antiClockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, note);
    start.translate(Cardinal.UP);
    end.translate(Cardinal.UP, 3);
    RectSolid.newRect(start, end).fill(editor, black);

    start = new Coord(origin);
    start.translate(dir.antiClockwise(), 2);
    start.translate(Cardinal.UP);
    end = new Coord(start);
    end.translate(dir.antiClockwise());
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, shelf);
    cursor = new Coord(start);
    cursor.translate(Cardinal.UP);
    jungle.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    bean.stroke(editor, cursor);

    start = new Coord(origin);
    start.translate(dir.clockwise(), 3);
    start.translate(Cardinal.UP);
    end = new Coord(start);
    end.translate(dir.clockwise());
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, shelf);
    cursor = new Coord(start);
    cursor.translate(Cardinal.UP);
    jungle.stroke(editor, cursor);

    cursor.translate(dir.reverse());
    bean.stroke(editor, cursor);

  }

  public int getSize() {
    return 8;
  }

}
