package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.Firework;
import com.github.fnar.util.Color;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.ListNBT;

import java.util.List;
import java.util.stream.Collectors;

public class FireworkMapper1_14 extends RldBaseItemMapper1_14<Firework> {

  @Override
  public Class<Firework> getClazz() {
    return Firework.class;
  }

  @Override
  public ItemStack map(Firework firework) {
    ListNBT explosionsTag = mapExplosions(firework);

    CompoundNBT fireworks = new CompoundNBT();
    fireworks.putByte("Flight", (byte) firework.getFlightLength().ordinal());
    fireworks.put("Explosions", explosionsTag);

    CompoundNBT tag = new CompoundNBT();
    tag.put("Fireworks", fireworks);

    ItemStack rocket = new ItemStack(Items.FIREWORK_ROCKET);
    rocket.setTag(tag);

    return rocket;
  }

  private static ListNBT mapExplosions(Firework firework) {
    ListNBT explosionsTag = new ListNBT();
    firework.getExplosions().stream()
        .map(FireworkMapper1_14::mapExplosion)
        .forEach(explosionsTag::add);
    return explosionsTag;
  }

  private static CompoundNBT mapExplosion(Firework.Explosion explosion) {
    CompoundNBT explosionTag = new CompoundNBT();
    explosionTag.putByte("Flicker", (byte) (explosion.hasFlicker() ? 1 : 0));
    explosionTag.putByte("Trail", (byte) (explosion.hasTrail() ? 1 : 0));
    explosionTag.putByte("Type", (byte) explosion.getShape().ordinal());
    explosionTag.put("Colors", new IntArrayNBT(mapColors(explosion.getColors())));
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
}
