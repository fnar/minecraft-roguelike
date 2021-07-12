package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import com.github.fnar.minecraft.item.ToolType;
import com.github.fnar.roguelike.loot.special.tools.SpecialTool;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class ItemTool extends ItemBase {

  private static final Map<Integer, IWeighted<Quality>> toolQuality = new HashMap<>();
  static {
    ItemTool.loadToolQualityOddsTable();
  }

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

  public static void loadToolQualityOddsTable() {
    for (int i = 0; i < 5; ++i) {
      WeightedRandomizer<Quality> tool = new WeightedRandomizer<>();
      switch (i) {
        case 0:
          tool.add(new WeightedChoice<>(Quality.WOOD, 10));
          tool.add(new WeightedChoice<>(Quality.STONE, 20));
          tool.add(new WeightedChoice<>(Quality.IRON, 10));
          tool.add(new WeightedChoice<>(Quality.GOLD, 3));
          tool.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 1:
          tool.add(new WeightedChoice<>(Quality.WOOD, 2));
          tool.add(new WeightedChoice<>(Quality.STONE, 10));
          tool.add(new WeightedChoice<>(Quality.IRON, 10));
          tool.add(new WeightedChoice<>(Quality.GOLD, 3));
          tool.add(new WeightedChoice<>(Quality.DIAMOND, 1));
          break;
        case 2:
          tool.add(new WeightedChoice<>(Quality.WOOD, 1));
          tool.add(new WeightedChoice<>(Quality.STONE, 5));
          tool.add(new WeightedChoice<>(Quality.IRON, 10));
          tool.add(new WeightedChoice<>(Quality.GOLD, 5));
          tool.add(new WeightedChoice<>(Quality.DIAMOND, 3));
          break;
        case 3:
          tool.add(new WeightedChoice<>(Quality.WOOD, 1));
          tool.add(new WeightedChoice<>(Quality.STONE, 3));
          tool.add(new WeightedChoice<>(Quality.IRON, 10));
          tool.add(new WeightedChoice<>(Quality.GOLD, 5));
          tool.add(new WeightedChoice<>(Quality.DIAMOND, 5));
          break;
        case 4:
          tool.add(new WeightedChoice<>(Quality.WOOD, 1));
          tool.add(new WeightedChoice<>(Quality.STONE, 2));
          tool.add(new WeightedChoice<>(Quality.IRON, 10));
          tool.add(new WeightedChoice<>(Quality.GOLD, 3));
          tool.add(new WeightedChoice<>(Quality.DIAMOND, 5));
          break;
      }
      toolQuality.put(i, tool);
    }
  }

  public static Quality rollToolQuality(Random rand, int level) {
    return toolQuality.get(level).get(rand);
  }

  @Override
  public ItemStack getLootItem(Random random, int level) {
    if (type == null) {
      return getRandom(random, level, true);
    }

    ToolType toolType = type.asToolType();
    if (toolType == null) {
      return getRandom(random, level, true);
    }
    Quality quality = Optional.ofNullable(this.quality).orElseGet(() -> Quality.get(level));

    Item toolItem = toolType.asItem(quality);
    ItemStack toolItemStack = new ItemStack(toolItem);
    if (enchant) {
      return Enchant.enchantItem(random, toolItemStack, Enchant.getLevel(random, level));
    }
    return toolItemStack;
  }

  public static ItemStack getRandom(Random random, int level, boolean enchant) {
    if (enchant && random.nextInt(20 + (level * 10)) == 0) {
      return SpecialTool.createTool(random, level);
    }
    boolean shouldEnchant = enchant && random.nextInt(6 - level) == 0;
    ToolType toolType = ToolType.random(random);
    Quality quality = rollToolQuality(random, level);
    Item toolItem = toolType.asItem(quality);
    ItemStack toolItemStack = new ItemStack(toolItem);
    if (shouldEnchant) {
      return Enchant.enchantItem(random, toolItemStack, Enchant.getLevel(random, level));
    }
    return toolItemStack;
  }


}
