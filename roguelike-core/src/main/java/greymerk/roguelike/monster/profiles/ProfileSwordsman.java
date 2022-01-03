package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.RldItemStack;
import com.github.fnar.util.Color;

import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.treasure.loot.provider.WeaponLootItem;

public class ProfileSwordsman implements IMonsterProfile {

  @Override
  public void equip(World world, Random random, int level, IEntity mob) {
    RldItemStack weapon = random.nextInt(20) == 0
        ? ItemNovelty.valandrahsKiss()
        : WeaponLootItem.getSword(random, level, IMonsterProfile.canEnchant(world.getDifficulty(), random, level));

    mob.equipMainhand(weapon);
    mob.equipShield(random);
    mob.equipArmor(world, random, level, Color.random());
  }

}
