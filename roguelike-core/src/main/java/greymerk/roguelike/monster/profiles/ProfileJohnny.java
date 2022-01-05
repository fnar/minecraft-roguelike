package greymerk.roguelike.monster.profiles;

import com.github.fnar.roguelike.loot.special.tools.SpecialAxe;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.Mob;

public class ProfileJohnny implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random random) {
    mob.equipMainhand(new SpecialAxe(random, 4).complete());
    mob.equipArmor(random, level, Color.random(), difficulty);
    mob.setName("Johnny");
    return mob;
  }

}
