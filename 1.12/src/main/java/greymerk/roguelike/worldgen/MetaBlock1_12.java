package greymerk.roguelike.worldgen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class MetaBlock1_12 {

  private IBlockState state;
  private int flag = 2;

  public <T extends Comparable<T>, V extends T> MetaBlock1_12(Block block, IProperty<T> property, V value) {
    this(block);
    withProperty(property, value);
  }

  public MetaBlock1_12(Block block) {
    this.setState(block.getDefaultState());
  }

  @SuppressWarnings("deprecation")
  public MetaBlock1_12(JsonElement e) throws DungeonSettingParseException {
    JsonObject json = (JsonObject) e;
    String name = json.get("name").getAsString();
    ResourceLocation location = new ResourceLocation(name);
    if (!Block.REGISTRY.containsKey(location)) {
      throw new DungeonSettingParseException("No such block: " + name);
    }
    Block block = Block.REGISTRY.getObject(location);
    int meta = json.has("meta") ? json.get("meta").getAsInt() : 0;
    IBlockState blockState = block.getStateFromMeta(meta);
    this.setState(blockState);
    if (name.contains("leaves")) {
      withProperty(BlockLeaves.DECAYABLE, false);
    }
    flag = json.has("flag") ? json.get("flag").getAsInt() : 2;
  }

  public <T extends Comparable<T>, V extends T> MetaBlock1_12 withProperty(IProperty<T> property, V value) {
    this.state = this.state.withProperty(property, value);
    return this;
  }

  public IBlockState getState() {
    return this.state;
  }

  private void setState(IBlockState state) {
    this.state = state;
  }

  public Block getBlock() {
    return this.getState().getBlock();
  }

  public int getFlag() {
    return this.flag;
  }

  @Override
  public String toString() {
    return this.state.getBlock().getUnlocalizedName();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (!(other instanceof MetaBlock1_12)) {
      return false;
    }

    MetaBlock1_12 otherBlock = (MetaBlock1_12) other;
    return this.getState().equals(otherBlock.getState());
  }

  public Item asItem() {
    return Item.getItemFromBlock(getBlock());
  }
}
