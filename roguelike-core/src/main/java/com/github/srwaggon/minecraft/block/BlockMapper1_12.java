package com.github.srwaggon.minecraft.block;

import com.google.gson.JsonElement;

import com.github.srwaggon.minecraft.block.decorative.AnvilBlock;
import com.github.srwaggon.minecraft.block.decorative.BedBlock;
import com.github.srwaggon.minecraft.block.decorative.CropBlock;
import com.github.srwaggon.minecraft.block.decorative.Plant;
import com.github.srwaggon.minecraft.block.decorative.PlantBlock;
import com.github.srwaggon.minecraft.block.decorative.PumpkinBlock;
import com.github.srwaggon.minecraft.block.decorative.TallPlant;
import com.github.srwaggon.minecraft.block.decorative.TallPlantBlock;
import com.github.srwaggon.minecraft.block.decorative.TorchBlock;
import com.github.srwaggon.minecraft.block.normal.ColoredBlock;
import com.github.srwaggon.minecraft.block.normal.InfestedBlock;
import com.github.srwaggon.minecraft.block.normal.Quartz;
import com.github.srwaggon.minecraft.block.normal.SlabBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;
import com.github.srwaggon.minecraft.block.normal.Stone;
import com.github.srwaggon.minecraft.block.normal.Wood;
import com.github.srwaggon.minecraft.block.redstone.ComparatorBlock;
import com.github.srwaggon.minecraft.block.redstone.DoorBlock;
import com.github.srwaggon.minecraft.block.redstone.LeverBlock;
import com.github.srwaggon.minecraft.block.redstone.RepeaterBlock;
import com.github.srwaggon.minecraft.block.redstone.TrapdoorBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.MetaBlock1_2;

public class BlockMapper1_12 {

  public static MetaBlock1_2 map(SingleBlockBrush block) {
    Direction facing = block.getFacing();

    EnumFacing enumFacing = facing.getFacing();

    switch (block.getBlockType()) {
      case ANVIL:
        return getAnvil((AnvilBlock) block);
      case ACACIA_LOG:
        return getLogMetaBlock(Wood.ACACIA, facing);
      case BIRCH_LOG:
        return getLogMetaBlock(Wood.BIRCH, facing);
      case DARK_OAK_LOG:
        return getLogMetaBlock(Wood.DARK_OAK, facing);
      case JUNGLE_LOG:
        return getLogMetaBlock(Wood.JUNGLE, facing);
      case OAK_LOG:
        return getLogMetaBlock(Wood.OAK, facing);
      case SPRUCE_LOG:
        return getLogMetaBlock(Wood.SPRUCE, facing);
      case ACACIA_FENCE:
        return getFenceMetaBlock(Wood.ACACIA);
      case BIRCH_FENCE:
        return getFenceMetaBlock(Wood.BIRCH);
      case DARK_OAK_FENCE:
        return getFenceMetaBlock(Wood.DARK_OAK);
      case JUNGLE_FENCE:
        return getFenceMetaBlock(Wood.JUNGLE);
      case OAK_FENCE:
        return getFenceMetaBlock(Wood.OAK);
      case SPRUCE_FENCE:
        return getFenceMetaBlock(Wood.SPRUCE);
      case ACACIA_PLANK:
        return getPlankMetaBlock(Wood.ACACIA);
      case BIRCH_PLANK:
        return getPlankMetaBlock(Wood.BIRCH);
      case DARK_OAK_PLANK:
        return getPlankMetaBlock(Wood.DARK_OAK);
      case JUNGLE_PLANK:
        return getPlankMetaBlock(Wood.JUNGLE);
      case OAK_PLANK:
        return getPlankMetaBlock(Wood.OAK);
      case SPRUCE_PLANK:
        return getPlankMetaBlock(Wood.SPRUCE);
      case OAK_LEAVES:
        return getLeaves(Wood.OAK, true);
      case SPRUCE_LEAVES:
        return getLeaves(Wood.SPRUCE, true);
      case BIRCH_LEAVES:
        return getLeaves(Wood.BIRCH, true);
      case JUNGLE_LEAVES:
        return getLeaves(Wood.JUNGLE, true);
      case ACACIA_LEAVES2:
        return getLeaves(Wood.ACACIA, true);
      case DARK_OAK_LEAVES:
        return getLeaves(Wood.DARK_OAK, true);
      case CARPET:
      case CONCRETE:
      case CONCRETE_POWDER:
      case STAINED_GLASS:
      case STAINED_GLASS_PANE:
      case STAINED_HARDENED_CLAY:
      case TERRACOTTA:
      case WOOL:
        return getColoredBlock((ColoredBlock) block);
      case ACACIA_STAIRS:
      case BIRCH_STAIRS:
      case BRICK_STAIRS:
      case DARK_OAK_STAIRS:
      case JUNGLE_STAIRS:
      case NETHER_BRICK_STAIRS:
      case OAK_STAIRS:
      case PURPUR_STAIRS:
      case QUARTZ_STAIRS:
      case RED_SANDSTONE_STAIRS:
      case SANDSTONE_STAIRS:
      case SPRUCE_STAIRS:
      case STONE_BRICK_STAIRS:
      case STONE_STAIRS:
        return mapStairs((StairsBlock) block);
      case ACACIA_SLAB:
      case BIRCH_SLAB:
      case BRICK_SLAB:
      case COBBLE_SLAB:
      case DARK_OAK_SLAB:
      case JUNGLE_SLAB:
      case LEGACY_OAK_SLAB:
      case NETHERBRICK_SLAB:
      case OAK_SLAB:
      case QUARTZ_SLAB:
      case RED_SANDSTONE_SLAB:
      case SANDSTONE_SLAB:
      case SMOOTH_RED_SANDSTONE_SLAB:
      case SPRUCE_SLAB:
      case STONEBRICK_SLAB:
      case STONE_SLAB:
        return getSlab((SlabBlock) block);
      case IRON_DOOR:
      case OAK_DOOR:
      case BIRCH_DOOR:
      case SPRUCE_DOOR:
      case JUNGLE_DOOR:
      case ACACIA_DOOR:
      case DARK_OAK_DOOR:
      case WARPED_DOOR:
        return mapDoor((DoorBlock) block);
      case CHISELED_QUARTZ:
        return getQuartz(Quartz.CHISELED, facing);
      case PILLAR_QUARTZ:
        return getQuartz(Quartz.PILLAR, facing);
      case SMOOTH_QUARTZ:
        return getQuartz(Quartz.SMOOTH, facing);
      case VINE:
        return createVine(facing);
      case CROP:
        return getCrop((CropBlock) block);
      case PUMPKIN:
        return getPumpkin((PumpkinBlock) block);
      case FLOWER_POT:
        return new MetaBlock1_2(Blocks.FLOWER_POT);
      case BROWN_MUSHROOM:
        return new MetaBlock1_2(Blocks.BROWN_MUSHROOM);
      case RED_MUSHROOM:
        return new MetaBlock1_2(Blocks.RED_MUSHROOM);
      case PLANT:
        return getPlant((PlantBlock) block);
      case TALL_PLANT:
        return getTallPlant((TallPlantBlock) block);
      case TRAPDOOR:
        return getTrapdoor((TrapdoorBlock) block);
      case LEVER:
        return getLever((LeverBlock) block);
      case TORCH:
      case REDSTONE_TORCH:
        return getTorch((TorchBlock) block);
      case BED:
        return getBed((BedBlock) block);
      case FURNACE:
        if (!RogueConfig.FURNITURE.getBoolean()) {
          return new MetaBlock1_2(Blocks.COBBLESTONE);
        }
        return new MetaBlock1_2(Blocks.FURNACE)
            .withProperty(BlockFurnace.FACING, enumFacing);
      case DISPENSER:
        return new MetaBlock1_2(Blocks.DISPENSER)
            .withProperty(BlockDispenser.FACING, enumFacing);
      case DROPPER:
        return new MetaBlock1_2(Blocks.DROPPER)
            .withProperty(BlockDropper.FACING, enumFacing);
      case HOPPER:
        return new MetaBlock1_2(Blocks.HOPPER)
            .withProperty(BlockHopper.FACING, enumFacing);
      case COMPARATOR:
        return getComparator((ComparatorBlock) block);
      case REPEATER:
        return getRepeater((RepeaterBlock) block);
      case PISTON:
        return new MetaBlock1_2(Blocks.PISTON)
            .withProperty(BlockPistonBase.FACING, enumFacing);
      case STICKY_PISTON:
        return new MetaBlock1_2(Blocks.STICKY_PISTON)
            .withProperty(BlockPistonBase.FACING, enumFacing);
      case INFESTED_BLOCK:
        return getInfestedBlock((InfestedBlock) block);
      case CHEST:
        return new MetaBlock1_2(Blocks.CHEST)
            .withProperty(BlockChest.FACING, facing.getFacing());
      case TRAPPED_CHEST:
        return new MetaBlock1_2(Blocks.TRAPPED_CHEST)
            .withProperty(BlockChest.FACING, facing.getFacing());
      default:
        return map(block.getBlockType());
    }
  }

  private static MetaBlock1_2 getCrop(CropBlock cropBlock) {
    switch (cropBlock.getCrop()) {
      case BEETROOTS:
        return new MetaBlock1_2(Blocks.BEETROOTS);
      case CARROTS:
        return new MetaBlock1_2(Blocks.CARROTS);
      case COCOA:
        return getCocoaBlock(cropBlock.getFacing());
      case MELON_STEM:
        return new MetaBlock1_2(Blocks.MELON_STEM);
      case NETHER_WART:
        return new MetaBlock1_2(Blocks.NETHER_WART);
      case POTATOES:
        return new MetaBlock1_2(Blocks.POTATOES);
      case PUMPKIN_STEM:
        return new MetaBlock1_2(Blocks.PUMPKIN_STEM);
      default:
      case WHEAT:
        return new MetaBlock1_2(Blocks.WHEAT);
    }
  }

  private static MetaBlock1_2 getRepeater(RepeaterBlock block) {
    return new MetaBlock1_2(block.isPowered() ? Blocks.POWERED_REPEATER : Blocks.UNPOWERED_REPEATER)
        .withProperty(BlockRedstoneRepeater.DELAY, block.getDelay())
        .withProperty(BlockRedstoneRepeater.FACING, block.getFacing().getFacing());
  }

  private static MetaBlock1_2 getComparator(ComparatorBlock block) {
    return new MetaBlock1_2(block.isPowered() ? Blocks.POWERED_COMPARATOR : Blocks.UNPOWERED_COMPARATOR)
        .withProperty(BlockRedstoneComparator.FACING, block.getFacing().getFacing())
        .withProperty(BlockRedstoneComparator.MODE, block.getMode() == ComparatorBlock.Mode.SUBTRACTION
            ? BlockRedstoneComparator.Mode.SUBTRACT
            : BlockRedstoneComparator.Mode.COMPARE);
  }

  private static MetaBlock1_2 getCocoaBlock(Direction facing) {
    return new MetaBlock1_2(Blocks.COCOA)
        .withProperty(BlockCocoa.FACING, facing.reverse().getFacing())
        .withProperty(BlockCocoa.AGE, 2);
  }

  private static MetaBlock1_2 getLever(LeverBlock leverBlock) {
    Direction dir = leverBlock.getFacing();
    return new MetaBlock1_2(Blocks.LEVER)
        .withProperty(BlockLever.POWERED, leverBlock.isActive())
        .withProperty(BlockLever.FACING, dir == Direction.UP
            ? BlockLever.EnumOrientation.UP_X
            : dir == Direction.DOWN
                ? BlockLever.EnumOrientation.DOWN_X
                : dir.reverse().getOrientation());
  }

  public static MetaBlock1_2 map(BlockType blockType) {
    switch (blockType) {
      case WATER_STILL:
        return new MetaBlock1_2(Blocks.WATER);
      case WATER_FLOWING:
        return new MetaBlock1_2(Blocks.FLOWING_WATER);
      case LAVA_STILL:
        return new MetaBlock1_2(Blocks.LAVA);
      case LAVA_FLOWING:
        return new MetaBlock1_2(Blocks.FLOWING_LAVA);
      case FIRE:
        return new MetaBlock1_2(Blocks.FIRE);
      case IRON_BAR:
        return new MetaBlock1_2(Blocks.IRON_BARS);
      case STONE_SMOOTH:
        return new MetaBlock1_2(Blocks.STONE);
      case GRANITE:
        return new MetaBlock1_2(Blocks.STONE, BlockStone.VARIANT, BlockStone.EnumType.GRANITE);
      case GRANITE_POLISHED:
        return new MetaBlock1_2(Blocks.STONE, BlockStone.VARIANT, BlockStone.EnumType.GRANITE_SMOOTH);
      case DIORITE:
        return new MetaBlock1_2(Blocks.STONE, BlockStone.VARIANT, BlockStone.EnumType.DIORITE);
      case DIORITE_POLISHED:
        return new MetaBlock1_2(Blocks.STONE, BlockStone.VARIANT, BlockStone.EnumType.DIORITE_SMOOTH);
      case ANDESITE:
        return new MetaBlock1_2(Blocks.STONE, BlockStone.VARIANT, BlockStone.EnumType.ANDESITE);
      case ANDESITE_POLISHED:
        return new MetaBlock1_2(Blocks.STONE, BlockStone.VARIANT, BlockStone.EnumType.ANDESITE_SMOOTH);
      case GRASS:
        return new MetaBlock1_2(Blocks.GRASS);
      case DIRT:
        return new MetaBlock1_2(Blocks.DIRT);
      case DIRT_COARSE:
        return new MetaBlock1_2(Blocks.DIRT, BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
      case DIRT_PODZOL:
        return new MetaBlock1_2(Blocks.DIRT, BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
      case COBBLESTONE:
        return new MetaBlock1_2(Blocks.COBBLESTONE);
      case COBBLESTONE_WALL:
        return new MetaBlock1_2(Blocks.COBBLESTONE_WALL);
      case BED:
        return new MetaBlock1_2(Blocks.BED);
      case BEDROCK:
        return new MetaBlock1_2(Blocks.BEDROCK);
      case SAND:
        return new MetaBlock1_2(Blocks.SAND);
      case SAND_RED:
        return new MetaBlock1_2(Blocks.SAND, BlockSand.VARIANT, BlockSand.EnumType.RED_SAND);
      case GRAVEL:
        return new MetaBlock1_2(Blocks.GRAVEL);
      case ORE_GOLD:
        return new MetaBlock1_2(Blocks.GOLD_ORE);
      case ORE_IRON:
        return new MetaBlock1_2(Blocks.IRON_ORE);
      case ORE_COAL:
        return new MetaBlock1_2(Blocks.COAL_BLOCK);
      case GLASS:
        return new MetaBlock1_2(Blocks.GLASS);
      case GLASS_PANE:
        return new MetaBlock1_2(Blocks.GLASS_PANE);
      case STAINED_GLASS:
        return map(BlockType.STAINED_GLASS.getBrush());
      case STAINED_GLASS_PANE:
        return map(BlockType.STAINED_GLASS_PANE.getBrush());
      case ORE_LAPIS:
        return new MetaBlock1_2(Blocks.LAPIS_ORE);
      case LAPIS_BLOCK:
        return new MetaBlock1_2(Blocks.LAPIS_BLOCK);
      case ORE_EMERALD:
        return new MetaBlock1_2(Blocks.EMERALD_ORE);
      case SANDSTONE:
        return new MetaBlock1_2(Blocks.SANDSTONE);
      case CHISELED_SANDSTONE:
        return new MetaBlock1_2(Blocks.SANDSTONE, BlockSandStone.TYPE, BlockSandStone.EnumType.CHISELED);
      case SANDSTONE_SMOOTH:
        return new MetaBlock1_2(Blocks.SANDSTONE, BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH);
      case GOLD_BLOCK:
        return new MetaBlock1_2(Blocks.GOLD_BLOCK);
      case IRON_BLOCK:
        return new MetaBlock1_2(Blocks.IRON_BLOCK);
      case BRICK:
        return new MetaBlock1_2(Blocks.BRICK_BLOCK);
      case COBBLESTONE_MOSSY:
        return new MetaBlock1_2(Blocks.MOSSY_COBBLESTONE);
      case OBSIDIAN:
        return new MetaBlock1_2(Blocks.OBSIDIAN);
      case ORE_DIAMOND:
        return new MetaBlock1_2(Blocks.DIAMOND_ORE);
      case DIAMOND_BLOCK:
        return new MetaBlock1_2(Blocks.DIAMOND_BLOCK);
      case FARMLAND:
        return new MetaBlock1_2(Blocks.FARMLAND);
      case ORE_REDSTONE:
        return new MetaBlock1_2(Blocks.REDSTONE_ORE);
      case ICE:
        return new MetaBlock1_2(Blocks.ICE);
      case SNOW:
        return new MetaBlock1_2(Blocks.SNOW);
      case CLAY:
        return new MetaBlock1_2(Blocks.CLAY);
      case NETHERRACK:
        return new MetaBlock1_2(Blocks.NETHERRACK);
      case SOUL_SAND:
        return new MetaBlock1_2(Blocks.SOUL_SAND);
      case GLOWSTONE:
        return new MetaBlock1_2(Blocks.GLOWSTONE);
      case STONE_BRICK:
        return new MetaBlock1_2(Blocks.STONEBRICK);
      case STONE_BRICK_MOSSY:
        return new MetaBlock1_2(Blocks.STONEBRICK, BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
      case STONE_BRICK_CRACKED:
        return new MetaBlock1_2(Blocks.STONEBRICK, BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED);
      case STONE_BRICK_CHISELED:
        return new MetaBlock1_2(Blocks.STONEBRICK, BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
      case MYCELIUM:
        return new MetaBlock1_2(Blocks.MYCELIUM);
      case NETHERBRICK:
        return new MetaBlock1_2(Blocks.NETHER_BRICK);
      case END_STONE:
        return new MetaBlock1_2(Blocks.END_STONE);
      case EMERALD_BLOCK:
        return new MetaBlock1_2(Blocks.EMERALD_BLOCK);
      case ORE_QUARTZ:
        return new MetaBlock1_2(Blocks.QUARTZ_ORE);
      case PRISMITE:
        return new MetaBlock1_2(Blocks.PRISMARINE, BlockPrismarine.VARIANT, BlockPrismarine.EnumType.ROUGH);
      case PRISMARINE:
        return new MetaBlock1_2(Blocks.PRISMARINE, BlockPrismarine.VARIANT, BlockPrismarine.EnumType.BRICKS);
      case PRISMARINE_DARK:
        return new MetaBlock1_2(Blocks.PRISMARINE, BlockPrismarine.VARIANT, BlockPrismarine.EnumType.DARK);
      case SEA_LANTERN:
        return new MetaBlock1_2(Blocks.SEA_LANTERN);
      case COAL_BLOCK:
        return new MetaBlock1_2(Blocks.COAL_BLOCK);
      case ICE_PACKED:
        return new MetaBlock1_2(Blocks.PACKED_ICE);
      case RED_SANDSTONE:
        return new MetaBlock1_2(Blocks.RED_SANDSTONE);
      case CHISELED_RED_SANDSTONE:
        return new MetaBlock1_2(Blocks.RED_SANDSTONE, BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.CHISELED);
      case SMOOTH_RED_SANDSTONE:
        return new MetaBlock1_2(Blocks.RED_SANDSTONE, BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH);
      case QUARTZ:
        return new MetaBlock1_2(Blocks.QUARTZ_BLOCK);
      case REDSTONE_BLOCK:
        return new MetaBlock1_2(Blocks.REDSTONE_BLOCK);
      case PRESSURE_PLATE_STONE:
        return new MetaBlock1_2(Blocks.STONE_PRESSURE_PLATE);
      case PRESSURE_PLATE_WOODEN:
        return new MetaBlock1_2(Blocks.WOODEN_PRESSURE_PLATE);
      case BOOKSHELF:
        return new MetaBlock1_2(Blocks.BOOKSHELF);
      case REDSTONE_WIRE:
        return new MetaBlock1_2(Blocks.REDSTONE_WIRE);
      case REEDS:
        return new MetaBlock1_2(Blocks.REEDS);
      case CRAFTING_TABLE:
        return (!RogueConfig.FURNITURE.getBoolean()
            ? getPlankMetaBlock(Wood.OAK)
            : new MetaBlock1_2(Blocks.CRAFTING_TABLE));
      case NOTEBLOCK:
        return new MetaBlock1_2(Blocks.NOTEBLOCK);
      case REDSTONE_LAMP:
        return new MetaBlock1_2(Blocks.REDSTONE_LAMP);
      case REDSTONE_LAMP_LIT:
        return new MetaBlock1_2(Blocks.LIT_REDSTONE_LAMP);
      case JUKEBOX:
        return new MetaBlock1_2(Blocks.JUKEBOX);
      case TNT:
        return new MetaBlock1_2(Blocks.TNT);
      case ENCHANTING_TABLE:
        return new MetaBlock1_2(Blocks.ENCHANTING_TABLE);
      case FENCE_NETHER_BRICK:
        return new MetaBlock1_2(Blocks.NETHER_BRICK_FENCE);
      case WEB:
        return new MetaBlock1_2(Blocks.WEB);
      case VINE:
        return new MetaBlock1_2(Blocks.VINE);
      case PURPUR_BLOCK:
        return new MetaBlock1_2(Blocks.PURPUR_BLOCK);
      case PURPUR_PILLAR:
        return new MetaBlock1_2(Blocks.PURPUR_PILLAR);
      case PURPUR_STAIR:
        return new MetaBlock1_2(Blocks.PURPUR_STAIRS);
      case PURPUR_DOUBLE_SLAB:
        return new MetaBlock1_2(Blocks.PURPUR_DOUBLE_SLAB);
      case PURPUR_SLAB:
        return new MetaBlock1_2(Blocks.PURPUR_SLAB);
      case ENDER_BRICK:
        return new MetaBlock1_2(Blocks.END_BRICKS);
      case MAGMA:
        return new MetaBlock1_2(Blocks.MAGMA);
      case RED_NETHERBRICK:
        return new MetaBlock1_2(Blocks.RED_NETHER_BRICK);
      case NETHER_WART_BLOCK:
        return new MetaBlock1_2(Blocks.NETHER_WART_BLOCK);
      case OAK_SAPLING:
        return getSapling(Wood.OAK);
      case SPRUCE_SAPLING:
        return getSapling(Wood.SPRUCE);
      case BIRCH_SAPLING:
        return getSapling(Wood.BIRCH);
      case BONE_BLOCK:
        return new MetaBlock1_2(Blocks.BONE_BLOCK);
      case BREWING_STAND:
        return new MetaBlock1_2(Blocks.BREWING_STAND);
      case BROWN_MUSHROOM:
        return new MetaBlock1_2(Blocks.BROWN_MUSHROOM);
      case CACTUS:
        return new MetaBlock1_2(Blocks.CACTUS);
      case CAKE:
        return new MetaBlock1_2(Blocks.CAKE);
      case CARPET:
        return new MetaBlock1_2(Blocks.CARPET);
      case CHEST:
        return new MetaBlock1_2(Blocks.CHEST);
      case TRAPPED_CHEST:
        return new MetaBlock1_2(Blocks.TRAPPED_CHEST);
      case MOB_SPAWNER:
        return new MetaBlock1_2(Blocks.MOB_SPAWNER);
      case CONCRETE:
        return new MetaBlock1_2(Blocks.CONCRETE);
      case ENDER_CHEST:
        return new MetaBlock1_2(Blocks.ENDER_CHEST);
      case FURNACE:
        return new MetaBlock1_2(Blocks.FURNACE);
      case RED_FLOWER:
        return new MetaBlock1_2(Blocks.RED_FLOWER);
      case RED_MUSHROOM:
        return new MetaBlock1_2(Blocks.RED_MUSHROOM);
      case SKULL:
        return new MetaBlock1_2(Blocks.SKULL);
      case TALL_PLANT:
        return new MetaBlock1_2(Blocks.TALLGRASS);
      case COMPARATOR:
        return new MetaBlock1_2(Blocks.UNPOWERED_COMPARATOR);
      case WOOL:
        return new MetaBlock1_2(Blocks.WOOL);
      case YELLOW_FLOWER:
        return new MetaBlock1_2(Blocks.YELLOW_FLOWER);
      case HOPPER:
        return new MetaBlock1_2(Blocks.HOPPER);
      case DISPENSER:
        return new MetaBlock1_2(Blocks.DISPENSER);
      case PISTON:
        return new MetaBlock1_2(Blocks.PISTON);
      case JUNGLE_SAPLING:
        return getSapling(Wood.JUNGLE);
      case ACACIA_SAPLING:
        return getSapling(Wood.ACACIA);
      case DARK_OAK_SAPLING:
        return getSapling(Wood.DARK_OAK);
      case TORCH:
        return map(BlockType.TORCH.getBrush());
      case REDSTONE_TORCH:
        return map(BlockType.REDSTONE_TORCH.getBrush());
      case AIR:
      default:
        return new MetaBlock1_2(Blocks.AIR);
    }
  }

  private static MetaBlock1_2 getColoredBlock(ColoredBlock coloredBlock) {
    if (coloredBlock.getBlockType() == BlockType.TERRACOTTA) {
      return new MetaBlock1_2(getTerracottaByColor(coloredBlock.getColor()));
    }
    return new MetaBlock1_2(getBlock(coloredBlock.getBlockType()))
        .withProperty(BlockColored.COLOR, DyeColor.get(coloredBlock.getColor()));
  }

  private static Block getBlock(BlockType type) {
    switch (type) {
      case STAINED_HARDENED_CLAY:
        return Blocks.STAINED_HARDENED_CLAY;
      case CARPET:
        return Blocks.CARPET;
      case STAINED_GLASS:
        return Blocks.STAINED_GLASS;
      case STAINED_GLASS_PANE:
        return Blocks.STAINED_GLASS_PANE;
      case CONCRETE:
        return Blocks.CONCRETE;
      case CONCRETE_POWDER:
        return Blocks.CONCRETE_POWDER;
      case WOOL:
      case TERRACOTTA:
      default:
        return Blocks.WOOL;
    }
  }

  public static MetaBlock1_2 getBed(BedBlock bedBlock) {
    return new MetaBlock1_2(Blocks.BED)
        .withProperty(BlockBed.FACING, bedBlock.getFacing().getFacing())
        .withProperty(BlockBed.PART, bedBlock.isHead() ? BlockBed.EnumPartType.HEAD : BlockBed.EnumPartType.FOOT);
  }

  public static MetaBlock1_2 getPumpkin(PumpkinBlock block) {
    return new MetaBlock1_2(block.isLit() ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN)
        .withProperty(BlockPumpkin.FACING, block.getFacing().getFacing());
  }

  public static MetaBlock1_2 getAnvil(AnvilBlock block) {
    if (!RogueConfig.FURNITURE.getBoolean()) {
      return map(BlockType.ANDESITE_POLISHED);
    }
    return new MetaBlock1_2(Blocks.ANVIL)
        .withProperty(BlockAnvil.DAMAGE, block.getDamage().ordinal())
        .withProperty(BlockAnvil.FACING, block.getFacing().getFacing());
  }

  public static MetaBlock1_2 mapStairs(StairsBlock block) {
    JsonElement json = block.getJson();
    MetaBlock1_2 metaBlock1_2 = json != null
        ? new MetaBlock1_2(json)
        : new MetaBlock1_2(getBlockForStairs(block.getBlockType()));
    return metaBlock1_2
        .withProperty(BlockStairs.FACING, block.getFacing().getFacing())
        .withProperty(BlockStairs.HALF, block.isUpsideDown() ? BlockStairs.EnumHalf.TOP : BlockStairs.EnumHalf.BOTTOM);
  }

  public static Block getBlockForStairs(BlockType stairs) {
    switch (stairs) {
      case ACACIA_STAIRS:
        return Blocks.ACACIA_STAIRS;
      case BIRCH_STAIRS:
        return Blocks.BIRCH_STAIRS;
      case BRICK_STAIRS:
        return Blocks.BRICK_STAIRS;
      case DARK_OAK_STAIRS:
        return Blocks.DARK_OAK_STAIRS;
      case JUNGLE_STAIRS:
        return Blocks.JUNGLE_STAIRS;
      case NETHER_BRICK_STAIRS:
        return Blocks.NETHER_BRICK_STAIRS;
      case OAK_STAIRS:
        return Blocks.OAK_STAIRS;
      case PURPUR_STAIRS:
        return Blocks.PURPUR_STAIRS;
      case QUARTZ_STAIRS:
        return Blocks.QUARTZ_STAIRS;
      case RED_SANDSTONE_STAIRS:
        return Blocks.RED_SANDSTONE_STAIRS;
      case SANDSTONE_STAIRS:
        return Blocks.SANDSTONE_STAIRS;
      case SPRUCE_STAIRS:
        return Blocks.SPRUCE_STAIRS;
      case STONE_BRICK_STAIRS:
        return Blocks.STONE_BRICK_STAIRS;
      case STONE_STAIRS:
      default:
        return Blocks.STONE_STAIRS;
    }
  }

  private static MetaBlock1_2 getTrapdoor(TrapdoorBlock block) {
    return new MetaBlock1_2(block.getMaterial() == Material.METAL ? Blocks.IRON_DOOR : Blocks.TRAPDOOR)
        .withProperty(BlockTrapDoor.HALF, block.isFlushWithTop() ? BlockTrapDoor.DoorHalf.TOP : BlockTrapDoor.DoorHalf.BOTTOM)
        .withProperty(BlockTrapDoor.OPEN, block.isOpen())
        .withProperty(BlockTrapDoor.FACING, block.getFacing().getFacing());
  }

  public static MetaBlock1_2 mapDoor(DoorBlock block) {
    JsonElement json = block.getJson();
    MetaBlock1_2 doorMetaBlock = json != null
        ? new MetaBlock1_2(json)
        : getDoorMetaBlock(block.getBlockType());
    return doorMetaBlock
        .withProperty(BlockDoor.HALF, block.isTop() ? BlockDoor.EnumDoorHalf.UPPER : BlockDoor.EnumDoorHalf.LOWER)
        .withProperty(BlockDoor.FACING, block.getFacing().getFacing())
        .withProperty(BlockDoor.OPEN, block.isOpen())
        .withProperty(BlockDoor.HINGE, block.isHingeLeft() ? BlockDoor.EnumHingePosition.LEFT : BlockDoor.EnumHingePosition.RIGHT);
  }

  private static MetaBlock1_2 getDoorMetaBlock(BlockType type) {
    switch (type) {
      case IRON_DOOR:
        return new MetaBlock1_2(Blocks.IRON_DOOR);
      case BIRCH_DOOR:
        return new MetaBlock1_2(Blocks.BIRCH_DOOR);
      case SPRUCE_DOOR:
        return new MetaBlock1_2(Blocks.SPRUCE_DOOR);
      case JUNGLE_DOOR:
        return new MetaBlock1_2(Blocks.JUNGLE_DOOR);
      case ACACIA_DOOR:
        return new MetaBlock1_2(Blocks.ACACIA_DOOR);
      case DARK_OAK_DOOR:
        return new MetaBlock1_2(Blocks.DARK_OAK_DOOR);
      case OAK_DOOR:
      default:
        return new MetaBlock1_2(Blocks.OAK_DOOR);
    }
  }

  private static MetaBlock1_2 getSlab(SlabBlock slabBlock) {
    Block minecraftBlock = getSlabMinecraftBlock(slabBlock.getBlockType(), slabBlock.isFullBlock());
    MetaBlock1_2 metaBlock = new MetaBlock1_2(minecraftBlock);
    switch (slabBlock.getBlockType()) {
      case STONE_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.STONE);
        break;
      case SANDSTONE_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND);
        break;
      case LEGACY_OAK_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.WOOD);
        break;
      case COBBLE_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.COBBLESTONE);
        break;
      case BRICK_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.BRICK);
        break;
      case STONEBRICK_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK);
        break;
      case NETHERBRICK_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK);
        break;
      case QUARTZ_SLAB:
        metaBlock.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.QUARTZ);
        break;
      case RED_SANDSTONE_SLAB:
      case SMOOTH_RED_SANDSTONE_SLAB:
        metaBlock.withProperty(BlockStoneSlabNew.VARIANT, BlockStoneSlabNew.EnumType.RED_SANDSTONE);
        break;
      case OAK_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK);
        break;
      case SPRUCE_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.SPRUCE);
        break;
      case BIRCH_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.BIRCH);
        break;
      case JUNGLE_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.JUNGLE);
        break;
      case ACACIA_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.ACACIA);
        break;
      case DARK_OAK_SLAB:
        metaBlock.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.DARK_OAK);
        break;
      default:
    }

    if (!slabBlock.isFullBlock() && slabBlock.isTop()) {
      metaBlock.withProperty(BlockWoodSlab.HALF, BlockSlab.EnumBlockHalf.TOP);
    }

    if (slabBlock.isFullBlock() && slabBlock.isSeamless()) {
      metaBlock.withProperty(BlockStoneSlab.SEAMLESS, true);
    }

    return metaBlock;
  }


  public static Block getSlabMinecraftBlock(BlockType blockType, boolean isFullBlock) {
    switch (blockType) {
      case STONE_SLAB:
      case SANDSTONE_SLAB:
      case LEGACY_OAK_SLAB:
      case COBBLE_SLAB:
      case BRICK_SLAB:
      case STONEBRICK_SLAB:
      case NETHERBRICK_SLAB:
      case QUARTZ_SLAB:
        return isFullBlock ? Blocks.DOUBLE_STONE_SLAB : Blocks.STONE_SLAB;
      case RED_SANDSTONE:
      case SMOOTH_RED_SANDSTONE_SLAB:
        return isFullBlock ? Blocks.DOUBLE_STONE_SLAB2 : Blocks.STONE_SLAB2;
      case OAK_SLAB:
      case SPRUCE_SLAB:
      case BIRCH_SLAB:
      case JUNGLE_SLAB:
      case ACACIA_SLAB:
      case DARK_OAK_SLAB:
        return isFullBlock ? Blocks.DOUBLE_WOODEN_SLAB : Blocks.WOODEN_SLAB;
      default:
        return Blocks.STONE_SLAB;
    }
  }


  public static MetaBlock1_2 getTorch(TorchBlock torchBlock) {
    Block minecraftTorchBlock = !torchBlock.isLit()
        ? Blocks.UNLIT_REDSTONE_TORCH
        : torchBlock.getBlockType() == BlockType.REDSTONE_TORCH
            ? Blocks.REDSTONE_TORCH
            : Blocks.TORCH;

    Direction dir = torchBlock.getFacing();

    EnumFacing facing = dir == Direction.UP
        ? EnumFacing.UP
        : dir == Direction.DOWN
            ? EnumFacing.DOWN
            : dir.reverse().getFacing();

    return new MetaBlock1_2(minecraftTorchBlock)
        .withProperty(BlockTorch.FACING, facing);
  }

  public static Block getTerracottaByColor(DyeColor color) {

    switch (color) {
      case WHITE:
        return Blocks.WHITE_GLAZED_TERRACOTTA;
      case ORANGE:
        return Blocks.ORANGE_GLAZED_TERRACOTTA;
      case MAGENTA:
        return Blocks.MAGENTA_GLAZED_TERRACOTTA;
      case LIGHT_BLUE:
        return Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA;
      case YELLOW:
        return Blocks.YELLOW_GLAZED_TERRACOTTA;
      case LIME:
        return Blocks.LIME_GLAZED_TERRACOTTA;
      case PINK:
        return Blocks.PINK_GLAZED_TERRACOTTA;
      case GRAY:
        return Blocks.GRAY_GLAZED_TERRACOTTA;
      case LIGHT_GRAY:
        return Blocks.SILVER_GLAZED_TERRACOTTA;
      case CYAN:
        return Blocks.CYAN_GLAZED_TERRACOTTA;
      case PURPLE:
        return Blocks.PURPLE_GLAZED_TERRACOTTA;
      case BLUE:
        return Blocks.BLUE_GLAZED_TERRACOTTA;
      case BROWN:
        return Blocks.BROWN_GLAZED_TERRACOTTA;
      case GREEN:
        return Blocks.GREEN_GLAZED_TERRACOTTA;
      case RED:
        return Blocks.RED_GLAZED_TERRACOTTA;
      case BLACK:
      default:
        return Blocks.BLACK_GLAZED_TERRACOTTA;
    }
  }

  public static MetaBlock1_2 getLogMetaBlock(Wood type, Direction facing) {
    Block minecraftLogBlock = getMinecraftLogBlock(type);
    MetaBlock1_2 log = new MetaBlock1_2(minecraftLogBlock);
    MetaBlock1_2 logWithVariants = addLogVariants(log, type);
    return addLogFacing(logWithVariants, facing);
  }

  private static MetaBlock1_2 addLogFacing(MetaBlock1_2 log, Direction facing) {
    switch (facing) {
      case UP:
      case DOWN:
        return log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
      case EAST:
      case WEST:
        return log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.X);
      case NORTH:
      case SOUTH:
        return log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Z);
      default:
        return log.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.NONE);
    }
  }

  public static Block getMinecraftLogBlock(Wood type) {
    switch (type) {
      case OAK:
      case JUNGLE:
      case BIRCH:
      case SPRUCE:
      default:
        return Blocks.LOG;
      case ACACIA:
      case DARK_OAK:
        return Blocks.LOG2;
    }
  }

  private static MetaBlock1_2 addLogVariants(MetaBlock1_2 log, Wood wood) {
    switch (wood) {
      default:
      case OAK:
      case SPRUCE:
      case JUNGLE:
      case BIRCH:
        return log.withProperty(BlockOldLog.VARIANT, getWoodVariant(wood));
      case ACACIA:
      case DARK_OAK:
        return log.withProperty(BlockNewLog.VARIANT, getWoodVariant(wood));
    }
  }

  public static MetaBlock1_2 getPlankMetaBlock(Wood type) {
    MetaBlock1_2 plank = new MetaBlock1_2(Blocks.PLANKS);
    switch (type) {
      default:
      case OAK:
        return plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK);
      case SPRUCE:
        return plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE);
      case BIRCH:
        return plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH);
      case JUNGLE:
        return plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE);
      case ACACIA:
        return plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA);
      case DARK_OAK:
        return plank.withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK);
    }
  }

  public static MetaBlock1_2 getFenceMetaBlock(Wood type) {
    switch (type) {
      case SPRUCE:
        return new MetaBlock1_2(Blocks.SPRUCE_FENCE);
      case BIRCH:
        return new MetaBlock1_2(Blocks.BIRCH_FENCE);
      case JUNGLE:
        return new MetaBlock1_2(Blocks.JUNGLE_FENCE);
      case ACACIA:
        return new MetaBlock1_2(Blocks.ACACIA_FENCE);
      case DARK_OAK:
        return new MetaBlock1_2(Blocks.DARK_OAK_FENCE);
      case OAK:
      default:
        return new MetaBlock1_2(Blocks.OAK_FENCE);
    }
  }

  public static MetaBlock1_2 getSapling(Wood type) {
    MetaBlock1_2 sapling = new MetaBlock1_2(Blocks.SAPLING);
    switch (type) {
      default:
      case OAK:
        return sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.OAK);
      case SPRUCE:
        return sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.SPRUCE);
      case BIRCH:
        return sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.BIRCH);
      case JUNGLE:
        return sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.JUNGLE);
      case ACACIA:
        return sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.ACACIA);
      case DARK_OAK:
        return sapling.withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.DARK_OAK);
    }
  }

  public static MetaBlock1_2 getLeaves(Wood type, boolean decay) {
    Block base = getBlockId(type);
    PropertyEnum<BlockPlanks.EnumType> variantType = base == Blocks.LEAVES ? BlockOldLeaf.VARIANT : BlockNewLeaf.VARIANT;
    return new MetaBlock1_2(base)
        .withProperty(variantType, getWoodVariant(type))
        .withProperty(BlockLeaves.DECAYABLE, decay);

  }

  private static Block getBlockId(Wood type) {
    switch (type) {
      case OAK:
      case SPRUCE:
      case JUNGLE:
      case BIRCH:
        return Blocks.LEAVES;
      case ACACIA:
      case DARK_OAK:
        return Blocks.LEAVES2;
      default:
        return Blocks.LOG;
    }
  }

  private static BlockPlanks.EnumType getWoodVariant(Wood type) {

    switch (type) {
      case SPRUCE:
        return BlockPlanks.EnumType.SPRUCE;
      case BIRCH:
        return BlockPlanks.EnumType.BIRCH;
      case JUNGLE:
        return BlockPlanks.EnumType.JUNGLE;
      case ACACIA:
        return BlockPlanks.EnumType.ACACIA;
      case DARK_OAK:
        return BlockPlanks.EnumType.DARK_OAK;
      case OAK:
      default:
        return BlockPlanks.EnumType.OAK;
    }
  }

  public static MetaBlock1_2 createVine(Direction dir) {
    MetaBlock1_2 vine = new MetaBlock1_2(Blocks.VINE);
    vine.withProperty(BlockVine.UP, dir == Direction.UP);
    vine.withProperty(BlockVine.NORTH, dir == Direction.NORTH);
    vine.withProperty(BlockVine.EAST, dir == Direction.EAST);
    vine.withProperty(BlockVine.SOUTH, dir == Direction.SOUTH);
    vine.withProperty(BlockVine.WEST, dir == Direction.WEST);
    return vine;
  }

  private static MetaBlock1_2 getPlant(PlantBlock block) {
    return getPlant(block.getPlant());
  }

  public static MetaBlock1_2 getPlant(Plant type) {
    switch (type) {
      case POPPY:
        return new MetaBlock1_2(Blocks.RED_FLOWER);
      case ORCHID:
        return new MetaBlock1_2(Blocks.RED_FLOWER)
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.BLUE_ORCHID);
      case ALLIUM:
        return new MetaBlock1_2(Blocks.RED_FLOWER)
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.ALLIUM);
      case BLUET:
        return new MetaBlock1_2(Blocks.RED_FLOWER)
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.HOUSTONIA);
      case REDTULIP:
        return new MetaBlock1_2(Blocks.RED_FLOWER)
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.RED_TULIP);
      case ORANGETULIP:
        return new MetaBlock1_2(Blocks.RED_FLOWER)
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.ORANGE_TULIP);
      case WHITETULIP:
        return new MetaBlock1_2(Blocks.RED_FLOWER)
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.WHITE_TULIP);
      case PINKTULIP:
        return new MetaBlock1_2(Blocks.RED_FLOWER)
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.PINK_TULIP);
      case DAISY:
        return new MetaBlock1_2(Blocks.RED_FLOWER)
            .withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.OXEYE_DAISY);
      case RED_MUSHROOM:
        return new MetaBlock1_2(Blocks.RED_MUSHROOM);
      case BROWN_MUSHROOM:
        return new MetaBlock1_2(Blocks.BROWN_MUSHROOM);
      case CACTUS:
        return new MetaBlock1_2(Blocks.CACTUS);
      case OAK_SAPLING:
        return getSapling(Wood.OAK);
      case SPRUCE_SAPLING:
        return getSapling(Wood.SPRUCE);
      case BIRCH_SAPLING:
        return getSapling(Wood.BIRCH);
      case JUNGLE_SAPLING:
        return getSapling(Wood.JUNGLE);
      case ACACIA_SAPLING:
        return getSapling(Wood.ACACIA);
      case DARKOAK_SAPLING:
        return getSapling(Wood.DARK_OAK);
      case SHRUB:
        return new MetaBlock1_2(Blocks.TALLGRASS)
            .withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.DEAD_BUSH);
      case FERN:
        return new MetaBlock1_2(Blocks.TALLGRASS)
            .withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN);
      case DANDELION:
      default:
        return new MetaBlock1_2(Blocks.YELLOW_FLOWER);
    }
  }

  private static MetaBlock1_2 getInfestedBlock(InfestedBlock block) {
    MetaBlock1_2 block1 = new MetaBlock1_2(Blocks.MONSTER_EGG);
    Stone stone = block.getStone();
    switch (stone) {
      default:
      case STONE:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE);
      case COBBLE:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.COBBLESTONE);
      case STONEBRICK:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONEBRICK);
      case STONEBRICK_MOSSY:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.MOSSY_STONEBRICK);
      case STONEBRICK_CRACKED:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.CRACKED_STONEBRICK);
      case STONEBRICK_CHISELED:
        return block1.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.CHISELED_STONEBRICK);
    }
  }

  public static MetaBlock1_2 getQuartz(Quartz type, Direction facing) {
    MetaBlock1_2 block = new MetaBlock1_2(Blocks.QUARTZ_BLOCK);
    switch (type) {
      case CHISELED:
        block = block.withProperty(BlockQuartz.VARIANT, BlockQuartz.EnumType.CHISELED);
        return addPillarLines(block, facing);
      case PILLAR:
        return addPillarLines(block, facing);
      case SMOOTH:
      default:
        return block;
    }
  }

  public static MetaBlock1_2 addPillarLines(MetaBlock1_2 block, Direction facing) {
    switch (facing) {
      case EAST:
      case WEST:
        return block.withProperty(BlockQuartz.VARIANT, BlockQuartz.EnumType.LINES_X);
      case NORTH:
      case SOUTH:
        return block.withProperty(BlockQuartz.VARIANT, BlockQuartz.EnumType.LINES_Z);
      case UP:
      case DOWN:
      default:
        return block.withProperty(BlockQuartz.VARIANT, BlockQuartz.EnumType.LINES_Y);
    }
  }

  public static MetaBlock1_2 getTallPlant(TallPlantBlock tallPlantBlock) {
    return new MetaBlock1_2(Blocks.DOUBLE_PLANT)
        .withProperty(BlockDoublePlant.VARIANT, getTallPlantMinecraftBlock(tallPlantBlock.getTallPlant()))
        .withProperty(BlockDoublePlant.FACING, tallPlantBlock.getFacing().getFacing())
        .withProperty(BlockDoublePlant.HALF, tallPlantBlock.isTop()
            ? BlockDoublePlant.EnumBlockHalf.UPPER
            : BlockDoublePlant.EnumBlockHalf.LOWER);
  }

  public static BlockDoublePlant.EnumPlantType getTallPlantMinecraftBlock(TallPlant type) {
    switch (type) {
      default:
      case DOUBLE_TALL_GRASS:
        return BlockDoublePlant.EnumPlantType.GRASS;
      case SUNFLOWER:
        return BlockDoublePlant.EnumPlantType.SUNFLOWER;
      case LILAC:
        return BlockDoublePlant.EnumPlantType.SYRINGA;
      case LARGE_FERN:
        return BlockDoublePlant.EnumPlantType.FERN;
      case ROSE_BUSH:
        return BlockDoublePlant.EnumPlantType.ROSE;
      case PEONY:
        return BlockDoublePlant.EnumPlantType.PAEONIA;
    }
  }
}
