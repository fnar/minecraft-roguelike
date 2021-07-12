package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Color;

import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;

public class ProfileWither implements IMonsterProfile {

  @Override
  public void equip(World world, Random rand, int level, IEntity mob) {
    mob.setMobClass(MobType.WITHERSKELETON, false);
    mob.equipMainhand(ItemWeapon.getSword(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level)));
    mob.equipArmor(world, rand, level, Color.random());
  }

}
