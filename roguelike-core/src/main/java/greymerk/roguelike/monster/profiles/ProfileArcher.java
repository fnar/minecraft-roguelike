package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.Difficulty;
import com.github.fnar.minecraft.item.Arrow;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;

public class ProfileArcher implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random random) {
    mob.equipBow(random, level, Difficulty.fromInt(difficulty));

    boolean hasPoisonTippedArrows = random.nextDouble() < 0.1;
    if (hasPoisonTippedArrows) {
      mob.equipArrows(Arrow.newRandomHarmful(random));
    }
    mob.equipArmor(random, level, Color.random(), difficulty);
    return mob;
  }

}
