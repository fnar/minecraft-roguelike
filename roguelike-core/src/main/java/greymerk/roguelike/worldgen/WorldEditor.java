package greymerk.roguelike.worldgen;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.Material;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.Plant;
import com.github.fnar.minecraft.block.decorative.Skull;
import com.github.fnar.minecraft.block.spawner.Spawner;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.world.BiomeTag;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.util.DyeColor;

public interface WorldEditor {

  int FURNACE_FUEL_SLOT = 1;

  void generateSpawner(Spawner spawner, Coord cursor);

  boolean isSolidBlock(Coord coord);

  boolean isOpaqueBlock(Coord coord);

  boolean isOpaqueCubeBlock(Coord coord);

  boolean isBlockOfTypeAt(BlockType blockType, Coord coord);

  boolean isMaterialAt(Material material, Coord coord);

  Random getRandom();

  Random getRandom(Coord pos);

  boolean setBlock(Coord pos, SingleBlockBrush singleBlockBrush, boolean fillAir, boolean replaceSolid);

  boolean isAirBlock(Coord pos);

  long getSeed();

  void fillDown(Coord origin, BlockBrush blocks);

  boolean isValidGroundBlock(Coord pos);

  Map<BlockType, Integer> getStats();

  boolean canPlace(SingleBlockBrush block, Coord pos, Direction dir);

  Coord findNearestStructure(VanillaStructure type, Coord pos);

  void setBedColorAt(Coord cursor, DyeColor color);

  void setItem(Coord pos, int slot, RldItemStack itemStack);

  void setFlowerPotContent(Coord pos, Plant choice);

  void setSkull(WorldEditor editor, Coord cursor, Direction dir, Skull type);

  void setLootTable(Coord pos, String table);

  default int getSeed(Coord coord) {
    return Objects.hash(coord.hashCode(), getSeed());
  }

  int getCapacity(TreasureChest treasureChest);

  boolean isEmptySlot(TreasureChest treasureChest, int slot);

  TreasureManager getTreasureManager();

  int getDimension();

  boolean isBiomeTypeAt(BiomeTag biomeTag, Coord coord);

  String getBiomeName(Coord coord);

  List<String> getBiomeTagNames(Coord coord);
}
