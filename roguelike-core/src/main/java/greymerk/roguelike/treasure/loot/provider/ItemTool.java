package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class ItemTool extends ItemBase {

  private Equipment type;
  private boolean enchant;
  private Quality quality;

  public ItemTool(int weight, int level) {
    super(weight, level);
  }

  public ItemTool(JsonObject data, int weight) throws Exception {
    super(weight);

    this.enchant = !data.has("ench") || data.get("ench").getAsBoolean();

    if (!data.has("level")) {
      throw new Exception("Tool Loot requires a level");
    }
    this.level = data.get("level").getAsInt();


    if (data.has("equipment")) {
      try {
        this.type = Equipment.valueOf(data.get("equipment").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Equipment as: " + data.get("equipment").getAsString());
      }
    }

    if (data.has("quality")) {
      try {
        this.quality = Quality.valueOf(data.get("quality").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Quality as: " + data.get("quality").getAsString());
      }
    }
  }

  public static ItemStack create(Random rand, int level, Quality quality, Equipment toolEquipment, boolean enchant) {
    Quality ensuredQuality = Optional.ofNullable(quality).orElseGet(() -> Quality.get(level));

    ItemStack toolItem = toolEquipment.get(ensuredQuality);
    if (enchant) {
      return Enchant.enchantItem(rand, toolItem, Enchant.getLevel(rand, level));
    }
    return toolItem;
  }

  public static ItemStack getRandom(Random rand, int level, boolean enchant) {

    if (enchant && rand.nextInt(20 + (level * 10)) == 0) {
      ItemSpecialty.createTool(rand, level);
    }

    ItemStack tool = pickTool(rand, level);

    if (enchant && rand.nextInt(6 - level) == 0) {
      Enchant.enchantItem(rand, tool, Enchant.getLevel(rand, level));
    }

    return tool;
  }

  private static ItemStack pickTool(Random rand, int rank) {

    switch (rand.nextInt(3)) {
      case 1:
        return pickAxe(rand, rank);
      case 2:
        return pickShovel(rand, rank);
      case 0:
      default:
        return pickPick(rand, rank);
    }
  }

  private static ItemStack pickAxe(Random rand, int level) {
    return createTool(rand, level, Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE);
  }

  private static ItemStack pickShovel(Random rand, int level) {
    return createTool(rand, level, Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL);
  }

  private static ItemStack pickPick(Random rand, int level) {
    return createTool(rand, level, Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE);
  }

  private static ItemStack createTool(Random rand, int level, Item woodenItem, Item stoneItem, Item ironItem, Item goldenItem, Item diamondItem) {
    Quality quality = Quality.rollToolQuality(rand, level);
    switch (quality) {
      case DIAMOND:
        return new ItemStack(diamondItem);
      case GOLD:
        return new ItemStack(goldenItem);
      case IRON:
        return new ItemStack(ironItem);
      case STONE:
        return new ItemStack(stoneItem);
      case WOOD:
      default:
        return new ItemStack(woodenItem);
    }
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    if (type != null) {
      return create(rand, level, quality, type, enchant);
    }

    return getRandom(rand, level, true);
  }


}
