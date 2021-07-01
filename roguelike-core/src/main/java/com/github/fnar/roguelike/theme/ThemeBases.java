package com.github.fnar.roguelike.theme;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.normal.ColoredBlock;
import com.github.fnar.minecraft.block.normal.InfestedBlock;
import com.github.fnar.minecraft.block.normal.Quartz;
import com.github.fnar.minecraft.block.normal.SlabBlock;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.normal.Wood;
import com.github.fnar.minecraft.block.redstone.DoorBlock;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.theme.BlockSet;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Direction;

import static com.github.fnar.minecraft.block.normal.ColoredBlock.concrete;
import static com.github.fnar.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeBases {


  public static ThemeBase OAK = new ThemeBase() {
    {
      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.STONE_BRICK.getBrush(), 30);
      BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();
      walls.addBlock(cracked, 20);
      walls.addBlock(BlockType.COBBLESTONE.getBrush(), 5);
      walls.addBlock(BlockType.GRAVEL.getBrush(), 1);

      StairsBlock stair = StairsBlock.stoneBrick();
      DoorBlock door = DoorBlock.spruce();
      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          walls,
          door,
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

      Wood wood = Wood.OAK;
      BlockBrush pillar = wood.getLog();
      BlockBrush segmentWall = wood.getPlanks();
      StairsBlock segmentStair = StairsBlock.oak();
      this.secondary = new BlockSet(
          segmentWall,
          segmentWall,
          segmentStair,
          pillar,
          door,
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
    }
  };

  public static ThemeBase BLING = new ThemeBase() {
    {
      primary = new

          BlockSet(
          createWalls(),

          createWalls(),
          StairsBlock.quartz(),
          BlockType.LAPIS_BLOCK.getBrush(),
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      secondary = primary;
    }

    private BlockWeightedRandom createWalls() {
      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.IRON_BLOCK.getBrush(), 10);
      walls.addBlock(BlockType.GOLD_BLOCK.getBrush(), 3);
      walls.addBlock(BlockType.EMERALD_BLOCK.getBrush(), 10);
      walls.addBlock(BlockType.DIAMOND_BLOCK.getBrush(), 20);
      return walls;
    }
  };

  public static ThemeBase BRICK = new ThemeBase() {{
    BlockBrush walls = BlockType.BRICK.getBrush();
    StairsBlock stair = StairsBlock.brick();
    BlockBrush pillar = Wood.SPRUCE.getLog();
    BlockBrush segmentWall = Wood.SPRUCE.getPlanks();
    this.primary = new BlockSet(
        walls,
        walls,
        stair,
        walls,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = new BlockSet(
        segmentWall,
        segmentWall,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

  }};

  public static ThemeBase BUMBO = new ThemeBase() {{
    BlockBrush green = stainedHardenedClay().setColor(DyeColor.GREEN);
    BlockBrush moustache = stainedHardenedClay().setColor(DyeColor.BLACK);
    BlockBrush black = concrete().setColor(DyeColor.BLACK);
    BlockBrush white = concrete().setColor(DyeColor.WHITE);

    BlockBrush yellow = concrete().setColor(DyeColor.YELLOW);
    BlockBrush red = concrete().setColor(DyeColor.RED);

    primary = new BlockSet(
        moustache,
        green,
        StairsBlock.acacia(),
        white,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    secondary = new BlockSet(
        red,
        yellow,
        StairsBlock.acacia(),
        black,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }};

  public static ThemeBase CAVE = new ThemeBase() {{

    BlockJumble floor = new BlockJumble();
    floor.addBlock(BlockType.GRAVEL.getBrush());
    floor.addBlock(BlockType.DIRT.getBrush());
    floor.addBlock(BlockType.COBBLESTONE.getBrush());

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.STONE_SMOOTH.getBrush(), 1000);
    walls.addBlock(BlockType.DIRT.getBrush(), 100);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 50);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 20);
    walls.addBlock(BlockType.ORE_COAL.getBrush(), 10);
    walls.addBlock(BlockType.ORE_IRON.getBrush(), 5);
    walls.addBlock(BlockType.ORE_EMERALD.getBrush(), 2);
    walls.addBlock(BlockType.ORE_DIAMOND.getBrush(), 1);
    walls.addBlock(BlockType.WATER_FLOWING.getBrush(), 3);
    walls.addBlock(BlockType.LAVA_FLOWING.getBrush(), 1);

    StairsBlock stair = StairsBlock.cobble();
    BlockBrush pillar = BlockType.COBBLESTONE.getBrush();

    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    this.secondary = primary;
  }};
  public static ThemeBase CHECKER = new ThemeBase() {{
    BlockBrush one = BlockType.OBSIDIAN.getBrush();
    BlockBrush two = BlockType.QUARTZ.getBrush();
    BlockBrush checks = new BlockCheckers(one, two);
    StairsBlock stair = StairsBlock.quartz();

    this.primary = new BlockSet(
        checks,
        checks,
        stair,
        checks,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    this.secondary = primary;

  }};
  public static ThemeBase CRYPT = new ThemeBase() {{

    BlockJumble stone = new BlockJumble();
    stone.addBlock(BlockType.STONE_BRICK.getBrush());
    stone.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush());
    stone.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush());

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(stone, 100);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 10);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 5);

    BlockBrush andesite = BlockType.ANDESITE.getBrush();
    BlockBrush smoothAndesite = BlockType.ANDESITE_POLISHED.getBrush();
    BlockCheckers pillar = new BlockCheckers(andesite, smoothAndesite);

    StairsBlock stair = StairsBlock.stoneBrick();

    this.primary = new BlockSet(
        walls,
        walls,
        stair,
        pillar,
        DoorBlock.iron(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = this.primary;

  }};

  public static ThemeBase DARK_HALL = new ThemeBase() {
    {

      BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.COBBLESTONE.getBrush(), 30);
      walls.addBlock(BlockType.COBBLESTONE_MOSSY.getBrush(), 10);
      walls.addBlock(BlockType.STONE_BRICK.getBrush(), 20);
      walls.addBlock(cracked, 10);
      walls.addBlock(BlockType.GRAVEL.getBrush(), 5);
      StairsBlock stair = StairsBlock.stoneBrick();
      BlockBrush pillar = BlockType.STONE_BRICK_MOSSY.getBrush();

      BlockBrush walls2 = Wood.DARK_OAK.getPlanks();
      StairsBlock stair2 = StairsBlock.darkOak();
      BlockBrush pillar2 = Wood.DARK_OAK.getLog();

      DoorBlock door = DoorBlock.darkOak();

      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          door,
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = new BlockSet(
          walls2,
          walls2,
          stair2,
          pillar2,
          door,
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

    }
  };
  public static ThemeBase DARK_OAK = new ThemeBase() {
    {
      BlockBrush walls = BlockType.DARK_OAK_PLANK.getBrush();
      StairsBlock stair = StairsBlock.darkOak();
      BlockBrush pillar = Wood.DARK_OAK.getLog();
      DoorBlock door = DoorBlock.darkOak();

      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          door,
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = primary;
    }
  };
  public static ThemeBase ENDER = new ThemeBase() {{

    BlockStripes floor = new BlockStripes();
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.BROWN));
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.YELLOW));

    BlockBrush walls = BlockType.ENDER_BRICK.getBrush();
    StairsBlock stair = StairsBlock.sandstone();
    BlockBrush pillar = BlockType.OBSIDIAN.getBrush();
    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    this.secondary = new BlockSet(
        floor,
        BlockType.CHISELED_SANDSTONE.getBrush(),
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }};

  public static ThemeBase ENI_ICE = new ThemeBase() {
    {
      BlockBrush ice = BlockType.ICE_PACKED.getBrush();
      BlockBrush purple = stainedHardenedClay().setColor(DyeColor.PURPLE);

      BlockWeightedRandom light = new BlockWeightedRandom();
      light.addBlock(purple, 100);
      light.addBlock(BlockType.WATER_FLOWING.getBrush(), 5);
      light.addBlock(BlockType.LAPIS_BLOCK.getBrush(), 1);

      BlockWeightedRandom dark = new BlockWeightedRandom();
      dark.addBlock(BlockType.OBSIDIAN.getBrush(), 100);
      dark.addBlock(BlockType.LAVA_FLOWING.getBrush(), 5);
      dark.addBlock(BlockType.REDSTONE_BLOCK.getBrush(), 1);

      BlockStripes floor = new BlockStripes();
      floor.addBlock(light);
      floor.addBlock(dark);

      StairsBlock stair = StairsBlock.quartz();
      BlockBrush quartzPillar = Quartz.PILLAR.getBrush().setFacing(Direction.UP);

      this.primary = new BlockSet(
          floor,
          ice,
          stair,
          quartzPillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = new BlockSet(
          floor,
          ice,
          stair,
          quartzPillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
    }
  };

  public static ThemeBase ENIKO = new ThemeBase() {
    {
      BlockStripes floor = new BlockStripes();
      floor.addBlock(stainedHardenedClay().setColor(DyeColor.LIGHT_BLUE));
      floor.addBlock(stainedHardenedClay().setColor(DyeColor.WHITE));

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.STONE_BRICK.getBrush(), 100);
      walls.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush(), 1);
      walls.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush(), 5);

      StairsBlock stair = StairsBlock.stoneBrick();
      BlockBrush pillar = SlabBlock.stone().setTop(false).setFullBlock(true).setSeamless(true);

      this.primary = new BlockSet(
          floor,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = primary;
    }
  };

  public static ThemeBase ENIKO2 = new ThemeBase() {{

    BlockStripes floor = new BlockStripes();
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.BLUE));
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.LIGHT_GRAY));

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.STONE_BRICK.getBrush(), 20);
    walls.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush(), 10);
    walls.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush(), 5);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 3);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 1);

    StairsBlock stair = StairsBlock.stoneBrick();
    BlockBrush pillar = SlabBlock.stone().setTop(false).setFullBlock(true).setSeamless(true);

    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    this.secondary = primary;
  }};

  public static ThemeBase ENI_QUARTZ = new ThemeBase() {{

    BlockWeightedRandom red = new BlockWeightedRandom();
    red.addBlock(stainedHardenedClay().setColor(DyeColor.RED), 100);
    red.addBlock(BlockType.WATER_FLOWING.getBrush(), 5);
    red.addBlock(BlockType.REDSTONE_BLOCK.getBrush(), 1);

    BlockStripes floor = new BlockStripes();
    floor.addBlock(red);
    floor.addBlock(BlockType.OBSIDIAN.getBrush());

    BlockBrush walls = BlockType.BRICK.getBrush();

    StairsBlock stair = StairsBlock.brick();

    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        walls,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    BlockBrush quartz = BlockType.QUARTZ.getBrush();
    StairsBlock quartzStair = StairsBlock.quartz();
    BlockBrush quartzPillar = Quartz.PILLAR.getBrush().setFacing(Direction.UP);

    this.secondary = new BlockSet(
        floor,
        quartz,
        quartzStair,
        quartzPillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
  }};

  public static ThemeBase ETHO = new ThemeBase() {{

    BlockBrush floor = BlockType.GRASS.getBrush();

    BlockBrush walls = Wood.OAK.getPlanks();

    StairsBlock stair = StairsBlock.oak();
    BlockBrush pillar = Wood.OAK.getLog();

    this.primary = new BlockSet(
        floor,
        walls,
        stair,
        pillar,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );

    this.secondary = primary;
  }};
  public static ThemeBase ETHO_TOWER = new ThemeBase() {
    {

      BlockBrush primaryWall = stainedHardenedClay().setColor(DyeColor.CYAN);
      StairsBlock stair = StairsBlock.sandstone();
      BlockBrush secondaryWall = BlockType.SANDSTONE_SMOOTH.getBrush();

      this.primary = new BlockSet(
          primaryWall,
          primaryWall,
          stair,
          primaryWall,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = new BlockSet(
          secondaryWall,
          secondaryWall,
          stair,
          secondaryWall,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
    }
  };
  public static ThemeBase GREY = new ThemeBase() {{
    BlockBrush andesite = BlockType.ANDESITE.getBrush();
    BlockBrush smoothAndesite = BlockType.ANDESITE_POLISHED.getBrush();
    StairsBlock stair = StairsBlock.stoneBrick();

    this.primary = new BlockSet(
        andesite,
        smoothAndesite,
        stair,
        smoothAndesite,
        DoorBlock.oak(),
        BlockType.GLOWSTONE.getBrush(),
        BlockType.WATER_FLOWING.getBrush()
    );
    this.secondary = this.primary;
  }};
  public static ThemeBase HELL = new ThemeBase() {{

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.NETHERBRICK.getBrush(), 200);
    walls.addBlock(BlockType.NETHERRACK.getBrush(), 20);
    walls.addBlock(BlockType.ORE_QUARTZ.getBrush(), 20);
    walls.addBlock(BlockType.SOUL_SAND.getBrush(), 15);
    walls.addBlock(BlockType.NETHER_WART_BLOCK.getBrush(), 10);
    walls.addBlock(BlockType.COAL_BLOCK.getBrush(), 5);

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(walls, 1500);
    floor.addBlock(BlockType.RED_NETHERBRICK.getBrush(), 500);
    floor.addBlock(BlockType.REDSTONE_BLOCK.getBrush(), 50);
    if (RogueConfig.PRECIOUSBLOCKS.getBoolean()) {
      floor.addBlock(BlockType.GOLD_BLOCK.getBrush(), 2);
    }
    if (RogueConfig.PRECIOUSBLOCKS.getBoolean()) {
      floor.addBlock(BlockType.DIAMOND_BLOCK.getBrush(), 1);
    }

    StairsBlock stair = StairsBlock.netherBrick();
    BlockBrush pillar = BlockType.OBSIDIAN.getBrush();

    DoorBlock door = DoorBlock.iron();
    BlockBrush lightstone = BlockType.GLOWSTONE.getBrush();
    BlockBrush liquid = BlockType.LAVA_FLOWING.getBrush();

    this.primary = new BlockSet(floor, walls, stair, pillar, door, lightstone, liquid);

    BlockBrush secondaryWalls = BlockType.RED_NETHERBRICK.getBrush();
    BlockBrush secondaryPillar = BlockType.MAGMA.getBrush();
    this.secondary = new BlockSet(floor, secondaryWalls, stair, secondaryPillar, door, lightstone, liquid);
  }};

  public static ThemeBase HOUSE = new ThemeBase() {
    {
      BlockBrush walls = BlockType.BRICK.getBrush();
      StairsBlock stair = StairsBlock.brick();
      BlockBrush pillar = BlockType.GRANITE_POLISHED.getBrush();
      BlockBrush floor = BlockType.GRANITE_POLISHED.getBrush();
      this.primary = new BlockSet(
          floor,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

      Wood wood = Wood.OAK;
      BlockBrush secondaryWalls = wood.getPlanks();
      BlockBrush secondaryFloor = BlockType.ANDESITE_POLISHED.getBrush();
      StairsBlock secondaryStair = StairsBlock.oak();
      BlockBrush secondaryPillar = wood.getLog();
      this.secondary = new BlockSet(
          secondaryFloor,
          secondaryWalls,
          secondaryStair,
          secondaryPillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

    }
  };

  public static ThemeBase ICE = new ThemeBase() {
    {

      this.primary = new BlockSet(
          BlockType.SNOW.getBrush(),
          BlockType.SNOW.getBrush(),
          StairsBlock.quartz(),
          BlockType.ICE_PACKED.getBrush(),
          DoorBlock.iron(),
          BlockType.SEA_LANTERN.getBrush(),
          BlockType.WATER_FLOWING.getBrush());
      this.secondary = this.primary;
    }
  };

  public static ThemeBase JUNGLE = new ThemeBase() {
    {
      BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();
      BlockBrush mossy = BlockType.STONE_BRICK_MOSSY.getBrush();
      BlockBrush chisel = BlockType.STONE_BRICK_CHISELED.getBrush();

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.COBBLESTONE_MOSSY.getBrush(), 50);
      walls.addBlock(mossy, 30);
      walls.addBlock(cracked, 20);
      walls.addBlock(chisel, 15);

      StairsBlock stair = StairsBlock.cobble();

      BlockBrush pillar = chisel;
      BlockBrush pillar2 = Wood.JUNGLE.getLog();

      BlockJumble stairJumble = new BlockJumble();
      for (Direction dir : Direction.CARDINAL) {
        stairJumble.addBlock(StairsBlock.stoneBrick().setFacing(dir));
      }

      BlockWeightedRandom floor = new BlockWeightedRandom();
      floor.addBlock(stairJumble, 1);
      floor.addBlock(walls, 5);


      this.primary = new BlockSet(
          floor,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = new BlockSet(
          chisel,
          chisel,
          stair,
          pillar2,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
    }
  };
  public static ThemeBase MINESHAFT = new ThemeBase() {
    {

      BlockJumble floor = new BlockJumble();
      floor.addBlock(BlockType.GRAVEL.getBrush());
      floor.addBlock(BlockType.DIRT.getBrush());
      floor.addBlock(BlockType.COBBLESTONE.getBrush());

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.STONE_SMOOTH.getBrush(), 50);
      walls.addBlock(BlockType.COBBLESTONE.getBrush(), 15);
      walls.addBlock(BlockType.ORE_REDSTONE.getBrush(), 1);

      this.primary = getPrimaryBlockSet(floor, walls);
      this.secondary = getSecondaryBlockSet(floor);
    }

    private BlockSet getPrimaryBlockSet(BlockJumble floor, BlockWeightedRandom walls) {
      StairsBlock stair = StairsBlock.cobble();
      BlockBrush pillar = BlockType.COBBLESTONE_WALL.getBrush();
      return new BlockSet(
          floor,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
    }

    private BlockSet getSecondaryBlockSet(BlockJumble floor) {
      Wood oak = Wood.OAK;
      return new BlockSet(
          floor,
          oak.getPlanks(),
          oak.getStairs(),
          oak.getPlanks(),
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
    }
  };

  public static ThemeBase MOSSY = new ThemeBase() {
    {

      BlockBrush mossBrick = BlockType.STONE_BRICK_MOSSY.getBrush();
      BlockBrush mossy = BlockType.COBBLESTONE_MOSSY.getBrush();
      BlockBrush cobble = BlockType.COBBLESTONE.getBrush();
      BlockBrush egg = InfestedBlock.getJumble();
      BlockBrush gravel = BlockType.GRAVEL.getBrush();

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(cobble, 60);
      walls.addBlock(mossBrick, 30);
      walls.addBlock(egg, 5);
      walls.addBlock(mossy, 10);
      walls.addBlock(gravel, 15);

      BlockWeightedRandom pillar = new BlockWeightedRandom();
      pillar.addBlock(mossBrick, 20);
      pillar.addBlock(cobble, 5);
      pillar.addBlock(egg, 3);
      pillar.addBlock(mossy, 5);

      BlockWeightedRandom floor = new BlockWeightedRandom();
      floor.addBlock(mossy, 10);
      floor.addBlock(mossBrick, 4);
      floor.addBlock(cobble, 2);
      floor.addBlock(gravel, 1);

      StairsBlock stair = StairsBlock.cobble();
      DoorBlock door = DoorBlock.iron();
      this.primary = new BlockSet(
          floor,
          walls,
          stair,
          walls,
          door,
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = this.primary;
    }
  };

  public static ThemeBase MUDDY = new ThemeBase() {
    {

      BlockWeightedRandom floor = new BlockWeightedRandom();
      floor.addBlock(BlockType.SOUL_SAND.getBrush(), 1);
      floor.addBlock(BlockType.CLAY.getBrush(), 4);
      floor.addBlock(BlockType.DIRT.getBrush(), 3);
      floor.addBlock(BlockType.MYCELIUM.getBrush(), 1);
      floor.addBlock(BlockType.GRAVEL.getBrush(), 3);
      floor.addBlock(BlockType.DIRT_COARSE.getBrush(), 1);

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.COBBLESTONE.getBrush(), 50);
      walls.addBlock(BlockType.COBBLESTONE_MOSSY.getBrush(), 30);
      BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();
      walls.addBlock(cracked, 10);
      walls.addBlock(BlockType.DIRT.getBrush(), 15);
      walls.addBlock(BlockType.CLAY.getBrush(), 30);
      walls.addBlock(BlockType.GRAVEL.getBrush(), 15);

      StairsBlock stair = StairsBlock.cobble();
      BlockBrush mossy = BlockType.STONE_BRICK_MOSSY.getBrush();
      BlockBrush pillar = mossy;

      this.primary = new BlockSet(
          floor,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

      BlockBrush segmentWall = Wood.DARK_OAK.getLog();
      StairsBlock segmentStair = StairsBlock.darkOak();

      this.secondary = new BlockSet(
          floor,
          segmentWall,
          segmentStair,
          segmentWall,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
    }
  };

  public static ThemeBase NETHER = new ThemeBase() {
    {

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.NETHERRACK.getBrush(), 200);
      walls.addBlock(BlockType.ORE_QUARTZ.getBrush(), 30);
      walls.addBlock(BlockType.SOUL_SAND.getBrush(), 30);
      walls.addBlock(BlockType.COAL_BLOCK.getBrush(), 5);

      BlockWeightedRandom floor = new BlockWeightedRandom();
      floor.addBlock(walls, 2000);
      floor.addBlock(BlockType.REDSTONE_BLOCK.getBrush(), 50);
      if (RogueConfig.PRECIOUSBLOCKS.getBoolean()) {
        floor.addBlock(BlockType.GOLD_BLOCK.getBrush(), 2);
      }
      if (RogueConfig.PRECIOUSBLOCKS.getBoolean()) {
        floor.addBlock(BlockType.DIAMOND_BLOCK.getBrush(), 1);
      }

      this.primary = new BlockSet(
          floor,
          walls,
          StairsBlock.netherBrick(),
          BlockType.OBSIDIAN.getBrush(),
          DoorBlock.iron(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.LAVA_FLOWING.getBrush());

      this.secondary = new BlockSet(
          floor,
          walls,
          StairsBlock.netherBrick(),
          BlockType.NETHERBRICK.getBrush(),
          DoorBlock.iron(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.LAVA_FLOWING.getBrush());
    }
  };

  public static ThemeBase NETHER_FORTRESS = new ThemeBase(
      new BlockSet(
          BlockType.NETHERBRICK.getBrush(),
          BlockType.NETHERBRICK.getBrush(),
          StairsBlock.netherBrick(),
          BlockType.OBSIDIAN.getBrush(),
          DoorBlock.iron(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.LAVA_FLOWING.getBrush()),
      new BlockSet(
          BlockType.NETHERBRICK.getBrush(),
          BlockType.NETHERBRICK.getBrush(),
          StairsBlock.netherBrick(),
          BlockType.NETHERBRICK.getBrush(),
          DoorBlock.iron(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.LAVA_FLOWING.getBrush()
      ));

  public static ThemeBase PURPUR = new ThemeBase() {
    {

      BlockBrush walls = BlockType.PURPUR_BLOCK.getBrush();
      StairsBlock stair = StairsBlock.purpur();
      BlockBrush pillar = BlockType.PURPUR_PILLAR.getBrush();

      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      BlockBrush SegmentWall = BlockType.ENDER_BRICK.getBrush();
      this.secondary = new BlockSet(
          SegmentWall,
          SegmentWall,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

    }
  };

  public static ThemeBase PYRAMID = new ThemeBase() {
    {

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.SANDSTONE_SMOOTH.getBrush(), 100);
      walls.addBlock(BlockType.SANDSTONE.getBrush(), 10);
      walls.addBlock(BlockType.CHISELED_SANDSTONE.getBrush(), 5);

      StairsBlock stair = StairsBlock.sandstone();
      BlockBrush pillar = BlockType.SANDSTONE_SMOOTH.getBrush();
      BlockBrush SegmentWall = BlockType.CHISELED_SANDSTONE.getBrush();

      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = new BlockSet(
          SegmentWall,
          SegmentWall,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

    }

  };

  public static ThemeBase QUARTZ = new ThemeBase() {
    {

      BlockBrush walls = BlockType.QUARTZ.getBrush();
      StairsBlock stair = StairsBlock.quartz();
      BlockBrush pillar = Quartz.PILLAR.getBrush().setFacing(Direction.UP);

      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      BlockBrush SegmentWall = Quartz.CHISELED.getBrush();
      this.secondary = new BlockSet(
          SegmentWall,
          SegmentWall,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

    }
  };

  public static ThemeBase RAINBOW = new ThemeBase() {
    {

      BlockStripes rainbow = new BlockStripes();
      for (DyeColor color : DyeColor.values()) {
        rainbow.addBlock(concrete().setColor(color));
      }

      StairsBlock stair = StairsBlock.acacia();
      BlockBrush pillar = Wood.ACACIA.getLog();
      BlockBrush planks = Wood.ACACIA.getPlanks();

      this.primary = new BlockSet(
          rainbow,
          rainbow,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = new BlockSet(
          planks,
          planks,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

    }
  };

  public static ThemeBase SANDSTONE = new ThemeBase() {
    {

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.SANDSTONE.getBrush(), 100);
      walls.addBlock(BlockType.SAND.getBrush(), 5);

      StairsBlock stair = StairsBlock.sandstone();
      BlockBrush pillar = BlockType.SANDSTONE_SMOOTH.getBrush();

      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

      BlockBrush segmentWall = BlockType.CHISELED_SANDSTONE.getBrush();

      this.secondary = new BlockSet(
          segmentWall,
          segmentWall,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

    }

  };

  public static ThemeBase SANDSTONE_RED = new ThemeBase() {
    {

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.RED_SANDSTONE.getBrush(), 100);
      walls.addBlock(BlockType.SAND_RED.getBrush(), 5);

      StairsBlock stair = StairsBlock.redSandstone();

      BlockBrush pillar = BlockType.SMOOTH_RED_SANDSTONE.getBrush();

      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

      BlockBrush segmentWall = BlockType.CHISELED_RED_SANDSTONE.getBrush();

      this.secondary = new BlockSet(
          segmentWall,
          segmentWall,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

    }
  };

  public static ThemeBase SEWER = new ThemeBase() {
    {

      BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();
      BlockBrush mossy = BlockType.STONE_BRICK_MOSSY.getBrush();

      BlockWeightedRandom wall = new BlockWeightedRandom();
      wall.addBlock(BlockType.STONE_BRICK.getBrush(), 10);
      wall.addBlock(mossy, 4);
      wall.addBlock(cracked, 1);

      BlockBrush floor = BlockType.STONE_BRICK.getBrush();

      StairsBlock stair = StairsBlock.stoneBrick();


      this.primary = new BlockSet(
          floor,
          wall,
          stair,
          wall,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

      this.secondary = this.primary;
    }
  };

  public static ThemeBase SNOW = new ThemeBase() {
    {
      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.STONE_BRICK.getBrush(), 10);
      walls.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush(), 1);
      walls.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush(), 1);

      StairsBlock stair = StairsBlock.stoneBrick();
      BlockBrush pillar = Wood.SPRUCE.getLog();

      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

      BlockBrush SegmentWall = BlockType.SNOW.getBrush();
      StairsBlock SegmentStair = StairsBlock.brick();
      BlockBrush pillar2 = BlockType.BRICK.getBrush();

      this.secondary = new BlockSet(
          SegmentWall,
          SegmentWall,
          SegmentStair,
          pillar2,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
    }
  };

  public static ThemeBase SPRUCE = new ThemeBase() {
    {
      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.STONE_BRICK.getBrush(), 20);
      BlockBrush cracked = BlockType.STONE_BRICK_CRACKED.getBrush();

      walls.addBlock(cracked, 10);
      walls.addBlock(BlockType.COBBLESTONE.getBrush(), 5);
      walls.addBlock(BlockType.GRAVEL.getBrush(), 1);

      StairsBlock stair = StairsBlock.stoneBrick();

      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          walls,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

      Wood wood = Wood.SPRUCE;
      BlockBrush segmentWall = wood.getPlanks();
      StairsBlock segmentStair = StairsBlock.spruce();

      BlockBrush pillar = wood.getLog();
      this.secondary = new BlockSet(
          segmentWall,
          segmentWall,
          segmentStair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

    }
  };

  public static ThemeBase STONE = new ThemeBase() {
    {
      BlockBrush walls = BlockType.STONE_BRICK.getBrush();
      StairsBlock stair = StairsBlock.stoneBrick();
      BlockBrush pillar = BlockType.ANDESITE_POLISHED.getBrush();

      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = primary;
    }
  };

  public static ThemeBase TEMPLE = new ThemeBase() {
    {

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(BlockType.PRISMARINE.getBrush(), 5);
      walls.addBlock(BlockType.PRISMARINE_DARK.getBrush(), 10);
      walls.addBlock(BlockType.PRISMITE.getBrush(), 5);
      walls.addBlock(BlockType.SEA_LANTERN.getBrush(), 3);

      StairsBlock stair = StairsBlock.quartz();
      BlockBrush pillar = BlockType.PRISMARINE_DARK.getBrush();
      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = this.primary;
    }

  };

  public static ThemeBase TERRACOTTA = new ThemeBase() {
    {

      BlockJumble blocks = new BlockJumble();
      for (Direction dir : Direction.CARDINAL) {
        blocks.addBlock(ColoredBlock.terracotta().setColor(DyeColor.MAGENTA).setFacing(dir));
      }

      StairsBlock stair = StairsBlock.purpur();
      BlockBrush pillar = BlockType.PURPUR_PILLAR.getBrush();
      BlockBrush deco = BlockType.PURPUR_DOUBLE_SLAB.getBrush();

      this.primary = new BlockSet(
          blocks,
          blocks,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = new BlockSet(
          deco,
          deco,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );

    }
  };

  public static ThemeBase TOWER = new ThemeBase() {
    {

      BlockJumble stone = new BlockJumble();
      stone.addBlock(BlockType.STONE_BRICK.getBrush());
      stone.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush());
      stone.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush());

      BlockWeightedRandom walls = new BlockWeightedRandom();
      walls.addBlock(SingleBlockBrush.AIR, 5);
      walls.addBlock(stone, 30);
      walls.addBlock(BlockType.COBBLESTONE.getBrush(), 10);
      walls.addBlock(BlockType.GRAVEL.getBrush(), 5);

      StairsBlock stair = StairsBlock.stoneBrick();

      BlockBrush pillar = BlockType.ANDESITE_POLISHED.getBrush();
      this.primary = new BlockSet(
          walls,
          walls,
          stair,
          pillar,
          DoorBlock.oak(),
          BlockType.GLOWSTONE.getBrush(),
          BlockType.WATER_FLOWING.getBrush()
      );
      this.secondary = this.primary;

    }
  };
}
