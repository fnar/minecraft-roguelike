package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Color;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;

public class ProfilePigman implements IMonsterProfile {

  @Override
  public void addEquipment(World world, Random random, int level, IEntity mob) {
    mob.setMobClass(MobType.PIGZOMBIE, true);
    ItemStack weapon = ItemWeapon.getSword(random, level, true);
    mob.equipMainhand(weapon);
    mob.equipShield(random);
    mob.equipArmor(world, random, level, Color.random());
  }

}
