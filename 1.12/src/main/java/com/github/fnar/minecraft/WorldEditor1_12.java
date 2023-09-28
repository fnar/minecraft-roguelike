package com.github.fnar.minecraft;


import com.google.common.collect.Sets;

import com.github.fnar.minecraft.block.BlockMapper1_12;
import com.github.fnar.minecraft.block.BlockParser1_12;
import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.ColoredBlockMapper1_12;
import com.github.fnar.minecraft.block.DirectionMapper1_12;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.BedBlock;
import com.github.fnar.minecraft.block.decorative.Plant;
import com.github.fnar.minecraft.block.decorative.Skull;
import com.github.fnar.minecraft.block.spawner.SpawnPotentialMapper1_12;
import com.github.fnar.minecraft.block.spawner.Spawner;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.mapper.ItemMapper1_12;
import com.github.fnar.minecraft.item.mapper.PlantMapper1_12;
import com.github.fnar.minecraft.world.BiomeTag;
import com.github.fnar.minecraft.world.BiomeTagMapper1_12;
import com.github.fnar.minecraft.world.BlockPosMapper1_12;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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

import org.apache.commons.lang3.NotImplementedException;
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
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
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

  @Override
  public void setSkull(WorldEditor editor, Coord cursor, Direction dir, Skull type) {
    SingleBlockBrush skullBlock = BlockType.SKELETONS_SKULL.getBrush();
    // Makes the skull sit flush against the block below it.
    skullBlock.setFacing(Direction.UP);
    if (!skullBlock.stroke(editor, cursor)) {
      return;
    }

    TileEntity tileEntity = getTileEntity(cursor);
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
    return world.getBlockState(BlockPosMapper1_12.map(coord));
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
    return BlockMapper1_12.map(blockType.getBrush()).getBlock() == getBlockStateAt(coord).getBlock();
  }

  @Override
  public boolean isMaterialAt(com.github.fnar.minecraft.block.Material material, Coord coord) {
    // TODO: implement
    throw new NotImplementedException("Bad Fnar, bad!");
  }

  @Override
  public Random getRandom() {
    return random;
  }

  @Override
  public Random getRandom(Coord coord) {
    random.setSeed(getSeed(coord));
    return random;
  }

  @Override
  public boolean setBlock(Coord coord, SingleBlockBrush singleBlockBrush, boolean fillAir, boolean replaceSolid) {
    if (isBlockOfTypeAt(BlockType.CHEST, coord) ||
        isBlockOfTypeAt(BlockType.TRAPPED_CHEST, coord) ||
        isBlockOfTypeAt(BlockType.SPAWNER, coord)) {
      return false;
    }
    boolean isAir = isBlockOfTypeAt(BlockType.AIR, coord);
    if (!fillAir && isAir) {
      return false;
    }
    if (!replaceSolid && !isAir) {
      return false;
    }

    IBlockState state = singleBlockBrush.getJson() == null
        ? BlockMapper1_12.map(singleBlockBrush)
        : BlockParser1_12.parse(singleBlockBrush.getJson());
    world.setBlockState(BlockPosMapper1_12.map(coord), state, 2);

    setColorIfBed(coord, singleBlockBrush);

    BlockType blockType = singleBlockBrush.getBlockType();
    // block type is null when it's a block from JSON
    if (blockType != null) {
      stats.merge(blockType, 1, Integer::sum);
    }

    return true;
  }

  @Override
  public boolean isAirBlock(Coord coord) {
    return world.isAirBlock(BlockPosMapper1_12.map(coord));
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

  public TileEntity getTileEntity(Coord pos) {
    return world.getTileEntity(BlockPosMapper1_12.map(pos));
  }

  @Override
  public boolean isValidGroundBlock(Coord coord) {
    return validGroundBlocks.contains(getBlockStateAt(coord).getMaterial());
  }

  @Override
  public Map<BlockType, Integer> getStats() {
    return stats;
  }

  @Override
  public boolean isValidPosition(SingleBlockBrush block, Coord coord) {
    return BlockMapper1_12.map(block).getBlock().canPlaceBlockOnSide(world, BlockPosMapper1_12.map(coord), DirectionMapper1_12.map(block.getFacing()));
  }

  @Override
  public Coord findNearestStructure(VanillaStructure type, Coord coord, int radius) {
    ChunkProviderServer chunkProvider = ((WorldServer) world).getChunkProvider();
    String structureName = VanillaStructure.getName(type);
    BlockPos structureBlockPosition = chunkProvider.getNearestStructurePos(world, structureName, BlockPosMapper1_12.map(coord), false);
    return Optional.ofNullable(structureBlockPosition).map(BlockPosMapper1_12::map).orElse(null);
  }

  private void setColorIfBed(Coord coord, SingleBlockBrush singleBlockBrush) {
    TileEntity tileEntity = getTileEntity(coord);
    if (!singleBlockBrush.getBlockType().equals(BlockType.BED)) {
      return;
    }
    if (!(tileEntity instanceof TileEntityBed) || !(singleBlockBrush instanceof BedBlock)) {
      logger.error("Failed to paint bed at position {}. Current block at position is {}.", coord, getBlockStateAt(coord));
      return;
    }

    ((TileEntityBed) tileEntity).setColor(ColoredBlockMapper1_12.toEnumDyeColor(((BedBlock) singleBlockBrush).getColor()));
  }

  @Override
  public void setItem(Coord coord, int slot, RldItemStack itemStack) {
    TileEntity tileEntity = getTileEntity(coord);
    if (tileEntity == null) {
      return;
    }
    if (!(tileEntity instanceof TileEntityLockableLoot)) {
      return;
    }
    ItemStack forgeItemStack = new ItemMapper1_12().map(itemStack);
    try {
      ((TileEntityLockableLoot) tileEntity).setInventorySlotContents(slot, forgeItemStack);
    } catch (NullPointerException nullPointerException) {
      logger.error("Could not place item {} at position {}. BlockState at pos: {}.", forgeItemStack, coord, getBlockStateAt(coord));
    }
  }

  @Override
  public void setFlowerPotContent(Coord coord, Plant choice) {
    TileEntity potEntity = getTileEntity(coord);

    if (potEntity == null) {
      return;
    }
    if (!(potEntity instanceof TileEntityFlowerPot)) {
      return;
    }

    TileEntityFlowerPot flowerPot = (TileEntityFlowerPot) potEntity;

    ItemStack flowerItem = new PlantMapper1_12().map(choice);
    flowerPot.setItemStack(flowerItem);
  }

  @Override
  public void setLootTable(Coord coord, String table) {
    ((TileEntityChest) getTileEntity(coord)).setLootTable(new ResourceLocation(table), getSeed(coord));
  }

  @Override
  public int getCapacity(TreasureChest treasureChest) {
    TileEntity tileEntity = getTileEntity(treasureChest.getCoord());
    if (!(tileEntity instanceof TileEntityLockableLoot)) {
      return 0;
    }
    return ((TileEntityLockableLoot) tileEntity).getSizeInventory();
  }

  @Override
  public boolean isEmptySlot(TreasureChest treasureChest, int slot) {
    return ((TileEntityLockableLoot) getTileEntity(treasureChest.getCoord())).getStackInSlot(slot).isEmpty();
  }

  @Override
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

  public Biome getBiomeAt(Coord coord) {
    return world.getBiome(BlockPosMapper1_12.map(coord));
  }

  @Override
  public String toString() {
    return stats.entrySet().stream()
        .map(pair -> pair.getKey().toString() + ": " + pair.getValue() + "\n")
        .collect(Collectors.joining());
  }

}
