package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.normal.Wood;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsWood extends DungeonBase {

  public DungeonsWood(RoomSetting roomSetting) {
    super(roomSetting);
  }

  @Override
  public DungeonBase generate(WorldEditor editor, LevelSettings settings, Coord origin, List<Cardinal> entrances) {
    Random random = editor.getRandom();
    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    final int HEIGHT = 3;
    final int WIDTH = random.nextInt(2) + 2;
    final int LENGTH = random.nextInt(2) + 3;

    Wood wood = Wood.values()[random.nextInt(Wood.values().length)];
    BlockBrush pillar = wood.getLog();
    BlockBrush planks = Wood.OAK.getPlanks();

    BlockBrush glowstone = settings.getTheme().getPrimary().getLightBlock();


    RectSolid.newRect(new Coord(x - WIDTH, y, z - LENGTH), new Coord(x + WIDTH, y + HEIGHT, z + LENGTH)).fill(editor, SingleBlockBrush.AIR);
    RectHollow.newRect(new Coord(x - WIDTH - 1, y - 1, z - LENGTH - 1), new Coord(x + WIDTH + 1, y + HEIGHT + 1, z + LENGTH + 1)).fill(editor, planks, false, true);

    // log beams
    RectSolid.newRect(new Coord(x - WIDTH, y, z - LENGTH), new Coord(x - WIDTH, y + HEIGHT, z - LENGTH)).fill(editor, pillar);
    RectSolid.newRect(new Coord(x - WIDTH, y, z + LENGTH), new Coord(x - WIDTH, y + HEIGHT, z + LENGTH)).fill(editor, pillar);
    RectSolid.newRect(new Coord(x + WIDTH, y, z - LENGTH), new Coord(x + WIDTH, y + HEIGHT, z - LENGTH)).fill(editor, pillar);
    RectSolid.newRect(new Coord(x + WIDTH, y, z + LENGTH), new Coord(x + WIDTH, y + HEIGHT, z + LENGTH)).fill(editor, pillar);

    // glowstone
    glowstone.stroke(editor, new Coord(x - WIDTH + 1, y - 1, z - LENGTH + 1));
    glowstone.stroke(editor, new Coord(x - WIDTH + 1, y - 1, z + LENGTH - 1));
    glowstone.stroke(editor, new Coord(x + WIDTH - 1, y - 1, z - LENGTH + 1));
    glowstone.stroke(editor, new Coord(x + WIDTH - 1, y - 1, z + LENGTH - 1));

    planks.stroke(editor, new Coord(x, y, z));
    BlockType.CAKE.getBrush().stroke(editor, new Coord(x, y + 1, z));

    List<Coord> spaces = new ArrayList<>();
    spaces.add(new Coord(x - WIDTH, y, z - LENGTH + 1));
    spaces.add(new Coord(x - WIDTH, y, z + LENGTH - 1));
    spaces.add(new Coord(x + WIDTH, y, z - LENGTH + 1));
    spaces.add(new Coord(x + WIDTH, y, z + LENGTH - 1));

    List<Coord> chestLocations = chooseRandomLocations(random, 1, spaces);
    editor.getTreasureChestEditor().createChests(Dungeon.getLevel(y), chestLocations, false, getRoomSetting().getChestType().orElse(ChestType.FOOD));
    return this;
  }

  public int getSize() {
    return 6;
  }

}