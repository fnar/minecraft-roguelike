package greymerk.roguelike.worldgen;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.block.Material;
import com.github.fnar.minecraft.block.SingleBlockBrush;
import com.github.fnar.minecraft.block.decorative.Plant;
import com.github.fnar.minecraft.block.decorative.Skull;
import com.github.fnar.minecraft.block.normal.StairsBlock;
import com.github.fnar.minecraft.block.spawner.Spawner;
import com.github.fnar.minecraft.item.RldItemStack;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

import greymerk.roguelike.TreasureChestEditor;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.util.DyeColor;

public interface WorldEditor {

  int FURNACE_FUEL_SLOT = 1;

  void generateSpawner(Spawner spawner, Coord cursor, int level);

  boolean isSolidBlock(Coord coord);

  boolean isOpaqueBlock(Coord coord);

  boolean isOpaqueCubeBlock(Coord coord);

  boolean isBlockOfTypeAt(BlockType blockType, Coord coord);

  boolean isMaterialAt(Material material, Coord coord);

  Random getRandom();

  boolean setBlock(Coord pos, SingleBlockBrush singleBlockBrush, boolean fillAir, boolean replaceSolid);

  boolean isAirBlock(Coord pos);

  long getSeed();

  void spiralStairStep(Random rand, Coord origin, StairsBlock stair, BlockBrush fill);

  void fillDown(Coord origin, BlockBrush blocks);

  TileEntity getTileEntity(Coord pos);

  boolean validGroundBlock(Coord pos);

  Map<BlockType, Integer> getStats();

  boolean canPlace(SingleBlockBrush block, Coord pos, Direction dir);

  PositionInfo getInfo(Coord pos);

  Coord findNearestStructure(VanillaStructure type, Coord pos);

  TreasureChestEditor getTreasureChestEditor();

  void setBedColorAt(Coord cursor, DyeColor color);

  void setItem(Coord pos, int slot, ItemStack item);

  void setItem(Coord pos, int slot, RldItemStack itemStack);

  void setFlowerPotContent(Coord pos, Plant choice);

  void setSkull(WorldEditor editor, Coord cursor, Direction dir, Skull type);

  void setLootTable(Coord pos, String table);

  default int getSeed(Coord coord) {
    return Objects.hash(coord.hashCode(), getSeed());
  }

  int getCapacity(TreasureChest treasureChest);

  boolean isEmptySlot(TreasureChest treasureChest, int slot);

}
