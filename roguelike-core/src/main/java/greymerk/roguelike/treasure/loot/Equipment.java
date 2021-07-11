package greymerk.roguelike.treasure.loot;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

import static net.minecraft.init.Items.CHAINMAIL_BOOTS;
import static net.minecraft.init.Items.CHAINMAIL_CHESTPLATE;
import static net.minecraft.init.Items.CHAINMAIL_HELMET;
import static net.minecraft.init.Items.CHAINMAIL_LEGGINGS;
import static net.minecraft.init.Items.DIAMOND_AXE;
import static net.minecraft.init.Items.DIAMOND_BOOTS;
import static net.minecraft.init.Items.DIAMOND_CHESTPLATE;
import static net.minecraft.init.Items.DIAMOND_HELMET;
import static net.minecraft.init.Items.DIAMOND_LEGGINGS;
import static net.minecraft.init.Items.DIAMOND_PICKAXE;
import static net.minecraft.init.Items.DIAMOND_SHOVEL;
import static net.minecraft.init.Items.DIAMOND_SWORD;
import static net.minecraft.init.Items.GOLDEN_AXE;
import static net.minecraft.init.Items.GOLDEN_BOOTS;
import static net.minecraft.init.Items.GOLDEN_CHESTPLATE;
import static net.minecraft.init.Items.GOLDEN_HELMET;
import static net.minecraft.init.Items.GOLDEN_LEGGINGS;
import static net.minecraft.init.Items.GOLDEN_PICKAXE;
import static net.minecraft.init.Items.GOLDEN_SHOVEL;
import static net.minecraft.init.Items.GOLDEN_SWORD;
import static net.minecraft.init.Items.IRON_AXE;
import static net.minecraft.init.Items.IRON_BOOTS;
import static net.minecraft.init.Items.IRON_CHESTPLATE;
import static net.minecraft.init.Items.IRON_HELMET;
import static net.minecraft.init.Items.IRON_LEGGINGS;
import static net.minecraft.init.Items.IRON_PICKAXE;
import static net.minecraft.init.Items.IRON_SHOVEL;
import static net.minecraft.init.Items.IRON_SWORD;
import static net.minecraft.init.Items.LEATHER_BOOTS;
import static net.minecraft.init.Items.LEATHER_CHESTPLATE;
import static net.minecraft.init.Items.LEATHER_HELMET;
import static net.minecraft.init.Items.LEATHER_LEGGINGS;
import static net.minecraft.init.Items.STICK;
import static net.minecraft.init.Items.STONE_AXE;
import static net.minecraft.init.Items.STONE_PICKAXE;
import static net.minecraft.init.Items.STONE_SHOVEL;
import static net.minecraft.init.Items.STONE_SWORD;
import static net.minecraft.init.Items.WOODEN_AXE;
import static net.minecraft.init.Items.WOODEN_PICKAXE;
import static net.minecraft.init.Items.WOODEN_SHOVEL;
import static net.minecraft.init.Items.WOODEN_SWORD;

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

  private final String name;
  private final boolean isArmor;
  private final Item wood;
  private final Item stone;
  private final Item iron;
  private final Item gold;
  private final Item diamond;

  public static Equipment random(Random rand) {
    Equipment[] equipmentValues = values();
    int choice = rand.nextInt(equipmentValues.length);
    return equipmentValues[choice];
  }

  public ItemStack get(Quality quality) {
    return new ItemStack(getItem(quality));
  }

  private Item getItem(Quality quality) {
    switch (quality) {
      case WOOD:
        return wood;
      case STONE:
        return stone;
      case IRON:
        return iron;
      case GOLD:
        return gold;
      case DIAMOND:
        return diamond;
      default:
        return STICK;
    }
  }

  public String getMinecraftName(Quality quality) {
    String qualityName = isArmor
        ? quality.getArmorName()
        : quality.getToolName();
    return "minecraft:" + qualityName + "_" + name;
  }

}
