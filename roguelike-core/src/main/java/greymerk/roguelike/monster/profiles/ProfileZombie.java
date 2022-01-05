package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.MonsterProfileType;
import greymerk.roguelike.treasure.loot.provider.ToolLootItem;

public class ProfileZombie implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {


    if (level == 4 && rand.nextInt(20) == 0) {
      return MonsterProfileType.PIG_ZOMBIE.apply(mob, level, difficulty, rand);
    }

    if (level == 3 && rand.nextInt(100) == 0) {
      return MonsterProfileType.WITCH.apply(mob, level, difficulty, rand);
    }

    if (level == 2 && rand.nextInt(300) == 0) {
      return MonsterProfileType.EVOKER.apply(mob, level, difficulty, rand);
    }

    if (level == 1 && rand.nextInt(200) == 0) {
      return MonsterProfileType.JOHNNY.apply(mob, level, difficulty, rand);
    }

    if (rand.nextInt(100) == 0) {
      return MonsterProfileType.RLEAHY.apply(mob, level, difficulty, rand);
    }

    if (rand.nextInt(100) == 0) {
      return MonsterProfileType.ASHLEA.apply(mob, level, difficulty, rand);
    }

    if (rand.nextInt(40) == 0) {
      return MonsterProfileType.BABY.apply(mob, level, difficulty, rand);
    }

    if (level > 1 && rand.nextInt(20) == 0) {
      return MonsterProfileType.HUSK.apply(mob, level, difficulty, rand);
    }

    if (level < 3 && rand.nextInt(20) == 0) {
      return MonsterProfileType.ZOMBIE_VILLAGER.apply(mob, level, difficulty, rand);
    }

    mob.equipMainhand(ToolLootItem.get(rand, level, difficulty));
    mob.equipShield(rand);
    mob.equipArmor(rand, level, Color.random(), difficulty);
    return mob;
  }

}
