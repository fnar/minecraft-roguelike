package greymerk.roguelike.treasure.loot;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static net.minecraft.init.Items.*;

public enum Equipment {
  SWORD("sword", false, WOODEN_SWORD, STONE_SWORD, IRON_SWORD, GOLDEN_SWORD, DIAMOND_SWORD),
  BOW("minecraft:bow", false, Items.BOW, Items.BOW, Items.BOW, Items.BOW, Items.BOW),
  HELMET("helmet", true, LEATHER_HELMET, CHAINMAIL_HELMET, IRON_HELMET, GOLDEN_HELMET, DIAMOND_HELMET),
  CHEST("chestplate", true, LEATHER_CHESTPLATE, CHAINMAIL_CHESTPLATE, IRON_CHESTPLATE, GOLDEN_CHESTPLATE, DIAMOND_CHESTPLATE),
  LEGS("leggings", true, LEATHER_LEGGINGS, CHAINMAIL_LEGGINGS, IRON_LEGGINGS, GOLDEN_LEGGINGS, DIAMOND_LEGGINGS),
  FEET("boots", true, LEATHER_BOOTS, CHAINMAIL_BOOTS, IRON_BOOTS, GOLDEN_BOOTS, DIAMOND_BOOTS),
  PICK("pickaxe", false, WOODEN_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, GOLDEN_PICKAXE, DIAMOND_PICKAXE),
  AXE("axe", false, WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE),
  SHOVEL("shovel", false, WOODEN_SHOVEL, STONE_SHOVEL, IRON_SHOVEL, GOLDEN_SHOVEL, DIAMOND_SHOVEL),
  ;

  Equipment(String name, boolean isArmor, Item wood, Item stone, Item iron, Item gold, Item diamond) {
    this.name = name;
    this.isArmor = isArmor;
    this.wood = wood;
    this.stone = stone;
    this.iron = iron;
    this.gold = gold;
    this.diamond = diamond;
  }

  private String name;
  private boolean isArmor;
  private Item wood;
  private Item stone;
  private Item iron;
  private Item gold;
  private Item diamond;

  public ItemStack get(Quality quality) {
    return new ItemStack(getItem(quality));
  }

  private Item getItem(Quality quality) {
    switch (quality) {
      case WOOD: return wood;
      case STONE: return stone;
      case IRON: return iron;
      case GOLD: return gold;
      case DIAMOND: return diamond;
      default: return STICK;
    }
  }

  public String getMinecraftName(Quality quality) {
    String qualityName = isArmor
        ? quality.getArmorName()
        : quality.getToolName();
    return "minecraft:" + qualityName + "_" + name;
  }

}
