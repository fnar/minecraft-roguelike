package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.block.BlockType;
import com.github.fnar.minecraft.item.ArmourType;
import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.minecraft.item.Food;
import com.github.fnar.minecraft.item.Ingredient;
import com.github.fnar.minecraft.item.Material;
import com.github.fnar.minecraft.item.Miscellaneous;
import com.github.fnar.minecraft.item.Potion;
import com.github.fnar.minecraft.item.RldItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.util.DyeColor;

public class ItemJunk extends LootItem {

  public ItemJunk(int weight, int level) {
    super(weight, level);
  }

  @Override
  public RldItemStack getLootItem(Random rand, int level) {

    if (level > 0 && rand.nextInt(200) == 0) {
      if (level > 2 && rand.nextInt(10) == 0) {
        return ArmourType.HORSE.asItem().diamond().asStack();
      }
      if (level > 1 && rand.nextInt(5) == 0) {
        return ArmourType.HORSE.asItem().golden().asStack();
      }
      if (rand.nextInt(3) == 0) {
        return ArmourType.HORSE.asItem().iron().asStack();
      }
      return Miscellaneous.Type.SADDLE.asItem().asStack();
    }

    if (rand.nextInt(100) == 0) {
      return PotionMixture.chooseRandomPotion(rand);
    }

    if (level > 1 && rand.nextInt(100) == 0) {
      return Ingredient.Type.GHAST_TEAR.asItem().asStack();
    }

    if (level < 3 && rand.nextInt(80) == 0) {
      return Miscellaneous.Type.BOOK.asItem().asStack();
    }

    if (rand.nextInt(80) == 0) {
      return Shield.get(rand);
    }

    if (level > 1 && rand.nextInt(60) == 0) {
      return getTippedArrow(rand, level);
    }

    if (level > 1 && rand.nextInt(50) == 0) {
      switch (rand.nextInt(6)) {
        case 0:
          return Ingredient.Type.GUNPOWDER.asItem().asStack().withCount(1 + rand.nextInt(3));
        case 1:
          return Ingredient.Type.BLAZE_POWDER.asItem().asStack().withCount(1 + rand.nextInt(3));
        case 2:
          return Material.Type.GOLD_NUGGET.asItem().asStack().withCount(1 + rand.nextInt(3));
        case 3:
          return Ingredient.Type.REDSTONE.asItem().asStack().withCount(1 + rand.nextInt(3));
        case 4:
          return Ingredient.Type.GLOWSTONE_DUST.asItem().asStack().withCount(1 + rand.nextInt(8));
        case 5:
          return DyeColor.WHITE.asItem().asStack().withCount(1 + rand.nextInt(3));
      }
    }

    if (rand.nextInt(60) == 0) {
      return PotionMixture.getPotion(rand, PotionMixture.LAUDANUM);
    }

    if (rand.nextInt(30) == 0) {
      return BlockType.TORCH.asItem().asStack().withCount(rand.nextInt(20) + 6);
    }

    if (level > 0 && rand.nextInt(8) == 0) {
      switch (rand.nextInt(8)) {
        case 0:
          return Material.Type.SLIME_BALL.asItem().asStack();
        case 1:
          return Material.Type.SNOWBALL.asItem().asStack();
        case 2:
          return Food.Type.MUSHROOM_STEW.asItem().asStack();
        case 3:
          return Material.Type.CLAY_BALL.asItem().asStack();
        case 4:
          return Material.Type.FLINT.asItem().asStack();
        case 5:
          return Material.Type.FEATHER.asItem().asStack();
        case 6:
          return Miscellaneous.Type.GLASS_BOTTLE.asItem().asStack();
        case 7:
          return Material.Type.LEATHER.asItem().asStack();
      }
    }

    switch (rand.nextInt(7)) {
      case 0:
        return Material.Type.BONE.asItem().asStack();
      case 1:
        return Food.Type.ROTTEN_FLESH.asItem().asStack();
      case 2:
        return Ingredient.Type.SPIDER_EYE.asItem().asStack();
      case 3:
        return Material.Type.PAPER.asItem().asStack();
      case 4:
        return Material.Type.STRING.asItem().asStack();
      case 5:
      default:
        return Material.Type.STICK.asItem().asStack();
    }
  }

  private RldItemStack getTippedArrow(Random rand, int level) {
    return Arrow.newArrow()
        .withTip(Potion.Effect.chooseRandom(rand).asItem())
        .asStack()
        .withCount(rand.nextInt(level) * 2 + 4);
  }

}
