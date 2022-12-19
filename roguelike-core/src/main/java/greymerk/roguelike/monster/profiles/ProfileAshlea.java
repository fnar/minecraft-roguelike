package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.MonsterProfileType;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;

public class ProfileAshlea implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {
    MonsterProfileType.ZOMBIE_VILLAGER.apply(mob, level, difficulty, rand);
    mob.setChild(true);
    mob.equipMainhand(ItemNovelty.ashleasOatmealCookie());
    mob.equipArmor(rand, level, Color.PINK_FLAMINGO, difficulty);
    return mob;
  }
}
