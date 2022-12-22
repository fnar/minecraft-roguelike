package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.Difficulty;
import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.MonsterProfileType;

public class ProfileZombie implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random random) {
    mob.setMobType(MobType.ZOMBIE);

    if (level == 4 && random.nextInt(20) == 0) {
      return MonsterProfileType.PIG_ZOMBIE.apply(mob, level, difficulty, random);
    }

    if (level == 3 && random.nextInt(100) == 0) {
      return MonsterProfileType.WITCH.apply(mob, level, difficulty, random);
    }

    if (level == 2 && random.nextInt(300) == 0) {
      return MonsterProfileType.EVOKER.apply(mob, level, difficulty, random);
    }

    if (level == 1 && random.nextInt(200) == 0) {
      return MonsterProfileType.JOHNNY.apply(mob, level, difficulty, random);
    }

    if (random.nextInt(100) == 0) {
      return MonsterProfileType.RLEAHY.apply(mob, level, difficulty, random);
    }

    if (random.nextInt(100) == 0) {
      return MonsterProfileType.ASHLEA.apply(mob, level, difficulty, random);
    }

    if (random.nextInt(40) == 0) {
      return MonsterProfileType.BABY.apply(mob, level, difficulty, random);
    }

    if (level > 1 && random.nextInt(20) == 0) {
      return MonsterProfileType.HUSK.apply(mob, level, difficulty, random);
    }

    if (level < 3 && random.nextInt(20) == 0) {
      return MonsterProfileType.ZOMBIE_VILLAGER.apply(mob, level, difficulty, random);
    }

    mob.equipTool(random, level, Difficulty.fromInt(difficulty));
    mob.equipShield(random);
    mob.equipArmor(random, level, Color.random(), difficulty);
    return mob;
  }

}
