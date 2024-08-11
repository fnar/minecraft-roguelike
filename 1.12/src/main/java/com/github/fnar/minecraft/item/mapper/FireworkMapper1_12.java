package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.Firework;
import com.github.fnar.util.Color;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;

import java.util.List;
import java.util.stream.Collectors;

public class FireworkMapper1_12 extends BaseItemMapper1_12<Firework> {

  private static NBTTagList mapExplosions(Firework firework) {
    NBTTagList explosionsTag = new NBTTagList();
    firework.getExplosions().stream()
        .map(FireworkMapper1_12::mapExplosion)
        .forEach(explosionsTag::appendTag);
    return explosionsTag;
  }

  private static NBTTagCompound mapExplosion(Firework.Explosion explosion) {
    NBTTagCompound explosionTag = new NBTTagCompound();
    explosionTag.setByte("Flicker", (byte) (explosion.hasFlicker() ? 1 : 0));
    explosionTag.setByte("Trail", (byte) (explosion.hasTrail() ? 1 : 0));
    explosionTag.setByte("Type", (byte) explosion.getShape().ordinal());
    explosionTag.setTag("Colors", new NBTTagIntArray(mapColors(explosion.getColors())));
    return explosionTag;
  }

  private static int[] mapColors(List<Color> colors) {
    List<Integer> colorsAsInt = colors.stream().map(Color::asInt).collect(Collectors.toList());
    int[] colorArr = new int[colorsAsInt.size()];
    for (int i = 0; i < colorsAsInt.size(); i++) {
      colorArr[i] = colorsAsInt.get(i);
    }
    return colorArr;
  }

  @Override
  public Class<Firework> getClazz() {
    return Firework.class;
  }

  @Override
  public ItemStack map(Firework firework) {
    NBTTagList explosionsTag = mapExplosions(firework);

    NBTTagCompound fireworks = new NBTTagCompound();
    fireworks.setByte("Flight", (byte) firework.getFlightLength().ordinal());
    fireworks.setTag("Explosions", explosionsTag);

    NBTTagCompound tag = new NBTTagCompound();
    tag.setTag("Fireworks", fireworks);

    ItemStack rocket = new ItemStack(Items.FIREWORKS);
    rocket.setTagCompound(tag);
    rocket = addEnchantmentNbtTags(firework, rocket);

    return rocket;
  }
}
