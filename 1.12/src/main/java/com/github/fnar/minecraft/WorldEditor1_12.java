package com.github.fnar.minecraft;


import com.google.common.collect.Sets;
import com.google.gson.JsonElement;

import com.github.fnar.minecraft.block.BlockMapper1_12;
import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.Plant;
import com.github.fnar.minecraft.block.decorative.Skull;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;
import com.github.fnar.minecraft.block.spawner.SpawnPotentialMapper1_12;
import com.github.fnar.minecraft.block.spawner.Spawner;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.mapper.ItemMapper1_12;
import com.github.fnar.minecraft.world.BiomeTagMapper1_12;
import com.github.fnar.minecraft.world.BiomeTag;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.BiomeDictionary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.MetaBlock1_12;
import greymerk.roguelike.worldgen.VanillaStructure;
import greymerk.roguelike.worldgen.WorldEditor;

import static greymerk.roguelike.dungeon.Dungeon.MOD_ID;
import static greymerk.roguelike.dungeon.Dungeon.getLevel;

public class WorldEditor1_12 implements WorldEditor {

  private static final Logger logger = LogManager.getLogger(MOD_ID);

  private static final Set<Material> validGroundBlocks = Sets.newHashSet(
      Material.GRASS,
      Material.GROUND,
      Material.ROCK,
      Material.SAND,
      Material.ICE,
      Material.PACKED_ICE,
      Material.SNOW,
      Material.CLAY
  );

  private final World world;
  private final Map<BlockType, Integer> stats = new HashMap<>();
  private final Random random;
  private final TreasureManager treasureManager;

  public WorldEditor1_12(World world) {
    this.world = world;
    random = new Random(Objects.hash(getSeed()));
    treasureManager = new TreasureManager(random);
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
    setSkullRotation(editor.getRandom(), tileEntitySkull, dir);
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
  public boolean isMaterialAt(com.github.fnar.minecraft.block.Material material, Coord coord) {
    return false;
  }

  @Override
  public Random getRandom() {
    return random;
  }

  @Override
  public Random getRandom(Coord pos) {
    random.setSeed(getSeed(pos));
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

    MetaBlock1_12 metaBlock = getMetaBlock(singleBlockBrush);
    try {
      world.setBlockState(getBlockPos(coord), metaBlock.getState(), metaBlock.getFlag());
    } catch (NullPointerException npe) {
      LogManager.getLogger(MOD_ID).error(npe);
    }

    BlockType blockType = singleBlockBrush.getBlockType();
    // block type is null when it's a block from JSON
    if (blockType != null) {
      stats.merge(blockType, 1, Integer::sum);
    }

    return true;
  }

  private static MetaBlock1_12 getMetaBlock(SingleBlockBrush singleBlockBrush) {
    JsonElement json = singleBlockBrush.getJson();
    if (json == null) {
      return BlockMapper1_12.map(singleBlockBrush);
    }
    if (singleBlockBrush instanceof StairsBlock) {
      return BlockMapper1_12.mapStairs((StairsBlock) singleBlockBrush);
    }
    if (singleBlockBrush instanceof DoorBlock) {
      return BlockMapper1_12.mapDoor((DoorBlock) singleBlockBrush);
    }
    return new MetaBlock1_12(json);
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
  public boolean isValidGroundBlock(Coord pos) {
    return validGroundBlocks.contains(getBlockStateAt(pos).getMaterial());
  }

  @Override
  public Map<BlockType, Integer> getStats() {
    return stats;
  }

  @Override
  public boolean canPlace(SingleBlockBrush block, Coord pos, Direction dir) {
    // todo: can we map the `block` here instead of block.getType()?
    return isAirBlock(pos)
        && BlockMapper1_12.map(block.getBlockType()).getBlock().canPlaceBlockOnSide(world, getBlockPos(pos), BlockMapper1_12.getFacing(dir));
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
        .map(pair -> pair.getKey().toString() + ": " + pair.getValue() + "\n")
        .collect(Collectors.joining());
  }

  @Override
  public void setBedColorAt(Coord cursor, DyeColor color) {
    TileEntity tileEntity = getTileEntity(cursor);
    if (tileEntity instanceof TileEntityBed) {
      ((TileEntityBed) tileEntity).setColor(BlockMapper1_12.toEnumDyeColor(color));
    } else {
      logger.error("Failed to paint bed at position {} to become color {}. Current block at position is {}.", cursor, color, getBlockStateAt(cursor));
    }
  }

  @Override
  public void setItem(Coord pos, int slot, RldItemStack itemStack) {
    setItem(pos, slot, new ItemMapper1_12().map(itemStack));
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
    try {
      ((TileEntityLockableLoot) tileEntity).setInventorySlotContents(slot, itemStack);
    } catch (NullPointerException nullPointerException) {
      logger.error("Could not place item {} at position {}. BlockState at pos: {}.", itemStack, pos, getBlockStateAt(pos));
    }
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
    ((TileEntityChest) getTileEntity(pos)).setLootTable(new ResourceLocation(table), getSeed(pos));
  }

  public int getCapacity(TreasureChest treasureChest) {
    return ((TileEntityLockableLoot) getTileEntity(treasureChest.getCoord())).getSizeInventory();
  }

  public boolean isEmptySlot(TreasureChest treasureChest, int slot) {
    return ((TileEntityLockableLoot) getTileEntity(treasureChest.getCoord())).getStackInSlot(slot).isEmpty();
  }

  public void generateSpawner(Spawner spawner, Coord cursor) {
    Coord pos = cursor.copy();

    spawner.stroke(this, pos);

    TileEntity tileentity = getTileEntity(pos);
    if (!(tileentity instanceof TileEntityMobSpawner)) {
      return;
    }

    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("x", pos.getX());
    nbt.setInteger("y", pos.getY());
    nbt.setInteger("z", pos.getZ());

    nbt.setTag("SpawnPotentials", SpawnPotentialMapper1_12.mapToNbt(spawner.getPotentials(), getRandom(), getLevel(pos.getY())));

    TileEntityMobSpawner tileEntity = (TileEntityMobSpawner) tileentity;
    MobSpawnerBaseLogic spawnerLogic = tileEntity.getSpawnerBaseLogic();
    spawnerLogic.readFromNBT(nbt);
    spawnerLogic.updateSpawner();
    tileentity.markDirty();
  }

  @Override
  public TreasureManager getTreasureManager() {
    return treasureManager;
  }

  public Biome getBiomeAt(Coord coord) {
    return world.getBiome(new BlockPos(coord.getX(), coord.getY(), coord.getZ()));
  }

  @Override
  public int getDimension() {
    return world.provider.getDimension();
  }

  @Override
  public boolean isBiomeTypeAt(BiomeTag biomeTag, Coord coord) {
    return BiomeDictionary.hasType(getBiomeAt(coord), BiomeTagMapper1_12.toBiomeDictionaryType(biomeTag));
  }

  @Override
  public String getBiomeName(Coord coord) {
    ResourceLocation registryName = getBiomeAt(coord).getRegistryName();
    if (Optional.ofNullable(registryName).isPresent()) {
      return registryName.toString();
    }
    // TODO: Consider if returning empty string is appropriate, or default biome instead
    return "";
  }

  @Override
  public List<String> getBiomeTagNames(Coord pos) {
    return BiomeDictionary.getTypes(getBiomeAt(pos)).stream()
        .map(type -> type.getName() + " ")
        .collect(Collectors.toList());
  }

}
