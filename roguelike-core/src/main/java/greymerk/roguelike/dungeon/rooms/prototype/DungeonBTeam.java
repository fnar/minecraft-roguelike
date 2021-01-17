package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.decorative.BrewingStand;
import com.github.srwaggon.minecraft.block.decorative.Crop;
import com.github.srwaggon.minecraft.block.normal.ColoredBlock;
import com.github.srwaggon.minecraft.block.normal.SlabBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;
import com.github.srwaggon.minecraft.block.normal.Wood;
import com.github.srwaggon.minecraft.block.redstone.TrapdoorBlock;
import com.github.srwaggon.minecraft.item.Record;
import com.github.srwaggon.util.Color;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.stainedGlass;
import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class DungeonBTeam extends DungeonBase {

  public DungeonBTeam(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    StairsBlock stair = StairsBlock.spruce();
    BlockBrush log = Wood.OAK.getLog();
    BlockBrush stonebrick = BlockType.STONE_BRICK.getBrush();
    BlockBrush cyan = stainedHardenedClay().setColor(DyeColor.CYAN);
    BlockBrush slab = SlabBlock.stone().setTop(false).setFullBlock(true).setSeamless(true);
    BlockBrush cobble = BlockType.COBBLESTONE.getBrush();
    BlockBrush lamp = BlockType.REDSTONE_LAMP.getBrush();

    Direction dir = entrances.get(0);

    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    end = origin.copy();
    start.translate(dir, 5);
    end.translate(dir.reverse(), 4);
    start.translate(dir.antiClockwise(), 6);
    end.translate(dir.clockwise(), 6);
    end.translate(Direction.UP, 5);
    start.translate(Direction.DOWN);
    RectHollow.newRect(start, end).fill(worldEditor, stonebrick);

    start = origin.copy();
    start.translate(Direction.DOWN);
    end = start.copy();
    start.translate(dir, 4);
    end.translate(dir.reverse(), 3);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    RectSolid.newRect(start, end).fill(worldEditor, cobble);

    start = origin.copy();
    start.translate(Direction.DOWN);
    end = start.copy();
    start.translate(dir, 3);
    end.translate(dir.reverse(), 2);
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    RectSolid.newRect(start, end).fill(worldEditor, cyan);

    start = origin.copy();
    start.translate(Direction.DOWN);
    end = start.copy();
    start.translate(dir, 2);
    end.translate(dir.reverse(), 1);
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 3);
    RectSolid.newRect(start, end).fill(worldEditor, slab);

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 4);
    logWall(worldEditor, dir, cursor);
    cursor.translate(dir, 9);
    logWall(worldEditor, dir.reverse(), cursor);

    cursor = origin.copy();
    cursor.translate(dir.antiClockwise(), 6);
    tvWall(worldEditor, dir.antiClockwise(), cursor);

    cursor = origin.copy();
    cursor.translate(dir.clockwise(), 6);
    bWall(worldEditor, dir.clockwise(), cursor);

    table(worldEditor, dir, origin);

    start = origin.copy();
    start.translate(dir.reverse(), 4);
    end = start.copy();
    start.translate(dir.antiClockwise());
    end.translate(dir.clockwise());
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(worldEditor, cobble);

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 4);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.translate(Direction.UP);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);

    cursor = origin.copy();
    cursor.translate(dir.reverse());
    cursor.translate(dir.antiClockwise(), 3);
    cursor.translate(Direction.UP, 5);
    log.stroke(worldEditor, cursor);
    cursor.translate(dir, 3);
    log.stroke(worldEditor, cursor);
    cursor.translate(dir.clockwise(), 6);
    log.stroke(worldEditor, cursor);
    cursor.translate(dir.reverse(), 3);
    log.stroke(worldEditor, cursor);

    start = origin.copy();
    start.translate(dir.reverse());
    start.translate(Direction.UP, 5);
    end = start.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    stair.setUpsideDown(true).setFacing(dir).fill(worldEditor, new RectSolid(start, end));
    start.translate(dir, 3);
    end.translate(dir, 3);
    stair.setUpsideDown(true).setFacing(dir.reverse()).fill(worldEditor, new RectSolid(start, end));

    for (Direction d : dir.orthogonals()) {
      start = origin.copy();
      start.translate(Direction.UP, 5);
      start.translate(d, 3);
      end = start.copy();
      end.translate(dir);
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(worldEditor, new RectSolid(start, end));
    }

    start = origin.copy();
    start.translate(Direction.UP, 5);
    end = start.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 2);
    end.translate(dir);
    RectSolid.newRect(start, end).fill(worldEditor, lamp);

    cursor = origin.copy();
    cursor.translate(dir, 4);
    cursor.translate(dir.clockwise(), 5);
    BlockType.BOOKSHELF.getBrush().stroke(worldEditor, cursor);
    cursor.translate(Direction.UP);
    BlockType.BREWING_STAND.getBrush().stroke(worldEditor, cursor);
    worldEditor.setItem(cursor, BrewingStand.Slot.MIDDLE, PotionMixture.getPotionAsRldItemStack(worldEditor.getRandom(), PotionMixture.MOONSHINE));

    cursor = origin.copy();
    cursor.translate(dir, 4);
    cursor.translate(dir.antiClockwise(), 4);
    BlockType.JUKEBOX.getBrush().stroke(worldEditor, cursor);
    cursor.translate(dir.antiClockwise());
    int level = levelSettings.getDifficulty(cursor);
    ChestType chestType = ChestType.EMPTY;
    TreasureChest stal = worldEditor.getTreasureChestEditor().createChest(cursor, false, level, chestType);
    stal.setSlot(stal.worldEditor.getCapacity(stal) / 2, Record.newRecord().withSong(Record.Song.STAL).asItemStack());

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 3);
    cursor.translate(dir.antiClockwise(), 4);
    TreasureChest bdub = worldEditor.getTreasureChestEditor().createChest(cursor, false, level, chestType);
    bdub.setSlot((bdub.worldEditor.getCapacity(bdub) / 2) - 2, ItemNovelty.getItem(ItemNovelty.BDOUBLEO));
    ItemStack shirt = new ItemStack(Items.LEATHER_CHESTPLATE);
    shirt.setStackDisplayName("Pink Sweater");
    Loot.setItemLore(shirt, "\"It's chinese red!\"");
    ItemArmour.dyeArmor(shirt, new Color(250, 96, 128));
    bdub.setSlot((bdub.worldEditor.getCapacity(bdub) / 2) + 2, shirt);

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 3);
    cursor.translate(dir.clockwise(), 4);
    TreasureChest genny = worldEditor.getTreasureChestEditor().createChest(cursor, false, level, chestType);
    genny.setSlot(genny.worldEditor.getCapacity(genny) / 2, ItemNovelty.getItem(ItemNovelty.GENERIKB));

    return this;
  }

  private void table(WorldEditor editor, Direction dir, Coord origin) {
    StairsBlock stair = StairsBlock.spruce();
    StairsBlock chair = StairsBlock.netherBrick();
    BlockBrush slab = SlabBlock.spruce().setTop(true).setFullBlock(false).setSeamless(false);

    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    start.translate(dir.antiClockwise());
    end = origin.copy();
    end.translate(dir.clockwise());
    end.translate(dir);
    RectSolid.newRect(start, end).fill(editor, slab);

    for (Direction d : dir.orthogonals()) {
      start = origin.copy();
      start.translate(d, 2);
      end = start.copy();
      end.translate(dir);
      stair.setUpsideDown(true).setFacing(d).fill(editor, new RectSolid(start, end));
    }

    cursor = origin.copy();
    cursor.translate(dir.reverse(), 2);
    for (Direction d : dir.orthogonals()) {
      Coord c = cursor.copy();
      c.translate(d);
      chair.setUpsideDown(false).setFacing(dir).stroke(editor, c);
    }

    cursor.translate(dir, 5);
    for (Direction d : dir.orthogonals()) {
      Coord c = cursor.copy();
      c.translate(d);
      chair.setUpsideDown(false).setFacing(dir.reverse()).stroke(editor, c);
    }
  }

  private void lamp(WorldEditor editor, Direction dir, Coord origin) {
    BlockBrush fence = Wood.OAK.getFence();
    BlockBrush plank = Wood.SPRUCE.getPlanks();

    Coord cursor;

    cursor = origin.copy();
    plank.stroke(editor, cursor);
    cursor.translate(Direction.UP);
    fence.stroke(editor, cursor);
    cursor.translate(Direction.UP);
    BlockType.GLOWSTONE.getBrush().stroke(editor, cursor);
    for (Direction d : Direction.CARDINAL) {
      if (d == dir.reverse()) {
        continue;
      }

      Coord c = cursor.copy();
      c.translate(d);

      TrapdoorBlock.wood()
          .setOpen()
          .setFacing(d.reverse())
          .stroke(editor, c);

//      Trapdoor.get(Trapdoor.OAK, d.reverse(), false, true).stroke(editor, c);
    }
    cursor.translate(Direction.UP);
    fence.stroke(editor, cursor);
    cursor.translate(Direction.UP);
    plank.stroke(editor, cursor);
    cursor.translate(Direction.UP);
    plank.stroke(editor, cursor);
  }

  private void logWall(WorldEditor editor, Direction dir, Coord origin) {

    Coord start;
    Coord end;
    Coord cursor;

    Wood wood = Wood.SPRUCE;

    StairsBlock stair = wood.getStairs();
    BlockBrush plank = wood.getPlanks();

    BlockCheckers checkers = new BlockCheckers(
        wood.getLog().setFacing(Direction.UP),
        wood.getLog().setFacing(dir.antiClockwise()));

    start = origin.copy();
    start.translate(Direction.UP);
    end = start.copy();
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, checkers);

    start = origin.copy();
    end = start.copy();
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    RectSolid.newRect(start, end).fill(editor, plank);
    start.translate(dir);
    end.translate(dir);
    start.translate(Direction.UP, 4);
    end.translate(Direction.UP, 4);
    stair.setUpsideDown(true).setFacing(dir).fill(editor, new RectSolid(start, end));

    for (Direction d : dir.orthogonals()) {
      start = origin.copy();
      start.translate(d, 5);
      start.translate(Direction.UP);
      end = start.copy();
      end.translate(Direction.UP, 2);
      wood.getLog().setFacing(Direction.UP).fill(editor, new RectSolid(start, end));

      cursor = origin.copy();
      cursor.translate(dir);
      cursor.translate(d, 3);
      lamp(editor, dir, cursor);
    }


  }

  private void bWall(WorldEditor editor, Direction dir, Coord origin) {
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

    start = origin.copy();
    end = start.copy();
    start.translate(dir.clockwise(), 3);
    end.translate(dir.antiClockwise(), 4);
    RectSolid.newRect(start, end).fill(editor, plank);

    start = origin.copy();
    start.translate(Direction.UP);
    end = start.copy();
    start.translate(dir.clockwise(), 3);
    end.translate(dir.antiClockwise(), 4);
    end.translate(Direction.UP, 3);
    RectSolid.newRect(start, end).fill(editor, bricks);

    cursor = origin.copy();
    cursor.translate(dir.reverse());
    for (int i = 0; i < 5; ++i) {

      if (i % 2 == 0) {
        start = cursor.copy();
        end = start.copy();
        end.translate(dir.antiClockwise(), 2);
        RectSolid.newRect(start, end).fill(editor, b);
      } else {
        Coord c = cursor.copy();
        c.translate(dir.clockwise());
        b.stroke(editor, c);
        c.translate(dir.antiClockwise(), 3);
        b.stroke(editor, c);
      }

      cursor.translate(Direction.UP);
    }

  }

  private void tvWall(WorldEditor editor, Direction dir, Coord origin) {

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

    start = origin.copy();
    start.translate(dir.reverse());
    start.translate(Direction.UP, 4);
    end = start.copy();
    start.translate(dir.antiClockwise(), 2);
    end.translate(dir.clockwise(), 3);
    RectSolid.newRect(start, end).fill(editor, slab);

    start = origin.copy();
    end = origin.copy();
    start.translate(dir.antiClockwise(), 3);
    end.translate(dir.clockwise(), 4);
    RectSolid.newRect(start, end).fill(editor, plank);
    start.translate(dir.clockwise(), 2);
    end.translate(dir.antiClockwise(), 2);
    RectSolid.newRect(start, end).fill(editor, note);
    start.translate(Direction.UP);
    end.translate(Direction.UP, 3);
    RectSolid.newRect(start, end).fill(editor, black);

    start = origin.copy();
    start.translate(dir.antiClockwise(), 2);
    start.translate(Direction.UP);
    end = start.copy();
    end.translate(dir.antiClockwise());
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, shelf);
    cursor = start.copy();
    cursor.translate(Direction.UP);
    jungle.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    bean.stroke(editor, cursor);

    start = origin.copy();
    start.translate(dir.clockwise(), 3);
    start.translate(Direction.UP);
    end = start.copy();
    end.translate(dir.clockwise());
    end.translate(Direction.UP, 2);
    RectSolid.newRect(start, end).fill(editor, shelf);
    cursor = start.copy();
    cursor.translate(Direction.UP);
    jungle.stroke(editor, cursor);

    cursor.translate(dir.reverse());
    bean.stroke(editor, cursor);

  }

  public int getSize() {
    return 8;
  }

}
