package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.roguelike.loot.special.tools.SpecialAxe;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.treasure.loot.Equipment;

public class ProfileJohnny implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random random) {
    mob.setMobType(MobType.VINDICATOR);
    mob.equipMainhand(new SpecialAxe(random, Equipment.rollQuality(random, 4)).asStack());
    mob.equipArmor(random, level, Color.random(random), difficulty);
    mob.setName("Johnny");
    return mob;
  }

}
