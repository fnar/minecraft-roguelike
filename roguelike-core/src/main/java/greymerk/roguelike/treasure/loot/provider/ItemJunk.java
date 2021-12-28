package greymerk.roguelike.treasure.loot.provider;

import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.minecraft.item.ItemMapper1_12;
import com.github.fnar.minecraft.item.Potion;
import com.github.fnar.minecraft.item.RldItemStack;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.Shield;

public class ItemJunk extends ItemBase {

  public ItemJunk(int weight, int level) {
    super(weight, level);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {

    if (level > 0 && rand.nextInt(200) == 0) {
      if (level > 2 && rand.nextInt(10) == 0) {
        return new ItemStack(Items.DIAMOND_HORSE_ARMOR, 1, 0);
      }
      if (level > 1 && rand.nextInt(5) == 0) {
        return new ItemStack(Items.GOLDEN_HORSE_ARMOR, 1, 0);
      }
      if (rand.nextInt(3) == 0) {
        return new ItemStack(Items.IRON_HORSE_ARMOR, 1, 0);
      }
      return new ItemStack(Items.SADDLE);
    }

    if (rand.nextInt(100) == 0) {
      return PotionMixture.chooseRandomPotion(rand);
    }

    if (level > 1 && rand.nextInt(100) == 0) {
      return new ItemStack(Items.GHAST_TEAR);
    }

    if (level < 3 && rand.nextInt(80) == 0) {
      return new ItemStack(Items.BOOK);
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
          return new ItemStack(Items.GUNPOWDER, 1 + rand.nextInt(3));
        case 1:
          return new ItemStack(Items.BLAZE_POWDER, 1 + rand.nextInt(3));
        case 2:
          return new ItemStack(Items.GOLD_NUGGET, 1 + rand.nextInt(3));
        case 3:
          return new ItemStack(Items.REDSTONE, 1 + rand.nextInt(3));
        case 4:
          return new ItemStack(Items.GLOWSTONE_DUST, 1 + rand.nextInt(8));
        case 5:
          return new ItemStack(Items.DYE, 1 + rand.nextInt(3));
      }
    }

    if (rand.nextInt(60) == 0) {
      return PotionMixture.getPotion(rand, PotionMixture.LAUDANUM);
    }

    if (rand.nextInt(30) == 0) {
      return new ItemStack(Blocks.TORCH, 6 + rand.nextInt(20));
    }

    if (level > 0 && rand.nextInt(8) == 0) {
      switch (rand.nextInt(8)) {
        case 0:
          return new ItemStack(Items.SLIME_BALL);
        case 1:
          return new ItemStack(Items.SNOWBALL);
        case 2:
          return new ItemStack(Items.MUSHROOM_STEW);
        case 3:
          return new ItemStack(Items.CLAY_BALL);
        case 4:
          return new ItemStack(Items.FLINT);
        case 5:
          return new ItemStack(Items.FEATHER);
        case 6:
          return new ItemStack(Items.GLASS_BOTTLE);
        case 7:
          return new ItemStack(Items.LEATHER);
      }
    }

    switch (rand.nextInt(7)) {
      case 0:
        return new ItemStack(Items.BONE);
      case 1:
        return new ItemStack(Items.ROTTEN_FLESH);
      case 2:
        return new ItemStack(Items.SPIDER_EYE);
      case 3:
        return new ItemStack(Items.PAPER);
      case 4:
        return new ItemStack(Items.STRING);
      case 5:
      default:
        return new ItemStack(Items.STICK);
    }
  }

  private ItemStack getTippedArrow(Random rand, int level) {
    int count = 4 + rand.nextInt(level) * 2;

    RldItemStack rldItemStack = Arrow.newArrow()
        .withTip(Potion.newPotion()
            .withEffect(Potion.Effect.chooseRandom(rand)))
        .asItemStack()
        .withCount(count);

    return new ItemMapper1_12().map(rldItemStack);
  }
}
