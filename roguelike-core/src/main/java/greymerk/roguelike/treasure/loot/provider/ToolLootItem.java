package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.item.Tool;
import com.github.fnar.minecraft.item.ToolType;
import com.github.fnar.roguelike.loot.special.tools.SpecialTool;

import java.util.Optional;
import java.util.Random;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class ToolLootItem extends LootItem {

  private Equipment type;
  private Quality quality;
  private boolean enchant;

  public ToolLootItem(int weight, int level) {
    super(weight, level);
  }

  public ToolLootItem(int weight, int level, Equipment type, Quality quality, boolean enchant) {
    super(weight, level);
    this.type = type;
    this.enchant = enchant;
    this.quality = quality;
  }

  @Override
  public RldItemStack getLootItem(Random random, int level) {
    if (type == null) {
      return getRandom(random, level, true);
    }

    ToolType toolType = type.asToolType();
    if (toolType == null) {
      return getRandom(random, level, true);
    }
    Quality quality = Optional.ofNullable(this.quality).orElseGet(() -> Quality.get(level));

    Tool tool = toolType.asItem().withQuality(quality);

    if (enchant) {
      tool.plzEnchantAtLevel(LootItem.getEnchantmentLevel(random, level));
    }
    return tool.asStack();
  }

  public static RldItemStack getRandom(Random random, int level, boolean enchant) {
    if (enchant && random.nextInt(20 + (level * 10)) == 0) {
      return SpecialTool.createTool(random, level);
    }
    ToolType toolType = ToolType.random(random);
    Quality quality = ToolQualityOddsTable.rollToolQuality(random, level);
    Tool tool = toolType.asItem().withQuality(quality);
    if (enchant && random.nextInt(6 - level) == 0) {
      tool.plzEnchantAtLevel(LootItem.getEnchantmentLevel(random, level));
    }
    return tool.asStack();
  }


}
