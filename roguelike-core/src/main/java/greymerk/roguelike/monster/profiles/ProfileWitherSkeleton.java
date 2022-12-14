package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;

public class ProfileWitherSkeleton implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {
    mob.equipSword(level, difficulty, rand);
    mob.equipArmor(rand, level, Color.random(), difficulty);
    return mob;
  }

}
