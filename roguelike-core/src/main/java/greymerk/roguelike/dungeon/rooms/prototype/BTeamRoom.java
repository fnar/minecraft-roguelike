package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.BrewingStand;
import com.github.fnar.minecraft.block.normal.ColoredBlock;
import com.github.fnar.minecraft.block.normal.SlabBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.TrapdoorBlock;
import com.github.fnar.minecraft.item.Record;
import com.github.fnar.minecraft.material.Crop;
import com.github.fnar.minecraft.material.Wood;

import java.util.List;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.treasure.loot.PotionMixture;
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

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedGlass;
import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class BTeamRoom extends BaseRoom {

  public BTeamRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 7;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {

    Direction entrance = getEntrance(entrances);

    return generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(at, entrance.reverse());
  }

  private BTeamRoom generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(Coord at, Direction entrance) {
    StairsBlock stair = StairsBlock.spruce();
    BlockBrush log = Wood.OAK.getLog();
    BlockBrush stonebrick = BlockType.STONE_BRICKS.getBrush();
    BlockBrush cyan = stainedHardenedClay().setColor(DyeColor.CYAN);
    BlockBrush slab = SlabBlock.stone().setTop(false).setFullBlock(true).setSeamless(true);
    BlockBrush cobble = BlockType.COBBLESTONE.getBrush();
    BlockBrush lamp = BlockType.REDSTONE_LAMP.getBrush();

    Coord start = at.copy();
    Coord end = at.copy();
    start.translate(entrance, 5);
    end.translate(entrance.reverse(), 4);
    start.translate(entrance.antiClockwise(), 6);
    end.translate(entrance.clockwise(), 6);
    end.up(5);
    start.down();
    RectHollow.newRect(start, end).fill(worldEditor, stonebrick);

    start = at.copy();
    start.down();
    end = start.copy();
    start.translate(entrance, 4);
    end.translate(entrance.reverse(), 3);
    start.translate(entrance.antiClockwise(), 5);
    end.translate(entrance.clockwise(), 5);
    cobble.fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    start.down();
    end = start.copy();
    start.translate(entrance, 3);
    end.translate(entrance.reverse(), 2);
    start.translate(entrance.antiClockwise(), 4);
    end.translate(entrance.clockwise(), 4);
    cyan.fill(worldEditor, RectSolid.newRect(start, end));

    start = at.copy();
    start.down();
    end = start.copy();
    start.translate(entrance, 2);
    end.translate(entrance.reverse(), 1);
    start.translate(entrance.antiClockwise(), 3);
    end.translate(entrance.clockwise(), 3);
    slab.fill(worldEditor, RectSolid.newRect(start, end));

    Coord cursor = at.copy();
    cursor.translate(entrance.reverse(), 4);
    logWall(entrance, cursor);
    cursor.translate(entrance, 9);
    logWall(entrance.reverse(), cursor);

    cursor = at.copy();
    cursor.translate(entrance.antiClockwise(), 6);
    tvWall(worldEditor, entrance.antiClockwise(), cursor);

    cursor = at.copy();
    cursor.translate(entrance.clockwise(), 6);
    bWall(entrance.clockwise(), cursor);

    table(worldEditor, entrance, at);

    start = at.copy();
    start.translate(entrance.reverse(), 4);
    end = start.copy();
    start.translate(entrance.antiClockwise());
    end.translate(entrance.clockwise());
    end.up(2);
    cobble.fill(worldEditor, RectSolid.newRect(start, end));

    cursor = at.copy();
    cursor.translate(entrance.reverse(), 4);
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);
    cursor.up();
    SingleBlockBrush.AIR.stroke(worldEditor, cursor);

    cursor = at.copy();
    cursor.translate(entrance.reverse());
    cursor.translate(entrance.antiClockwise(), 3);
    cursor.up(5);
    log.stroke(worldEditor, cursor);
    cursor.translate(entrance, 3);
    log.stroke(worldEditor, cursor);
    cursor.translate(entrance.clockwise(), 6);
    log.stroke(worldEditor, cursor);
    cursor.translate(entrance.reverse(), 3);
    log.stroke(worldEditor, cursor);

    start = at.copy();
    start.translate(entrance.reverse());
    start.up(5);
    end = start.copy();
    start.translate(entrance.antiClockwise(), 2);
    end.translate(entrance.clockwise(), 2);
    stair.setUpsideDown(true).setFacing(entrance).fill(worldEditor, RectSolid.newRect(start, end));
    start.translate(entrance, 3);
    end.translate(entrance, 3);
    stair.setUpsideDown(true).setFacing(entrance.reverse()).fill(worldEditor, RectSolid.newRect(start, end));

    for (Direction d : entrance.orthogonals()) {
      start = at.copy();
      start.up(5);
      start.translate(d, 3);
      end = start.copy();
      end.translate(entrance);
      stair.setUpsideDown(true).setFacing(d.reverse()).fill(worldEditor, RectSolid.newRect(start, end));
    }

    start = at.copy();
    start.up(5);
    end = start.copy();
    start.translate(entrance.antiClockwise(), 2);
    end.translate(entrance.clockwise(), 2);
    end.translate(entrance);
    lamp.fill(worldEditor, RectSolid.newRect(start, end));

    cursor = at.copy();
    cursor.translate(entrance, 4);
    cursor.translate(entrance.clockwise(), 5);
    BlockType.BOOKSHELF.getBrush().stroke(worldEditor, cursor);
    cursor.up();
    BlockType.BREWING_STAND.getBrush().stroke(worldEditor, cursor);
    worldEditor.setItem(cursor, BrewingStand.Slot.MIDDLE, PotionMixture.getPotionAsRldItemStack(random(), PotionMixture.MOONSHINE));

    placeStalChest(at, entrance);
    placeBDouble0Chest(at, entrance);
    placeGenerikBChest(at, entrance);
    return this;
  }

  private void placeStalChest(Coord origin, Direction dir) {
    Coord cursor;
    cursor = origin.copy();
    cursor.translate(dir, 4);
    cursor.translate(dir.antiClockwise(), 4);
    BlockType.JUKEBOX.getBrush().stroke(worldEditor, cursor);
    cursor.translate(dir.antiClockwise());
    new TreasureChest(cursor, worldEditor)
        .withChestType(getChestTypeOrUse(ChestType.EMPTY))
        .withFacing(dir)
        .withTrap(false)
        .stroke(worldEditor, cursor).ifPresent(stal -> stal.setSlot(worldEditor.getCapacity(stal) / 2, Record.Song.STAL.asItem().asStack()));
  }

  private void placeBDouble0Chest(Coord origin, Direction dir) {
    Coord cursor;
    cursor = origin.copy();
    cursor.translate(dir.reverse(), 3);
    cursor.translate(dir.antiClockwise(), 4);
    new TreasureChest(cursor, worldEditor)
        .withChestType(getChestTypeOrUse(ChestType.EMPTY))
        .withFacing(dir)
        .withTrap(false)
        .stroke(worldEditor, cursor).ifPresent(bdub -> {
      bdub.setSlot((worldEditor.getCapacity(bdub) / 2) - 2, ItemNovelty.bDoubleOsDigJob());
      bdub.setSlot((worldEditor.getCapacity(bdub) / 2) + 2, ItemNovelty.bDoubleOspinkSweater());
    });
  }

  private void placeGenerikBChest(Coord origin, Direction dir) {
    Coord cursor;
    cursor = origin.copy();
    cursor.translate(dir.reverse(), 3);
    cursor.translate(dir.clockwise(), 4);
    new TreasureChest(cursor, worldEditor)
        .withChestType(getChestTypeOrUse(ChestType.EMPTY))
        .withFacing(dir)
        .withTrap(false)
        .stroke(worldEditor, cursor).ifPresent(genny -> genny.setSlot(worldEditor.getCapacity(genny) / 2, ItemNovelty.generikBsHotPotato()));
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
      stair.setUpsideDown(true).setFacing(d).fill(editor, RectSolid.newRect(start, end));
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

  private void lamp(Direction dir, Coord origin) {
    BlockBrush fence = Wood.OAK.getFence();
    BlockBrush plank = Wood.SPRUCE.getPlanks();

    Coord cursor = origin.copy();
    plank.stroke(worldEditor, cursor);
    cursor.up();
    fence.stroke(worldEditor, cursor);
    cursor.up();
    primaryLightBrush().stroke(worldEditor, cursor);
    for (Direction d : Direction.CARDINAL) {
      if (d == dir.reverse()) {
        continue;
      }

      Coord c = cursor.copy();
      c.translate(d);

      TrapdoorBlock.wood()
          .setOpen()
          .setFacing(d.reverse())
          .stroke(worldEditor, c);
    }
    cursor.up();
    fence.stroke(worldEditor, cursor);
    cursor.up();
    plank.stroke(worldEditor, cursor);
    cursor.up();
    plank.stroke(worldEditor, cursor);
  }

  private void logWall(Direction dir, Coord origin) {
    Wood wood = Wood.SPRUCE;
    StairsBlock stair = wood.getStairs();
    BlockBrush plank = wood.getPlanks();

    BlockCheckers checkers = new BlockCheckers(
        wood.getLog().setFacing(Direction.UP),
        wood.getLog().setFacing(dir.antiClockwise()));

    Coord start = origin.copy();
    start.up();
    Coord end = start.copy();
    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    end.up(2);
    checkers.fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy();
    end = start.copy();
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    plank.fill(worldEditor, RectSolid.newRect(start, end));
    start.translate(dir);
    end.translate(dir);
    start.up(4);
    end.up(4);
    stair.setUpsideDown(true).setFacing(dir).fill(worldEditor, RectSolid.newRect(start, end));

    for (Direction d : dir.orthogonals()) {
      start = origin.copy();
      start.translate(d, 5);
      start.up();
      end = start.copy();
      end.up(2);
      wood.getLog().setFacing(Direction.UP).fill(worldEditor, RectSolid.newRect(start, end));

      Coord cursor = origin.copy();
      cursor.translate(dir);
      cursor.translate(d, 3);
      lamp(dir, cursor);
    }


  }

  private void bWall(Direction dir, Coord origin) {
    BlockJumble bricks = new BlockJumble();
    bricks.addBlock(BlockType.STONE_BRICKS.getBrush());
    bricks.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush());
    bricks.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush());
    BlockBrush plank = Wood.SPRUCE.getPlanks();
    BlockBrush b = RogueConfig.GENEROUS.getBoolean()
        ? BlockType.EMERALD_BLOCK.getBrush()
        : stainedGlass().setColor(DyeColor.LIME);

    Coord start;
    Coord end;
    Coord cursor;

    start = origin.copy();
    end = start.copy();
    start.translate(dir.clockwise(), 3);
    end.translate(dir.antiClockwise(), 4);
    plank.fill(worldEditor, RectSolid.newRect(start, end));

    start = origin.copy();
    start.up();
    end = start.copy();
    start.translate(dir.clockwise(), 3);
    end.translate(dir.antiClockwise(), 4);
    end.up(3);
    bricks.fill(worldEditor, RectSolid.newRect(start, end));

    cursor = origin.copy();
    cursor.translate(dir.reverse());
    for (int i = 0; i < 5; ++i) {

      if (i % 2 == 0) {
        start = cursor.copy();
        end = start.copy();
        end.translate(dir.antiClockwise(), 2);
        b.fill(worldEditor, RectSolid.newRect(start, end));
      } else {
        Coord c = cursor.copy();
        c.translate(dir.clockwise());
        b.stroke(worldEditor, c);
        c.translate(dir.antiClockwise(), 3);
        b.stroke(worldEditor, c);
      }

      cursor.up();
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
    start.up(4);
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
    start.up();
    end.up(3);
    RectSolid.newRect(start, end).fill(editor, black);

    start = origin.copy();
    start.translate(dir.antiClockwise(), 2);
    start.up();
    end = start.copy();
    end.translate(dir.antiClockwise());
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, shelf);
    cursor = start.copy();
    cursor.up();
    jungle.stroke(editor, cursor);
    cursor.translate(dir.reverse());
    bean.stroke(editor, cursor);

    start = origin.copy();
    start.translate(dir.clockwise(), 3);
    start.up();
    end = start.copy();
    end.translate(dir.clockwise());
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, shelf);
    cursor = start.copy();
    cursor.up();
    jungle.stroke(editor, cursor);

    cursor.translate(dir.reverse());
    bean.stroke(editor, cursor);

  }

}
