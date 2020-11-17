package greymerk.roguelike.worldgen;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.treasure.ChestPlacementException;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class WorldEditor {

  private static final List<Material> invalid;
  private final World world;
  private final Map<Block, Integer> stats = new HashMap<>();
  private final TreasureManager chests = new TreasureManager();

  static {
    invalid = new ArrayList<>();
    invalid.add(Material.WOOD);
    invalid.add(Material.WATER);
    invalid.add(Material.CACTUS);
    invalid.add(Material.SNOW);
    invalid.add(Material.GRASS);
    invalid.add(Material.GOURD);
    invalid.add(Material.LEAVES);
    invalid.add(Material.PLANTS);
  }

  public WorldEditor(World world) {
    this.world = world;
  }

  private boolean setBlock(Coord pos, MetaBlock block, int flags, boolean fillAir, boolean replaceSolid) {

    MetaBlock currentBlock = getBlock(pos);

    if (currentBlock.getBlock() == Blocks.CHEST) {
      return false;
    }
    if (currentBlock.getBlock() == Blocks.TRAPPED_CHEST) {
      return false;
    }
    if (currentBlock.getBlock() == Blocks.MOB_SPAWNER) {
      return false;
    }

    //boolean isAir = world.isAirBlock(pos.getBlockPos());
    boolean isAir = currentBlock.getBlock() == Blocks.AIR;

    if (!fillAir && isAir) {
      return false;
    }
    if (!replaceSolid && !isAir) {
      return false;
    }

    try {
      world.setBlockState(pos.getBlockPos(), block.getState(), flags);
    } catch (NullPointerException npe) {
      //ignore it.
    }

    Block type = block.getBlock();
    stats.merge(type, 1, Integer::sum);
    return true;

  }

  public boolean setBlock(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid) {
    return setBlock(pos, block, block.getFlag(), fillAir, replaceSolid);
  }

  public boolean isAirBlock(Coord pos) {
    return world.isAirBlock(pos.getBlockPos());
  }

  public long getSeed() {
    return world.getSeed();
  }

  public Random getSeededRandom(int a, int b, int c) {
    return world.setRandomSeed(a, b, c);
  }

  public void spiralStairStep(Random rand, Coord origin, IStair stair, IBlockFactory fill) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.translate(new Coord(-1, 0, -1));
    end = new Coord(origin);
    end.translate(new Coord(1, 0, 1));

    RectSolid.fill(this, rand, start, end, air);
    fill.set(this, rand, origin);

    Cardinal dir = Cardinal.directions[origin.getY() % 4];
    cursor = new Coord(origin);
    cursor.translate(dir);
    stair.setOrientation(dir.antiClockwise(), false).set(this, cursor);
    cursor.translate(dir.clockwise());
    stair.setOrientation(dir.clockwise(), true).set(this, cursor);
    cursor.translate(dir.reverse());
    stair.setOrientation(dir.reverse(), true).set(this, cursor);
  }

  public void fillDown(Random rand, Coord origin, IBlockFactory blocks) {

    Coord cursor = new Coord(origin);

    while (!getBlock(cursor).isOpaqueCube() && cursor.getY() > 1) {
      blocks.set(this, rand, cursor);
      cursor.translate(Cardinal.DOWN);
    }
  }

  public MetaBlock getBlock(Coord pos) {
    return new MetaBlock(world.getBlockState(pos.getBlockPos()));
  }

  public TileEntity getTileEntity(Coord pos) {
    return world.getTileEntity(pos.getBlockPos());
  }

  public boolean validGroundBlock(Coord pos) {
    if (isAirBlock(pos)) {
      return false;
    }
    return !invalid.contains(getBlock(pos).getMaterial());
  }

  public int getStat(Block type) {
    if (!stats.containsKey(type)) {
      return 0;
    }
    return stats.get(type);
  }

  public Map<Block, Integer> getStats() {
    return stats;
  }

  public TreasureManager getTreasure() {
    return chests;
  }

  public boolean canPlace(MetaBlock block, Coord pos, Cardinal dir) {
    if (!isAirBlock(pos)) {
      return false;
    }
    return block.getBlock().canPlaceBlockOnSide(world, pos.getBlockPos(), dir.getFacing());
  }

  public IPositionInfo getInfo(Coord pos) {
    return new PositionInfo(world, pos);
  }

  public Coord findNearestStructure(VanillaStructure type, Coord pos) {

    ChunkProviderServer chunkProvider = ((WorldServer) world).getChunkProvider();
    String structureName = VanillaStructure.getName(type);

    BlockPos structurebp = null;

    try {
      structurebp = chunkProvider.getNearestStructurePos(world, structureName, pos.getBlockPos(), false);
    } catch (NullPointerException e) {
      // happens for some reason if structure type is disabled in Chunk Generator Settings
    }

    if (structurebp == null) {
      return null;
    }

    return new Coord(structurebp);
  }

  @Override
  public String toString() {
    return stats.entrySet().stream()
        .map(pair -> pair.getKey().getLocalizedName() + ": " + pair.getValue() + "\n")
        .collect(Collectors.joining());
  }

  public TreasureChest generate(Random random, Coord pos, boolean isTrapped, Treasure treasureType, int level) throws ChestPlacementException {
    MetaBlock chestType = new MetaBlock(isTrapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST);

    boolean success = chestType.set(this, pos);

    if (!success) {
      throw new ChestPlacementException("Failed to place chest in world");
    }

    TileEntityChest tileEntityChest = (TileEntityChest) getTileEntity(pos);
    int seed = Objects.hash(pos.hashCode(), getSeed());
    TreasureChest treasureChest = new TreasureChest(
        treasureType,
        level,
        isTrapped,
        random,
        tileEntityChest,
        seed
    );
    chests.add(treasureChest);
    return treasureChest;
  }
}

