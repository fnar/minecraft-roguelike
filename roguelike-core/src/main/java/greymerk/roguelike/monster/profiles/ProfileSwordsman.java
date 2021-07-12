package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Color;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;

public class ProfileSwordsman implements IMonsterProfile {

  @Override
  public void equip(World world, Random random, int level, IEntity mob) {
    ItemStack weapon = random.nextInt(20) == 0
        ? ItemNovelty.getItem(ItemNovelty.VALANDRAH)
        : ItemWeapon.getSword(random, level, Enchant.canEnchant(world.getDifficulty(), random, level));

    mob.equipMainhand(weapon);
    mob.equipShield(random);
    mob.equipArmor(world, random, level, Color.random());
  }

}
