package greymerk.roguelike.monster.profiles;

import java.util.Random;

import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfileType;

public class ProfileSkeleton implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {

    if (level == 3 && rand.nextInt(40) == 0) {
      return MonsterProfileType.POISON_ARCHER.apply(mob, level, difficulty, rand);
    }

    if (level > 1 && rand.nextInt(50) == 0) {
      return MonsterProfileType.MAGIC_ARCHER.apply(mob, level, difficulty, rand);
    }

    if (level > 1 && rand.nextInt(10) == 0) {
      return MonsterProfileType.WITHER_SKELETON.apply(mob, level, difficulty, rand);
    }

    if (level > 0 && rand.nextInt(20) == 0) {
      return MonsterProfileType.SWORDSMAN.apply(mob, level, difficulty, rand);
    }

    return MonsterProfileType.ARCHER.apply(mob, level, difficulty, rand);
  }

}
