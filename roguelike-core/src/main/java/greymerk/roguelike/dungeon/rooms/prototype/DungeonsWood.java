package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.Wood;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsWood extends DungeonBase {

  public DungeonsWood(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {
    Random random = worldEditor.getRandom(origin);
    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    final int HEIGHT = 3;
    final int WIDTH = random.nextInt(2) + 2;
    final int LENGTH = random.nextInt(2) + 3;

    Wood wood = Wood.values()[random.nextInt(Wood.values().length)];
    BlockBrush pillar = wood.getLog();
    BlockBrush planks = Wood.OAK.getPlanks();

    BlockBrush glowstone = levelSettings.getTheme().getPrimary().getLightBlock();


    RectSolid.newRect(new Coord(x - WIDTH, y, z - LENGTH), new Coord(x + WIDTH, y + HEIGHT, z + LENGTH)).fill(worldEditor, SingleBlockBrush.AIR);
    RectHollow.newRect(new Coord(x - WIDTH - 1, y - 1, z - LENGTH - 1), new Coord(x + WIDTH + 1, y + HEIGHT + 1, z + LENGTH + 1)).fill(worldEditor, planks, false, true);

    // log beams
    RectSolid.newRect(new Coord(x - WIDTH, y, z - LENGTH), new Coord(x - WIDTH, y + HEIGHT, z - LENGTH)).fill(worldEditor, pillar);
    RectSolid.newRect(new Coord(x - WIDTH, y, z + LENGTH), new Coord(x - WIDTH, y + HEIGHT, z + LENGTH)).fill(worldEditor, pillar);
    RectSolid.newRect(new Coord(x + WIDTH, y, z - LENGTH), new Coord(x + WIDTH, y + HEIGHT, z - LENGTH)).fill(worldEditor, pillar);
    RectSolid.newRect(new Coord(x + WIDTH, y, z + LENGTH), new Coord(x + WIDTH, y + HEIGHT, z + LENGTH)).fill(worldEditor, pillar);

    // glowstone
    glowstone.stroke(worldEditor, new Coord(x - WIDTH + 1, y - 1, z - LENGTH + 1));
    glowstone.stroke(worldEditor, new Coord(x - WIDTH + 1, y - 1, z + LENGTH - 1));
    glowstone.stroke(worldEditor, new Coord(x + WIDTH - 1, y - 1, z - LENGTH + 1));
    glowstone.stroke(worldEditor, new Coord(x + WIDTH - 1, y - 1, z + LENGTH - 1));

    planks.stroke(worldEditor, new Coord(x, y, z));
    BlockType.CAKE.getBrush().stroke(worldEditor, new Coord(x, y + 1, z));

    List<Coord> spaces = new ArrayList<>();
    spaces.add(new Coord(x - WIDTH, y, z - LENGTH + 1));
    spaces.add(new Coord(x - WIDTH, y, z + LENGTH - 1));
    spaces.add(new Coord(x + WIDTH, y, z - LENGTH + 1));
    spaces.add(new Coord(x + WIDTH, y, z + LENGTH - 1));

    List<Coord> chestLocations = chooseRandomLocations(1, spaces);
    worldEditor.getTreasureChestEditor().createChests(chestLocations, false, Dungeon.getLevel(y), getRoomSetting().getChestType().orElse(ChestType.FOOD));
    return this;
  }

  public int getSize() {
    return 6;
  }

}
