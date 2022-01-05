package greymerk.roguelike.monster.profiles;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.monster.MonsterProfileType;
import greymerk.roguelike.treasure.loot.provider.ToolLootItem;

public class ProfileBaby implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {
    mob.setChild(true);

    if (rand.nextBoolean()) {
      return MonsterProfileType.ZOMBIE_VILLAGER.apply(mob, level, difficulty, rand);
    }

    mob.equipMainhand(ToolLootItem.get(rand, level, difficulty));
    return mob;
  }

}
