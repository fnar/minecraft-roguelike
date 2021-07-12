package greymerk.roguelike.monster.profiles;

import com.github.fnar.roguelike.loot.special.tools.SpecialAxe;
import com.github.fnar.util.Color;

import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;

public class ProfileJohnny implements IMonsterProfile {

  @Override
  public void equip(World world, Random random, int level, IEntity mob) {
    mob.setMobClass(MobType.VINDICATOR, false);
    mob.equipMainhand(new SpecialAxe(random, 4).complete());
    mob.equipArmor(world, random, level, Color.random());
    mob.setName("Johnny");
  }

}
