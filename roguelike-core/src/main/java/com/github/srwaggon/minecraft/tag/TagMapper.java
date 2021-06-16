package com.github.srwaggon.minecraft.tag;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class TagMapper {

  public static NBTBase map(Tag tag) {
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

  public static NBTTagString map(StringTag stringTag) {
    return new NBTTagString(stringTag.getValue());
  }

  public static NBTTagInt map(IntTag intTag) {
    return new NBTTagInt(intTag.getValue());
  }

  public static NBTTagCompound map(CompoundTag compoundTag) {
    NBTTagCompound nbtTagCompound = new NBTTagCompound();

    compoundTag.getTags()
        .forEach(tag ->
            nbtTagCompound.setTag(tag.getKey(), map(tag.getValue())));

    return nbtTagCompound;
  }

  public static NBTTagList map(ListTag tagList) {
    NBTTagList nbtTagList = new NBTTagList();

    tagList.getTags()
        .forEach(tag -> nbtTagList.appendTag(map(tag)));

    return nbtTagList;
  }

}
