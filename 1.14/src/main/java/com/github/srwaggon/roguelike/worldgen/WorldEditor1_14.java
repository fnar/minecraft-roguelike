package com.github.srwaggon.roguelike.worldgen;

import com.google.common.collect.Lists;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.decorative.Plant;
import com.github.srwaggon.minecraft.block.decorative.Skull;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;
import com.github.srwaggon.minecraft.item.RldItemStack;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import greymerk.roguelike.TreasureChestEditor;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.PositionInfo;
import greymerk.roguelike.worldgen.VanillaStructure;
import greymerk.roguelike.worldgen.WorldEditor;

public class WorldEditor1_14 implements WorldEditor {

  private static final List<Material> invalid = Lists.newArrayList(
      Material.WOOD,
      Material.WATER,
      Material.CACTUS,
      Material.SNOW,
      Material.ORGANIC,
      Material.GOURD,
      Material.LEAVES,
      Material.PLANTS
  );
  private final World world;
  private final Map<Block, Integer> stats = new HashMap<>();
  private final TreasureChestEditor treasureChestEditor;
  private final Random random;

  public WorldEditor1_14(World world) {
    this.world = world;
    random = new Random(Objects.hash(getSeed()));
    treasureChestEditor = new TreasureChestEditor(this, random);
  }

  @Override
  public boolean isSolidBlock(Coord coord) {
    return getBlockState(coord).isSolid();
  }

  private BlockState getBlockState(Coord coord) {
    return world.getBlockState(getBlockPos(coord));
  }

  @Override
  public boolean isOpaqueBlock(Coord coord) {
    return false;
  }

  @Override
  public boolean isOpaqueCubeBlock(Coord coord) {
    return false;
  }

  @Override
  public boolean isBlockOfTypeAt(BlockType blockType, Coord coord) {
    return false;
  }

  @Override
  public boolean isMaterialAt(com.github.srwaggon.minecraft.block.Material material, Coord coord) {
    return false;
  }

  @Override
  public Random getRandom() {
    return random;
  }

  @Override
  public boolean setBlock(Coord pos, SingleBlockBrush singleBlockBrush, boolean fillAir, boolean replaceSolid) {
    return false;
  }

  @Override
  public boolean isAirBlock(Coord pos) {
    return false;
  }

  @Override
  public long getSeed() {
    return 0;
  }

  @Override
  public Random getSeededRandom(int a, int b, int c) {
    return null;
  }

  @Override
  public void spiralStairStep(Random rand, Coord origin, StairsBlock stair, BlockBrush fill) {

  }

  @Override
  public void fillDown(Coord origin, BlockBrush blocks) {

  }

  private BlockPos getBlockPos(Coord pos) {
    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
  }

  @Override
  public TileEntity getTileEntity(Coord pos) {
    return null;
  }

  @Override
  public boolean validGroundBlock(Coord pos) {
    return false;
  }

  @Override
  public int getStat(Block type) {
    return 0;
  }

  @Override
  public Map<Block, Integer> getStats() {
    return null;
  }

  @Override
  public boolean canPlace(SingleBlockBrush block, Coord pos, Direction dir) {
    return false;
  }

  @Override
  public PositionInfo getInfo(Coord pos) {
    return null;
  }

  @Override
  public Coord findNearestStructure(VanillaStructure type, Coord pos) {
    return null;
  }

  @Override
  public TreasureChestEditor getTreasureChestEditor() {
    return treasureChestEditor;
  }

  @Override
  public void setBedColorAt(Coord cursor, DyeColor color) {

  }

  @Override
  public void setItem(Coord pos, int slot, ItemStack item) {

  }

  @Override
  public void setItem(Coord pos, int slot, RldItemStack itemStack) {

  }

  @Override
  public void setFlowerPotContent(Coord pos, Plant choice) {

  }

  @Override
  public void setSkull(WorldEditor editor, Coord cursor, Direction dir, Skull type) {

  }

  @Override
  public void setLootTable(Coord pos, String table) {

  }

  public int getCapacity(TreasureChest treasureChest) {
    return 0;
  }

  public boolean isEmptySlot(TreasureChest treasureChest, int slot) {
    return false;
  }
}
