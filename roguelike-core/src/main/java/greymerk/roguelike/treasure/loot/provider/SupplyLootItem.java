package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.item.Food;
import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.minecraft.material.Crop;
import com.github.fnar.minecraft.material.Wood;

import java.util.Random;

public class SupplyLootItem extends LootItem {

  public SupplyLootItem(int weight, int level) {
    super(weight, level);
  }

  @Override
  public RldItemStack getLootItem(Random rand, int level) {

    if (rand.nextInt(20) == 0) {
      return Food.Type.CARROT.asItem().asStack().withCount(1);
    }
    if (rand.nextInt(20) == 0) {
      return Food.Type.POTATO.asItem().asStack().withCount(1);
    }

    switch (rand.nextInt(8)) {
      case 0:
        return Crop.WHEAT.asSeed().asStack().withCount(rand.nextInt(8) + 1);
      case 1:
        return Crop.PUMPKIN.asSeed().asStack().withCount(rand.nextInt(8) + 1);
      case 2:
        return Crop.MELON.asSeed().asStack().withCount(rand.nextInt(8) + 1);
      case 3:
        return Material.Type.WHEAT.asItem().asStack().withCount(rand.nextInt(8) + 1);
      case 4:
        return BlockType.TORCH.asItem().asStack().withCount(10 + rand.nextInt(10));
      case 5:
        return Material.Type.PAPER.asItem().asStack().withCount(rand.nextInt(8) + 1);
      case 6:
        return Material.Type.BOOK.asItem().asStack().withCount(rand.nextInt(4) + 1);
      case 7:
        return Wood.chooseRandom().asSapling().asStack().withCount(rand.nextInt(4));
      default:
        return Material.Type.STICK.asItem().asStack().withCount(1);
    }
  }
}
