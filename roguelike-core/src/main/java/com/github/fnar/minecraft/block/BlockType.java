package com.github.fnar.minecraft.block;

import com.github.fnar.minecraft.block.decorative.TorchBlock;
import com.github.fnar.minecraft.block.normal.ColoredBlock;
import com.github.fnar.minecraft.block.normal.SlabBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.item.BlockItem;

public enum BlockType {

  ACACIA_DOOR,
  ACACIA_FENCE,
  ACACIA_LEAVES2,
  ACACIA_LOG,
  ACACIA_PLANK,
  ACACIA_SAPLING,
  ACACIA_SLAB,
  ACACIA_STAIRS,
  AIR,
  ANDESITE,
  ANDESITE_POLISHED,
  ANVIL,
  BED,
  BEDROCK,
  BIRCH_DOOR,
  BIRCH_FENCE,
  BIRCH_LEAVES,
  BIRCH_LOG,
  BIRCH_PLANK,
  BIRCH_SAPLING,
  BIRCH_SLAB,
  BIRCH_STAIRS,
  BONE_BLOCK,
  BOOKSHELF,
  BREWING_STAND,
  BRICK,
  BRICK_SLAB,
  BRICK_STAIRS,
  BROWN_MUSHROOM,
  CACTUS,
  CAKE,
  CARPET,
  CHEST,
  CHISELED_QUARTZ,
  CHISELED_RED_SANDSTONE,
  CHISELED_SANDSTONE,
  CLAY,
  COAL_BLOCK,
  COBBLESTONE,
  COBBLESTONE_MOSSY,
  COBBLESTONE_WALL,
  COBBLE_SLAB,
  COCOA_BEANS,
  COMPARATOR,
  CONCRETE,
  CONCRETE_POWDER,
  CRAFTING_TABLE,
  CRIMSON_DOOR,
  CROP,
  DARK_OAK_DOOR,
  DARK_OAK_FENCE,
  DARK_OAK_LEAVES,
  DARK_OAK_LOG,
  DARK_OAK_PLANK,
  DARK_OAK_SAPLING,
  DARK_OAK_SLAB,
  DARK_OAK_STAIRS,
  DIAMOND_BLOCK,
  DIORITE,
  DIORITE_POLISHED,
  DIRT,
  DIRT_COARSE,
  DIRT_PODZOL,
  DISPENSER,
  DROPPER,
  EMERALD_BLOCK,
  ENCHANTING_TABLE,
  ENDER_BRICK,
  ENDER_CHEST,
  END_STONE,
  FARMLAND,
  FENCE_NETHER_BRICK,
  FIRE,
  FLOWER_POT,
  FURNACE,
  GLASS,
  GLASS_PANE,
  GLOWSTONE,
  GOLD_BLOCK,
  GRANITE,
  GRANITE_POLISHED,
  GRASS,
  GRAVEL,
  HOPPER,
  ICE,
  ICE_PACKED,
  INFESTED_BLOCK,
  IRON_BAR,
  IRON_BLOCK,
  IRON_DOOR,
  JUKEBOX,
  JUNGLE_DOOR,
  JUNGLE_FENCE,
  JUNGLE_LEAVES,
  JUNGLE_LOG,
  JUNGLE_PLANK,
  JUNGLE_SAPLING,
  JUNGLE_SLAB,
  JUNGLE_STAIRS,
  LADDER,
  LAPIS_BLOCK,
  LAVA_FLOWING,
  LAVA_STILL,
  LEGACY_OAK_SLAB,
  LEVER,
  MAGMA,
  MOB_SPAWNER,
  MYCELIUM,
  NETHERBRICK,
  NETHERBRICK_SLAB,
  NETHERRACK,
  NETHER_BRICK_STAIRS,
  NETHER_PORTAL,
  NETHER_WART_BLOCK,
  NOTEBLOCK,
  OAK_DOOR,
  OAK_FENCE,
  OAK_LEAVES,
  OAK_LOG,
  OAK_PLANK,
  OAK_SAPLING,
  OAK_SLAB,
  OAK_STAIRS,
  OBSIDIAN,
  ORE_COAL,
  ORE_DIAMOND,
  ORE_EMERALD,
  ORE_GOLD,
  ORE_IRON,
  ORE_LAPIS,
  ORE_QUARTZ,
  ORE_REDSTONE,
  PILLAR_QUARTZ,
  PISTON,
  PLANT,
  PRESSURE_PLATE_STONE,
  PRESSURE_PLATE_WOODEN,
  PRISMARINE,
  PRISMARINE_DARK,
  PRISMITE,
  PUMPKIN,
  PURPUR_BLOCK,
  PURPUR_DOUBLE_SLAB,
  PURPUR_PILLAR,
  PURPUR_SLAB,
  PURPUR_STAIR,
  PURPUR_STAIRS,
  QUARTZ,
  QUARTZ_SLAB,
  QUARTZ_STAIRS,
  REDSTONE_BLOCK,
  REDSTONE_LAMP,
  REDSTONE_LAMP_LIT,
  REDSTONE_TORCH,
  REDSTONE_WIRE,
  RED_FLOWER,
  RED_MUSHROOM,
  RED_NETHERBRICK,
  RED_SANDSTONE,
  RED_SANDSTONE_SLAB,
  RED_SANDSTONE_STAIRS,
  REEDS,
  REPEATER,
  SAND,
  SANDSTONE,
  SANDSTONE_SLAB,
  SANDSTONE_SMOOTH,
  SANDSTONE_STAIRS,
  SAND_RED,
  SEA_LANTERN,
  SIGN,
  SKULL,
  SMOOTH_QUARTZ,
  SMOOTH_RED_SANDSTONE,
  SMOOTH_RED_SANDSTONE_SLAB,
  SNOW,
  SOUL_SAND,
  SPONGE,
  SPRUCE_DOOR,
  SPRUCE_FENCE,
  SPRUCE_LEAVES,
  SPRUCE_LOG,
  SPRUCE_PLANK,
  SPRUCE_SAPLING,
  SPRUCE_SLAB,
  SPRUCE_STAIRS,
  STAINED_GLASS,
  STAINED_GLASS_PANE,
  STAINED_HARDENED_CLAY,
  STICKY_PISTON,
  STONEBRICK_SLAB,
  STONE_BRICK,
  STONE_BRICK_CHISELED,
  STONE_BRICK_CRACKED,
  STONE_BRICK_MOSSY,
  STONE_BRICK_STAIRS,
  STONE_SLAB,
  STONE_SMOOTH,
  STONE_STAIRS,
  TALL_PLANT,
  TERRACOTTA,
  TNT,
  TORCH,
  TRAPDOOR,
  TRAPPED_CHEST,
  TRIPWIRE,
  TRIPWIRE_HOOK,
  VINE,
  WARPED_DOOR,
  WATER_FLOWING,
  WATER_STILL,
  WEB,
  WHEAT,
  WOOL,
  YELLOW_FLOWER,
  ;

  public BlockItem asItem() {
    return new BlockItem(this);
  }

  public SingleBlockBrush getBrush() {
    switch (this) {

      // colored blocks
      case CARPET:
        return ColoredBlock.carpet();
      case CONCRETE:
        return ColoredBlock.concrete();
      case CONCRETE_POWDER:
        return ColoredBlock.concretePowder();
      case STAINED_GLASS:
        return ColoredBlock.stainedGlass();
      case STAINED_GLASS_PANE:
        return ColoredBlock.stainedGlassPane();
      case STAINED_HARDENED_CLAY:
        return ColoredBlock.stainedHardenedClay();
      case TERRACOTTA:
        return ColoredBlock.terracotta();
      case WOOL:
        return ColoredBlock.wool();


      // stairs
      case ACACIA_STAIRS:
        return StairsBlock.acacia();
      case BIRCH_STAIRS:
        return StairsBlock.birch();
      case BRICK_STAIRS:
        return StairsBlock.brick();
      case DARK_OAK_STAIRS:
        return StairsBlock.darkOak();
      case JUNGLE_STAIRS:
        return StairsBlock.jungle();
      case NETHER_BRICK_STAIRS:
        return StairsBlock.netherBrick();
      case OAK_STAIRS:
        return StairsBlock.oak();
      case PURPUR_STAIRS:
        return StairsBlock.purpur();
      case QUARTZ_STAIRS:
        return StairsBlock.quartz();
      case RED_SANDSTONE_STAIRS:
        return StairsBlock.redSandstone();
      case SANDSTONE_STAIRS:
        return StairsBlock.sandstone();
      case SPRUCE_STAIRS:
        return StairsBlock.spruce();
      case STONE_BRICK_STAIRS:
        return StairsBlock.stoneBrick();
      case STONE_STAIRS:
        return StairsBlock.stone();


      // slabs
      case ACACIA_SLAB:
        return SlabBlock.acacia();
      case BIRCH_SLAB:
        return SlabBlock.birch();
      case BRICK_SLAB:
        return SlabBlock.brick();
      case COBBLE_SLAB:
        return SlabBlock.cobble();
      case DARK_OAK_SLAB:
        return SlabBlock.darkOak();
      case JUNGLE_SLAB:
        return SlabBlock.jungle();
      case LEGACY_OAK_SLAB:
        return SlabBlock.legacyOak();
      case NETHERBRICK_SLAB:
        return SlabBlock.netherBrick();
      case OAK_SLAB:
        return SlabBlock.oak();
      case QUARTZ_SLAB:
        return SlabBlock.quartz();
      case RED_SANDSTONE_SLAB:
        return SlabBlock.redSandStone();
      case SANDSTONE_SLAB:
        return SlabBlock.sandstone();
      case SMOOTH_RED_SANDSTONE_SLAB:
        return SlabBlock.smoothRedSandstone();
      case SPRUCE_SLAB:
        return SlabBlock.spruce();
      case STONEBRICK_SLAB:
        return SlabBlock.stoneBrick();
      case STONE_SLAB:
        return SlabBlock.stone();

      // torches
      case REDSTONE_TORCH:
        return TorchBlock.redstone();
      case TORCH:
        return TorchBlock.torch();
      default:
        return new SingleBlockBrush(this);
    }
  }

}
