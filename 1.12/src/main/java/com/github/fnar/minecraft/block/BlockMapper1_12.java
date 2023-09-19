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
import com.github.fnar.minecraft.material.Wood;
import com.github.fnar.util.ReportThisIssueException;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Direction;

public class BlockMapper1_12 {

  public static IBlockState map(SingleBlockBrush blockBrush) {
    Direction facing = blockBrush.getFacing();

    EnumFacing enumFacing = FacingMapper1_12.getFacing(facing);

    BlockType blockType = blockBrush.getBlockType();

    switch (blockType) {
      case ANVIL:
        return AnvilBlockMapper1_12.map((AnvilBlock) blockBrush);
      case ACACIA_LOG:
        return WoodMapper1_12.mapLogs(Wood.ACACIA, facing);
      case BIRCH_LOG:
        return WoodMapper1_12.mapLogs(Wood.BIRCH, facing);
      case DARK_OAK_LOG:
        return WoodMapper1_12.mapLogs(Wood.DARK_OAK, facing);
      case JUNGLE_LOG:
        return WoodMapper1_12.mapLogs(Wood.JUNGLE, facing);
      case OAK_LOG:
        return WoodMapper1_12.mapLogs(Wood.OAK, facing);
      case SPRUCE_LOG:
        return WoodMapper1_12.mapLogs(Wood.SPRUCE, facing);
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
        return WoodMapper1_12.mapPlanks(Wood.ACACIA);
      case BIRCH_PLANK:
        return WoodMapper1_12.mapPlanks(Wood.BIRCH);
      case DARK_OAK_PLANK:
        return WoodMapper1_12.mapPlanks(Wood.DARK_OAK);
      case JUNGLE_PLANK:
        return WoodMapper1_12.mapPlanks(Wood.JUNGLE);
      case OAK_PLANK:
        return WoodMapper1_12.mapPlanks(Wood.OAK);
      case SPRUCE_PLANK:
        return WoodMapper1_12.mapPlanks(Wood.SPRUCE);
      case OAK_LEAVES:
        return WoodMapper1_12.mapLeaves(Wood.OAK);
      case SPRUCE_LEAVES:
        return WoodMapper1_12.mapLeaves(Wood.SPRUCE);
      case BIRCH_LEAVES:
        return WoodMapper1_12.mapLeaves(Wood.BIRCH);
      case JUNGLE_LEAVES:
        return WoodMapper1_12.mapLeaves(Wood.JUNGLE);
      case ACACIA_LEAVES2:
        return WoodMapper1_12.mapLeaves(Wood.ACACIA);
      case DARK_OAK_LEAVES:
        return WoodMapper1_12.mapLeaves(Wood.DARK_OAK);
      case CARPET:
      case CONCRETE:
      case CONCRETE_POWDER:
      case STAINED_GLASS:
      case STAINED_GLASS_PANE:
      case STAINED_HARDENED_CLAY:
      case TERRACOTTA:
      case WOOL:
        return ColoredBlockMapper1_12.getColoredBlock((ColoredBlock) blockBrush);
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
        return SlabBlockMapper1_12.map((SlabBlock) blockBrush);
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
        return VineBlockMapper1_12.map(facing);
      case CROP:
        return CropBlockMapper1_12.map((CropBlock) blockBrush);
      case PUMPKIN:
        return blockBrush instanceof PumpkinBlock ? PumpkinBlockMapper1_12.map((PumpkinBlock) blockBrush) : Blocks.PUMPKIN.getDefaultState();
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
        return TrapdoorBlockMapper1_12.map((TrapdoorBlock) blockBrush);
      case LEVER:
        return LeverMapper1_12.getLever((LeverBlock) blockBrush);
      case TORCH:
      case REDSTONE_TORCH:
        return TorchBlockMapper1_12.map((TorchBlock) blockBrush);
      case BED:
        return BedBlockMapper1_12.map((BedBlock) blockBrush);
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
        return ComparatorBlockMapper1_12.map((ComparatorBlock) blockBrush);
      case REPEATER:
        return RepeaterBlockMapper1_12.map((RepeaterBlock) blockBrush);
      case PISTON:
        return Blocks.PISTON.getDefaultState()
            .withProperty(BlockPistonBase.FACING, enumFacing);
      case STICKY_PISTON:
        return Blocks.STICKY_PISTON.getDefaultState()
            .withProperty(BlockPistonBase.FACING, enumFacing);
      case INFESTED_BLOCK:
        return InfestedBlockMapper1_12.map((InfestedBlock) blockBrush);
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
            ? map(Wood.OAK.getPlanks())
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

}
