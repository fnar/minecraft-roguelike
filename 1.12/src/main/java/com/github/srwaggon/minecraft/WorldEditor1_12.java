package com.github.srwaggon.minecraft;


import com.google.common.collect.Lists;

import com.github.srwaggon.minecraft.block.BlockMapper1_12;
import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.decorative.Plant;
import com.github.srwaggon.minecraft.block.decorative.Skull;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;
import com.github.srwaggon.minecraft.item.ItemMapper1_12;
import com.github.srwaggon.minecraft.item.RldItemStack;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import greymerk.roguelike.TreasureChestEditor;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.MetaBlock1_2;
import greymerk.roguelike.worldgen.PositionInfo;
import greymerk.roguelike.worldgen.VanillaStructure;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class WorldEditor1_12 implements WorldEditor {

  private static final List<Material> invalid = Lists.newArrayList(
      Material.AIR,
      Material.WOOD,
      Material.WATER,
      Material.CACTUS,
      Material.SNOW,
      Material.GRASS,
      Material.GOURD,
      Material.LEAVES,
      Material.PLANTS
  );
  private final World world;
  private final Map<Block, Integer> stats = new HashMap<>();
  private final TreasureChestEditor treasureChestEditor;
  private final Random random;

  public WorldEditor1_12(World world) {
    this.world = world;
    random = new Random(Objects.hash(getSeed()));
    treasureChestEditor = new TreasureChestEditor(this, random);
  }

  public void setSkull(WorldEditor editor, Coord cursor, Direction dir, Skull type) {
    SingleBlockBrush skullBlock = BlockType.SKULL.getBrush();
    // Makes the skull sit flush against the block below it.
    skullBlock.setFacing(Direction.UP);
    if (!skullBlock.stroke(editor, cursor)) {
      return;
    }

    TileEntity tileEntity = editor.getTileEntity(cursor);
    if (tileEntity == null) {
      return;
    }
    if (!(tileEntity instanceof TileEntitySkull)) {
      return;
    }

    TileEntitySkull tileEntitySkull = (TileEntitySkull) tileEntity;
    setSkullType(tileEntitySkull, type);
    setSkullRotation(editor.getRandom(cursor), tileEntitySkull, dir);
  }

  public static void setSkullType(TileEntitySkull skull, Skull type) {
    skull.setType(getSkullId(type));
  }

  public static void setSkullRotation(Random rand, TileEntitySkull skull, Direction dir) {

    int directionValue = getDirectionValue(dir);

    // nudge the skull so that it isn't perfectly aligned.
    directionValue += -1 + rand.nextInt(3);

    // make sure the skull direction value is less than 16
    directionValue = directionValue % 16;

    skull.setSkullRotation(directionValue);
  }

  public static int getSkullId(Skull type) {
    switch (type) {
      default:
      case SKELETON:
        return 0;
      case WITHER:
        return 1;
      case ZOMBIE:
        return 2;
      case STEVE:
        return 3;
      case CREEPER:
        return 4;
    }
  }

  public static int getDirectionValue(Direction dir) {
    switch (dir) {
      default:
      case NORTH:
        return 0;
      case EAST:
        return 4;
      case SOUTH:
        return 8;
      case WEST:
        return 12;
    }
  }

  @Override
  public boolean isSolidBlock(Coord coord) {
    return getBlockStateAt(coord).getMaterial().isSolid();
  }

  private IBlockState getBlockStateAt(Coord coord) {
    return world.getBlockState(getBlockPos(coord));
  }

  @Override
  public boolean isOpaqueBlock(Coord coord) {
    return getBlockStateAt(coord).getMaterial().isOpaque();
  }

  @Override
  public boolean isOpaqueCubeBlock(Coord coord) {
    return getBlockStateAt(coord).isOpaqueCube();
  }

  @Override
  public boolean isBlockOfTypeAt(BlockType blockType, Coord coord) {
    return BlockMapper1_12.map(blockType).getBlock() == getBlockStateAt(coord).getBlock();
  }

  @Override
  public boolean isMaterialAt(com.github.srwaggon.minecraft.block.Material material, Coord coord) {
    return false;
  }

  @Override
  public Random getRandom() {
    return random;
  }

  private BlockPos getBlockPos(Coord pos) {
    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
  }

  @Override
  public boolean setBlock(Coord coord, SingleBlockBrush singleBlockBrush, boolean fillAir, boolean replaceSolid) {

    if (isBlockOfTypeAt(BlockType.CHEST, coord) ||
        isBlockOfTypeAt(BlockType.TRAPPED_CHEST, coord) ||
        isBlockOfTypeAt(BlockType.MOB_SPAWNER, coord)) {
      return false;
    }
    boolean isAir = isBlockOfTypeAt(BlockType.AIR, coord);
    if (!fillAir && isAir) {
      return false;
    }
    if (!replaceSolid && !isAir) {
      return false;
    }

    return setBlock(MetaBlock1_2.getMetaBlock(singleBlockBrush), coord);
  }

  private boolean setBlock(MetaBlock1_2 metaBlock, Coord coord) {
    try {
      world.setBlockState(getBlockPos(coord), metaBlock.getState(), metaBlock.getFlag());
    } catch (NullPointerException npe) {
      System.out.println(npe);
    }

    stats.merge(metaBlock.getBlock(), 1, Integer::sum);

    return true;
  }

  @Override
  public boolean isAirBlock(Coord pos) {
    return world.isAirBlock(getBlockPos(pos));
  }

  @Override
  public long getSeed() {
    return world.getSeed();
  }

  @Override
  public Random getSeededRandom(int a, int b, int c) {
    return world.setRandomSeed(a, b, c);
  }

  @Override
  public void spiralStairStep(Random rand, Coord origin, StairsBlock stair, BlockBrush fill) {
    Coord cursor;
    Coord start;
    Coord end;

    start = origin.copy();
    start.translate(new Coord(-1, 0, -1));
    end = origin.copy();
    end.translate(new Coord(1, 0, 1));

    RectSolid.newRect(start, end).fill(this, SingleBlockBrush.AIR);
    fill.stroke(this, origin);

    Direction dir = Direction.CARDINAL.get(origin.getY() % 4);
    cursor = origin.copy();
    cursor.translate(dir);
    stair.setUpsideDown(false).setFacing(dir.antiClockwise()).stroke(this, cursor);
    cursor.translate(dir.clockwise());
    stair.setUpsideDown(true).setFacing(dir.clockwise()).stroke(this, cursor);
    cursor.translate(dir.reverse());
    stair.setUpsideDown(true).setFacing(dir.reverse()).stroke(this, cursor);
  }

  @Override
  public void fillDown(Coord origin, BlockBrush blocks) {

    Coord cursor = origin.copy();

    while (!isOpaqueCubeBlock(cursor) && cursor.getY() > 1) {
      blocks.stroke(this, cursor);
      cursor.down();
    }
  }

  @Override
  public TileEntity getTileEntity(Coord pos) {
    return world.getTileEntity(getBlockPos(pos));
  }

  @Override
  public boolean validGroundBlock(Coord pos) {
    return !invalid.contains(getBlockStateAt(pos).getMaterial());
  }

  @Override
  public int getStat(Block type) {
    if (!stats.containsKey(type)) {
      return 0;
    }
    return stats.get(type);
  }

  @Override
  public Map<Block, Integer> getStats() {
    return stats;
  }

  @Override
  public boolean canPlace(SingleBlockBrush block, Coord pos, Direction dir) {
    return isAirBlock(pos)
        && BlockMapper1_12.map(block.getBlockType()).getBlock().canPlaceBlockOnSide(world, getBlockPos(pos), dir.getFacing());
  }

  @Override
  public PositionInfo getInfo(Coord pos) {
    return new PositionInfo1_12(world, pos);
  }

  @Override
  public Coord findNearestStructure(VanillaStructure type, Coord pos) {

    ChunkProviderServer chunkProvider = ((WorldServer) world).getChunkProvider();
    String structureName = VanillaStructure.getName(type);

    BlockPos structurebp = null;

    try {
      structurebp = chunkProvider.getNearestStructurePos(world, structureName, getBlockPos(pos), false);
    } catch (NullPointerException e) {
      // happens for some reason if structure type is disabled in Chunk Generator Settings
    }

    if (structurebp == null) {
      return null;
    }

    return new Coord(structurebp.getX(), structurebp.getY(), structurebp.getZ());
  }

  @Override
  public String toString() {
    return stats.entrySet().stream()
        .map(pair -> pair.getKey().getLocalizedName() + ": " + pair.getValue() + "\n")
        .collect(Collectors.joining());
  }

  @Override
  public TreasureChestEditor getTreasureChestEditor() {
    return treasureChestEditor;
  }

  @Override
  public void setBedColorAt(Coord cursor, DyeColor color) {
    ((TileEntityBed) getTileEntity(cursor)).setColor(DyeColor.get(color));
  }

  @Override
  public void setItem(Coord pos, int slot, RldItemStack itemStack) {
    setItem(pos, slot, ItemMapper1_12.map(itemStack));
  }

  @Override
  public void setItem(Coord pos, int slot, ItemStack itemStack) {
    TileEntity tileEntity = getTileEntity(pos);
    if (tileEntity == null) {
      return;
    }
    if (!(tileEntity instanceof TileEntityLockableLoot)) {
      return;
    }
    ((TileEntityLockableLoot) tileEntity).setInventorySlotContents(slot, itemStack);
  }

  @Override
  public void setFlowerPotContent(Coord pos, Plant choice) {
    TileEntity potEntity = getTileEntity(pos);

    if (potEntity == null) {
      return;
    }
    if (!(potEntity instanceof TileEntityFlowerPot)) {
      return;
    }

    TileEntityFlowerPot flowerPot = (TileEntityFlowerPot) potEntity;

    ItemStack flowerItem = getFlowerItem(choice);
    flowerPot.setItemStack(flowerItem);
  }

  private static ItemStack getFlowerItem(Plant type) {
    switch (type) {
      case POPPY:
        return new ItemStack(Blocks.RED_FLOWER, 1, 0);
      case ORCHID:
        return new ItemStack(Blocks.RED_FLOWER, 1, 1);
      case ALLIUM:
        return new ItemStack(Blocks.RED_FLOWER, 1, 2);
      case BLUET:
        return new ItemStack(Blocks.RED_FLOWER, 1, 3);
      case REDTULIP:
        return new ItemStack(Blocks.RED_FLOWER, 1, 4);
      case ORANGETULIP:
        return new ItemStack(Blocks.RED_FLOWER, 1, 5);
      case WHITETULIP:
        return new ItemStack(Blocks.RED_FLOWER, 1, 6);
      case PINKTULIP:
        return new ItemStack(Blocks.RED_FLOWER, 1, 7);
      case DAISY:
        return new ItemStack(Blocks.RED_FLOWER, 1, 8);
      case RED_MUSHROOM:
        return new ItemStack(Blocks.RED_MUSHROOM);
      case BROWN_MUSHROOM:
        return new ItemStack(Blocks.BROWN_MUSHROOM);
      case CACTUS:
        return new ItemStack(Blocks.CACTUS);
      case OAK_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 0);
      case SPRUCE_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 1);
      case BIRCH_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 2);
      case JUNGLE_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 3);
      case ACACIA_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 4);
      case DARKOAK_SAPLING:
        return new ItemStack(Blocks.SAPLING, 1, 5);
      case SHRUB:
        return new ItemStack(Blocks.TALLGRASS, 1, 0);
      case FERN:
        return new ItemStack(Blocks.TALLGRASS, 1, 2);
      case DANDELION:
      default:
        return new ItemStack(Blocks.YELLOW_FLOWER);
    }
  }

  @Override
  public void setLootTable(Coord pos, String table) {
    ((TileEntityChest) getTileEntity(pos)).setLootTable(new ResourceLocation(table), getSeed());
  }

  public int getCapacity(TreasureChest treasureChest) {
    return ((TileEntityLockableLoot) getTileEntity(treasureChest.getPos())).getSizeInventory();
  }

  public boolean isEmptySlot(TreasureChest treasureChest, int slot) {
    return ((TileEntityLockableLoot) getTileEntity(treasureChest.getPos())).getStackInSlot(slot).isEmpty();
  }
}

