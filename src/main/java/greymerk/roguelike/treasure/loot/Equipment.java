package greymerk.roguelike.treasure.loot;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

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
  ;

  Equipment(String name, boolean isArmor) {
    this.name = name;
    this.isArmor = isArmor;
  }

  private String name;
  private boolean isArmor;

  public static String getName(Equipment equipment, Quality quality) {
    String qualityName = equipment.isArmor
        ? quality.getArmorName()
        : quality.getToolName();
    return "minecraft:" + qualityName + "_" + equipment.name;
  }

  public static ItemStack get(Equipment type, Quality quality) {
    switch (type) {
      case SWORD:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.WOODEN_SWORD);
          case STONE:
            return new ItemStack(Items.STONE_SWORD);
          case IRON:
            return new ItemStack(Items.IRON_SWORD);
          case GOLD:
            return new ItemStack(Items.GOLDEN_SWORD);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_SWORD);
          default:
        }
      case BOW:
        return new ItemStack(Items.BOW);
      case HELMET:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.LEATHER_HELMET);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_HELMET);
          case IRON:
            return new ItemStack(Items.IRON_HELMET);
          case GOLD:
            return new ItemStack(Items.GOLDEN_HELMET);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_HELMET);
          default:
        }
      case CHEST:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.LEATHER_CHESTPLATE);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_CHESTPLATE);
          case IRON:
            return new ItemStack(Items.IRON_CHESTPLATE);
          case GOLD:
            return new ItemStack(Items.GOLDEN_CHESTPLATE);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_CHESTPLATE);
          default:
        }
      case LEGS:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.LEATHER_LEGGINGS);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_LEGGINGS);
          case IRON:
            return new ItemStack(Items.IRON_LEGGINGS);
          case GOLD:
            return new ItemStack(Items.GOLDEN_LEGGINGS);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_LEGGINGS);
          default:
        }
      case FEET:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.LEATHER_BOOTS);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_BOOTS);
          case IRON:
            return new ItemStack(Items.IRON_BOOTS);
          case GOLD:
            return new ItemStack(Items.GOLDEN_BOOTS);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_BOOTS);
          default:
        }
      case PICK:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.WOODEN_PICKAXE);
          case STONE:
            return new ItemStack(Items.STONE_PICKAXE);
          case IRON:
            return new ItemStack(Items.IRON_PICKAXE);
          case GOLD:
            return new ItemStack(Items.GOLDEN_PICKAXE);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_PICKAXE);
          default:
        }
      case AXE:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.WOODEN_AXE);
          case STONE:
            return new ItemStack(Items.STONE_AXE);
          case IRON:
            return new ItemStack(Items.IRON_AXE);
          case GOLD:
            return new ItemStack(Items.GOLDEN_AXE);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_AXE);
          default:
        }
      case SHOVEL:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.WOODEN_SHOVEL);
          case STONE:
            return new ItemStack(Items.STONE_SHOVEL);
          case IRON:
            return new ItemStack(Items.IRON_SHOVEL);
          case GOLD:
            return new ItemStack(Items.GOLDEN_SHOVEL);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_SHOVEL);
          default:
        }
      default:
        return new ItemStack(Items.STICK);
    }
  }
}
