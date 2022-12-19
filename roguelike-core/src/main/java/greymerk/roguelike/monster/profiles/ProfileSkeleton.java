package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.block.spawner.MobType;

import java.util.Random;

import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfileType;

public class ProfileSkeleton implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {
    mob.setMobType(MobType.SKELETON);

    if (level == 3 && rand.nextInt(40) == 0) {
      return MonsterProfileType.POISON_ARCHER.apply(mob, level, difficulty, rand).withMobType(MobType.STRAY);
    }

    if (level > 1 && rand.nextInt(50) == 0) {
      return MonsterProfileType.MAGIC_ARCHER.apply(mob, level, difficulty, rand).withMobType(MobType.STRAY);
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
