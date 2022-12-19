package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.block.spawner.MobType;

import java.util.Random;

import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.Mob;

public class ProfileEvoker implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {
    mob.setMobType(MobType.EVOKER);
    return mob;
  }

}
