package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.util.Colors;

import net.minecraft.world.World;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;

public class ProfileMagicArcher implements IMonsterProfile {

  @Override
  public void equip(World world, Random random, int level, IEntity mob) {
    mob.setMobClass(MobType.STRAY, false);
    mob.equipBow(world, random, level);
    mob.equipArrows(Arrow.newRandomHarmful(random));
    mob.equipArmor(world, random, level, Colors.DEEP_VIOLET);
  }

}
