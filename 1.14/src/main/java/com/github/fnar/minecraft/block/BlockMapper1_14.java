package com.github.fnar.minecraft.block;

import com.google.gson.JsonElement;

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

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.TripWireHookBlock;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Direction;

import static net.minecraft.block.Blocks.END_PORTAL;
import static net.minecraft.block.Blocks.END_PORTAL_FRAME;

public class BlockMapper1_14 {

  public static BlockState map(SingleBlockBrush blockBrush) throws CouldNotMapBlockException {
    JsonElement json = blockBrush.getJson();
    BlockType blockType = blockBrush.getBlockType();
    if (json != null) {
      if (BlockType.OAK_STAIRS.equals(blockType)) {
        return StairsBlockMapper1_14.map((StairsBlock) blockBrush);
      }
      if (BlockType.OAK_DOOR.equals(blockType)) {
        return DoorBlockMapper1_14.map((DoorBlock) blockBrush);
      }
      return BlockParser1_14.parse(json);
    }

    Direction facing = blockBrush.getFacing();

    net.minecraft.util.Direction direction = DirectionMapper1_14.map(facing);

    switch (blockType) {
      case ANVIL:
        return AnvilBlockMapper1_14.map((AnvilBlock) blockBrush);
      case ACACIA_LOG:
        return WoodMapper1_14.mapLogs(Wood.ACACIA, facing);
      case BIRCH_LOG:
        return WoodMapper1_14.mapLogs(Wood.BIRCH, facing);
      case DARK_OAK_LOG:
        return WoodMapper1_14.mapLogs(Wood.DARK_OAK, facing);
      case JUNGLE_LOG:
        return WoodMapper1_14.mapLogs(Wood.JUNGLE, facing);
      case OAK_LOG:
        return WoodMapper1_14.mapLogs(Wood.OAK, facing);
      case SPRUCE_LOG:
        return WoodMapper1_14.mapLogs(Wood.SPRUCE, facing);
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
        return WoodMapper1_14.mapPlanks(Wood.ACACIA);
      case BIRCH_PLANK:
        return WoodMapper1_14.mapPlanks(Wood.BIRCH);
      case DARK_OAK_PLANK:
        return WoodMapper1_14.mapPlanks(Wood.DARK_OAK);
      case JUNGLE_PLANK:
        return WoodMapper1_14.mapPlanks(Wood.JUNGLE);
      case OAK_PLANK:
        return WoodMapper1_14.mapPlanks(Wood.OAK);
      case SPRUCE_PLANK:
        return WoodMapper1_14.mapPlanks(Wood.SPRUCE);
      case OAK_LEAVES:
        return WoodMapper1_14.mapLeaves(Wood.OAK);
      case SPRUCE_LEAVES:
        return WoodMapper1_14.mapLeaves(Wood.SPRUCE);
      case BIRCH_LEAVES:
        return WoodMapper1_14.mapLeaves(Wood.BIRCH);
      case JUNGLE_LEAVES:
        return WoodMapper1_14.mapLeaves(Wood.JUNGLE);
      case ACACIA_LEAVES2:
        return WoodMapper1_14.mapLeaves(Wood.ACACIA);
      case DARK_OAK_LEAVES:
        return WoodMapper1_14.mapLeaves(Wood.DARK_OAK);
      case CARPET:
      case CONCRETE:
      case CONCRETE_POWDER:
      case STAINED_GLASS:
      case STAINED_GLASS_PANE:
      case STAINED_HARDENED_CLAY:
      case TERRACOTTA:
      case WOOL:
        return ColoredBlockMapper1_14.getColoredBlock((ColoredBlock) blockBrush);
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
        return StairsBlockMapper1_14.map((StairsBlock) blockBrush);
      case ACACIA_SLAB:
      case BIRCH_SLAB:
      case BRICK_SLAB:
      case COBBLESTONE_SLAB:
      case DARK_OAK_SLAB:
      case JUNGLE_SLAB:
      case PETRIFIED_OAK_SLAB:
      case NETHER_BRICK_SLAB:
      case OAK_SLAB:
      case QUARTZ_SLAB:
      case RED_SANDSTONE_SLAB:
      case SANDSTONE_SLAB:
      case SMOOTH_RED_SANDSTONE_SLAB:
      case SPRUCE_SLAB:
      case STONE_BRICK_SLAB:
      case STONE_SLAB:
        return SlabBlockMapper1_14.map((SlabBlock) blockBrush);
      case IRON_DOOR:
      case OAK_DOOR:
      case BIRCH_DOOR:
      case SPRUCE_DOOR:
      case JUNGLE_DOOR:
      case ACACIA_DOOR:
      case DARK_OAK_DOOR:
      case WARPED_DOOR:
      case CRIMSON_DOOR:
        return DoorBlockMapper1_14.map((DoorBlock) blockBrush);
      case CHISELED_QUARTZ:
        return QuartzBlockMapper1_14.map(Quartz.CHISELED, facing);
      case PILLAR_QUARTZ:
        return QuartzBlockMapper1_14.map(Quartz.PILLAR, facing);
      case SMOOTH_QUARTZ:
        return QuartzBlockMapper1_14.map(Quartz.SMOOTH, facing);
      case VINE:
        return VineBlockMapper1_14.map(facing);
      case CROP:
        return CropBlockMapper1_14.map((CropBlock) blockBrush);
      case PUMPKIN:
        return blockBrush instanceof PumpkinBlock ? PumpkinBlockMapper1_14.map((PumpkinBlock) blockBrush) : Blocks.PUMPKIN.getDefaultState();
      case FLOWER_POT:
        return Blocks.FLOWER_POT.getDefaultState();
      case BROWN_MUSHROOM:
        return Blocks.BROWN_MUSHROOM.getDefaultState();
      case RED_MUSHROOM:
        return Blocks.RED_MUSHROOM.getDefaultState();
      case PLANT:
        return PlantBlockMapper1_14.map((PlantBlock) blockBrush);
      case TALL_PLANT:
        return TallGrassMapper1_14.map((TallPlantBlock) blockBrush);
      case GRASS_PLANT:
        return Blocks.GRASS.getDefaultState();
      case FERN:
        return Blocks.FERN.getDefaultState();
      case TRAPDOOR:
        return TrapdoorBlockMapper1_14.map((TrapdoorBlock) blockBrush);
      case LEVER:
        return LeverMapper1_14.getLever((LeverBlock) blockBrush);
      case TORCH:
      case REDSTONE_TORCH:
        return TorchBlockMapper1_14.map((TorchBlock) blockBrush);
      case BED:
        return BedBlockMapper1_14.map((BedBlock) blockBrush);
      case FURNACE:
        if (!RogueConfig.FURNITURE.getBoolean()) {
          return Blocks.COBBLESTONE.getDefaultState();
        }
        return Blocks.FURNACE.getDefaultState().with(FurnaceBlock.FACING, direction);
      case DISPENSER:
        return Blocks.DISPENSER.getDefaultState().with(DispenserBlock.FACING, direction);
      case DROPPER:
        return Blocks.DROPPER.getDefaultState().with(DropperBlock.FACING, direction);
      case HOPPER:
        return Blocks.HOPPER.getDefaultState()
            .with(HopperBlock.FACING, direction);
      case COCOA_BEANS:
        return Blocks.COCOA.getDefaultState();
      case COMPARATOR:
        return ComparatorBlockMapper1_14.map((ComparatorBlock) blockBrush);
      case REPEATER:
        return RepeaterBlockMapper1_14.map((RepeaterBlock) blockBrush);
      case PISTON:
        return Blocks.PISTON.getDefaultState()
            .with(PistonBlock.FACING, direction);
      case STICKY_PISTON:
        return Blocks.STICKY_PISTON.getDefaultState()
            .with(PistonBlock.FACING, direction);
      case INFESTED_BLOCK:
        return InfestedBlockMapper1_14.map((InfestedBlock) blockBrush);
      case CHEST:
        return Blocks.CHEST.getDefaultState()
            .with(ChestBlock.FACING, direction);
      case TRAPPED_CHEST:
        return Blocks.TRAPPED_CHEST.getDefaultState()
            .with(ChestBlock.FACING, direction);
      case NETHER_PORTAL:
        return Blocks.NETHER_PORTAL.getDefaultState()
            .with(NetherPortalBlock.AXIS, direction.getAxis());
      case TRIPWIRE:
        return Blocks.TRIPWIRE.getDefaultState();
      case TRIPWIRE_HOOK:
        return Blocks.TRIPWIRE_HOOK.getDefaultState()
            .with(TripWireHookBlock.ATTACHED, ((TripwireHookBlock) blockBrush).isAttached())
            .with(TripWireHookBlock.FACING, direction);
      case LADDER:
        return Blocks.LADDER.getDefaultState()
            .with(LadderBlock.FACING, direction);
      case AIR:
        return Blocks.AIR.getDefaultState();
      case WATER_STILL:
        return Blocks.WATER.getDefaultState();
      case WATER_FLOWING:
        return Blocks.WATER.getDefaultState();
      case LAVA_STILL:
        return Blocks.LAVA.getDefaultState();
      case LAVA_FLOWING:
        return Blocks.LAVA.getDefaultState();
      case FIRE:
        return Blocks.FIRE.getDefaultState();
      case IRON_BAR:
        return Blocks.IRON_BARS.getDefaultState();
      case STONE_SMOOTH:
        return Blocks.STONE.getDefaultState();
      case GRANITE:
        return Blocks.GRANITE.getDefaultState();
      case GRANITE_POLISHED:
        return Blocks.POLISHED_GRANITE.getDefaultState();
      case DIORITE:
        return Blocks.DIORITE.getDefaultState();
      case DIORITE_POLISHED:
        return Blocks.POLISHED_DIORITE.getDefaultState();
      case ANDESITE:
        return Blocks.ANDESITE.getDefaultState();
      case ANDESITE_POLISHED:
        return Blocks.POLISHED_ANDESITE.getDefaultState();
      case GRASS_BLOCK:
        return Blocks.GRASS.getDefaultState();
      case DIRT:
        return Blocks.DIRT.getDefaultState();
      case DIRT_COARSE:
        return Blocks.COARSE_DIRT.getDefaultState();
      case DIRT_PODZOL:
        return Blocks.PODZOL.getDefaultState();
      case COBBLESTONE:
        return Blocks.COBBLESTONE.getDefaultState();
      case COBBLESTONE_WALL:
        return Blocks.COBBLESTONE_WALL.getDefaultState();
      case BEDROCK:
        return Blocks.BEDROCK.getDefaultState();
      case SAND:
        return Blocks.SAND.getDefaultState();
      case SAND_RED:
        return Blocks.RED_SAND.getDefaultState();
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
        return Blocks.CHISELED_SANDSTONE.getDefaultState();
      case SANDSTONE_SMOOTH:
        return Blocks.SMOOTH_SANDSTONE.getDefaultState();
      case GOLD_BLOCK:
        return Blocks.GOLD_BLOCK.getDefaultState();
      case IRON_BLOCK:
        return Blocks.IRON_BLOCK.getDefaultState();
      case BRICK:
        return Blocks.BRICKS.getDefaultState();
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
      case STONE_BRICKS:
        return Blocks.STONE_BRICKS.getDefaultState();
      case STONE_BRICK_MOSSY:
        return Blocks.MOSSY_STONE_BRICKS.getDefaultState();
      case STONE_BRICK_CRACKED:
        return Blocks.CRACKED_STONE_BRICKS.getDefaultState();
      case STONE_BRICK_CHISELED:
        return Blocks.CHISELED_STONE_BRICKS.getDefaultState();
      case MYCELIUM:
        return Blocks.MYCELIUM.getDefaultState();
      case NETHERBRICK:
        return Blocks.NETHER_BRICKS.getDefaultState();
      case END_PORTAL:
        return END_PORTAL.getDefaultState();
      case END_PORTAL_FRAME:
        return END_PORTAL_FRAME.getDefaultState();
      case END_STONE:
        return Blocks.END_STONE.getDefaultState();
      case EMERALD_BLOCK:
        return Blocks.EMERALD_BLOCK.getDefaultState();
      case ORE_QUARTZ:
        return Blocks.NETHER_QUARTZ_ORE.getDefaultState();
      case PRISMARINE:
        return Blocks.PRISMARINE.getDefaultState();
      case PRISMARINE_BRICKS:
        return Blocks.PRISMARINE_BRICKS.getDefaultState();
      case PRISMARINE_DARK:
        return Blocks.DARK_PRISMARINE.getDefaultState();
      case SEA_LANTERN:
        return Blocks.SEA_LANTERN.getDefaultState();
      case COAL_BLOCK:
        return Blocks.COAL_BLOCK.getDefaultState();
      case ICE_PACKED:
        return Blocks.PACKED_ICE.getDefaultState();
      case RED_SANDSTONE:
        return Blocks.RED_SANDSTONE.getDefaultState();
      case CHISELED_RED_SANDSTONE:
        return Blocks.CHISELED_RED_SANDSTONE.getDefaultState();
      case SMOOTH_RED_SANDSTONE:
        return Blocks.SMOOTH_RED_SANDSTONE.getDefaultState();
      case QUARTZ:
        return Blocks.QUARTZ_BLOCK.getDefaultState();
      case REDSTONE_BLOCK:
        return Blocks.REDSTONE_BLOCK.getDefaultState();
      case PRESSURE_PLATE_STONE:
        return Blocks.STONE_PRESSURE_PLATE.getDefaultState();
      case OAK_PRESSURE_PLATE:
        return Blocks.OAK_PRESSURE_PLATE.getDefaultState();
      case ACACIA_PRESSURE_PLATE:
        return Blocks.ACACIA_PRESSURE_PLATE.getDefaultState();
      case BIRCH_PRESSURE_PLATE:
        return Blocks.BIRCH_PRESSURE_PLATE.getDefaultState();
      case DARK_OAK_PRESSURE_PLATE:
        return Blocks.DARK_OAK_PRESSURE_PLATE.getDefaultState();
      case SPRUCE_PRESSURE_PLATE:
        return Blocks.SPRUCE_PRESSURE_PLATE.getDefaultState();
      case JUNGLE_PRESSURE_PLATE:
        return Blocks.JUNGLE_PRESSURE_PLATE.getDefaultState();
      case BOOKSHELF:
        return Blocks.BOOKSHELF.getDefaultState();
      case REDSTONE_WIRE:
        return Blocks.REDSTONE_WIRE.getDefaultState();
      case SUGAR_CANE:
        return Blocks.SUGAR_CANE.getDefaultState();
      case CRAFTING_TABLE:
        return (!RogueConfig.FURNITURE.getBoolean()
            ? map(Wood.OAK.getPlanks())
            : Blocks.CRAFTING_TABLE.getDefaultState());
      case NOTEBLOCK:
        return Blocks.NOTE_BLOCK.getDefaultState();
      case REDSTONE_LAMP:
        return Blocks.REDSTONE_LAMP.getDefaultState().with(RedstoneLampBlock.LIT, false);
      case REDSTONE_LAMP_LIT:
        return Blocks.REDSTONE_LAMP.getDefaultState().with(RedstoneLampBlock.LIT, true);
      case JUKEBOX:
        return Blocks.JUKEBOX.getDefaultState();
      case TNT:
        return Blocks.TNT.getDefaultState();
      case ENCHANTING_TABLE:
        return Blocks.ENCHANTING_TABLE.getDefaultState();
      case FENCE_NETHER_BRICK:
        return Blocks.NETHER_BRICK_FENCE.getDefaultState();
      case COBWEB:
        return Blocks.COBWEB.getDefaultState();
      case PURPUR_BLOCK:
        return Blocks.PURPUR_BLOCK.getDefaultState();
      case PURPUR_PILLAR:
        return Blocks.PURPUR_PILLAR.getDefaultState();
      case PURPUR_STAIR:
        return Blocks.PURPUR_STAIRS.getDefaultState();
      case PURPUR_SLAB:
        return Blocks.PURPUR_SLAB.getDefaultState();
      case END_STONE_BRICKS:
        return Blocks.END_STONE_BRICKS.getDefaultState();
      case MAGMA:
        return Blocks.MAGMA_BLOCK.getDefaultState();
      case RED_NETHER_BRICKS:
        return Blocks.RED_NETHER_BRICKS.getDefaultState();
      case NETHER_WART_BLOCK:
        return Blocks.NETHER_WART_BLOCK.getDefaultState();
      case OAK_SAPLING:
        return WoodMapper1_14.map(Wood.OAK);
      case SPRUCE_SAPLING:
        return WoodMapper1_14.map(Wood.SPRUCE);
      case BIRCH_SAPLING:
        return WoodMapper1_14.map(Wood.BIRCH);
      case BONE_BLOCK:
        return Blocks.BONE_BLOCK.getDefaultState();
      case BREWING_STAND:
        return Blocks.BREWING_STAND.getDefaultState();
      case CACTUS:
        return Blocks.CACTUS.getDefaultState();
      case CAKE:
        return Blocks.CAKE.getDefaultState();
      case SPAWNER:
        return Blocks.SPAWNER.getDefaultState();
      case ENDER_CHEST:
        return Blocks.ENDER_CHEST.getDefaultState();
      case POPPY:
        return Blocks.POPPY.getDefaultState();
      case SKELETONS_SKULL:
        return Blocks.SKELETON_SKULL.getDefaultState();
      case WITHER_SKELETON_SKULL:
        return Blocks.WITHER_SKELETON_SKULL.getDefaultState();
      case DANDELION:
        return Blocks.DANDELION.getDefaultState();
      case JUNGLE_SAPLING:
        return WoodMapper1_14.map(Wood.JUNGLE);
      case ACACIA_SAPLING:
        return WoodMapper1_14.map(Wood.ACACIA);
      case DARK_OAK_SAPLING:
        return WoodMapper1_14.map(Wood.DARK_OAK);
      case WHEAT:
        return Blocks.WHEAT.getDefaultState();
      case OAK_SIGN:
        return Blocks.OAK_SIGN.getDefaultState();
      case SPONGE:
        return Blocks.SPONGE.getDefaultState();
    }
    throw new ReportThisIssueException(new UnmappedBlockException(blockType));
  }

}
