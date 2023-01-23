package greymerk.roguelike.monster.profiles;

import com.github.fnar.minecraft.Difficulty;
import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

public class ProfileSwordsman implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random random) {

    if (random.nextDouble() < 0.05) {
      mob.equipMainhand(ItemNovelty.valandrahsKiss());
    } else {
      mob.equipSword(random, level, Difficulty.fromInt(difficulty));
    }

    mob.equipShield(random);
    mob.equipArmor(random, level, Color.random(random), difficulty);
    return mob;
  }

}
