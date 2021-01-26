package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.decorative.BrewingStand;
import com.github.srwaggon.minecraft.block.decorative.Crop;
import com.github.srwaggon.minecraft.block.decorative.FlowerPotBlock;
import com.github.srwaggon.minecraft.block.decorative.TorchBlock;
import com.github.srwaggon.minecraft.block.normal.SlabBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class DungeonLab extends DungeonBase {

  public DungeonLab(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  private static void corner(WorldEditor editor, ThemeBase theme, int x, int y, int z) {

    BlockBrush doubleSlab = SlabBlock.stone().setTop(false).setFullBlock(true).setSeamless(true);
    BlockBrush cobble = BlockType.COBBLESTONE.getBrush();
    BlockBrush cyan = stainedHardenedClay().setColor(DyeColor.CYAN);

    // pillars
    pillar(editor, theme, x, y, z);
    pillar(editor, theme, x + 5, y, z);
    pillar(editor, theme, x, y, z + 5);
    pillar(editor, theme, x + 5, y, z + 5);

    // tile floor
    RectSolid.newRect(new Coord(x, y - 1, z), new Coord(x + 5, y - 1, z + 5)).fill(editor, cyan);
    RectSolid.newRect(new Coord(x + 1, y - 1, z + 2), new Coord(x + 4, y - 1, z + 3)).fill(editor, doubleSlab);
    RectSolid.newRect(new Coord(x + 2, y - 1, z + 1), new Coord(x + 3, y - 1, z + 4)).fill(editor, doubleSlab);

    // ceiling dome
    RectSolid.newRect(new Coord(x + 2, y + 4, z + 2), new Coord(x + 3, y + 8, z + 3)).fill(editor, SingleBlockBrush.AIR);
    SingleBlockBrush.AIR.stroke(editor, new Coord(x + 3, y + 4, z + 1));
    SingleBlockBrush.AIR.stroke(editor, new Coord(x + 4, y + 4, z + 1));
    SingleBlockBrush.AIR.stroke(editor, new Coord(x + 3, y + 4, z + 4));
    SingleBlockBrush.AIR.stroke(editor, new Coord(x + 4, y + 4, z + 4));

    SingleBlockBrush.AIR.stroke(editor, new Coord(x + 1, y + 4, z + 3));
    SingleBlockBrush.AIR.stroke(editor, new Coord(x + 1, y + 4, z + 4));
    SingleBlockBrush.AIR.stroke(editor, new Coord(x + 4, y + 4, z + 3));
    SingleBlockBrush.AIR.stroke(editor, new Coord(x + 4, y + 4, z + 4));

    RectHollow.newRect(new Coord(x + 1, y + 4, z + 1), new Coord(x + 4, y + 8, z + 4)).fill(editor, cobble, false, true);
    RectSolid.newRect(new Coord(x + 2, y + 8, z + 2), new Coord(x + 3, y + 8, z + 3)).fill(editor, SingleBlockBrush.AIR);
  }

  // fountains
  private static void southEast(WorldEditor editor, ThemeBase theme, int x, int y, int z) {

    BlockBrush stone = BlockType.STONE_BRICK.getBrush();
    StairsBlock stair = StairsBlock.stoneBrick();
    BlockBrush slab = SlabBlock.stoneBrick();
    BlockBrush water = BlockType.WATER_FLOWING.getBrush();

    corner(editor, theme, x, y, z);

    RectSolid.newRect(new Coord(x + 1, y, z + 5), new Coord(x + 4, y, z + 5)).fill(editor, stone);
    stair.setUpsideDown(false).setFacing(Direction.WEST).stroke(editor, new Coord(x + 1, y + 1, z + 5));
    water.stroke(editor, new Coord(x + 2, y + 1, z + 5));
    slab.stroke(editor, new Coord(x + 2, y + 2, z + 5));
    stair.setUpsideDown(false).setFacing(Direction.EAST).stroke(editor, new Coord(x + 3, y + 1, z + 5));

    RectSolid.newRect(new Coord(x + 5, y, z + 1), new Coord(x + 5, y, z + 4)).fill(editor, stone);
    stair.setUpsideDown(false).setFacing(Direction.NORTH).stroke(editor, new Coord(x + 5, y + 1, z + 1));
    water.stroke(editor, new Coord(x + 5, y + 1, z + 2));
    slab.stroke(editor, new Coord(x + 5, y + 2, z + 2));
    stair.setUpsideDown(false).setFacing(Direction.SOUTH).stroke(editor, new Coord(x + 5, y + 1, z + 3));

    RectSolid.newRect(new Coord(x + 3, y, z + 3), new Coord(x + 4, y, z + 4)).fill(editor, stone);
    TorchBlock.torch().setFacing(Direction.UP).stroke(editor, new Coord(x + 3, y + 1, z + 3));

    stair.setUpsideDown(false).setFacing(Direction.NORTH).stroke(editor, new Coord(x + 4, y, z + 1));
    stair.setUpsideDown(false).setFacing(Direction.WEST).stroke(editor, new Coord(x + 3, y, z + 2));
    stair.setUpsideDown(false).setFacing(Direction.NORTH).stroke(editor, new Coord(x + 2, y, z + 3));
    stair.setUpsideDown(false).setFacing(Direction.WEST).stroke(editor, new Coord(x + 1, y, z + 4));


  }

  private static void northWest(WorldEditor editor, ThemeBase theme, int x, int y, int z) {

    BlockBrush stone = BlockType.STONE_BRICK.getBrush();
    BlockBrush redstone = BlockType.REDSTONE_BLOCK.getBrush();
    BlockBrush lamp = BlockType.REDSTONE_LAMP_LIT.getBrush();
    BlockBrush farmland = BlockType.FARMLAND.getBrush();
    BlockBrush soul_sand = BlockType.SOUL_SAND.getBrush();

    corner(editor, theme, x, y, z);

    stone.stroke(editor, new Coord(x + 1, y, z));
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, new Coord(x + 1, y + 1, z));
    farmland.stroke(editor, new Coord(x + 2, y, z));
    Crop.CARROTS.getBrush().stroke(editor, new Coord(x + 2, y + 1, z));
    farmland.stroke(editor, new Coord(x + 3, y, z));
    Crop.CARROTS.getBrush().stroke(editor, new Coord(x + 3, y + 1, z));
    stone.stroke(editor, new Coord(x + 4, y, z));
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, new Coord(x + 4, y + 1, z));

    stone.stroke(editor, new Coord(x, y, z + 1));
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, new Coord(x, y + 1, z + 1));
    soul_sand.stroke(editor, new Coord(x, y, z + 2));
    Crop.NETHER_WART.getBrush().stroke(editor, new Coord(x, y + 1, z + 2));
    soul_sand.stroke(editor, new Coord(x, y, z + 3));
    Crop.NETHER_WART.getBrush().stroke(editor, new Coord(x, y + 1, z + 3));
    stone.stroke(editor, new Coord(x, y, z + 4));
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, new Coord(x, y + 1, z + 4));

    stone.stroke(editor, new Coord(x + 1, y, z + 1));

    StairsBlock stair = StairsBlock.stoneBrick();
    stair.setUpsideDown(false).setFacing(Direction.SOUTH).fill(editor, new RectSolid(new Coord(x + 2, y, z + 1), new Coord(x + 4, y, z + 1)));
    stair.setUpsideDown(false).setFacing(Direction.EAST).fill(editor, new RectSolid(new Coord(x + 1, y, z + 2), new Coord(x + 1, y, z + 4)));

    redstone.stroke(editor, new Coord(x + 2, y - 1, z + 2));
    lamp.stroke(editor, new Coord(x + 3, y - 1, z + 2));
    lamp.stroke(editor, new Coord(x + 2, y - 1, z + 3));

    BlockType.WATER_FLOWING.getBrush().stroke(editor, new Coord(x, y, z));
  }

  private static void northEast(WorldEditor editor, ThemeBase theme, int x, int y, int z) {

    BlockBrush stone = BlockType.STONE_BRICK.getBrush();
    BlockBrush redstone = BlockType.REDSTONE_BLOCK.getBrush();
    BlockBrush lamp = BlockType.REDSTONE_LAMP_LIT.getBrush();
    BlockBrush farmland = BlockType.FARMLAND.getBrush();

    corner(editor, theme, x, y, z);

    stone.stroke(editor, new Coord(x + 1, y, z));
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, new Coord(x + 1, y + 1, z));
    farmland.stroke(editor, new Coord(x + 2, y, z));
    Crop.MELON_STEM.getBrush().stroke(editor, new Coord(x + 2, y + 1, z));
    farmland.stroke(editor, new Coord(x + 3, y, z));
    stone.stroke(editor, new Coord(x + 4, y, z));
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, new Coord(x + 4, y + 1, z));

    stone.stroke(editor, new Coord(x + 5, y, z + 1));
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, new Coord(x + 5, y + 1, z + 1));
    farmland.stroke(editor, new Coord(x + 5, y, z + 2));
    // todo grow pumpkin stem
    Crop.PUMPKIN_STEM.getBrush().stroke(editor, new Coord(x + 5, y + 1, z + 2));

    farmland.stroke(editor, new Coord(x + 5, y, z + 3));
    stone.stroke(editor, new Coord(x + 5, y, z + 4));
    FlowerPotBlock.flowerPot().withRandomContent(editor.getRandom()).stroke(editor, new Coord(x + 5, y + 1, z + 4));

    stone.stroke(editor, new Coord(x + 4, y, z + 1));

    StairsBlock stair = StairsBlock.stoneBrick();

    stair.setUpsideDown(false).setFacing(Direction.SOUTH).fill(editor, new RectSolid(new Coord(x + 1, y, z + 1), new Coord(x + 3, y, z + 1)));
    stair.setUpsideDown(false).setFacing(Direction.WEST).fill(editor, new RectSolid(new Coord(x + 4, y, z + 2), new Coord(x + 4, y, z + 4)));

    redstone.stroke(editor, new Coord(x + 3, y - 1, z + 2));
    lamp.stroke(editor, new Coord(x + 2, y - 1, z + 2));
    lamp.stroke(editor, new Coord(x + 3, y - 1, z + 3));

    BlockType.WATER_FLOWING.getBrush().stroke(editor, new Coord(x + 5, y, z));
  }

  private static void pillar(WorldEditor editor, ThemeBase theme, int x, int y, int z) {

    theme.getSecondary().getPillar().fill(editor, new RectSolid(new Coord(x, y, z), new Coord(x, y + 2, z)));
    theme.getPrimary().getWall().stroke(editor, new Coord(x, y + 3, z));
    StairsBlock stair = theme.getSecondary().getStair();
    stair.setUpsideDown(true).setFacing(Direction.EAST).stroke(editor, new Coord(x + 1, y + 3, z));
    stair.setUpsideDown(true).setFacing(Direction.WEST).stroke(editor, new Coord(x - 1, y + 3, z));
    stair.setUpsideDown(true).setFacing(Direction.SOUTH).stroke(editor, new Coord(x, y + 3, z + 1));
    stair.setUpsideDown(true).setFacing(Direction.NORTH).stroke(editor, new Coord(x, y + 3, z - 1));

  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ThemeBase theme = levelSettings.getTheme();

    BlockBrush blocks = theme.getPrimary().getWall();


    // Air
    SingleBlockBrush.AIR.fill(worldEditor, new RectSolid(new Coord(x - 7, y, z - 7), new Coord(x + 7, y + 3, z + 7)));

    BlockBrush roof = theme.getSecondary().getWall();
    // Wood upper Roof
    RectSolid.newRect(new Coord(x - 6, y + 5, z - 6), new Coord(x + 6, y + 5, z + 6)).fill(worldEditor, roof);
    RectSolid.newRect(new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 4, z + 1)).fill(worldEditor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 5, y + 4, z - 1), new Coord(x - 3, y + 4, z + 1)).fill(worldEditor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x + 3, y + 4, z - 1), new Coord(x + 5, y + 4, z + 1)).fill(worldEditor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 1, y + 4, z - 5), new Coord(x + 1, y + 4, z - 3)).fill(worldEditor, SingleBlockBrush.AIR);
    RectSolid.newRect(new Coord(x - 1, y + 4, z + 3), new Coord(x + 1, y + 4, z + 5)).fill(worldEditor, SingleBlockBrush.AIR);

    // shell
    RectHollow.newRect(new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y + 4, z + 8)).fill(worldEditor, blocks, false, true);
    RectSolid.newRect(new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y - 1, z + 8)).fill(worldEditor, theme.getPrimary().getFloor(), false, true);


    // corner rooms
    southWest(worldEditor, theme, x - 7, y, z + 2);
    southEast(worldEditor, theme, x + 2, y, z + 2);
    northWest(worldEditor, theme, x - 7, y, z - 7);
    northEast(worldEditor, theme, x + 2, y, z - 7);

    // outer walls
    RectSolid.newRect(new Coord(x - 8, y, z - 7), new Coord(x - 8, y + 3, z - 7)).fill(worldEditor, blocks);
    RectSolid.newRect(new Coord(x + 8, y, z - 7), new Coord(x + 8, y + 3, z - 7)).fill(worldEditor, blocks);
    RectSolid.newRect(new Coord(x + 8, y, z - 7), new Coord(x + 8, y + 3, z - 7)).fill(worldEditor, blocks);

    BlockBrush backWalls = theme.getSecondary().getWall();

    // wall planks
    RectSolid.newRect(new Coord(x - 8, y + 1, z - 6), new Coord(x - 8, y + 3, z - 3)).fill(worldEditor, backWalls);
    RectSolid.newRect(new Coord(x - 8, y + 1, z + 3), new Coord(x - 8, y + 3, z + 6)).fill(worldEditor, backWalls);
    RectSolid.newRect(new Coord(x + 8, y + 1, z - 6), new Coord(x + 8, y + 3, z - 3)).fill(worldEditor, backWalls);
    RectSolid.newRect(new Coord(x + 8, y + 1, z + 3), new Coord(x + 8, y + 3, z + 6)).fill(worldEditor, backWalls);

    RectSolid.newRect(new Coord(x - 6, y + 1, z - 8), new Coord(x - 3, y + 3, z - 8)).fill(worldEditor, backWalls);
    RectSolid.newRect(new Coord(x + 3, y + 1, z - 8), new Coord(x + 6, y + 3, z - 8)).fill(worldEditor, backWalls);
    RectSolid.newRect(new Coord(x - 6, y + 1, z + 8), new Coord(x - 3, y + 3, z + 8)).fill(worldEditor, backWalls);
    RectSolid.newRect(new Coord(x + 3, y + 1, z + 8), new Coord(x + 6, y + 3, z + 8)).fill(worldEditor, backWalls);

    return this;
  }

  private void southWest(WorldEditor editor, ThemeBase theme, int x, int y, int z) {

    corner(editor, theme, x, y, z);

    StairsBlock stair = theme.getSecondary().getStair();
    stair.setUpsideDown(true).setFacing(Direction.NORTH);
    RectSolid.newRect(new Coord(x + 1, y, z + 5), new Coord(x + 4, y, z + 5)).fill(editor, stair);
    stair.setUpsideDown(true).setFacing(Direction.EAST);
    RectSolid.newRect(new Coord(x, y, z + 1), new Coord(x, y, z + 4)).fill(editor, stair);

    if (RogueConfig.GENEROUS.getBoolean()) {
      Coord bs = new Coord(x + 1, y + 1, z + 5);
      BlockType.BREWING_STAND.getBrush().stroke(editor, bs);
      editor.setItem(bs, BrewingStand.Slot.FUEL, new ItemStack(Items.BLAZE_POWDER));
    }
    editor.getTreasureChestEditor().createChest(new Coord(x, y + 1, z + 4), false, Dungeon.getLevel(y), getRoomSetting().getChestType().orElse(ChestType.BREWING));
  }

  public int getSize() {
    return 9;
  }

}
