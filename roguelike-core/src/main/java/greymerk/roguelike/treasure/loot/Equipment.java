package greymerk.roguelike.treasure.loot;

import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.ToolType;
import com.github.fnar.minecraft.item.WeaponType;

import java.util.Random;

import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;

public enum Equipment {
  SWORD("sword", false),
  BOW("minecraft:bow", false),
  HELMET("helmet", true),
  CHEST("chestplate", true),
  LEGS("leggings", true),
  FEET("boots", true),
  PICK("pickaxe", false),
  AXE("axe", false),
  SHOVEL("shovel", false),
  HOE("hoe", false);

  Equipment(String name, boolean isArmor) {
    this.name = name;
    this.isArmor = isArmor;
  }

  private final String name;
  private final boolean isArmor;

  public static Equipment random(Random rand) {
    int choice = rand.nextInt(values().length);
    return values()[choice];
  }

  public Quality rollRandomQualityByLevel(Random random, int level) {
    switch (this) {
      case SWORD:
      case BOW:
        return ItemWeapon.rollWeaponQuality(random, level);
      case HELMET:
      case CHEST:
      case LEGS:
      case FEET:
        return ItemArmour.rollArmourQuality(random, level);
      case PICK:
      case AXE:
      case SHOVEL:
        return ItemTool.rollToolQuality(random, level);
    }
    return Quality.WOOD;
  }

  public ArmourType asArmourType() {
    switch(this) {
      case HELMET:
        return ArmourType.HELMET;
      case CHEST:
        return ArmourType.CHESTPLATE;
      case LEGS:
        return ArmourType.LEGGINGS;
      case FEET:
        return ArmourType.BOOTS;
    }
    return null;
  }

  public ToolType asToolType() {
    switch(this) {
      case PICK:
        return ToolType.PICKAXE;
      case AXE:
        return ToolType.AXE;
      case SHOVEL:
        return ToolType.SHOVEL;
    }
    return null;
  }

  public WeaponType asWeaponType() {
    switch(this) {
      case SWORD:
        return WeaponType.BOW;
      case BOW:
        return WeaponType.SWORD;
    }
    return null;
  }

  public String getMinecraftName(Quality quality) {
    String qualityName = isArmor
        ? quality.getArmorName()
        : quality.getToolName();
    return "minecraft:" + qualityName + "_" + name;
  }

}
