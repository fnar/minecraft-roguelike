package com.github.fnar.minecraft;

import com.google.common.collect.Sets;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.Plant;
import com.github.fnar.minecraft.block.decorative.Skull;
import com.github.fnar.minecraft.block.spawner.SpawnPotentialMapper1_14;
import com.github.fnar.minecraft.block.spawner.Spawner;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.world.BiomeTag;
import com.github.fnar.minecraft.world.BiomeTagMapper1_14;
import com.github.fnar.minecraft.world.BlockPosMapper1_14;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.spawner.AbstractSpawner;
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
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.VanillaStructure;
import greymerk.roguelike.worldgen.WorldEditor;

import static greymerk.roguelike.dungeon.Dungeon.MOD_ID;
import static greymerk.roguelike.dungeon.Dungeon.getLevel;

public class WorldEditor1_14 implements WorldEditor {

  private static final Logger logger = LogManager.getLogger(MOD_ID);

  private static final Set<Material> validGroundBlocks = Sets.newHashSet(
      Material.ORGANIC,
      Material.EARTH,
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

  public WorldEditor1_14(World world) {
    this.world = world;
    random = new Random(Objects.hash(getSeed()));
    treasureManager = new TreasureManager(random);
  }

  public TileEntity getTileEntity(Coord pos) {
    return world.getTileEntity(BlockPosMapper1_14.map(pos));
  }

  @Override
  public void generateSpawner(Spawner spawner, Coord cursor) {
    Coord pos = cursor.copy();

    spawner.stroke(this, pos);

    TileEntity tileentity = getTileEntity(pos);
    if (!(tileentity instanceof MobSpawnerTileEntity)) {
      return;
    }

    CompoundNBT nbt = new CompoundNBT();
    nbt.putInt("x", pos.getX());
    nbt.putInt("y", pos.getY());
    nbt.putInt("z", pos.getZ());

    nbt.put("SpawnPotentials", SpawnPotentialMapper1_14.mapToNbt(spawner.getPotentials(), getRandom(), getLevel(pos.getY())));

    MobSpawnerTileEntity tileEntity = (MobSpawnerTileEntity) tileentity;
    AbstractSpawner spawnerLogic = tileEntity.getSpawnerBaseLogic();
    spawnerLogic.read(nbt);
    spawnerLogic.tick();
    tileentity.markDirty();
  }

  @Override
  public boolean isSolidBlock(Coord coord) {
    return getBlockStateAt(coord).getMaterial().isSolid();
  }

  private BlockState getBlockStateAt(Coord coord) {
    return world.getBlockState(BlockPosMapper1_14.map(coord));
  }

  @Override
  public boolean isOpaqueBlock(Coord coord) {
    return getBlockStateAt(coord).getMaterial().isOpaque();
  }

  @Override
  public boolean isOpaqueCubeBlock(Coord coord) {
    return getBlockStateAt(coord).isOpaqueCube(world, BlockPosMapper1_14.map(coord));
  }

  @Override
  public boolean isBlockOfTypeAt(BlockType blockType, Coord coord) {
    return false;
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
  public Random getRandom(Coord coord) {
    random.setSeed(getSeed(coord));
    return random;
  }

  @Override
  public boolean setBlock(Coord coord, SingleBlockBrush singleBlockBrush, boolean fillAir, boolean replaceSolid) {
//    if (isBlockOfTypeAt(BlockType.CHEST, coord) ||
//        isBlockOfTypeAt(BlockType.TRAPPED_CHEST, coord) ||
//        isBlockOfTypeAt(BlockType.MOB_SPAWNER, coord)) {
//      return false;
//    }
//    boolean isAir = isBlockOfTypeAt(BlockType.AIR, coord);
//    if (!fillAir && isAir) {
//      return false;
//    }
//    if (!replaceSolid && !isAir) {
//      return false;
//    }
//
//    MetaBlock1_14 metaBlock = getMetaBlock(singleBlockBrush);
//    try {
//      world.setBlockState(BlockPosMapper1_14.map(coord), metaBlock.getState(), metaBlock.getFlag());
//    } catch (NullPointerException npe) {
//      LogManager.getLogger(MOD_ID).error(npe);
//    }
//
//    BlockType blockType = singleBlockBrush.getBlockType();
//    // block type is null when it's a block from JSON
//    if (blockType != null) {
//      stats.merge(blockType, 1, Integer::sum);
//    }
//
//    return true;
    throw new NotImplementedException("Not yet implemented");
  }

  @Override
  public boolean isAirBlock(Coord coord) {
    return world.isAirBlock(BlockPosMapper1_14.map(coord));
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
  public boolean isValidGroundBlock(Coord coord) {
    return validGroundBlocks.contains(getBlockStateAt(coord).getMaterial());
  }

  @Override
  public Map<BlockType, Integer> getStats() {
    return stats;
  }

  @Override
  public boolean canPlace(SingleBlockBrush block, Coord coord, Direction dir) {
    // todo: can we map the `block` here instead of block.getType()?
//    return isAirBlock(coord)
//        && BlockMapper1_14.map(block.getBlockType()).getBlock().canPlaceBlockOnSide(world, BlockPosMapper1_14.map(coord), BlockMapper1_12.getFacing(dir));
    throw new NotImplementedException("Not yet implemented");
  }

  @Override
  public Coord findNearestStructure(VanillaStructure type, Coord coord, int radius) {
    String structureName = VanillaStructure.getName(type);
    BlockPos structureBlockPosition = world.findNearestStructure(structureName, BlockPosMapper1_14.map(coord), radius, false);
    return Optional.ofNullable(structureBlockPosition).map(BlockPosMapper1_14::map).orElse(null);
  }

  @Override
  public void setBedColorAt(Coord cursor, DyeColor color) {
//    TileEntity tileEntity = getTileEntity(cursor);
//    if (tileEntity instanceof BedTileEntity) {
//      ((BedTileEntity) tileEntity).setColor(BlockMapper1_14.toEnumDyeColor(color));
//    } else {
//      logger.error("Failed to paint bed at position {} to become color {}. Current block at position is {}.", cursor, color, getBlockStateAt(cursor));
//    }
    throw new NotImplementedException("Not yet implemented");
  }

  @Override
  public void setItem(Coord coord, int slot, RldItemStack itemStack) {
//    TileEntity tileEntity = getTileEntity(coord);
//    if (tileEntity == null) {
//      return;
//    }
//    if (!(tileEntity instanceof LockableLootTileEntity)) {
//      return;
//    }
//    ItemStack forgeItemStack = new ItemMapper1_14().map(itemStack);
//    try {
//      ((LockableLootTileEntity) tileEntity).setInventorySlotContents(slot, forgeItemStack);
//    } catch (NullPointerException nullPointerException) {
//      logger.error("Could not place item {} at position {}. BlockState at pos: {}.", forgeItemStack, coord, getBlockStateAt(coord));
//    }
    throw new NotImplementedException("Not yet implemented");
  }

  @Override
  public void setFlowerPotContent(Coord coord, Plant choice) {
//    TileEntity potEntity = getTileEntity(coord);
//
//    if (potEntity == null) {
//      return;
//    }
//    if (!(potEntity instanceof FlowerPotTileEntity)) {
//      return;
//    }
//
//    TileEntityFlowerPot flowerPot = (TileEntityFlowerPot) potEntity;
//
//    ItemStack flowerItem = new PlantMapper1_14().map(choice);
//    flowerPot.setItemStack(flowerItem);
//
//    ///////
//    Supplier<Block> flowerDelegate = () -> Blocks.CACTUS;
//    new FlowerPotBlock(null, flowerDelegate, null);
    throw new NotImplementedException("Not yet implemented");
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
    if (!(tileEntity instanceof SkullTileEntity)) {
      return;
    }

    SkullTileEntity tileEntitySkull = (SkullTileEntity) tileEntity;
    setSkullType(tileEntitySkull, type);
    setSkullRotation(editor.getRandom(), tileEntitySkull, dir);
  }

  public static void setSkullType(SkullTileEntity skull, Skull type) {

  }

  public static void setSkullRotation(Random rand, SkullTileEntity skull, Direction dir) {

  }

  @Override
  public void setLootTable(Coord coord, String table) {
    ((ChestTileEntity) getTileEntity(coord)).setLootTable(new ResourceLocation(table), getSeed(coord));
  }

  public int getCapacity(TreasureChest treasureChest) {
    return ((LockableLootTileEntity) getTileEntity(treasureChest.getCoord())).getSizeInventory();
  }

  public boolean isEmptySlot(TreasureChest treasureChest, int slot) {
    return ((LockableLootTileEntity) getTileEntity(treasureChest.getCoord())).getStackInSlot(slot).isEmpty();
  }

  @Override
  public TreasureManager getTreasureManager() {
    return treasureManager;
  }

  @Override
  public int getDimension() {
    return world.getDimension().getType().getId();
  }

  @Override
  public boolean isBiomeTypeAt(BiomeTag biomeTag, Coord coord) {
    return BiomeDictionary.hasType(getBiomeAt(coord), BiomeTagMapper1_14.toBiomeDictionaryType(biomeTag));
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
    return world.getBiome(BlockPosMapper1_14.map(coord));
  }

  @Override
  public String toString() {
    return stats.entrySet().stream()
        .map(pair -> pair.getKey().toString() + ": " + pair.getValue() + "\n")
        .collect(Collectors.joining());
  }

}
