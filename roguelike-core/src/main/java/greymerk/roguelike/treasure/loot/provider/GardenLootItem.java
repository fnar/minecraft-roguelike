package greymerk.roguelike.treasure.loot.provider;

import com.google.common.collect.Lists;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.item.Dye;
import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItem;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.material.Crop;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.util.DyeColor;

public class GardenLootItem extends LootItem {

  public GardenLootItem(int weight, int level) {
    super(weight, level);
  }

  @Override
  public RldItemStack getLootItem(Random random) {
    List<RldItemStack> items = Lists.newArrayList();

    Arrays.stream(Crop.values()).map(Crop::asSeed).map(RldItem::asStack).forEach(items::add);
    items.add(BlockType.PUMPKIN.asItem().asStack());
    items.add(BlockType.WHEAT.asItem().asStack());
    items.add(Material.clayBall().asStack());
    items.add(new Dye(DyeColor.WHITE).asStack());

    return items.get(random.nextInt(items.size()));
  }

}
