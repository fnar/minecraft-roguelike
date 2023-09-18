package com.github.fnar.minecraft.item.mapper;

import com.github.fnar.minecraft.item.DesignPattern;

public class DesignMapper1_14 {

  public net.minecraft.tileentity.BannerPattern map(DesignPattern designPattern) {
    try {
      return net.minecraft.tileentity.BannerPattern.valueOf(designPattern.toString());
    } catch (IllegalArgumentException illegalArgumentException) {
      return net.minecraft.tileentity.BannerPattern.BASE;
    }
  }
}
