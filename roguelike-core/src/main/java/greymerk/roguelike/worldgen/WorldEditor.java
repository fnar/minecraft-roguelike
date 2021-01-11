package greymerk.roguelike.worldgen;

import com.github.srwaggon.roguelike.minecraft.item.RldItemStack;
import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;
import com.github.srwaggon.roguelike.worldgen.block.BlockType;
import com.github.srwaggon.roguelike.worldgen.block.Material;
import com.github.srwaggon.roguelike.worldgen.block.decorative.Plant;
import com.github.srwaggon.roguelike.worldgen.block.decorative.Skull;
import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Map;
import java.util.Random;

import greymerk.roguelike.TreasureChestEditor;
import greymerk.roguelike.util.DyeColor;

public interface WorldEditor {

  int FURNACE_FUEL_SLOT = 1;

  boolean isSolidBlock(Coord coord);

  boolean isOpaqueBlock(Coord coord);

  boolean isOpaqueCubeBlock(Coord coord);

  boolean isBlockOfTypeAt(BlockType blockType, Coord coord);

  boolean isMaterialAt(Material material, Coord coord);

  Random getRandom();

  boolean setBlock(Coord pos, SingleBlockBrush singleBlockBrush, boolean fillAir, boolean replaceSolid);

  boolean isAirBlock(Coord pos);

  long getSeed();

  Random getSeededRandom(int a, int b, int c);

  void spiralStairStep(Random rand, Coord origin, StairsBlock stair, BlockBrush fill);

  void fillDown(Coord origin, BlockBrush blocks);

  TileEntity getTileEntity(Coord pos);

  boolean validGroundBlock(Coord pos);

  int getStat(Block type);

  Map<Block, Integer> getStats();

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
}
