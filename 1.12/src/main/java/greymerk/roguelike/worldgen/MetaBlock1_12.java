package greymerk.roguelike.worldgen;

import com.google.gson.JsonElement;

import com.github.fnar.minecraft.block.BlockParser1_12;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import greymerk.roguelike.dungeon.settings.DungeonSettingParseException;

public class MetaBlock1_12 {

  private IBlockState state;

  public MetaBlock1_12(Block block) {
    this.state = block.getDefaultState();
  }

  public MetaBlock1_12(JsonElement e) throws DungeonSettingParseException {
    this.state = BlockParser1_12.parse(e);
  }

  public <T extends Comparable<T>, V extends T> MetaBlock1_12 withProperty(IProperty<T> property, V value) {
    this.state = this.state.withProperty(property, value);
    return this;
  }

  public IBlockState getState() {
    return this.state;
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

}
