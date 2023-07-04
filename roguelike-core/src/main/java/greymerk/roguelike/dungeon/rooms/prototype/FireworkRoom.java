package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.TorchBlock;
import com.github.fnar.minecraft.block.redstone.ComparatorBlock;
import com.github.fnar.minecraft.block.redstone.LeverBlock;
import com.github.fnar.minecraft.block.redstone.RepeaterBlock;
import com.github.fnar.minecraft.item.Firework;
import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.ToolType;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.BaseRoom;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.util.TextFormat;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class FireworkRoom extends BaseRoom {

  public FireworkRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
    this.wallDist = 9;
  }

  @Override
  public BaseRoom generate(Coord at, List<Direction> entrances) {
    Direction entrance = getEntrance(entrances);

    return generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(at, entrance.reverse());
  }

  private FireworkRoom generateReversedBecauseEntrancesShouldBeOutwardFromRoomCenter(Coord at, Direction entrance) {
    BlockBrush breadboard = stainedHardenedClay().setColor(DyeColor.GREEN);

    Coord start = at.copy();
    Coord end = start.copy();
    start.translate(entrance.reverse(), 9);
    end.translate(entrance, 9);
    start.translate(entrance.antiClockwise(), 4);
    end.translate(entrance.clockwise(), 4);
    start.down();
    end.up(3);
    RectHollow.newRect(start, end).fill(worldEditor, stainedHardenedClay().setColor(DyeColor.ORANGE), false, true);

    start = at.copy();
    start.translate(entrance.antiClockwise(), 2);
    end = start.copy();
    start.translate(entrance.reverse(), 3);
    end.translate(entrance, 7);
    end.up();
    breadboard.fill(worldEditor, RectSolid.newRect(start, end));

    start.translate(entrance.clockwise(), 2);
    end.translate(entrance.clockwise(), 2);
    breadboard.fill(worldEditor, RectSolid.newRect(start, end));

    start.translate(entrance.clockwise(), 2);
    end.translate(entrance.clockwise(), 2);
    breadboard.fill(worldEditor, RectSolid.newRect(start, end));

    Coord cursor = at.copy();
    cursor.translate(entrance.antiClockwise(), 2);

    launcher(worldEditor, entrance, cursor);
    cursor.translate(entrance.clockwise(), 2);
    launcher(worldEditor, entrance, cursor);
    cursor.translate(entrance.clockwise(), 2);
    launcher(worldEditor, entrance, cursor);
    cursor.translate(entrance, 6);
    launcher(worldEditor, entrance, cursor);
    cursor.translate(entrance.antiClockwise(), 2);
    launcher(worldEditor, entrance, cursor);
    cursor.translate(entrance.antiClockwise(), 2);
    launcher(worldEditor, entrance, cursor);

    start = at.copy();
    start.translate(entrance, 4);
    end = start.copy();
    start.translate(entrance.antiClockwise(), 2);
    end.translate(entrance.clockwise(), 2);
    end.translate(entrance, 2);
    SingleBlockBrush.AIR.fill(worldEditor, RectSolid.newRect(start, end));

    cursor = at.copy();
    cursor.translate(entrance, 2);
    RepeaterBlock.repeater().setFacing(entrance).stroke(worldEditor, cursor);
    cursor.translate(entrance.antiClockwise(), 2);
    RepeaterBlock.repeater().setFacing(entrance).stroke(worldEditor, cursor);
    cursor.translate(entrance.clockwise(), 4);
    RepeaterBlock.repeater().setFacing(entrance).stroke(worldEditor, cursor);

    cursor = at.copy();
    cursor.translate(entrance.reverse(), 3);
    cursor.translate(entrance.antiClockwise());
    RepeaterBlock.repeater().setFacing(entrance.antiClockwise()).stroke(worldEditor, cursor);
    cursor.translate(entrance.clockwise(), 2);
    RepeaterBlock.repeater().setFacing(entrance.clockwise()).stroke(worldEditor, cursor);

    BlockBrush wire = BlockType.REDSTONE_WIRE.getBrush();

    start = at.copy();
    start.down(2);
    start.translate(entrance.clockwise());
    start.translate(entrance.reverse(), 2);
    end = start.copy();
    end.translate(entrance.antiClockwise(), 5);
    end.translate(entrance.reverse(), 5);
    end.down(2);
    BlockType.COBBLESTONE.getBrush().fill(worldEditor, RectSolid.newRect(start, end));

    cursor = at.copy();
    cursor.translate(entrance.reverse(), 3);
    cursor.down();
    TorchBlock.redstone().setFacing(Direction.UP).stroke(worldEditor, cursor);
    cursor.down();
    breadboard.stroke(worldEditor, cursor);
    cursor.translate(entrance.antiClockwise());
    TorchBlock.redstone().setFacing(entrance.antiClockwise()).stroke(worldEditor, cursor);
    cursor.translate(entrance.antiClockwise());
    wire.stroke(worldEditor, cursor);
    cursor.translate(entrance.reverse());
    wire.stroke(worldEditor, cursor);
    cursor.translate(entrance.reverse());
    wire.stroke(worldEditor, cursor);
    cursor.translate(entrance.clockwise());
    wire.stroke(worldEditor, cursor);
    cursor.translate(entrance.clockwise());
    wire.stroke(worldEditor, cursor);
    cursor.translate(entrance);
    RepeaterBlock.repeater()
        .setDelay(RepeaterBlock.Delay.FOUR)
        .setPowered(true)
        .setFacing(entrance)
        .stroke(worldEditor, cursor);
    cursor.up();
    cursor.translate(entrance.reverse());
    stainedHardenedClay().setColor(DyeColor.RED).stroke(worldEditor, cursor);
    cursor.up();
    LeverBlock.lever().setActive(true).setFacing(Direction.UP).stroke(worldEditor, cursor);

    BlockBrush light = primaryLightBrush();
    cursor = at.copy();
    cursor.translate(entrance.reverse(), 5);
    cursor.up(3);
    light.stroke(worldEditor, cursor);
    cursor.translate(entrance, 4);
    light.stroke(worldEditor, cursor);
    cursor.translate(entrance, 6);
    light.stroke(worldEditor, cursor);

    return this;
  }


  private void launcher(WorldEditor editor, Direction dir, Coord pos) {
    Coord cursor = pos.copy();
    BlockType.REDSTONE_WIRE.getBrush().stroke(editor, cursor);
    cursor.translate(dir.reverse());
    BlockType.REDSTONE_WIRE.getBrush().stroke(editor, cursor);
    cursor.translate(dir.reverse());
    RepeaterBlock.repeater().setFacing(dir).stroke(editor, cursor);
    cursor.translate(dir.reverse());
    cursor.up();

    BlockType.DROPPER.getBrush().setFacing(Direction.UP).stroke(editor, cursor);
    for (int i = 0; i < 8; ++i) {

      RldItemStack stick = Material.Type.STICK.asItem().asStack()
          .withDisplayName(Integer.toString(i))
          //.withDisplayLore(TextFormat.DARKGRAY.apply("Random logic unit"));
          .withDisplayLore("lore.roguelike.stick_rlu");
      editor.setItem(cursor, i, stick);
    }
    editor.setItem(cursor, 8, ToolType.HOE.asItem().withQuality(Quality.WOOD).asStack());

    cursor.up();
    BlockType.HOPPER.getBrush().setFacing(Direction.DOWN).stroke(editor, cursor);
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
      top.down();
      if (editor.isSolidBlock(top)) {
        break;
      }
    }

    if (top.getY() >= 100) {
      return;
    }

    Coord start = cursor.copy();
    start.up();


    start.translate(dir);
    Coord end = start.copy();

    BlockBrush breadboard = stainedHardenedClay().setColor(DyeColor.GREEN);


    boolean torch = false;
    while (end.getY() < top.getY()) {
      if (torch) {
        TorchBlock.redstone().setFacing(Direction.UP).stroke(editor, cursor);
      } else {
        breadboard.stroke(editor, cursor);
      }
      torch = !torch;
      cursor.up();
      end.up();
    }

    if (torch) {
      cursor.down();
    }

    BlockType.DISPENSER.getBrush().setFacing(Direction.UP).stroke(editor, cursor);
    for (int i = 0; i < 9; i++) {
      editor.setItem(cursor, i, createFireworks(editor.getRandom()));
    }

    cursor.up();
    BlockBrush cob = BlockType.COBBLESTONE.getBrush();
    RectSolid.newRect(start, end).fill(editor, cob);
    start.translate(dir.reverse(), 2);
    end.translate(dir.reverse(), 2);
    RectSolid.newRect(start, end).fill(editor, cob);
    start.translate(dir);
    end.translate(dir);
    Coord above = end.copy();
    above.up(10);
    for (Coord c : RectSolid.newRect(cursor, above)) {
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

  private RldItemStack createFireworks(Random random) {
    int stackSize = 16 + random.nextInt(16);
    return new Firework()
        .withExplosion(new Firework.Explosion()
            .withFlicker(random.nextBoolean())
            .withTrail(random.nextBoolean())
            .withColors(Firework.randomColors(random)))
        .withFlightLength(Firework.FlightLength.chooseRandom(random)).asStack().withCount(stackSize);
  }

}
