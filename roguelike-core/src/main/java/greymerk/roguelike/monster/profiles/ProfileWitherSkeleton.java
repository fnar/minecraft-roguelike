package greymerk.roguelike.monster.profiles;

import com.github.fnar.util.Color;

import java.util.Random;

import greymerk.roguelike.monster.Mob;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.provider.WeaponLootItem;

public class ProfileWitherSkeleton implements MonsterProfile {

  @Override
  public Mob apply(Mob mob, int level, int difficulty, Random rand) {
    mob.equipMainhand(WeaponLootItem.getSword(rand, level, difficulty));
    mob.equipArmor(rand, level, Color.random(), difficulty);
    return mob;
  }

}
