package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.Difficulty;
import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;

public class ProfileWitherSkeleton implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {
    mob.setMobType(MobType.WITHERSKELETON);
    mob.equipSword(rand, level, Difficulty.fromInt(difficulty));
    mob.equipArmor(rand, level, Color.random(rand), difficulty);
    return mob;
  }

}
