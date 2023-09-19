package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.AnvilBlock;
import com.github.fnar.minecraft.block.decorative.BedBlock;
import com.github.fnar.minecraft.block.decorative.CropBlock;
import com.github.fnar.minecraft.block.decorative.PlantBlock;
import com.github.fnar.minecraft.block.decorative.PumpkinBlock;
import com.github.fnar.minecraft.block.decorative.TallPlantBlock;
import com.github.fnar.minecraft.block.decorative.TorchBlock;
import com.github.fnar.minecraft.block.normal.ColoredBlock;
import com.github.fnar.minecraft.block.normal.InfestedBlock;
import com.github.fnar.minecraft.block.normal.Quartz;
import com.github.fnar.minecraft.block.normal.SlabBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.redstone.ComparatorBlock;
import com.github.fnar.minecraft.block.redstone.DoorBlock;
import com.github.fnar.minecraft.block.redstone.LeverBlock;
import com.github.fnar.minecraft.block.redstone.RepeaterBlock;
import com.github.fnar.minecraft.block.redstone.TrapdoorBlock;
import com.github.fnar.minecraft.block.redstone.TripwireHookBlock;
import com.github.fnar.minecraft.material.Stone;
import com.github.fnar.minecraft.material.Wood;
import com.github.fnar.util.ReportThisIssueException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.MetaBlock1_12;

public class BlockMapper1_12 {

  public static IBlockState map(SingleBlockBrush blockBrush) {
    Direction facing = blockBrush.getFacing();

    EnumFacing enumFacing = getFacing(facing);

    BlockType blockType = blockBrush.getBlockType();

    switch (blockType) {
      case ANVIL:
        return getAnvil((AnvilBlock) blockBrush).getState();
      case ACACIA_LOG:
        return getLogMetaBlock(Wood.ACACIA, facing).getState();
      case BIRCH_LOG:
        return getLogMetaBlock(Wood.BIRCH, facing).getState();
      case DARK_OAK_LOG:
        return getLogMetaBlock(Wood.DARK_OAK, facing).getState();
      case JUNGLE_LOG:
        return getLogMetaBlock(Wood.JUNGLE, facing).getState();
      case OAK_LOG:
        return getLogMetaBlock(Wood.OAK, facing).getState();
      case SPRUCE_LOG:
        return getLogMetaBlock(Wood.SPRUCE, facing).getState();
      case ACACIA_FENCE:
        return Blocks.ACACIA_FENCE.getDefaultState();
      case BIRCH_FENCE:
        return Blocks.BIRCH_FENCE.getDefaultState();
      case DARK_OAK_FENCE:
        return Blocks.DARK_OAK_FENCE.getDefaultState();
      case JUNGLE_FENCE:
        return Blocks.JUNGLE_FENCE.getDefaultState();
      case OAK_FENCE:
        return Blocks.OAK_FENCE.getDefaultState();
      case SPRUCE_FENCE:
        return Blocks.SPRUCE_FENCE.getDefaultState();
      case ACACIA_PLANK:
        return getPlankMetaBlock(Wood.ACACIA).getState();
      case BIRCH_PLANK:
        return getPlankMetaBlock(Wood.BIRCH).getState();
      case DARK_OAK_PLANK:
        return getPlankMetaBlock(Wood.DARK_OAK).getState();
      case JUNGLE_PLANK:
        return getPlankMetaBlock(Wood.JUNGLE).getState();
      case OAK_PLANK:
        return getPlankMetaBlock(Wood.OAK).getState();
      case SPRUCE_PLANK:
        return getPlankMetaBlock(Wood.SPRUCE).getState();
      case OAK_LEAVES:
        return LeavesBlockMapper1_12.map(Wood.OAK, false);
      case SPRUCE_LEAVES:
        return LeavesBlockMapper1_12.map(Wood.SPRUCE, false);
      case BIRCH_LEAVES:
        return LeavesBlockMapper1_12.map(Wood.BIRCH, false);
      case JUNGLE_LEAVES:
        return LeavesBlockMapper1_12.map(Wood.JUNGLE, false);
      case ACACIA_LEAVES2:
        return LeavesBlockMapper1_12.map(Wood.ACACIA, false);
      case DARK_OAK_LEAVES:
        return LeavesBlockMapper1_12.map(Wood.DARK_OAK, false);
      case CARPET:
      case CONCRETE:
      case CONCRETE_POWDER:
      case STAINED_GLASS:
      case STAINED_GLASS_PANE:
      case STAINED_HARDENED_CLAY:
      case TERRACOTTA:
      case WOOL:
        return getColoredBlock((ColoredBlock) blockBrush).getState();
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
        return StairsBlockMapper1_12.mapStairsToState((StairsBlock) blockBrush);
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
        return getSlab((SlabBlock) blockBrush).getState();
      case IRON_DOOR:
      case OAK_DOOR:
      case BIRCH_DOOR:
      case SPRUCE_DOOR:
      case JUNGLE_DOOR:
      case ACACIA_DOOR:
      case DARK_OAK_DOOR:
      case WARPED_DOOR:
      case CRIMSON_DOOR:
        return DoorBlockMapper1_12.map((DoorBlock) blockBrush);
      case CHISELED_QUARTZ:
        return QuartzBlockMapper1_12.map(Quartz.CHISELED, facing);
      case PILLAR_QUARTZ:
        return QuartzBlockMapper1_12.map(Quartz.PILLAR, facing);
      case SMOOTH_QUARTZ:
        return QuartzBlockMapper1_12.map(Quartz.SMOOTH, facing);
      case VINE:
        return createVine(facing).getState();
      case CROP:
        return CropBlockMapper1_12.map((CropBlock) blockBrush);
      case PUMPKIN:
        return blockBrush instanceof PumpkinBlock ? getPumpkin((PumpkinBlock) blockBrush).getState() : Blocks.PUMPKIN.getDefaultState();
      case FLOWER_POT:
        return Blocks.FLOWER_POT.getDefaultState();
      case BROWN_MUSHROOM:
        return Blocks.BROWN_MUSHROOM.getDefaultState();
      case RED_MUSHROOM:
        return Blocks.RED_MUSHROOM.getDefaultState();
      case PLANT:
        return PlantBlockMapper1_12.getPlant((PlantBlock) blockBrush);
      case TALL_PLANT:
        return TallGrassMapper1_12.getTallPlant((TallPlantBlock) blockBrush);
      case TRAPDOOR:
        return getTrapdoor((TrapdoorBlock) blockBrush).getState();
      case LEVER:
        return getLever((LeverBlock) blockBrush).getState();
      case TORCH:
      case REDSTONE_TORCH:
        return getTorch((TorchBlock) blockBrush).getState();
      case BED:
        return getBed((BedBlock) blockBrush).getState();
      case FURNACE:
        if (!RogueConfig.FURNITURE.getBoolean()) {
          return Blocks.COBBLESTONE.getDefaultState();
        }
        return Blocks.FURNACE.getDefaultState().withProperty(BlockFurnace.FACING, enumFacing);
      case DISPENSER:
        return Blocks.DISPENSER.getDefaultState().withProperty(BlockDispenser.FACING, enumFacing);
      case DROPPER:
        return Blocks.DROPPER.getDefaultState().withProperty(BlockDropper.FACING, enumFacing);
      case HOPPER:
        return Blocks.HOPPER.getDefaultState()
            .withProperty(BlockHopper.FACING, enumFacing);
      case COCOA_BEANS:
        return Blocks.COCOA.getDefaultState();
      case COMPARATOR:
        return getComparator((ComparatorBlock) blockBrush).getState();
      case REPEATER:
        return getRepeater((RepeaterBlock) blockBrush).getState();
      case PISTON:
        return Blocks.PISTON.getDefaultState()
            .withProperty(BlockPistonBase.FACING, enumFacing);
      case STICKY_PISTON:
        return Blocks.STICKY_PISTON.getDefaultState()
            .withProperty(BlockPistonBase.FACING, enumFacing);
      case INFESTED_BLOCK:
        return getInfestedBlock((InfestedBlock) blockBrush).getState();
      case CHEST:
        return Blocks.CHEST.getDefaultState()
            .withProperty(BlockChest.FACING, enumFacing);
      case TRAPPED_CHEST:
        return Blocks.TRAPPED_CHEST.getDefaultState()
            .withProperty(BlockChest.FACING, enumFacing);
      case NETHER_PORTAL:
        return Blocks.PORTAL.getDefaultState()
            .withProperty(BlockPortal.AXIS, enumFacing.getAxis());
      case TRIPWIRE:
        return Blocks.TRIPWIRE.getDefaultState();
      case TRIPWIRE_HOOK:
        return Blocks.TRIPWIRE_HOOK.getDefaultState()
            .withProperty(BlockTripWire.ATTACHED, ((TripwireHookBlock) blockBrush).isAttached())
            .withProperty(BlockTripWireHook.FACING, enumFacing);
      case LADDER:
        return Blocks.LADDER.getDefaultState()
            .withProperty(BlockLadder.FACING, enumFacing);
      case AIR:
        return Blocks.AIR.getDefaultState();
      case WATER_STILL:
        return Blocks.WATER.getDefaultState();
      case WATER_FLOWING:
        return Blocks.FLOWING_WATER.getDefaultState();
      case LAVA_STILL:
        return Blocks.LAVA.getDefaultState();
      case LAVA_FLOWING:
        return Blocks.FLOWING_LAVA.getDefaultState();
      case FIRE:
        return Blocks.FIRE.getDefaultState();
      case IRON_BAR:
        return Blocks.IRON_BARS.getDefaultState();
      case STONE_SMOOTH:
        return Blocks.STONE.getDefaultState();
      case GRANITE:
        return Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE);
      case GRANITE_POLISHED:
        return Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE_SMOOTH);
      case DIORITE:
        return Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE);
      case DIORITE_POLISHED:
        return Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE_SMOOTH);
      case ANDESITE:
        return Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE);
      case ANDESITE_POLISHED:
        return Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE_SMOOTH);
      case GRASS:
        return Blocks.GRASS.getDefaultState();
      case DIRT:
        return Blocks.DIRT.getDefaultState();
      case DIRT_COARSE:
        return Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
      case DIRT_PODZOL:
        return Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
      case COBBLESTONE:
        return Blocks.COBBLESTONE.getDefaultState();
      case COBBLESTONE_WALL:
        return Blocks.COBBLESTONE_WALL.getDefaultState();
      case BEDROCK:
        return Blocks.BEDROCK.getDefaultState();
      case SAND:
        return Blocks.SAND.getDefaultState();
      case SAND_RED:
        return Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND);
      case GRAVEL:
        return Blocks.GRAVEL.getDefaultState();
      case ORE_GOLD:
        return Blocks.GOLD_ORE.getDefaultState();
      case ORE_IRON:
        return Blocks.IRON_ORE.getDefaultState();
      case ORE_COAL:
        return Blocks.COAL_BLOCK.getDefaultState();
      case GLASS:
        return Blocks.GLASS.getDefaultState();
      case GLASS_PANE:
        return Blocks.GLASS_PANE.getDefaultState();
      case ORE_LAPIS:
        return Blocks.LAPIS_ORE.getDefaultState();
      case LAPIS_BLOCK:
        return Blocks.LAPIS_BLOCK.getDefaultState();
      case ORE_EMERALD:
        return Blocks.EMERALD_ORE.getDefaultState();
      case SANDSTONE:
        return Blocks.SANDSTONE.getDefaultState();
      case CHISELED_SANDSTONE:
        return Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.CHISELED);
      case SANDSTONE_SMOOTH:
        return Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH);
      case GOLD_BLOCK:
        return Blocks.GOLD_BLOCK.getDefaultState();
      case IRON_BLOCK:
        return Blocks.IRON_BLOCK.getDefaultState();
      case BRICK:
        return Blocks.BRICK_BLOCK.getDefaultState();
      case COBBLESTONE_MOSSY:
        return Blocks.MOSSY_COBBLESTONE.getDefaultState();
      case OBSIDIAN:
        return Blocks.OBSIDIAN.getDefaultState();
      case ORE_DIAMOND:
        return Blocks.DIAMOND_ORE.getDefaultState();
      case DIAMOND_BLOCK:
        return Blocks.DIAMOND_BLOCK.getDefaultState();
      case FARMLAND:
        return Blocks.FARMLAND.getDefaultState();
      case ORE_REDSTONE:
        return Blocks.REDSTONE_ORE.getDefaultState();
      case ICE:
        return Blocks.ICE.getDefaultState();
      case SNOW:
        return Blocks.SNOW.getDefaultState();
      case CLAY:
        return Blocks.CLAY.getDefaultState();
      case NETHERRACK:
        return Blocks.NETHERRACK.getDefaultState();
      case SOUL_SAND:
        return Blocks.SOUL_SAND.getDefaultState();
      case GLOWSTONE:
        return Blocks.GLOWSTONE.getDefaultState();
      case STONE_BRICK:
        return Blocks.STONEBRICK.getDefaultState();
      case STONE_BRICK_MOSSY:
        return Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
      case STONE_BRICK_CRACKED:
        return Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED);
      case STONE_BRICK_CHISELED:
        return Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
      case MYCELIUM:
        return Blocks.MYCELIUM.getDefaultState();
      case NETHERBRICK:
        return Blocks.NETHER_BRICK.getDefaultState();
      case END_STONE:
        return Blocks.END_STONE.getDefaultState();
      case EMERALD_BLOCK:
        return Blocks.EMERALD_BLOCK.getDefaultState();
      case ORE_QUARTZ:
        return Blocks.QUARTZ_ORE.getDefaultState();
      case PRISMITE:
        return Blocks.PRISMARINE.getDefaultState().withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.ROUGH);
      case PRISMARINE:
        return Blocks.PRISMARINE.getDefaultState().withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.BRICKS);
      case PRISMARINE_DARK:
        return Blocks.PRISMARINE.getDefaultState().withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.DARK);
      case SEA_LANTERN:
        return Blocks.SEA_LANTERN.getDefaultState();
      case COAL_BLOCK:
        return Blocks.COAL_BLOCK.getDefaultState();
      case ICE_PACKED:
        return Blocks.PACKED_ICE.getDefaultState();
      case RED_SANDSTONE:
        return Blocks.RED_SANDSTONE.getDefaultState();
      case CHISELED_RED_SANDSTONE:
        return Blocks.RED_SANDSTONE.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.CHISELED);
      case SMOOTH_RED_SANDSTONE:
        return Blocks.RED_SANDSTONE.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH);
      case QUARTZ:
        return Blocks.QUARTZ_BLOCK.getDefaultState();
      case REDSTONE_BLOCK:
        return Blocks.REDSTONE_BLOCK.getDefaultState();
      case PRESSURE_PLATE_STONE:
        return Blocks.STONE_PRESSURE_PLATE.getDefaultState();
      case PRESSURE_PLATE_WOODEN:
        return Blocks.WOODEN_PRESSURE_PLATE.getDefaultState();
      case BOOKSHELF:
        return Blocks.BOOKSHELF.getDefaultState();
      case REDSTONE_WIRE:
        return Blocks.REDSTONE_WIRE.getDefaultState();
      case REEDS:
        return Blocks.REEDS.getDefaultState();
      case CRAFTING_TABLE:
        return (!RogueConfig.FURNITURE.getBoolean()
            ? getPlankMetaBlock(Wood.OAK).getState()
            : Blocks.CRAFTING_TABLE.getDefaultState());
      case NOTEBLOCK:
        return Blocks.NOTEBLOCK.getDefaultState();
      case REDSTONE_LAMP:
        return Blocks.REDSTONE_LAMP.getDefaultState();
      case REDSTONE_LAMP_LIT:
        return Blocks.LIT_REDSTONE_LAMP.getDefaultState();
      case JUKEBOX:
        return Blocks.JUKEBOX.getDefaultState();
      case TNT:
        return Blocks.TNT.getDefaultState();
      case ENCHANTING_TABLE:
        return Blocks.ENCHANTING_TABLE.getDefaultState();
      case FENCE_NETHER_BRICK:
        return Blocks.NETHER_BRICK_FENCE.getDefaultState();
      case WEB:
        return Blocks.WEB.getDefaultState();
      case PURPUR_BLOCK:
        return Blocks.PURPUR_BLOCK.getDefaultState();
      case PURPUR_PILLAR:
        return Blocks.PURPUR_PILLAR.getDefaultState();
      case PURPUR_STAIR:
        return Blocks.PURPUR_STAIRS.getDefaultState();
      case PURPUR_DOUBLE_SLAB:
        return Blocks.PURPUR_DOUBLE_SLAB.getDefaultState();
      case PURPUR_SLAB:
        return Blocks.PURPUR_SLAB.getDefaultState();
      case ENDER_BRICK:
        return Blocks.END_BRICKS.getDefaultState();
      case MAGMA:
        return Blocks.MAGMA.getDefaultState();
      case RED_NETHERBRICK:
        return Blocks.RED_NETHER_BRICK.getDefaultState();
      case NETHER_WART_BLOCK:
        return Blocks.NETHER_WART_BLOCK.getDefaultState();
      case OAK_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.OAK);
      case SPRUCE_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.SPRUCE);
      case BIRCH_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.BIRCH);
      case BONE_BLOCK:
        return Blocks.BONE_BLOCK.getDefaultState();
      case BREWING_STAND:
        return Blocks.BREWING_STAND.getDefaultState();
      case CACTUS:
        return Blocks.CACTUS.getDefaultState();
      case CAKE:
        return Blocks.CAKE.getDefaultState();
      case MOB_SPAWNER:
        return Blocks.MOB_SPAWNER.getDefaultState();
      case ENDER_CHEST:
        return Blocks.ENDER_CHEST.getDefaultState();
      case RED_FLOWER:
        return Blocks.RED_FLOWER.getDefaultState();
      case SKULL:
        return Blocks.SKULL.getDefaultState();
      case YELLOW_FLOWER:
        return Blocks.YELLOW_FLOWER.getDefaultState();
      case JUNGLE_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.JUNGLE);
      case ACACIA_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.ACACIA);
      case DARK_OAK_SAPLING:
        return SaplingBlockMapper1_12.map(Wood.DARK_OAK);
      case WHEAT:
        return Blocks.WHEAT.getDefaultState();
      case SIGN:
        return Blocks.STANDING_SIGN.getDefaultState();
      case SPONGE:
        return Blocks.SPONGE.getDefaultState();
    }
    throw new ReportThisIssueException(new UnmappedBlockException(blockType));
  }

  private static MetaBlock1_12 createMetaBlock(Block block) {
    return new MetaBlock1_12(block);
  }

  private static MetaBlock1_12 getRepeater(RepeaterBlock block) {
    return createMetaBlock(block.isPowered() ? Blocks.POWERED_REPEATER : Blocks.UNPOWERED_REPEATER)
        .withProperty(BlockRedstoneRepeater.DELAY, block.getDelay().asInt())
        .withProperty(BlockRedstoneRepeater.FACING, getFacing(block.getFacing()));
  }

  private static MetaBlock1_12 getComparator(ComparatorBlock block) {
    return createMetaBlock(block.isPowered() ? Blocks.POWERED_COMPARATOR : Blocks.UNPOWERED_COMPARATOR)
        .withProperty(BlockRedstoneComparator.FACING, getFacing(block.getFacing()))
        .withProperty(BlockRedstoneComparator.MODE, block.getMode() == ComparatorBlock.Mode.SUBTRACTION
            ? BlockRedstoneComparator.Mode.SUBTRACT
            : BlockRedstoneComparator.Mode.COMPARE);
  }

  private static MetaBlock1_12 getLever(LeverBlock leverBlock) {
    Direction dir = leverBlock.getFacing();
    return createMetaBlock(Blocks.LEVER)
        .withProperty(BlockLever.POWERED, leverBlock.isActive())
        .withProperty(BlockLever.FACING, getLeverOrientation(dir));
  }

  public static BlockLever.EnumOrientation getLeverOrientation(Direction direction) {
    switch (direction) {
      default:
      case NORTH:
        return BlockLever.EnumOrientation.NORTH;
      case EAST:
        return BlockLever.EnumOrientation.EAST;
      case SOUTH:
        return BlockLever.EnumOrientation.SOUTH;
      case WEST:
        return BlockLever.EnumOrientation.WEST;
      case UP:
        return BlockLever.EnumOrientation.UP_X;
      case DOWN:
        return BlockLever.EnumOrientation.DOWN_X;
    }
  }

  // TODO: can all public calls to this be replaced with calls to map(SingleBlockBrush)?

  private static MetaBlock1_12 getColoredBlock(ColoredBlock coloredBlock) {
    if (coloredBlock.getBlockType() == BlockType.TERRACOTTA) {
      return createMetaBlock(getTerracottaByColor(coloredBlock.getColor()));
    }
    return createMetaBlock(getBlock(coloredBlock.getBlockType()))
        .withProperty(BlockColored.COLOR, toEnumDyeColor(coloredBlock.getColor()));
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

  private static MetaBlock1_12 getBed(BedBlock bedBlock) {
    return createMetaBlock(Blocks.BED)
        .withProperty(BlockBed.FACING, getFacing(bedBlock.getFacing()))
        .withProperty(BlockBed.PART, bedBlock.isHead() ? BlockBed.EnumPartType.HEAD : BlockBed.EnumPartType.FOOT);
  }

  private static MetaBlock1_12 getPumpkin(PumpkinBlock block) {
    return createMetaBlock(block.isLit() ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN)
        .withProperty(BlockPumpkin.FACING, getFacing(block.getFacing()));
  }

  private static MetaBlock1_12 getAnvil(AnvilBlock block) {
    if (!RogueConfig.FURNITURE.getBoolean()) {
      return createMetaBlock(Blocks.STONE).withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE);
    }
    return createMetaBlock(Blocks.ANVIL)
        .withProperty(BlockAnvil.DAMAGE, block.getDamage().ordinal())
        .withProperty(BlockAnvil.FACING, getFacing(block.getFacing()));
  }

  private static MetaBlock1_12 getTrapdoor(TrapdoorBlock block) {
    return createMetaBlock(block.getMaterial() == Material.METAL ? Blocks.IRON_DOOR : Blocks.TRAPDOOR)
        .withProperty(BlockTrapDoor.HALF, block.isFlushWithTop() ? BlockTrapDoor.DoorHalf.TOP : BlockTrapDoor.DoorHalf.BOTTOM)
        .withProperty(BlockTrapDoor.OPEN, block.isOpen())
        .withProperty(BlockTrapDoor.FACING, getFacing(block.getFacing()));
  }

  private static MetaBlock1_12 getSlab(SlabBlock slabBlock) {
    Block minecraftBlock = getSlabMinecraftBlock(slabBlock.getBlockType(), slabBlock.isFullBlock());
    MetaBlock1_12 metaBlock = createMetaBlock(minecraftBlock);
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

  private static Block getSlabMinecraftBlock(BlockType blockType, boolean isFullBlock) {
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

  private static MetaBlock1_12 getTorch(TorchBlock torchBlock) {
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
            : getFacing(dir.reverse());

    return createMetaBlock(minecraftTorchBlock)
        .withProperty(BlockTorch.FACING, facing);
  }

  private static Block getTerracottaByColor(DyeColor color) {

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

  private static MetaBlock1_12 getLogMetaBlock(Wood type, Direction facing) {
    Block minecraftLogBlock = getMinecraftLogBlock(type);
    MetaBlock1_12 log = createMetaBlock(minecraftLogBlock);
    MetaBlock1_12 logWithVariants = addLogVariants(log, type);
    return logWithVariants.withProperty(BlockLog.LOG_AXIS, mapLogFacing(facing));
  }

  public static BlockLog.EnumAxis mapLogFacing(Direction facing) {
    switch (facing) {
      case UP:
      case DOWN:
        return BlockLog.EnumAxis.Y;
      case EAST:
      case WEST:
        return BlockLog.EnumAxis.X;
      case NORTH:
      case SOUTH:
        return BlockLog.EnumAxis.Z;
      default:
        return BlockLog.EnumAxis.NONE;
    }
  }

  private static Block getMinecraftLogBlock(Wood type) {
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

  private static MetaBlock1_12 addLogVariants(MetaBlock1_12 log, Wood wood) {
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

  private static MetaBlock1_12 getPlankMetaBlock(Wood type) {
    MetaBlock1_12 plank = createMetaBlock(Blocks.PLANKS);
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

  public static BlockPlanks.EnumType getWoodVariant(Wood type) {
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

  private static MetaBlock1_12 createVine(Direction dir) {
    MetaBlock1_12 vine = createMetaBlock(Blocks.VINE);
    vine.withProperty(BlockVine.UP, dir == Direction.UP);
    vine.withProperty(BlockVine.NORTH, dir == Direction.NORTH);
    vine.withProperty(BlockVine.EAST, dir == Direction.EAST);
    vine.withProperty(BlockVine.SOUTH, dir == Direction.SOUTH);
    vine.withProperty(BlockVine.WEST, dir == Direction.WEST);
    return vine;
  }

  private static MetaBlock1_12 getInfestedBlock(InfestedBlock block) {
    MetaBlock1_12 block1 = createMetaBlock(Blocks.MONSTER_EGG);
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

  public static EnumFacing getFacing(Direction direction) {
    // NOTE: I notice these are flipped but I'm not entirely sure why.
    // *** If you "fix" them, then all of the blocks in the dungeon are reversed.
    switch (direction) {
      default:
      case NORTH:
        return EnumFacing.SOUTH;
      case EAST:
        return EnumFacing.WEST;
      case SOUTH:
        return EnumFacing.NORTH;
      case WEST:
        return EnumFacing.EAST;
      case UP:
        return EnumFacing.UP;
      case DOWN:
        return EnumFacing.DOWN;
    }
  }

  public static EnumDyeColor toEnumDyeColor(DyeColor color) {
    try {
      return EnumDyeColor.valueOf(color.toString());
    } catch (IllegalArgumentException illegalArgumentException) {
      return EnumDyeColor.WHITE;
    }
  }
}
