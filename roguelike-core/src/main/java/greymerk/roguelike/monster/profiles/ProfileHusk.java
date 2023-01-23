package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.Difficulty;
import com.github.fnar.minecraft.block.spawner.MobType;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;

public class ProfileHusk implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random random) {
    mob.setMobType(MobType.HUSK);
    mob.equipTool(random, level, Difficulty.fromInt(difficulty));
    mob.equipShield(random);
    mob.equipArmor(random, level, Color.random(random), difficulty);
    return mob;
  }
}
