package com.github.fnar.minecraft.tag;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

public class TagMapper1_14 {

  public INBT map(Tag tag) {
    switch (tag.getType()) {
      default: // this is a dumb default.
      case INT:
        return map((IntTag) tag);
      case STRING:
        return map((StringTag) tag);
      case COMPOUND:
        return map((CompoundTag) tag);
      case LIST:
        return map((ListTag) tag);
    }
  }

  public StringNBT map(StringTag stringTag) {
    return new StringNBT(stringTag.getValue());
  }

  public IntNBT map(IntTag intTag) {
    return new IntNBT(intTag.getValue());
  }

  public ListNBT map(ListTag tagList) {
    ListNBT nbtTagList = new ListNBT();

    tagList.getTags()
        .forEach(tag -> nbtTagList.add(map(tag)));

    return nbtTagList;
  }

  public CompoundNBT map(CompoundTag compoundTag) {
    CompoundNBT nbtTagCompound = new CompoundNBT();

    compoundTag.getTags()
        .forEach(tag ->
            nbtTagCompound.put(tag.getKey(), map(tag.getValue())));

    return nbtTagCompound;
  }
}
